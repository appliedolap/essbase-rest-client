package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.misc.MdxJson;
import com.appliedolap.essbase.util.NativeHttp;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Represents an Essbase cube on the server.
 */
public class EssCubeImpl extends AbstractEssObject implements EssCube {

    private static final Logger logger = LoggerFactory.getLogger(EssCubeImpl.class);

    /** How long to wait for an export job before giving up on it. */
    private static final long JOB_TIMEOUT_MILLIS = 120_000;

    private static final long JOB_POLL_MILLIS = 1_500;

    private final EssApplication application;

    /** Not final: {@link #refreshStatus()} replaces it with what the server currently says. */
    private Cube cube;

    public EssCubeImpl(ApiContext api, EssApplication application, Cube cube) {
        super(api);
        this.application = application;
        this.cube = cube;
    }

    @Override
    public String getName() {
        return cube.getName();
    }

    @Override
    public Type getType() {
        return Type.CUBE;
    }

    @Override
    public List<EssScript> getCalcScripts() {
        return getScripts(EssScript.ScriptType.CALC);
    }

    @Override
    public List<EssScript> getMdxScripts() {
        return getScripts(EssScript.ScriptType.MDX);
    }

    /**
     * Lists the scripts of one kind.
     * <p>
     * Not through the generated client: the type has to travel on every scripts call and the generated
     * signature makes it optional, which produces the server's least helpful error. See
     * {@link EssScripts}.
     */
    @Override
    public List<EssScript> getScripts(EssScript.ScriptType scriptType) {
        String path = NativeHttp.withQuery("/applications/" + ApiClient.urlEncode(getApplicationName())
                + "/databases/" + ApiClient.urlEncode(getName()) + "/scripts",
                "file", scriptType.getParameter());
        String body = EssScripts.send(api, "GET", path, null, "scriptsListScripts");
        try {
            ScriptList list = api.getClient().getObjectMapper().readValue(body, ScriptList.class);
            List<EssScript> scripts = new ArrayList<>();
            for (Script script : wrap(list.getItems())) {
                scripts.add(new EssScriptImpl(api, this, script, scriptType));
            }
            return Collections.unmodifiableList(scripts);
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssScript createScript(EssScript.ScriptType scriptType, String name, String content) {
        String path = NativeHttp.withQuery("/applications/" + ApiClient.urlEncode(getApplicationName())
                + "/databases/" + ApiClient.urlEncode(getName()) + "/scripts",
                "file", scriptType.getParameter());
        EssScripts.send(api, "POST", path,
                Map.of("name", name, "content", content == null ? "" : content), "scriptsCreateScript");
        Script created = new Script();
        created.setName(name);
        return new EssScriptImpl(api, this, created, scriptType);
    }

    @Override
    public EssApplication getApplication() {
        return application;
    }

    private String getApplicationName() {
        return getApplication().getName();
    }

    @Override
    public List<EssSession> getSessions() {
        try {
            List<SessionAttributes> sessions = api.getSessionsApi().sessionsGetAllActiveSessions(application.getName(), cube.getName(), null);
            List<EssSession> sessionList = new ArrayList<>();
            for (SessionAttributes sessionAttributes : sessions) {
                EssSession session = new EssSessionImpl(api, sessionAttributes);
                sessionList.add(session);
            }
            return Collections.unmodifiableList(sessionList);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EssCubeVariable createVariable(String name, String value) {
        Variable variable = new Variable();
        variable.setName(name);
        variable.setValue(value);
        Variable created = WrapperUtil.doWithWrap(() -> api.getVariablesApi()
                .variablesCreateVariable(application.getName(), cube.getName(), variable));
        return new EssCubeVariableImpl(api, this, created);
    }

    @Override
    public List<com.appliedolap.essbase.EssEffectiveVariable> getEffectiveVariables() {
        // Widest first, so a nearer definition simply replaces what is already there and the one it
        // displaced is recorded as shadowed. Doing it the other way would mean checking before each
        // insert whether something nearer had already claimed the name.
        Map<String, List<EssVariable>> byName = new LinkedHashMap<>();
        collect(byName, getApplication().getServer().getVariables());
        collect(byName, getApplication().getVariables());
        collect(byName, getVariables());

        List<com.appliedolap.essbase.EssEffectiveVariable> effective = new ArrayList<>();
        for (List<EssVariable> definitions : byName.values()) {
            EssVariable winner = definitions.get(definitions.size() - 1);
            effective.add(new com.appliedolap.essbase.EssEffectiveVariable(winner,
                    new ArrayList<>(definitions.subList(0, definitions.size() - 1))));
        }
        effective.sort(Comparator.comparing(com.appliedolap.essbase.EssEffectiveVariable::getName,
                String.CASE_INSENSITIVE_ORDER));
        return Collections.unmodifiableList(effective);
    }

    /**
     * Variable names are case insensitive to Essbase, so they are keyed that way here - otherwise
     * CurMonth on a cube would sit beside curmonth from the server as two unrelated entries rather
     * than one overriding the other.
     */
    private static void collect(Map<String, List<EssVariable>> byName, List<? extends EssVariable> found) {
        for (EssVariable variable : found) {
            byName.computeIfAbsent(variable.getName().toLowerCase(Locale.ROOT), key -> new ArrayList<>())
                    .add(variable);
        }
    }

    @Override
    public List<EssCubeVariable> getVariables() {
        try {
            VariableList variables = api.getVariablesApi().variablesListVariables(application.getName(), cube.getName());
            List<EssCubeVariable> cubeVariables = new ArrayList<>();
            for (Variable variable : wrap(variables.getItems())) {
                cubeVariables.add(new EssCubeVariableImpl(api, this, variable));
            }
            return Collections.unmodifiableList(cubeVariables);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    @Override
    public EssOutline getOutline() {
        return new EssOutlineImpl(api, this);
    }

    @Override
    public boolean isScenariosEnabled() {
        try {
            String applicationName = getApplication().getName();
            String cubeName = getName();
            ScenarioCubesList scenarioCubesList = api.getScenariosApi().scenariosGetRegisteredCubes();

            for (ScenarioCubes scenarioCubes : wrap(scenarioCubesList.getItems())) {
                if (scenarioCubes.getApplication().equals(applicationName)) {
                    List<String> databases = scenarioCubes.getDatabases();
                    return databases.contains(cubeName);
//                        boolean participateInScenario = scenarioCubes.getParticipateInScenario();
//                        boolean createScenario = scenarioCubes.getCreateScenario();
                }
            }
            return false;
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    //{
    //	"name": "Foo",
    //	"description": "Tests the foo",
    //	"priority": "MEDIUM",
    //	"application": "Test2",
    //	"database": "Test2",
    //	"useCalculatedValues": false,
    //	"approvers": null,
    //	"participants": null,
    //	"dueDate": 1622962799999
    //}
    //TODO
//    private void createScenario() {
//
//    }

    //{
    //	"name": "SomeDrill",
    //	"type": "URL",
    //	"dataSourceName": "",
    //	"columns": [],
    //	"columnMapping": {},
    //	"parameterMapping": {},
    //	"drillableRegions": ["@Member(\"Actual\")"], -- can be normal member names too but they appear to be
    // dynamically validated against the outline which is why my first attempts to edit them didn't work
    //	"url": "the URL"
    //}

    @Override
    public EssDrillthrough createDrillthroughURL(String urlName, String urlLink, List<String> drillRegions) {
        WrapperUtil.wrap(() -> {
            DrillthroughBean drillthroughBean = new DrillthroughBean();
            drillthroughBean.setName(urlName);
            drillthroughBean.setType("URL");
            drillthroughBean.setUrl(urlLink);
            drillthroughBean.setDrillableRegions(drillRegions);
            api.getDrillThroughReportsApi().drillThroughReportsCreate(getApplicationName(), this.getName(), drillthroughBean);
        });
        return getDrillthrough(urlName);
    }

    @Override
    public List<EssDrillthrough> getDrillthroughs() {
        try {
            ReportList reportList = api.getDrillThroughReportsApi().drillThroughReportsGetReports(getApplicationName(), getName());
            List<EssDrillthrough> essDrillthroughs = new ArrayList<>();
            for (ReportBean reportBean : reportList.getItems()) {
                EssDrillthrough essDrillthrough = new EssDrillthroughImpl(api, this, reportBean);
                essDrillthroughs.add(essDrillthrough);
            }
            return Collections.unmodifiableList(essDrillthroughs);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    @Override
    public EssDrillthrough getDrillthrough(String drillthroughName) {
        for (EssDrillthrough drillthrough : getDrillthroughs()) {
            if (drillthrough.getName().equals(drillthroughName)) {
                return drillthrough;
            }
        }
        throw new NoSuchEssbaseObjectException(drillthroughName, Type.DRILLTHROUGH);
    }

    @Override
    public List<EssScenario> getScenarios() {
        try {
            ScenarioCollectionResponse response = api.getScenariosApi().scenariosGetScenarios(null, null, false, null, getApplication().getName(), getName(), null, null, null, null, false);
            List<EssScenario> scenarios = new ArrayList<>();
            for (ScenarioBean scenarioBean : wrap(response.getItems())) {
                EssScenario scenario = new EssScenarioImpl(api, this, scenarioBean);
                scenarios.add(scenario);
            }
            return Collections.unmodifiableList(scenarios);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    @Override
    public EssMember getMember(String memberName) {
        try {
            MemberBean memberBean = api.getOutlineViewerApi().outlineGetMemberInfo(getApplicationName(), cube.getName(), memberName, null);
            return new EssMemberImpl(api, this, memberBean);
        } catch (ApiException apiException) {
            // unfortunately, the Essbase REST API just throws a 400 exception (as opposed to something more specific)
            // when you have an invalid member name (or cube or application, but we're assuming that those are okay)
            throw new NoSuchEssbaseObjectException(memberName, Type.MEMBER);
        }
    }

    @Override
    public List<EssDimension> getDimensions() {
        try {
            DimensionList dimensionList = api.getDimensionsApi().dimensionsListDimensions(application.getName(), cube.getName());
            List<EssDimension> dimensions = new ArrayList<>();
            if (dimensionList.getItems() != null) {
                for (DimensionBean dimensionBean : dimensionList.getItems()) {
                    EssDimension dimension = new EssDimensionImpl(api, application, this, dimensionBean);
                    dimensions.add(dimension);
                }
            }
            return Collections.unmodifiableList(dimensions);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssLock> getLockedObjects(Integer offset, Integer limit) {
        try {
            LockObjectList lockObjectLists = api.getLocksApi().locksGetLockedObjects(application.getName(), cube.getName(), offset, limit);
            List<EssLock> locks = new ArrayList<>();
            List<LockObject> lockObject = lockObjectLists.getItems();
            if (Objects.nonNull(lockObject)) {
                for (LockObject object : lockObject) {
                    EssLock lock = new EssLockImpl(api, object);
                    locks.add(lock);
                }
            }
            return locks;
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void unlockObject(EssLock lockedObject) {
        try {
            LockObject object = new LockObject();
            object.setName(lockedObject.getName());
            object.setTime(lockedObject.getTime());
            object.setUser(lockedObject.getUser());
            object.setType(lockedObject.getLockObjectType());
            api.getLocksApi().locksUnLockObject(application.getName(), cube.getName(), object);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void lockObject(EssLock unlockedObject) {
        try {
            LockObject object = new LockObject();
            object.setName(unlockedObject.getName());
            object.setTime(unlockedObject.getTime());
            object.setUser(unlockedObject.getUser());
            object.setType(unlockedObject.getLockObjectType());
            api.getLocksApi().locksLockObject(application.getName(), cube.getName(), object);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Runs the Excel export job and waits for it.
     *
     * <p>{@code buildMethod} is required and has to be {@code GENERATION}, which makes no sense for an
     * export and is not in any documentation - the job simply fails without it:
     * <pre>
     * An argument buildMethod in parameters cannot be null or empty
     * </pre>
     * and rejects every other value ({@code LEVEL}, {@code PARENT_CHILD}, ...) with "Invalid value of
     * build method". The export job evidently shares a parameter validator with dimension build. This
     * was true on both 21.7 and 26.1, so it has never worked without it.
     *
     * <p>Waits rather than returning the job, because the caller wants the workbook and the workbook
     * does not exist until the job is done. The job is submitted synchronously but runs in the
     * background: a fresh submit comes back {@code IN_PROGRESS} and turns into a real status a few
     * seconds later.
     */
    @Override
    public String exportExcel() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(getApplicationName());
        job.setDb(getName());
        job.setJobtype(EssJob.JobType.EXPORT_EXCEL.getParam());

        ParametersBean params = new ParametersBean();
        params.dataLevel("ALL_DATA");
        // Strings, not booleans: the server rejects real JSON booleans here outright.
        params.columnFormat("false");
        params.compress("false");
        params.buildMethod("GENERATION");
        job.setParameters(params);

        try {
            logger.info("Submitting job for {}.{} for Excel export", getApplicationName(), getName());
            JobRecordBean record = api.getJobsApi().jobsExecuteJob(job);
            record = awaitCompletion(record);
            EssJob.Status status = EssJob.Status.fromCode(record.getStatusCode());
            if (!status.isSuccessful()) {
                throw new EssApiException("The Excel export failed: " + describeFailure(record));
            }
            String path = outputFile(record);
            if (path == null) {
                throw new EssApiException("The Excel export reported success but named no file");
            }
            logger.info("Exported {}.{} to {}", getApplicationName(), getName(), path);
            return path;
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /** Polls until the job stops being in progress, or until it has plainly stalled. */
    private JobRecordBean awaitCompletion(JobRecordBean record) throws ApiException {
        long deadline = System.currentTimeMillis() + JOB_TIMEOUT_MILLIS;
        while (EssJob.Status.fromCode(record.getStatusCode()) == EssJob.Status.IN_PROGRESS) {
            if (System.currentTimeMillis() > deadline) {
                throw new EssApiException("The Excel export did not finish within "
                        + (JOB_TIMEOUT_MILLIS / 1000) + " seconds");
            }
            try {
                Thread.sleep(JOB_POLL_MILLIS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new EssApiException("Interrupted while waiting for the Excel export");
            }
            record = api.getJobsApi().jobsGetJobInfo(String.valueOf(record.getJobID()));
        }
        return record;
    }

    /**
     * The catalogue path the job wrote, which the server reports as {@code metadataFile} inside
     * {@code jobOutputInfo} - an untyped map, so this reads it as one.
     */
    private static String outputFile(JobRecordBean record) {
        Object info = record.getJobOutputInfo();
        if (info instanceof Map) {
            Object file = ((Map<?, ?>) info).get("metadataFile");
            if (file != null && !file.toString().isBlank()) {
                return file.toString();
            }
        }
        return record.getJobfileName();
    }

    private static String describeFailure(JobRecordBean record) {
        Object info = record.getJobOutputInfo();
        if (info instanceof Map) {
            Object message = ((Map<?, ?>) info).get("errorMessage");
            if (message != null && !message.toString().isBlank()) {
                return message.toString();
            }
        }
        return String.valueOf(record.getStatusMessage());
    }

    @Override
    public void importExcel(String path, String filename) {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(getApplicationName());
        job.setDb(getName());
        job.setJobtype("importExcel");

        ParametersBean params = new ParametersBean();

        params.loaddata("false");
        params.overwrite("true");
        params.deleteExcelOnSuccess("false");
        params.importExcelFileName(filename);
        params.recreateApplication("true");
        params.createFiles("true");
        params.setCatalogExcelPath(path);
        job.setParameters(params);

        try {
            api.getJobsApi().jobsExecuteJob(job);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public void importFile(File file) {
        EssFolder folder = new EssFolderImpl(api, getApplication().getServer(), getName(), String.format("applications/%s/%s", getApplicationName(), getName()));
        folder.uploadFile(file);
    }

    @Override
    public void executeMdx(String query, MdxOutputType outputType, MdxOptions mdxOptions, OutputStream outputStream) {
        try {
            MDXInput mdxInput = new MDXInput();
            mdxInput.setQuery(query);

            // CSV, HTML, JSON, XLSX
            String path = "/applications/" + ApiClient.urlEncode(getApplicationName())
                    + "/databases/" + ApiClient.urlEncode(getName()) + "/mdx";
            path = NativeHttp.withQuery(path, "format", outputType.name());
            NativeHttp.copyBodyTo(NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/octet-stream, application/json, text/csv, text/html, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .header("Content-Type", "application/json")
                    .POST(NativeHttp.jsonBody(api.getClient(), mdxInput)), "mDXExecuteMDX"), outputStream);
        } catch (IOException | ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssApplication.Status getStatus() {
        return cube.getStatus() == null ? EssApplication.Status.UNKNOWN
                : EssApplication.Status.parse(cube.getStatus());
    }

    @Override
    public EssApplication.Status refreshStatus() {
        cube = WrapperUtil.doWithWrap(() -> api.getApplicationsApi()
                .applicationsGetCube(application.getName(), getName()));
        return getStatus();
    }

    @Override
    public EssCubeAi getAi() {
        return new EssCubeAiImpl(api, this);
    }

    @Override
    public EssGrid executeMdx(String query) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        executeMdx(query, MdxOutputType.JSON, new MdxOptions(), outputStream);

        String json = outputStream.toString();
        try {
            MdxJson mdxJson = api.getClient().getObjectMapper().readValue(json, MdxJson.class);
            return new EssGridImpl(mdxJson);
        } catch (IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssCubeView openCubeView() {
        try {
            Grid grid = api.getGridApi().gridGetDefault(getApplicationName(), getName());
            return new EssCubeViewImpl(api, getApplicationName(), getName(), grid);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssCubeView openCubeView(String layoutName) {
        try {
            Grid grid = api.getGridApi().gridExecuteLayout(getApplicationName(), getName(), layoutName, null);
            return new EssCubeViewImpl(api, getApplicationName(), getName(), grid);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * The layout to delete is named after the current user, so this needs to know who that is.
     * <p>
     * It asks the authentication strategy first and only falls back to {@code GET /session} when the
     * strategy doesn't know - which is a strategy carrying a session or token rather than a name. That
     * ordering is not just to save a round trip: {@code GET /session} answers 500 on Essbase 26.1, so
     * asking it first made this method, and therefore every fresh ad hoc grid, fail outright there.
     */
    @Override
    public void resetDefaultView() {
        try {
            String username = api.getAuthentication() == null ? null
                    : api.getAuthentication().username().orElse(null);
            if (username == null) {
                username = api.getUserSessionApi().userSessionGetSession(false).getId();
            }
            api.getLayoutsApi().deleteLayout(getApplicationName(), getName(), "Session_Layout_" + username, null);
        } catch (ApiException e) {
            EssApiException wrapped = new EssApiException(e);
            if (wrapped.getMessage() == null || !wrapped.getMessage().contains("No layout exists")) {
                throw wrapped;
            }
        }
    }

    public static class ExcelExportOptions {

        private boolean columnFormat;

    }

    public static class MdxOptions {

        private boolean dataless;

        private boolean hideRestrictedData;

        private boolean cellAttributes;

        private boolean formatString;

        private boolean formatValues;

        private boolean meaninglessCells;

        private boolean textList;

        private boolean urlDrillthrough;

        // NAME, ALIAS, UNIQUE_NAME
        //private String memberIdentifierType = "NAME";

    }

    @Override
    public List<com.appliedolap.essbase.EssMdxFunctionGroup> getMdxFunctions() {
        return EssMdxFunctions.read(api, getApplicationName(), getName());
    }

    @Override
    public void start() {
        performOperation("start");
    }

    @Override
    public void stop() {
        performOperation("stop");
    }

    /**
     * Starts or stops the cube.
     *
     * <p>By hand rather than through the generated client, which models the operation only for an
     * application: {@code applicationsPerformOperation} takes an application name and nothing else.
     * The action is a query parameter, not a path segment - {@code /databases/Basic/action/start} is
     * a 404.
     */
    private void performOperation(String action) {
        String path = NativeHttp.withQuery("/applications/" + ApiClient.urlEncode(getApplicationName())
                + "/databases/" + ApiClient.urlEncode(getName()), "action", action);
        try {
            NativeHttp.sendAndDiscard(api.getClient(),
                    NativeHttp.request(api.getClient(), path).PUT(HttpRequest.BodyPublishers.noBody()),
                    "databasesPerformOperation");
        } catch (com.appliedolap.essbase.client.ApiException | java.io.IOException e) {
            throw new EssApiException(e);
        }
    }

}