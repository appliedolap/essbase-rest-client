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


package com.appliedolap.essbase.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * SessionAttributes
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class SessionAttributes {
  public static final String SERIALIZED_NAME_USER_ID = "userId";
  @SerializedName(SERIALIZED_NAME_USER_ID)
  private String userId;

  public static final String SERIALIZED_NAME_SESSION_ID = "sessionId";
  @SerializedName(SERIALIZED_NAME_SESSION_ID)
  private String sessionId;

  public static final String SERIALIZED_NAME_LOGIN_TIME_IN_SECONDS = "loginTimeInSeconds";
  @SerializedName(SERIALIZED_NAME_LOGIN_TIME_IN_SECONDS)
  private String loginTimeInSeconds;

  public static final String SERIALIZED_NAME_APPLICATION = "application";
  @SerializedName(SERIALIZED_NAME_APPLICATION)
  private String application;

  public static final String SERIALIZED_NAME_DATABASE = "database";
  @SerializedName(SERIALIZED_NAME_DATABASE)
  private String database;

  public static final String SERIALIZED_NAME_DB_CONNECT_TIME_IN_SECONDS = "dbConnectTimeInSeconds";
  @SerializedName(SERIALIZED_NAME_DB_CONNECT_TIME_IN_SECONDS)
  private String dbConnectTimeInSeconds;

  public static final String SERIALIZED_NAME_REQUEST = "request";
  @SerializedName(SERIALIZED_NAME_REQUEST)
  private String request;

  public static final String SERIALIZED_NAME_REQUEST_TIME_IN_SECONDS = "requestTimeInSeconds";
  @SerializedName(SERIALIZED_NAME_REQUEST_TIME_IN_SECONDS)
  private String requestTimeInSeconds;

  public static final String SERIALIZED_NAME_CONNECTION_SOURCE = "connectionSource";
  @SerializedName(SERIALIZED_NAME_CONNECTION_SOURCE)
  private String connectionSource;

  public static final String SERIALIZED_NAME_REQUEST_STATE = "requestState";
  @SerializedName(SERIALIZED_NAME_REQUEST_STATE)
  private String requestState;


  public SessionAttributes userId(String userId) {
    
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUserId() {
    return userId;
  }


  public void setUserId(String userId) {
    this.userId = userId;
  }


  public SessionAttributes sessionId(String sessionId) {
    
    this.sessionId = sessionId;
    return this;
  }

   /**
   * Get sessionId
   * @return sessionId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSessionId() {
    return sessionId;
  }


  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  public SessionAttributes loginTimeInSeconds(String loginTimeInSeconds) {
    
    this.loginTimeInSeconds = loginTimeInSeconds;
    return this;
  }

   /**
   * Get loginTimeInSeconds
   * @return loginTimeInSeconds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLoginTimeInSeconds() {
    return loginTimeInSeconds;
  }


  public void setLoginTimeInSeconds(String loginTimeInSeconds) {
    this.loginTimeInSeconds = loginTimeInSeconds;
  }


  public SessionAttributes application(String application) {
    
    this.application = application;
    return this;
  }

   /**
   * Get application
   * @return application
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplication() {
    return application;
  }


  public void setApplication(String application) {
    this.application = application;
  }


  public SessionAttributes database(String database) {
    
    this.database = database;
    return this;
  }

   /**
   * Get database
   * @return database
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabase() {
    return database;
  }


  public void setDatabase(String database) {
    this.database = database;
  }


  public SessionAttributes dbConnectTimeInSeconds(String dbConnectTimeInSeconds) {
    
    this.dbConnectTimeInSeconds = dbConnectTimeInSeconds;
    return this;
  }

   /**
   * Get dbConnectTimeInSeconds
   * @return dbConnectTimeInSeconds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDbConnectTimeInSeconds() {
    return dbConnectTimeInSeconds;
  }


  public void setDbConnectTimeInSeconds(String dbConnectTimeInSeconds) {
    this.dbConnectTimeInSeconds = dbConnectTimeInSeconds;
  }


  public SessionAttributes request(String request) {
    
    this.request = request;
    return this;
  }

   /**
   * Get request
   * @return request
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getRequest() {
    return request;
  }


  public void setRequest(String request) {
    this.request = request;
  }


  public SessionAttributes requestTimeInSeconds(String requestTimeInSeconds) {
    
    this.requestTimeInSeconds = requestTimeInSeconds;
    return this;
  }

   /**
   * Get requestTimeInSeconds
   * @return requestTimeInSeconds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getRequestTimeInSeconds() {
    return requestTimeInSeconds;
  }


  public void setRequestTimeInSeconds(String requestTimeInSeconds) {
    this.requestTimeInSeconds = requestTimeInSeconds;
  }


  public SessionAttributes connectionSource(String connectionSource) {
    
    this.connectionSource = connectionSource;
    return this;
  }

   /**
   * Get connectionSource
   * @return connectionSource
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getConnectionSource() {
    return connectionSource;
  }


  public void setConnectionSource(String connectionSource) {
    this.connectionSource = connectionSource;
  }


  public SessionAttributes requestState(String requestState) {
    
    this.requestState = requestState;
    return this;
  }

   /**
   * Get requestState
   * @return requestState
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getRequestState() {
    return requestState;
  }


  public void setRequestState(String requestState) {
    this.requestState = requestState;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SessionAttributes sessionAttributes = (SessionAttributes) o;
    return Objects.equals(this.userId, sessionAttributes.userId) &&
        Objects.equals(this.sessionId, sessionAttributes.sessionId) &&
        Objects.equals(this.loginTimeInSeconds, sessionAttributes.loginTimeInSeconds) &&
        Objects.equals(this.application, sessionAttributes.application) &&
        Objects.equals(this.database, sessionAttributes.database) &&
        Objects.equals(this.dbConnectTimeInSeconds, sessionAttributes.dbConnectTimeInSeconds) &&
        Objects.equals(this.request, sessionAttributes.request) &&
        Objects.equals(this.requestTimeInSeconds, sessionAttributes.requestTimeInSeconds) &&
        Objects.equals(this.connectionSource, sessionAttributes.connectionSource) &&
        Objects.equals(this.requestState, sessionAttributes.requestState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, sessionId, loginTimeInSeconds, application, database, dbConnectTimeInSeconds, request, requestTimeInSeconds, connectionSource, requestState);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SessionAttributes {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    loginTimeInSeconds: ").append(toIndentedString(loginTimeInSeconds)).append("\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
    sb.append("    dbConnectTimeInSeconds: ").append(toIndentedString(dbConnectTimeInSeconds)).append("\n");
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    requestTimeInSeconds: ").append(toIndentedString(requestTimeInSeconds)).append("\n");
    sb.append("    connectionSource: ").append(toIndentedString(connectionSource)).append("\n");
    sb.append("    requestState: ").append(toIndentedString(requestState)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

