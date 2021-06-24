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
import com.appliedolap.essbase.client.model.QName;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * JAXBElementObject
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-23T11:48:17.898-07:00[America/Los_Angeles]")
public class JAXBElementObject {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private QName name;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private Object value;

  public static final String SERIALIZED_NAME_NIL = "nil";
  @SerializedName(SERIALIZED_NAME_NIL)
  private Boolean nil;

  public static final String SERIALIZED_NAME_GLOBAL_SCOPE = "globalScope";
  @SerializedName(SERIALIZED_NAME_GLOBAL_SCOPE)
  private Boolean globalScope;

  public static final String SERIALIZED_NAME_TYPE_SUBSTITUTED = "typeSubstituted";
  @SerializedName(SERIALIZED_NAME_TYPE_SUBSTITUTED)
  private Boolean typeSubstituted;


  public JAXBElementObject name(QName name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public QName getName() {
    return name;
  }


  public void setName(QName name) {
    this.name = name;
  }


  public JAXBElementObject value(Object value) {
    
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Object getValue() {
    return value;
  }


  public void setValue(Object value) {
    this.value = value;
  }


  public JAXBElementObject nil(Boolean nil) {
    
    this.nil = nil;
    return this;
  }

   /**
   * Get nil
   * @return nil
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getNil() {
    return nil;
  }


  public void setNil(Boolean nil) {
    this.nil = nil;
  }


  public JAXBElementObject globalScope(Boolean globalScope) {
    
    this.globalScope = globalScope;
    return this;
  }

   /**
   * Get globalScope
   * @return globalScope
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getGlobalScope() {
    return globalScope;
  }


  public void setGlobalScope(Boolean globalScope) {
    this.globalScope = globalScope;
  }


  public JAXBElementObject typeSubstituted(Boolean typeSubstituted) {
    
    this.typeSubstituted = typeSubstituted;
    return this;
  }

   /**
   * Get typeSubstituted
   * @return typeSubstituted
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTypeSubstituted() {
    return typeSubstituted;
  }


  public void setTypeSubstituted(Boolean typeSubstituted) {
    this.typeSubstituted = typeSubstituted;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JAXBElementObject jaXBElementObject = (JAXBElementObject) o;
    return Objects.equals(this.name, jaXBElementObject.name) &&
        Objects.equals(this.value, jaXBElementObject.value) &&
        Objects.equals(this.nil, jaXBElementObject.nil) &&
        Objects.equals(this.globalScope, jaXBElementObject.globalScope) &&
        Objects.equals(this.typeSubstituted, jaXBElementObject.typeSubstituted);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, nil, globalScope, typeSubstituted);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JAXBElementObject {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    nil: ").append(toIndentedString(nil)).append("\n");
    sb.append("    globalScope: ").append(toIndentedString(globalScope)).append("\n");
    sb.append("    typeSubstituted: ").append(toIndentedString(typeSubstituted)).append("\n");
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

