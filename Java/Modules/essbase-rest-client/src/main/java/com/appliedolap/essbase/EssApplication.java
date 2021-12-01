package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.Utils;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Represents an Essbase application object. An application is a container for one or more {@link EssCube} cubes.
 */
public class EssApplication extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssApplication.class);

    private final EssServer server;

    private final Application application;

    /**
     * An enumeration for the different application states.
     */
    public enum Status {

        /**
         * Application is in the stopped state.
         */
        STOPPED,

        /**
         * Application is in the started state.
         */
        STARTED,

        /**
         * State is unknown, currently unknown whether there are any states beyond stopped and started (such as starting)
         * but this gives us some buffer against unknown states breaking the API.
         */
        UNKNOWN;

        /**
         * Creates a status by parsing the given text. Parsing is case-insensitive. If a status cannot be determined then
         * the <code>UNKNOWN</code> status will be returned.
         *
         * @param statusText the status text to parse.
         * @return a status for the text
         */
        public static Status parse(String statusText) {
            if (statusText == null) throw new IllegalArgumentException();
            for (Status status : Status.values()) {
                if (status.name().equalsIgnoreCase(statusText)) {
                    return status;
                }
            }
            return UNKNOWN;
        }

    }

    EssApplication(ApiContext api, EssServer server, Application application) {
        super(api);
        this.server = server;
        this.application = application;
        logger.info("Application {}, status: {}, created: {}, modified: {}", getName(), getStatus(), getCreated(), getModified());
    }

    /**
     * Gets the name of the application.
     *
     * @return the name of this application
     */
    @Override
    public String getName() {
        return application.getName();
    }

    @Override
    public Type getType() {
        return Type.APPLICATION;
    }

    /**
     * Get the server that contains this application.
     *
     * @return the parent server for the application
     */
    public EssServer getServer() {
        return server;
    }

    /**
     * Gets the created time for this application.
     *
     * @return when the application was created
     */
    public Instant getCreated() {
        return Utils.instant(application.getCreationTime());
    }

    /**
     * Gets the modified time for this application.
     *
     * @return the modified time for this application
     */
    public Instant getModified() {
        return Utils.instant(application.getModifiedTime());
    }

    /**
     * Gets the list of cubes in this application.
     *
     * @return the list of cubes for this application, or an empty list if there are none
     */
    public List<EssCube> getCubes() {
        try {
            CubeList cubeList = api.applicationsApi().applicationsGetCubes(application.getName(), null, null);
            List<EssCube> cubes = new ArrayList<>();
            if (cubeList.getItems() != null) {
                for (Cube cube : cubeList.getItems()) {
                    EssCube essCube = new EssCube(api, this, cube);
                    cubes.add(essCube);
                }
            }
            return Collections.unmodifiableList(cubes);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Gets a cube with the given name.
     *
     * @param cubeName the name of the cube
     * @return the cube
     */
    public EssCube getCube(String cubeName) {
        return Utils.getWithName(getCubes(), cubeName, EssObject.Type.CUBE);
    }

    public ApiContext getApi() {
        return api;
    }

    /**
     * Copies this application to a new application with the given name.
     *
     * @param copyName the name of the copy
     */
    public void copy(String copyName) {
        CopyRenameBean copy = new CopyRenameBean();
        copy.setFrom(application.getName());
        copy.setTo(copyName);
        try {
            logger.info("Copying application {} to application {}", application.getName(), copyName);
            api.applicationsApi().applicationsCopyApplication(copy);
            logger.info("Finished copy");
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Deletes this application.
     */
    public void delete() {
        try {
            logger.info("Deleting application {}", application.getName());
            api.applicationsApi().applicationsDeleteApplication(application.getName());
            logger.info("Deleted application {}", application.getName());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Renames this application.
     *
     * @param newName the new name for the application
     */
    public void rename(String newName) {
        CopyRenameBean copy = new CopyRenameBean();
        copy.setFrom(getName());
        copy.setTo(newName);
        try {
            logger.info("Renaming {} to {}", getName(), newName);
            api.applicationsApi().applicationsRenameApplication(copy);
            logger.info("Renamed {} to {}", getName(), newName);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Gets the list of configuration for this application.
     *
     * @return the configurations
     */
    public List<EssApplicationConfiguration> getConfigurations() {
        try {
            ApplicationConfigList applicationConfigList = api.getApplicationConfigurationApi().applicationConfigurationGetConfigurations(getName());
            List<EssApplicationConfiguration> configurations = new ArrayList<>();
            for (ApplicationConfigEntry entry : wrap(applicationConfigList.getItems())) {
                EssApplicationConfiguration keyValue = new EssApplicationConfiguration(this, entry.getKey(), entry.getValue());
                configurations.add(keyValue);
            }
            return Collections.unmodifiableList(configurations);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Get the list of jobs associated with this application. In the Essbase REST API there is no specific endpoint for
     * jobs related to an application, but the general jobs service can take an application name to filter on. So this
     * implementation serves as a convenience method to get the jobs specific to this application.
     *
     * @return list of jobs for this application, empty list if none
     */
    public List<EssJob> getJobs() {
        try {
            logger.info("Fetching list of jobs from application {}", application.getName());
            JobRecordPaginatedResultWrapper results = api.getJobsApi().jobsGetAllJobRecords(null, application.getName(), null, null, null, null);
            List<EssJob> jobs = new ArrayList<>();
            if (results.getItems() != null) {
                for (JobRecordBean jobRecordBean : results.getItems()) {
                    EssJob job = new EssJob(api, getServer(), jobRecordBean);
                    jobs.add(job);
                }
            }
            return Collections.unmodifiableList(jobs);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Gets the list of variables specific to this application
     *
     * @return the application variables
     */
    public List<EssVariable> getVariables() {
        try {
            VariableList variables = api.getVariablesApi().variablesListAppVariables(getName());
            List<EssVariable> applicationVariables = new ArrayList<>();
            for (Variable variable : wrap(variables.getItems())) {
                EssApplicationVariable applicationVariable = new EssApplicationVariable(api, this, variable);
                applicationVariables.add(applicationVariable);
            }
            return Collections.unmodifiableList(applicationVariables);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * Gets the status of this application. Known statuses are simply <code>STOPPED</code> and <code>STARTED</code>;
     * other statuses, if they exist, are not known at this time and will simply get converted to <code>UNKNOWN</code>.
     *
     * <p>Note that this status represents the status when this object was instantiated. In other words, if the status
     * of the app changes during the lifetime of this object, it won't be refreshed automatically.</p>
     * @return the status of this application
     */
    public Status getStatus() {
        return Status.parse(application.getStatus());
    }

    /**
     * Stops the application.
     */
    public void stop() {
        // in practice, it seems that 'stop' also works or the input parameter is simply not case-sensitive
        WrapperUtil.wrap(() -> api.getApplicationsApi().applicationsPerformOperation(getName(), "Stop"));
    }

    /**
     * Starts the application
     */
    public void start() {
        // in practice, it seems that 'start' also works or the input parameter is simply not case-sensitive
        WrapperUtil.wrap(() -> api.getApplicationsApi().applicationsPerformOperation(getName(), "Start"));
    }

}