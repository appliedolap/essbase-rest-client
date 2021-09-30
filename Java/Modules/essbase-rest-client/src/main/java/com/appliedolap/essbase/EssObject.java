package com.appliedolap.essbase;

/**
 * Parent class for all objects in this library's object hierarchy.
 */
public abstract class EssObject {

    protected final ApiContext api;

    protected EssObject(ApiContext api) {
        this.api = api;
    }

    /**
     * Gets the name of this object. This will generally be the "nice" and "unique" name of this object, such
     * as the application name, cube, member, variable, or whatever.
     *
     * @return the simple name of this object
     */
    public abstract String getName();

    //public abstract Type getType();

    public enum Type {
        APPLICATION,
        CUBE,
        DATASOURCE,
        DRILLTHROUGH,
        FILE,
        GROUP,
        JOB,
        MEMBER,
        OUTLINE,
        SCENARIO,
        SCRIPT,
        SERVER,
        SESSION,
        URL,
        UTILITY,
        VARIABLE
    }

}