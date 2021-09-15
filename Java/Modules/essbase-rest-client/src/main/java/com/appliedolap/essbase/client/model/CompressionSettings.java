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
 * CompressionSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class CompressionSettings {
  public static final String SERIALIZED_NAME_DIMENSION_NAME = "dimensionName";
  @SerializedName(SERIALIZED_NAME_DIMENSION_NAME)
  private String dimensionName;

  public static final String SERIALIZED_NAME_AVERAGE_BUNDLE_FILL = "averageBundleFill";
  @SerializedName(SERIALIZED_NAME_AVERAGE_BUNDLE_FILL)
  private Double averageBundleFill;

  public static final String SERIALIZED_NAME_AVERAGE_VALUE_LENGTH = "averageValueLength";
  @SerializedName(SERIALIZED_NAME_AVERAGE_VALUE_LENGTH)
  private Double averageValueLength;

  public static final String SERIALIZED_NAME_IS_COMPRESSION = "isCompression";
  @SerializedName(SERIALIZED_NAME_IS_COMPRESSION)
  private Boolean isCompression;

  public static final String SERIALIZED_NAME_LEVEL0_M_B = "level0MB";
  @SerializedName(SERIALIZED_NAME_LEVEL0_M_B)
  private Double level0MB;

  public static final String SERIALIZED_NAME_STORED_LEVEL0_MEMBERS = "storedLevel0Members";
  @SerializedName(SERIALIZED_NAME_STORED_LEVEL0_MEMBERS)
  private Double storedLevel0Members;


  public CompressionSettings dimensionName(String dimensionName) {
    
    this.dimensionName = dimensionName;
    return this;
  }

   /**
   * Get dimensionName
   * @return dimensionName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimensionName() {
    return dimensionName;
  }


  public void setDimensionName(String dimensionName) {
    this.dimensionName = dimensionName;
  }


  public CompressionSettings averageBundleFill(Double averageBundleFill) {
    
    this.averageBundleFill = averageBundleFill;
    return this;
  }

   /**
   * Get averageBundleFill
   * @return averageBundleFill
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getAverageBundleFill() {
    return averageBundleFill;
  }


  public void setAverageBundleFill(Double averageBundleFill) {
    this.averageBundleFill = averageBundleFill;
  }


  public CompressionSettings averageValueLength(Double averageValueLength) {
    
    this.averageValueLength = averageValueLength;
    return this;
  }

   /**
   * Get averageValueLength
   * @return averageValueLength
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getAverageValueLength() {
    return averageValueLength;
  }


  public void setAverageValueLength(Double averageValueLength) {
    this.averageValueLength = averageValueLength;
  }


  public CompressionSettings isCompression(Boolean isCompression) {
    
    this.isCompression = isCompression;
    return this;
  }

   /**
   * Get isCompression
   * @return isCompression
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsCompression() {
    return isCompression;
  }


  public void setIsCompression(Boolean isCompression) {
    this.isCompression = isCompression;
  }


  public CompressionSettings level0MB(Double level0MB) {
    
    this.level0MB = level0MB;
    return this;
  }

   /**
   * Get level0MB
   * @return level0MB
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getLevel0MB() {
    return level0MB;
  }


  public void setLevel0MB(Double level0MB) {
    this.level0MB = level0MB;
  }


  public CompressionSettings storedLevel0Members(Double storedLevel0Members) {
    
    this.storedLevel0Members = storedLevel0Members;
    return this;
  }

   /**
   * Get storedLevel0Members
   * @return storedLevel0Members
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getStoredLevel0Members() {
    return storedLevel0Members;
  }


  public void setStoredLevel0Members(Double storedLevel0Members) {
    this.storedLevel0Members = storedLevel0Members;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompressionSettings compressionSettings = (CompressionSettings) o;
    return Objects.equals(this.dimensionName, compressionSettings.dimensionName) &&
        Objects.equals(this.averageBundleFill, compressionSettings.averageBundleFill) &&
        Objects.equals(this.averageValueLength, compressionSettings.averageValueLength) &&
        Objects.equals(this.isCompression, compressionSettings.isCompression) &&
        Objects.equals(this.level0MB, compressionSettings.level0MB) &&
        Objects.equals(this.storedLevel0Members, compressionSettings.storedLevel0Members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimensionName, averageBundleFill, averageValueLength, isCompression, level0MB, storedLevel0Members);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompressionSettings {\n");
    sb.append("    dimensionName: ").append(toIndentedString(dimensionName)).append("\n");
    sb.append("    averageBundleFill: ").append(toIndentedString(averageBundleFill)).append("\n");
    sb.append("    averageValueLength: ").append(toIndentedString(averageValueLength)).append("\n");
    sb.append("    isCompression: ").append(toIndentedString(isCompression)).append("\n");
    sb.append("    level0MB: ").append(toIndentedString(level0MB)).append("\n");
    sb.append("    storedLevel0Members: ").append(toIndentedString(storedLevel0Members)).append("\n");
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

