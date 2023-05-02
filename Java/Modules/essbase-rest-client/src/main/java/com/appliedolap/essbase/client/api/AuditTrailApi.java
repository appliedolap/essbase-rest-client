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



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditTrailApi {
    private ApiClient localVarApiClient;

    public AuditTrailApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AuditTrailApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for auditTrailGetDataAudit
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail data returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailGetDataAuditCall(String application, String database, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{application}/databases/{database}/audittrail/data"
            .replaceAll("\\{" + "application" + "\\}", localVarApiClient.escapeString(application.toString()))
            .replaceAll("\\{" + "database" + "\\}", localVarApiClient.escapeString(database.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "text/plain", "text/csv"
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
    private okhttp3.Call auditTrailGetDataAuditValidateBeforeCall(String application, String database, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'application' is set
        if (application == null) {
            throw new ApiException("Missing the required parameter 'application' when calling auditTrailGetDataAudit(Async)");
        }
        
        // verify the required parameter 'database' is set
        if (database == null) {
            throw new ApiException("Missing the required parameter 'database' when calling auditTrailGetDataAudit(Async)");
        }
        

        okhttp3.Call localVarCall = auditTrailGetDataAuditCall(application, database, _callback);
        return localVarCall;

    }

    /**
     * Get Audit Data
     * &lt;p&gt;Returns audit trail data in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit data as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit data list in JSON format.&lt;p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail data returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public String auditTrailGetDataAudit(String application, String database) throws ApiException {
        ApiResponse<String> localVarResp = auditTrailGetDataAuditWithHttpInfo(application, database);
        return localVarResp.getData();
    }

    /**
     * Get Audit Data
     * &lt;p&gt;Returns audit trail data in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit data as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit data list in JSON format.&lt;p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail data returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<String> auditTrailGetDataAuditWithHttpInfo(String application, String database) throws ApiException {
        okhttp3.Call localVarCall = auditTrailGetDataAuditValidateBeforeCall(application, database, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Audit Data (asynchronously)
     * &lt;p&gt;Returns audit trail data in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit data as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit data list in JSON format.&lt;p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail data returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailGetDataAuditAsync(String application, String database, final ApiCallback<String> _callback) throws ApiException {

        okhttp3.Call localVarCall = auditTrailGetDataAuditValidateBeforeCall(application, database, _callback);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for auditTrailGetMetadataAudit
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail metadata returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailGetMetadataAuditCall(String application, String database, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{application}/databases/{database}/audittrail/metadata"
            .replaceAll("\\{" + "application" + "\\}", localVarApiClient.escapeString(application.toString()))
            .replaceAll("\\{" + "database" + "\\}", localVarApiClient.escapeString(database.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "text/plain", "text/csv"
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
    private okhttp3.Call auditTrailGetMetadataAuditValidateBeforeCall(String application, String database, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'application' is set
        if (application == null) {
            throw new ApiException("Missing the required parameter 'application' when calling auditTrailGetMetadataAudit(Async)");
        }
        
        // verify the required parameter 'database' is set
        if (database == null) {
            throw new ApiException("Missing the required parameter 'database' when calling auditTrailGetMetadataAudit(Async)");
        }
        

        okhttp3.Call localVarCall = auditTrailGetMetadataAuditCall(application, database, _callback);
        return localVarCall;

    }

    /**
     * Get Audit Metadata
     * &lt;p&gt;Returns audit metadata in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit metadata as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit metadata list in JSON format.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail metadata returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public String auditTrailGetMetadataAudit(String application, String database) throws ApiException {
        ApiResponse<String> localVarResp = auditTrailGetMetadataAuditWithHttpInfo(application, database);
        return localVarResp.getData();
    }

    /**
     * Get Audit Metadata
     * &lt;p&gt;Returns audit metadata in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit metadata as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit metadata list in JSON format.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail metadata returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<String> auditTrailGetMetadataAuditWithHttpInfo(String application, String database) throws ApiException {
        okhttp3.Call localVarCall = auditTrailGetMetadataAuditValidateBeforeCall(application, database, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Audit Metadata (asynchronously)
     * &lt;p&gt;Returns audit metadata in CSV string format if &lt;code&gt;Accept&#x3D;&#39;text/csv&#39;&lt;/code&gt; or &lt;code&gt;Accept&#x3D;&#39;text/plain&#39;&lt;/code&gt;. If &lt;code&gt;Accept&#x3D;&#39;application/octet-stream&#39;&lt;/code&gt;, returns audit metadata as a CSV stream to download. If &lt;code&gt;Accept&#x3D;&#39;application/json&#39;&lt;/code&gt;, returns the audit metadata list in JSON format.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit trail metadata returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to return audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailGetMetadataAuditAsync(String application, String database, final ApiCallback<String> _callback) throws ApiException {

        okhttp3.Call localVarCall = auditTrailGetMetadataAuditValidateBeforeCall(application, database, _callback);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for auditTrailPurge
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit data deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailPurgeCall(String application, String database, Long olderthan, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{application}/databases/{database}/audittrail/data"
            .replaceAll("\\{" + "application" + "\\}", localVarApiClient.escapeString(application.toString()))
            .replaceAll("\\{" + "database" + "\\}", localVarApiClient.escapeString(database.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (olderthan != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("olderthan", olderthan));
        }

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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call auditTrailPurgeValidateBeforeCall(String application, String database, Long olderthan, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'application' is set
        if (application == null) {
            throw new ApiException("Missing the required parameter 'application' when calling auditTrailPurge(Async)");
        }
        
        // verify the required parameter 'database' is set
        if (database == null) {
            throw new ApiException("Missing the required parameter 'database' when calling auditTrailPurge(Async)");
        }
        

        okhttp3.Call localVarCall = auditTrailPurgeCall(application, database, olderthan, _callback);
        return localVarCall;

    }

    /**
     * Delete Audit Data
     * &lt;p&gt;Deletes audit trail data older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit data deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void auditTrailPurge(String application, String database, Long olderthan) throws ApiException {
        auditTrailPurgeWithHttpInfo(application, database, olderthan);
    }

    /**
     * Delete Audit Data
     * &lt;p&gt;Deletes audit trail data older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit data deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> auditTrailPurgeWithHttpInfo(String application, String database, Long olderthan) throws ApiException {
        okhttp3.Call localVarCall = auditTrailPurgeValidateBeforeCall(application, database, olderthan, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Audit Data (asynchronously)
     * &lt;p&gt;Deletes audit trail data older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit data deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit data.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailPurgeAsync(String application, String database, Long olderthan, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = auditTrailPurgeValidateBeforeCall(application, database, olderthan, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for auditTrailPurgeMetadataAudit
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit metadata deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailPurgeMetadataAuditCall(String application, String database, Long olderthan, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{application}/databases/{database}/audittrail/metadata"
            .replaceAll("\\{" + "application" + "\\}", localVarApiClient.escapeString(application.toString()))
            .replaceAll("\\{" + "database" + "\\}", localVarApiClient.escapeString(database.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (olderthan != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("olderthan", olderthan));
        }

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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call auditTrailPurgeMetadataAuditValidateBeforeCall(String application, String database, Long olderthan, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'application' is set
        if (application == null) {
            throw new ApiException("Missing the required parameter 'application' when calling auditTrailPurgeMetadataAudit(Async)");
        }
        
        // verify the required parameter 'database' is set
        if (database == null) {
            throw new ApiException("Missing the required parameter 'database' when calling auditTrailPurgeMetadataAudit(Async)");
        }
        

        okhttp3.Call localVarCall = auditTrailPurgeMetadataAuditCall(application, database, olderthan, _callback);
        return localVarCall;

    }

    /**
     * Delete Audit Metadata
     * &lt;p&gt;Deletes audit trail metadata older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit metadata deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void auditTrailPurgeMetadataAudit(String application, String database, Long olderthan) throws ApiException {
        auditTrailPurgeMetadataAuditWithHttpInfo(application, database, olderthan);
    }

    /**
     * Delete Audit Metadata
     * &lt;p&gt;Deletes audit trail metadata older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit metadata deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> auditTrailPurgeMetadataAuditWithHttpInfo(String application, String database, Long olderthan) throws ApiException {
        okhttp3.Call localVarCall = auditTrailPurgeMetadataAuditValidateBeforeCall(application, database, olderthan, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Audit Metadata (asynchronously)
     * &lt;p&gt;Deletes audit trail metadata older than the specified time.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param olderthan &lt;p&gt;Time in milliseconds.&lt;/p&gt; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Audit metadata deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;Failed to delete audit metadata.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call auditTrailPurgeMetadataAuditAsync(String application, String database, Long olderthan, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = auditTrailPurgeMetadataAuditValidateBeforeCall(application, database, olderthan, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}