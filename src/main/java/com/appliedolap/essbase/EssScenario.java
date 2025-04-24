package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.ScenarioBean;

/**
 * Models a particular scenario in a scenarios-enabled Essbase cube.
 */
public class EssScenario extends AbstractEssObject {

    private final EssCube cube;

    private final ScenarioBean scenarioBean;

    EssScenario(ApiContext api, EssCube cube, ScenarioBean scenarioBean) {
        super(api);
        this.cube = cube;
        this.scenarioBean = scenarioBean;
    }

    /**
     * Gets the name of this scenario.
     *
     * @return the scenario name
     */
    @Override
    public String getName() {
        return scenarioBean.getName();
    }

    @Override
    public Type getType() {
        return Type.SCENARIO;
    }

    public EssCube getCube() {
        return cube;
    }

}