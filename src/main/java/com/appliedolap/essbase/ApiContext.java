package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.api.*;

/**
 * Serves as a holder for all the various API interfaces. We can pass this around as needed without having to
 * clutter up individual classes with having to instantiate and track all of these.
 */
public class ApiContext {

    private final ApplicationsApi applicationsApi;

    private final ApplicationConfigurationApi applicationConfigurationApi;

    private final AboutEssbaseApi aboutEssbaseApi;

    private final JobsApi jobsApi;

    private final ServerVariablesApi serverVariablesApi;

    private final TemplatesAndUtilitiesApi templatesAndUtilitiesApi;

    private final FilesApi filesApi;

    private final FilesApi2 filesApi2;

    private final DrillThroughReportsApi drillThroughReportsApi;

    private final ScriptsApi scriptsApi;

    private final SessionsApi sessionsApi;

    private final VariablesApi variablesApi;

    private final OutlineViewerApi outlineViewerApi;

    private final DimensionsApi dimensionsApi;

    private final ScenariosApi scenariosApi;

    private final UrlsApi urlsApi;

    private final BatchOutlineEditingApi batchOutlineEditingApi;

    private final GroupsApi groupsApi;

    private final ExecuteMdxApi executeMdxApi;

    private final GlobalDataSourcesApi globalDataSourcesApi;

    private final LocksApi locksApi;

    private final ApplicationLogsApi logsApi;

    public ApiContext(ApiClient client) {
        this.applicationsApi = new ApplicationsApi(client);
        this.applicationConfigurationApi = new ApplicationConfigurationApi(client);
        this.aboutEssbaseApi = new AboutEssbaseApi(client);
        this.jobsApi = new JobsApi(client);
        this.serverVariablesApi = new ServerVariablesApi(client);
        this.templatesAndUtilitiesApi = new TemplatesAndUtilitiesApi(client);
        this.filesApi = new FilesApi(client);
        this.filesApi2 = new FilesApi2(client);
        this.drillThroughReportsApi = new DrillThroughReportsApi(client);
        this.scriptsApi = new ScriptsApi(client);
        this.sessionsApi = new SessionsApi(client);
        this.variablesApi = new VariablesApi(client);
        this.outlineViewerApi = new OutlineViewerApi(client);
        this.dimensionsApi = new DimensionsApi(client);
        this.scenariosApi = new ScenariosApi(client);
        this.urlsApi = new UrlsApi(client);
        this.batchOutlineEditingApi = new BatchOutlineEditingApi(client);
        this.groupsApi = new GroupsApi(client);
        this.executeMdxApi = new ExecuteMdxApi(client);
        this.globalDataSourcesApi = new GlobalDataSourcesApi(client);
        this.locksApi = new LocksApi(client);
        this.logsApi = new ApplicationLogsApi(client);
    }

    public ApplicationsApi applicationsApi() {
        return applicationsApi;
    }

    public ApplicationConfigurationApi getApplicationConfigurationApi() {
        return applicationConfigurationApi;
    }

    public ApplicationsApi getApplicationsApi() {
        return applicationsApi;
    }

    public AboutEssbaseApi getAboutEssbaseApi() {
        return aboutEssbaseApi;
    }

    public JobsApi getJobsApi() {
        return jobsApi;
    }

    public ServerVariablesApi getServerVariablesApi() {
        return serverVariablesApi;
    }

    public TemplatesAndUtilitiesApi getTemplatesAndUtilitiesApi() {
        return templatesAndUtilitiesApi;
    }

    public FilesApi getFilesApi() {
        return filesApi;
    }

    public FilesApi2 getFilesApi2() {
        return filesApi2;
    }

    public DrillThroughReportsApi getDrillThroughReportsApi() {
        return drillThroughReportsApi;
    }

    public ScriptsApi getScriptsApi() {
        return scriptsApi;
    }

    public SessionsApi getSessionsApi() {
        return sessionsApi;
    }

    public VariablesApi getVariablesApi() {
        return variablesApi;
    }

    public OutlineViewerApi getOutlineViewerApi() {
        return outlineViewerApi;
    }

    public DimensionsApi getDimensionsApi() {
        return dimensionsApi;
    }

    public ScenariosApi getScenariosApi() {
        return scenariosApi;
    }

    public ExecuteMdxApi getExecuteMdxApi() {
        return executeMdxApi;
    }

    public UrlsApi getUrlsApi() {
        return urlsApi;
    }

    public BatchOutlineEditingApi getBatchOutlineEditingApi() {
        return batchOutlineEditingApi;
    }

    public GroupsApi getGroupsApi() {
        return groupsApi;
    }

    public GlobalDataSourcesApi getGlobalDataSourcesApi() {
        return globalDataSourcesApi;
    }

    public LocksApi getLocksApi() {
        return locksApi;
    }

    public ApplicationLogsApi getApplicationLogsApi() {
        return logsApi;
    }

}