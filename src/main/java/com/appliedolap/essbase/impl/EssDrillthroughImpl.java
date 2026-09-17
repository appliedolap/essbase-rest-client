package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.NativeHttp;
import com.fasterxml.jackson.databind.JsonNode;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.exceptions.DrillthroughColumnMismatchException;
import com.appliedolap.essbase.util.WrapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    /**
     * <p>Parsed without regard to case because the server is not consistent about it: creating a
     * report echoes back the {@code datasource} it was sent, while reading one answers
     * {@code DATASOURCE}. Matching exactly would work on the path this class happens to take and fail
     * on the other.
     */
    @Override
    public DrillthroughType getDrillthroughType() {
        String type = getDrillthroughBean().getType();
        if (type == null) {
            throw new EssApiException("The server reported no type for drill-through report " + name);
        }
        return DrillthroughType.valueOf(type.toUpperCase(Locale.ROOT));
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
    @Override
    public List<String> getColumns() {
        return getDrillthroughBean().getColumns();
    }

    /**
     * Sets the list of column names for this report.
     *
     * @param columns a list of column names for this report
     */
    @Override
    public void setColumns(List<String> columns) {
        getDrillthroughBean().setColumns(columns);
    }

    @Override
    public Map<String, EssDrillthroughColumnMapping> getColumnMappings() {
        Map<String, ColumnMappingInfo> infos = getDrillthroughBean().getColumnMapping();
        Map<String, EssDrillthroughColumnMapping> mappings = new LinkedHashMap<>();
        if (infos != null) {
            for (Map.Entry<String, ColumnMappingInfo> entry : infos.entrySet()) {
                ColumnMappingInfo info = entry.getValue();
                mappings.put(entry.getKey(), new EssDrillthroughColumnMapping(
                        info.getDimension(),
                        info.getType() == null ? null
                                : EssDrillthroughColumnMapping.MappingType.valueOf(
                                        info.getType().getValue().toUpperCase(Locale.ROOT)),
                        info.getGeneration(), info.getLevel(), info.getGenerationNumber()));
            }
        }
        return mappings;
    }

    @Override
    public void setColumnMappings(Map<String, EssDrillthroughColumnMapping> columnMappings) {
        Map<String, ColumnMappingInfo> infos = new LinkedHashMap<>();
        for (Map.Entry<String, EssDrillthroughColumnMapping> entry : columnMappings.entrySet()) {
            EssDrillthroughColumnMapping mapping = entry.getValue();
            ColumnMappingInfo info = new ColumnMappingInfo();
            info.setDimension(mapping.getDimension());
            if (mapping.getMappingType() != null) {
                info.setType(ColumnMappingInfo.TypeEnum.fromValue(mapping.getMappingType().name()));
            }
            info.setGeneration(mapping.getGeneration());
            info.setLevel(mapping.getLevel());
            info.setGenerationNumber(mapping.getGenerationNumber());
            infos.put(entry.getKey(), info);
        }
        getDrillthroughBean().setColumnMapping(infos);
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
    public EssDrillthroughResult execute(Map<String, Set<String>> pov) {
        return execute(pov, null);
    }

    /**
     * <p>Through {@link NativeHttp} rather than the generated method, because the specification gives
     * this operation's 200 no schema at all - it describes the shape in prose and models none of it,
     * so the generated call returns {@code void} and discards the rows. The prose is accurate: the
     * body is a JSON array whose first element is the column datatypes, whose second is the column
     * names, and whose remainder is one array per record.
     *
     * <p>The POV's member sets are sent as sets because that is what the endpoint wants - handing it
     * a bare string earns {@code Cannot construct instance of java.util.HashSet from String value}.
     */
    @Override
    public EssDrillthroughResult execute(Map<String, Set<String>> pov, String aliasTable) {
        DrillThroughRange range = new DrillThroughRange();
        range.setCells(new LinkedHashMap<>(pov));
        DrillthroughMetadataBean metadata = new DrillthroughMetadataBean();
        metadata.setDtrContext(Collections.singletonList(range));
        if (aliasTable != null) {
            metadata.setAliasTable(aliasTable);
        }

        String path = "/applications/" + ApiClient.urlEncode(cube.getApplication().getName())
                + "/databases/" + ApiClient.urlEncode(cube.getName())
                + "/reports/" + ApiClient.urlEncode(getName());
        try {
            String body = NativeHttp.sendForString(api.getClient(),
                    NativeHttp.request(api.getClient(), path)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .POST(NativeHttp.jsonBody(api.getClient(), metadata)),
                    "drillThroughReportsExecute");
            return parse(body);
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    /**
     * Reads the three-part array the server answers with.
     *
     * <p>A report that matches nothing still returns its two header rows, so an empty result is two
     * elements rather than none - and fewer than two is a response this code does not understand,
     * which is worth saying rather than silently reporting no rows.
     */
    private EssDrillthroughResult parse(String body) throws IOException {
        JsonNode root = api.getClient().getObjectMapper().readTree(body);
        if (!root.isArray() || root.size() < 2) {
            throw new EssApiException("Expected a drill-through result of datatypes, column names and"
                    + " records, and got: " + StringUtils.abbreviate(body, 200));
        }
        JsonNode types = root.get(0);
        JsonNode names = root.get(1);
        List<EssDrillthroughResult.Column> columns = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            columns.add(new EssDrillthroughResult.Column(names.get(i).asText(),
                    i < types.size() ? types.get(i).asText() : null));
        }
        List<List<String>> rows = new ArrayList<>();
        for (int i = 2; i < root.size(); i++) {
            List<String> row = new ArrayList<>();
            for (JsonNode value : root.get(i)) {
                row.add(value.isNull() ? null : value.asText());
            }
            rows.add(row);
        }
        return new EssDrillthroughResult(columns, rows);
    }

    @Override
    public void delete() {
        logger.info("Deleting drill-through report {}", getName());
        WrapperUtil.wrap(() -> api.getDrillThroughReportsApi().drillThroughReportsDelete(cube.getApplication().getName(), cube.getName(), getName()));
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