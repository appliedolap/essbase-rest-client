package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.Variable;

/**
 * Represents a variable that is associated with a particular application. Seemingly unlike the original Essbase API,
 * these variables are ostensibly applicable for individual child cubes but do not show up as actual variables in the
 * {@link EssCube#getVariables()} list.
 */
public class EssApplicationVariable extends EssVariable {

    private final EssApplication application;

    EssApplicationVariable(ApiContext api, EssApplication application, Variable variable) {
        super(api, variable);
        this.application = application;
    }

    /**
     * Gets the scope of this variable. For this type of object the scope will always be at least to the application
     * level but if using this instance via the {@link EssVariable} interface, callers may need/want to check the scope
     * and cast to {@link com.appliedolap.essbase.EssVariable.Scope#CUBE} (or application scope) as needed, if necessary
     * to get a reference to the owning cube/application.
     *
     * @return the scope of this variable
     */
    public Scope getScope() {
        return Scope.APPLICATION;
    }

    /**
     * Gets the owning application for this variable.
     *
     * @return the application this variable is associated with
     */
    public EssApplication getApplication() {
        return application;
    }

}