package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.DrillthroughColumnMismatchException;
import com.appliedolap.essbase.util.WrapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.*;

/**
 * Represents a drill-through report on a given cube.
 */
public class EssDrillthroughImpl extends AbstractEssObject implements EssDrillthrough {

    private static final Logger logger = LoggerFactory.getLogger(EssDrillthroughImpl.class);

    private final EssCube cube;

    /**
     * name is final -- the REST API does not honor the name field when updating the report, and it is used as part of
     * the URL. A pseudo-rename looks like it is accomplished via a copy and delete
     */
    private final String name;

    private DrillthroughBean drillthroughBean;

    public EssDrillthroughImpl(ApiContext api, EssCube cube, ReportBean reportBean) {
        super(api);
        this.cube = cube;
        this.name = reportBean.getName();
    }

    @Override
    public EssCube getCube() {
        return cube;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.DRILLTHROUGH;
    }

    @Override
    public String getUrl() {
        return getDrillthroughBean().getUrl();
    }

    @Override
    public DrillthroughType getDrillthroughType() {
        return DrillthroughType.valueOf(getDrillthroughBean().getType());
    }

    @Override
    public void setUrl(String url) {
        getDrillthroughBean().setUrl(url);
    }

    @Override
    public List<String> getDrillableRegions() {
        return getDrillthroughBean().getDrillableRegions();
    }

    @Override
    public void setDrillableRegions(List<String> drillableRegions) {
        getDrillthroughBean().setDrillableRegions(drillableRegions);
    }

    @Override
    public boolean isUseTempTables() {
        // Note: return value tests for null as getUseTempTables may be null and not a valid boolean
        return getDrillthroughBean().getUseTempTables() != null && getDrillthroughBean().getUseTempTables();
    }

    @Override
    public void setUseTempTables(boolean useTempTables) {
        getDrillthroughBean().setUseTempTables(useTempTables);
    }

    @Override
    public String getDataSourceName() {
        // Note: return value tests for null as getUseTempTables may be null and not a valid boolean
        return getDrillthroughBean().getDataSourceName();
    }

    @Override
    public void setDataSourceName(String dataSourceName) {
        getDrillthroughBean().setDataSourceName(dataSourceName);
    }

    /**
     * Gets a list of column names for this report.
     *
     * @return a list of column names for this report.
     */
    public List<String> getColumns() {
        return getDrillthroughBean().getColumns();
    }

    /**
     * Sets the list of column names for this report.
     *
     * @param columns a list of column names for this report
     */
    public void setColumns(List<String> columns) {
        getDrillthroughBean().setColumns(columns);
    }

    /**
     * Gets information about report column mappings.
     *
     * @return information about report column mappings.
     */
    public Map<String, ColumnMapping> getColumnMappings() {
        Map<String, ColumnMappingInfo> columnMappingInfos = getDrillthroughBean().getColumnMapping();

        LinkedHashMap<String, ColumnMapping> columnMapping = new LinkedHashMap<>();

        if (columnMappingInfos != null) {
            for (Map.Entry<String, ColumnMappingInfo> entry : columnMappingInfos.entrySet()) {
                String columnName = entry.getKey();
                ColumnMappingInfo columnMappingInfo = entry.getValue();

                columnMapping.put(columnName, new ColumnMapping(columnName, columnMappingInfo));
            }
        }

        return columnMapping;
    }

    /**
     * Sets a map of column mapping objects to update.
     *
     * @param columnMappings a map of column mapping objects to update
     */
    public void setColumnMappings(Map<String, ColumnMapping> columnMappings) {
        // create a map to pass to the REST API
        Map<String, ColumnMappingInfo> columnMappingInfos = new LinkedHashMap<>();

        // loop the mappings
        for (ColumnMapping columnMapping : columnMappings.values()) {
            // add it to the map
            columnMappingInfos.put(columnMapping.getColumnName(), columnMapping.getColumnMappingInfo());
        }

        // call the server to set the mappings
        getDrillthroughBean().setColumnMapping(columnMappingInfos);
    }


    /**
     * Gets information about parameter mappings.
     *
     * @return information about parameter mappings.
     */
    public Map<String, Parameter> getParameterMappings() {
        Map<String, RunTimeParametersInfo> parametersInfos = getDrillthroughBean().getParameterMapping();

        LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<>();

        if (parametersInfos != null) {
            for (Map.Entry<String, RunTimeParametersInfo> entry : parametersInfos.entrySet()) {
                String key = entry.getKey();
                RunTimeParametersInfo parametersInfo = entry.getValue();

                parameters.put(key, new Parameter(key, parametersInfo));
            }
        }

        return parameters;
    }

    /**
     * Sets a map of parameter mappings to update.
     *
     * @param parameters a map of parameter mappings to update
     */
    public void setRunTimeParameters(Map<String, Parameter> parameters) {
        // create a map to pass to the REST API
        Map<String, RunTimeParametersInfo> parametersInfos = new LinkedHashMap<>();

        // loop the mappings
        for (Parameter parameter : parameters.values()) {
            // add it to the map
            parametersInfos.put(parameter.getKey(), parameter.getRunTimeParametersInfo());
        }

        // call the server to set the mappings
        getDrillthroughBean().setParameterMapping(parametersInfos);
    }

    @Override
    public void save() {
        try {
            api.getDrillThroughReportsApi().drillThroughReportsUpdateReport(cube.getApplication().getName(), cube.getName(), name, drillthroughBean);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * The overall drill-through object is constructed from the "get drill-through reports API" which basically has the
     * name of the report, all other properties must be fetched from the individual report API. This class employs a
     * lazy fetch strategy so that the details are fetched if needed, once, and then subsequently used. Therefore,
     * accesses to the report type and other properties go through this method so that the fetch will happen. This method
     * is intentionally private so that the DrillthroughBean object does not leak out into the public client API.
     *
     * @return the drillthrough bean object (details of the drill-through report)
     */
    private DrillthroughBean getDrillthroughBean() {
        if (drillthroughBean == null) {
            try {
                drillthroughBean = api.getDrillThroughReportsApi().drillThroughReportsGetReport(cube.getApplication().getName(), cube.getName(), getName());
                return drillthroughBean;
            } catch (ApiException apiException) {
                throw new EssApiException(apiException);
            }
        }
        return drillthroughBean;
    }

    @Override
    public void delete() {
        logger.info("Deleting drill-through report {}", getName());
        WrapperUtil.wrap(() -> api.getDrillThroughReportsApi().drillThroughReportsDelete(cube.getApplication().getName(), cube.getName(), getName()));
    }

    /**
     * Executes a drill-through request for the given POV and sends output to the given output stream. The default
     * {@link Options} will be used for output.
     *
     * @param pov a mappings of dimensions to members representing the drilled cell.
     * @param outputStream the stream to output to
     */
    public void run(Map<String, String> pov, OutputStream outputStream) {
        run(pov, outputStream, new Options());
    }

    /**
     * Executes a drill-through request for the given POV and sends output to the given output stream.
     *
     * @param pov a mappings of dimensions to members representing the drilled cell.
     * @param options custom options for output generation
     * @param outputStream the stream to output to
     */
    public void run(Map<String, String> pov, OutputStream outputStream, Options options) {
        if (getDrillthroughType() != DrillthroughType.DATASOURCE) throw new IllegalStateException("Cannot call run on a drill-through report that is not of type " + Type.DATASOURCE);

        // consider the column mappings and pov together and make sure that necessary values are provided.
        Map<String, String> filters = new LinkedHashMap<>();
        Map<String, ColumnMappingInfo> columnMappings = drillthroughBean.getColumnMapping();
        if (columnMappings != null) {
            for (String columnName : columnMappings.keySet()) {
                ColumnMappingInfo columnMappingInfo = columnMappings.get(columnName);
                String dimension = columnMappingInfo.getDimension();
                if (!pov.containsKey(dimension)) {
                    throw new DrillthroughColumnMismatchException(this, columnName, dimension);
                } else {
                    filters.put(columnName, pov.get(dimension));
                }
            }
        }

        // build the query against the data source
        String query = new DataSourceQueryBuilder(drillthroughBean.getDataSourceName())
                .columns(drillthroughBean.getColumns())
                .filter(filters)
                .build();

        logger.info("Drill-through {} execution constructed query: {}", getName(), query);
        cube.getApplication().getServer().streamDataSource(query, options.isIncludeHeaders(), options.getDelimiter(), Collections.emptyMap(), outputStream);
    }

    /**
     * Helper class for building a query against a data source.
     */
    private static class DataSourceQueryBuilder {

        private final String dataSourceName;

        private final List<String> columns;

        private final Map<String, String> columnMappings;

        public DataSourceQueryBuilder(String dataSourceName) {
            this.dataSourceName = dataSourceName;
            this.columns = new ArrayList<>();
            this.columnMappings = new LinkedHashMap<>();
        }

        public DataSourceQueryBuilder columns(List<String> columns) {
            this.columns.addAll(columns);
            return this;
        }

        public DataSourceQueryBuilder filter(String columnName, String value) {
            columnMappings.put(columnName, value);
            return this;
        }

        public DataSourceQueryBuilder filter(Map<String, String> columnNamesToMembers) {
            for (Map.Entry<String, String> entry : columnNamesToMembers.entrySet()) {
                filter(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public String build() {
            StringBuilder sb = new StringBuilder("SELECT ");
            String allColumnNames = StringUtils.join(columns, ", ");
            sb.append(allColumnNames);
            sb.append(" FROM ");
            sb.append(dataSourceName);
            sb.append (" WHERE (1 = 1)");
            for (Map.Entry<String, String> mappingEntry : columnMappings.entrySet()) {
                sb.append(" AND ");
                sb.append(mappingEntry.getKey());
                sb.append(" = '");
                sb.append(mappingEntry.getValue());
                sb.append("'");
            }
            return sb.toString();
        }

    }

    /**
     * Options for drill-through report execution.
     */
    public static class Options {

        private String delimiter = EssDataSource.DELIMITER_TAB;

        private boolean includeHeaders = true;

        /**
         * Gets the current delimiter.
         *
         * @return the delimiter
         */
        public String getDelimiter() {
            return delimiter;
        }

        /**
         * Sets the delimiter to use. At the time of this writing, the Essbase REST API only allows a comma or tab. This
         * Java API will not preclude you from using something else, but you are encouraged to use the constants in {@link EssDataSourceImpl}.
         *
         * @param delimiter the delimiter
         */
        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        /**
         * Gets the value for including headers.
         *
         * @return true if include headers is on, false otherwise
         */
        public boolean isIncludeHeaders() {
            return includeHeaders;
        }

        /**
         * Sets whether headers are to be included in the output or not. This is a property on the Essbase REST API call
         * (as opposed to a post-processing step).
         *
         * @param includeHeaders value for include headers
         */
        public void setIncludeHeaders(boolean includeHeaders) {
            this.includeHeaders = includeHeaders;
        }

    }

    public static class ColumnMapping {
        private final String columnName;
        private final ColumnMappingInfo columnMappingInfo;

        private ColumnMapping(String columnName, ColumnMappingInfo columnMappingInfo) {
            this.columnName = columnName;
            this.columnMappingInfo = columnMappingInfo;
        }

        public String getColumnName() { return columnName;}
        public ColumnMappingInfo getColumnMappingInfo() { return columnMappingInfo;}
    }

    public static class Parameter {
        private final String key;
        private final RunTimeParametersInfo runTimeParametersInfo;

        private Parameter(String key, RunTimeParametersInfo runTimeParametersInfo) {
            this.key = key;
            this.runTimeParametersInfo = runTimeParametersInfo;
        }

        public String getKey() { return key;}
        public RunTimeParametersInfo getRunTimeParametersInfo() { return runTimeParametersInfo;}
    }
}