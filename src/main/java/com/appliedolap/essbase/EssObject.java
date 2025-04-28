package com.appliedolap.essbase;

public interface EssObject {

    /**
     * Gets the name of this object. This will generally be the "nice" and "unique" name of this object, such as the
     * application name, cube, member, variable, or whatever.
     *
     * @return the simple name of this object
     */
    String getName();

    /**
     * Gets the type of this object.
     *
     * @return the object type
     */
    Type getType();

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