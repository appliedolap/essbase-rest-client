package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.AbstractEssObject;
import com.appliedolap.essbase.ApiContext;
import com.appliedolap.essbase.EssUtility;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.Resource;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import okhttp3.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Represents one of the downloadable utilities on the Essbase server.
 */
public class EssUtilityImpl extends AbstractEssObject implements EssUtility {

    private static final Logger logger = LoggerFactory.getLogger(EssUtilityImpl.class);

    private final Resource resource;

    public EssUtilityImpl(ApiContext api, Resource resource) {
        super(api);
        this.resource = resource;
    }

    @Override
    public String getName() {
        return resource.getName();
    }

    @Override
    public Type getType() {
        return Type.UTILITY;
    }

    @Override
    public boolean isDownloadable() {
        return resource.getLinks() != null && !resource.getLinks().isEmpty();
    }

    @Override
    public void download() {
        if (isDownloadable()) {
            try {
                Call call = api.getTemplatesAndUtilitiesApi().resourcesDownloadUtilityCall(resource.getId(), new GenericApiCallback());
                GenericDownload.download(call.execute());
            } catch (ApiException | IOException e) {
                logger.error("Unable to download: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Not a downloadable type");
        }
    }

}