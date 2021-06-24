package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.model.SessionAttributes;

public class EssSession {

    private final ApiClient client;

    private final EssCube cube;

    private final SessionAttributes sessionAttributes;

    public EssSession(ApiClient client, EssCube cube, SessionAttributes sessionAttributes) {
        this.client = client;
        this.cube = cube;
        this.sessionAttributes = sessionAttributes;
    }

    public String getSessionId() {
        return sessionAttributes.getSessionId();
    }

    public void delete() {

    }

}