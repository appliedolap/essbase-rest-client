package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.util.NativeHttp;
import com.appliedolap.essbase.util.Utils;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    /**
     * Connects using the given authentication strategy, which need not be a username and password - see
     * {@link com.appliedolap.essbase.EssAuthentication}. The way in for a session established elsewhere,
     * such as by a user signing in through an external identity provider.
     *
     * @param server the server REST API path
     * @param authentication how to authenticate
     */
    public EssServerImpl(String server, EssAuthentication authentication) {
        super(createApiContext(server, authentication));
        this.server = server;
    }

    public EssServerImpl(EssServerConnectionDetailsImpl connectionDetails) {
        super(createApiContext(connectionDetails.getServer(), connectionDetails.getUsername(), connectionDetails.getPassword(), connectionDetails.isStateless()));
        this.server = connectionDetails.getServer();
    }

    private static ApiContext createApiContext(String server, String username, String password, boolean stateless) {
        return createApiContext(server, stateless
                ? EssAuthentication.basic(username, password)
                : EssAuthentication.session(username, password));
    }

    private static ApiContext createApiContext(String server, EssAuthentication authentication) {
        ApiClientFactory clientFactory = new ApiClientFactory(server + DEFAULT_REST_API_PATH, authentication);
        return new ApiContext(clientFactory.create(), authentication);
    }

    private static ApiContext createApiContext(EssServerConnectionDetailsImpl connectionDetails) {
        return createApiContext(connectionDetails.getServer(), connectionDetails.getUsername(),
                connectionDetails.getPassword(), connectionDetails.isStateless());
    }

    /**
     * Newest first, on the reasoning that a server serving both should be described by its more current
     * document, and that new deployments are the likelier case over time.
     */
    private static final List<String> API_SPEC_PATHS = List.of("/openapi.json", "/swagger.json");

    @Override
    public EssApiSpec getApiSpec() {
        EssApiException lastFailure = null;
        for (String path : API_SPEC_PATHS) {
            try {
                String json = getRaw(path);
                JsonNode parsed = api.getClient().getObjectMapper().readTree(json);
                // The document says which specification it follows, so there is no need to infer it from
                // the path it happened to be served at.
                if (parsed.hasNonNull("openapi")) {
                    return new EssApiSpec("openapi", parsed.get("openapi").asText(), json);
                }
                if (parsed.hasNonNull("swagger")) {
                    return new EssApiSpec("swagger", parsed.get("swagger").asText(), json);
                }
                throw new EssApiException("The document at " + path + " is not an API definition");
            } catch (EssApiException e) {
                lastFailure = e;
            } catch (IOException e) {
                lastFailure = new EssApiException(e);
            }
        }
        throw lastFailure != null ? lastFailure
                : new EssApiException("This server does not serve an API definition anywhere known");
    }

    @Override
    public Map<String, Object> getInstanceDetails() {
        try {
            return api.getClient().getObjectMapper().readValue(getRaw("/about/instance"),
                    new TypeReference<LinkedHashMap<String, Object>>() { });
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public Map<String, Object> getAboutDetails() {
        try {
            return api.getClient().getObjectMapper().readValue(getRaw("/about"),
                    new TypeReference<LinkedHashMap<String, Object>>() { });
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * GETs a path below the REST base URL and returns the body, for the handful of things the generated
     * client cannot usefully express - a document whose shape varies by release, or one wanted verbatim.
     * <p>
     * Uses the client's own interceptor, so it authenticates exactly as every generated call does,
     * including with a session or a supplied cookie rather than only a password.
     */
    private String getRaw(String path) {
        ApiClient client = api.getClient();
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(client.getBaseUri() + path))
                    .header("Accept", "application/json")
                    .GET();
            client.getRequestInterceptor().accept(request);
            HttpResponse<String> response = client.getHttpClient()
                    .send(request.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() / 100 != 2) {
                throw new EssApiException("GET " + path + " answered HTTP " + response.statusCode());
            }
            return response.body();
        } catch (IOException e) {
            throw new EssApiException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new EssApiException(e);
        }
    }

    @Override
    public EssAuthentication getAuthentication() {
        return api.getAuthentication();
    }

    @Override
    public void signOff() {
        try {
            api.getUserSessionApi().userSessionSignoff();
        } catch (ApiException e) {
            throw new EssApiException(e);
        } finally {
            // In the finally, not the try: if the sign-off call failed because the session was already
            // gone - expired, or killed from elsewhere - then continuing to present it is certainly wrong,
            // and re-authenticating is the right recovery either way.
            if (api.getAuthentication() != null) {
                api.getAuthentication().sessionEnded();
            }
        }
    }

    @Override
    public Optional<Instant> getSessionExpiry() {
        return api.getAuthentication() == null ? Optional.empty() : api.getAuthentication().sessionExpiry();
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
                applications.add(new EssApplicationImpl(api, this, application));
            }
            return Collections.unmodifiableList(applications);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssApplicationImpl getApplication(String applicationName) {
        try {
            // calls the same list method as the getApplications method but provides a filter on the app name so that
            // only one is returned. In the future we could potentially go straight to the /applications/{applicationName}
            // endpoint, but that returns much more information, so doing it this way gives us consistency with the data
            // that is returned from the other method
            ApplicationList applicationList = api.getApplicationsApi().applicationsGetApplications(applicationName, null, null, null, null, null);
            if (Utils.isNotEmpty(applicationList.getItems())) {
                return new EssApplicationImpl(api, this, applicationList.getItems().get(0));
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
                EssFile essFile = isFolder ? new EssFolderImpl(api, this, name, fullPath) : new EssFileImpl(api, this, name, fullPath);
                files.add(essFile);
            }
            return Collections.unmodifiableList(files);
        } catch (ApiException e) {
            throw new RuntimeException("Could not list files", e);
        }
    }

    /**
     * Finds a file by the folder holding it and its name.
     *
     * <p>Lists through {@link EssFolderImpl}, which is the one listing path that encodes a path
     * correctly - the generated {@code filesListFiles} escapes the separators, so anything below the
     * top level came back as "Specified path '/%2Fapplications%2FSample' does not exist". A leading
     * slash is fine here: catalogue paths are reported with one and the encoder trims it.
     */
    @Override
    public EssFile getFile(String path, String filename) {
        for (EssFile file : new EssFolderImpl(api, this, path, path).getFiles()) {
            if (file.getName().equals(filename)) {
                return file;
            }
        }
        return null;
    }

    @Override
    public List<EssSession> getSessions() {
        return WrapperUtil.wrapList(() -> api.getSessionsApi().sessionsGetAllActiveSessions(null, null, null), sessionAttributes -> new EssSessionImpl(api, sessionAttributes));
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
            // The native OpenAPI client tries to deserialize this endpoint as a JSON string, but Essbase
            // returns plain text such as "/users/admin". Keep this call on the configured client while
            // preserving the raw text body.
            try (InputStream body = NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), "/files/homepath")
                    .header("Accept", "application/json, application/xml")
                    .GET(), "filesGetUserHomePath").body()) {
                String homepath = new String(body.readAllBytes(), StandardCharsets.UTF_8).trim();
                return new EssFolderImpl(api, this, homepath, homepath);
            }
        } catch (ApiException | IOException e) {
            logger.error("Unable to get home path: {}", e.getMessage());
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssUtility> getUtilities() {
        try {
            ResourceList utilities = api.getTemplatesAndUtilitiesApi().resourcesGetUtilities();
            List<EssUtility> essUtilities = new ArrayList<>();
            for (Resource resource : wrap(utilities.getItems())) {
                EssUtility utility = new EssUtilityImpl(api, resource);
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
                EssJob essJob = new EssJobImpl(api, this, jobRecordBean);
                essJobs.add(essJob);
            }
            return Collections.unmodifiableList(essJobs);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public List<EssPermission> getPermissions() {
        try {
            return EssPermissions.from(
                    api.getServiceRoleProvisioningApi().serviceRoleProvisioningSearchProvision(null, null, null));
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssUser> getUsers() {
        try {
            Users users = api.getUsersApi().usersSearch(null, -1, "all");
            List<EssUser> essUsers = new ArrayList<>();
            for (UserBean user : wrap(users.getItems())) {
                essUsers.add(new EssUserImpl(api, this, user));
            }
            return Collections.unmodifiableList(essUsers);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssGroup> getGroups() {
        try {
            Groups groups = api.getGroupsApi().groupsSearch(null, -1, "all");
            List<EssGroup> essGroups = new ArrayList<>();
            for (GroupBean group : wrap(groups.getItems())) {
                EssGroup essGroup = new EssGroupImpl(api, this, group);
                essGroups.add(essGroup);
            }
            return Collections.unmodifiableList(essGroups);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssServerVariable> getVariables() {
        try {
            VariableList variableList = api.getServerVariablesApi().variablesListServerVariables();
            List<EssServerVariable> variables = new ArrayList<>();
            for (Variable variable : wrap(variableList.getItems())) {
                variables.add(new EssServerVariableImpl(api, this, variable));
            }
            return Collections.unmodifiableList(variables);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssServerVariable createVariable(String name, String value) {
        Variable variable = new Variable();
        variable.setName(name);
        variable.setValue(value);
        Variable created = WrapperUtil.doWithWrap(
                () -> api.getServerVariablesApi().variablesCreateServerVariable(variable));
        return new EssServerVariableImpl(api, this, created);
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
        job.setJobtype(EssJobImpl.JobType.IMPORT_EXCEL.getParam());

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
            return new EssJobImpl(api, this, jobRecordBean);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssURL> getURLs() {
        try {
            return api.getUrlsApi().uRLsGet().getItems()
                    .stream()
                    .map(url -> new EssURLImpl(api, this, url))
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
                    .map(ds -> new EssDataSourceImpl(api, this, ds))
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
    /**
     * Runs a query against a data source and writes the rows out.
     *
     * <p>Two calls, and both are load-bearing. {@code POST /datasources/query} does not return data -
     * it answers with the column list and a link carrying a stream id, and the rows come from a
     * follow-up GET on that link.
     *
     * <p>The stream lives in server-side session state, so the second call has to reach the same
     * session as the first. Under an authentication strategy that establishes no session - Basic, for
     * one - every request lands somewhere new and the follow-up answers
     * {@code Stream id '...' does not exist}. The cookies the first response set are therefore carried
     * onto the second explicitly, which works whatever the strategy is. Oracle's own curl example
     * hints at this with a {@code --cookie-jar} that is easy to read as boilerplate.
     *
     * <p>Not {@code /datasources/query/stream}, which also exists, and which answers HTTP 200 with
     * {@code Failed to stream... Failed to execute query.} in the body for every input tried.
     */
    @Override
    public void streamDataSource(String query, boolean includeHeaders, String delimiter,
                                 Map<String, Object> params, OutputStream outputStream) {
        DatasourceQueryInfo info = new DatasourceQueryInfo();
        info.setQuery(query);
        info.setDelimiter(delimiter);
        info.setParams(params);
        try {
            HttpResponse<InputStream> opened = NativeHttp.send(api.getClient(),
                    NativeHttp.request(api.getClient(), "/datasources/query")
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .POST(NativeHttp.jsonBody(api.getClient(), info)),
                    "globalDatasourcesQuery");
            String body;
            try (InputStream in = opened.body()) {
                body = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            }
            String streamPath = streamPathIn(body);
            if (streamPath == null) {
                throw new EssApiException("The query returned no stream to read: " + body);
            }
            HttpRequest.Builder rows = NativeHttp.request(api.getClient(), streamPath)
                    .header("Accept", "text/csv, application/octet-stream, application/json")
                    .GET();
            String cookies = sessionCookiesFrom(opened);
            if (cookies != null) {
                rows.header("Cookie", cookies);
            }
            NativeHttp.copyBodyTo(NativeHttp.send(api.getClient(), rows, "globalDatasourcesGetDataStream"),
                    outputStream);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /** Pulls the stream link out of the query response and reduces it to a path this client can call. */
    private String streamPathIn(String body) throws IOException {
        JsonNode parsed = api.getClient().getObjectMapper().readTree(body);
        for (JsonNode link : parsed.path("links")) {
            String href = link.path("href").asText("");
            int rest = href.indexOf("/datasources/query/data/");
            if (rest >= 0) {
                return href.substring(rest);
            }
        }
        return null;
    }

    /**
     * The cookies the server set on the query response, joined for a Cookie header. Returns null when
     * it set none, in which case whatever the auth strategy already sends is all there is.
     */
    private static String sessionCookiesFrom(HttpResponse<InputStream> response) {
        StringBuilder header = new StringBuilder();
        for (String setCookie : response.headers().allValues("Set-Cookie")) {
            String pair = setCookie.split(";", 2)[0].trim();
            if (!pair.isEmpty()) {
                if (header.length() > 0) {
                    header.append("; ");
                }
                header.append(pair);
            }
        }
        return header.length() == 0 ? null : header.toString();
    }

    @Override
    public void deleteDataSource(String name) {
        EssConnections.send(api, "DELETE", "/datasources/" + ApiClient.urlEncode(name), null,
                "globalDatasourcesDeleteDatasource");
    }

    @Override
    public List<EssConnection> getConnections() {
        String body = EssConnections.send(api, "GET", "/connections", null, "globalConnectionsGetConnections");
        try {
            JsonNode items = api.getClient().getObjectMapper().readTree(body).path("items");
            List<EssConnection> connections = new ArrayList<>();
            for (JsonNode item : items) {
                connections.add(new EssConnectionImpl(api,
                        api.getClient().getObjectMapper().convertValue(item, LinkedHashMap.class)));
            }
            return Collections.unmodifiableList(connections);
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * One connection, with everything the server knows about it.
     * <p>
     * Two calls, because neither endpoint answers with the whole object: the list carries
     * {@code description} but no {@code path}, and fetching one carries {@code path} but no
     * {@code description}. A caller shouldn't have to know that, so they are merged here.
     */
    @Override
    public Optional<EssConnection> getConnection(String name) {
        Optional<EssConnection> summary = getConnections().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
        if (summary.isEmpty()) {
            return summary;
        }
        String body = EssConnections.send(api, "GET", "/connections/" + ApiClient.urlEncode(name), null,
                "globalConnectionsGetConnectionDetails");
        try {
            Map<String, Object> merged = new LinkedHashMap<>(
                    ((EssConnectionImpl) summary.get()).getProperties());
            merged.putAll(api.getClient().getObjectMapper()
                    .convertValue(api.getClient().getObjectMapper().readTree(body), LinkedHashMap.class));
            return Optional.of(new EssConnectionImpl(api, merged));
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssConnection createFileConnection(String name, String catalogPath, String description) {
        EssConnections.send(api, "POST", "/connections", fileConnection(name, catalogPath, description),
                "globalConnectionsCreateConnection");
        return getConnection(name).orElseThrow(
                () -> new EssApiException("The server accepted the connection but does not list it: " + name));
    }

    @Override
    public Optional<String> testFileConnection(String name, String catalogPath) {
        try {
            EssConnections.send(api, "POST", "/connections/actions/test",
                    fileConnection(name, catalogPath, null), "globalConnectionsTestConnection");
            return Optional.empty();
        } catch (EssApiException e) {
            return Optional.of(e.getMessage());
        }
    }

    /**
     * The body a file connection wants. {@code ociAPIFormat} is declared required on every connection,
     * including ones that have nothing to do with OCI, so it is sent empty rather than omitted.
     */
    private static Map<String, Object> fileConnection(String name, String catalogPath, String description) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name", name);
        body.put("type", "FILE");
        body.put("subtype", "FILE");
        body.put("path", catalogPath);
        body.put("ociAPIFormat", "");
        if (description != null) {
            body.put("description", description);
        }
        return body;
    }

    /**
     * The about information of this server.
     */
    @Override
    public List<String> getServerLogTypes() {
        ApiClient client = api.getClient();
        try {
            HttpRequest.Builder request = HttpRequest.newBuilder(URI.create(client.getBaseUri() + "/logs"))
                    .header("Accept", "application/json")
                    .GET();
            client.getRequestInterceptor().accept(request);
            HttpResponse<String> response = client.getHttpClient()
                    .send(request.build(), HttpResponse.BodyHandlers.ofString());
            // A deployment without server-level logs answers 404 here, which is an answer and not a
            // failure - see the interface note. Anything else unexpected is treated the same way rather
            // than stopping a caller who only wanted to know whether to offer the feature.
            if (response.statusCode() / 100 != 2 || response.body() == null || response.body().isBlank()) {
                return Collections.emptyList();
            }
            JsonNode links = client.getObjectMapper().readTree(response.body());
            List<String> types = new ArrayList<>();
            for (JsonNode link : links) {
                // The application-level equivalent answers with links whose rel names what they fetch;
                // the server-level one is the same shape a level up, with the type in the path.
                JsonNode href = link.get("href");
                if (href == null) {
                    continue;
                }
                String type = serverTypeIn(href.asText());
                if (type != null && !types.contains(type)) {
                    types.add(type);
                }
            }
            return Collections.unmodifiableList(types);
        } catch (IOException e) {
            return Collections.emptyList();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    /** Pulls {@code essbase} out of {@code .../logs/essbase/latest}. */
    private static String serverTypeIn(String href) {
        int logs = href.indexOf("/logs/");
        if (logs < 0) {
            return null;
        }
        String rest = href.substring(logs + "/logs/".length());
        int slash = rest.indexOf('/');
        String type = slash < 0 ? rest : rest.substring(0, slash);
        return type.isBlank() ? null : type;
    }

    @Override
    public void downloadLatestServerLog(String serverType, OutputStream outputStream) {
        downloadServerLog(serverType, "latest", outputStream, "serverLogsDownloadLatestLogFile");
    }

    @Override
    public void downloadServerLogsAsZip(String serverType, OutputStream outputStream) {
        downloadServerLog(serverType, "all", outputStream, "serverLogsDownloadAllLogFiles");
    }

    private void downloadServerLog(String serverType, String which, OutputStream outputStream, String operationId) {
        String path = "/logs/" + ApiClient.urlEncode(serverType) + "/" + which;
        try {
            NativeHttp.copyBodyTo(NativeHttp.send(api.getClient(),
                    NativeHttp.request(api.getClient(), path).GET(), operationId), outputStream);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

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

    @Override
    public List<EssMdxFunctionGroup> getMdxFunctions() {
        // Any cube will do and the answer is the same, so this takes the first one it finds rather
        // than looking for a "best" one. A stopped application counts: the endpoint describes the
        // server's MDX dialect and answers whether or not the cube could be queried.
        for (EssApplication application : getApplications()) {
            for (EssCube cube : application.getCubes()) {
                return EssMdxFunctions.read(api, application.getName(), cube.getName());
            }
        }
        throw new EssApiException("This server has no cube to read the MDX function list through. "
                + "Essbase offers the list only on a cube-scoped endpoint, even though what it "
                + "describes is the server.");
    }

    @Override
    public EssMcp getMcp() {
        return new EssMcpImpl(api);
    }

    @Override
    public EssMaintenanceLimits getMaintenanceLimits() {
        return EssSettings.maintenanceLimits(api);
    }

    @Override
    public List<EssLogSetting> getLogSettings() {
        return EssSettings.logSettings(api);
    }

    @Override
    public void setLogSettings(List<EssLogSetting> settings) {
        EssSettings.setLogSettings(api, settings);
    }

}