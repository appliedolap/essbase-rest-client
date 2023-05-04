package com.appliedolap.essbase;

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
 * See the main constructor {@link EssServer#EssServer(String, String, String)} for information on instantiating.
 */
public class EssServer extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssServer.class);

    /**
     * For now, we're setting a generous upper limit on the number of applications that can be returned in a listing. We
     * may want to revisit this in the future. This library is currently designed to hide pagination details so that they
     * don't leak into the abstractions provided in this library, but we may need to rethink this in the future.
     */
    public static final int MAX_APPLICATIONS = 1000;

    public static final String DEFAULT_REST_API_PATH = "/rest/v1";

    private final String server;

    /**
     * Creates a new server connection object. At present, the server path is assumed to be in the exact format such as:
     * <code>http://docker1:9000/essbase</code>. In other words, this class will currently not try to prepend or
     * append any information such as the protocol, port, or REST path. Note that if using HTTPS without a valid cert,
     * you may encounter PKIX errors. Note: SSL verification is currently turned off.
     *
     * @param server the server REST API path
     * @param username the username
     * @param password the password
     */
    public EssServer(String server, String username, String password) {
        super(createApiContext(server, username, password));
        this.server = server;
    }

    private static ApiContext createApiContext(String server, String username, String password) {
        ApiClientFactory clientFactory = new ApiClientFactory(server + DEFAULT_REST_API_PATH, username, password);
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

    /**
     * Fetch the list of applications available on the server for the currently connected user. The number of returned
     * applications is limited to {@value MAX_APPLICATIONS} (the value of {@link #MAX_APPLICATIONS}).
     *
     * @return a list of applications
     */
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

    /**
     * Gets an application with the given name
     *
     * @param applicationName the application name
     * @return an application object for the application
     */
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

    /**
     * Gets the list of files available through the files API. The returned files may include folders.
     *
     * @return the list of files on this server
     */
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

    public List<EssSession> getSessions() {
        return WrapperUtil.wrapList(() -> api.getSessionsApi().sessionsGetAllActiveSessions(null, null, null), sessionAttributes -> new EssSession(api, sessionAttributes));
    }

    /**
     * Kill all sessions on the server.
     *
     * @param logoff true to also log them off
     */
    public void killSessions(boolean logoff) {
        throw new UnsupportedOperationException();
    }

    /**
     * Kill all sessions on the server for the given user.
     *
     * @param userId the user ID
     * @param logoff true to also log them off
     */
    public void killSessions(String userId, boolean logoff) {
        throw new UnsupportedOperationException();
    }

//    public EssFile getFile(String fileFullPath) {
//        return null;
//    }

    /**
     * Gets the home path of the currently connected user. The value of the home path is returned by a REST API call
     * (curiously, one that returns plaintext instead of JSON but whatever). The value is ostensibly <code>/users/</code>
     * followed by the name of the connected user (e.g. <code>/users/admin</code>).
     *
     * @return a folder object for the user's home path
     */
    public EssFolder getHomePath() {
        try {
            String homepath = api.getFilesApi().filesGetUserHomePath();
            return new EssFolder(api, this, homepath, homepath);
        } catch (ApiException apiException) {
            logger.error("Unable to get home path: {}", apiException.getMessage());
            throw new EssApiException(apiException);
        }
    }

    /**
     * Gets the list of utilities on this server.
     *
     * @return the server utilities
     */
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

    /**
     * Gets the list of jobs on this server.
     *
     * @return the list of jobs
     */
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

    /**
     * Gets a list of groups on this server
     *
     * @return the list of groups
     */
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

    /**
     * Gets server-scoped variables.
     *
     * @return the server-wide variables
     */
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

    /**
     * Creates a new server-wide variable with the given name and value.
     *
     * @param name the name of the variable
     * @param value the value of the variable
     */
    public void createVariable(String name, String value) {
        Variable variable = new Variable();
        variable.setName(name);
        variable.setValue(value);
        WrapperUtil.wrap(() -> api.getServerVariablesApi().variablesCreateServerVariable(variable));
    }

    /**
     * Gets the "about" information for this server.
     *
     * @return the server about info
     */
    public About getAbout() {
        return WrapperUtil.wrapFunc(() -> api.getAboutEssbaseApi().aboutGetAbout(), About::new);
    }

    public AboutInstance getAboutInstance() {
        return WrapperUtil.wrapFunc(() -> api.getAboutEssbaseApi().getInstanceDetails(), AboutInstance::new);
    }

    /**
     * Creates an application (and a database) with the given names. While we tend to historically think of Essbase as
     * employing the concept of an application containing one or more databases/cubes, most of the operations in the
     * REST API are centered around actions you do on a particular cube, and in cases like these where you are creating
     * a cube, there is no separate application creation step, it just gets created or re-used as the case may be.
     *
     * <p>The default database creation options will be BSO cube with scenarios and duplicates turned off. For more
     * granular control of the created database type, use {@link #createApplication(String, String, DatabaseCreateOptions)}.
     *
     * @param applicationName the application name
     * @param databaseName the database name
     */
    public void createApplication(String applicationName, String databaseName) {
        createApplication(applicationName, databaseName, new DatabaseCreateOptions());
    }

    /**
     * Create an application/database with the given name and options.
     *
     * @param applicationName the name of the application
     * @param databaseName the name of the database/cube
     * @param databaseCreateOptions the database creation options
     */
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

    /**
     * Creates or updates an application from an uploaded workbook.
     *
     * @param application the application
     * @param database the cube/database
     * @param file the XLSX file
     */
    public void createApplicationFromWorkbook(String application, String database, EssFile file) {
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
            api.getJobsApi().jobsExecuteJob(job);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    /**
     * Returns the list of URLs known to this server. Generally speaking this seems to be the URL for the Jet UI, REST API,
     * XMLA provider, and some others.
     *
     * @return list of URLs from the corresponding API
     */
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

    /**
     * Get the list of global data sources defined on the server.
     *
     * @return list of global data sources
     */
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

    /**
     * Get the data source with the given name. Will throw {@link com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException}
     * if there is no data source with that name.
     *
     * @param dataSourceName the data source name
     * @return the data source with that name
     */
    public EssDataSource getDataSource(String dataSourceName) {
        return Utils.getWithName(getDataSources(), dataSourceName, Type.DATASOURCE);
    }

    /**
     * Calls the global data source endpoint to execute a query against a data source with the given parameters.
     *
     * @param query the query
     * @param includeHeaders whether to include headers in the result
     * @param delimiter the delimiter (currently only space and tab are supported by the server, you can use constants in {@link EssDataSource} for convenience)
     * @param params the parameters, if any. If none, supply an empty map
     * @param outputStream the output stream to write results to
     */
    // TODO: get the metadataOnly flag into the OpenAPI call; it exists but is not getting generated. It causes you to get JDBC headers only but with no data
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

    /**
     * A cube type, despite the name.
     */
    public enum ApplicationType {

        /**
         * ASO/Aggregate Storage Option
         */
        ASO("A"),

        /**
         * BSO/Block Storage Option (including hybrid)
         */
        BSO("B");

        private final String code;

        ApplicationType(String code) {
            this.code = code;
        }

        /**
         * This is private because we don't want this shitty abbreviation leaking into the public API.
         * Note: this might not be the case after all. Not sure, leaving in for now
         * @return the code for the database type as used in the create request
         */
        private String getCode() {
            return code;
        }

    }

}