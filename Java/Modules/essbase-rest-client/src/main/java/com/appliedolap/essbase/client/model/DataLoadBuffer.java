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
 * DataLoadBuffer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class DataLoadBuffer {
  public static final String SERIALIZED_NAME_BUFFER_ID = "bufferId";
  @SerializedName(SERIALIZED_NAME_BUFFER_ID)
  private Long bufferId;

  /**
   * Gets or Sets duplicateAggregationMethod
   */
  @JsonAdapter(DuplicateAggregationMethodEnum.Adapter.class)
  public enum DuplicateAggregationMethodEnum {
    ADD("ADD"),
    
    ASSUME_EQUAL("ASSUME_EQUAL"),
    
    USE_LAST("USE_LAST");

    private String value;

    DuplicateAggregationMethodEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DuplicateAggregationMethodEnum fromValue(String value) {
      for (DuplicateAggregationMethodEnum b : DuplicateAggregationMethodEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DuplicateAggregationMethodEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DuplicateAggregationMethodEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DuplicateAggregationMethodEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DuplicateAggregationMethodEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DUPLICATE_AGGREGATION_METHOD = "duplicateAggregationMethod";
  @SerializedName(SERIALIZED_NAME_DUPLICATE_AGGREGATION_METHOD)
  private DuplicateAggregationMethodEnum duplicateAggregationMethod;

  /**
   * Gets or Sets loadBufferOptions
   */
  @JsonAdapter(LoadBufferOptionsEnum.Adapter.class)
  public enum LoadBufferOptionsEnum {
    NONE("IGNORE_NONE"),
    
    MISSING_VALUES("IGNORE_MISSING_VALUES"),
    
    ZERO_VALUES("IGNORE_ZERO_VALUES"),
    
    MISSING_AND_ZERO_VALUES("IGNORE_MISSING_AND_ZERO_VALUES");

    private String value;

    LoadBufferOptionsEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LoadBufferOptionsEnum fromValue(String value) {
      for (LoadBufferOptionsEnum b : LoadBufferOptionsEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<LoadBufferOptionsEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LoadBufferOptionsEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LoadBufferOptionsEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return LoadBufferOptionsEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_LOAD_BUFFER_OPTIONS = "loadBufferOptions";
  @SerializedName(SERIALIZED_NAME_LOAD_BUFFER_OPTIONS)
  private LoadBufferOptionsEnum loadBufferOptions;

  public static final String SERIALIZED_NAME_RESOURCE_USAGE = "resourceUsage";
  @SerializedName(SERIALIZED_NAME_RESOURCE_USAGE)
  private Long resourceUsage;


  public DataLoadBuffer bufferId(Long bufferId) {
    
    this.bufferId = bufferId;
    return this;
  }

   /**
   * Get bufferId
   * @return bufferId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getBufferId() {
    return bufferId;
  }


  public void setBufferId(Long bufferId) {
    this.bufferId = bufferId;
  }


  public DataLoadBuffer duplicateAggregationMethod(DuplicateAggregationMethodEnum duplicateAggregationMethod) {
    
    this.duplicateAggregationMethod = duplicateAggregationMethod;
    return this;
  }

   /**
   * Get duplicateAggregationMethod
   * @return duplicateAggregationMethod
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DuplicateAggregationMethodEnum getDuplicateAggregationMethod() {
    return duplicateAggregationMethod;
  }


  public void setDuplicateAggregationMethod(DuplicateAggregationMethodEnum duplicateAggregationMethod) {
    this.duplicateAggregationMethod = duplicateAggregationMethod;
  }


  public DataLoadBuffer loadBufferOptions(LoadBufferOptionsEnum loadBufferOptions) {
    
    this.loadBufferOptions = loadBufferOptions;
    return this;
  }

   /**
   * Get loadBufferOptions
   * @return loadBufferOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public LoadBufferOptionsEnum getLoadBufferOptions() {
    return loadBufferOptions;
  }


  public void setLoadBufferOptions(LoadBufferOptionsEnum loadBufferOptions) {
    this.loadBufferOptions = loadBufferOptions;
  }


  public DataLoadBuffer resourceUsage(Long resourceUsage) {
    
    this.resourceUsage = resourceUsage;
    return this;
  }

   /**
   * Percentage of the total load buffer resources that the load buffer will be allowed to use; must be within [0, 100], and the value of 0 is interpreted as default, which is currently 100.
   * @return resourceUsage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Percentage of the total load buffer resources that the load buffer will be allowed to use; must be within [0, 100], and the value of 0 is interpreted as default, which is currently 100.")

  public Long getResourceUsage() {
    return resourceUsage;
  }


  public void setResourceUsage(Long resourceUsage) {
    this.resourceUsage = resourceUsage;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataLoadBuffer dataLoadBuffer = (DataLoadBuffer) o;
    return Objects.equals(this.bufferId, dataLoadBuffer.bufferId) &&
        Objects.equals(this.duplicateAggregationMethod, dataLoadBuffer.duplicateAggregationMethod) &&
        Objects.equals(this.loadBufferOptions, dataLoadBuffer.loadBufferOptions) &&
        Objects.equals(this.resourceUsage, dataLoadBuffer.resourceUsage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bufferId, duplicateAggregationMethod, loadBufferOptions, resourceUsage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataLoadBuffer {\n");
    sb.append("    bufferId: ").append(toIndentedString(bufferId)).append("\n");
    sb.append("    duplicateAggregationMethod: ").append(toIndentedString(duplicateAggregationMethod)).append("\n");
    sb.append("    loadBufferOptions: ").append(toIndentedString(loadBufferOptions)).append("\n");
    sb.append("    resourceUsage: ").append(toIndentedString(resourceUsage)).append("\n");
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

