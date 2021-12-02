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
 * BufferSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class BufferSettings {
  public static final String SERIALIZED_NAME_DATA_RETRIEVAL_BUFFER_SIZE = "dataRetrievalBufferSize";
  @SerializedName(SERIALIZED_NAME_DATA_RETRIEVAL_BUFFER_SIZE)
  private Long dataRetrievalBufferSize;

  public static final String SERIALIZED_NAME_DATA_RETRIEVAL_SORT_BUFFER_SIZE = "dataRetrievalSortBufferSize";
  @SerializedName(SERIALIZED_NAME_DATA_RETRIEVAL_SORT_BUFFER_SIZE)
  private Long dataRetrievalSortBufferSize;


  public BufferSettings dataRetrievalBufferSize(Long dataRetrievalBufferSize) {
    
    this.dataRetrievalBufferSize = dataRetrievalBufferSize;
    return this;
  }

   /**
   * Get dataRetrievalBufferSize
   * @return dataRetrievalBufferSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDataRetrievalBufferSize() {
    return dataRetrievalBufferSize;
  }


  public void setDataRetrievalBufferSize(Long dataRetrievalBufferSize) {
    this.dataRetrievalBufferSize = dataRetrievalBufferSize;
  }


  public BufferSettings dataRetrievalSortBufferSize(Long dataRetrievalSortBufferSize) {
    
    this.dataRetrievalSortBufferSize = dataRetrievalSortBufferSize;
    return this;
  }

   /**
   * Get dataRetrievalSortBufferSize
   * @return dataRetrievalSortBufferSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDataRetrievalSortBufferSize() {
    return dataRetrievalSortBufferSize;
  }


  public void setDataRetrievalSortBufferSize(Long dataRetrievalSortBufferSize) {
    this.dataRetrievalSortBufferSize = dataRetrievalSortBufferSize;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BufferSettings bufferSettings = (BufferSettings) o;
    return Objects.equals(this.dataRetrievalBufferSize, bufferSettings.dataRetrievalBufferSize) &&
        Objects.equals(this.dataRetrievalSortBufferSize, bufferSettings.dataRetrievalSortBufferSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataRetrievalBufferSize, dataRetrievalSortBufferSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BufferSettings {\n");
    sb.append("    dataRetrievalBufferSize: ").append(toIndentedString(dataRetrievalBufferSize)).append("\n");
    sb.append("    dataRetrievalSortBufferSize: ").append(toIndentedString(dataRetrievalSortBufferSize)).append("\n");
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

