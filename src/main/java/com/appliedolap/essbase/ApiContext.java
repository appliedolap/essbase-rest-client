package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.api.*;

/**
 * Serves as a holder for all the various API interfaces. We can pass this around as needed without having to
 * clutter up individual classes with having to instantiate and track all of these.
 */
public class ApiContext {

    private final ApiClient client;

    private final EssAuthentication authentication;

    private final ApplicationsApi applicationsApi;

    private final ApplicationConfigurationApi applicationConfigurationApi;

    private final AboutEssbaseApi aboutEssbaseApi;

    private final JobsApi jobsApi;

    private final ServerVariablesApi serverVariablesApi;

    private final TemplatesAndUtilitiesApi templatesAndUtilitiesApi;

    private final FilesApi filesApi;

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

    private final UsersApi usersApi;

    private final ExecuteMdxApi executeMdxApi;

    private final GlobalDataSourcesApi globalDataSourcesApi;

    private final LocksApi locksApi;

    private final ApplicationLogsApi logsApi;

    private final GridApi gridApi;

    private final GridPreferencesApi gridPreferencesApi;

    private final LayoutsApi layoutsApi;

    private final UserSessionApi userSessionApi;

    public ApiContext(ApiClient client) {
        this(client, null);
    }

    /**
     * @param authentication the strategy the client authenticates with, so that operations which change
     *                       the session's validity - signing off, above all - can tell it. May be null.
     */
    public ApiContext(ApiClient client, EssAuthentication authentication) {
        this.client = client;
        this.authentication = authentication;
        this.applicationsApi = new ApplicationsApi(client);
        this.applicationConfigurationApi = new ApplicationConfigurationApi(client);
        this.aboutEssbaseApi = new AboutEssbaseApi(client);
        this.jobsApi = new JobsApi(client);
        this.serverVariablesApi = new ServerVariablesApi(client);
        this.templatesAndUtilitiesApi = new TemplatesAndUtilitiesApi(client);
        this.filesApi = new FilesApi(client);
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
        this.usersApi = new UsersApi(client);
        this.executeMdxApi = new ExecuteMdxApi(client);
        this.globalDataSourcesApi = new GlobalDataSourcesApi(client);
        this.locksApi = new LocksApi(client);
        this.logsApi = new ApplicationLogsApi(client);
        this.gridApi = new GridApi(client);
        this.gridPreferencesApi = new GridPreferencesApi(client);
        this.layoutsApi = new LayoutsApi(client);
        this.userSessionApi = new UserSessionApi(client);
    }

    public ApplicationsApi applicationsApi() {
        return applicationsApi;
    }

    public ApiClient getClient() {
        return client;
    }

    /** The authentication strategy in use, or null when the context was built without one. */
    public EssAuthentication getAuthentication() {
        return authentication;
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

    public UsersApi getUsersApi() {
        return usersApi;
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

    public GridApi getGridApi() {
        return gridApi;
    }

    public GridPreferencesApi getGridPreferencesApi() {
        return gridPreferencesApi;
    }

    public LayoutsApi getLayoutsApi() {
        return layoutsApi;
    }

    public UserSessionApi getUserSessionApi() {
        return userSessionApi;
    }

}