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
 * GridDimension
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class GridDimension {
  public static final String SERIALIZED_NAME_COLUMN = "column";
  @SerializedName(SERIALIZED_NAME_COLUMN)
  private Integer column;

  public static final String SERIALIZED_NAME_ROW = "row";
  @SerializedName(SERIALIZED_NAME_ROW)
  private Integer row;

  public static final String SERIALIZED_NAME_POV = "pov";
  @SerializedName(SERIALIZED_NAME_POV)
  private String pov;

  public static final String SERIALIZED_NAME_EXPANDED = "expanded";
  @SerializedName(SERIALIZED_NAME_EXPANDED)
  private Boolean expanded;

  public static final String SERIALIZED_NAME_DISPLAY_NAME = "displayName";
  @SerializedName(SERIALIZED_NAME_DISPLAY_NAME)
  private String displayName;

  public static final String SERIALIZED_NAME_HIDDEN = "hidden";
  @SerializedName(SERIALIZED_NAME_HIDDEN)
  private Boolean hidden;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;


  public GridDimension column(Integer column) {
    
    this.column = column;
    return this;
  }

   /**
   * Get column
   * @return column
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getColumn() {
    return column;
  }


  public void setColumn(Integer column) {
    this.column = column;
  }


  public GridDimension row(Integer row) {
    
    this.row = row;
    return this;
  }

   /**
   * Get row
   * @return row
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRow() {
    return row;
  }


  public void setRow(Integer row) {
    this.row = row;
  }


  public GridDimension pov(String pov) {
    
    this.pov = pov;
    return this;
  }

   /**
   * Get pov
   * @return pov
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPov() {
    return pov;
  }


  public void setPov(String pov) {
    this.pov = pov;
  }


  public GridDimension expanded(Boolean expanded) {
    
    this.expanded = expanded;
    return this;
  }

   /**
   * Get expanded
   * @return expanded
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getExpanded() {
    return expanded;
  }


  public void setExpanded(Boolean expanded) {
    this.expanded = expanded;
  }


  public GridDimension displayName(String displayName) {
    
    this.displayName = displayName;
    return this;
  }

   /**
   * Get displayName
   * @return displayName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDisplayName() {
    return displayName;
  }


  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  public GridDimension hidden(Boolean hidden) {
    
    this.hidden = hidden;
    return this;
  }

   /**
   * Get hidden
   * @return hidden
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getHidden() {
    return hidden;
  }


  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }


  public GridDimension name(String name) {
    
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GridDimension gridDimension = (GridDimension) o;
    return Objects.equals(this.column, gridDimension.column) &&
        Objects.equals(this.row, gridDimension.row) &&
        Objects.equals(this.pov, gridDimension.pov) &&
        Objects.equals(this.expanded, gridDimension.expanded) &&
        Objects.equals(this.displayName, gridDimension.displayName) &&
        Objects.equals(this.hidden, gridDimension.hidden) &&
        Objects.equals(this.name, gridDimension.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row, pov, expanded, displayName, hidden, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GridDimension {\n");
    sb.append("    column: ").append(toIndentedString(column)).append("\n");
    sb.append("    row: ").append(toIndentedString(row)).append("\n");
    sb.append("    pov: ").append(toIndentedString(pov)).append("\n");
    sb.append("    expanded: ").append(toIndentedString(expanded)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    hidden: ").append(toIndentedString(hidden)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

