package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.Variable;
import com.appliedolap.essbase.util.WrapperUtil;

/**
 * A variable on the Essbase server. May be a server variable (this object), an application variable, or a cube variable.
 */
public class EssVariable extends EssObject {

    private final Variable variable;

    EssVariable(ApiContext api, Variable variable) {
        super(api);
        this.variable = variable;
    }

    /**
     * Gets the scope of this variable.
     *
     * @return the variable scope
     */
    public Scope getScope() {
        return Scope.SERVER;
    }

    /**
     * Gets the name of this variable.
     *
     * @return the name of this variable
     */
    @Override
    public String getName() {
        return variable.getName();
    }

    /**
     * Gets the value of this variable.
     *
     * @return the value of this variable
     */
    public String getValue() {
        return variable.getValue();
    }

    /**
     * Deletes the variable.
     */
    public void delete() {
        WrapperUtil.wrap(() -> api.getServerVariablesApi().variablesDeleteServerVariable(variable.getName()));
    }

    /**
     * Models variable scope.
     */
    public enum Scope {

        SERVER, APPLICATION, CUBE

    }

}