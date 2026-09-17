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

    /**
     * The columns this data source exposes, in the order the server lists them.
     *
     * <p>Fetches the data source in full the first time it is asked, because the listing does not carry
     * columns - {@code GET /datasources} answers name, type and connection and nothing more, while
     * {@code GET /datasources/{name}} answers the whole thing.
     *
     * @return the columns, empty where the server reports none
     */
    java.util.List<EssDataSourceColumn> getColumns();

}