package com.appliedolap.essbase;

import java.util.List;

public interface EssDimension extends EssObject {

    /**
     * Gets the name of the dimension.
     *
     * @return the name of this dimension
     */
    @Override
    String getName();

    @Override
    Type getType();

    /**
     * Gets the list of generations on the dimension, if any.
     *
     * @return the dimension's generations.
     */
    List<EssGeneration> listDimGenerations();

    /**
     * Gets the generation on the dimension, if any.
     *
     * @param generationNumber the generation number
     * @return the dimension's generation.
     */
    EssGeneration getGeneration(Integer generationNumber);

    /**
     * Gets the level on the dimension, if any.
     *
     * @param levelNumber the level number
     * @return the dimension's level.
     */
    EssLevel getLevel(Integer levelNumber);

    /**
     * Gets the list of levels on the dimension, if any.
     *
     * @return the dimension's levels.
     */
    List<EssLevel> getLevels();

}