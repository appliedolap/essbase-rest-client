package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssServerVariable;
import com.appliedolap.essbase.client.model.Variable;
import com.appliedolap.essbase.util.WrapperUtil;

/**
 * A variable defined on the server itself.
 */
public class EssServerVariableImpl extends EssVariableImpl implements EssServerVariable {

    private final EssServer server;

    EssServerVariableImpl(ApiContext api, EssServer server, Variable variable) {
        super(api, variable);
        this.server = server;
    }

    @Override
    public Scope getScope() {
        return Scope.SERVER;
    }

    @Override
    public EssServer getServer() {
        return server;
    }

    @Override
    protected Variable edit(Variable edited) {
        return WrapperUtil.doWithWrap(() -> api.getServerVariablesApi()
                .variablesEditServerVariable(getName(), edited));
    }

    @Override
    public void delete() {
        WrapperUtil.wrap(() -> api.getServerVariablesApi().variablesDeleteServerVariable(getName()));
    }

}
