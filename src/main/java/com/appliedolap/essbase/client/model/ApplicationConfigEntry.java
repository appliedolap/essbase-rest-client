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
import com.appliedolap.essbase.client.model.Link;
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
 * ApplicationConfigEntry
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class ApplicationConfigEntry {
  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_SYNTAX = "syntax";
  @SerializedName(SERIALIZED_NAME_SYNTAX)
  private String syntax;

  public static final String SERIALIZED_NAME_EXAMPLE = "example";
  @SerializedName(SERIALIZED_NAME_EXAMPLE)
  private String example;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private String value;

  public static final String SERIALIZED_NAME_CONFIGURED = "configured";
  @SerializedName(SERIALIZED_NAME_CONFIGURED)
  private Boolean configured;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public ApplicationConfigEntry key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public ApplicationConfigEntry description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ApplicationConfigEntry syntax(String syntax) {
    
    this.syntax = syntax;
    return this;
  }

   /**
   * Get syntax
   * @return syntax
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSyntax() {
    return syntax;
  }


  public void setSyntax(String syntax) {
    this.syntax = syntax;
  }


  public ApplicationConfigEntry example(String example) {
    
    this.example = example;
    return this;
  }

   /**
   * Get example
   * @return example
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getExample() {
    return example;
  }


  public void setExample(String example) {
    this.example = example;
  }


  public ApplicationConfigEntry value(String value) {
    
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getValue() {
    return value;
  }


  public void setValue(String value) {
    this.value = value;
  }


  public ApplicationConfigEntry configured(Boolean configured) {
    
    this.configured = configured;
    return this;
  }

   /**
   * Get configured
   * @return configured
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getConfigured() {
    return configured;
  }


  public void setConfigured(Boolean configured) {
    this.configured = configured;
  }


  public ApplicationConfigEntry links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public ApplicationConfigEntry addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Link> getLinks() {
    return links;
  }


  public void setLinks(List<Link> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationConfigEntry applicationConfigEntry = (ApplicationConfigEntry) o;
    return Objects.equals(this.key, applicationConfigEntry.key) &&
        Objects.equals(this.description, applicationConfigEntry.description) &&
        Objects.equals(this.syntax, applicationConfigEntry.syntax) &&
        Objects.equals(this.example, applicationConfigEntry.example) &&
        Objects.equals(this.value, applicationConfigEntry.value) &&
        Objects.equals(this.configured, applicationConfigEntry.configured) &&
        Objects.equals(this.links, applicationConfigEntry.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, description, syntax, example, value, configured, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationConfigEntry {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    syntax: ").append(toIndentedString(syntax)).append("\n");
    sb.append("    example: ").append(toIndentedString(example)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    configured: ").append(toIndentedString(configured)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

