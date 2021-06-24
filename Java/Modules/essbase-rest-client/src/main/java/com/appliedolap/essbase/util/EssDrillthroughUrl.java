package com.appliedolap.essbase.util;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssDrillthrough;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.model.ReportBean;

public class EssDrillthroughUrl extends EssDrillthrough {

    public EssDrillthroughUrl(ApiClient client, EssCube cube, ReportBean reportBean) {
        super(client, cube, reportBean);
    }

}