package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Represents a server job, which is any number of different types of actions that have been performed on the
 * server, such as a dimension build, data load, and others. See {@link Type} for full list of known types.
 */
public class EssJob extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssJob.class);

    private final JobRecordBean record;

    private JobRecordBean detailedRecord;

    private final EssServer server;

    private final Type type;

    private final Long id;

    EssJob(ApiContext api, EssServer server, JobRecordBean record) {
        super(api);
        if (record.getJobID() == null) throw new IllegalArgumentException("Job must supply an ID");
        this.server = server;
        this.record = record;
        this.id = record.getJobID();
        this.type = Type.parse(record.getJobType());
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
        return record.getJobID();
    }

    /**
     * Check if the job execution was successful.
     *
     * @return true if it was (as based on status code), false otherwise
     */
    public boolean isSuccessful() {
        return record.getStatusCode() != null && record.getStatusCode() == 200;
    }

    /**
     * This is temporary just to force some output details to log to the console
     */
    public void getDetailedInfo() {
        try {
            detailedRecord = api.getJobsApi().jobsGetJobInfo(id.toString());
            logger.info("Job details: {}", detailedRecord);
            logger.info("Detailed output: {}", detailedRecord.getJobOutputInfo());
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    /**
     * Implemented for conformance to {@link EssObject}; the name is simply the job ID.
     *
     * @return the name of this job
     */
    @Override
    public String getName() {
        return id.toString();
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
    public Type getType() {
        return type;
    }

    /**
     * Re-run the job (using the Essbase REST API method to re-run a job).
     */
    public void rerun() {
        try {
            logger.info("Re-running job {}", record.getJobID());
            api.getJobsApi().jobsExecuteByJobId(record.getJobID());
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    /**
     * Gets the status of this job.
     *
     * @return the job status
     */
    public Status getStatus() {
        return Status.fromCode(record.getStatusCode());
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
        Object errorMessage = outputInfo.get("errorMessage");
        if (errorMessage != null) {
            return errorMessage.toString();
        }
        return null;
    }

    /**
     * A textual description of the job.
     *
     * @return a slightly longer description of the job
     */
    public String getDescription() {
        String filename = record.getJobfileName() == null ? "" : ", filename: " + record.getJobfileName();
        return String.format("%s (%d)%s", record.getJobType(), record.getJobID(), filename);
    }

    /**
     * Known job types.
     */
    public enum Type {

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
         * introduced or we cannot otherwise properly parse the return type.
         */
        UNKNOWN("other", "Other");

        private final String param;

        private final String responseType;

        Type(String param, String responseType) {
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

        public static Type parse(String textType) {
            for (Type type : Type.values()) {
                if (type.getResponseType().equals(textType)) {
                    return type;
                }
            }
            return Type.UNKNOWN;
        }

        @Override
        public String toString() {
            return responseType;
        }

    }

    /**
     * Models the status of a job. This enum may move inside of the EssJob object in the future.
     */
    public enum Status {

        /**
         * Represented in the Essbase REST API with code 100.
         */
        IN_PROGRESS(100),
        COMPLETED(200),
        COMPLETED_WITH_WARNINGS(300),
        FAILED(400);

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

    }
}