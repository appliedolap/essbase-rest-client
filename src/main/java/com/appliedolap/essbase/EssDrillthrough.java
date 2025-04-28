package com.appliedolap.essbase;

import java.util.List;

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
     * The drill-through report type
     */
    enum DrillthroughType {

        // URL type seems to have nulls for: dataSourceName, columnMapping, columns, parameterMapping
        DATASOURCE,

        URL

    }

}