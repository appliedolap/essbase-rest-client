package com.appliedolap.essbase;

public interface EssScenario extends EssObject {

    /**
     * Gets the name of this scenario.
     *
     * @return the scenario name
     */
    @Override
    String getName();

    @Override
    Type getType();

    EssCube getCube();

}