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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ProvisionReportItem
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class ProvisionReportItem {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_INHERITED_FROM_GROUPS = "inheritedFromGroups";
  @SerializedName(SERIALIZED_NAME_INHERITED_FROM_GROUPS)
  private Set<String> inheritedFromGroups = null;


  public ProvisionReportItem name(String name) {
    
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


  public ProvisionReportItem inheritedFromGroups(Set<String> inheritedFromGroups) {
    
    this.inheritedFromGroups = inheritedFromGroups;
    return this;
  }

  public ProvisionReportItem addInheritedFromGroupsItem(String inheritedFromGroupsItem) {
    if (this.inheritedFromGroups == null) {
      this.inheritedFromGroups = new LinkedHashSet<String>();
    }
    this.inheritedFromGroups.add(inheritedFromGroupsItem);
    return this;
  }

   /**
   * Get inheritedFromGroups
   * @return inheritedFromGroups
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Set<String> getInheritedFromGroups() {
    return inheritedFromGroups;
  }


  public void setInheritedFromGroups(Set<String> inheritedFromGroups) {
    this.inheritedFromGroups = inheritedFromGroups;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProvisionReportItem provisionReportItem = (ProvisionReportItem) o;
    return Objects.equals(this.name, provisionReportItem.name) &&
        Objects.equals(this.inheritedFromGroups, provisionReportItem.inheritedFromGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, inheritedFromGroups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProvisionReportItem {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    inheritedFromGroups: ").append(toIndentedString(inheritedFromGroups)).append("\n");
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

