package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.model.ScenarioBean;

public class EssScenario extends EssObject {

    private final EssCube cube;

    private final ScenarioBean scenarioBean;

    public EssScenario(ApiClient client, EssCube cube, ScenarioBean scenarioBean) {
        super(client);
        this.cube = cube;
        this.scenarioBean = scenarioBean;
    }

    @Override
    public String getName() {
        return scenarioBean.getName();
    }

}