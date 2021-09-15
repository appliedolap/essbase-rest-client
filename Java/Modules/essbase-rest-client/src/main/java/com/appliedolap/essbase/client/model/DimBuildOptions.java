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
import com.appliedolap.essbase.client.model.SmartList;
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
 * DimBuildOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class DimBuildOptions {
  public static final String SERIALIZED_NAME_AUTO_CONFIG = "autoConfig";
  @SerializedName(SERIALIZED_NAME_AUTO_CONFIG)
  private Boolean autoConfig;

  public static final String SERIALIZED_NAME_ARRANGE_DIMENSIONS = "arrangeDimensions";
  @SerializedName(SERIALIZED_NAME_ARRANGE_DIMENSIONS)
  private Boolean arrangeDimensions;

  public static final String SERIALIZED_NAME_ALIAS_TABLE = "aliasTable";
  @SerializedName(SERIALIZED_NAME_ALIAS_TABLE)
  private String aliasTable;

  public static final String SERIALIZED_NAME_SMART_LISTS = "smartLists";
  @SerializedName(SERIALIZED_NAME_SMART_LISTS)
  private List<SmartList> smartLists = null;


  public DimBuildOptions autoConfig(Boolean autoConfig) {
    
    this.autoConfig = autoConfig;
    return this;
  }

   /**
   * Get autoConfig
   * @return autoConfig
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAutoConfig() {
    return autoConfig;
  }


  public void setAutoConfig(Boolean autoConfig) {
    this.autoConfig = autoConfig;
  }


  public DimBuildOptions arrangeDimensions(Boolean arrangeDimensions) {
    
    this.arrangeDimensions = arrangeDimensions;
    return this;
  }

   /**
   * Get arrangeDimensions
   * @return arrangeDimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getArrangeDimensions() {
    return arrangeDimensions;
  }


  public void setArrangeDimensions(Boolean arrangeDimensions) {
    this.arrangeDimensions = arrangeDimensions;
  }


  public DimBuildOptions aliasTable(String aliasTable) {
    
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


  public DimBuildOptions smartLists(List<SmartList> smartLists) {
    
    this.smartLists = smartLists;
    return this;
  }

  public DimBuildOptions addSmartListsItem(SmartList smartListsItem) {
    if (this.smartLists == null) {
      this.smartLists = new ArrayList<SmartList>();
    }
    this.smartLists.add(smartListsItem);
    return this;
  }

   /**
   * Get smartLists
   * @return smartLists
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<SmartList> getSmartLists() {
    return smartLists;
  }


  public void setSmartLists(List<SmartList> smartLists) {
    this.smartLists = smartLists;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DimBuildOptions dimBuildOptions = (DimBuildOptions) o;
    return Objects.equals(this.autoConfig, dimBuildOptions.autoConfig) &&
        Objects.equals(this.arrangeDimensions, dimBuildOptions.arrangeDimensions) &&
        Objects.equals(this.aliasTable, dimBuildOptions.aliasTable) &&
        Objects.equals(this.smartLists, dimBuildOptions.smartLists);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoConfig, arrangeDimensions, aliasTable, smartLists);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DimBuildOptions {\n");
    sb.append("    autoConfig: ").append(toIndentedString(autoConfig)).append("\n");
    sb.append("    arrangeDimensions: ").append(toIndentedString(arrangeDimensions)).append("\n");
    sb.append("    aliasTable: ").append(toIndentedString(aliasTable)).append("\n");
    sb.append("    smartLists: ").append(toIndentedString(smartLists)).append("\n");
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

