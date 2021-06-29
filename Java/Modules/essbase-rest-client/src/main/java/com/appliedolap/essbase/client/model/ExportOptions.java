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
 * ExportOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class ExportOptions {
  public static final String SERIALIZED_NAME_ALIAS_TABLE = "aliasTable";
  @SerializedName(SERIALIZED_NAME_ALIAS_TABLE)
  private String aliasTable;

  public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS)
  private List<String> dimensions = null;

  public static final String SERIALIZED_NAME_TREE = "tree";
  @SerializedName(SERIALIZED_NAME_TREE)
  private Boolean tree;


  public ExportOptions aliasTable(String aliasTable) {
    
    this.aliasTable = aliasTable;
    return this;
  }

   /**
   * Get aliasTable
   * @return aliasTable
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAliasTable() {
    return aliasTable;
  }


  public void setAliasTable(String aliasTable) {
    this.aliasTable = aliasTable;
  }


  public ExportOptions dimensions(List<String> dimensions) {
    
    this.dimensions = dimensions;
    return this;
  }

  public ExportOptions addDimensionsItem(String dimensionsItem) {
    if (this.dimensions == null) {
      this.dimensions = new ArrayList<String>();
    }
    this.dimensions.add(dimensionsItem);
    return this;
  }

   /**
   * Get dimensions
   * @return dimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getDimensions() {
    return dimensions;
  }


  public void setDimensions(List<String> dimensions) {
    this.dimensions = dimensions;
  }


  public ExportOptions tree(Boolean tree) {
    
    this.tree = tree;
    return this;
  }

   /**
   * Get tree
   * @return tree
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTree() {
    return tree;
  }


  public void setTree(Boolean tree) {
    this.tree = tree;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExportOptions exportOptions = (ExportOptions) o;
    return Objects.equals(this.aliasTable, exportOptions.aliasTable) &&
        Objects.equals(this.dimensions, exportOptions.dimensions) &&
        Objects.equals(this.tree, exportOptions.tree);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aliasTable, dimensions, tree);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExportOptions {\n");
    sb.append("    aliasTable: ").append(toIndentedString(aliasTable)).append("\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    tree: ").append(toIndentedString(tree)).append("\n");
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

