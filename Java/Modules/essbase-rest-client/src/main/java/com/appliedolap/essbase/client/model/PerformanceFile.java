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
 * PerformanceFile
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class PerformanceFile {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_LASTMODIFIED_MILLIS = "lastmodifiedMillis";
  @SerializedName(SERIALIZED_NAME_LASTMODIFIED_MILLIS)
  private Long lastmodifiedMillis;

  public static final String SERIALIZED_NAME_SIZE = "size";
  @SerializedName(SERIALIZED_NAME_SIZE)
  private Long size;


  public PerformanceFile name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public PerformanceFile lastmodifiedMillis(Long lastmodifiedMillis) {
    
    this.lastmodifiedMillis = lastmodifiedMillis;
    return this;
  }

   /**
   * Get lastmodifiedMillis
   * @return lastmodifiedMillis
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getLastmodifiedMillis() {
    return lastmodifiedMillis;
  }


  public void setLastmodifiedMillis(Long lastmodifiedMillis) {
    this.lastmodifiedMillis = lastmodifiedMillis;
  }


  public PerformanceFile size(Long size) {
    
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getSize() {
    return size;
  }


  public void setSize(Long size) {
    this.size = size;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PerformanceFile performanceFile = (PerformanceFile) o;
    return Objects.equals(this.name, performanceFile.name) &&
        Objects.equals(this.lastmodifiedMillis, performanceFile.lastmodifiedMillis) &&
        Objects.equals(this.size, performanceFile.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, lastmodifiedMillis, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PerformanceFile {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    lastmodifiedMillis: ").append(toIndentedString(lastmodifiedMillis)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

