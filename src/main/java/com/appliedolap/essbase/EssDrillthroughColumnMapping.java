package com.appliedolap.essbase;

/**
 * Ties one source column of a drill-through report to a cube dimension.
 *
 * <p>Its own type rather than the generated {@code ColumnMappingInfo} so that the generated client
 * does not leak through the domain model - the same reason every other model here is wrapped.
 */
public final class EssDrillthroughColumnMapping {

    private final String dimension;

    private final MappingType type;

    private final String generation;

    private final String level;

    private final Integer generationNumber;

    public EssDrillthroughColumnMapping(String dimension, MappingType type) {
        this(dimension, type, null, null, null);
    }

    public EssDrillthroughColumnMapping(String dimension, MappingType type, String generation,
            String level, Integer generationNumber) {
        this.dimension = dimension;
        this.type = type;
        this.generation = generation;
        this.level = level;
        this.generationNumber = generationNumber;
    }

    public String getDimension() {
        return dimension;
    }

    public MappingType getMappingType() {
        return type;
    }

    public String getGeneration() {
        return generation;
    }

    public String getLevel() {
        return level;
    }

    public Integer getGenerationNumber() {
        return generationNumber;
    }

    @Override
    public String toString() {
        return dimension + " (" + type + ")";
    }

    /** How the cell's members are matched against the column. */
    public enum MappingType {

        DIMENSION,

        GENERATION,

        LEVEL0,

        PARENT_CHILD

    }

}
