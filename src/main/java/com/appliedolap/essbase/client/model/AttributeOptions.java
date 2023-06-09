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
import com.appliedolap.essbase.client.model.IndepDimension;
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
 * AttributeOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class AttributeOptions {
  public static final String SERIALIZED_NAME_INDEP_DIMENSIONS = "indepDimensions";
  @SerializedName(SERIALIZED_NAME_INDEP_DIMENSIONS)
  private List<IndepDimension> indepDimensions = null;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    NUMERIC("NUMERIC"),
    
    BOOLEAN("BOOLEAN"),
    
    TEXT("TEXT"),
    
    DATE("DATE"),
    
    EXISTING("EXISTING");

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

  public static final String SERIALIZED_NAME_BASE_DIMENSION = "baseDimension";
  @SerializedName(SERIALIZED_NAME_BASE_DIMENSION)
  private String baseDimension;

  public static final String SERIALIZED_NAME_MODIFIED = "modified";
  @SerializedName(SERIALIZED_NAME_MODIFIED)
  private Boolean modified;

  /**
   * Gets or Sets scaassociationMode
   */
  @JsonAdapter(ScaassociationModeEnum.Adapter.class)
  public enum ScaassociationModeEnum {
    NOOVERWRITE("NOOVERWRITE"),
    
    OVERWRITE("OVERWRITE");

    private String value;

    ScaassociationModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScaassociationModeEnum fromValue(String value) {
      for (ScaassociationModeEnum b : ScaassociationModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ScaassociationModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScaassociationModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScaassociationModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ScaassociationModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SCAASSOCIATION_MODE = "scaassociationMode";
  @SerializedName(SERIALIZED_NAME_SCAASSOCIATION_MODE)
  private ScaassociationModeEnum scaassociationMode;

  /**
   * Gets or Sets scadisAssociationMode
   */
  @JsonAdapter(ScadisAssociationModeEnum.Adapter.class)
  public enum ScadisAssociationModeEnum {
    NOOVERWRITE("NOOVERWRITE"),
    
    OVERWRITE("OVERWRITE"),
    
    EXTEND("EXTEND");

    private String value;

    ScadisAssociationModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ScadisAssociationModeEnum fromValue(String value) {
      for (ScadisAssociationModeEnum b : ScadisAssociationModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ScadisAssociationModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ScadisAssociationModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ScadisAssociationModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ScadisAssociationModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SCADIS_ASSOCIATION_MODE = "scadisAssociationMode";
  @SerializedName(SERIALIZED_NAME_SCADIS_ASSOCIATION_MODE)
  private ScadisAssociationModeEnum scadisAssociationMode;


  public AttributeOptions indepDimensions(List<IndepDimension> indepDimensions) {
    
    this.indepDimensions = indepDimensions;
    return this;
  }

  public AttributeOptions addIndepDimensionsItem(IndepDimension indepDimensionsItem) {
    if (this.indepDimensions == null) {
      this.indepDimensions = new ArrayList<IndepDimension>();
    }
    this.indepDimensions.add(indepDimensionsItem);
    return this;
  }

   /**
   * Get indepDimensions
   * @return indepDimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<IndepDimension> getIndepDimensions() {
    return indepDimensions;
  }


  public void setIndepDimensions(List<IndepDimension> indepDimensions) {
    this.indepDimensions = indepDimensions;
  }


  public AttributeOptions type(TypeEnum type) {
    
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


  public AttributeOptions baseDimension(String baseDimension) {
    
    this.baseDimension = baseDimension;
    return this;
  }

   /**
   * Get baseDimension
   * @return baseDimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getBaseDimension() {
    return baseDimension;
  }


  public void setBaseDimension(String baseDimension) {
    this.baseDimension = baseDimension;
  }


  public AttributeOptions modified(Boolean modified) {
    
    this.modified = modified;
    return this;
  }

   /**
   * Get modified
   * @return modified
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getModified() {
    return modified;
  }


  public void setModified(Boolean modified) {
    this.modified = modified;
  }


  public AttributeOptions scaassociationMode(ScaassociationModeEnum scaassociationMode) {
    
    this.scaassociationMode = scaassociationMode;
    return this;
  }

   /**
   * Get scaassociationMode
   * @return scaassociationMode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ScaassociationModeEnum getScaassociationMode() {
    return scaassociationMode;
  }


  public void setScaassociationMode(ScaassociationModeEnum scaassociationMode) {
    this.scaassociationMode = scaassociationMode;
  }


  public AttributeOptions scadisAssociationMode(ScadisAssociationModeEnum scadisAssociationMode) {
    
    this.scadisAssociationMode = scadisAssociationMode;
    return this;
  }

   /**
   * Get scadisAssociationMode
   * @return scadisAssociationMode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ScadisAssociationModeEnum getScadisAssociationMode() {
    return scadisAssociationMode;
  }


  public void setScadisAssociationMode(ScadisAssociationModeEnum scadisAssociationMode) {
    this.scadisAssociationMode = scadisAssociationMode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttributeOptions attributeOptions = (AttributeOptions) o;
    return Objects.equals(this.indepDimensions, attributeOptions.indepDimensions) &&
        Objects.equals(this.type, attributeOptions.type) &&
        Objects.equals(this.baseDimension, attributeOptions.baseDimension) &&
        Objects.equals(this.modified, attributeOptions.modified) &&
        Objects.equals(this.scaassociationMode, attributeOptions.scaassociationMode) &&
        Objects.equals(this.scadisAssociationMode, attributeOptions.scadisAssociationMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indepDimensions, type, baseDimension, modified, scaassociationMode, scadisAssociationMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttributeOptions {\n");
    sb.append("    indepDimensions: ").append(toIndentedString(indepDimensions)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    baseDimension: ").append(toIndentedString(baseDimension)).append("\n");
    sb.append("    modified: ").append(toIndentedString(modified)).append("\n");
    sb.append("    scaassociationMode: ").append(toIndentedString(scaassociationMode)).append("\n");
    sb.append("    scadisAssociationMode: ").append(toIndentedString(scadisAssociationMode)).append("\n");
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

