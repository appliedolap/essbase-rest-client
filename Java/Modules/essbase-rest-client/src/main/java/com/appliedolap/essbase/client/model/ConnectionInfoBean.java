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
import com.appliedolap.essbase.client.model.EsbToColMap;
import com.appliedolap.essbase.client.model.EssToDsMapDTO;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ConnectionInfoBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class ConnectionInfoBean {
  public static final String SERIALIZED_NAME_CONNECTION_NAME = "connectionName";
  @SerializedName(SERIALIZED_NAME_CONNECTION_NAME)
  private String connectionName;

  public static final String SERIALIZED_NAME_SERVER_NAME = "serverName";
  @SerializedName(SERIALIZED_NAME_SERVER_NAME)
  private String serverName;

  public static final String SERIALIZED_NAME_USER_NAME = "userName";
  @SerializedName(SERIALIZED_NAME_USER_NAME)
  private String userName;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_APPLICATION_NAME = "applicationName";
  @SerializedName(SERIALIZED_NAME_APPLICATION_NAME)
  private String applicationName;

  public static final String SERIALIZED_NAME_DATABASE_NAME = "databaseName";
  @SerializedName(SERIALIZED_NAME_DATABASE_NAME)
  private String databaseName;

  public static final String SERIALIZED_NAME_DATASOURCE_NAME = "datasourceName";
  @SerializedName(SERIALIZED_NAME_DATASOURCE_NAME)
  private String datasourceName;

  public static final String SERIALIZED_NAME_MEASURES_DIMENSION_NAME = "measuresDimensionName";
  @SerializedName(SERIALIZED_NAME_MEASURES_DIMENSION_NAME)
  private String measuresDimensionName;

  public static final String SERIALIZED_NAME_ESSBASE_TO_COLUMN_MAP = "essbaseToColumnMap";
  @SerializedName(SERIALIZED_NAME_ESSBASE_TO_COLUMN_MAP)
  private EsbToColMap essbaseToColumnMap;

  public static final String SERIALIZED_NAME_ESSBASE_TO_DATA_SOURCE_MAP = "essbaseToDataSourceMap";
  @SerializedName(SERIALIZED_NAME_ESSBASE_TO_DATA_SOURCE_MAP)
  private EssToDsMapDTO essbaseToDataSourceMap;

  public static final String SERIALIZED_NAME_ALTER_CREDENTIALS = "alterCredentials";
  @SerializedName(SERIALIZED_NAME_ALTER_CREDENTIALS)
  private Boolean alterCredentials;

  public static final String SERIALIZED_NAME_APPLICATION_LEVEL_CONNECTION = "applicationLevelConnection";
  @SerializedName(SERIALIZED_NAME_APPLICATION_LEVEL_CONNECTION)
  private Boolean applicationLevelConnection;

  public static final String SERIALIZED_NAME_APPLICATION_LEVEL_DATASOURCE = "applicationLevelDatasource";
  @SerializedName(SERIALIZED_NAME_APPLICATION_LEVEL_DATASOURCE)
  private Boolean applicationLevelDatasource;


  public ConnectionInfoBean connectionName(String connectionName) {
    
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


  public ConnectionInfoBean serverName(String serverName) {
    
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


  public ConnectionInfoBean userName(String userName) {
    
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


  public ConnectionInfoBean password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public ConnectionInfoBean description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ConnectionInfoBean applicationName(String applicationName) {
    
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


  public ConnectionInfoBean databaseName(String databaseName) {
    
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


  public ConnectionInfoBean datasourceName(String datasourceName) {
    
    this.datasourceName = datasourceName;
    return this;
  }

   /**
   * Get datasourceName
   * @return datasourceName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatasourceName() {
    return datasourceName;
  }


  public void setDatasourceName(String datasourceName) {
    this.datasourceName = datasourceName;
  }


  public ConnectionInfoBean measuresDimensionName(String measuresDimensionName) {
    
    this.measuresDimensionName = measuresDimensionName;
    return this;
  }

   /**
   * Get measuresDimensionName
   * @return measuresDimensionName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMeasuresDimensionName() {
    return measuresDimensionName;
  }


  public void setMeasuresDimensionName(String measuresDimensionName) {
    this.measuresDimensionName = measuresDimensionName;
  }


  public ConnectionInfoBean essbaseToColumnMap(EsbToColMap essbaseToColumnMap) {
    
    this.essbaseToColumnMap = essbaseToColumnMap;
    return this;
  }

   /**
   * Get essbaseToColumnMap
   * @return essbaseToColumnMap
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public EsbToColMap getEssbaseToColumnMap() {
    return essbaseToColumnMap;
  }


  public void setEssbaseToColumnMap(EsbToColMap essbaseToColumnMap) {
    this.essbaseToColumnMap = essbaseToColumnMap;
  }


  public ConnectionInfoBean essbaseToDataSourceMap(EssToDsMapDTO essbaseToDataSourceMap) {
    
    this.essbaseToDataSourceMap = essbaseToDataSourceMap;
    return this;
  }

   /**
   * Get essbaseToDataSourceMap
   * @return essbaseToDataSourceMap
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public EssToDsMapDTO getEssbaseToDataSourceMap() {
    return essbaseToDataSourceMap;
  }


  public void setEssbaseToDataSourceMap(EssToDsMapDTO essbaseToDataSourceMap) {
    this.essbaseToDataSourceMap = essbaseToDataSourceMap;
  }


  public ConnectionInfoBean alterCredentials(Boolean alterCredentials) {
    
    this.alterCredentials = alterCredentials;
    return this;
  }

   /**
   * Get alterCredentials
   * @return alterCredentials
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAlterCredentials() {
    return alterCredentials;
  }


  public void setAlterCredentials(Boolean alterCredentials) {
    this.alterCredentials = alterCredentials;
  }


  public ConnectionInfoBean applicationLevelConnection(Boolean applicationLevelConnection) {
    
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


  public ConnectionInfoBean applicationLevelDatasource(Boolean applicationLevelDatasource) {
    
    this.applicationLevelDatasource = applicationLevelDatasource;
    return this;
  }

   /**
   * Get applicationLevelDatasource
   * @return applicationLevelDatasource
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getApplicationLevelDatasource() {
    return applicationLevelDatasource;
  }


  public void setApplicationLevelDatasource(Boolean applicationLevelDatasource) {
    this.applicationLevelDatasource = applicationLevelDatasource;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectionInfoBean connectionInfoBean = (ConnectionInfoBean) o;
    return Objects.equals(this.connectionName, connectionInfoBean.connectionName) &&
        Objects.equals(this.serverName, connectionInfoBean.serverName) &&
        Objects.equals(this.userName, connectionInfoBean.userName) &&
        Objects.equals(this.password, connectionInfoBean.password) &&
        Objects.equals(this.description, connectionInfoBean.description) &&
        Objects.equals(this.applicationName, connectionInfoBean.applicationName) &&
        Objects.equals(this.databaseName, connectionInfoBean.databaseName) &&
        Objects.equals(this.datasourceName, connectionInfoBean.datasourceName) &&
        Objects.equals(this.measuresDimensionName, connectionInfoBean.measuresDimensionName) &&
        Objects.equals(this.essbaseToColumnMap, connectionInfoBean.essbaseToColumnMap) &&
        Objects.equals(this.essbaseToDataSourceMap, connectionInfoBean.essbaseToDataSourceMap) &&
        Objects.equals(this.alterCredentials, connectionInfoBean.alterCredentials) &&
        Objects.equals(this.applicationLevelConnection, connectionInfoBean.applicationLevelConnection) &&
        Objects.equals(this.applicationLevelDatasource, connectionInfoBean.applicationLevelDatasource);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectionName, serverName, userName, password, description, applicationName, databaseName, datasourceName, measuresDimensionName, essbaseToColumnMap, essbaseToDataSourceMap, alterCredentials, applicationLevelConnection, applicationLevelDatasource);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnectionInfoBean {\n");
    sb.append("    connectionName: ").append(toIndentedString(connectionName)).append("\n");
    sb.append("    serverName: ").append(toIndentedString(serverName)).append("\n");
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    databaseName: ").append(toIndentedString(databaseName)).append("\n");
    sb.append("    datasourceName: ").append(toIndentedString(datasourceName)).append("\n");
    sb.append("    measuresDimensionName: ").append(toIndentedString(measuresDimensionName)).append("\n");
    sb.append("    essbaseToColumnMap: ").append(toIndentedString(essbaseToColumnMap)).append("\n");
    sb.append("    essbaseToDataSourceMap: ").append(toIndentedString(essbaseToDataSourceMap)).append("\n");
    sb.append("    alterCredentials: ").append(toIndentedString(alterCredentials)).append("\n");
    sb.append("    applicationLevelConnection: ").append(toIndentedString(applicationLevelConnection)).append("\n");
    sb.append("    applicationLevelDatasource: ").append(toIndentedString(applicationLevelDatasource)).append("\n");
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

