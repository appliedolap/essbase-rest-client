package com.appliedolap.essbase;

/**
 * A substitution variable defined on an application, visible to its cubes unless one of them defines
 * the same name, and invisible to every other application.
 */
public interface EssApplicationVariable extends EssVariable {

    /**
     * @return the application this variable is defined on
     */
    EssApplication getApplication();

}
