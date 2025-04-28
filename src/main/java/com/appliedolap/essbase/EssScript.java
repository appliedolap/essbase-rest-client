package com.appliedolap.essbase;

public interface EssScript extends EssObject {

    Long getModifiedTime();

    Long getSize();

    String getContent();

    void delete();

    /**
     * Create a job to execute this script (assumes calc for now)
     */
    void execute();

}