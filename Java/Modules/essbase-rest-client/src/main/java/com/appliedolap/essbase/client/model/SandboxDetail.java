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
import com.appliedolap.essbase.client.model.Link;
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
 * SandboxDetail
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class SandboxDetail {
  public static final String SERIALIZED_NAME_TOTAL = "total";
  @SerializedName(SERIALIZED_NAME_TOTAL)
  private Integer total;

  public static final String SERIALIZED_NAME_AVAILABLE = "available";
  @SerializedName(SERIALIZED_NAME_AVAILABLE)
  private Integer available;

  public static final String SERIALIZED_NAME_ASSIGNED = "assigned";
  @SerializedName(SERIALIZED_NAME_ASSIGNED)
  private Integer assigned;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public SandboxDetail total(Integer total) {
    
    this.total = total;
    return this;
  }

   /**
   * Get total
   * @return total
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getTotal() {
    return total;
  }


  public void setTotal(Integer total) {
    this.total = total;
  }


  public SandboxDetail available(Integer available) {
    
    this.available = available;
    return this;
  }

   /**
   * Get available
   * @return available
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAvailable() {
    return available;
  }


  public void setAvailable(Integer available) {
    this.available = available;
  }


  public SandboxDetail assigned(Integer assigned) {
    
    this.assigned = assigned;
    return this;
  }

   /**
   * Get assigned
   * @return assigned
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAssigned() {
    return assigned;
  }


  public void setAssigned(Integer assigned) {
    this.assigned = assigned;
  }


  public SandboxDetail links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public SandboxDetail addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Link> getLinks() {
    return links;
  }


  public void setLinks(List<Link> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SandboxDetail sandboxDetail = (SandboxDetail) o;
    return Objects.equals(this.total, sandboxDetail.total) &&
        Objects.equals(this.available, sandboxDetail.available) &&
        Objects.equals(this.assigned, sandboxDetail.assigned) &&
        Objects.equals(this.links, sandboxDetail.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(total, available, assigned, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SandboxDetail {\n");
    sb.append("    total: ").append(toIndentedString(total)).append("\n");
    sb.append("    available: ").append(toIndentedString(available)).append("\n");
    sb.append("    assigned: ").append(toIndentedString(assigned)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

