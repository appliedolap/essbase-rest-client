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
 * GeneralStatistics
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class GeneralStatistics {
  public static final String SERIALIZED_NAME_DATABASE_START_TIME = "databaseStartTime";
  @SerializedName(SERIALIZED_NAME_DATABASE_START_TIME)
  private Long databaseStartTime;

  public static final String SERIALIZED_NAME_DATABASE_ELAPSED_TIME = "databaseElapsedTime";
  @SerializedName(SERIALIZED_NAME_DATABASE_ELAPSED_TIME)
  private String databaseElapsedTime;

  public static final String SERIALIZED_NAME_NUMBER_OF_CONNECTIONS = "numberOfConnections";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_CONNECTIONS)
  private Integer numberOfConnections;

  public static final String SERIALIZED_NAME_NUMBER_OF_DIMENSIONS = "numberOfDimensions";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_DIMENSIONS)
  private Integer numberOfDimensions;

  public static final String SERIALIZED_NAME_LOAD_STATUS = "loadStatus";
  @SerializedName(SERIALIZED_NAME_LOAD_STATUS)
  private String loadStatus;


  public GeneralStatistics databaseStartTime(Long databaseStartTime) {
    
    this.databaseStartTime = databaseStartTime;
    return this;
  }

   /**
   * Get databaseStartTime
   * @return databaseStartTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDatabaseStartTime() {
    return databaseStartTime;
  }


  public void setDatabaseStartTime(Long databaseStartTime) {
    this.databaseStartTime = databaseStartTime;
  }


  public GeneralStatistics databaseElapsedTime(String databaseElapsedTime) {
    
    this.databaseElapsedTime = databaseElapsedTime;
    return this;
  }

   /**
   * Get databaseElapsedTime
   * @return databaseElapsedTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabaseElapsedTime() {
    return databaseElapsedTime;
  }


  public void setDatabaseElapsedTime(String databaseElapsedTime) {
    this.databaseElapsedTime = databaseElapsedTime;
  }


  public GeneralStatistics numberOfConnections(Integer numberOfConnections) {
    
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


  public GeneralStatistics numberOfDimensions(Integer numberOfDimensions) {
    
    this.numberOfDimensions = numberOfDimensions;
    return this;
  }

   /**
   * Get numberOfDimensions
   * @return numberOfDimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfDimensions() {
    return numberOfDimensions;
  }


  public void setNumberOfDimensions(Integer numberOfDimensions) {
    this.numberOfDimensions = numberOfDimensions;
  }


  public GeneralStatistics loadStatus(String loadStatus) {
    
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
    GeneralStatistics generalStatistics = (GeneralStatistics) o;
    return Objects.equals(this.databaseStartTime, generalStatistics.databaseStartTime) &&
        Objects.equals(this.databaseElapsedTime, generalStatistics.databaseElapsedTime) &&
        Objects.equals(this.numberOfConnections, generalStatistics.numberOfConnections) &&
        Objects.equals(this.numberOfDimensions, generalStatistics.numberOfDimensions) &&
        Objects.equals(this.loadStatus, generalStatistics.loadStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(databaseStartTime, databaseElapsedTime, numberOfConnections, numberOfDimensions, loadStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GeneralStatistics {\n");
    sb.append("    databaseStartTime: ").append(toIndentedString(databaseStartTime)).append("\n");
    sb.append("    databaseElapsedTime: ").append(toIndentedString(databaseElapsedTime)).append("\n");
    sb.append("    numberOfConnections: ").append(toIndentedString(numberOfConnections)).append("\n");
    sb.append("    numberOfDimensions: ").append(toIndentedString(numberOfDimensions)).append("\n");
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
