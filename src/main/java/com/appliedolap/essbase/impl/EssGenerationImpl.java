package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssGeneration;
import com.appliedolap.essbase.client.model.GenerationLevel;

/**
 * Special Type of Member that represents a Generation
 */
public class EssGenerationImpl extends AbstractEssObject implements EssGeneration {

    private final GenerationLevel generationLevel;

    public EssGenerationImpl(ApiContext api, GenerationLevel generationLevel) {
        super(api);
        this.generationLevel = generationLevel;
    }

    @Override
    public String getName() {
        return generationLevel.getName();
    }

    @Override
    public Type getType() {
        return Type.GENERATION;
    }

    @Override
    public String getActualName() {
        return generationLevel.getActualName();
    }

    @Override
    public Integer getNumber() {
        return generationLevel.getNumber();
    }

    @Override
    public Boolean getUnique() {
        return generationLevel.getUnique();
    }
}