package com.appliedolap.essbase.client.api;

import com.appliedolap.essbase.client.*;
import com.appliedolap.essbase.client.model.CollectionResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exists to get around limitation in standard class that encodes URL paramters incorrectly
 */
public class FilesApi2 {

    private final ApiClient localVarApiClient;

    public FilesApi2(ApiClient client) {
        this.localVarApiClient = client;
    }

    public CollectionResponse filesListFiles(String path, Integer offset, Integer limit, String type, Boolean overwrite, String action, Long fileSize, String filter, Boolean recursive) throws ApiException {
        ApiResponse<CollectionResponse> localVarResp = filesListFilesWithHttpInfo(path, offset, limit, type, overwrite, action, fileSize, filter, recursive);
        return localVarResp.getData();
    }

    public ApiResponse<CollectionResponse> filesListFilesWithHttpInfo(String path, Integer offset, Integer limit, String type, Boolean overwrite, String action, Long fileSize, String filter, Boolean recursive) throws ApiException {
        okhttp3.Call localVarCall = filesListFilesValidateBeforeCall(path, offset, limit, type, overwrite, action, fileSize, filter, recursive, null);
        Type localVarReturnType = new TypeToken<CollectionResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call filesListFilesValidateBeforeCall(String path, Integer offset, Integer limit, String type, Boolean overwrite, String action, Long fileSize, String filter, Boolean recursive, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException("Missing the required parameter 'path' when calling filesListFiles(Async)");
        }
        okhttp3.Call localVarCall = filesListFilesCall(path, offset, limit, type, overwrite, action, fileSize, filter, recursive, _callback);
        return localVarCall;
    }

    public okhttp3.Call filesListFilesCall(String path, Integer offset, Integer limit, String type, Boolean overwrite, String action, Long fileSize, String filter, Boolean recursive, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;
        path = trimLeadingSlash(path);

        // create path and map variables
        String localVarPath = "/files/{path}"
                .replaceAll("\\{" + "path" + "\\}", path);

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        }

        if (overwrite != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("overwrite", overwrite));
        }

        if (action != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("action", action));
        }

        if (fileSize != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("fileSize", fileSize));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (recursive != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("recursive", recursive));
        }

        final String[] localVarAccepts = {
                "application/json", "application/xml"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    public okhttp3.Call filesDownloadFilesCall(String path, Integer offset, Integer limit, String type, Boolean overwrite, String action, Long fileSize, String filter, Boolean recursive, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;
        path = trimLeadingSlash(path);

        // create path and map variables
        String localVarPath = "/files/{path}"
                .replaceAll("\\{" + "path" + "\\}", path);

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        }

        if (overwrite != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("overwrite", overwrite));
        }

        if (action != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("action", action));
        }

        if (fileSize != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("fileSize", fileSize));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
        }

        if (recursive != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("recursive", recursive));
        }

//        final String[] localVarAccepts = {
//                "application/json", "application/xml"
//        };
//        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
//        if (localVarAccept != null) {
//            localVarHeaderParams.put("Accept", localVarAccept);
//        }

        localVarHeaderParams.put("Accept", "application/octet-stream");
        final String[] localVarContentTypes = {

        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    public okhttp3.Call filesAddFileCall(String path, Boolean overwrite, byte[] bytes, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = bytes;
        path = trimLeadingSlash(path);

        // create path and map variables
        String localVarPath = "/files/{path}"
                .replaceAll("\\{" + "path" + "\\}", path);

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (overwrite != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("overwrite", overwrite));
        }

        final String[] localVarAccepts = {
                "application/json", "application/xml"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/octet-stream"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }


    public ApiResponse<Void> filesDeleteFileWithHttpInfo(String path) throws ApiException {
        okhttp3.Call localVarCall = filesDeleteFileValidateBeforeCall(path, null);
        return localVarApiClient.execute(localVarCall);
    }

    public void filesDeleteFile(String path) throws ApiException {
        filesDeleteFileWithHttpInfo(path);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call filesDeleteFileValidateBeforeCall(String path, final ApiCallback _callback) throws ApiException {

        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException("Missing the required parameter 'path' when calling filesDeleteFile(Async)");
        }


        okhttp3.Call localVarCall = filesDeleteFileCall(path, _callback);
        return localVarCall;

    }

    public okhttp3.Call filesDeleteFileCall(String path, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;
        path = trimLeadingSlash(path);

        // create path and map variables
        String localVarPath = "/files/{path}"
                .replaceAll("\\{" + "path" + "\\}", path);

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {

        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    public static String trimLeadingSlash(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

}