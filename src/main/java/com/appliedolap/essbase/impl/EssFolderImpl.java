package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.FileBean;
import com.appliedolap.essbase.client.model.CollectionResponse;
import com.appliedolap.essbase.util.NativeHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
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
        EssUploads.upload(api, fullPath + "/" + file.getName(), file);
        logger.info("Uploaded file {} to {}", file.getName(), fullPath);
    }

    /**
     * Lists this folder's contents.
     *
     * <p>Goes through {@link NativeHttp} rather than the generated client, for the same reason
     * {@link #uploadFile} and {@link #createSubFolder} already do: the generated call puts the whole
     * path through {@code urlEncode}, which escapes the separators too, so anything below the top
     * level is asked for as {@code /files/applications%2FSample} and the server answers
     * {@code Specified path '/applications%2FSample' does not exist}. Every folder but the roots was
     * unbrowsable.
     */
    @Override
    public List<EssFile> getFiles() {
        String path = NativeHttp.withQuery(
                "/files/" + NativeHttp.encodePathKeepingSlashes(fullPath), "recursive", false);
        try {
            HttpResponse<InputStream> response = NativeHttp.send(api.getClient(),
                    NativeHttp.request(api.getClient(), path).header("Accept", "application/json").GET(),
                    "filesListFiles");
            CollectionResponse files;
            try (InputStream body = response.body()) {
                files = api.getClient().getObjectMapper().readValue(body, CollectionResponse.class);
            }
            List<EssFile> childFiles = new ArrayList<>();
            List<FileBean> items = files.getItems() == null ? Collections.emptyList() : files.getItems();
            for (FileBean file : items) {
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
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

}
