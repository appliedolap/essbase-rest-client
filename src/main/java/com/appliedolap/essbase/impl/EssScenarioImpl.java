package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssScenario;
import com.appliedolap.essbase.client.model.ScenarioBean;

/**
 * Models a particular scenario in a scenarios-enabled Essbase cube.
 */
public class EssScenarioImpl extends AbstractEssObject implements EssScenario {

    private final EssCube cube;

    private final ScenarioBean scenarioBean;

    EssScenarioImpl(ApiContext api, EssCube cube, ScenarioBean scenarioBean) {
        super(api);
        this.cube = cube;
        this.scenarioBean = scenarioBean;
    }

    @Override
    public String getName() {
        return scenarioBean.getName();
    }

    @Override
    public Type getType() {
        return Type.SCENARIO;
    }

    @Override
    public EssCube getCube() {
        return cube;
    }

}