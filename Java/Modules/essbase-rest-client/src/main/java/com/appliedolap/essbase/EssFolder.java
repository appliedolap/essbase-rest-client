package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiCallback;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.FilesApi;
import okhttp3.Call;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class EssFolder extends EssFile {

    private static final Logger logger = LoggerFactory.getLogger(EssFolder.class);

    public EssFolder(String name, String fullPath, ApiClient client) {
        super(name, fullPath, client);
    }

    public boolean isFolder() {
        return true;
    }

    public boolean isFile() {
        return false;
    }

    public void createSubFolder(String subFolderName) {

    }

    public void uploadFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());

            Call call = filesApi2.filesAddFileCall(fullPath + "/" + file.getName(), true, bytes, new ApiCallback() {
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