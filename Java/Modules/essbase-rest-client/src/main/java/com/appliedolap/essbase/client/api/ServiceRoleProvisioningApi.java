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


import com.appliedolap.essbase.client.model.UserGroupProvisionInfo;
import com.appliedolap.essbase.client.model.UserGroupProvisionInfoList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRoleProvisioningApi {
    private ApiClient localVarApiClient;

    public ServiceRoleProvisioningApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ServiceRoleProvisioningApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for serviceRoleProvisioningDeprovision
     * @param id User or group ID. (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Role deprovisioned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningDeprovisionCall(String id, Boolean group, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/permissions/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (group != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("group", group));
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
    private okhttp3.Call serviceRoleProvisioningDeprovisionValidateBeforeCall(String id, Boolean group, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling serviceRoleProvisioningDeprovision(Async)");
        }
        

        okhttp3.Call localVarCall = serviceRoleProvisioningDeprovisionCall(id, group, _callback);
        return localVarCall;

    }

    /**
     * Deprovision
     * Deprovision single user or group from a service role.
     * @param id User or group ID. (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Role deprovisioned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void serviceRoleProvisioningDeprovision(String id, Boolean group) throws ApiException {
        serviceRoleProvisioningDeprovisionWithHttpInfo(id, group);
    }

    /**
     * Deprovision
     * Deprovision single user or group from a service role.
     * @param id User or group ID. (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Role deprovisioned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> serviceRoleProvisioningDeprovisionWithHttpInfo(String id, Boolean group) throws ApiException {
        okhttp3.Call localVarCall = serviceRoleProvisioningDeprovisionValidateBeforeCall(id, group, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Deprovision (asynchronously)
     * Deprovision single user or group from a service role.
     * @param id User or group ID. (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Role deprovisioned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningDeprovisionAsync(String id, Boolean group, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = serviceRoleProvisioningDeprovisionValidateBeforeCall(id, group, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for serviceRoleProvisioningGetProvision
     * @param id &lt;p&gt;User or group ID.&lt;/p&gt; (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Provisioning information returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningGetProvisionCall(String id, Boolean group, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/permissions/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (group != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("group", group));
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
    private okhttp3.Call serviceRoleProvisioningGetProvisionValidateBeforeCall(String id, Boolean group, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling serviceRoleProvisioningGetProvision(Async)");
        }
        

        okhttp3.Call localVarCall = serviceRoleProvisioningGetProvisionCall(id, group, _callback);
        return localVarCall;

    }

    /**
     * Get Provision
     * &lt;p&gt;Get service role provisioning information.&lt;/p&gt;
     * @param id &lt;p&gt;User or group ID.&lt;/p&gt; (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @return UserGroupProvisionInfo
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Provisioning information returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public UserGroupProvisionInfo serviceRoleProvisioningGetProvision(String id, Boolean group) throws ApiException {
        ApiResponse<UserGroupProvisionInfo> localVarResp = serviceRoleProvisioningGetProvisionWithHttpInfo(id, group);
        return localVarResp.getData();
    }

    /**
     * Get Provision
     * &lt;p&gt;Get service role provisioning information.&lt;/p&gt;
     * @param id &lt;p&gt;User or group ID.&lt;/p&gt; (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @return ApiResponse&lt;UserGroupProvisionInfo&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Provisioning information returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserGroupProvisionInfo> serviceRoleProvisioningGetProvisionWithHttpInfo(String id, Boolean group) throws ApiException {
        okhttp3.Call localVarCall = serviceRoleProvisioningGetProvisionValidateBeforeCall(id, group, null);
        Type localVarReturnType = new TypeToken<UserGroupProvisionInfo>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get Provision (asynchronously)
     * &lt;p&gt;Get service role provisioning information.&lt;/p&gt;
     * @param id &lt;p&gt;User or group ID.&lt;/p&gt; (required)
     * @param group &lt;p&gt;If true, ID is for a group. If false, ID is for a user. Default is false (ID is considered to be for a user.)&lt;/p&gt; (optional, default to false)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> &lt;p&gt;&lt;strong&gt;OK&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;Provisioning information returned successfully.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningGetProvisionAsync(String id, Boolean group, final ApiCallback<UserGroupProvisionInfo> _callback) throws ApiException {

        okhttp3.Call localVarCall = serviceRoleProvisioningGetProvisionValidateBeforeCall(id, group, _callback);
        Type localVarReturnType = new TypeToken<UserGroupProvisionInfo>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for serviceRoleProvisioningProvision
     * @param id User or group ID. (required)
     * @param body User or group provisioning information. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Role provision is successful </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningProvisionCall(String id, UserGroupProvisionInfo body, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = body;

        // create path and map variables
        String localVarPath = "/permissions/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

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
            "application/json", "application/xml"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call serviceRoleProvisioningProvisionValidateBeforeCall(String id, UserGroupProvisionInfo body, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling serviceRoleProvisioningProvision(Async)");
        }
        

        okhttp3.Call localVarCall = serviceRoleProvisioningProvisionCall(id, body, _callback);
        return localVarCall;

    }

    /**
     * Provision User or Group
     * &lt;p&gt;Provision a single user or group for a service role.&lt;/p&gt;
     * @param id User or group ID. (required)
     * @param body User or group provisioning information. (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Role provision is successful </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public void serviceRoleProvisioningProvision(String id, UserGroupProvisionInfo body) throws ApiException {
        serviceRoleProvisioningProvisionWithHttpInfo(id, body);
    }

    /**
     * Provision User or Group
     * &lt;p&gt;Provision a single user or group for a service role.&lt;/p&gt;
     * @param id User or group ID. (required)
     * @param body User or group provisioning information. (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Role provision is successful </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> serviceRoleProvisioningProvisionWithHttpInfo(String id, UserGroupProvisionInfo body) throws ApiException {
        okhttp3.Call localVarCall = serviceRoleProvisioningProvisionValidateBeforeCall(id, body, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Provision User or Group (asynchronously)
     * &lt;p&gt;Provision a single user or group for a service role.&lt;/p&gt;
     * @param id User or group ID. (required)
     * @param body User or group provisioning information. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Role provision is successful </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningProvisionAsync(String id, UserGroupProvisionInfo body, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = serviceRoleProvisioningProvisionValidateBeforeCall(id, body, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for serviceRoleProvisioningSearchProvision
     * @param id &lt;p&gt;User or group ID wildcard pattern. if specified, returns users and groups matching the pattern, if not specified, returns all the users and groups having some role. Users or groups having no role are not returned.&lt;/p&gt; (optional, default to *)
     * @param role &lt;p&gt;Input may include &lt;code&gt;all&lt;/code&gt;, &lt;code&gt;none&lt;/code&gt;, or a comma-separated list of roles (for example, &lt;code&gt;service_administrator&lt;/code&gt;, &lt;code&gt;power_user&lt;/code&gt;, or &lt;code&gt;user&lt;/code&gt;). Default value is &lt;code&gt;all&lt;/code&gt;, so if this query parameter is not specified, all users and groups having some role are returned. If &lt;code&gt;none&lt;/code&gt; is specified, only users and groups having no role will be returned. If named roles are specified, then only users and groups having any of the named roles are returned.&lt;/p&gt; (optional, default to all)
     * @param filter all/group/user. Default value is all so if this query parameter is not specified then all users/groups will be returned. (optional, default to all)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> provisioning information for users/groups matching with search parameters </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningSearchProvisionCall(String id, String role, String filter, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/permissions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (id != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("id", id));
        }

        if (role != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("role", role));
        }

        if (filter != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("filter", filter));
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
    private okhttp3.Call serviceRoleProvisioningSearchProvisionValidateBeforeCall(String id, String role, String filter, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = serviceRoleProvisioningSearchProvisionCall(id, role, filter, _callback);
        return localVarCall;

    }

    /**
     * Search Provision
     * Search provisioning information.
     * @param id &lt;p&gt;User or group ID wildcard pattern. if specified, returns users and groups matching the pattern, if not specified, returns all the users and groups having some role. Users or groups having no role are not returned.&lt;/p&gt; (optional, default to *)
     * @param role &lt;p&gt;Input may include &lt;code&gt;all&lt;/code&gt;, &lt;code&gt;none&lt;/code&gt;, or a comma-separated list of roles (for example, &lt;code&gt;service_administrator&lt;/code&gt;, &lt;code&gt;power_user&lt;/code&gt;, or &lt;code&gt;user&lt;/code&gt;). Default value is &lt;code&gt;all&lt;/code&gt;, so if this query parameter is not specified, all users and groups having some role are returned. If &lt;code&gt;none&lt;/code&gt; is specified, only users and groups having no role will be returned. If named roles are specified, then only users and groups having any of the named roles are returned.&lt;/p&gt; (optional, default to all)
     * @param filter all/group/user. Default value is all so if this query parameter is not specified then all users/groups will be returned. (optional, default to all)
     * @return UserGroupProvisionInfoList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> provisioning information for users/groups matching with search parameters </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public UserGroupProvisionInfoList serviceRoleProvisioningSearchProvision(String id, String role, String filter) throws ApiException {
        ApiResponse<UserGroupProvisionInfoList> localVarResp = serviceRoleProvisioningSearchProvisionWithHttpInfo(id, role, filter);
        return localVarResp.getData();
    }

    /**
     * Search Provision
     * Search provisioning information.
     * @param id &lt;p&gt;User or group ID wildcard pattern. if specified, returns users and groups matching the pattern, if not specified, returns all the users and groups having some role. Users or groups having no role are not returned.&lt;/p&gt; (optional, default to *)
     * @param role &lt;p&gt;Input may include &lt;code&gt;all&lt;/code&gt;, &lt;code&gt;none&lt;/code&gt;, or a comma-separated list of roles (for example, &lt;code&gt;service_administrator&lt;/code&gt;, &lt;code&gt;power_user&lt;/code&gt;, or &lt;code&gt;user&lt;/code&gt;). Default value is &lt;code&gt;all&lt;/code&gt;, so if this query parameter is not specified, all users and groups having some role are returned. If &lt;code&gt;none&lt;/code&gt; is specified, only users and groups having no role will be returned. If named roles are specified, then only users and groups having any of the named roles are returned.&lt;/p&gt; (optional, default to all)
     * @param filter all/group/user. Default value is all so if this query parameter is not specified then all users/groups will be returned. (optional, default to all)
     * @return ApiResponse&lt;UserGroupProvisionInfoList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> provisioning information for users/groups matching with search parameters </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UserGroupProvisionInfoList> serviceRoleProvisioningSearchProvisionWithHttpInfo(String id, String role, String filter) throws ApiException {
        okhttp3.Call localVarCall = serviceRoleProvisioningSearchProvisionValidateBeforeCall(id, role, filter, null);
        Type localVarReturnType = new TypeToken<UserGroupProvisionInfoList>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Search Provision (asynchronously)
     * Search provisioning information.
     * @param id &lt;p&gt;User or group ID wildcard pattern. if specified, returns users and groups matching the pattern, if not specified, returns all the users and groups having some role. Users or groups having no role are not returned.&lt;/p&gt; (optional, default to *)
     * @param role &lt;p&gt;Input may include &lt;code&gt;all&lt;/code&gt;, &lt;code&gt;none&lt;/code&gt;, or a comma-separated list of roles (for example, &lt;code&gt;service_administrator&lt;/code&gt;, &lt;code&gt;power_user&lt;/code&gt;, or &lt;code&gt;user&lt;/code&gt;). Default value is &lt;code&gt;all&lt;/code&gt;, so if this query parameter is not specified, all users and groups having some role are returned. If &lt;code&gt;none&lt;/code&gt; is specified, only users and groups having no role will be returned. If named roles are specified, then only users and groups having any of the named roles are returned.&lt;/p&gt; (optional, default to all)
     * @param filter all/group/user. Default value is all so if this query parameter is not specified then all users/groups will be returned. (optional, default to all)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> provisioning information for users/groups matching with search parameters </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> &lt;p&gt;&lt;strong&gt;Bad Request&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;The logged in user may not have the required service administrator role.&lt;/p&gt; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> &lt;p&gt;Internal Server Error.&lt;/p&gt; </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call serviceRoleProvisioningSearchProvisionAsync(String id, String role, String filter, final ApiCallback<UserGroupProvisionInfoList> _callback) throws ApiException {

        okhttp3.Call localVarCall = serviceRoleProvisioningSearchProvisionValidateBeforeCall(id, role, filter, _callback);
        Type localVarReturnType = new TypeToken<UserGroupProvisionInfoList>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
