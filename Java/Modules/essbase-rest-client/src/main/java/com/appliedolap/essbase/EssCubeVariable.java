package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.Variable;

/**
 * A variable that is specific to a particular cube.
 */
public class EssCubeVariable extends EssApplicationVariable {

    private final EssCube cube;

    EssCubeVariable(ApiContext api, EssCube cube, Variable variable) {
        super(api, cube.getApplication(), variable);
        this.cube = cube;
    }

    @Override
    public Scope getScope() {
        return Scope.CUBE;
    }

    public EssCube getCube() {
        return cube;
    }

}