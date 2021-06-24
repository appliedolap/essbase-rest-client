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
import com.appliedolap.essbase.client.model.GeneralStatistics;
import com.appliedolap.essbase.client.model.Link;
import com.appliedolap.essbase.client.model.RuntimeStatistics;
import com.appliedolap.essbase.client.model.StorageStatistics;
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
 * StatisticsList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-23T11:48:17.898-07:00[America/Los_Angeles]")
public class StatisticsList {
  public static final String SERIALIZED_NAME_GENERAL = "general";
  @SerializedName(SERIALIZED_NAME_GENERAL)
  private GeneralStatistics general;

  public static final String SERIALIZED_NAME_STORAGE = "storage";
  @SerializedName(SERIALIZED_NAME_STORAGE)
  private StorageStatistics storage;

  public static final String SERIALIZED_NAME_RUNTIME = "runtime";
  @SerializedName(SERIALIZED_NAME_RUNTIME)
  private RuntimeStatistics runtime;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public StatisticsList general(GeneralStatistics general) {
    
    this.general = general;
    return this;
  }

   /**
   * Get general
   * @return general
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public GeneralStatistics getGeneral() {
    return general;
  }


  public void setGeneral(GeneralStatistics general) {
    this.general = general;
  }


  public StatisticsList storage(StorageStatistics storage) {
    
    this.storage = storage;
    return this;
  }

   /**
   * Get storage
   * @return storage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public StorageStatistics getStorage() {
    return storage;
  }


  public void setStorage(StorageStatistics storage) {
    this.storage = storage;
  }


  public StatisticsList runtime(RuntimeStatistics runtime) {
    
    this.runtime = runtime;
    return this;
  }

   /**
   * Get runtime
   * @return runtime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public RuntimeStatistics getRuntime() {
    return runtime;
  }


  public void setRuntime(RuntimeStatistics runtime) {
    this.runtime = runtime;
  }


  public StatisticsList links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public StatisticsList addLinksItem(Link linksItem) {
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
    StatisticsList statisticsList = (StatisticsList) o;
    return Objects.equals(this.general, statisticsList.general) &&
        Objects.equals(this.storage, statisticsList.storage) &&
        Objects.equals(this.runtime, statisticsList.runtime) &&
        Objects.equals(this.links, statisticsList.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(general, storage, runtime, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatisticsList {\n");
    sb.append("    general: ").append(toIndentedString(general)).append("\n");
    sb.append("    storage: ").append(toIndentedString(storage)).append("\n");
    sb.append("    runtime: ").append(toIndentedString(runtime)).append("\n");
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

