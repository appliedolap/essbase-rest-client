package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiCallback;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.util.GenericApiCallback;
import okhttp3.Call;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

/**
 * Represents a folder in the remote Essbase server pseudo-filesystem.
 */
public class EssFolder extends EssFile {

    private static final Logger logger = LoggerFactory.getLogger(EssFolder.class);

    EssFolder(ApiContext api, EssServer server, String name, String fullPath) {
        super(api, server, name, fullPath);
    }

    /**
     * Check if this item is a folder (true for <code>EssFolder</code> objects, false for {@link EssFile} objects.
     *
     * @return true if a folder, false if file
     */
    public boolean isFolder() {
        return true;
    }

    /**
     * Inverse of {@link #isFolder()}.
     *
     * @return true if file, false otherwise
     */
    public boolean isFile() {
        return false;
    }

    /**
     * Creates a subfolder in this folder.
     *
     * @param subFolderName the name of the subfolder
     */
    public void createSubFolder(String subFolderName) {
        try {
            logger.info("Creating new folder {}", subFolderName);
            api.getFilesApi2().filesAddFileCall(fullPath + "/" + subFolderName, false, null,new GenericApiCallback()).execute();
        } catch (ApiException apiException) {
            throw new EssApiException(apiException);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Uploads a file to this folder.
     *
     * @param file the file
     */
    public void uploadFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());

            Call call = api.getFilesApi2().filesAddFileCall(fullPath + "/" + file.getName(), true, bytes, new ApiCallback() {
                @Override
                public void onFailure(ApiException e, int statusCode, Map responseHeaders) {
                    logger.info("on failure");
                }

                @Override
                public void onSuccess(Object result, int statusCode, Map responseHeaders) {
                    logger.info("on success");
                }

                @Override
                public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
                    logger.info("on upload {} / {} / {}", bytesWritten, contentLength, done);
                }

                @Override
                public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                    logger.info("on download");

                }
            });
            Response response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

}