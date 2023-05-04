package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.DimensionBean;
import com.appliedolap.essbase.client.model.GenerationLevel;
import com.appliedolap.essbase.client.model.GenerationLevelList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A special type of member that represents a dimension.
 */
public class EssDimension extends EssObject {

    private final DimensionBean dimensionBean;

    private final EssApplication application;

    private final EssCube cube;

    public EssDimension(ApiContext api, EssApplication application, EssCube cube, DimensionBean dimensionBean) {
        super(api);
        this.application = application;
        this.cube = cube;
        this.dimensionBean = dimensionBean;
    }

    /**
     * Gets the name of the dimension.
     *
     * @return the name of this dimension
     */
    @Override
    public String getName() {
        return dimensionBean.getName();
    }

    @Override
    public Type getType() {
        return Type.DIMENSION;
    }

    /**
     * Gets the list of generations on the dimension, if any.
     *
     * @return the dimension's generations.
     */
    public List<EssGeneration> listDimGenerations() {
        try {
            GenerationLevelList generationLevelList = api.getDimensionsApi().dimensionsListDimGenerations(application.getName(), cube.getName(), getName());
            List<EssGeneration> generations = new ArrayList<>();
            if (Objects.nonNull(generationLevelList) && Objects.nonNull(generationLevelList.getItems())) {
                for (GenerationLevel generationLevel : generationLevelList.getItems()) {
                    EssGeneration generation = new EssGeneration(api, generationLevel);
                    generations.add(generation);
                }
            }
            return Collections.unmodifiableList(generations);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Gets the generation on the dimension, if any.
     *
     * @param generationNumber the generation number
     * @return the dimension's generation.
     */
    public EssGeneration getGeneration(Integer generationNumber) {
        try {
            GenerationLevel generationLevel = api.getDimensionsApi().dimensionsGetDimGenerations(application.getName(), cube.getName(), getName(), generationNumber);
            return new EssGeneration(api, generationLevel);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

//    /**
//     * Update The Generation with the specified generationNumber and EssGeneration body
//     *
//     * @param generationNumber generaitonNumber
//     * @param generation       body of EssGeneration
//     * @return updated EssGeneration object
//     * /
//    public EssGeneration updateGeneration(Integer generationNumber, EssGeneration generation) {
//        try {
//            api.getDimensionsApi().dimensionsEditDimGenerations(application.getName(), cube.getName(), getName(), generationNumber, generation.getGenerationLevel());
//            return generation;
//        } catch (ApiException e) {
//            throw new EssApiException(e);
//        }
//    }

    /**
     * Gets the level on the dimension, if any.
     *
     * @param levelNumber the level number
     * @return the dimension's level.
     */
    public EssLevel getLevel(Integer levelNumber) {
        try {
            GenerationLevel generationLevel = api.getDimensionsApi().dimensionsGetDimLevels(application.getName(), cube.getName(), getName(), levelNumber);
            return new EssLevel(api, generationLevel);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * update the level with the specified level number and EssLevel body
     * @param levelNumber the level number.
     * @param level EssLevel body.
     * @return updated EssLevel.
    public EssLevel updateLevel(Integer levelNumber, EssLevel level) {
        try {
            api.getDimensionsApi().dimensionsEditDimLevels(application.getName(), cube.getName(), getName(), levelNumber, level.getGenerationLevel());
            return level;
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }*/

    /**
     * Gets the list of levels on the dimension, if any.
     *
     * @return the dimension's levels.
     */
    public List<EssLevel> getLevels() {
        try {
            GenerationLevelList generationLevelList = api.getDimensionsApi().dimensionsListDimLevels(application.getName(), cube.getName(), getName());
            List<EssLevel> levels = new ArrayList<>();
            if (Objects.nonNull(generationLevelList) && Objects.nonNull(generationLevelList.getItems())) {
                for (GenerationLevel generationLevel : generationLevelList.getItems()) {
                    EssLevel level = new EssLevel(api, generationLevel);
                    levels.add(level);
                }
            }
            return Collections.unmodifiableList(levels);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

}