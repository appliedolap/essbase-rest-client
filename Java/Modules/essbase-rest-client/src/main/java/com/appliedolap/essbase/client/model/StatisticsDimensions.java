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
 * StatisticsDimensions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-23T11:48:17.898-07:00[America/Los_Angeles]")
public class StatisticsDimensions {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_LEVELS = "levels";
  @SerializedName(SERIALIZED_NAME_LEVELS)
  private String levels;

  public static final String SERIALIZED_NAME_BITS = "bits";
  @SerializedName(SERIALIZED_NAME_BITS)
  private Double bits;


  public StatisticsDimensions name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public StatisticsDimensions levels(String levels) {
    
    this.levels = levels;
    return this;
  }

   /**
   * Get levels
   * @return levels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLevels() {
    return levels;
  }


  public void setLevels(String levels) {
    this.levels = levels;
  }


  public StatisticsDimensions bits(Double bits) {
    
    this.bits = bits;
    return this;
  }

   /**
   * Get bits
   * @return bits
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getBits() {
    return bits;
  }


  public void setBits(Double bits) {
    this.bits = bits;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StatisticsDimensions statisticsDimensions = (StatisticsDimensions) o;
    return Objects.equals(this.name, statisticsDimensions.name) &&
        Objects.equals(this.levels, statisticsDimensions.levels) &&
        Objects.equals(this.bits, statisticsDimensions.bits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, levels, bits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StatisticsDimensions {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    levels: ").append(toIndentedString(levels)).append("\n");
    sb.append("    bits: ").append(toIndentedString(bits)).append("\n");
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

