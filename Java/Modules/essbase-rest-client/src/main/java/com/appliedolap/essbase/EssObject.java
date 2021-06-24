package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;

public abstract class EssObject {

    protected final ApiClient client;

    public EssObject(ApiClient client) {
        this.client = client;
    }

    public abstract String getName();

}