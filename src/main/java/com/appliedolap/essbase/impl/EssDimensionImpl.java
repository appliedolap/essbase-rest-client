package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
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
public class EssDimensionImpl extends AbstractEssObject implements EssDimension {

    private final DimensionBean dimensionBean;

    private final EssApplication application;

    private final EssCube cube;

    public EssDimensionImpl(ApiContext api, EssApplication application, EssCube cube, DimensionBean dimensionBean) {
        super(api);
        this.application = application;
        this.cube = cube;
        this.dimensionBean = dimensionBean;
    }

    @Override
    public String getName() {
        return dimensionBean.getName();
    }

    @Override
    public Type getType() {
        return Type.DIMENSION;
    }

    @Override
    public List<EssGeneration> listDimGenerations() {
        try {
            GenerationLevelList generationLevelList = api.getDimensionsApi().dimensionsListDimGenerations(application.getName(), cube.getName(), getName());
            List<EssGeneration> generations = new ArrayList<>();
            if (Objects.nonNull(generationLevelList) && Objects.nonNull(generationLevelList.getItems())) {
                for (GenerationLevel generationLevel : generationLevelList.getItems()) {
                    EssGeneration generation = new EssGenerationImpl(api, generationLevel);
                    generations.add(generation);
                }
            }
            return Collections.unmodifiableList(generations);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public EssGeneration getGeneration(Integer generationNumber) {
        try {
            GenerationLevel generationLevel = api.getDimensionsApi().dimensionsGetDimGenerations(application.getName(), cube.getName(), getName(), generationNumber);
            return new EssGenerationImpl(api, generationLevel);
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

    @Override
    public EssLevel getLevel(Integer levelNumber) {
        try {
            GenerationLevel generationLevel = api.getDimensionsApi().dimensionsGetDimLevels(application.getName(), cube.getName(), getName(), levelNumber);
            return new EssLevelImpl(api, generationLevel);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * update the level with the specified level number and EssLevel body
     * @return updated EssLevel.
    public EssLevel updateLevel(Integer levelNumber, EssLevel level) {
        try {
            api.getDimensionsApi().dimensionsEditDimLevels(application.getName(), cube.getName(), getName(), levelNumber, level.getGenerationLevel());
            return level;
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }*/

    @Override
    public List<EssLevel> getLevels() {
        try {
            GenerationLevelList generationLevelList = api.getDimensionsApi().dimensionsListDimLevels(application.getName(), cube.getName(), getName());
            List<EssLevel> levels = new ArrayList<>();
            if (Objects.nonNull(generationLevelList) && Objects.nonNull(generationLevelList.getItems())) {
                for (GenerationLevel generationLevel : generationLevelList.getItems()) {
                    EssLevel level = new EssLevelImpl(api, generationLevel);
                    levels.add(level);
                }
            }
            return Collections.unmodifiableList(levels);
        } catch (ApiException e) {
            throw new EssApiException(e);
        }
    }

}