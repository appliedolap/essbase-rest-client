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
import com.appliedolap.essbase.client.model.CopyOptions;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * ScenarioActionPayload
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class ScenarioActionPayload {
  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_TO = "to";
  @SerializedName(SERIALIZED_NAME_TO)
  private String to;

  public static final String SERIALIZED_NAME_COPY_OPTIONS = "copyOptions";
  @SerializedName(SERIALIZED_NAME_COPY_OPTIONS)
  private CopyOptions copyOptions;


  public ScenarioActionPayload comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public ScenarioActionPayload to(String to) {
    
    this.to = to;
    return this;
  }

   /**
   * Get to
   * @return to
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getTo() {
    return to;
  }


  public void setTo(String to) {
    this.to = to;
  }


  public ScenarioActionPayload copyOptions(CopyOptions copyOptions) {
    
    this.copyOptions = copyOptions;
    return this;
  }

   /**
   * Get copyOptions
   * @return copyOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CopyOptions getCopyOptions() {
    return copyOptions;
  }


  public void setCopyOptions(CopyOptions copyOptions) {
    this.copyOptions = copyOptions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScenarioActionPayload scenarioActionPayload = (ScenarioActionPayload) o;
    return Objects.equals(this.comment, scenarioActionPayload.comment) &&
        Objects.equals(this.to, scenarioActionPayload.to) &&
        Objects.equals(this.copyOptions, scenarioActionPayload.copyOptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comment, to, copyOptions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScenarioActionPayload {\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    to: ").append(toIndentedString(to)).append("\n");
    sb.append("    copyOptions: ").append(toIndentedString(copyOptions)).append("\n");
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

