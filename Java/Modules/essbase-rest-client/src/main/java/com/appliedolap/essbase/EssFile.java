package com.appliedolap.essbase;

import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.api.FilesApi;
import com.appliedolap.essbase.client.api.FilesApi2;
import com.appliedolap.essbase.client.api.JobsApi;
import com.appliedolap.essbase.client.model.CollectionResponse;

import com.appliedolap.essbase.client.model.JobRecordBean;
import com.appliedolap.essbase.client.model.JobsInputBean;
import com.appliedolap.essbase.client.model.ParametersBean;
import com.appliedolap.essbase.util.GenericApiCallback;
import com.appliedolap.essbase.util.GenericDownload;
import okhttp3.Call;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EssFile {

    private static final Logger logger = LoggerFactory.getLogger(EssFile.class);

    private final ApiClient client;

    private final String name;

    protected final String fullPath;

    protected final FilesApi filesApi;

    protected final FilesApi2 filesApi2;

    private final JobsApi jobsApi;

    public EssFile(String name, String fullPath, ApiClient client) {
        this.client = client;
        this.name = name;
        this.fullPath = fullPath;
        this.filesApi = new FilesApi(client);
        this.filesApi2 = new FilesApi2(client);
        this.jobsApi = new JobsApi(client);
    }

    public String getName() {
        return name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public boolean isFolder() {
        return false;
    }

    public boolean isFile() {
        return true;
    }

    public File download() {
        return downloadToFolder(new File("."));
    }

    public void delete() {
        try {
            logger.info("Deleting {}", fullPath);
            filesApi2.filesDeleteFile(fullPath);
        } catch (ApiException apiException) {
            logger.error("Deletion error: {}", apiException.getResponseBody());
            throw new EssApiException(apiException);
        }
    }

    public File downloadToFolder(File folder) {
        try {
            String pathForFetch = fullPath;
            Call call = filesApi2.filesDownloadFilesCall(pathForFetch, null, null, null, null, null, null, null, false, new GenericApiCallback());
            return GenericDownload.download(folder, call.execute());
        } catch (ApiException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void lcmImport() {
        JobsInputBean jobsInputBean = new JobsInputBean();
        jobsInputBean.setJobtype("lcmimport");
        ParametersBean parametersBean = new ParametersBean();
        parametersBean.setZipFileName(getName());
        parametersBean.setOverwrite("true");
        jobsInputBean.setParameters(parametersBean);

        try {
            logger.info("Submitting LCM import job");
            JobRecordBean jobRecordBean = jobsApi.jobsExecuteJob(jobsInputBean);
        } catch (ApiException apiException) {
            apiException.printStackTrace();
        }
    }

    public List<EssFile> getFiles() {
        try {
            String pathForFetch = fullPath;
            if (pathForFetch.startsWith("/")) {
                pathForFetch = pathForFetch.substring(1);
            }
            CollectionResponse files = filesApi2.filesListFiles(pathForFetch, null, null, null, null, null, null, null, false);
            List<EssFile> childFiles = new ArrayList<>();
            for (Object file : files.getItems()) {
                Map<String, String> fileMap = (Map) file;
                // name, fullPath, type, permissions (another map), links
                String name = fileMap.get("name");
                boolean isFolder = "folder".equals(fileMap.get("type"));
                EssFile essFile;
                if (isFolder) {
                    essFile = new EssFolder(name, fileMap.get("fullPath"), client);
                } else {
                    essFile = new EssFile(name, fileMap.get("fullPath"), client);
                }
                childFiles.add(essFile);
            }
            return childFiles;
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}