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
 * FileProperties
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class FileProperties {
  public static final String SERIALIZED_NAME_DELIMITER = "delimiter";
  @SerializedName(SERIALIZED_NAME_DELIMITER)
  private String delimiter;

  public static final String SERIALIZED_NAME_WIDTH = "width";
  @SerializedName(SERIALIZED_NAME_WIDTH)
  private Integer width;

  public static final String SERIALIZED_NAME_DATALOAD_RECORD_NUMBER = "dataloadRecordNumber";
  @SerializedName(SERIALIZED_NAME_DATALOAD_RECORD_NUMBER)
  private Integer dataloadRecordNumber;

  public static final String SERIALIZED_NAME_DIMENSION_BUILD_RECORD_NUMBER = "dimensionBuildRecordNumber";
  @SerializedName(SERIALIZED_NAME_DIMENSION_BUILD_RECORD_NUMBER)
  private Integer dimensionBuildRecordNumber;

  public static final String SERIALIZED_NAME_HEADER_RECORD_NUMBER = "headerRecordNumber";
  @SerializedName(SERIALIZED_NAME_HEADER_RECORD_NUMBER)
  private Integer headerRecordNumber;

  public static final String SERIALIZED_NAME_LINE_SKIP_COUNT = "lineSkipCount";
  @SerializedName(SERIALIZED_NAME_LINE_SKIP_COUNT)
  private Integer lineSkipCount;


  public FileProperties delimiter(String delimiter) {
    
    this.delimiter = delimiter;
    return this;
  }

   /**
   * Get delimiter
   * @return delimiter
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDelimiter() {
    return delimiter;
  }


  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }


  public FileProperties width(Integer width) {
    
    this.width = width;
    return this;
  }

   /**
   * Get width
   * @return width
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getWidth() {
    return width;
  }


  public void setWidth(Integer width) {
    this.width = width;
  }


  public FileProperties dataloadRecordNumber(Integer dataloadRecordNumber) {
    
    this.dataloadRecordNumber = dataloadRecordNumber;
    return this;
  }

   /**
   * Get dataloadRecordNumber
   * @return dataloadRecordNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getDataloadRecordNumber() {
    return dataloadRecordNumber;
  }


  public void setDataloadRecordNumber(Integer dataloadRecordNumber) {
    this.dataloadRecordNumber = dataloadRecordNumber;
  }


  public FileProperties dimensionBuildRecordNumber(Integer dimensionBuildRecordNumber) {
    
    this.dimensionBuildRecordNumber = dimensionBuildRecordNumber;
    return this;
  }

   /**
   * Get dimensionBuildRecordNumber
   * @return dimensionBuildRecordNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getDimensionBuildRecordNumber() {
    return dimensionBuildRecordNumber;
  }


  public void setDimensionBuildRecordNumber(Integer dimensionBuildRecordNumber) {
    this.dimensionBuildRecordNumber = dimensionBuildRecordNumber;
  }


  public FileProperties headerRecordNumber(Integer headerRecordNumber) {
    
    this.headerRecordNumber = headerRecordNumber;
    return this;
  }

   /**
   * Get headerRecordNumber
   * @return headerRecordNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getHeaderRecordNumber() {
    return headerRecordNumber;
  }


  public void setHeaderRecordNumber(Integer headerRecordNumber) {
    this.headerRecordNumber = headerRecordNumber;
  }


  public FileProperties lineSkipCount(Integer lineSkipCount) {
    
    this.lineSkipCount = lineSkipCount;
    return this;
  }

   /**
   * Get lineSkipCount
   * @return lineSkipCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getLineSkipCount() {
    return lineSkipCount;
  }


  public void setLineSkipCount(Integer lineSkipCount) {
    this.lineSkipCount = lineSkipCount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileProperties fileProperties = (FileProperties) o;
    return Objects.equals(this.delimiter, fileProperties.delimiter) &&
        Objects.equals(this.width, fileProperties.width) &&
        Objects.equals(this.dataloadRecordNumber, fileProperties.dataloadRecordNumber) &&
        Objects.equals(this.dimensionBuildRecordNumber, fileProperties.dimensionBuildRecordNumber) &&
        Objects.equals(this.headerRecordNumber, fileProperties.headerRecordNumber) &&
        Objects.equals(this.lineSkipCount, fileProperties.lineSkipCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delimiter, width, dataloadRecordNumber, dimensionBuildRecordNumber, headerRecordNumber, lineSkipCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileProperties {\n");
    sb.append("    delimiter: ").append(toIndentedString(delimiter)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
    sb.append("    dataloadRecordNumber: ").append(toIndentedString(dataloadRecordNumber)).append("\n");
    sb.append("    dimensionBuildRecordNumber: ").append(toIndentedString(dimensionBuildRecordNumber)).append("\n");
    sb.append("    headerRecordNumber: ").append(toIndentedString(headerRecordNumber)).append("\n");
    sb.append("    lineSkipCount: ").append(toIndentedString(lineSkipCount)).append("\n");
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

