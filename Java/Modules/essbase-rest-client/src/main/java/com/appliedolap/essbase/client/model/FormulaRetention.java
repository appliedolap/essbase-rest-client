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
 * FormulaRetention
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class FormulaRetention {
  public static final String SERIALIZED_NAME_COMMENTS = "comments";
  @SerializedName(SERIALIZED_NAME_COMMENTS)
  private Boolean comments;

  public static final String SERIALIZED_NAME_ZOOM = "zoom";
  @SerializedName(SERIALIZED_NAME_ZOOM)
  private Boolean zoom;

  public static final String SERIALIZED_NAME_FOCUS = "focus";
  @SerializedName(SERIALIZED_NAME_FOCUS)
  private Boolean focus;

  public static final String SERIALIZED_NAME_RETRIVE = "retrive";
  @SerializedName(SERIALIZED_NAME_RETRIVE)
  private Boolean retrive;

  public static final String SERIALIZED_NAME_FILL = "fill";
  @SerializedName(SERIALIZED_NAME_FILL)
  private Boolean fill;


  public FormulaRetention comments(Boolean comments) {
    
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


  public FormulaRetention zoom(Boolean zoom) {
    
    this.zoom = zoom;
    return this;
  }

   /**
   * Get zoom
   * @return zoom
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getZoom() {
    return zoom;
  }


  public void setZoom(Boolean zoom) {
    this.zoom = zoom;
  }


  public FormulaRetention focus(Boolean focus) {
    
    this.focus = focus;
    return this;
  }

   /**
   * Get focus
   * @return focus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getFocus() {
    return focus;
  }


  public void setFocus(Boolean focus) {
    this.focus = focus;
  }


  public FormulaRetention retrive(Boolean retrive) {
    
    this.retrive = retrive;
    return this;
  }

   /**
   * Get retrive
   * @return retrive
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getRetrive() {
    return retrive;
  }


  public void setRetrive(Boolean retrive) {
    this.retrive = retrive;
  }


  public FormulaRetention fill(Boolean fill) {
    
    this.fill = fill;
    return this;
  }

   /**
   * Get fill
   * @return fill
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getFill() {
    return fill;
  }


  public void setFill(Boolean fill) {
    this.fill = fill;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FormulaRetention formulaRetention = (FormulaRetention) o;
    return Objects.equals(this.comments, formulaRetention.comments) &&
        Objects.equals(this.zoom, formulaRetention.zoom) &&
        Objects.equals(this.focus, formulaRetention.focus) &&
        Objects.equals(this.retrive, formulaRetention.retrive) &&
        Objects.equals(this.fill, formulaRetention.fill);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comments, zoom, focus, retrive, fill);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FormulaRetention {\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("    zoom: ").append(toIndentedString(zoom)).append("\n");
    sb.append("    focus: ").append(toIndentedString(focus)).append("\n");
    sb.append("    retrive: ").append(toIndentedString(retrive)).append("\n");
    sb.append("    fill: ").append(toIndentedString(fill)).append("\n");
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

