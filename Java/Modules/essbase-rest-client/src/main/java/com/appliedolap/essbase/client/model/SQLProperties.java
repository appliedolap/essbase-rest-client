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
 * SQLProperties
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class SQLProperties {
  public static final String SERIALIZED_NAME_SERVER = "server";
  @SerializedName(SERIALIZED_NAME_SERVER)
  private String server;

  public static final String SERIALIZED_NAME_APPLICATION = "application";
  @SerializedName(SERIALIZED_NAME_APPLICATION)
  private String application;

  public static final String SERIALIZED_NAME_DATABASE = "database";
  @SerializedName(SERIALIZED_NAME_DATABASE)
  private String database;

  public static final String SERIALIZED_NAME_DICTIONARY = "dictionary";
  @SerializedName(SERIALIZED_NAME_DICTIONARY)
  private String dictionary;

  public static final String SERIALIZED_NAME_SELECT = "select";
  @SerializedName(SERIALIZED_NAME_SELECT)
  private String select;

  public static final String SERIALIZED_NAME_FROM = "from";
  @SerializedName(SERIALIZED_NAME_FROM)
  private String from;

  public static final String SERIALIZED_NAME_WHERE = "where";
  @SerializedName(SERIALIZED_NAME_WHERE)
  private String where;


  public SQLProperties server(String server) {
    
    this.server = server;
    return this;
  }

   /**
   * Get server
   * @return server
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getServer() {
    return server;
  }


  public void setServer(String server) {
    this.server = server;
  }


  public SQLProperties application(String application) {
    
    this.application = application;
    return this;
  }

   /**
   * Get application
   * @return application
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplication() {
    return application;
  }


  public void setApplication(String application) {
    this.application = application;
  }


  public SQLProperties database(String database) {
    
    this.database = database;
    return this;
  }

   /**
   * Get database
   * @return database
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabase() {
    return database;
  }


  public void setDatabase(String database) {
    this.database = database;
  }


  public SQLProperties dictionary(String dictionary) {
    
    this.dictionary = dictionary;
    return this;
  }

   /**
   * Get dictionary
   * @return dictionary
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDictionary() {
    return dictionary;
  }


  public void setDictionary(String dictionary) {
    this.dictionary = dictionary;
  }


  public SQLProperties select(String select) {
    
    this.select = select;
    return this;
  }

   /**
   * Get select
   * @return select
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSelect() {
    return select;
  }


  public void setSelect(String select) {
    this.select = select;
  }


  public SQLProperties from(String from) {
    
    this.from = from;
    return this;
  }

   /**
   * Get from
   * @return from
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFrom() {
    return from;
  }


  public void setFrom(String from) {
    this.from = from;
  }


  public SQLProperties where(String where) {
    
    this.where = where;
    return this;
  }

   /**
   * Get where
   * @return where
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getWhere() {
    return where;
  }


  public void setWhere(String where) {
    this.where = where;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SQLProperties sqLProperties = (SQLProperties) o;
    return Objects.equals(this.server, sqLProperties.server) &&
        Objects.equals(this.application, sqLProperties.application) &&
        Objects.equals(this.database, sqLProperties.database) &&
        Objects.equals(this.dictionary, sqLProperties.dictionary) &&
        Objects.equals(this.select, sqLProperties.select) &&
        Objects.equals(this.from, sqLProperties.from) &&
        Objects.equals(this.where, sqLProperties.where);
  }

  @Override
  public int hashCode() {
    return Objects.hash(server, application, database, dictionary, select, from, where);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SQLProperties {\n");
    sb.append("    server: ").append(toIndentedString(server)).append("\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
    sb.append("    dictionary: ").append(toIndentedString(dictionary)).append("\n");
    sb.append("    select: ").append(toIndentedString(select)).append("\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    where: ").append(toIndentedString(where)).append("\n");
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

