package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApiException;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssJob;
import com.appliedolap.essbase.EssScript;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.client.model.Script;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * A calculation or MDX script on a cube.
 */
public class EssScriptImpl extends AbstractEssObject implements EssScript {

    private static final Logger logger = LoggerFactory.getLogger(EssScriptImpl.class);

    private static final long JOB_TIMEOUT_MILLIS = 300_000;

    private static final long JOB_POLL_MILLIS = 1_500;

    private final EssCube cube;

    private final Script script;

    private final ScriptType scriptType;

    EssScriptImpl(ApiContext api, EssCube cube, Script script, ScriptType scriptType) {
        super(api);
        this.cube = cube;
        this.script = script;
        this.scriptType = scriptType;
    }

    @Override
    public String getName() {
        return script.getName();
    }

    @Override
    public Type getType() {
        return Type.SCRIPT;
    }

    @Override
    public ScriptType getScriptType() {
        return scriptType;
    }

    @Override
    public EssCube getCube() {
        return cube;
    }

    @Override
    public Long getModifiedTime() {
        return script.getModifiedTime();
    }

    @Override
    public Long getSize() {
        return script.getSizeInBytes();
    }

    @Override
    public String getContent() {
        String body = send("GET", path() + "/content", null, "scriptsGetScriptContent");
        JsonNode parsed = parse(body);
        // The endpoint answers {"content": "..."} rather than the text itself.
        return parsed != null && parsed.hasNonNull("content") ? parsed.get("content").asText() : "";
    }

    @Override
    public void save(String content) {
        logger.info("Saving {} {}", scriptType.getLabel(), getName());
        send("PUT", path(), Map.of("name", getName(), "content", content == null ? "" : content),
                "scriptsEditScript");
    }

    @Override
    public Optional<String> validate() {
        String body;
        try {
            body = send("POST", scriptsPath() + "/scriptops/validate",
                    Map.of("name", getName(), "content", getContent()), "scriptsValidateScript");
        } catch (EssApiException e) {
            // A script that doesn't validate is a 400 with the reason in it. That is an answer, not a
            // failure - the caller asked whether it is valid.
            return Optional.of(e.getMessage());
        }
        return body == null || body.isBlank() ? Optional.empty() : Optional.empty();
    }

    @Override
    public void delete() {
        logger.info("Deleting {} {}", scriptType.getLabel(), getName());
        send("DELETE", path(), null, "scriptsDeleteScript");
    }

    /**
     * Runs the script as a job and waits.
     *
     * <p>The two types are parameterised differently, and neither is guessable. A calculation goes in
     * {@code file} as its bare name; putting it in {@code script} answers "Invalid file name
     * 'CalcAll'. Does not have an extension." An MDX script goes in {@code script} as its file name
     * with the {@code .mdx} extension; putting it in {@code file} runs and then fails inside Essbase
     * with "NULL argument (5) passed to ESSAPI function executeMdxScript".
     */
    @Override
    public void execute() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(cube.getApplication().getName());
        job.setDb(cube.getName());
        ParametersBean params = new ParametersBean();
        if (scriptType == ScriptType.MDX) {
            job.setJobtype(EssJob.JobType.RUN_MDX_SCRIPT.getParam());
            params.setScript(getName() + scriptType.getExtension());
        } else {
            job.setJobtype(EssJob.JobType.RUN_CALCULATION.getParam());
            params.setFile(getName());
        }
        job.setParameters(params);

        try {
            logger.info("Executing {} {}", scriptType.getLabel(), getName());
            JobRecordBean record = awaitCompletion(api.getJobsApi().jobsExecuteJob(job));
            if (!EssJob.Status.fromCode(record.getStatusCode()).isSuccessful()) {
                throw new EssApiException(scriptType.getLabel() + " " + getName() + " failed: "
                        + failureOf(record));
            }
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    private JobRecordBean awaitCompletion(JobRecordBean record) throws ApiException {
        long deadline = System.currentTimeMillis() + JOB_TIMEOUT_MILLIS;
        while (EssJob.Status.fromCode(record.getStatusCode()) == EssJob.Status.IN_PROGRESS) {
            if (System.currentTimeMillis() > deadline) {
                throw new EssApiException(scriptType.getLabel() + " " + getName()
                        + " did not finish within " + (JOB_TIMEOUT_MILLIS / 1000) + " seconds");
            }
            try {
                Thread.sleep(JOB_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new EssApiException("Interrupted while waiting for " + getName());
            }
            record = api.getJobsApi().jobsGetJobInfo(String.valueOf(record.getJobID()));
        }
        return record;
    }

    private static String failureOf(JobRecordBean record) {
        Object info = record.getJobOutputInfo();
        if (info instanceof Map) {
            Object message = ((Map<?, ?>) info).get("errorMessage");
            if (message != null && !message.toString().isBlank()) {
                return message.toString();
            }
        }
        return String.valueOf(record.getStatusMessage());
    }

    private String scriptsPath() {
        return "/applications/" + ApiClient.urlEncode(cube.getApplication().getName())
                + "/databases/" + ApiClient.urlEncode(cube.getName()) + "/scripts";
    }

    private String path() {
        return scriptsPath() + "/" + ApiClient.urlEncode(getName());
    }

    /**
     * Sends a scripts request, always carrying the {@code file} type.
     * <p>
     * Through {@link NativeHttp} rather than the generated client because the type has to be on every
     * call - list, read, write, delete and validate alike - and the generated signatures make it
     * optional, which is exactly the mistake the server punishes.
     */
    private String send(String method, String path, Object body, String operationId) {
        String withType = NativeHttp.withQuery(path, "file", scriptType.getParameter());
        return EssScripts.send(api, method, withType, body, operationId);
    }

    private JsonNode parse(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            return api.getClient().getObjectMapper().readTree(body);
        } catch (IOException e) {
            throw new EssApiException("The server's answer was not JSON: " + body);
        }
    }

}
