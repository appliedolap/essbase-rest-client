package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.FileBean;
import com.appliedolap.essbase.client.model.FileCollectionResponse;
import com.appliedolap.essbase.util.NativeHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a folder in the remote Essbase server pseudo-filesystem.
 */
public class EssFolderImpl extends EssFileImpl implements EssFolder {

    private static final Logger logger = LoggerFactory.getLogger(EssFolderImpl.class);

    private final EssServer server;

    public EssFolderImpl(ApiContext api, EssServer server, String name, String fullPath) {
        super(api, server, name, fullPath);
        this.server = server;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public void createSubFolder(String subFolderName) {
        try {
            logger.info("Creating new folder {}", subFolderName);
            String path = NativeHttp.withQuery("/files/" + NativeHttp.encodePathKeepingSlashes(fullPath + "/" + subFolderName), "overwrite", false);
            NativeHttp.sendAndDiscard(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/json, application/xml")
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.noBody()), "filesAddFile");
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public void uploadFile(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String path = NativeHttp.withQuery("/files/" + NativeHttp.encodePathKeepingSlashes(fullPath + "/" + file.getName()), "overwrite", true);
            NativeHttp.sendAndDiscard(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/json, application/xml")
                    .header("Content-Type", "application/octet-stream")
                    .PUT(HttpRequest.BodyPublishers.ofByteArray(bytes)), "filesAddFile");
            logger.info("Uploaded file {} to {}", file.getName(), fullPath);
        } catch (IOException | ApiException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    public List<EssFile> getFiles() {
        try {
            String pathForFetch = fullPath;
            if (pathForFetch.startsWith("/")) {
                pathForFetch = pathForFetch.substring(1);
            }
            FileCollectionResponse files = api.getFilesApi().filesListFiles(pathForFetch, null, null, null, null, null, null, null, false);
            List<EssFile> childFiles = new ArrayList<>();
            for (FileBean file : files.getItems()) {
                String name = file.getName();
                boolean isFolder = "folder".equals(file.getType());
                EssFile essFile;
                if (isFolder) {
                    essFile = new EssFolderImpl(api, server, name, file.getFullPath());
                } else {
                    essFile = new EssFileImpl(api, server, name, file.getFullPath());
                }
                childFiles.add(essFile);
            }
            return childFiles;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}