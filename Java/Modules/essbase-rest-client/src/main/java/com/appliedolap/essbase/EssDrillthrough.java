package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.DrillthroughBean;
import com.appliedolap.essbase.client.model.ReportBean;
import com.appliedolap.essbase.util.WrapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a drill-through report on a given cube
 */
public class EssDrillthrough extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssDrillthrough.class);

    private final EssCube cube;

    private boolean hasDetails = false;

    /**
     * name is final -- the REST API does not honor the name field when updating the report, and it is used as part of
     * the URL. A pseudo-rename looks like it is accomplished via a copy and delete
     */
    private final String name;

    private String url;

    private List<String> drillableRegions;

    EssDrillthrough(ApiContext api, EssCube cube, ReportBean reportBean) {
        super(api);
        this.cube = cube;
        this.name = reportBean.getName();

//        try {
//            // if a URL-type drillthrough, will have:
//            // type = URL
//            // url = "the url value"
//            // name = "the name of the drill report"
//            // a List<String> for drillable regions containing regions such as @Member("Actual")
//            // useTempTables = false (not sure if relevant)
//            // the following seem blank for this type: dataSourceName, columnMapping, columns, parameterMapping
//
//            // if columnar:
//            // dataSourceName is set ("UserList")
//            // columnMapping is Map<String to ColumnMappingInfo), such as
//            //   FIRST_NAME -> dimension=CellProperties, generation=null, level = Level0, type = enum(LEVEL0), generationNumber= null
//            // null: url
//            // columns: List<String> FIRST_NAME, LAST_NAME
//            // drillable regions still set as normal
//            // type: DATASOURCE
//            // name = same
//            // Use Temporary Tables is an option in the Essbase JET UI but seems greyed out; may be enabled when using a
//            // true SQL source
//            // DrillthroughBean drillthroughBean = drillThroughReportsApi.drillThroughReportsGetReport(cube.getApplication().getName(), cube.getName(), reportBean.getName());
//        } catch (ApiException apiException) {
//            apiException.printStackTrace();
//        }
    }

    private void loadDetails() {
        if (!hasDetails) {
            try {
                DrillthroughBean drillthroughBean = api.getDrillThroughReportsApi().drillThroughReportsGetReport(cube.getApplication().getName(), cube.getName(), getName());
                this.url = drillthroughBean.getUrl();
                this.drillableRegions = new ArrayList<>(drillthroughBean.getDrillableRegions());
                this.hasDetails = true;
            } catch (ApiException apiException) {
                throw new EssApiException(apiException);
            }
        }
    }

    /**
     * Gets the name of this drill-through report.
     *
     * @return the drill-through name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the URL of this drill-through report (URL-style only!). This API will likely change soon as support
     * for different drill-through types is built out.
     *
     * @return the drill URL
     */
    public String getUrl() {
        loadDetails();
        return url;
    }

    /**
     * Sets the drill (again, likely to change soon)
     *
     * @param url the drill URL
     */
    public void setUrl(String url) {
        loadDetails();
        this.url = url;
    }

    /**
     * Get the drillable regions)
     *
     * @return the list of drillable regions
     */
    public List<String> getDrillableRegions() {
        loadDetails();
        return Collections.unmodifiableList(drillableRegions);
    }

    /**
     * Sets the drillable regions for this report
     *
     * @param drillableRegions the drillable regions
     */
    public void setDrillableRegions(List<String> drillableRegions) {
        loadDetails();
        this.drillableRegions = drillableRegions;
    }

    /**
     * Saves updates to this report
     */
    public void save() {
        try {
            DrillthroughBean drillthroughBean = new DrillthroughBean();
            drillthroughBean.setType("URL");
            drillthroughBean.setUrl(url);
            drillthroughBean.setDrillableRegions(drillableRegions);
            api.getDrillThroughReportsApi().drillThroughReportsUpdateReport(cube.getApplication().getName(), cube.getName(), name, drillthroughBean);
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        }
    }

    /**
     * Deletes this drill-through report
     */
    public void delete() {
        logger.info("Deleting drill-through report {}", getName());
        WrapperUtil.wrap(() -> api.getDrillThroughReportsApi().drillThroughReportsDelete(cube.getApplication().getName(), cube.getName(), getName()));
    }

}