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
 * RunTimeParametersInfo
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class RunTimeParametersInfo {
  public static final String SERIALIZED_NAME_DIMENSION = "dimension";
  @SerializedName(SERIALIZED_NAME_DIMENSION)
  private String dimension;

  public static final String SERIALIZED_NAME_GENERATION = "generation";
  @SerializedName(SERIALIZED_NAME_GENERATION)
  private String generation;

  public static final String SERIALIZED_NAME_LEVEL = "level";
  @SerializedName(SERIALIZED_NAME_LEVEL)
  private String level;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    DIMENSION("DIMENSION"),
    
    GENERATION("GENERATION"),
    
    LEVEL0("LEVEL0"),
    
    PARENT_CHILD("PARENT_CHILD");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeEnum type;

  public static final String SERIALIZED_NAME_GENERATION_NUMBER = "generationNumber";
  @SerializedName(SERIALIZED_NAME_GENERATION_NUMBER)
  private Integer generationNumber;


  public RunTimeParametersInfo dimension(String dimension) {
    
    this.dimension = dimension;
    return this;
  }

   /**
   * Get dimension
   * @return dimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimension() {
    return dimension;
  }


  public void setDimension(String dimension) {
    this.dimension = dimension;
  }


  public RunTimeParametersInfo generation(String generation) {
    
    this.generation = generation;
    return this;
  }

   /**
   * Get generation
   * @return generation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getGeneration() {
    return generation;
  }


  public void setGeneration(String generation) {
    this.generation = generation;
  }


  public RunTimeParametersInfo level(String level) {
    
    this.level = level;
    return this;
  }

   /**
   * Get level
   * @return level
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLevel() {
    return level;
  }


  public void setLevel(String level) {
    this.level = level;
  }


  public RunTimeParametersInfo type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public RunTimeParametersInfo generationNumber(Integer generationNumber) {
    
    this.generationNumber = generationNumber;
    return this;
  }

   /**
   * Get generationNumber
   * @return generationNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGenerationNumber() {
    return generationNumber;
  }


  public void setGenerationNumber(Integer generationNumber) {
    this.generationNumber = generationNumber;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunTimeParametersInfo runTimeParametersInfo = (RunTimeParametersInfo) o;
    return Objects.equals(this.dimension, runTimeParametersInfo.dimension) &&
        Objects.equals(this.generation, runTimeParametersInfo.generation) &&
        Objects.equals(this.level, runTimeParametersInfo.level) &&
        Objects.equals(this.type, runTimeParametersInfo.type) &&
        Objects.equals(this.generationNumber, runTimeParametersInfo.generationNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimension, generation, level, type, generationNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RunTimeParametersInfo {\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    generation: ").append(toIndentedString(generation)).append("\n");
    sb.append("    level: ").append(toIndentedString(level)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    generationNumber: ").append(toIndentedString(generationNumber)).append("\n");
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

