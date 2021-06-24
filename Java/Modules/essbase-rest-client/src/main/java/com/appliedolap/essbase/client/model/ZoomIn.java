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
 * ZoomIn
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-23T11:48:17.898-07:00[America/Los_Angeles]")
public class ZoomIn {
  /**
   * Gets or Sets ancestor
   */
  @JsonAdapter(AncestorEnum.Adapter.class)
  public enum AncestorEnum {
    TOP("top"),
    
    BOTTOM("bottom");

    private String value;

    AncestorEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AncestorEnum fromValue(String value) {
      for (AncestorEnum b : AncestorEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AncestorEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AncestorEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AncestorEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AncestorEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ANCESTOR = "ancestor";
  @SerializedName(SERIALIZED_NAME_ANCESTOR)
  private AncestorEnum ancestor;

  /**
   * Gets or Sets mode
   */
  @JsonAdapter(ModeEnum.Adapter.class)
  public enum ModeEnum {
    CHILDREN("children"),
    
    DESCENDENTS("descendents"),
    
    BASE("base");

    private String value;

    ModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ModeEnum fromValue(String value) {
      for (ModeEnum b : ModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MODE = "mode";
  @SerializedName(SERIALIZED_NAME_MODE)
  private ModeEnum mode;


  public ZoomIn ancestor(AncestorEnum ancestor) {
    
    this.ancestor = ancestor;
    return this;
  }

   /**
   * Get ancestor
   * @return ancestor
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AncestorEnum getAncestor() {
    return ancestor;
  }


  public void setAncestor(AncestorEnum ancestor) {
    this.ancestor = ancestor;
  }


  public ZoomIn mode(ModeEnum mode) {
    
    this.mode = mode;
    return this;
  }

   /**
   * Get mode
   * @return mode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ModeEnum getMode() {
    return mode;
  }


  public void setMode(ModeEnum mode) {
    this.mode = mode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ZoomIn zoomIn = (ZoomIn) o;
    return Objects.equals(this.ancestor, zoomIn.ancestor) &&
        Objects.equals(this.mode, zoomIn.mode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ancestor, mode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ZoomIn {\n");
    sb.append("    ancestor: ").append(toIndentedString(ancestor)).append("\n");
    sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
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

