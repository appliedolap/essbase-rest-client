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
 * FilterRow
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class FilterRow {
  public static final String SERIALIZED_NAME_ACCESS = "access";
  @SerializedName(SERIALIZED_NAME_ACCESS)
  private String access;

  public static final String SERIALIZED_NAME_MBR_SPEC = "mbrSpec";
  @SerializedName(SERIALIZED_NAME_MBR_SPEC)
  private String mbrSpec;


  public FilterRow access(String access) {
    
    this.access = access;
    return this;
  }

   /**
   * Get access
   * @return access
  **/
  @ApiModelProperty(required = true, value = "")

  public String getAccess() {
    return access;
  }


  public void setAccess(String access) {
    this.access = access;
  }


  public FilterRow mbrSpec(String mbrSpec) {
    
    this.mbrSpec = mbrSpec;
    return this;
  }

   /**
   * Get mbrSpec
   * @return mbrSpec
  **/
  @ApiModelProperty(required = true, value = "")

  public String getMbrSpec() {
    return mbrSpec;
  }


  public void setMbrSpec(String mbrSpec) {
    this.mbrSpec = mbrSpec;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FilterRow filterRow = (FilterRow) o;
    return Objects.equals(this.access, filterRow.access) &&
        Objects.equals(this.mbrSpec, filterRow.mbrSpec);
  }

  @Override
  public int hashCode() {
    return Objects.hash(access, mbrSpec);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FilterRow {\n");
    sb.append("    access: ").append(toIndentedString(access)).append("\n");
    sb.append("    mbrSpec: ").append(toIndentedString(mbrSpec)).append("\n");
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

