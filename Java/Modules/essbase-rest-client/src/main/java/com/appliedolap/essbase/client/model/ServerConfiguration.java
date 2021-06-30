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
 * ServerConfiguration
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class ServerConfiguration {
  public static final String SERIALIZED_NAME_HOST = "host";
  @SerializedName(SERIALIZED_NAME_HOST)
  private String host;

  public static final String SERIALIZED_NAME_PORT = "port";
  @SerializedName(SERIALIZED_NAME_PORT)
  private Integer port;

  public static final String SERIALIZED_NAME_USER = "user";
  @SerializedName(SERIALIZED_NAME_USER)
  private String user;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_FOLDER = "folder";
  @SerializedName(SERIALIZED_NAME_FOLDER)
  private String folder;

  public static final String SERIALIZED_NAME_MESSAGE_ACTION = "messageAction";
  @SerializedName(SERIALIZED_NAME_MESSAGE_ACTION)
  private String messageAction;

  public static final String SERIALIZED_NAME_MESSAGE_LOCATION = "messageLocation";
  @SerializedName(SERIALIZED_NAME_MESSAGE_LOCATION)
  private String messageLocation;


  public ServerConfiguration host(String host) {
    
    this.host = host;
    return this;
  }

   /**
   * Get host
   * @return host
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHost() {
    return host;
  }


  public void setHost(String host) {
    this.host = host;
  }


  public ServerConfiguration port(Integer port) {
    
    this.port = port;
    return this;
  }

   /**
   * Get port
   * @return port
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getPort() {
    return port;
  }


  public void setPort(Integer port) {
    this.port = port;
  }


  public ServerConfiguration user(String user) {
    
    this.user = user;
    return this;
  }

   /**
   * Get user
   * @return user
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUser() {
    return user;
  }


  public void setUser(String user) {
    this.user = user;
  }


  public ServerConfiguration password(String password) {
    
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


  public ServerConfiguration folder(String folder) {
    
    this.folder = folder;
    return this;
  }

   /**
   * Get folder
   * @return folder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFolder() {
    return folder;
  }


  public void setFolder(String folder) {
    this.folder = folder;
  }


  public ServerConfiguration messageAction(String messageAction) {
    
    this.messageAction = messageAction;
    return this;
  }

   /**
   * Get messageAction
   * @return messageAction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMessageAction() {
    return messageAction;
  }


  public void setMessageAction(String messageAction) {
    this.messageAction = messageAction;
  }


  public ServerConfiguration messageLocation(String messageLocation) {
    
    this.messageLocation = messageLocation;
    return this;
  }

   /**
   * Get messageLocation
   * @return messageLocation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMessageLocation() {
    return messageLocation;
  }


  public void setMessageLocation(String messageLocation) {
    this.messageLocation = messageLocation;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerConfiguration serverConfiguration = (ServerConfiguration) o;
    return Objects.equals(this.host, serverConfiguration.host) &&
        Objects.equals(this.port, serverConfiguration.port) &&
        Objects.equals(this.user, serverConfiguration.user) &&
        Objects.equals(this.password, serverConfiguration.password) &&
        Objects.equals(this.folder, serverConfiguration.folder) &&
        Objects.equals(this.messageAction, serverConfiguration.messageAction) &&
        Objects.equals(this.messageLocation, serverConfiguration.messageLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port, user, password, folder, messageAction, messageLocation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServerConfiguration {\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    folder: ").append(toIndentedString(folder)).append("\n");
    sb.append("    messageAction: ").append(toIndentedString(messageAction)).append("\n");
    sb.append("    messageLocation: ").append(toIndentedString(messageLocation)).append("\n");
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

