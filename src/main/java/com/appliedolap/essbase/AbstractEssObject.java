package com.appliedolap.essbase;

/**
 * Parent class for all objects in this library's object hierarchy.
 */
public abstract class AbstractEssObject implements EssObject {

    protected final ApiContext api;

    protected AbstractEssObject(ApiContext api) {
        this.api = api;
    }

}