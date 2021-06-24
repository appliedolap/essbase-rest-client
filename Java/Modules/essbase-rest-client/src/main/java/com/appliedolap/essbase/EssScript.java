package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.JobsApi;
import com.appliedolap.essbase.client.api.ScriptsApi;
import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.client.model.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EssScript extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssScript.class);

    private final ScriptsApi scriptsApi;

    private final JobsApi jobsApi;

    private final EssCube cube;

    private final Script script;

    public EssScript(ApiClient client, EssCube cube, Script script) {
        super(client);
        this.scriptsApi = new ScriptsApi(client);
        this.jobsApi = new JobsApi(client);
        this.cube = cube;
        this.script = script;
    }

    @Override
    public String getName() {
        return script.getName();
    }

    public Long getModifiedTime() {
        return script.getModifiedTime();
    }

    public Long getSize() {
        return script.getSizeInBytes();
    }

    public String getContent() {
        throw new UnsupportedOperationException();
    }

    public void delete() {
        throw new UnsupportedOperationException();
    }

    public void execute() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(cube.getApplication().getName());
        job.setDb(cube.getName());
        job.setJobtype("calc");

        ParametersBean params = new ParametersBean();
        job.setParameters(params);
        params.setFile(script.getName());

        try {
            logger.info("Executing calc {}", script.getName());
            JobRecordBean jobRecord = jobsApi.jobsExecuteJob(job);
            logger.info("Executed calc job request, job ID is {}", jobRecord.getJobID());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}