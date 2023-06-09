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
import com.appliedolap.essbase.client.model.AppGeneralSettings;
import com.appliedolap.essbase.client.model.AppSecuritySettings;
import com.appliedolap.essbase.client.model.AppStartupSettings;
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
 * AppSettingsList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class AppSettingsList {
  public static final String SERIALIZED_NAME_GENERAL = "general";
  @SerializedName(SERIALIZED_NAME_GENERAL)
  private AppGeneralSettings general;

  public static final String SERIALIZED_NAME_STARTUP = "startup";
  @SerializedName(SERIALIZED_NAME_STARTUP)
  private AppStartupSettings startup;

  public static final String SERIALIZED_NAME_SECURITY = "security";
  @SerializedName(SERIALIZED_NAME_SECURITY)
  private AppSecuritySettings security;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public AppSettingsList general(AppGeneralSettings general) {
    
    this.general = general;
    return this;
  }

   /**
   * Get general
   * @return general
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AppGeneralSettings getGeneral() {
    return general;
  }


  public void setGeneral(AppGeneralSettings general) {
    this.general = general;
  }


  public AppSettingsList startup(AppStartupSettings startup) {
    
    this.startup = startup;
    return this;
  }

   /**
   * Get startup
   * @return startup
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AppStartupSettings getStartup() {
    return startup;
  }


  public void setStartup(AppStartupSettings startup) {
    this.startup = startup;
  }


  public AppSettingsList security(AppSecuritySettings security) {
    
    this.security = security;
    return this;
  }

   /**
   * Get security
   * @return security
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AppSecuritySettings getSecurity() {
    return security;
  }


  public void setSecurity(AppSecuritySettings security) {
    this.security = security;
  }


  public AppSettingsList links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public AppSettingsList addLinksItem(Link linksItem) {
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
    AppSettingsList appSettingsList = (AppSettingsList) o;
    return Objects.equals(this.general, appSettingsList.general) &&
        Objects.equals(this.startup, appSettingsList.startup) &&
        Objects.equals(this.security, appSettingsList.security) &&
        Objects.equals(this.links, appSettingsList.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(general, startup, security, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppSettingsList {\n");
    sb.append("    general: ").append(toIndentedString(general)).append("\n");
    sb.append("    startup: ").append(toIndentedString(startup)).append("\n");
    sb.append("    security: ").append(toIndentedString(security)).append("\n");
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

