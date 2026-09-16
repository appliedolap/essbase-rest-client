package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.util.GenericDownload;
import com.appliedolap.essbase.util.NativeHttp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A file or folder in the Essbase server file hierarchy. If a folder, the {@link #isFolder()} method will return
 * <code>true</code> and the instance can be cast to an {@link EssFolderImpl}.
 */
public class EssFileImpl extends AbstractEssObject implements EssFile {

    private static final Logger logger = LoggerFactory.getLogger(EssFileImpl.class);

    private final EssServer server;

    private final String name;

    protected final String fullPath;

    public EssFileImpl(ApiContext api, EssServer server, String name, String fullPath) {
        super(api);
        this.server = server;
        this.name = name;
        if (fullPath.startsWith("/")) {
            // kind of a hack for now
            this.fullPath = fullPath.substring(1);
        } else {
            this.fullPath = fullPath;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public String getPath() {
        String path = StringUtils.removeEnd(fullPath, "/" + name);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }

    @Override
    public boolean isFolder() {
        return false;
    }

    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public EssServer getServer() {
        return server;
    }

    @Override
    public File download() {
        return downloadToFolder(new File("."));
    }

    @Override
    public File downloadToFolder(File folder) {
        try {
            String pathForFetch = fullPath;
            String path = NativeHttp.withQuery("/files/" + NativeHttp.encodePathKeepingSlashes(pathForFetch), "recursive", false);
            return GenericDownload.download(folder, NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/octet-stream")
                    .GET(), "filesDownloadFiles"));
        } catch (ApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File downloadTo(File file) {
        String path = NativeHttp.withQuery(
                "/files/" + NativeHttp.encodePathKeepingSlashes(fullPath), "recursive", false);
        try (OutputStream out = new FileOutputStream(file)) {
            NativeHttp.copyBodyTo(NativeHttp.send(api.getClient(), NativeHttp.request(api.getClient(), path)
                    .header("Accept", "application/octet-stream")
                    .GET(), "filesDownloadFiles"), out);
            return file;
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    @Override
    /**
     * Not through the generated client, for the same reason listing a folder isn't: it runs the path
     * through {@code urlEncode}, which escapes the separators as well, so the server is asked for
     * {@code /users%2Fadmin%2Freport.txt} and answers that no such path exists. Anything below a root
     * folder was undeletable, and the error blamed the path rather than the encoding of it.
     */
    public void delete() {
        logger.info("Deleting file {}", fullPath);
        try {
            NativeHttp.sendAndDiscard(api.getClient(),
                    NativeHttp.request(api.getClient(),
                            "/files/" + NativeHttp.encodePathKeepingSlashes(fullPath)).DELETE(),
                    "filesDeleteFile");
        } catch (ApiException | IOException e) {
            throw new EssApiException(e);
        }
    }

    // TODO: move, this shouldn't be in this class
    @Deprecated
    @Override
    public void lcmImport() {
        JobsInputBean jobsInputBean = new JobsInputBean();
        jobsInputBean.setJobtype("lcmimport");
        ParametersBean parametersBean = new ParametersBean();
        parametersBean.setZipFileName(getName());
        parametersBean.setOverwrite("true");
        jobsInputBean.setParameters(parametersBean);

        try {
            logger.info("Submitting LCM import job");
            JobRecordBean jobRecordBean = api.getJobsApi().jobsExecuteJob(jobsInputBean);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    @Override
    public void copy(EssFilePathDetail body, Boolean overwrite) {
        try {
            if (overwrite == null){
                overwrite = false;
            }
            api.getFilesApi().filesCopyResource(body, overwrite);
        } catch (ApiException a) {
            throw new EssApiException(a);
        }
    }

    @Override
    public void move(EssFilePathDetail body, Boolean overwrite) {
        try {
            if (overwrite == null){
                overwrite = false;
            }
            api.getFilesApi().filesMoveResource(body, overwrite);
        } catch (ApiException a) {
            throw new EssApiException(a);
        }
    }

    @Override
    public void rename(EssFilePathDetail body, Boolean overwrite) {
        try {
            if (overwrite == null){
                overwrite = false;
            }
            api.getFilesApi().filesMoveResource(body, overwrite);
        } catch (ApiException a) {
            throw new EssApiException(a);
        }
    }

    @Override
    public void extract(EssZipFileDetails body, Boolean overwrite) {
        try {
            if (overwrite == null){
                overwrite = false;
            }
            api.getFilesApi().filesExtract(body, overwrite);
        } catch (ApiException a) {
            throw new EssApiException(a);
        }
    }

}