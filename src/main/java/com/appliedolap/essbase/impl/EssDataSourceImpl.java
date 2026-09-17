package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssDataSource;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssDataSourceColumn;
import com.appliedolap.essbase.client.model.ColumnType;
import com.appliedolap.essbase.client.model.Datasource;
import com.appliedolap.essbase.util.WrapperUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A data source object on the Essbase server, which seems to be a general abstraction for a tabular data
 * source, such as a SQL table, MDX result set, CSV file, and others.
 */
public class EssDataSourceImpl extends AbstractEssObject implements EssDataSource {

    private final EssServer server;

    private Datasource datasource;

    /** So a data source with genuinely no columns is not re-fetched on every read. */
    private boolean fetchedInFull;

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

    @Override
    public List<EssDataSourceColumn> getColumns() {
        if (columnsOf(datasource).isEmpty() && !fetchedInFull) {
            fetchedInFull = true;
            datasource = WrapperUtil.doWithWrap(() -> api.getGlobalDatasourcesApi()
                    .globalDatasourcesGetDatasourceDetails(getName()));
        }
        List<EssDataSourceColumn> columns = new ArrayList<>();
        for (ColumnType column : columnsOf(datasource)) {
            columns.add(new EssDataSourceColumn(column.getName(),
                    column.getType() == null ? null : column.getType().getValue(), column.getIndex()));
        }
        return Collections.unmodifiableList(columns);
    }

    private static List<ColumnType> columnsOf(Datasource datasource) {
        if (datasource == null || datasource.getColumns() == null
                || datasource.getColumns().getColumn() == null) {
            return Collections.emptyList();
        }
        return datasource.getColumns().getColumn();
    }

}