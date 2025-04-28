package com.appliedolap.essbase.impl;

import com.appliedolap.essbase.*;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import okhttp3.Call;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
            Call call = api.getFilesApi2().filesDownloadFilesCall(pathForFetch, null, null, null, null, null, null, null, false, new GenericApiCallback());
            return GenericDownload.download(folder, call.execute());
        } catch (ApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete() {
        try {
            logger.info("Deleting file {}", fullPath);
            api.getFilesApi().filesDeleteFile(fullPath);
        } catch (ApiException apiException) {
            logger.error("Deletion error: {}", apiException.getResponseBody());
            throw new EssApiException(apiException);
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