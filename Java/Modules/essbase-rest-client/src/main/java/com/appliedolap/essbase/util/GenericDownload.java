package com.appliedolap.essbase.util;

import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ContentDisposition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenericDownload {

    private static final Logger logger = LoggerFactory.getLogger(GenericDownload.class);

    public static byte[] downloadBytes(Response response) throws IOException {
        ResponseBody body = response.body();
        byte[] outline = IOUtils.toByteArray(body.byteStream());
        return outline;
    }

    public static File download(Response response) throws IOException {
        return download(new File("."), response);
    }

    public static File download(File directory, Response response) throws IOException {
        ResponseBody body = response.body();

        String filename = null;
        String contentDisposition = response.header("Content-Disposition");
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
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            IOUtils.copy(body.byteStream(), fos);
        }
        logger.info("Wrote file to {}", outputFile.getAbsolutePath());
        return outputFile;
    }

}