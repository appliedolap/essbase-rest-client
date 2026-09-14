package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssApplicationVariable;
import com.appliedolap.essbase.client.model.Variable;
import com.appliedolap.essbase.util.WrapperUtil;

/**
 * A variable defined on an application.
 *
 * <p>Its cubes can see it when they run, but it will not appear in any cube's own variable list -
 * that endpoint reports only what the cube itself defines. {@code EssCube.getEffectiveVariables()}
 * is what puts the two together.
 */
public class EssApplicationVariableImpl extends EssVariableImpl implements EssApplicationVariable {

    private final EssApplication application;

    EssApplicationVariableImpl(ApiContext api, EssApplication application, Variable variable) {
        super(api, variable);
        this.application = application;
    }

    @Override
    public Scope getScope() {
        return Scope.APPLICATION;
    }

    @Override
    public EssApplication getApplication() {
        return application;
    }

    @Override
    protected Variable edit(Variable edited) {
        return WrapperUtil.doWithWrap(() -> api.getVariablesApi()
                .variablesEditAppVariable(application.getName(), getName(), edited));
    }

    @Override
    public void delete() {
        WrapperUtil.wrap(() -> api.getVariablesApi()
                .variablesDeleteAppVariable(application.getName(), getName()));
    }

}
