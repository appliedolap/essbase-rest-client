package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.GenerationLevel;

/**
 * Special Type of Member that represents a Level
 */
public class EssLevel extends AbstractEssObject {

    private final GenerationLevel generationLevel;

    public EssLevel(ApiContext api, GenerationLevel generationLevel) {
        super(api);
        this.generationLevel = generationLevel;
    }

    /**
     * Gets the name of the level.
     *
     * @return the name of this level
     */
    @Override
    public String getName() {
        return generationLevel.getName();
    }

    @Override
    public Type getType() {
        return Type.LEVEL;
    }

    /**
     * Gets the number of the level.
     *
     * @return the number of the level.
     */
    public Integer getNumber() {
        return generationLevel.getNumber();
    }
}