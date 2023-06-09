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
 * JobStatisticsBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class JobStatisticsBean {
  public static final String SERIALIZED_NAME_ERR_CT = "errCt";
  @SerializedName(SERIALIZED_NAME_ERR_CT)
  private Long errCt;

  public static final String SERIALIZED_NAME_SUCCES_CT = "succesCt";
  @SerializedName(SERIALIZED_NAME_SUCCES_CT)
  private Long succesCt;

  public static final String SERIALIZED_NAME_WARNING_CT = "warningCt";
  @SerializedName(SERIALIZED_NAME_WARNING_CT)
  private Long warningCt;

  public static final String SERIALIZED_NAME_RUNNING_CT = "runningCt";
  @SerializedName(SERIALIZED_NAME_RUNNING_CT)
  private Long runningCt;


  public JobStatisticsBean errCt(Long errCt) {
    
    this.errCt = errCt;
    return this;
  }

   /**
   * Get errCt
   * @return errCt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getErrCt() {
    return errCt;
  }


  public void setErrCt(Long errCt) {
    this.errCt = errCt;
  }


  public JobStatisticsBean succesCt(Long succesCt) {
    
    this.succesCt = succesCt;
    return this;
  }

   /**
   * Get succesCt
   * @return succesCt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getSuccesCt() {
    return succesCt;
  }


  public void setSuccesCt(Long succesCt) {
    this.succesCt = succesCt;
  }


  public JobStatisticsBean warningCt(Long warningCt) {
    
    this.warningCt = warningCt;
    return this;
  }

   /**
   * Get warningCt
   * @return warningCt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getWarningCt() {
    return warningCt;
  }


  public void setWarningCt(Long warningCt) {
    this.warningCt = warningCt;
  }


  public JobStatisticsBean runningCt(Long runningCt) {
    
    this.runningCt = runningCt;
    return this;
  }

   /**
   * Get runningCt
   * @return runningCt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getRunningCt() {
    return runningCt;
  }


  public void setRunningCt(Long runningCt) {
    this.runningCt = runningCt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JobStatisticsBean jobStatisticsBean = (JobStatisticsBean) o;
    return Objects.equals(this.errCt, jobStatisticsBean.errCt) &&
        Objects.equals(this.succesCt, jobStatisticsBean.succesCt) &&
        Objects.equals(this.warningCt, jobStatisticsBean.warningCt) &&
        Objects.equals(this.runningCt, jobStatisticsBean.runningCt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errCt, succesCt, warningCt, runningCt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JobStatisticsBean {\n");
    sb.append("    errCt: ").append(toIndentedString(errCt)).append("\n");
    sb.append("    succesCt: ").append(toIndentedString(succesCt)).append("\n");
    sb.append("    warningCt: ").append(toIndentedString(warningCt)).append("\n");
    sb.append("    runningCt: ").append(toIndentedString(runningCt)).append("\n");
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

