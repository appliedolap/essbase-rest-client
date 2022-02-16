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
 * QName
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class QName {
  public static final String SERIALIZED_NAME_NAMESPACE_U_R_I = "namespaceURI";
  @SerializedName(SERIALIZED_NAME_NAMESPACE_U_R_I)
  private String namespaceURI;

  public static final String SERIALIZED_NAME_LOCAL_PART = "localPart";
  @SerializedName(SERIALIZED_NAME_LOCAL_PART)
  private String localPart;

  public static final String SERIALIZED_NAME_PREFIX = "prefix";
  @SerializedName(SERIALIZED_NAME_PREFIX)
  private String prefix;


  public QName namespaceURI(String namespaceURI) {
    
    this.namespaceURI = namespaceURI;
    return this;
  }

   /**
   * Get namespaceURI
   * @return namespaceURI
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getNamespaceURI() {
    return namespaceURI;
  }


  public void setNamespaceURI(String namespaceURI) {
    this.namespaceURI = namespaceURI;
  }


  public QName localPart(String localPart) {
    
    this.localPart = localPart;
    return this;
  }

   /**
   * Get localPart
   * @return localPart
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLocalPart() {
    return localPart;
  }


  public void setLocalPart(String localPart) {
    this.localPart = localPart;
  }


  public QName prefix(String prefix) {
    
    this.prefix = prefix;
    return this;
  }

   /**
   * Get prefix
   * @return prefix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPrefix() {
    return prefix;
  }


  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QName qname = (QName) o;
    return Objects.equals(this.namespaceURI, qname.namespaceURI) &&
        Objects.equals(this.localPart, qname.localPart) &&
        Objects.equals(this.prefix, qname.prefix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespaceURI, localPart, prefix);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QName {\n");
    sb.append("    namespaceURI: ").append(toIndentedString(namespaceURI)).append("\n");
    sb.append("    localPart: ").append(toIndentedString(localPart)).append("\n");
    sb.append("    prefix: ").append(toIndentedString(prefix)).append("\n");
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

