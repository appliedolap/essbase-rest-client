package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.appliedolap.essbase.util.Utils.wrap;

/**
 * Represents an Essbase cube on the server.
 */
public class EssCube extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssCube.class);

    private final EssApplication application;

    private final Cube cube;

    EssCube(ApiContext api, EssApplication application, Cube cube) {
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
    public List<EssVariable> getVariables() {
        try {
            VariableList variables = api.getVariablesApi().variablesListVariables(application.getName(), cube.getName());
            List<EssVariable> cubeVariables = new ArrayList<>();
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
    public void createDrillthroughURL(String urlName, String urlLink, List<String> drillRegions) {
        WrapperUtil.wrap(() -> {
            DrillthroughBean drillthroughBean = new DrillthroughBean();
            drillthroughBean.setName(urlName);
            drillthroughBean.setType("URL");
            drillthroughBean.setUrl(urlLink);
            drillthroughBean.setDrillableRegions(drillRegions);
            api.getDrillThroughReportsApi().drillThroughReportsCreate(getApplicationName(), this.getName(), drillthroughBean);
        });
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
                EssDrillthrough essDrillthrough = new EssDrillthrough(api, this, reportBean);
                essDrillthroughs.add(essDrillthrough);
            }
            return Collections.unmodifiableList(essDrillthroughs);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
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
     * Exports this cube to an Excel workbook.
     */
    public void exportExcel() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(getApplicationName());
        job.setDb(getName());
        job.setJobtype("exportExcel");

        ParametersBean params = new ParametersBean();
        params.dataLevel("ALL_DATA");
        params.columnFormat("false");
        params.compress("false");
        job.setParameters(params);

        try {
            logger.info("Submitting job for {}.{} for Excel export", getApplicationName(), getName());
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
        //params.catalogExcelPath(String.format("/applications/%s/%s", getApplicationName(), getName()));
        //params.catalogExcelPath(String.format("/users/admin/%s", getApplicationName(), getName()));
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

}