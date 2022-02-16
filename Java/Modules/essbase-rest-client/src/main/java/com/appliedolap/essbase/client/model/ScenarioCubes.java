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
import java.util.ArrayList;
import java.util.List;

/**
 * ScenarioCubes
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class ScenarioCubes {
  public static final String SERIALIZED_NAME_APPLICATION = "application";
  @SerializedName(SERIALIZED_NAME_APPLICATION)
  private String application;

  public static final String SERIALIZED_NAME_DATABASES = "databases";
  @SerializedName(SERIALIZED_NAME_DATABASES)
  private List<String> databases = null;

  public static final String SERIALIZED_NAME_CREATE_SCENARIO = "createScenario";
  @SerializedName(SERIALIZED_NAME_CREATE_SCENARIO)
  private Boolean createScenario;

  public static final String SERIALIZED_NAME_PARTICIPATE_IN_SCENARIO = "participateInScenario";
  @SerializedName(SERIALIZED_NAME_PARTICIPATE_IN_SCENARIO)
  private Boolean participateInScenario;

  public static final String SERIALIZED_NAME_DATABASE_ADMIN = "databaseAdmin";
  @SerializedName(SERIALIZED_NAME_DATABASE_ADMIN)
  private Boolean databaseAdmin;


  public ScenarioCubes application(String application) {
    
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


  public ScenarioCubes databases(List<String> databases) {
    
    this.databases = databases;
    return this;
  }

  public ScenarioCubes addDatabasesItem(String databasesItem) {
    if (this.databases == null) {
      this.databases = new ArrayList<String>();
    }
    this.databases.add(databasesItem);
    return this;
  }

   /**
   * Get databases
   * @return databases
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getDatabases() {
    return databases;
  }


  public void setDatabases(List<String> databases) {
    this.databases = databases;
  }


  public ScenarioCubes createScenario(Boolean createScenario) {
    
    this.createScenario = createScenario;
    return this;
  }

   /**
   * Get createScenario
   * @return createScenario
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getCreateScenario() {
    return createScenario;
  }


  public void setCreateScenario(Boolean createScenario) {
    this.createScenario = createScenario;
  }


  public ScenarioCubes participateInScenario(Boolean participateInScenario) {
    
    this.participateInScenario = participateInScenario;
    return this;
  }

   /**
   * Get participateInScenario
   * @return participateInScenario
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getParticipateInScenario() {
    return participateInScenario;
  }


  public void setParticipateInScenario(Boolean participateInScenario) {
    this.participateInScenario = participateInScenario;
  }


  public ScenarioCubes databaseAdmin(Boolean databaseAdmin) {
    
    this.databaseAdmin = databaseAdmin;
    return this;
  }

   /**
   * Get databaseAdmin
   * @return databaseAdmin
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getDatabaseAdmin() {
    return databaseAdmin;
  }


  public void setDatabaseAdmin(Boolean databaseAdmin) {
    this.databaseAdmin = databaseAdmin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScenarioCubes scenarioCubes = (ScenarioCubes) o;
    return Objects.equals(this.application, scenarioCubes.application) &&
        Objects.equals(this.databases, scenarioCubes.databases) &&
        Objects.equals(this.createScenario, scenarioCubes.createScenario) &&
        Objects.equals(this.participateInScenario, scenarioCubes.participateInScenario) &&
        Objects.equals(this.databaseAdmin, scenarioCubes.databaseAdmin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(application, databases, createScenario, participateInScenario, databaseAdmin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScenarioCubes {\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    databases: ").append(toIndentedString(databases)).append("\n");
    sb.append("    createScenario: ").append(toIndentedString(createScenario)).append("\n");
    sb.append("    participateInScenario: ").append(toIndentedString(participateInScenario)).append("\n");
    sb.append("    databaseAdmin: ").append(toIndentedString(databaseAdmin)).append("\n");
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

