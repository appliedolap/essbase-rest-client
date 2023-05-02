package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.DimensionBean;
import com.appliedolap.essbase.client.model.GenerationLevel;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Special Type of Member that represents a Generation
 */
public class EssGeneration extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssGeneration.class);

    private final GenerationLevel generationLevel;

    public EssGeneration(ApiContext api, GenerationLevel generationLevel) {
        super(api);
        this.generationLevel = generationLevel;
    }

    /**
     * Gets the name of the generation.
     *
     * @return the name of this generation
     */
    @Override
    public String getName() {
        return generationLevel.getName();
    }

    @Override
    public Type getType() {
        return Type.GENERATION;
    }

    /**
     * Gets the actual name of the generation
     *
     * @return the actual name of this generation.
     */
    public String getActualName() {
        return generationLevel.getActualName();
    }

    /**
     * Gets the number of the generation
     *
     * @return the number of this generation.
     */
    public Integer getNumber() {
        return generationLevel.getNumber();
    }

    /**
     * Gets the unique of the generation
     *
     * @return the unique of this generation.
     */
    public Boolean getUnique() {
        return generationLevel.getUnique();
    }
}
