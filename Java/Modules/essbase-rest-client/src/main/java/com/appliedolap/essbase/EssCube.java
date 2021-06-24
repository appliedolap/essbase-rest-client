package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.*;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.WrapperUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class EssCube extends EssObject {

    private final ApplicationsApi applicationsApi;

    private final EssApplication application;

    private final ScriptsApi scriptsApi;

    private final SessionsApi sessionsApi;

    private final OutlineViewerApi outlineViewerApi;

    private final VariablesApi variablesApi;

    private final JobsApi jobsApi;

    private final ScenariosApi scenariosApi;

    private final DrillThroughReportsApi drillThroughReportsApi;

    private final Cube cube;

    public EssCube(ApiClient client, EssApplication application, Cube cube) {
        super(client);
        this.applicationsApi = new ApplicationsApi(client);
        this.scriptsApi = new ScriptsApi(client);
        this.sessionsApi = new SessionsApi(client);
        this.outlineViewerApi = new OutlineViewerApi(client);
        this.variablesApi = new VariablesApi(client);
        this.jobsApi = new JobsApi(client);
        this.scenariosApi = new ScenariosApi(client);
        this.drillThroughReportsApi = new DrillThroughReportsApi(client);
        this.application = application;
        this.cube = cube;
    }

    public String getName() {
        return cube.getName();
    }

    public List<EssScript> getCalcScripts() {
        try {
            // specifies calc, apparently same as null
            ScriptList scriptList = scriptsApi.scriptsListScripts(application.getName(), cube.getName(), "calc");
            List<EssScript> scripts = new ArrayList<>();
            for (Script script : scriptList.getItems()) {
                EssScript essScript = new EssScript(client, this, script);
                scripts.add(essScript);
            }
            return scripts;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public EssApplication getApplication() {
        return application;
    }

    private String getApplicationName() {
        return getApplication().getName();
    }

    public List<EssSession> getSessions() {
        try {
            List<SessionAttributes> sessions = sessionsApi.sessionsGetAllActiveSessions(application.getName(), cube.getName(), null);
            List<EssSession> sessionList = new ArrayList<>();
            for (SessionAttributes sessionAttributes : sessions) {
                EssSession session = new EssSession(client, this, sessionAttributes);
                sessionList.add(session);
            }
            return sessionList;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSessions() {
        // TODO
    }

    public List<EssCubeVariable> getVariables() {
        try {
            System.out.println("Fetching variables");
            List<VariableList> variables = variablesApi.variablesListVariables(application.getName(), cube.getName());

        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public EssOutline getOutline() {
        return new EssOutline(client, this);
    }

    public boolean isScenariosEnabled() {
        try {
            String applicationName = getApplication().getName();
            String cubeName = getName();
            ScenarioCubesList scenarioCubesList = scenariosApi.scenariosGetRegisteredCubes();

            for (ScenarioCubes scenarioCubes : scenarioCubesList.getItems()) {
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
    public void createScenario() {

    }

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
    public void createDrillthroughURL(String urlName, String urlLink, List<String> drillRegions) {
        WrapperUtil.wrap(() -> {
            DrillthroughBean drillthroughBean = new DrillthroughBean();
            drillthroughBean.setName(urlName);
            drillthroughBean.setType("URL");
            drillthroughBean.setUrl(urlLink);
            drillthroughBean.setDrillableRegions(drillRegions);
            drillThroughReportsApi.drillThroughReportsCreate(getApplicationName(), this.getName(), drillthroughBean);
        });
    }

    public List<EssDrillthrough> getDrillthroughs() {
        try {
            ReportList reportList = drillThroughReportsApi.drillThroughReportsGetReports(getApplicationName(), getName());
            List<EssDrillthrough> essDrillthroughs = new ArrayList<>();
            for (ReportBean reportBean : reportList.getItems()) {
                EssDrillthrough essDrillthrough = new EssDrillthrough(client, this, reportBean);
                essDrillthroughs.add(essDrillthrough);
            }
            return essDrillthroughs;
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    public static <T, R> List<R> mapper(List<T> items, Function<T, R> func) {
        List<R> results = new ArrayList<>();
        for (T item : items) {
            R converted = func.apply(item);
            results.add(converted);
        }
        return results;
    }

    public List<EssScenario> getScenarios() {
        try {
            ScenarioCollectionResponse response = scenariosApi.scenariosGetScenarios(null, null, false, null, getApplication().getName(), getName(), null, null, null, null, false);
            List<EssScenario> scenarios = new ArrayList<>();
            for (ScenarioBean scenarioBean : response.getItems()) {
                EssScenario scenario = new EssScenario(client, this, scenarioBean);
                scenarios.add(scenario);
            }
            return scenarios;
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    public EssMember getMember(String memberName) {
        try {
            MemberBean memberBean = outlineViewerApi.outlineGetMemberInfo(getApplicationName(), cube.getName(), memberName, null);
            return new EssMember(client, this, memberBean);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    // getDimensions

    public void exportExcel() {
        JobsInputBean job = new JobsInputBean();
        job.setApplication(application.getName());
        job.setDb(cube.getName());
        job.setJobtype("exportExcel");

        ParametersBean params = new ParametersBean();
        job.setParameters(params);

        try {
            JobRecordBean jobRecord = jobsApi.jobsExecuteJob(job);
            //jobRecord.
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    // build cube from spreadsheet?

    //

}