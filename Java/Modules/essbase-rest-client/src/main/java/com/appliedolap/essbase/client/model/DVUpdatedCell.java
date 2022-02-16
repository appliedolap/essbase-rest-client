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
import java.util.ArrayList;
import java.util.List;

/**
 * DVUpdatedCell
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class DVUpdatedCell {
  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private String value;

  public static final String SERIALIZED_NAME_ROW_HEADERS = "rowHeaders";
  @SerializedName(SERIALIZED_NAME_ROW_HEADERS)
  private List<String> rowHeaders = null;

  public static final String SERIALIZED_NAME_COLUMN_HEADERS = "columnHeaders";
  @SerializedName(SERIALIZED_NAME_COLUMN_HEADERS)
  private List<String> columnHeaders = null;


  public DVUpdatedCell value(String value) {
    
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getValue() {
    return value;
  }


  public void setValue(String value) {
    this.value = value;
  }


  public DVUpdatedCell rowHeaders(List<String> rowHeaders) {
    
    this.rowHeaders = rowHeaders;
    return this;
  }

  public DVUpdatedCell addRowHeadersItem(String rowHeadersItem) {
    if (this.rowHeaders == null) {
      this.rowHeaders = new ArrayList<String>();
    }
    this.rowHeaders.add(rowHeadersItem);
    return this;
  }

   /**
   * Get rowHeaders
   * @return rowHeaders
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getRowHeaders() {
    return rowHeaders;
  }


  public void setRowHeaders(List<String> rowHeaders) {
    this.rowHeaders = rowHeaders;
  }


  public DVUpdatedCell columnHeaders(List<String> columnHeaders) {
    
    this.columnHeaders = columnHeaders;
    return this;
  }

  public DVUpdatedCell addColumnHeadersItem(String columnHeadersItem) {
    if (this.columnHeaders == null) {
      this.columnHeaders = new ArrayList<String>();
    }
    this.columnHeaders.add(columnHeadersItem);
    return this;
  }

   /**
   * Get columnHeaders
   * @return columnHeaders
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getColumnHeaders() {
    return columnHeaders;
  }


  public void setColumnHeaders(List<String> columnHeaders) {
    this.columnHeaders = columnHeaders;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DVUpdatedCell dvUpdatedCell = (DVUpdatedCell) o;
    return Objects.equals(this.value, dvUpdatedCell.value) &&
        Objects.equals(this.rowHeaders, dvUpdatedCell.rowHeaders) &&
        Objects.equals(this.columnHeaders, dvUpdatedCell.columnHeaders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, rowHeaders, columnHeaders);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DVUpdatedCell {\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    rowHeaders: ").append(toIndentedString(rowHeaders)).append("\n");
    sb.append("    columnHeaders: ").append(toIndentedString(columnHeaders)).append("\n");
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

