package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import com.appliedolap.essbase.util.Utils;
import com.appliedolap.essbase.util.WrapperUtil;
import okhttp3.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Represents an Essbase application object. An application is a container for one or more {@link EssCubeImpl} cubes.
 */
public class EssApplicationImpl extends AbstractEssObject implements EssApplication {

    private static final Logger logger = LoggerFactory.getLogger(EssApplicationImpl.class);

    private final EssServer server;

    private final Application application;

    public EssApplicationImpl(ApiContext api, EssServer server, Application application) {
        super(api);
        this.server = server;
        this.application = application;
        logger.info("Application {}, status: {}, created: {}, modified: {}", getName(), getStatus(), getCreated(), getModified());
    }

    @Override
    public String getName() {
        return application.getName();
    }

    @Override
    public Type getType() {
        return Type.APPLICATION;
    }

    @Override
    public EssServer getServer() {
        return server;
    }

    @Override
    public Instant getCreated() {
        return Utils.instant(application.getCreationTime());
    }

    @Override
    public Instant getModified() {
        return Utils.instant(application.getModifiedTime());
    }

    @Override
    public List<EssCube> getCubes() {
        try {
            CubeList cubeList = api.applicationsApi().applicationsGetCubes(application.getName(), null, null);
            List<EssCube> cubes = new ArrayList<>();
            if (cubeList.getItems() != null) {
                for (Cube cube : cubeList.getItems()) {
                    EssCube essCube = new EssCubeImpl(api, this, cube);
                    cubes.add(essCube);
                }
            }
            return Collections.unmodifiableList(cubes);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssCube getCube(String cubeName) {
        return Utils.getWithName(getCubes(), cubeName, EssObject.Type.CUBE);
    }

    public ApiContext getApi() {
        return api;
    }

    @Override
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

    @Override
    public void delete() {
        try {
            logger.info("Deleting application {}", application.getName());
            api.applicationsApi().applicationsDeleteApplication(application.getName());
            logger.info("Deleted application {}", application.getName());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
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

    @Override
    public List<EssApplicationConfiguration> getConfigurations() {
        try {
            ApplicationConfigList applicationConfigList = api.getApplicationConfigurationApi().applicationConfigurationGetConfigurations(getName());
            List<EssApplicationConfiguration> configurations = new ArrayList<>();
            for (ApplicationConfigEntry entry : wrap(applicationConfigList.getItems())) {
                EssApplicationConfiguration keyValue = new EssApplicationConfigurationImpl(this, entry.getKey(), entry.getValue());
                configurations.add(keyValue);
            }
            return Collections.unmodifiableList(configurations);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssJob> getJobs() {
        try {
            logger.info("Fetching list of jobs from application {}", application.getName());
            JobRecordPaginatedResultWrapper results = api.getJobsApi().jobsGetAllJobRecords(null, application.getName(), null, null, null, null);
            List<EssJob> jobs = new ArrayList<>();
            if (results.getItems() != null) {
                for (JobRecordBean jobRecordBean : results.getItems()) {
                    EssJob job = new EssJobImpl(api, getServer(), jobRecordBean);
                    jobs.add(job);
                }
            }
            return Collections.unmodifiableList(jobs);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssApplicationVariable> getVariables() {
        try {
            VariableList variables = api.getVariablesApi().variablesListAppVariables(getName());
            List<EssApplicationVariable> applicationVariables = new ArrayList<>();
            for (Variable variable : wrap(variables.getItems())) {
                EssApplicationVariable applicationVariable = new EssApplicationVariableImpl(api, this, variable);
                applicationVariables.add(applicationVariable);
            }
            return Collections.unmodifiableList(applicationVariables);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    @Override
    public Status getStatus() {
        return Status.parse(application.getStatus());
    }

    @Override
    public void stop() {
        // in practice, it seems that 'stop' also works or the input parameter is simply not case-sensitive
        WrapperUtil.wrap(() -> api.getApplicationsApi().applicationsPerformOperation(getName(), "Stop"));
    }

    @Override
    public void start() {
        // in practice, it seems that 'start' also works or the input parameter is simply not case-sensitive
        WrapperUtil.wrap(() -> api.getApplicationsApi().applicationsPerformOperation(getName(), "Start"));
    }

    @Override
    public void downloadLatestLog(OutputStream outputStream) {
        try {
            Call call = api.getApplicationLogsApi().applicationLogsDownloadLatestLogFileCall(getName(), new GenericApiCallback());
            outputStream.write(GenericDownload.downloadBytes(call.execute()));
        } catch (ApiException | IOException a) {
            throw new EssApiException(a);
        }
    }

    @Override
    public void downloadAllLogsAsZip(OutputStream outputStream) {
        try {
            Call call = api.getApplicationLogsApi().applicationLogsDownloadAllLogFilesCall(getName(), new GenericApiCallback());
            outputStream.write(GenericDownload.downloadBytes(call.execute()));
        } catch (ApiException | IOException a) {
            throw new EssApiException(a);
        }
    }

}