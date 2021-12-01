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
import com.appliedolap.essbase.client.model.LayoutData;
import com.appliedolap.essbase.client.model.LayoutDimension;
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
 * LayoutGrid
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class LayoutGrid {
  public static final String SERIALIZED_NAME_ALIAS = "alias";
  @SerializedName(SERIALIZED_NAME_ALIAS)
  private String alias;

  public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS)
  private List<LayoutDimension> dimensions = null;

  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private LayoutData data;


  public LayoutGrid alias(String alias) {
    
    this.alias = alias;
    return this;
  }

   /**
   * Get alias
   * @return alias
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAlias() {
    return alias;
  }


  public void setAlias(String alias) {
    this.alias = alias;
  }


  public LayoutGrid dimensions(List<LayoutDimension> dimensions) {
    
    this.dimensions = dimensions;
    return this;
  }

  public LayoutGrid addDimensionsItem(LayoutDimension dimensionsItem) {
    if (this.dimensions == null) {
      this.dimensions = new ArrayList<LayoutDimension>();
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

  public List<LayoutDimension> getDimensions() {
    return dimensions;
  }


  public void setDimensions(List<LayoutDimension> dimensions) {
    this.dimensions = dimensions;
  }


  public LayoutGrid data(LayoutData data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LayoutData getData() {
    return data;
  }


  public void setData(LayoutData data) {
    this.data = data;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LayoutGrid layoutGrid = (LayoutGrid) o;
    return Objects.equals(this.alias, layoutGrid.alias) &&
        Objects.equals(this.dimensions, layoutGrid.dimensions) &&
        Objects.equals(this.data, layoutGrid.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alias, dimensions, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LayoutGrid {\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

