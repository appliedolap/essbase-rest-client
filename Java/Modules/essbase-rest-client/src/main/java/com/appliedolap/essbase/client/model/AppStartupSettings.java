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
 * AppStartupSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class AppStartupSettings {
  public static final String SERIALIZED_NAME_ALLOW_USERS_TO_START_APPLICATION = "allowUsersToStartApplication";
  @SerializedName(SERIALIZED_NAME_ALLOW_USERS_TO_START_APPLICATION)
  private Boolean allowUsersToStartApplication;

  public static final String SERIALIZED_NAME_START_APPLICATION_WHEN_SERVER_STARTS = "startApplicationWhenServerStarts";
  @SerializedName(SERIALIZED_NAME_START_APPLICATION_WHEN_SERVER_STARTS)
  private Boolean startApplicationWhenServerStarts;


  public AppStartupSettings allowUsersToStartApplication(Boolean allowUsersToStartApplication) {
    
    this.allowUsersToStartApplication = allowUsersToStartApplication;
    return this;
  }

   /**
   * Get allowUsersToStartApplication
   * @return allowUsersToStartApplication
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowUsersToStartApplication() {
    return allowUsersToStartApplication;
  }


  public void setAllowUsersToStartApplication(Boolean allowUsersToStartApplication) {
    this.allowUsersToStartApplication = allowUsersToStartApplication;
  }


  public AppStartupSettings startApplicationWhenServerStarts(Boolean startApplicationWhenServerStarts) {
    
    this.startApplicationWhenServerStarts = startApplicationWhenServerStarts;
    return this;
  }

   /**
   * Get startApplicationWhenServerStarts
   * @return startApplicationWhenServerStarts
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStartApplicationWhenServerStarts() {
    return startApplicationWhenServerStarts;
  }


  public void setStartApplicationWhenServerStarts(Boolean startApplicationWhenServerStarts) {
    this.startApplicationWhenServerStarts = startApplicationWhenServerStarts;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppStartupSettings appStartupSettings = (AppStartupSettings) o;
    return Objects.equals(this.allowUsersToStartApplication, appStartupSettings.allowUsersToStartApplication) &&
        Objects.equals(this.startApplicationWhenServerStarts, appStartupSettings.startApplicationWhenServerStarts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowUsersToStartApplication, startApplicationWhenServerStarts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppStartupSettings {\n");
    sb.append("    allowUsersToStartApplication: ").append(toIndentedString(allowUsersToStartApplication)).append("\n");
    sb.append("    startApplicationWhenServerStarts: ").append(toIndentedString(startApplicationWhenServerStarts)).append("\n");
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

