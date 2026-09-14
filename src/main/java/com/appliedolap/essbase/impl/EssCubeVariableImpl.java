package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeVariable;
import com.appliedolap.essbase.client.model.Variable;
import com.appliedolap.essbase.util.WrapperUtil;

/**
 * A variable defined on one cube, which shadows an application or server variable of the same name
 * for anything running against that cube.
 */
public class EssCubeVariableImpl extends EssVariableImpl implements EssCubeVariable {

    private final EssCube cube;

    EssCubeVariableImpl(ApiContext api, EssCube cube, Variable variable) {
        super(api, variable);
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

    @Override
    public EssApplication getApplication() {
        return cube.getApplication();
    }

    @Override
    protected Variable edit(Variable edited) {
        return WrapperUtil.doWithWrap(() -> api.getVariablesApi()
                .variablesEditVariable(cube.getApplication().getName(), cube.getName(), getName(), edited));
    }

    @Override
    public void delete() {
        WrapperUtil.wrap(() -> api.getVariablesApi()
                .variablesDeleteVariable(cube.getApplication().getName(), cube.getName(), getName()));
    }

}
