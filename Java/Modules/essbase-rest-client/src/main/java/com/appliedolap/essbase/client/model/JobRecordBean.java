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
import com.appliedolap.essbase.client.model.Link;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JobRecordBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class JobRecordBean {
  public static final String SERIALIZED_NAME_JOB_I_D = "job_ID";
  @SerializedName(SERIALIZED_NAME_JOB_I_D)
  private Long jobID;

  public static final String SERIALIZED_NAME_APP_NAME = "appName";
  @SerializedName(SERIALIZED_NAME_APP_NAME)
  private String appName;

  public static final String SERIALIZED_NAME_DB_NAME = "dbName";
  @SerializedName(SERIALIZED_NAME_DB_NAME)
  private String dbName;

  public static final String SERIALIZED_NAME_JOB_TYPE = "jobType";
  @SerializedName(SERIALIZED_NAME_JOB_TYPE)
  private String jobType;

  public static final String SERIALIZED_NAME_JOBFILE_NAME = "jobfileName";
  @SerializedName(SERIALIZED_NAME_JOBFILE_NAME)
  private String jobfileName;

  public static final String SERIALIZED_NAME_USER_NAME = "userName";
  @SerializedName(SERIALIZED_NAME_USER_NAME)
  private String userName;

  public static final String SERIALIZED_NAME_START_TIME = "startTime";
  @SerializedName(SERIALIZED_NAME_START_TIME)
  private Long startTime;

  public static final String SERIALIZED_NAME_END_TIME = "endTime";
  @SerializedName(SERIALIZED_NAME_END_TIME)
  private Long endTime;

  public static final String SERIALIZED_NAME_STATUS_CODE = "statusCode";
  @SerializedName(SERIALIZED_NAME_STATUS_CODE)
  private Integer statusCode;

  public static final String SERIALIZED_NAME_STATUS_MESSAGE = "statusMessage";
  @SerializedName(SERIALIZED_NAME_STATUS_MESSAGE)
  private String statusMessage;

  public static final String SERIALIZED_NAME_JOB_INPUT_INFO = "jobInputInfo";
  @SerializedName(SERIALIZED_NAME_JOB_INPUT_INFO)
  private Map<String, Object> jobInputInfo = null;

  public static final String SERIALIZED_NAME_JOB_OUTPUT_INFO = "jobOutputInfo";
  @SerializedName(SERIALIZED_NAME_JOB_OUTPUT_INFO)
  private Map<String, Object> jobOutputInfo = null;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public JobRecordBean jobID(Long jobID) {
    
    this.jobID = jobID;
    return this;
  }

   /**
   * Get jobID
   * @return jobID
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getJobID() {
    return jobID;
  }


  public void setJobID(Long jobID) {
    this.jobID = jobID;
  }


  public JobRecordBean appName(String appName) {
    
    this.appName = appName;
    return this;
  }

   /**
   * Get appName
   * @return appName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAppName() {
    return appName;
  }


  public void setAppName(String appName) {
    this.appName = appName;
  }


  public JobRecordBean dbName(String dbName) {
    
    this.dbName = dbName;
    return this;
  }

   /**
   * Get dbName
   * @return dbName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDbName() {
    return dbName;
  }


  public void setDbName(String dbName) {
    this.dbName = dbName;
  }


  public JobRecordBean jobType(String jobType) {
    
    this.jobType = jobType;
    return this;
  }

   /**
   * Get jobType
   * @return jobType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getJobType() {
    return jobType;
  }


  public void setJobType(String jobType) {
    this.jobType = jobType;
  }


  public JobRecordBean jobfileName(String jobfileName) {
    
    this.jobfileName = jobfileName;
    return this;
  }

   /**
   * Get jobfileName
   * @return jobfileName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getJobfileName() {
    return jobfileName;
  }


  public void setJobfileName(String jobfileName) {
    this.jobfileName = jobfileName;
  }


  public JobRecordBean userName(String userName) {
    
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public JobRecordBean startTime(Long startTime) {
    
    this.startTime = startTime;
    return this;
  }

   /**
   * Get startTime
   * @return startTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getStartTime() {
    return startTime;
  }


  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }


  public JobRecordBean endTime(Long endTime) {
    
    this.endTime = endTime;
    return this;
  }

   /**
   * Get endTime
   * @return endTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getEndTime() {
    return endTime;
  }


  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }


  public JobRecordBean statusCode(Integer statusCode) {
    
    this.statusCode = statusCode;
    return this;
  }

   /**
   * Get statusCode
   * @return statusCode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getStatusCode() {
    return statusCode;
  }


  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }


  public JobRecordBean statusMessage(String statusMessage) {
    
    this.statusMessage = statusMessage;
    return this;
  }

   /**
   * Get statusMessage
   * @return statusMessage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getStatusMessage() {
    return statusMessage;
  }


  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }


  public JobRecordBean jobInputInfo(Map<String, Object> jobInputInfo) {
    
    this.jobInputInfo = jobInputInfo;
    return this;
  }

  public JobRecordBean putJobInputInfoItem(String key, Object jobInputInfoItem) {
    if (this.jobInputInfo == null) {
      this.jobInputInfo = new HashMap<String, Object>();
    }
    this.jobInputInfo.put(key, jobInputInfoItem);
    return this;
  }

   /**
   * Get jobInputInfo
   * @return jobInputInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Map<String, Object> getJobInputInfo() {
    return jobInputInfo;
  }


  public void setJobInputInfo(Map<String, Object> jobInputInfo) {
    this.jobInputInfo = jobInputInfo;
  }


  public JobRecordBean jobOutputInfo(Map<String, Object> jobOutputInfo) {
    
    this.jobOutputInfo = jobOutputInfo;
    return this;
  }

  public JobRecordBean putJobOutputInfoItem(String key, Object jobOutputInfoItem) {
    if (this.jobOutputInfo == null) {
      this.jobOutputInfo = new HashMap<String, Object>();
    }
    this.jobOutputInfo.put(key, jobOutputInfoItem);
    return this;
  }

   /**
   * Get jobOutputInfo
   * @return jobOutputInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Map<String, Object> getJobOutputInfo() {
    return jobOutputInfo;
  }


  public void setJobOutputInfo(Map<String, Object> jobOutputInfo) {
    this.jobOutputInfo = jobOutputInfo;
  }


  public JobRecordBean links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public JobRecordBean addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Link> getLinks() {
    return links;
  }


  public void setLinks(List<Link> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobRecordBean jobRecordBean = (JobRecordBean) o;
    return Objects.equals(this.jobID, jobRecordBean.jobID) &&
        Objects.equals(this.appName, jobRecordBean.appName) &&
        Objects.equals(this.dbName, jobRecordBean.dbName) &&
        Objects.equals(this.jobType, jobRecordBean.jobType) &&
        Objects.equals(this.jobfileName, jobRecordBean.jobfileName) &&
        Objects.equals(this.userName, jobRecordBean.userName) &&
        Objects.equals(this.startTime, jobRecordBean.startTime) &&
        Objects.equals(this.endTime, jobRecordBean.endTime) &&
        Objects.equals(this.statusCode, jobRecordBean.statusCode) &&
        Objects.equals(this.statusMessage, jobRecordBean.statusMessage) &&
        Objects.equals(this.jobInputInfo, jobRecordBean.jobInputInfo) &&
        Objects.equals(this.jobOutputInfo, jobRecordBean.jobOutputInfo) &&
        Objects.equals(this.links, jobRecordBean.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jobID, appName, dbName, jobType, jobfileName, userName, startTime, endTime, statusCode, statusMessage, jobInputInfo, jobOutputInfo, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JobRecordBean {\n");
    sb.append("    jobID: ").append(toIndentedString(jobID)).append("\n");
    sb.append("    appName: ").append(toIndentedString(appName)).append("\n");
    sb.append("    dbName: ").append(toIndentedString(dbName)).append("\n");
    sb.append("    jobType: ").append(toIndentedString(jobType)).append("\n");
    sb.append("    jobfileName: ").append(toIndentedString(jobfileName)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    jobInputInfo: ").append(toIndentedString(jobInputInfo)).append("\n");
    sb.append("    jobOutputInfo: ").append(toIndentedString(jobOutputInfo)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

