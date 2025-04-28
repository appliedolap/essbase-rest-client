package com.appliedolap.essbase;

public interface EssLevel extends EssObject {

    /**
     * Gets the name of the level.
     *
     * @return the name of this level
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the number of the level.
     *
     * @return the number of the level.
     */
    Integer getNumber();

}