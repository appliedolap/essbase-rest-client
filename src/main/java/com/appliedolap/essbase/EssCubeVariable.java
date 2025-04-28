package com.appliedolap.essbase;

public interface EssCubeVariable extends EssObject, EssVariable, EssApplicationVariable {

    @Override
    Scope getScope();

    EssCube getCube();

}