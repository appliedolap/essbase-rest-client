package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.NoSuchEssbaseObjectException;
import com.appliedolap.essbase.impl.EssDrillthroughImpl;
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
public class EssCube extends AbstractEssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssCube.class);

    private final EssApplication application;

    private final Cube cube;

    public EssCube(ApiContext api, EssApplication application, Cube cube) {
        super(api);
        this.application = application;
        this.cube = cube;
    }

    /**
     * Gets the name of this cube.
     *
     * @return the name of the cube
     */
    public String getName() {
        return cube.getName();
    }

    @Override
    public Type getType() {
        return Type.CUBE;
    }

    /**
     * Get the list of scripts associated with this cube.
     *
     * @return the scripts on this cube
     */
    public List<EssScript> getCalcScripts() {
        try {
            // specifies calc, apparently same as null
            ScriptList scriptList = api.getScriptsApi().scriptsListScripts(application.getName(), cube.getName(), "calc");
            List<EssScript> scripts = new ArrayList<>();
            if (scriptList.getItems() != null) {
                for (Script script : scriptList.getItems()) {
                    EssScript essScript = new EssScript(api, this, script);
                    scripts.add(essScript);
                }
            }
            return Collections.unmodifiableList(scripts);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the parent application of this cube.
     *
     * @return the parent application
     */
    public EssApplication getApplication() {
        return application;
    }

    private String getApplicationName() {
        return getApplication().getName();
    }

    /**
     * Gets the list of sessions on this cube.
     *
     * @return the current cube sessions
     */
    public List<EssSession> getSessions() {
        try {
            List<SessionAttributes> sessions = api.getSessionsApi().sessionsGetAllActiveSessions(application.getName(), cube.getName(), null);
            List<EssSession> sessionList = new ArrayList<>();
            for (SessionAttributes sessionAttributes : sessions) {
                EssSession session = new EssSession(api, sessionAttributes);
                sessionList.add(session);
            }
            return Collections.unmodifiableList(sessionList);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the list of variables specific to this cube. The return type is an {@link EssVariable}, however, the actually
     * implementation will be the subclass {@link EssCubeVariable}. The {@link EssVariable#getScope()} method can be used
     * to check the variable type and cast as needed.
     *
     * @return the cube variables
     */
    public List<EssCubeVariable> getVariables() {
        try {
            VariableList variables = api.getVariablesApi().variablesListVariables(application.getName(), cube.getName());
            List<EssCubeVariable> cubeVariables = new ArrayList<>();
            for (Variable variable : wrap(variables.getItems())) {
                cubeVariables.add(new EssCubeVariable(api, this, variable));
            }
            return Collections.unmodifiableList(cubeVariables);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * Gets an outline object for this cube.
     *
     * @return an outline object
     */
    public EssOutline getOutline() {
        return new EssOutline(api, this);
    }

    /**
     * Checks if this cube has scenarios enabled.
     *
     * @return true if scenarios are enabled, false otherwise
     */
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

    /**
     * Creates a URL-type drill-through on the cube,
     *
     * @param urlName the name (no spaces?!)
     * @param urlLink the drill-through link
     * @param drillRegions the drillable regions
     */
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

    /**
     * Get list of drill-through reports on the cube.
     *
     * @return the list of cube drill-through reports
     */
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

    /**
     * Gets a drill-through report with a specific name. Note that the current implementation of this method hits the
     * drill-through report list endpoint and filters based on the name, so you will likely end up with two trips to the
     * server: one to get the list, and one to get the details of the report, which will happen when you access most
     * detailed properties in the drill-through report.
     *
     * @param drillthroughName the name of the drill-through report
     * @return a drill-through report object
     */
    public EssDrillthrough getDrillthrough(String drillthroughName) {
        for (EssDrillthrough drillthrough : getDrillthroughs()) {
            if (drillthrough.getName().equals(drillthroughName)) {
                return drillthrough;
            }
        }
        throw new NoSuchEssbaseObjectException(drillthroughName, Type.DRILLTHROUGH);
    }

    /**
     * Gets the list of scenarios on the cube, if any.
     *
     * @return the cube's scenarios
     */
    public List<EssScenario> getScenarios() {
        try {
            ScenarioCollectionResponse response = api.getScenariosApi().scenariosGetScenarios(null, null, false, null, getApplication().getName(), getName(), null, null, null, null, false);
            List<EssScenario> scenarios = new ArrayList<>();
            for (ScenarioBean scenarioBean : wrap(response.getItems())) {
                EssScenario scenario = new EssScenario(api, this, scenarioBean);
                scenarios.add(scenario);
            }
            return Collections.unmodifiableList(scenarios);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * Gets a reference to a particular member in the cube outline. This method is likely to change in the future
     * as the outline management and other classes come in to focus, to say nothing of how member IDs and unique
     * members and such will need to be handled.
     *
     * @param memberName the name of the member
     * @return the member
     */
    public EssMember getMember(String memberName) {
        try {
            MemberBean memberBean = api.getOutlineViewerApi().outlineGetMemberInfo(getApplicationName(), cube.getName(), memberName, null);
            return new EssMember(api, this, memberBean);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * Gets the list of dimensions on the cube, if any.
     * @return the cube's dimensions
     */
    public List<EssDimension> getDimensions() {
        try {
            DimensionList dimensionList = api.getDimensionsApi().dimensionsListDimensions(application.getName(), cube.getName());
            List<EssDimension> dimensions = new ArrayList<>();
            if (dimensionList.getItems() != null) {
                for (DimensionBean dimensionBean : dimensionList.getItems()) {
                    EssDimension dimension = new EssDimension(api, application, this, dimensionBean);
                    dimensions.add(dimension);
                }
            }
            return Collections.unmodifiableList(dimensions);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * List Locked Objects Returns all the locked objects from the specified application and database
     * @param offset Number of items to omit from the start of the result set. Default value is 0
     * @param limit Maximum number of objects to return. Default is 50
     * @return LockObjectLis
     */
    public List<EssLock> getLockedObjects(Integer offset, Integer limit) {
        try {
            LockObjectList lockObjectLists = api.getLocksApi().locksGetLockedObjects(application.getName(), cube.getName(), offset, limit);
            List<EssLock> locks = new ArrayList<>();
            List<LockObject> lockObject = lockObjectLists.getItems();
            if (Objects.nonNull(lockObject)) {
                for (LockObject object : lockObject) {
                    EssLock lock = new EssLock(api, object);
                    locks.add(lock);
                }
            }
            return locks;
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Unlock Object Unlocks the object in the specified application and database
     * @param lockedObject Details about object to be unlocked
     */
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

    /**
     * Lock Object Locks the object in the specified application and database and returns the details of the locked object
     * @param unlockedObject Object details to be locked (required)
     */
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
     * Exports this cube to an Excel workbook.
     */
    public void exportExcel() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(getApplicationName());
        job.setDb(getName());
        job.setJobtype(EssJob.JobType.EXPORT_EXCEL.getParam());

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

    /**
     * Updates this cube using an Excel workbook. This API is likely to change soon to take an EssFile reference
     * @param path the path
     * @param filename the filename
     */
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

    /**
     * Acts as a convenience function for uploading a file to a cube's storage folder. The Essbase server maps the file
     * location <code>/applications/AppName/CubeName</code>, which contains the cube files such as calc scripts, load fules,
     * and whatnot.
     *
     * @param file the file to upload to the cube
     */
    public void importFile(File file) {
        EssFolder folder = new EssFolder(api, getApplication().getServer(), getName(), String.format("applications/%s/%s", getApplicationName(), getName()));
        folder.uploadFile(file);
    }

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

    public EssGrid executeMdx(String query) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        executeMdx(query, MdxOutputType.JSON, new MdxOptions(), outputStream);

        String json = new String(outputStream.toString());
        MdxJson mdxJson = new Gson().fromJson(json, MdxJson.class);
        return new EssGrid(mdxJson);
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

    public enum MdxOutputType {

        JSON, HTML, XLSX, CSV;

        public boolean isPrintable() {
            return this != XLSX;
        }

    }

    public enum MdxIdentifierType {
        NAME, ALIAS, UNIQUE_NAME
    }

}