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
 * ApplicationStatistics
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class ApplicationStatistics {
  public static final String SERIALIZED_NAME_APPLICATION_START_TIME = "applicationStartTime";
  @SerializedName(SERIALIZED_NAME_APPLICATION_START_TIME)
  private Long applicationStartTime;

  public static final String SERIALIZED_NAME_APPLICATION_ELAPSED_TIME = "applicationElapsedTime";
  @SerializedName(SERIALIZED_NAME_APPLICATION_ELAPSED_TIME)
  private String applicationElapsedTime;

  public static final String SERIALIZED_NAME_NUMBER_OF_CONNECTIONS = "numberOfConnections";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_CONNECTIONS)
  private Integer numberOfConnections;

  public static final String SERIALIZED_NAME_LOAD_STATUS = "loadStatus";
  @SerializedName(SERIALIZED_NAME_LOAD_STATUS)
  private String loadStatus;


  public ApplicationStatistics applicationStartTime(Long applicationStartTime) {
    
    this.applicationStartTime = applicationStartTime;
    return this;
  }

   /**
   * Get applicationStartTime
   * @return applicationStartTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getApplicationStartTime() {
    return applicationStartTime;
  }


  public void setApplicationStartTime(Long applicationStartTime) {
    this.applicationStartTime = applicationStartTime;
  }


  public ApplicationStatistics applicationElapsedTime(String applicationElapsedTime) {
    
    this.applicationElapsedTime = applicationElapsedTime;
    return this;
  }

   /**
   * Get applicationElapsedTime
   * @return applicationElapsedTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplicationElapsedTime() {
    return applicationElapsedTime;
  }


  public void setApplicationElapsedTime(String applicationElapsedTime) {
    this.applicationElapsedTime = applicationElapsedTime;
  }


  public ApplicationStatistics numberOfConnections(Integer numberOfConnections) {
    
    this.numberOfConnections = numberOfConnections;
    return this;
  }

   /**
   * Get numberOfConnections
   * @return numberOfConnections
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfConnections() {
    return numberOfConnections;
  }


  public void setNumberOfConnections(Integer numberOfConnections) {
    this.numberOfConnections = numberOfConnections;
  }


  public ApplicationStatistics loadStatus(String loadStatus) {
    
    this.loadStatus = loadStatus;
    return this;
  }

   /**
   * Get loadStatus
   * @return loadStatus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLoadStatus() {
    return loadStatus;
  }


  public void setLoadStatus(String loadStatus) {
    this.loadStatus = loadStatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationStatistics applicationStatistics = (ApplicationStatistics) o;
    return Objects.equals(this.applicationStartTime, applicationStatistics.applicationStartTime) &&
        Objects.equals(this.applicationElapsedTime, applicationStatistics.applicationElapsedTime) &&
        Objects.equals(this.numberOfConnections, applicationStatistics.numberOfConnections) &&
        Objects.equals(this.loadStatus, applicationStatistics.loadStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationStartTime, applicationElapsedTime, numberOfConnections, loadStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationStatistics {\n");
    sb.append("    applicationStartTime: ").append(toIndentedString(applicationStartTime)).append("\n");
    sb.append("    applicationElapsedTime: ").append(toIndentedString(applicationElapsedTime)).append("\n");
    sb.append("    numberOfConnections: ").append(toIndentedString(numberOfConnections)).append("\n");
    sb.append("    loadStatus: ").append(toIndentedString(loadStatus)).append("\n");
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

