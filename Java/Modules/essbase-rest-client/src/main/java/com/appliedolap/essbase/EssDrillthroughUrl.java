package com.appliedolap.essbase;

import com.appliedolap.essbase.client.model.ReportBean;

/**
 * Subclass for URL-style drill-through (work in progress)
 */
public class EssDrillthroughUrl extends EssDrillthrough {

    EssDrillthroughUrl(ApiContext api, EssCube cube, ReportBean reportBean) {
        super(api, cube, reportBean);
    }

}