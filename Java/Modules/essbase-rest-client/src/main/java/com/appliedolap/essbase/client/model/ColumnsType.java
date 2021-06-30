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
import com.appliedolap.essbase.client.model.ColumnType2;
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
 * ColumnsType
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class ColumnsType {
  public static final String SERIALIZED_NAME_COLUMN = "column";
  @SerializedName(SERIALIZED_NAME_COLUMN)
  private List<ColumnType2> column = new ArrayList<ColumnType2>();


  public ColumnsType column(List<ColumnType2> column) {
    
    this.column = column;
    return this;
  }

  public ColumnsType addColumnItem(ColumnType2 columnItem) {
    this.column.add(columnItem);
    return this;
  }

   /**
   * Get column
   * @return column
  **/
  @ApiModelProperty(required = true, value = "")

  public List<ColumnType2> getColumn() {
    return column;
  }


  public void setColumn(List<ColumnType2> column) {
    this.column = column;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnsType columnsType = (ColumnsType) o;
    return Objects.equals(this.column, columnsType.column);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ColumnsType {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
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

