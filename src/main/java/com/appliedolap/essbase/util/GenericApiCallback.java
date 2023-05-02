package com.appliedolap.essbase.util;

import com.appliedolap.essbase.client.ApiCallback;
import com.appliedolap.essbase.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class GenericApiCallback implements ApiCallback<Object> {

    private static final Logger logger = LoggerFactory.getLogger(GenericApiCallback.class);

    @Override
    public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
        logger.error("Download failed");
    }

    @Override
    public void onSuccess(Object result, int statusCode, Map<String, List<String>> responseHeaders) {
        logger.info("Download success");
    }

    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
        logger.info("Download progress, bytes read: {}, content length: {}, done: {}", bytesRead, contentLength, done);
    }

}