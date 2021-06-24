package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.api.DimensionsApi;
import com.appliedolap.essbase.client.model.DimensionBean;

public class EssDimension extends EssObject {

    private final DimensionsApi dimensionsApi;

    private final DimensionBean dimension;

    public EssDimension(ApiClient client, DimensionBean dimension) {
        super(client);
        this.dimensionsApi = new DimensionsApi(client);
        this.dimension = dimension;
    }

    @Override
    public String getName() {
        return dimension.getName();
    }

}