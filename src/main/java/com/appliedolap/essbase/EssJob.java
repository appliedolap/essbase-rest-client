package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Represents a server job, which is any number of different types of actions that have been performed on the
 * server, such as a dimension build, data load, and others. See {@link Type} for full list of known types.
 */
public class EssJob extends AbstractEssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssJob.class);

    private final JobRecordBean jobRecord;

    private JobRecordBean detailedRecord;

    private final EssServer server;

    private final JobType type;

    private final Long id;

    public EssJob(ApiContext api, EssServer server, JobRecordBean jobRecord) {
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

    /**
     * Gets the ID of the job.
     *
     * @return the numeric ID of this job
     */
    public Long getJobID() {
        return jobRecord.getJobID();
    }

    /**
     * Check if the job execution was successful.
     *
     * @return true if it was (as based on status code), false otherwise
     */
    public boolean isSuccessful() {
        return getStatus().isSuccessful();
    }

    /**
     * Implemented for conformance to {@link AbstractEssObject}; the name is simply the job ID.
     *
     * @return the name of this job
     */
    @Override
    public String getName() {
        return id.toString();
    }

    @Override
    public Type getType() {
        return Type.JOB;
    }

    /**
     * Gets the duration of this job, in milliseconds. This method may return 0 while the job is executing and only have
     * a useful value upon completion.
     *
     * @return the duration of the job
     */
    public long getDuration() {
        if (jobRecord.getEndTime() != null && jobRecord.getStartTime() != null) {
            return jobRecord.getEndTime() - jobRecord.getStartTime();
        } else {
            logger.warn("Job start/end time was null");
            return 0L;
        }
    }

    /**
     * Gets the owning server for this job.
     *
     * @return the server for this job
     */
    public EssServer getServer() {
        return server;
    }

    /**
     * Gets the type of the job.
     *
     * @return the job type
     */
    public JobType getJobType() {
        return type;
    }

    /**
     * Re-run the job (using the Essbase REST API method to re-run a job).
     */
    public void rerun() {
        try {
            logger.info("Re-running job {}", jobRecord.getJobID());
            api.getJobsApi().jobsExecuteByJobId(jobRecord.getJobID());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Gets the status of this job.
     *
     * @return the job status
     */
    public Status getStatus() {
        if (jobRecord.getStatusCode() != null) {
            return Status.fromCode(jobRecord.getStatusCode());
        } else {
            return Status.UNKNOWN;
        }
    }

    /**
     * Returns the error message for the job, if any. This API will likely change as the jobs API is filled out,
     * since this status is null for successful jobs.
     *
     * @return the error message, if any
     */
    public String getErrorMessage() {
        requireDetailedJob();
        Map<String, Object> outputInfo = detailedRecord.getJobOutputInfo();
        if (outputInfo != null && outputInfo.get("errorMessage") != null) {
            return outputInfo.get("errorMessage").toString();
        }
        return null;
    }

    /**
     * A textual description of the job.
     *
     * @return a slightly longer description of the job
     */
    public String getDescription() {
        String filename = jobRecord.getJobfileName() == null ? "" : ", filename: " + jobRecord.getJobfileName();
        return String.format("%s (%d)%s", jobRecord.getJobType(), jobRecord.getJobID(), filename);
    }

    /**
     * Loops while re-polling the job for its static to change to one that is complete (successfully or otherwise). The
     * default wait interval is 5 seconds, although a custom interval can be specified by using {@link #waitForCompletion(long, TimeUnit)}.
     *
     * @return the updated job object, once this job is complete
     * @throws InterruptedException if interruption is thrown
     */
    public EssJob waitForCompletion() throws InterruptedException {
        return waitForCompletion(5, TimeUnit.SECONDS);
    }

    /**
     * Waits given amount of time for job to complete. See {@link #waitForCompletion()} for more details.
     *
     * @param duration the duration
     * @param timeUnit the time unit of the duration
     * @return the updated job object, once this job is complete
     * @throws InterruptedException if interruption is thrown
     */
    public EssJob waitForCompletion(long duration, TimeUnit timeUnit) throws InterruptedException {
        try {
            while (true) {
                JobRecordBean jobRecordBean = api.getJobsApi().jobsGetJobInfo(id.toString());
                EssJob updatedJob = new EssJob(api, server, jobRecordBean);
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

    /**
     * Known job types.
     */
    public enum JobType {

        LOAD_DATA("dataload", "Data Load"),
        BUILD_DIMENSIONS("dimbuild", "Dimension Build"),
        RUN_CALCULATION("calc", "Calc Execution"),
        CLEAR_DATA("clear", "Clear Data"),
        IMPORT_EXCEL("importExcel", "Import Excel"),
        EXPORT_EXCEL("exportExcel", "Export Excel"),
        LCM_BACKUP("lcmExport", "LCM Export"),
        LCM_RESTORE("lcmImport", "LCM Import"),
        BUILD_AGGREGATION("buildAggregation", "Build Aggregation"),
        CLEAR_AGGREGATIONS("clearAggregation", "Clear Aggregation"),
        EXPORT_DATA("exportData", "Export Data"),
        RUN_MDX_SCRIPT("mdxScript", "MDX Script"),

        /**
         * There is no "other" type for an Essbase job, but this gives us some wiggle room in case a new type is
         * introduced, or we cannot otherwise properly parse the return type.
         */
        UNKNOWN("other", "Other");

        private final String param;

        private final String responseType;

        JobType(String param, String responseType) {
            this.param = param;
            this.responseType = responseType;
        }

        /**
         * Represents the name of the param used in the JSON request to the server. This is an instance of the JSON
         * request format 'leaking' but it is what it is. Having this constant be accessible allows us to reference these
         * consistently when putting together a job request.
         *
         * @return the parameter as used in the request payload to the job submission API
         */
        public String getParam() {
            return param;
        }

        /**
         * The job type as it is returned from the job post API.
         *
         * @return the job type in the job execution response payload
         */
        public String getResponseType() {
            return responseType;
        }

        public static JobType parse(String textType) {
            for (JobType type : JobType.values()) {
                if (type.getResponseType().equals(textType)) {
                    return type;
                }
            }
            return JobType.UNKNOWN;
        }

        @Override
        public String toString() {
            return responseType;
        }

    }

    /**
     * Models the status of a job. This enum may move inside the EssJob object in the future.
     */
    public enum Status {

        /**
         * Represented in the Essbase REST API with code 100.
         */
        IN_PROGRESS(100),

        COMPLETED(200),

        COMPLETED_WITH_WARNINGS(300),

        FAILED(400),

        /**
         * This is not an official code, we're just using it as a buffer against changes in the API
         */
        UNKNOWN(0);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Status fromCode(int code) {
            for (Status jobStatus : Status.values()) {
                if (jobStatus.getCode() == code) {
                    return jobStatus;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + code);
        }

        public boolean isComplete() {
            return this != IN_PROGRESS;
        }

        public boolean isSuccessful() {
            return this == COMPLETED || this == COMPLETED_WITH_WARNINGS;
        }

    }

}