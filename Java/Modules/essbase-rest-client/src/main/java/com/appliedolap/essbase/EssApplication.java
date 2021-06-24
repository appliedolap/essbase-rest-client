package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.ApplicationConfigurationApi;
import com.appliedolap.essbase.client.api.ApplicationsApi;
import com.appliedolap.essbase.client.api.JobsApi;
import com.appliedolap.essbase.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EssApplication extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssApplication.class);

    private final ApplicationsApi applicationsApi;

    private final ApplicationConfigurationApi applicationConfigurationApi;

    private final JobsApi jobsApi;

    private final EssServer server;

    private final Application application;

    public EssApplication(ApiClient client, EssServer server, Application application) {
        super(client);
        this.applicationsApi = new ApplicationsApi(client);
        this.applicationConfigurationApi = new ApplicationConfigurationApi(client);
        this.jobsApi = new JobsApi(client);
        this.server = server;
        this.application = application;
    }

    @Override
    public String getName() {
        return application.getName();
    }

    public EssServer getServer() {
        return server;
    }

    public List<EssCube> getCubes() {
        try {
            CubeList cubeList = applicationsApi.applicationsGetCubes(application.getName(), null, null);
            List<EssCube> cubes = new ArrayList<>();
            for (Cube cube : cubeList.getItems()) {
                EssCube essCube = new EssCube(client, this, cube);
                cubes.add(essCube);
            }
            return cubes;
        } catch (ApiException e) {
            throw new RuntimeException("Couldn't get cubes", e);
        }
    }

    public EssCube getCube(String cubeName) {
        Cube cube = new Cube();
        cube.setName(cubeName);
        return new EssCube(client, this, cube);
    }

    public void copy(String copyName) {
        CopyRenameBean copy = new CopyRenameBean();
        copy.setFrom(application.getName());
        copy.setTo(copyName);
        try {
            logger.info("Copying application {} to application {}", application.getName(), copyName);
            applicationsApi.applicationsCopyApplication(copy);
            logger.info("Finished copy");
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        try {
            logger.info("Deleting application {}", application.getName());
            applicationsApi.applicationsDeleteApplication(application.getName());
            logger.info("Deleted application {}", application.getName());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void rename(String newName) {
        CopyRenameBean copy = new CopyRenameBean();
        copy.setFrom(getName());
        copy.setTo(newName);
        try {
            logger.info("Renaming {} to {}", getName(), newName);
            applicationsApi.applicationsRenameApplication(copy);
            logger.info("Renamed {} to {}", getName(), newName);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EssApplicationConfiguration> getConfigurations() {
        try {
            ApplicationConfigList applicationConfigList = applicationConfigurationApi.applicationConfigurationGetConfigurations(getName());
            List<EssApplicationConfiguration> configurations = new ArrayList<>();
            for (ApplicationConfigEntry entry : applicationConfigList.getItems()) {
                EssApplicationConfiguration keyValue = new EssApplicationConfiguration(entry.getKey(), entry.getValue());
                configurations.add(keyValue);
            }
            return configurations;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EssJob> getJobs() {
        try {
            JobRecordPaginatedResultWrapper results = jobsApi.jobsGetAllJobRecords(null, application.getName(), null, null, null, null);
            List<JobRecordBean> jobRecordBeans = results.getItems();
            List<EssJob> jobs = new ArrayList<>();
            for (JobRecordBean jobRecordBean : jobRecordBeans) {
                EssJob job = new EssJob(client, getServer(), jobRecordBean);
                jobs.add(job);
            }
            return jobs;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}