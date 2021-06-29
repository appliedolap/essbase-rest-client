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
 * DefaultLayoutBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class DefaultLayoutBean {
  public static final String SERIALIZED_NAME_USER_DEFAULT = "userDefault";
  @SerializedName(SERIALIZED_NAME_USER_DEFAULT)
  private Boolean userDefault;

  public static final String SERIALIZED_NAME_DATABASE_DEFAULT = "databaseDefault";
  @SerializedName(SERIALIZED_NAME_DATABASE_DEFAULT)
  private Boolean databaseDefault;


  public DefaultLayoutBean userDefault(Boolean userDefault) {
    
    this.userDefault = userDefault;
    return this;
  }

   /**
   * Get userDefault
   * @return userDefault
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getUserDefault() {
    return userDefault;
  }


  public void setUserDefault(Boolean userDefault) {
    this.userDefault = userDefault;
  }


  public DefaultLayoutBean databaseDefault(Boolean databaseDefault) {
    
    this.databaseDefault = databaseDefault;
    return this;
  }

   /**
   * Get databaseDefault
   * @return databaseDefault
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getDatabaseDefault() {
    return databaseDefault;
  }


  public void setDatabaseDefault(Boolean databaseDefault) {
    this.databaseDefault = databaseDefault;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultLayoutBean defaultLayoutBean = (DefaultLayoutBean) o;
    return Objects.equals(this.userDefault, defaultLayoutBean.userDefault) &&
        Objects.equals(this.databaseDefault, defaultLayoutBean.databaseDefault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userDefault, databaseDefault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DefaultLayoutBean {\n");
    sb.append("    userDefault: ").append(toIndentedString(userDefault)).append("\n");
    sb.append("    databaseDefault: ").append(toIndentedString(databaseDefault)).append("\n");
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

