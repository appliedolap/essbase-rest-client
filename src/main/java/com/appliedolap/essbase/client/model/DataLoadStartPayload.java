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
 * DataLoadStartPayload
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class DataLoadStartPayload {
  public static final String SERIALIZED_NAME_RULE_FILE_NAME = "ruleFileName";
  @SerializedName(SERIALIZED_NAME_RULE_FILE_NAME)
  private String ruleFileName;

  public static final String SERIALIZED_NAME_DELIMITER = "delimiter";
  @SerializedName(SERIALIZED_NAME_DELIMITER)
  private String delimiter;


  public DataLoadStartPayload ruleFileName(String ruleFileName) {
    
    this.ruleFileName = ruleFileName;
    return this;
  }

   /**
   * Get ruleFileName
   * @return ruleFileName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getRuleFileName() {
    return ruleFileName;
  }


  public void setRuleFileName(String ruleFileName) {
    this.ruleFileName = ruleFileName;
  }


  public DataLoadStartPayload delimiter(String delimiter) {
    
    this.delimiter = delimiter;
    return this;
  }

   /**
   * Currently only Comma is supported as delimiter
   * @return delimiter
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Currently only Comma is supported as delimiter")

  public String getDelimiter() {
    return delimiter;
  }


  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataLoadStartPayload dataLoadStartPayload = (DataLoadStartPayload) o;
    return Objects.equals(this.ruleFileName, dataLoadStartPayload.ruleFileName) &&
        Objects.equals(this.delimiter, dataLoadStartPayload.delimiter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruleFileName, delimiter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataLoadStartPayload {\n");
    sb.append("    ruleFileName: ").append(toIndentedString(ruleFileName)).append("\n");
    sb.append("    delimiter: ").append(toIndentedString(delimiter)).append("\n");
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
