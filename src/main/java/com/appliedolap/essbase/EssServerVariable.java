package com.appliedolap.essbase;

/**
 * A substitution variable defined on the server, visible to every application and cube on it unless
 * one of them defines the same name.
 */
public interface EssServerVariable extends EssVariable {

    /**
     * @return the server this variable is defined on
     */
    EssServer getServer();

}
