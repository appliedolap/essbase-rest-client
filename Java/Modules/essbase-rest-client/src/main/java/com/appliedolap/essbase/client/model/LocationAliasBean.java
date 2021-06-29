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
 * LocationAliasBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class LocationAliasBean {
  public static final String SERIALIZED_NAME_ALIAS_NAME = "aliasName";
  @SerializedName(SERIALIZED_NAME_ALIAS_NAME)
  private String aliasName;

  public static final String SERIALIZED_NAME_CONNECTION_NAME = "connectionName";
  @SerializedName(SERIALIZED_NAME_CONNECTION_NAME)
  private String connectionName;

  public static final String SERIALIZED_NAME_SERVER_NAME = "serverName";
  @SerializedName(SERIALIZED_NAME_SERVER_NAME)
  private String serverName;

  public static final String SERIALIZED_NAME_USER_NAME = "userName";
  @SerializedName(SERIALIZED_NAME_USER_NAME)
  private String userName;

  public static final String SERIALIZED_NAME_APPLICATION_NAME = "applicationName";
  @SerializedName(SERIALIZED_NAME_APPLICATION_NAME)
  private String applicationName;

  public static final String SERIALIZED_NAME_DATABASE_NAME = "databaseName";
  @SerializedName(SERIALIZED_NAME_DATABASE_NAME)
  private String databaseName;

  public static final String SERIALIZED_NAME_APPLICATION_LEVEL_CONNECTION = "applicationLevelConnection";
  @SerializedName(SERIALIZED_NAME_APPLICATION_LEVEL_CONNECTION)
  private Boolean applicationLevelConnection;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public LocationAliasBean aliasName(String aliasName) {
    
    this.aliasName = aliasName;
    return this;
  }

   /**
   * Get aliasName
   * @return aliasName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAliasName() {
    return aliasName;
  }


  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }


  public LocationAliasBean connectionName(String connectionName) {
    
    this.connectionName = connectionName;
    return this;
  }

   /**
   * Get connectionName
   * @return connectionName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getConnectionName() {
    return connectionName;
  }


  public void setConnectionName(String connectionName) {
    this.connectionName = connectionName;
  }


  public LocationAliasBean serverName(String serverName) {
    
    this.serverName = serverName;
    return this;
  }

   /**
   * Get serverName
   * @return serverName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getServerName() {
    return serverName;
  }


  public void setServerName(String serverName) {
    this.serverName = serverName;
  }


  public LocationAliasBean userName(String userName) {
    
    this.userName = userName;
    return this;
  }

   /**
   * Get userName
   * @return userName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public LocationAliasBean applicationName(String applicationName) {
    
    this.applicationName = applicationName;
    return this;
  }

   /**
   * Get applicationName
   * @return applicationName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplicationName() {
    return applicationName;
  }


  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }


  public LocationAliasBean databaseName(String databaseName) {
    
    this.databaseName = databaseName;
    return this;
  }

   /**
   * Get databaseName
   * @return databaseName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabaseName() {
    return databaseName;
  }


  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }


  public LocationAliasBean applicationLevelConnection(Boolean applicationLevelConnection) {
    
    this.applicationLevelConnection = applicationLevelConnection;
    return this;
  }

   /**
   * Get applicationLevelConnection
   * @return applicationLevelConnection
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getApplicationLevelConnection() {
    return applicationLevelConnection;
  }


  public void setApplicationLevelConnection(Boolean applicationLevelConnection) {
    this.applicationLevelConnection = applicationLevelConnection;
  }


  public LocationAliasBean links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public LocationAliasBean addLinksItem(Link linksItem) {
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
    LocationAliasBean locationAliasBean = (LocationAliasBean) o;
    return Objects.equals(this.aliasName, locationAliasBean.aliasName) &&
        Objects.equals(this.connectionName, locationAliasBean.connectionName) &&
        Objects.equals(this.serverName, locationAliasBean.serverName) &&
        Objects.equals(this.userName, locationAliasBean.userName) &&
        Objects.equals(this.applicationName, locationAliasBean.applicationName) &&
        Objects.equals(this.databaseName, locationAliasBean.databaseName) &&
        Objects.equals(this.applicationLevelConnection, locationAliasBean.applicationLevelConnection) &&
        Objects.equals(this.links, locationAliasBean.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(aliasName, connectionName, serverName, userName, applicationName, databaseName, applicationLevelConnection, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LocationAliasBean {\n");
    sb.append("    aliasName: ").append(toIndentedString(aliasName)).append("\n");
    sb.append("    connectionName: ").append(toIndentedString(connectionName)).append("\n");
    sb.append("    serverName: ").append(toIndentedString(serverName)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    databaseName: ").append(toIndentedString(databaseName)).append("\n");
    sb.append("    applicationLevelConnection: ").append(toIndentedString(applicationLevelConnection)).append("\n");
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

