package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssApplication;
import com.appliedolap.essbase.EssApplicationVariable;
import com.appliedolap.essbase.client.model.Variable;

/**
 * Represents a variable that is associated with a particular application. Seemingly unlike the original Essbase API,
 * these variables are ostensibly applicable for individual child cubes but do not show up as actual variables in the
 * {@link EssCubeImpl#getVariables()} list.
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

}