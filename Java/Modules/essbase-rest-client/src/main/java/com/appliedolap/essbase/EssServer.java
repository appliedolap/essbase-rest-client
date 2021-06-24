package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiCallback;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.*;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.ApiClientFactory;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EssServer {

    private static final Logger logger = LoggerFactory.getLogger(EssServer.class);

    private ApiClient client;

    private final ApplicationsApi applicationsApi;

    private final AboutEssbaseApi aboutEssbaseApi;

    private final JobsApi jobsApi;

    private final ServerVariablesApi serverVariablesApi;

    private final TemplatesAndUtilitiesApi templatesAndUtilitiesApi;

    private final FilesApi filesApi;

    public EssServer(String server, String username, String password) {
        ApiClientFactory clientFactory = new ApiClientFactory(server, username, password);
        client = clientFactory.create();

        this.applicationsApi = new ApplicationsApi(client);
        this.aboutEssbaseApi = new AboutEssbaseApi(client);
        this.jobsApi = new JobsApi(client);
        this.serverVariablesApi = new ServerVariablesApi(client);
        this.templatesAndUtilitiesApi = new TemplatesAndUtilitiesApi(client);
        this.filesApi = new FilesApi(client);
    }

    public EssApplication getApplication(String applicationName) {
        Application application = new Application();
        application.setName(applicationName);
        return new EssApplication(client, this, application);
    }

    public List<EssApplication> getApplications() {
        try {
            ApplicationList applicationList = applicationsApi.applicationsGetApplications(null, null, null, null, null, null);
            List<EssApplication> applications = new ArrayList<>();
            for (Application application : applicationList.getItems()) {
                applications.add(new EssApplication(client, this, application));
            }
            return applications;
        } catch (ApiException e) {
            throw new RuntimeException("Couldn't get applications", e);
        }
    }

    public List<EssFile> getFiles() {
        try {
            CollectionResponse collectionResponse = filesApi.filesListRootFolders(null, false);
            List<Object> roots = collectionResponse.getItems();
            System.out.println("Received file count: " + roots.size());
            List<EssFile> files = new ArrayList<>();
            for (Object file : roots) {
                System.out.println("File: " + file);
                Map<String, String> fileMap = (Map) file;
                // name, fullPath, type, permissions (another map), links
                String name = fileMap.get("name");
                String fullPath = fileMap.get("fullPath");
                boolean isFolder = "folder".equals(fileMap.get("type"));
                EssFile essFile = null;
                if (isFolder) {
                    essFile = new EssFolder(name, fullPath, client);
                } else {
                    essFile = new EssFile(name, fullPath, client);
                }

                files.add(essFile);
            }
            return files;
        } catch (ApiException e) {
            throw new RuntimeException("Could not list files", e);
        }
    }

    public List<EssUtility> getUtilities() {
        try {
            ResourceList utilities = templatesAndUtilitiesApi.resourcesGetUtilities();
            List<EssUtility> essUtilities = new ArrayList<>();
            for (Resource resource : utilities.getItems()) {
                EssUtility utility = new EssUtility(client, resource);
                essUtilities.add(utility);
            }
            return essUtilities;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EssJob> getJobs() {
        try {
            JobRecordPaginatedResultWrapper jobs = jobsApi.jobsGetAllJobRecords(null, null, "job_ID:desc", 0L, 50L, false);
            List<EssJob> essJobs = new ArrayList<>();
            for (JobRecordBean jobRecordBean : jobs.getItems()) {
                EssJob essJob = new EssJob(client, this, jobRecordBean);
                essJobs.add(essJob);
            }
            return essJobs;
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    public List<EssVariable> getVariables() {
        try {
            VariableList variableList = serverVariablesApi.variablesListServerVariables();
            List<EssVariable> variables = new ArrayList<>();
            for (Variable variable : variableList.getItems()) {
                EssVariable essVariable = new EssVariable(client, variable);
                variables.add(essVariable);
            }
            return variables;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getAbout() {
        try {
            About about = aboutEssbaseApi.aboutGetAbout();
            Map<String, String> props = new LinkedHashMap<>();
            props.put("Name", about.getName());
            props.put("Description", about.getDescription());
            props.put("Version", about.getVersion());
            props.put("Build", about.getBuild());
            return props;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

//
//    An actual app create call:
//
//```
//{
//    "applicationName": "Test2",
//    "databaseName": "Test2",
//    "databaseType": "B",
//    "allowDuplicates": "false",
//    "enableScenario": "true"
//}
    public void createApplication(String applicationName, String databaseName) {
        CreateApplication createApplication = new CreateApplication();
        createApplication.setApplicationName(applicationName);
        createApplication.setDatabaseName(databaseName);
        createApplication.setDatabaseType("B");
        createApplication.setEnableScenario(false);
        createApplication.setAllowDuplicates(false);

        logger.info("Creating application");
        try {
            applicationsApi.applicationsCreateApplications(createApplication);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
        logger.info("Finished creating application");
    }

}