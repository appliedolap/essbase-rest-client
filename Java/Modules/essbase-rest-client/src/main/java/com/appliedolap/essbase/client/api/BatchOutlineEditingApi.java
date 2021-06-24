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


import com.appliedolap.essbase.client.model.BOEOutput;
import com.appliedolap.essbase.client.model.OtlEditMain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchOutlineEditingApi {
    private ApiClient localVarApiClient;

    public BatchOutlineEditingApi() {
        this(Configuration.getDefaultApiClient());
    }

    public BatchOutlineEditingApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for batchOutlineEditingExecute
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body Batch outline JSON/XML (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The batch outline edit completed successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to complete batch outline editing. The output may be invalid, the sequence of metadata operations may be incorrect, or saving the outline may have failed.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call batchOutlineEditingExecuteCall(String application, String database, OtlEditMain body, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/applications/{application}/databases/{database}/boe"
            .replaceAll("\\{" + "application" + "\\}", localVarApiClient.escapeString(application.toString()))
            .replaceAll("\\{" + "database" + "\\}", localVarApiClient.escapeString(database.toString()));

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
            "application/json", "application/xml"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call batchOutlineEditingExecuteValidateBeforeCall(String application, String database, OtlEditMain body, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'application' is set
        if (application == null) {
            throw new ApiException("Missing the required parameter 'application' when calling batchOutlineEditingExecute(Async)");
        }
        
        // verify the required parameter 'database' is set
        if (database == null) {
            throw new ApiException("Missing the required parameter 'database' when calling batchOutlineEditingExecute(Async)");
        }
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling batchOutlineEditingExecute(Async)");
        }
        

        okhttp3.Call localVarCall = batchOutlineEditingExecuteCall(application, database, body, _callback);
        return localVarCall;

    }

    /**
     * Run Batch Outline Edit
     * &lt;p&gt;Executes batch outline editing process. Based on the XML or JSON body, adds or removes members from the outline in the active cube.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body Batch outline JSON/XML (required)
     * @return BOEOutput
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The batch outline edit completed successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to complete batch outline editing. The output may be invalid, the sequence of metadata operations may be incorrect, or saving the outline may have failed.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public BOEOutput batchOutlineEditingExecute(String application, String database, OtlEditMain body) throws ApiException {
        ApiResponse<BOEOutput> localVarResp = batchOutlineEditingExecuteWithHttpInfo(application, database, body);
        return localVarResp.getData();
    }

    /**
     * Run Batch Outline Edit
     * &lt;p&gt;Executes batch outline editing process. Based on the XML or JSON body, adds or removes members from the outline in the active cube.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body Batch outline JSON/XML (required)
     * @return ApiResponse&lt;BOEOutput&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The batch outline edit completed successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to complete batch outline editing. The output may be invalid, the sequence of metadata operations may be incorrect, or saving the outline may have failed.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<BOEOutput> batchOutlineEditingExecuteWithHttpInfo(String application, String database, OtlEditMain body) throws ApiException {
        okhttp3.Call localVarCall = batchOutlineEditingExecuteValidateBeforeCall(application, database, body, null);
        Type localVarReturnType = new TypeToken<BOEOutput>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Run Batch Outline Edit (asynchronously)
     * &lt;p&gt;Executes batch outline editing process. Based on the XML or JSON body, adds or removes members from the outline in the active cube.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body Batch outline JSON/XML (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The batch outline edit completed successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to complete batch outline editing. The output may be invalid, the sequence of metadata operations may be incorrect, or saving the outline may have failed.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call batchOutlineEditingExecuteAsync(String application, String database, OtlEditMain body, final ApiCallback<BOEOutput> _callback) throws ApiException {

        okhttp3.Call localVarCall = batchOutlineEditingExecuteValidateBeforeCall(application, database, body, _callback);
        Type localVarReturnType = new TypeToken<BOEOutput>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
