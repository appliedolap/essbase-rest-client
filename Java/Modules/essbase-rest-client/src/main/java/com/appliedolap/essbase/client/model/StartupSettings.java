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
 * StartupSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class StartupSettings {
  public static final String SERIALIZED_NAME_ALLOW_USERS_TO_START_DATABASE = "allowUsersToStartDatabase";
  @SerializedName(SERIALIZED_NAME_ALLOW_USERS_TO_START_DATABASE)
  private Boolean allowUsersToStartDatabase;

  public static final String SERIALIZED_NAME_START_DATABASE_WHEN_APPLICATION_STARTS = "startDatabaseWhenApplicationStarts";
  @SerializedName(SERIALIZED_NAME_START_DATABASE_WHEN_APPLICATION_STARTS)
  private Boolean startDatabaseWhenApplicationStarts;


  public StartupSettings allowUsersToStartDatabase(Boolean allowUsersToStartDatabase) {
    
    this.allowUsersToStartDatabase = allowUsersToStartDatabase;
    return this;
  }

   /**
   * Get allowUsersToStartDatabase
   * @return allowUsersToStartDatabase
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowUsersToStartDatabase() {
    return allowUsersToStartDatabase;
  }


  public void setAllowUsersToStartDatabase(Boolean allowUsersToStartDatabase) {
    this.allowUsersToStartDatabase = allowUsersToStartDatabase;
  }


  public StartupSettings startDatabaseWhenApplicationStarts(Boolean startDatabaseWhenApplicationStarts) {
    
    this.startDatabaseWhenApplicationStarts = startDatabaseWhenApplicationStarts;
    return this;
  }

   /**
   * Get startDatabaseWhenApplicationStarts
   * @return startDatabaseWhenApplicationStarts
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStartDatabaseWhenApplicationStarts() {
    return startDatabaseWhenApplicationStarts;
  }


  public void setStartDatabaseWhenApplicationStarts(Boolean startDatabaseWhenApplicationStarts) {
    this.startDatabaseWhenApplicationStarts = startDatabaseWhenApplicationStarts;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StartupSettings startupSettings = (StartupSettings) o;
    return Objects.equals(this.allowUsersToStartDatabase, startupSettings.allowUsersToStartDatabase) &&
        Objects.equals(this.startDatabaseWhenApplicationStarts, startupSettings.startDatabaseWhenApplicationStarts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowUsersToStartDatabase, startDatabaseWhenApplicationStarts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StartupSettings {\n");
    sb.append("    allowUsersToStartDatabase: ").append(toIndentedString(allowUsersToStartDatabase)).append("\n");
    sb.append("    startDatabaseWhenApplicationStarts: ").append(toIndentedString(startDatabaseWhenApplicationStarts)).append("\n");
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

