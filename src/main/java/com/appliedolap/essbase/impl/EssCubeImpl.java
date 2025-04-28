package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.misc.MdxJson;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.WrapperUtil;
import com.google.gson.Gson;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Represents an Essbase cube on the server.
 */
public class EssCubeImpl extends AbstractEssObject implements EssCube {

    private static final Logger logger = LoggerFactory.getLogger(EssCubeImpl.class);

    private final EssApplication application;

    private final Cube cube;

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
        try {
            // specifies calc, apparently same as null
            ScriptList scriptList = api.getScriptsApi().scriptsListScripts(application.getName(), cube.getName(), "calc");
            List<EssScript> scripts = new ArrayList<>();
            if (scriptList.getItems() != null) {
                for (Script script : scriptList.getItems()) {
                    EssScript essScript = new EssScriptImpl(api, this, script);
                    scripts.add(essScript);
                }
            }
            return Collections.unmodifiableList(scripts);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
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
            throw new EssApiException(apiException);
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

    @Override
    public void exportExcel() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(getApplicationName());
        job.setDb(getName());
        job.setJobtype(EssJobImpl.JobType.EXPORT_EXCEL.getParam());

        ParametersBean params = new ParametersBean();
        params.dataLevel("ALL_DATA");
        params.columnFormat("false");
        params.compress("false");
        job.setParameters(params);

        try {
            logger.info("Submitting job for {}.{} for Excel export", getApplicationName(), getName());
            // TODO: return EssJob
            JobRecordBean jobRecord = api.getJobsApi().jobsExecuteJob(job);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
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
            Response response = api.getExecuteMdxApi().mDXExecuteMDXCall(getApplicationName(), getName(), outputType.name(), mdxInput, new GenericApiCallback()).execute();
            ResponseBody body = response.body();

            if (response.isSuccessful()) {
                IOUtils.write(body.bytes(), outputStream);
            } else {
                logger.error("Error during query execution: {}", response.message());
            }
        } catch (IOException | ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssGrid executeMdx(String query) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        executeMdx(query, MdxOutputType.JSON, new MdxOptions(), outputStream);

        String json = new String(outputStream.toString());
        MdxJson mdxJson = new Gson().fromJson(json, MdxJson.class);
        return new EssGridImpl(mdxJson);
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

}