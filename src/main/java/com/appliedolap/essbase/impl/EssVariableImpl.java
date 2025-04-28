package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssVariable;
import com.appliedolap.essbase.client.model.Variable;
import com.appliedolap.essbase.util.WrapperUtil;

/**
 * A variable on the Essbase server. This may be a server variable (this object), an application variable, or a
 * cube variable.
 */
public class EssVariableImpl extends AbstractEssObject implements EssVariable {

    private final Variable variable;

    public EssVariableImpl(ApiContext api, Variable variable) {
        super(api);
        this.variable = variable;
    }

    @Override
    public Scope getScope() {
        return Scope.SERVER;
    }

    @Override
    public String getName() {
        return variable.getName();
    }

    @Override
    public Type getType() {
        return Type.VARIABLE;
    }

    @Override
    public String getValue() {
        return variable.getValue();
    }

    @Override
    public void delete() {
        WrapperUtil.wrap(() -> api.getServerVariablesApi().variablesDeleteServerVariable(variable.getName()));
    }

}