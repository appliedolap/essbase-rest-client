package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.model.*;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import okhttp3.Call;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A file or folder in the Essbase server file hiearchy. If a folder, the {@link #isFolder()} method will return
 * <code>true</code> and the instance can be cast to an {@link EssFolder}.
 */
public class EssFile extends EssObject {

    private static final Logger logger = LoggerFactory.getLogger(EssFile.class);

    private final EssServer server;

    private final String name;

    protected final String fullPath;

    EssFile(ApiContext api, EssServer server, String name, String fullPath) {
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

    /**
     * Returns the filename portion of the file.
     *
     * @return the filename
     */
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.FILE;
    }

    /**
     * Returns the full path of the file, i.e. the combination of its path and filename.
     *
     * @return the full path of the file
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * Convenience method to return the path to the file.
     *
     * @return the path of this file
     */
    public String getPath() {
        String path = StringUtils.removeEnd(fullPath, "/" + name);
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }

    /**
     * Checks if this file object represents a file or a folder. If this method returns <code>true</code>, then this
     * class may be cast to {@link EssFolder}, which is a subclass of this one.
     *
     * @return true if this object represents a folder, false otherwise
     */
    public boolean isFolder() {
        return false;
    }

    /**
     * Returns true if this is a file, not a folder.
     *
     * @return true if this object is a file
     */
    public boolean isFile() {
        return true;
    }

    /**
     * Returns the owning Essbase server instance for this file.
     *
     * @return the Essbase server parent object
     */
    public EssServer getServer() {
        return server;
    }

    /**
     * Downloads this file to the current directory
     *
     * @return a local file system reference to the downloaded file
     */
    public File download() {
        return downloadToFolder(new File("."));
    }

    /**
     * Downloads the file to a particular folder).
     * TODO: check if folder and download there, if not, download as specific file
     * @param folder the folder
     * @return a local filesystem reference to the created file
     */
    public File downloadToFolder(File folder) {
        try {
            String pathForFetch = fullPath;
            Call call = api.getFilesApi2().filesDownloadFilesCall(pathForFetch, null, null, null, null, null, null, null, false, new GenericApiCallback());
            return GenericDownload.download(folder, call.execute());
        } catch (ApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete this file.
     */
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

    /**
     * Copy a file from source to destination.
     * @param body File path details.(required)
     * @param overwrite Overwrite existing file. (optional, default to false)
     */
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

    /**
     * Moves a file from source to destination. Moving a folder is not supported
     * @param body File path details.(required)
     * @param overwrite Overwrite existing file. Only applicable for moving a file. (optional, default to false)
     */
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

    /**
     * Renames a file or folder. Renaming a folder is supported only if the folder is not in the applications directory
     * @param body File path details.(required)
     * @param overwrite Overwrite existing file. Only applicable for renaming a file. (optional, default to false)
     */
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

    /**
     * Extract a zip file on same location. Supported for applications, users, and shared folders.
     * @param body File path details.(required)
     * @param overwrite Overwrite existing file. Not applicable for folder. (optional, default to false)
     */
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