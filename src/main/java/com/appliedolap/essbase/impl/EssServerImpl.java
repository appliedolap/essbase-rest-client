package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.Utils;
import com.appliedolap.essbase.util.WrapperUtil;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Server connection. This object is the general starting point for working with the Essbase REST API client library.
 * See the main constructor {@link EssServerImpl#EssServerImpl(String, String, String)} for information on instantiating.
 */
public class EssServerImpl extends AbstractEssObject implements EssServer {

    private static final Logger logger = LoggerFactory.getLogger(EssServerImpl.class);

    private final String server;

    /**
     * Creates a new server connection object. At present, the server path is assumed to be in the exact format such as:
     * <code><a href="http://docker1:9000/essbase">...</a></code>. In other words, this class will currently not try to prepend or
     * append any information such as the protocol, port, or REST path. Note that if using HTTPS without a valid cert,
     * you may encounter PKIX errors. Note: SSL verification is currently turned off.
     *
     * @param server the server REST API path
     * @param username the username
     * @param password the password
     */
    public EssServerImpl(String server, String username, String password) {
        super(createApiContext(server, username, password, false));
        this.server = server;
    }

    public EssServerImpl(EssServerConnectionDetailsImpl connectionDetails) {
        super(createApiContext(connectionDetails.getServer(), connectionDetails.getUsername(), connectionDetails.getPassword(), connectionDetails.isStateless()));
        this.server = connectionDetails.getServer();
    }

    private static ApiContext createApiContext(String server, String username, String password, boolean stateless) {
        ApiClientFactory clientFactory = new ApiClientFactory(server + DEFAULT_REST_API_PATH, username, password, stateless);
        ApiClient client = clientFactory.create();
        return new ApiContext(client);
    }

    private static ApiContext createApiContext(EssServerConnectionDetailsImpl connectionDetails) {
        ApiClientFactory clientFactory = new ApiClientFactory(connectionDetails.getServer() + DEFAULT_REST_API_PATH, connectionDetails.getUsername(), connectionDetails.getPassword(), connectionDetails.isStateless());
        ApiClient client = clientFactory.create();
        return new ApiContext(client);
    }

    @Override
    public String getName() {
        return server;
    }

    @Override
    public Type getType() {
        return Type.SERVER;
    }

    @Override
    public List<EssApplication> getApplications() {
        try {
            ApplicationList applicationList = api.getApplicationsApi().applicationsGetApplications(null, null, MAX_APPLICATIONS, null, null, null);
            List<EssApplication> applications = new ArrayList<>();
            for (Application application : wrap(applicationList.getItems())) {
                applications.add(new EssApplication(api, this, application));
            }
            return Collections.unmodifiableList(applications);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssApplication getApplication(String applicationName) {
        try {
            // calls the same list method as the getApplications method but provides a filter on the app name so that
            // only one is returned. In the future we could potentially go straight to the /applications/{applicationName}
            // endpoint, but that returns much more information, so doing it this way gives us consistency with the data
            // that is returned from the other method
            ApplicationList applicationList = api.getApplicationsApi().applicationsGetApplications(applicationName, null, null, null, null, null);
            if (Utils.isNotEmpty(applicationList.getItems())) {
                return new EssApplication(api, this, applicationList.getItems().get(0));
            } else {
                throw new NoSuchEssbaseObjectException(applicationName, Type.APPLICATION);
            }
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssFile> getFiles() {
        try {
            CollectionResponse collectionResponse = api.getFilesApi().filesListRootFolders(null, false);
            List<EssFile> files = new ArrayList<>();
            for (Object file : wrap(collectionResponse.getItems())) {
                Map<String, String> fileMap = (Map) file;
                // name, fullPath, type, permissions (another map), links
                String name = fileMap.get("name");
                String fullPath = fileMap.get("fullPath");
                boolean isFolder = "folder".equals(fileMap.get("type"));
                EssFile essFile = isFolder ? new EssFolder(api, this, name, fullPath) : new EssFile(api, this, name, fullPath);
                files.add(essFile);
            }
            return Collections.unmodifiableList(files);
        } catch (ApiException e) {
            throw new RuntimeException("Could not list files", e);
        }
    }

    @Override
    public EssFile getFile(String path, String filename) {
        try {
            FileCollectionResponse collectionResponse = api.getFilesApi().filesListFiles(path, null, null, null, null, null, null, null, null);
            for (FileBean fileBean : collectionResponse.getItems()) {
                if (fileBean.getName().equals(filename)) {
                    return new EssFile(api, this, filename, fileBean.getFullPath());
                }
            }
            return null;
        } catch (ApiException e) {
            throw new RuntimeException("Could not list files", e);
        }
    }

    @Override
    public List<EssSession> getSessions() {
        return WrapperUtil.wrapList(() -> api.getSessionsApi().sessionsGetAllActiveSessions(null, null, null), sessionAttributes -> new EssSession(api, sessionAttributes));
    }

    @Override
    public void killSessions(boolean logoff) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void killSessions(String userId, boolean logoff) {
        throw new UnsupportedOperationException();
    }

//    public EssFile getFile(String fileFullPath) {
//        return null;
//    }

    @Override
    public EssFolder getHomePath() {
        try {
            String homepath = api.getFilesApi().filesGetUserHomePath();
            return new EssFolder(api, this, homepath, homepath);
        } catch (ApiException apiException) {
            logger.error("Unable to get home path: {}", apiException.getMessage());
            throw new EssApiException(apiException);
        }
    }

    @Override
    public List<EssUtility> getUtilities() {
        try {
            ResourceList utilities = api.getTemplatesAndUtilitiesApi().resourcesGetUtilities();
            List<EssUtility> essUtilities = new ArrayList<>();
            for (Resource resource : wrap(utilities.getItems())) {
                EssUtility utility = new EssUtility(api, resource);
                essUtilities.add(utility);
            }
            return Collections.unmodifiableList(essUtilities);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EssJob> getJobs() {
        try {
            JobRecordPaginatedResultWrapper jobs = api.getJobsApi().jobsGetAllJobRecords(null, null, "job_ID:desc", 0L, 50L, false);
            List<EssJob> essJobs = new ArrayList<>();
            for (JobRecordBean jobRecordBean : wrap(jobs.getItems())) {
                EssJob essJob = new EssJob(api, this, jobRecordBean);
                essJobs.add(essJob);
            }
            return Collections.unmodifiableList(essJobs);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EssGroup> getGroups() {
        try {
            Groups groups = api.getGroupsApi().groupsSearch(null, -1, "all");
            List<EssGroup> essGroups = new ArrayList<>();
            for (GroupBean group : wrap(groups.getItems())) {
                EssGroup essGroup = new EssGroup(api, this, group);
                essGroups.add(essGroup);
            }
            return Collections.unmodifiableList(essGroups);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssVariable> getVariables() {
        try {
            VariableList variableList = api.getServerVariablesApi().variablesListServerVariables();
            List<EssVariable> variables = new ArrayList<>();
            for (Variable variable : wrap(variableList.getItems())) {
                EssVariable essVariable = new EssVariable(api, variable);
                variables.add(essVariable);
            }
            return Collections.unmodifiableList(variables);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void createVariable(String name, String value) {
        Variable variable = new Variable();
        variable.setName(name);
        variable.setValue(value);
        WrapperUtil.wrap(() -> api.getServerVariablesApi().variablesCreateServerVariable(variable));
    }

    @Override
    public About getAbout() {
        return WrapperUtil.wrapFunc(() -> api.getAboutEssbaseApi().aboutGetAbout(), About::new);
    }

    @Override
    public AboutInstance getAboutInstance() {
        return WrapperUtil.wrapFunc(() -> api.getAboutEssbaseApi().getInstanceDetails(), AboutInstance::new);
    }

    @Override
    public void createApplication(String applicationName, String databaseName) {
        createApplication(applicationName, databaseName, new DatabaseCreateOptions());
    }

    @Override
    public void createApplication(String applicationName, String databaseName, DatabaseCreateOptions databaseCreateOptions) {
        // the underlying JSON payload appears to need, at a minimum, the five values that are set here. The B parameter
        // is presumably the code for a BSO application. Untested if A is used for ASO
        CreateApplication createApplication = new CreateApplication();
        createApplication.setApplicationName(applicationName);
        createApplication.setDatabaseName(databaseName);
        createApplication.setDatabaseType(databaseCreateOptions.getType().name());
        createApplication.setEnableScenario(databaseCreateOptions.isEnableScenarios());
        createApplication.setAllowDuplicates(databaseCreateOptions.isAllowDuplicates());

        logger.info("Creating application");
        try {
            api.getApplicationsApi().applicationsCreateApplications(createApplication);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
        logger.info("Finished creating application");
    }

    @Override
    public EssJob createApplicationFromWorkbook(String application, String database, EssFile file) {
        logger.info("Submitting job to build/update {}.{} from {}", application, database, file);
        JobsInputBean job = new JobsInputBean();
        job.setApplication(application);
        job.setDb(database);
        job.setJobtype(EssJob.JobType.IMPORT_EXCEL.getParam());

        ParametersBean params = new ParametersBean();

        params.loaddata("false");
        params.overwrite("true");
        params.deleteExcelOnSuccess("false");
        params.setCatalogExcelPath(file.getPath());
        params.importExcelFileName(file.getName());
        params.recreateApplication("true");
        params.createFiles("true");
        job.setParameters(params);

        try {
            JobRecordBean jobRecordBean = api.getJobsApi().jobsExecuteJob(job);
            return new EssJob(api, this, jobRecordBean);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssURL> getURLs() {
        try {
            return api.getUrlsApi().uRLsGet().getItems()
                    .stream()
                    .map(url -> new EssURL(api, this, url))
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssDataSource> getDataSources() {
        try {
            return api.getGlobalDataSourcesApi().globalDatasourcesGetDatasources(0, 1000).getItems()
                    .stream()
                    .map(ds -> new EssDataSource(api, this, ds))
                    .collect(Collectors.toList());
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssDataSource getDataSource(String dataSourceName) {
        return Utils.getWithName(getDataSources(), dataSourceName, Type.DATASOURCE);
    }

    // TODO: get the metadataOnly flag into the OpenAPI call; it exists but is not getting generated. It causes you to get JDBC headers only but with no data
    @Override
    public void streamDataSource(String query, boolean includeHeaders, String delimiter, Map<String, Object> params, OutputStream outputStream) {
        try {
            DatasourceQueryInfo datasourceQueryInfo = new DatasourceQueryInfo();
            datasourceQueryInfo.setQuery(query);
            datasourceQueryInfo.setDelimiter(delimiter);
            datasourceQueryInfo.setParams(params);
            GenericApiCallback callback = new GenericApiCallback();
            Response response = api.getGlobalDataSourcesApi().globalDatasourcesGetDataStreamCall(includeHeaders, datasourceQueryInfo, callback).execute();
            IOUtils.copy(response.body().byteStream(), outputStream);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * The about information of this server.
     */
    public static class About {

        private final com.appliedolap.essbase.client.model.About about;

        private About(com.appliedolap.essbase.client.model.About about) {
            this.about = about;
        }

        public String getName() {
            return about.getName();
        }

        public String getDescription() {
            return about.getDescription();
        }

        public String getVersion() {
            return about.getVersion();
        }

        public String getBuild() {
            return about.getBuild();
        }

    }

    public static class AboutInstance {

        private final com.appliedolap.essbase.client.model.AboutInstance aboutInstance;

        public AboutInstance(com.appliedolap.essbase.client.model.AboutInstance aboutInstance) {
            this.aboutInstance = aboutInstance;
        }

        public Boolean getProvisioningSupported() {
            return aboutInstance.getProvisioningSupported();
        }

        public Boolean getResetPasswordSupported() {
            return aboutInstance.getResetPasswordSupported();
        }

        public Boolean getEasInstalled() {
            return aboutInstance.getEasInstalled();
        }
    }

    /**
     * Database creation options.
     */
    public static class DatabaseCreateOptions {

        private ApplicationType type = ApplicationType.BSO;

        private boolean enableScenarios;

        private boolean allowDuplicates;

        public ApplicationType getType() {
            return type;
        }

        public void setType(ApplicationType type) {
            this.type = type;
        }

        public boolean isEnableScenarios() {
            return enableScenarios;
        }

        public void setEnableScenarios(boolean enableScenarios) {
            this.enableScenarios = enableScenarios;
        }

        public boolean isAllowDuplicates() {
            return allowDuplicates;
        }

        public void setAllowDuplicates(boolean allowDuplicates) {
            this.allowDuplicates = allowDuplicates;
        }
    }

}