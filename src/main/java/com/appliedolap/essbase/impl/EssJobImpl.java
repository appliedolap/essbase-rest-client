package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Represents a server job, which is any number of different types of actions that have been performed on the
 * server, such as a dimension build, data load, and others. See {@link EssObject.Type} for full list of known types.
 */
public class EssJobImpl extends AbstractEssObject implements EssJob {

    private static final Logger logger = LoggerFactory.getLogger(EssJobImpl.class);

    private final JobRecordBean jobRecord;

    private JobRecordBean detailedRecord;

    private final EssServer server;

    private final JobType type;

    private final Long id;

    public EssJobImpl(ApiContext api, EssServer server, JobRecordBean jobRecord) {
        super(api);
        Objects.requireNonNull(jobRecord.getJobID(), "Job must supply an ID");
        this.server = server;
        this.jobRecord = jobRecord;
        this.id = jobRecord.getJobID();
        this.type = JobType.parse(jobRecord.getJobType());
    }

    private void requireDetailedJob() {
        if (detailedRecord == null) {
            try {
                detailedRecord = api.getJobsApi().jobsGetJobInfo(id.toString());
            } catch (ApiException apiException) {
                throw new EssApiException(apiException);
            }
        }
    }

    @Override
    public Long getJobID() {
        return jobRecord.getJobID();
    }

    @Override
    public boolean isSuccessful() {
        return getStatus().isSuccessful();
    }

    @Override
    public String getName() {
        return id.toString();
    }

    @Override
    public Type getType() {
        return Type.JOB;
    }

    @Override
    public long getDuration() {
        if (jobRecord.getEndTime() != null && jobRecord.getStartTime() != null) {
            return jobRecord.getEndTime() - jobRecord.getStartTime();
        } else {
            logger.warn("Job start/end time was null");
            return 0L;
        }
    }

    @Override
    public EssServer getServer() {
        return server;
    }

    @Override
    public JobType getJobType() {
        return type;
    }

    @Override
    public void rerun() {
        try {
            logger.info("Re-running job {}", jobRecord.getJobID());
            api.getJobsApi().jobsExecuteByJobId(jobRecord.getJobID());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public Status getStatus() {
        if (jobRecord.getStatusCode() != null) {
            return Status.fromCode(jobRecord.getStatusCode());
        } else {
            return Status.UNKNOWN;
        }
    }

    @Override
    public String getErrorMessage() {
        requireDetailedJob();
        Map<String, Object> outputInfo = detailedRecord.getJobOutputInfo();
        if (outputInfo != null && outputInfo.get("errorMessage") != null) {
            return outputInfo.get("errorMessage").toString();
        }
        return null;
    }

    @Override
    public String getDescription() {
        String filename = jobRecord.getJobfileName() == null ? "" : ", filename: " + jobRecord.getJobfileName();
        return String.format("%s (%d)%s", jobRecord.getJobType(), jobRecord.getJobID(), filename);
    }

    @Override
    public EssJob waitForCompletion() throws InterruptedException {
        return waitForCompletion(5, TimeUnit.SECONDS);
    }

    @Override
    public EssJob waitForCompletion(long duration, TimeUnit timeUnit) throws InterruptedException {
        try {
            while (true) {
                JobRecordBean jobRecordBean = api.getJobsApi().jobsGetJobInfo(id.toString());
                EssJob updatedJob = new EssJobImpl(api, server, jobRecordBean);
                if (updatedJob.getStatus().isComplete()) {
                    return updatedJob;
                }
                logger.info("Waiting {} {} to check for update to job {}", duration, timeUnit.toString().toLowerCase(), getDescription());
                timeUnit.sleep(duration);
            }
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

}