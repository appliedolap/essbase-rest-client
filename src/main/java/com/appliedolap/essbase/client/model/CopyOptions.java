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
 * CopyOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class CopyOptions {
  public static final String SERIALIZED_NAME_PROPERTIES = "properties";
  @SerializedName(SERIALIZED_NAME_PROPERTIES)
  private Boolean properties;

  public static final String SERIALIZED_NAME_COMMENTS = "comments";
  @SerializedName(SERIALIZED_NAME_COMMENTS)
  private Boolean comments;

  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private Boolean data;

  public static final String SERIALIZED_NAME_TASKS = "tasks";
  @SerializedName(SERIALIZED_NAME_TASKS)
  private Boolean tasks;

  public static final String SERIALIZED_NAME_KEY_METRICS = "keyMetrics";
  @SerializedName(SERIALIZED_NAME_KEY_METRICS)
  private Boolean keyMetrics;

  public static final String SERIALIZED_NAME_SLICE = "slice";
  @SerializedName(SERIALIZED_NAME_SLICE)
  private Boolean slice;

  public static final String SERIALIZED_NAME_LAYOUTS = "layouts";
  @SerializedName(SERIALIZED_NAME_LAYOUTS)
  private Boolean layouts;

  public static final String SERIALIZED_NAME_APPROVERS = "approvers";
  @SerializedName(SERIALIZED_NAME_APPROVERS)
  private Boolean approvers;

  public static final String SERIALIZED_NAME_PARTICIPANTS = "participants";
  @SerializedName(SERIALIZED_NAME_PARTICIPANTS)
  private Boolean participants;


  public CopyOptions properties(Boolean properties) {
    
    this.properties = properties;
    return this;
  }

   /**
   * Get properties
   * @return properties
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getProperties() {
    return properties;
  }


  public void setProperties(Boolean properties) {
    this.properties = properties;
  }


  public CopyOptions comments(Boolean comments) {
    
    this.comments = comments;
    return this;
  }

   /**
   * Get comments
   * @return comments
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getComments() {
    return comments;
  }


  public void setComments(Boolean comments) {
    this.comments = comments;
  }


  public CopyOptions data(Boolean data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getData() {
    return data;
  }


  public void setData(Boolean data) {
    this.data = data;
  }


  public CopyOptions tasks(Boolean tasks) {
    
    this.tasks = tasks;
    return this;
  }

   /**
   * Get tasks
   * @return tasks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTasks() {
    return tasks;
  }


  public void setTasks(Boolean tasks) {
    this.tasks = tasks;
  }


  public CopyOptions keyMetrics(Boolean keyMetrics) {
    
    this.keyMetrics = keyMetrics;
    return this;
  }

   /**
   * Get keyMetrics
   * @return keyMetrics
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getKeyMetrics() {
    return keyMetrics;
  }


  public void setKeyMetrics(Boolean keyMetrics) {
    this.keyMetrics = keyMetrics;
  }


  public CopyOptions slice(Boolean slice) {
    
    this.slice = slice;
    return this;
  }

   /**
   * Get slice
   * @return slice
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getSlice() {
    return slice;
  }


  public void setSlice(Boolean slice) {
    this.slice = slice;
  }


  public CopyOptions layouts(Boolean layouts) {
    
    this.layouts = layouts;
    return this;
  }

   /**
   * Get layouts
   * @return layouts
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getLayouts() {
    return layouts;
  }


  public void setLayouts(Boolean layouts) {
    this.layouts = layouts;
  }


  public CopyOptions approvers(Boolean approvers) {
    
    this.approvers = approvers;
    return this;
  }

   /**
   * Get approvers
   * @return approvers
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getApprovers() {
    return approvers;
  }


  public void setApprovers(Boolean approvers) {
    this.approvers = approvers;
  }


  public CopyOptions participants(Boolean participants) {
    
    this.participants = participants;
    return this;
  }

   /**
   * Get participants
   * @return participants
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getParticipants() {
    return participants;
  }


  public void setParticipants(Boolean participants) {
    this.participants = participants;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CopyOptions copyOptions = (CopyOptions) o;
    return Objects.equals(this.properties, copyOptions.properties) &&
        Objects.equals(this.comments, copyOptions.comments) &&
        Objects.equals(this.data, copyOptions.data) &&
        Objects.equals(this.tasks, copyOptions.tasks) &&
        Objects.equals(this.keyMetrics, copyOptions.keyMetrics) &&
        Objects.equals(this.slice, copyOptions.slice) &&
        Objects.equals(this.layouts, copyOptions.layouts) &&
        Objects.equals(this.approvers, copyOptions.approvers) &&
        Objects.equals(this.participants, copyOptions.participants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(properties, comments, data, tasks, keyMetrics, slice, layouts, approvers, participants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CopyOptions {\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    tasks: ").append(toIndentedString(tasks)).append("\n");
    sb.append("    keyMetrics: ").append(toIndentedString(keyMetrics)).append("\n");
    sb.append("    slice: ").append(toIndentedString(slice)).append("\n");
    sb.append("    layouts: ").append(toIndentedString(layouts)).append("\n");
    sb.append("    approvers: ").append(toIndentedString(approvers)).append("\n");
    sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
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
