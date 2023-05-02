package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.client.model.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a script object (calc, report, etc.) on Essbase cube/application.
 */
public class EssScript extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssScript.class);

    private final EssCube cube;

    private final Script script;

    EssScript(ApiContext api, EssCube cube, Script script) {
        super(api);
        this.cube = cube;
        this.script = script;
    }

    /**
     * The name of this script.
     *
     * @return the script name
     */
    @Override
    public String getName() {
        return script.getName();
    }

    @Override
    public Type getType() {
        return Type.SCRIPT;
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

    /**
     * Create a job to execute this script (assumes calc for now)
     */
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
            JobRecordBean jobRecord = api.getJobsApi().jobsExecuteJob(job);
            logger.info("Executed calc job request, job ID is {}", jobRecord.getJobID());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}