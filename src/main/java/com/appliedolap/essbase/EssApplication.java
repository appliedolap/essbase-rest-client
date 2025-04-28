package com.appliedolap.essbase;

import java.io.OutputStream;
import java.time.Instant;
import java.util.List;

public interface EssApplication extends EssObject {

    /**
     * Get the server that contains this application.
     *
     * @return the parent server for the application
     */
    EssServer getServer();

    /**
     * Gets the created time for this application.
     *
     * @return when the application was created
     */
    Instant getCreated();

    /**
     * Gets the modified time for this application.
     *
     * @return the modified time for this application
     */
    Instant getModified();

    /**
     * Gets the list of cubes in this application.
     *
     * @return the list of cubes for this application, or an empty list if there are none
     */
    List<EssCube> getCubes();

    /**
     * Gets a cube with the given name.
     *
     * @param cubeName the name of the cube
     * @return the cube
     */
    EssCube getCube(String cubeName);

    /**
     * Copies this application to a new application with the given name.
     *
     * @param copyName the name of the copy
     */
    void copy(String copyName);

    /**
     * Deletes this application.
     */
    void delete();

    /**
     * Renames this application.
     *
     * @param newName the new name for the application
     */
    void rename(String newName);

    /**
     * Gets the list of configuration for this application.
     *
     * @return the configurations
     */
    List<EssApplicationConfiguration> getConfigurations();

    /**
     * Get the list of jobs associated with this application. In the Essbase REST API there is no specific endpoint for
     * jobs related to an application, but the general jobs service can take an application name to filter on. So this
     * implementation serves as a convenience method to get the jobs specific to this application.
     *
     * @return list of jobs for this application, empty list if none
     */
    List<EssJob> getJobs();

    /**
     * Gets the list of variables specific to this application
     *
     * @return the application variables
     */
    List<EssApplicationVariable> getVariables();

    /**
     * Gets the status of this application. Known statuses are simply <code>STOPPED</code> and <code>STARTED</code>;
     * other statuses, if they exist, are not known at this time and will simply get converted to <code>UNKNOWN</code>.
     *
     * <p>Note that this status represents the status when this object was instantiated. In other words, if the status
     * of the app changes during the lifetime of this object, it won't be refreshed automatically.</p>
     *
     * @return the status of this application
     */
    Status getStatus();

    /**
     * Stops the application.
     */
    void stop();

    /**
     * Starts the application
     */
    void start();

    /**
     * Writes the latest application log to the given output stream.
     *
     * @param outputStream the output stream
     */
    void downloadLatestLog(OutputStream outputStream);

    /**
     * Writes all logs to the given output stream.
     *
     * @param outputStream the output stream
     */
    void downloadAllLogsAsZip(OutputStream outputStream);

    /**
     * An enumeration for the different application states.
     */
    enum Status {

        /**
         * Application is in the stopped state.
         */
        STOPPED,

        /**
         * Application is in the started state.
         */
        STARTED,

        /**
         * State is unknown, currently unknown whether there are any states beyond stopped and started (such as
         * starting) but this gives us some buffer against unknown states breaking the API.
         */
        UNKNOWN;

        /**
         * Creates a status by parsing the given text. Parsing is case-insensitive. If a status cannot be determined
         * then the <code>UNKNOWN</code> status will be returned.
         *
         * @param statusText the status text to parse.
         * @return a status for the text
         */
        public static Status parse(String statusText) {
            if (statusText == null) throw new IllegalArgumentException();
            for (Status status : EssApplication.Status.values()) {
                if (status.name().equalsIgnoreCase(statusText)) {
                    return status;
                }
            }
            return UNKNOWN;
        }

    }

}