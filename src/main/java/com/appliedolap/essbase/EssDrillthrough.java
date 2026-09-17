package com.appliedolap.essbase;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EssDrillthrough {

    /**
     * Returns the cube that owns this drill-through object.
     *
     * @return the parent cube for this drill-through definition
     */
    EssCube getCube();

    /**
     * Gets the name of this drill-through report.
     *
     * @return the drill-through name
     */
    String getName();

    EssObject.Type getType();

    /**
     * Gets the URL of this drill-through report (URL-style only!). This API will likely change soon as support for
     * different drill-through types is built out.
     *
     * @return the drill URL if there is one, null otherwise, such as on data source reports
     */
    String getUrl();

    /**
     * Gets the type of report, which will be either <code>DATASOURCE</code> or <code>URL</code>. This method causes a
     * fetch to the drill-through report definition endpoint if it hasn't already been fetched.
     *
     * @return the type
     * @see EssObject.Type
     */
    DrillthroughType getDrillthroughType();

    /**
     * Sets the drill (again, likely to change soon)
     *
     * @param url the drill URL
     */
    void setUrl(String url);

    /**
     * Get the drillable regions.
     *
     * @return the list of drillable regions
     */
    List<String> getDrillableRegions();

    /**
     * Sets the drillable regions for this report
     *
     * @param drillableRegions the drillable regions
     */
    void setDrillableRegions(List<String> drillableRegions);

    /**
     * Gets the useTempTables parameter for this report which is only valid using Oracle DB.
     *
     * @return the use temp tables setting
     */
    boolean isUseTempTables();

    /**
     * Sets the useTempTables parameter for this report which is only valid using Oracle DB.
     *
     * @param useTempTables the use temp tables setting
     */
    void setUseTempTables(boolean useTempTables);

    /**
     * Gets the name of the data source for this report.
     *
     * @return the name of the data source.
     */
    String getDataSourceName();

    /**
     * Sets the name of the data source for this report.
     *
     * @param dataSourceName the name of the data source
     */
    void setDataSourceName(String dataSourceName);

    /**
     * Saves updates to this report
     */
    void save();

    /**
     * Deletes this drill-through report
     */
    void delete();

    /**
     * The source columns this report returns, for a datasource report.
     *
     * @return the column names, or null on a URL report, which has none
     */
    List<String> getColumns();

    /**
     * Sets the source columns this report returns.
     *
     * @param columns the column names, in the order they should come back
     */
    void setColumns(List<String> columns);

    /**
     * Which source column is filtered by which cube dimension, keyed by column name.
     *
     * <p>This is what makes a datasource report a drill-through rather than a query: the drilled
     * cell's members become the filter on these columns.
     *
     * @return the mappings, empty on a URL report
     */
    Map<String, EssDrillthroughColumnMapping> getColumnMappings();

    /**
     * Sets the column-to-dimension mappings.
     *
     * @param columnMappings the mappings, keyed by source column name
     */
    void setColumnMappings(Map<String, EssDrillthroughColumnMapping> columnMappings);

    /**
     * Runs this report for a drilled cell and returns the detail behind it.
     *
     * <p>Executed by the server rather than assembled here, which is the difference that matters: the
     * drillable regions, the column mappings, the runtime parameters and the temporary-table setting
     * are all honoured, because Essbase is running its own report rather than this code guessing at
     * what that report means.
     *
     * <p>Each dimension carries a set of members rather than one, because a drill can come from a
     * selected range of cells and not only a single intersection.
     *
     * @param pov the drilled cell, as dimension to the members selected in it
     * @return the detail rows
     * @throws EssApiException if the report is a URL report, which the server cannot execute - there
     *         is nothing to run, only a URL for a client to open
     */
    EssDrillthroughResult execute(Map<String, Set<String>> pov);

    /**
     * Runs this report for a drilled cell, resolving member names through an alias table.
     *
     * @param pov the drilled cell, as dimension to the members selected in it
     * @param aliasTable the alias table the member names are expressed in, or null for none
     * @return the detail rows
     */
    EssDrillthroughResult execute(Map<String, Set<String>> pov, String aliasTable);

    /**
     * The drill-through report type
     */
    enum DrillthroughType {

        // URL type seems to have nulls for: dataSourceName, columnMapping, columns, parameterMapping
        DATASOURCE,

        URL

    }

}