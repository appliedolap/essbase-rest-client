package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.Datasource;

/**
 * A data source object on the Essbase server, which seems to be a general abstraction for a tabular data
 * source, such as a SQL table, MDX result set, CSV file, and others.
 */
public class EssDataSource extends EssObject {

    public static final String DELIMITER_SPACE = " ";

    public static final String DELIMITER_TAB = "\t";

    private final EssServer server;

    private final Datasource datasource;

    EssDataSource(ApiContext api, EssServer server, Datasource datasource) {
        super(api);
        this.server = server;
        this.datasource = datasource;
    }

    /**
     * Gets the name of this data source.
     *
     * @return the data source name
     */
    @Override
    public String getName() {
        return datasource.getName();
    }

    @Override
    public Type getType() {
        return Type.DATASOURCE;
    }

}