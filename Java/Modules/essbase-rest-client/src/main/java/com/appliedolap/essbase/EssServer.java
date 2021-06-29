package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Server connection. This object is the general starting point for working with the Essbase REST API client library.
 * See the main constructor {@link EssServer#EssServer(String, String, String)} for information on instantiating.
 */
public class EssServer extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssServer.class);

    private final String server;

    /**
     * Creates a new server connection object. At present, the server path is assumed to be in the exact format such as:
     * <code>http://docker1:9000/essbase/rest/v1</code>. In other words, this class will currently not try to prepend or
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
        ApiClientFactory clientFactory = new ApiClientFactory(server, username, password);
        ApiClient client = clientFactory.create();
        return new ApiContext(client);
    }

    /**
     * Gets an application with the given name
     *
     * @param applicationName the application name
     * @return an application object for the application
     */
    public EssApplication getApplication(String applicationName) {
        Application application = new Application();
        application.setName(applicationName);
        return new EssApplication(api, this, application);
    }

    @Override
    public String getName() {
        return server;
    }

    /**
     * Fetch the list of applications available on the server for the currently connected user.
     *
     * @return a list of applications
     */
    public List<EssApplication> getApplications() {
        try {
            ApplicationList applicationList = api.getApplicationsApi().applicationsGetApplications(null, null, null, null, null, null);
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
            throw new RuntimeException(e);
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

    /**
     * Creates an application (and a database) with the given names. While we tend to historically think of Essbase as
     * employing the concept of an application containing one or more databases/cubes, most of the operations in the
     * REST API are centered around actions you do on a particular cube, and in cases like these where you are creating
     * a cube, there is no separate application creation step, it just gets created or re-used as the case may be.
     *
     * @param applicationName the application name
     * @param databaseName the database name
     */
    public void createApplication(String applicationName, String databaseName) {
        // the underlying JSON payload appears to need, at a minimum, the five values that are set here. The B parameter
        // is presumably the code for a BSO application. Untested if A is used for ASO
        CreateApplication createApplication = new CreateApplication();
        createApplication.setApplicationName(applicationName);
        createApplication.setDatabaseName(databaseName);
        createApplication.setDatabaseType("B");
        createApplication.setEnableScenario(false);
        createApplication.setAllowDuplicates(false);

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
        job.setJobtype(EssJob.Type.IMPORT_EXCEL.getParam());

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

//    public void getUrls() {
//        try {
//            Preference preference = api.getUrlsApi().uRLsGet();
//            System.out.println();
//        } catch (ApiException apiException) {
//            apiException.printStackTrace();
//        }
//    }

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

}