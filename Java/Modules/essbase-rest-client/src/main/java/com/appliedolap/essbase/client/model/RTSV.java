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
 * RTSV
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class RTSV {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_ALLOW_MISSING = "allowMissing";
  @SerializedName(SERIALIZED_NAME_ALLOW_MISSING)
  private Boolean allowMissing;

  public static final String SERIALIZED_NAME_DIMENSION = "dimension";
  @SerializedName(SERIALIZED_NAME_DIMENSION)
  private String dimension;

  public static final String SERIALIZED_NAME_SINGLE_CHOICE = "singleChoice";
  @SerializedName(SERIALIZED_NAME_SINGLE_CHOICE)
  private Boolean singleChoice;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    STRING("STRING"),
    
    NUMBER("NUMBER"),
    
    DATE("DATE"),
    
    MEMBER("MEMBER");

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

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  private Object value;

  public static final String SERIALIZED_NAME_LIMIT = "limit";
  @SerializedName(SERIALIZED_NAME_LIMIT)
  private String limit;


  public RTSV name(String name) {
    
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


  public RTSV description(String description) {
    
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


  public RTSV allowMissing(Boolean allowMissing) {
    
    this.allowMissing = allowMissing;
    return this;
  }

   /**
   * Get allowMissing
   * @return allowMissing
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowMissing() {
    return allowMissing;
  }


  public void setAllowMissing(Boolean allowMissing) {
    this.allowMissing = allowMissing;
  }


  public RTSV dimension(String dimension) {
    
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


  public RTSV singleChoice(Boolean singleChoice) {
    
    this.singleChoice = singleChoice;
    return this;
  }

   /**
   * Get singleChoice
   * @return singleChoice
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getSingleChoice() {
    return singleChoice;
  }


  public void setSingleChoice(Boolean singleChoice) {
    this.singleChoice = singleChoice;
  }


  public RTSV type(TypeEnum type) {
    
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


  public RTSV value(Object value) {
    
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


  public RTSV limit(String limit) {
    
    this.limit = limit;
    return this;
  }

   /**
   * Get limit
   * @return limit
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLimit() {
    return limit;
  }


  public void setLimit(String limit) {
    this.limit = limit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RTSV RTSV = (RTSV) o;
    return Objects.equals(this.name, RTSV.name) &&
        Objects.equals(this.description, RTSV.description) &&
        Objects.equals(this.allowMissing, RTSV.allowMissing) &&
        Objects.equals(this.dimension, RTSV.dimension) &&
        Objects.equals(this.singleChoice, RTSV.singleChoice) &&
        Objects.equals(this.type, RTSV.type) &&
        Objects.equals(this.value, RTSV.value) &&
        Objects.equals(this.limit, RTSV.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, allowMissing, dimension, singleChoice, type, value, limit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RTSV {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    allowMissing: ").append(toIndentedString(allowMissing)).append("\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    singleChoice: ").append(toIndentedString(singleChoice)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
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

