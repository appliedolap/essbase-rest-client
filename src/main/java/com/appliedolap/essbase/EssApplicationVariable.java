package com.appliedolap.essbase;

import com.appliedolap.essbase.impl.EssVariableImpl;

public interface EssApplicationVariable extends EssObject, EssVariable {

    /**
     * Gets the scope of this variable. For this type of object the scope will always be at least to the application
     * level but if using this instance via the {@link EssVariableImpl} interface, callers may need/want to check the
     * scope and cast to {@link Scope#CUBE} (or application scope) as needed, if necessary to get a reference to the
     * owning cube/application.
     *
     * @return the scope of this variable
     */
    Scope getScope();

    /**
     * Gets the owning application for this variable.
     *
     * @return the application this variable is associated with
     */
    EssApplication getApplication();

}