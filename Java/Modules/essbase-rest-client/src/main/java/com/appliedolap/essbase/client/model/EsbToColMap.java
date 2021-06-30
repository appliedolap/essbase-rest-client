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
import com.appliedolap.essbase.client.model.EsbToColMapInfo;
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
 * EsbToColMap
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class EsbToColMap {
  public static final String SERIALIZED_NAME_ARR = "arr";
  @SerializedName(SERIALIZED_NAME_ARR)
  private List<EsbToColMapInfo> arr = null;


  public EsbToColMap arr(List<EsbToColMapInfo> arr) {
    
    this.arr = arr;
    return this;
  }

  public EsbToColMap addArrItem(EsbToColMapInfo arrItem) {
    if (this.arr == null) {
      this.arr = new ArrayList<EsbToColMapInfo>();
    }
    this.arr.add(arrItem);
    return this;
  }

   /**
   * Get arr
   * @return arr
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<EsbToColMapInfo> getArr() {
    return arr;
  }


  public void setArr(List<EsbToColMapInfo> arr) {
    this.arr = arr;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EsbToColMap esbToColMap = (EsbToColMap) o;
    return Objects.equals(this.arr, esbToColMap.arr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EsbToColMap {\n");
    sb.append("    arr: ").append(toIndentedString(arr)).append("\n");
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

