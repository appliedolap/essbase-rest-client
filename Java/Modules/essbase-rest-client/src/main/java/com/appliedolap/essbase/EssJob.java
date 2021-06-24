package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.JobsApi;
import com.appliedolap.essbase.client.model.JobRecordBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EssJob extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssJob.class);

    private final JobRecordBean record;

    private final EssServer server;

    private final JobsApi jobsApi;

    public EssJob(ApiClient apiClient, EssServer server, JobRecordBean record) {
        super(apiClient);
        this.server = server;
        this.record = record;
        this.jobsApi = new JobsApi(apiClient);
    }

    public Long getJobID() {
        return record.getJobID();
    }

    public String getJobType() {
        return record.getJobType();
    }

    public boolean isSuccessful() {
        return record.getStatusCode() != null && record.getStatusCode() == 200;
    }

    @Override
    public String getName() {
        return record.getJobID().toString();
    }

    public void rerun() {
        try {
            logger.info("Re-running job {}", record.getJobID());
            jobsApi.jobsExecuteByJobId(record.getJobID());
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    public EssJobStatus getStatus() {
        return EssJobStatus.fromCode(record.getStatusCode());
    }

    public String getDescription() {
        return String.format("ID: %s, type: %s, filename: %s, status message: %s", record.getJobID().toString(), record.getJobType(), record.getJobfileName(), record.getStatusMessage());
    }

}