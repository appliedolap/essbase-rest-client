package com.appliedolap.essbase;

public interface EssDataSource extends EssObject {

    String DELIMITER_SPACE = " ";

    String DELIMITER_TAB = "\t";

    /**
     * Gets the name of this data source.
     *
     * @return the data source name
     */
    @Override
    String getName();

    @Override
    Type getType();

}