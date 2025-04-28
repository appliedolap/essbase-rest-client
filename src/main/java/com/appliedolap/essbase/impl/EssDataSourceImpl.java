package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssDataSource;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.client.model.Datasource;

/**
 * A data source object on the Essbase server, which seems to be a general abstraction for a tabular data
 * source, such as a SQL table, MDX result set, CSV file, and others.
 */
public class EssDataSourceImpl extends AbstractEssObject implements EssDataSource {

    private final EssServer server;

    private final Datasource datasource;

    public EssDataSourceImpl(ApiContext api, EssServer server, Datasource datasource) {
        super(api);
        this.server = server;
        this.datasource = datasource;
    }

    @Override
    public String getName() {
        return datasource.getName();
    }

    @Override
    public Type getType() {
        return Type.DATASOURCE;
    }

}