package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssScript;
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
public class EssScriptImpl extends AbstractEssObject implements EssScript {

    private static final Logger logger = LoggerFactory.getLogger(EssScriptImpl.class);

    private final EssCube cube;

    private final Script script;

    EssScriptImpl(ApiContext api, EssCube cube, Script script) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException();
    }

    @Override
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