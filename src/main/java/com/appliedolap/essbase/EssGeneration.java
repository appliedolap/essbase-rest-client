package com.appliedolap.essbase;

public interface EssGeneration extends EssObject {

    /**
     * Gets the name of the generation.
     *
     * @return the name of this generation
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the actual name of the generation
     *
     * @return the actual name of this generation.
     */
    String getActualName();

    /**
     * Gets the number of the generation
     *
     * @return the number of this generation.
     */
    Integer getNumber();

    /**
     * Gets the unique setting of the generation
     *
     * @return the unique setting of this generation.
     */
    Boolean getUnique();

}