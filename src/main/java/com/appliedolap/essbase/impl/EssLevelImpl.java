package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssLevel;
import com.appliedolap.essbase.client.model.GenerationLevel;

/**
 * Special Type of Member that represents a Level
 */
public class EssLevelImpl extends AbstractEssObject implements EssLevel {

    private final GenerationLevel generationLevel;

    public EssLevelImpl(ApiContext api, GenerationLevel generationLevel) {
        super(api);
        this.generationLevel = generationLevel;
    }

    @Override
    public String getName() {
        return generationLevel.getName();
    }

    @Override
    public Type getType() {
        return Type.LEVEL;
    }

    @Override
    public Integer getNumber() {
        return generationLevel.getNumber();
    }
}