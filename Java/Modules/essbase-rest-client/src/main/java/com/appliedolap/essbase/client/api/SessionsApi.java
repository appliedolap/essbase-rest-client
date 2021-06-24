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


import com.appliedolap.essbase.client.model.SessionAttributes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionsApi {
    private ApiClient localVarApiClient;

    public SessionsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public SessionsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for sessionsDeleteAllActiveSessions
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (optional)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (optional)
     * @param userId &lt;p&gt;User ID.&lt;/p&gt; (optional)
     * @param disconnect &lt;p&gt;Disconnect value (Boolean). If false, the request is killed. Otherwise, the session is logged off.&lt;/p&gt; (optional, default to false)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;1. Deletes all the sessions for the parameter &#39;application&#39;, &#39;database&#39; and &#39;userid&#39; provided. If no parameters are specified, deletes all active sessions.&lt;/p&gt;&lt;p&gt;2. Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsDeleteAllActiveSessionsCall(String application, String database, String userId, Boolean disconnect, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/sessions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (application != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("application", application));
        }

        if (database != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("database", database));
        }

        if (userId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("userId", userId));
        }

        if (disconnect != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("disconnect", disconnect));
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

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sessionsDeleteAllActiveSessionsValidateBeforeCall(String application, String database, String userId, Boolean disconnect, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = sessionsDeleteAllActiveSessionsCall(application, database, userId, disconnect, _callback);
        return localVarCall;

    }

    /**
     * Delete All Sessions
     * &lt;p&gt;Deletes all the sessions currently active, or kills all the requests currently processing.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (optional)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (optional)
     * @param userId &lt;p&gt;User ID.&lt;/p&gt; (optional)
     * @param disconnect &lt;p&gt;Disconnect value (Boolean). If false, the request is killed. Otherwise, the session is logged off.&lt;/p&gt; (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;1. Deletes all the sessions for the parameter &#39;application&#39;, &#39;database&#39; and &#39;userid&#39; provided. If no parameters are specified, deletes all active sessions.&lt;/p&gt;&lt;p&gt;2. Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void sessionsDeleteAllActiveSessions(String application, String database, String userId, Boolean disconnect) throws ApiException {
        sessionsDeleteAllActiveSessionsWithHttpInfo(application, database, userId, disconnect);
    }

    /**
     * Delete All Sessions
     * &lt;p&gt;Deletes all the sessions currently active, or kills all the requests currently processing.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (optional)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (optional)
     * @param userId &lt;p&gt;User ID.&lt;/p&gt; (optional)
     * @param disconnect &lt;p&gt;Disconnect value (Boolean). If false, the request is killed. Otherwise, the session is logged off.&lt;/p&gt; (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;1. Deletes all the sessions for the parameter &#39;application&#39;, &#39;database&#39; and &#39;userid&#39; provided. If no parameters are specified, deletes all active sessions.&lt;/p&gt;&lt;p&gt;2. Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> sessionsDeleteAllActiveSessionsWithHttpInfo(String application, String database, String userId, Boolean disconnect) throws ApiException {
        okhttp3.Call localVarCall = sessionsDeleteAllActiveSessionsValidateBeforeCall(application, database, userId, disconnect, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete All Sessions (asynchronously)
     * &lt;p&gt;Deletes all the sessions currently active, or kills all the requests currently processing.&lt;/p&gt;
     * @param application &lt;p&gt;Application name.&lt;/p&gt; (optional)
     * @param database &lt;p&gt;Database name.&lt;/p&gt; (optional)
     * @param userId &lt;p&gt;User ID.&lt;/p&gt; (optional)
     * @param disconnect &lt;p&gt;Disconnect value (Boolean). If false, the request is killed. Otherwise, the session is logged off.&lt;/p&gt; (optional, default to false)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;1. Deletes all the sessions for the parameter &#39;application&#39;, &#39;database&#39; and &#39;userid&#39; provided. If no parameters are specified, deletes all active sessions.&lt;/p&gt;&lt;p&gt;2. Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsDeleteAllActiveSessionsAsync(String application, String database, String userId, Boolean disconnect, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = sessionsDeleteAllActiveSessionsValidateBeforeCall(application, database, userId, disconnect, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for sessionsDeleteSessionWithId
     * @param sessionId &lt;p&gt;sessionId of the session to be disconnected or request killed.&lt;/p&gt; (required)
     * @param disconnect &lt;p&gt;Disconnection value. Default is false, meaning kill the request. If true, disconnect the user session.&lt;/p&gt; (optional, default to false)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Session or request terminated successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;ol&gt;&lt;li&gt;Essbase or platform security exception.&lt;/li&gt;&lt;li&gt;If the sessionId is incorrect, &lt;code&gt;Error: No session with specified login id.&lt;/code&gt;&lt;/li&gt;&lt;li&gt;Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation&lt;/li&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsDeleteSessionWithIdCall(Long sessionId, Boolean disconnect, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/sessions/{sessionId}"
            .replaceAll("\\{" + "sessionId" + "\\}", localVarApiClient.escapeString(sessionId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (disconnect != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("disconnect", disconnect));
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

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sessionsDeleteSessionWithIdValidateBeforeCall(Long sessionId, Boolean disconnect, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'sessionId' is set
        if (sessionId == null) {
            throw new ApiException("Missing the required parameter 'sessionId' when calling sessionsDeleteSessionWithId(Async)");
        }
        

        okhttp3.Call localVarCall = sessionsDeleteSessionWithIdCall(sessionId, disconnect, _callback);
        return localVarCall;

    }

    /**
     * Delete Session By ID
     * &lt;p&gt;Delete a particular session or kill a particular request using the session id.&lt;/p&gt;
     * @param sessionId &lt;p&gt;sessionId of the session to be disconnected or request killed.&lt;/p&gt; (required)
     * @param disconnect &lt;p&gt;Disconnection value. Default is false, meaning kill the request. If true, disconnect the user session.&lt;/p&gt; (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Session or request terminated successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;ol&gt;&lt;li&gt;Essbase or platform security exception.&lt;/li&gt;&lt;li&gt;If the sessionId is incorrect, &lt;code&gt;Error: No session with specified login id.&lt;/code&gt;&lt;/li&gt;&lt;li&gt;Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation&lt;/li&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void sessionsDeleteSessionWithId(Long sessionId, Boolean disconnect) throws ApiException {
        sessionsDeleteSessionWithIdWithHttpInfo(sessionId, disconnect);
    }

    /**
     * Delete Session By ID
     * &lt;p&gt;Delete a particular session or kill a particular request using the session id.&lt;/p&gt;
     * @param sessionId &lt;p&gt;sessionId of the session to be disconnected or request killed.&lt;/p&gt; (required)
     * @param disconnect &lt;p&gt;Disconnection value. Default is false, meaning kill the request. If true, disconnect the user session.&lt;/p&gt; (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Session or request terminated successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;ol&gt;&lt;li&gt;Essbase or platform security exception.&lt;/li&gt;&lt;li&gt;If the sessionId is incorrect, &lt;code&gt;Error: No session with specified login id.&lt;/code&gt;&lt;/li&gt;&lt;li&gt;Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation&lt;/li&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> sessionsDeleteSessionWithIdWithHttpInfo(Long sessionId, Boolean disconnect) throws ApiException {
        okhttp3.Call localVarCall = sessionsDeleteSessionWithIdValidateBeforeCall(sessionId, disconnect, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Delete Session By ID (asynchronously)
     * &lt;p&gt;Delete a particular session or kill a particular request using the session id.&lt;/p&gt;
     * @param sessionId &lt;p&gt;sessionId of the session to be disconnected or request killed.&lt;/p&gt; (required)
     * @param disconnect &lt;p&gt;Disconnection value. Default is false, meaning kill the request. If true, disconnect the user session.&lt;/p&gt; (optional, default to false)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Session or request terminated successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;ol&gt;&lt;li&gt;Essbase or platform security exception.&lt;/li&gt;&lt;li&gt;If the sessionId is incorrect, &lt;code&gt;Error: No session with specified login id.&lt;/code&gt;&lt;/li&gt;&lt;li&gt;Cannot disconnect user. Essbase Error(1051041): Insufficient privilege for this operation&lt;/li&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsDeleteSessionWithIdAsync(Long sessionId, Boolean disconnect, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = sessionsDeleteSessionWithIdValidateBeforeCall(sessionId, disconnect, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for sessionsGetAllActiveSessions
     * @param application application name on which request is performed (optional)
     * @param database database name on which request is performed (optional)
     * @param userId user id for which we need all the active sessions. If not provided then by default all the sesions will be retrieved (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> returns list session attributes objects having &#39;user id&#39;, &#39;session id&#39;, &#39;login time&#39; &#39;connection source&#39; etc. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsGetAllActiveSessionsCall(String application, String database, String userId, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/sessions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (application != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("application", application));
        }

        if (database != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("database", database));
        }

        if (userId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("userId", userId));
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

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sessionsGetAllActiveSessionsValidateBeforeCall(String application, String database, String userId, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = sessionsGetAllActiveSessionsCall(application, database, userId, _callback);
        return localVarCall;

    }

    /**
     * List Sessions
     * Return list of session currently active for a user or request
     * @param application application name on which request is performed (optional)
     * @param database database name on which request is performed (optional)
     * @param userId user id for which we need all the active sessions. If not provided then by default all the sesions will be retrieved (optional)
     * @return List&lt;SessionAttributes&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> returns list session attributes objects having &#39;user id&#39;, &#39;session id&#39;, &#39;login time&#39; &#39;connection source&#39; etc. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public List<SessionAttributes> sessionsGetAllActiveSessions(String application, String database, String userId) throws ApiException {
        ApiResponse<List<SessionAttributes>> localVarResp = sessionsGetAllActiveSessionsWithHttpInfo(application, database, userId);
        return localVarResp.getData();
    }

    /**
     * List Sessions
     * Return list of session currently active for a user or request
     * @param application application name on which request is performed (optional)
     * @param database database name on which request is performed (optional)
     * @param userId user id for which we need all the active sessions. If not provided then by default all the sesions will be retrieved (optional)
     * @return ApiResponse&lt;List&lt;SessionAttributes&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> returns list session attributes objects having &#39;user id&#39;, &#39;session id&#39;, &#39;login time&#39; &#39;connection source&#39; etc. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<SessionAttributes>> sessionsGetAllActiveSessionsWithHttpInfo(String application, String database, String userId) throws ApiException {
        okhttp3.Call localVarCall = sessionsGetAllActiveSessionsValidateBeforeCall(application, database, userId, null);
        Type localVarReturnType = new TypeToken<List<SessionAttributes>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * List Sessions (asynchronously)
     * Return list of session currently active for a user or request
     * @param application application name on which request is performed (optional)
     * @param database database name on which request is performed (optional)
     * @param userId user id for which we need all the active sessions. If not provided then by default all the sesions will be retrieved (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> returns list session attributes objects having &#39;user id&#39;, &#39;session id&#39;, &#39;login time&#39; &#39;connection source&#39; etc. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Essbase or platform security exception.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sessionsGetAllActiveSessionsAsync(String application, String database, String userId, final ApiCallback<List<SessionAttributes>> _callback) throws ApiException {

        okhttp3.Call localVarCall = sessionsGetAllActiveSessionsValidateBeforeCall(application, database, userId, _callback);
        Type localVarReturnType = new TypeToken<List<SessionAttributes>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
