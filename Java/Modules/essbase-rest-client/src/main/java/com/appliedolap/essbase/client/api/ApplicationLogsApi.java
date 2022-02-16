/*
 * sometitle
 * The REST API for Essbase provides an automation framework for managing Essbase resources and operations. All requests and responses are communicated over secured HTTP.
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.appliedolap.essbase.client.api;

import com.appliedolap.essbase.client.ApiCallback;
import com.appliedolap.essbase.client.ApiClient;
import com.appliedolap.essbase.client.ApiException;
import com.appliedolap.essbase.client.ApiResponse;
import com.appliedolap.essbase.client.Configuration;
import com.appliedolap.essbase.client.Pair;
import com.appliedolap.essbase.client.ProgressRequestBody;
import com.appliedolap.essbase.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.appliedolap.essbase.client.model.Link;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationLogsApi {
    private ApiClient localVarApiClient;

    public ApplicationLogsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ApplicationLogsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for applicationLogsDownloadAllLogFiles
     * @param applicationName Name of the application (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Logs returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return logs.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadAllLogFilesCall(String applicationName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/logs/all"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()));

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

        String[] localVarAuthNames = new String[] { "basicAuth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call applicationLogsDownloadAllLogFilesValidateBeforeCall(String applicationName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling applicationLogsDownloadAllLogFiles(Async)");
        }
        

        okhttp3.Call localVarCall = applicationLogsDownloadAllLogFilesCall(applicationName, _callback);
        return localVarCall;

    }

    /**
     * Download All Logs
     * Returns or download zip file which contains all log files for the specific application
     * @param applicationName Name of the application (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Logs returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return logs.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void applicationLogsDownloadAllLogFiles(String applicationName) throws ApiException {
        applicationLogsDownloadAllLogFilesWithHttpInfo(applicationName);
    }

    /**
     * Download All Logs
     * Returns or download zip file which contains all log files for the specific application
     * @param applicationName Name of the application (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Logs returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return logs.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> applicationLogsDownloadAllLogFilesWithHttpInfo(String applicationName) throws ApiException {
        okhttp3.Call localVarCall = applicationLogsDownloadAllLogFilesValidateBeforeCall(applicationName, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Download All Logs (asynchronously)
     * Returns or download zip file which contains all log files for the specific application
     * @param applicationName Name of the application (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Logs returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return logs.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadAllLogFilesAsync(String applicationName, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = applicationLogsDownloadAllLogFilesValidateBeforeCall(applicationName, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for applicationLogsDownloadAppLogFiles
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;List of URI links returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return downloadable log files.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadAppLogFilesCall(String applicationName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/logs"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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

        String[] localVarAuthNames = new String[] { "basicAuth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call applicationLogsDownloadAppLogFilesValidateBeforeCall(String applicationName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling applicationLogsDownloadAppLogFiles(Async)");
        }
        

        okhttp3.Call localVarCall = applicationLogsDownloadAppLogFilesCall(applicationName, _callback);
        return localVarCall;

    }

    /**
     * Download Logs
     * &lt;p&gt;Returns links to download all log files as a zip file, and to download the latest log file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @return Link
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;List of URI links returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return downloadable log files.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public Link applicationLogsDownloadAppLogFiles(String applicationName) throws ApiException {
        ApiResponse<Link> localVarResp = applicationLogsDownloadAppLogFilesWithHttpInfo(applicationName);
        return localVarResp.getData();
    }

    /**
     * Download Logs
     * &lt;p&gt;Returns links to download all log files as a zip file, and to download the latest log file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @return ApiResponse&lt;Link&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;List of URI links returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return downloadable log files.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Link> applicationLogsDownloadAppLogFilesWithHttpInfo(String applicationName) throws ApiException {
        okhttp3.Call localVarCall = applicationLogsDownloadAppLogFilesValidateBeforeCall(applicationName, null);
        Type localVarReturnType = new TypeToken<Link>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Download Logs (asynchronously)
     * &lt;p&gt;Returns links to download all log files as a zip file, and to download the latest log file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;List of URI links returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return downloadable log files.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadAppLogFilesAsync(String applicationName, final ApiCallback<Link> _callback) throws ApiException {

        okhttp3.Call localVarCall = applicationLogsDownloadAppLogFilesValidateBeforeCall(applicationName, _callback);
        Type localVarReturnType = new TypeToken<Link>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for applicationLogsDownloadLatestLogFile
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Log file returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return log file.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadLatestLogFileCall(String applicationName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/logs/latest"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()));

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

        String[] localVarAuthNames = new String[] { "basicAuth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call applicationLogsDownloadLatestLogFileValidateBeforeCall(String applicationName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling applicationLogsDownloadLatestLogFile(Async)");
        }
        

        okhttp3.Call localVarCall = applicationLogsDownloadLatestLogFileCall(applicationName, _callback);
        return localVarCall;

    }

    /**
     * Download Latest Log
     * &lt;p&gt;Downloads the latest application log file as a text file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Log file returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return log file.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void applicationLogsDownloadLatestLogFile(String applicationName) throws ApiException {
        applicationLogsDownloadLatestLogFileWithHttpInfo(applicationName);
    }

    /**
     * Download Latest Log
     * &lt;p&gt;Downloads the latest application log file as a text file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Log file returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return log file.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> applicationLogsDownloadLatestLogFileWithHttpInfo(String applicationName) throws ApiException {
        okhttp3.Call localVarCall = applicationLogsDownloadLatestLogFileValidateBeforeCall(applicationName, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Download Latest Log (asynchronously)
     * &lt;p&gt;Downloads the latest application log file as a text file.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Name of the application.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Log file returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to return log file.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applicationLogsDownloadLatestLogFileAsync(String applicationName, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = applicationLogsDownloadLatestLogFileValidateBeforeCall(applicationName, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
