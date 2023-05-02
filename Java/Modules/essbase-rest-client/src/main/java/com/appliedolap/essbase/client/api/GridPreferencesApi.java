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


import com.appliedolap.essbase.client.model.Preferences;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridPreferencesApi {
    private ApiClient localVarApiClient;

    public GridPreferencesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public GridPreferencesApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for gridPreferencesGet
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Grid Preferences </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call gridPreferencesGetCall(final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/preferences/grid";

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
    private okhttp3.Call gridPreferencesGetValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = gridPreferencesGetCall(_callback);
        return localVarCall;

    }

    /**
     * Get Grid Preferences
     * Api returns the grid preference from session.
     * @return Preferences
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Grid Preferences </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public Preferences gridPreferencesGet() throws ApiException {
        ApiResponse<Preferences> localVarResp = gridPreferencesGetWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Get Grid Preferences
     * Api returns the grid preference from session.
     * @return ApiResponse&lt;Preferences&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Grid Preferences </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Preferences> gridPreferencesGetWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = gridPreferencesGetValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<Preferences>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Grid Preferences (asynchronously)
     * Api returns the grid preference from session.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Grid Preferences </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call gridPreferencesGetAsync(final ApiCallback<Preferences> _callback) throws ApiException {

        okhttp3.Call localVarCall = gridPreferencesGetValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<Preferences>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for gridPreferencesSet
     * @param body Preference to be store (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> ok </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call gridPreferencesSetCall(Preferences body, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/preferences/grid";

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
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call gridPreferencesSetValidateBeforeCall(Preferences body, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new ApiException("Missing the required parameter 'body' when calling gridPreferencesSet(Async)");
        }
        

        okhttp3.Call localVarCall = gridPreferencesSetCall(body, _callback);
        return localVarCall;

    }

    /**
     * Set Grid Preferences
     * Api store the preferences in current session.
     * @param body Preference to be store (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> ok </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void gridPreferencesSet(Preferences body) throws ApiException {
        gridPreferencesSetWithHttpInfo(body);
    }

    /**
     * Set Grid Preferences
     * Api store the preferences in current session.
     * @param body Preference to be store (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> ok </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> gridPreferencesSetWithHttpInfo(Preferences body) throws ApiException {
        okhttp3.Call localVarCall = gridPreferencesSetValidateBeforeCall(body, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Set Grid Preferences (asynchronously)
     * Api store the preferences in current session.
     * @param body Preference to be store (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> ok </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation failed. For example, Invalid session. </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call gridPreferencesSetAsync(Preferences body, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = gridPreferencesSetValidateBeforeCall(body, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}