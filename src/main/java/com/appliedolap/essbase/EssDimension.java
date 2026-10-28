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
     * Whether the dimension is dense or sparse, the storage setting the classic Java API reports as
     * {@code IEssDimension.getStorageType()}.
     *
     * @return the storage type, or null if the server reported one this doesn't name
     * @since 2.1.0
     */
    StorageType getStorageType();

    /**
     * How many members the dimension has, stored or not.
     *
     * @return the member count, or null if the server didn't report it
     * @since 2.1.0
     */
    Integer getMemberCount();

    /**
     * How many of the dimension's members are stored. An attribute dimension stores none, and shared
     * and dynamic members don't count.
     *
     * @return the stored member count, or null if the server didn't report it
     * @since 2.1.0
     */
    Integer getStoredMemberCount();

    /**
     * Whether a dimension is dense or sparse.
     *
     * @since 2.1.0
     */
    enum StorageType {
        DENSE, SPARSE;

        /**
         * Reads the server's name for a storage type.
         *
         * @param name the name, such as {@code DENSE}
         * @return the storage type, or null for a name this doesn't know
         */
        public static StorageType parse(String name) {
            for (StorageType type : values()) {
                if (type.name().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }
    }

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