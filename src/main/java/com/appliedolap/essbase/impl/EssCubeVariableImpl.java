package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeVariable;
import com.appliedolap.essbase.client.model.Variable;

/**
 * A variable that is specific to a particular cube.
 */
public class EssCubeVariableImpl extends EssApplicationVariableImpl implements EssCubeVariable {

    private final EssCube cube;

    EssCubeVariableImpl(ApiContext api, EssCube cube, Variable variable) {
        super(api, cube.getApplication(), variable);
        this.cube = cube;
    }

    @Override
    public Scope getScope() {
        return Scope.CUBE;
    }

    @Override
    public EssCube getCube() {
        return cube;
    }

}