package com.appliedolap.essbase.util;

import com.appliedolap.essbase.client.ApiException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;

public class GenericDownload {

    private static final Logger logger = LoggerFactory.getLogger(GenericDownload.class);

    public static byte[] downloadBytes(HttpResponse<InputStream> response) throws IOException, ApiException {
        assertSuccessful(response);
        try (InputStream body = response.body()) {
            return IOUtils.toByteArray(body);
        }
    }

    public static File download(HttpResponse<InputStream> response) throws IOException, ApiException {
        return download(new File("."), response);
    }

    public static File download(File directory, HttpResponse<InputStream> response) throws IOException, ApiException {
        assertSuccessful(response);

        String filename = null;
        String contentDisposition = response.headers().firstValue("Content-Disposition").orElse(null);
        if (contentDisposition != null) {
            try {
                ContentDisposition disposition = ContentDisposition.parse(contentDisposition);
                filename = disposition.getFilename();
            } catch (Exception e) {
                // null is handled below
            }
        }
        if (filename == null) filename = "temp.zip";

        System.out.println("Will download to " + filename);
        File outputFile = new File(directory, filename);
        try (InputStream body = response.body(); FileOutputStream fos = new FileOutputStream(outputFile)) {
            IOUtils.copy(body, fos);
        }
        logger.info("Wrote file to {}", outputFile.getAbsolutePath());
        return outputFile;
    }

    private static void assertSuccessful(HttpResponse<InputStream> response) throws ApiException {
        if (response.statusCode() / 100 != 2) {
            throw new ApiException(response.statusCode(), "Download failed with HTTP status " + response.statusCode(), response.headers(), null);
        }
    }

}