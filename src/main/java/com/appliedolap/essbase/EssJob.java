package com.appliedolap.essbase;

import java.util.concurrent.TimeUnit;

public interface EssJob extends EssObject {

    /**
     * Gets the ID of the job.
     *
     * @return the numeric ID of this job
     */
    Long getJobID();

    /**
     * Check if the job execution was successful.
     *
     * @return true if it was (as based on status code), false otherwise
     */
    boolean isSuccessful();

    /**
     * Implemented for conformance to {@link AbstractEssObject}; the name is simply the job ID.
     *
     * @return the name of this job
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the duration of this job, in milliseconds. This method may return 0 while the job is executing and only have
     * a useful value upon completion.
     *
     * @return the duration of the job
     */
    long getDuration();

    /**
     * Gets the owning server for this job.
     *
     * @return the server for this job
     */
    EssServer getServer();

    /**
     * Gets the type of the job.
     *
     * @return the job type
     */
    JobType getJobType();

    /**
     * Re-run the job (using the Essbase REST API method to re-run a job).
     */
    void rerun();

    /**
     * Gets the status of this job.
     *
     * @return the job status
     */
    Status getStatus();

    /**
     * Returns the error message for the job, if any. This API will likely change as the jobs API is filled out, since
     * this status is null for successful jobs.
     *
     * @return the error message, if any
     */
    String getErrorMessage();

    /**
     * A textual description of the job.
     *
     * @return a slightly longer description of the job
     */
    String getDescription();

    /**
     * Loops while re-polling the job for its static to change to one that is complete (successfully or otherwise). The
     * default wait interval is 5 seconds, although a custom interval can be specified by using
     * {@link #waitForCompletion(long, TimeUnit)}.
     *
     * @return the updated job object, once this job is complete
     * @throws InterruptedException if interruption is thrown
     */
    EssJob waitForCompletion() throws InterruptedException;

    /**
     * Waits given amount of time for job to complete. See {@link #waitForCompletion()} for more details.
     *
     * @param duration the duration
     * @param timeUnit the time unit of the duration
     * @return the updated job object, once this job is complete
     * @throws InterruptedException if interruption is thrown
     */
    EssJob waitForCompletion(long duration, TimeUnit timeUnit) throws InterruptedException;

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
         * request format 'leaking' but it is what it is. Having this constant be accessible allows us to reference
         * these consistently when putting together a job request.
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
            for (JobType type : EssJob.JobType.values()) {
                if (type.getResponseType().equals(textType)) {
                    return type;
                }
            }
            return EssJob.JobType.UNKNOWN;
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
            for (Status jobStatus : EssJob.Status.values()) {
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