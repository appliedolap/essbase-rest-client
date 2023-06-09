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


import com.appliedolap.essbase.client.model.SandboxDetail;
import com.appliedolap.essbase.client.model.SandboxRequestPayload;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SandboxDimensionApi {
    private ApiClient localVarApiClient;

    public SandboxDimensionApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SandboxDimensionApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for sandboxDimensionAddMembers
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Size of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox members added successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to add sandbox members. The application or database name may be incorrect, or scenarios may not be enabled.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionAddMembersCall(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/databases/{databaseName}/sandbox/members"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()))
            .replaceAll("\\{" + "databaseName" + "\\}", localVarApiClient.escapeString(databaseName.toString()));

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

        String[] localVarAuthNames = new String[] { "basicAuth" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sandboxDimensionAddMembersValidateBeforeCall(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling sandboxDimensionAddMembers(Async)");
        }
        
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new ApiException("Missing the required parameter 'databaseName' when calling sandboxDimensionAddMembers(Async)");
        }
        

        okhttp3.Call localVarCall = sandboxDimensionAddMembersCall(applicationName, databaseName, body, _callback);
        return localVarCall;

    }

    /**
     * Add Sandbox Members
     * &lt;p&gt;Add members to an existing sandbox dimension.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Size of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @return SandboxDetail
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox members added successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to add sandbox members. The application or database name may be incorrect, or scenarios may not be enabled.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public SandboxDetail sandboxDimensionAddMembers(String applicationName, String databaseName, SandboxRequestPayload body) throws ApiException {
        ApiResponse<SandboxDetail> localVarResp = sandboxDimensionAddMembersWithHttpInfo(applicationName, databaseName, body);
        return localVarResp.getData();
    }

    /**
     * Add Sandbox Members
     * &lt;p&gt;Add members to an existing sandbox dimension.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Size of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @return ApiResponse&lt;SandboxDetail&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox members added successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to add sandbox members. The application or database name may be incorrect, or scenarios may not be enabled.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SandboxDetail> sandboxDimensionAddMembersWithHttpInfo(String applicationName, String databaseName, SandboxRequestPayload body) throws ApiException {
        okhttp3.Call localVarCall = sandboxDimensionAddMembersValidateBeforeCall(applicationName, databaseName, body, null);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Add Sandbox Members (asynchronously)
     * &lt;p&gt;Add members to an existing sandbox dimension.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Size of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox members added successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to add sandbox members. The application or database name may be incorrect, or scenarios may not be enabled.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionAddMembersAsync(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback<SandboxDetail> _callback) throws ApiException {

        okhttp3.Call localVarCall = sandboxDimensionAddMembersValidateBeforeCall(applicationName, databaseName, body, _callback);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for sandboxDimensionCreate
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Maximum count of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox created successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed create sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionCreateCall(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/databases/{databaseName}/sandbox"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()))
            .replaceAll("\\{" + "databaseName" + "\\}", localVarApiClient.escapeString(databaseName.toString()));

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

        String[] localVarAuthNames = new String[] { "basicAuth" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sandboxDimensionCreateValidateBeforeCall(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling sandboxDimensionCreate(Async)");
        }
        
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new ApiException("Missing the required parameter 'databaseName' when calling sandboxDimensionCreate(Async)");
        }
        

        okhttp3.Call localVarCall = sandboxDimensionCreateCall(applicationName, databaseName, body, _callback);
        return localVarCall;

    }

    /**
     * Create Sandbox
     * &lt;p&gt;Create Sandbox and CellProperties dimensions, while at the same time enabling scenario management for this cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Maximum count of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @return SandboxDetail
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox created successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed create sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public SandboxDetail sandboxDimensionCreate(String applicationName, String databaseName, SandboxRequestPayload body) throws ApiException {
        ApiResponse<SandboxDetail> localVarResp = sandboxDimensionCreateWithHttpInfo(applicationName, databaseName, body);
        return localVarResp.getData();
    }

    /**
     * Create Sandbox
     * &lt;p&gt;Create Sandbox and CellProperties dimensions, while at the same time enabling scenario management for this cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Maximum count of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @return ApiResponse&lt;SandboxDetail&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox created successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed create sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SandboxDetail> sandboxDimensionCreateWithHttpInfo(String applicationName, String databaseName, SandboxRequestPayload body) throws ApiException {
        okhttp3.Call localVarCall = sandboxDimensionCreateValidateBeforeCall(applicationName, databaseName, body, null);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Create Sandbox (asynchronously)
     * &lt;p&gt;Create Sandbox and CellProperties dimensions, while at the same time enabling scenario management for this cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param body &lt;p&gt;Maximum count of sandbox dimension members. Default is 100 if body is empty.&lt;/p&gt; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox created successfully; includes links to sandbox details.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed create sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionCreateAsync(String applicationName, String databaseName, SandboxRequestPayload body, final ApiCallback<SandboxDetail> _callback) throws ApiException {

        okhttp3.Call localVarCall = sandboxDimensionCreateValidateBeforeCall(applicationName, databaseName, body, _callback);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for sandboxDimensionDelete
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to delete sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionDeleteCall(String applicationName, String databaseName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/databases/{databaseName}/sandbox"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()))
            .replaceAll("\\{" + "databaseName" + "\\}", localVarApiClient.escapeString(databaseName.toString()));

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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sandboxDimensionDeleteValidateBeforeCall(String applicationName, String databaseName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling sandboxDimensionDelete(Async)");
        }
        
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new ApiException("Missing the required parameter 'databaseName' when calling sandboxDimensionDelete(Async)");
        }
        

        okhttp3.Call localVarCall = sandboxDimensionDeleteCall(applicationName, databaseName, _callback);
        return localVarCall;

    }

    /**
     * Delete Sandbox
     * &lt;p&gt;Delete Sandbox and CellProperties dimensions from this cube. This action disables scenario management for the cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to delete sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void sandboxDimensionDelete(String applicationName, String databaseName) throws ApiException {
        sandboxDimensionDeleteWithHttpInfo(applicationName, databaseName);
    }

    /**
     * Delete Sandbox
     * &lt;p&gt;Delete Sandbox and CellProperties dimensions from this cube. This action disables scenario management for the cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to delete sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> sandboxDimensionDeleteWithHttpInfo(String applicationName, String databaseName) throws ApiException {
        okhttp3.Call localVarCall = sandboxDimensionDeleteValidateBeforeCall(applicationName, databaseName, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Sandbox (asynchronously)
     * &lt;p&gt;Delete Sandbox and CellProperties dimensions from this cube. This action disables scenario management for the cube.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox deleted successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to delete sandbox. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionDeleteAsync(String applicationName, String databaseName, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = sandboxDimensionDeleteValidateBeforeCall(applicationName, databaseName, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for sandboxDimensionGet
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox details returned successfully; includes total sandbox members, available members, and assigned member.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to get sandbox details. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionGetCall(String applicationName, String databaseName, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/applications/{applicationName}/databases/{databaseName}/sandbox"
            .replaceAll("\\{" + "applicationName" + "\\}", localVarApiClient.escapeString(applicationName.toString()))
            .replaceAll("\\{" + "databaseName" + "\\}", localVarApiClient.escapeString(databaseName.toString()));

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
    private okhttp3.Call sandboxDimensionGetValidateBeforeCall(String applicationName, String databaseName, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'applicationName' is set
        if (applicationName == null) {
            throw new ApiException("Missing the required parameter 'applicationName' when calling sandboxDimensionGet(Async)");
        }
        
        // verify the required parameter 'databaseName' is set
        if (databaseName == null) {
            throw new ApiException("Missing the required parameter 'databaseName' when calling sandboxDimensionGet(Async)");
        }
        

        okhttp3.Call localVarCall = sandboxDimensionGetCall(applicationName, databaseName, _callback);
        return localVarCall;

    }

    /**
     * Get Sandbox Details
     * &lt;p&gt;Return details about sandbox members.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return SandboxDetail
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox details returned successfully; includes total sandbox members, available members, and assigned member.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to get sandbox details. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public SandboxDetail sandboxDimensionGet(String applicationName, String databaseName) throws ApiException {
        ApiResponse<SandboxDetail> localVarResp = sandboxDimensionGetWithHttpInfo(applicationName, databaseName);
        return localVarResp.getData();
    }

    /**
     * Get Sandbox Details
     * &lt;p&gt;Return details about sandbox members.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @return ApiResponse&lt;SandboxDetail&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox details returned successfully; includes total sandbox members, available members, and assigned member.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to get sandbox details. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SandboxDetail> sandboxDimensionGetWithHttpInfo(String applicationName, String databaseName) throws ApiException {
        okhttp3.Call localVarCall = sandboxDimensionGetValidateBeforeCall(applicationName, databaseName, null);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Sandbox Details (asynchronously)
     * &lt;p&gt;Return details about sandbox members.&lt;/p&gt;
     * @param applicationName &lt;p&gt;Application name.&lt;/p&gt; (required)
     * @param databaseName &lt;p&gt;Database name.&lt;/p&gt; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Sandbox details returned successfully; includes total sandbox members, available members, and assigned member.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Failed to get sandbox details. The application or database name may be incorrect, or the logged in user may not have the appropriate application role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sandboxDimensionGetAsync(String applicationName, String databaseName, final ApiCallback<SandboxDetail> _callback) throws ApiException {

        okhttp3.Call localVarCall = sandboxDimensionGetValidateBeforeCall(applicationName, databaseName, _callback);
        Type localVarReturnType = new TypeToken<SandboxDetail>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
