package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.EssURL;
import com.appliedolap.essbase.client.model.EssbaseURL;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Models a URL object that can be returned from the URLs endpoint. This seems to be a 'discoverability' API so that
 * a client can dynamically ask for URLs, which as of now includes such things as the URL to the JET UI, XMLA provider,
 * REST API, Java API, Smart View, and so on.
 */
public class EssURLImpl extends AbstractEssObject implements EssURL {

    private final EssServer server;

    private final EssbaseURL essbaseURL;

    public EssURLImpl(ApiContext api, EssServer server, EssbaseURL essbaseURL) {
        super(api);
        this.server = server;
        this.essbaseURL = essbaseURL;
    }

    @Override
    public String getName() {
        return essbaseURL.getApplication();
    }

    @Override
    public Type getType() {
        return Type.URL;
    }

    @Override
    public String getURL() {
        return essbaseURL.getUrl();
    }

    @Override
    public URI getURI() {
        try {
            return new URI(server.getName() + "/" + essbaseURL.getUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}