package com.appliedolap.essbase;

/**
 * Parent class for all objects in this library's object hierarchy.
 */
public abstract class AbstractEssObject {

    protected final ApiContext api;

    protected AbstractEssObject(ApiContext api) {
        this.api = api;
    }

    /**
     * Gets the name of this object. This will generally be the "nice" and "unique" name of this object, such
     * as the application name, cube, member, variable, or whatever.
     *
     * @return the simple name of this object
     */
    public abstract String getName();

    /**
     * Gets the type of this object.
     *
     * @return the object type
     */
    public abstract Type getType();

    /**
     * Object types for the Essbase REST API.
     */
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
        USER,
        UTILITY,
        VARIABLE,
        DIMENSION,
        GENERATION,
        LEVEL,
        LOCK
    }

}