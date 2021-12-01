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
import com.appliedolap.essbase.client.model.AttributeBuildProperties;
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
 * FieldDimBuildOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class FieldDimBuildOptions {
  public static final String SERIALIZED_NAME_PARENT = "parent";
  @SerializedName(SERIALIZED_NAME_PARENT)
  private Integer parent;

  /**
   * Gets or Sets generationType
   */
  @JsonAdapter(GenerationTypeEnum.Adapter.class)
  public enum GenerationTypeEnum {
    NONE("NONE"),
    
    PARENT("PARENT"),
    
    CHILD("CHILD"),
    
    GENERATION("GENERATION"),
    
    LEVEL("LEVEL"),
    
    ATTRIBUTE_MEMBER("ATTRIBUTE_MEMBER"),
    
    ALIAS("ALIAS"),
    
    PROPERTY("PROPERTY"),
    
    FORMULA("FORMULA"),
    
    MEMBER_ID("MEMBER_ID"),
    
    SOLVE_ORDER("SOLVE_ORDER"),
    
    UDA("UDA"),
    
    DUPGEN("DUPGEN"),
    
    DUPLEVEL("DUPLEVEL"),
    
    DUPGEN_ALIAS("DUPGEN_ALIAS"),
    
    DUPLEVEL_ALIAS("DUPLEVEL_ALIAS"),
    
    CURRENCY_NAME("CURRENCY_NAME"),
    
    CURRENCY_CATEGORY("CURRENCY_CATEGORY"),
    
    NUMTYPES("NUMTYPES"),
    
    ATTRIBUTE_DIM_LABEL("ATTRIBUTE_DIM_LABEL"),
    
    ATTRIBUTE_PARENT("ATTRIBUTE_PARENT"),
    
    DUPGEN_CAPTION("DUPGEN_CAPTION"),
    
    DUPLEVEL_CAPTION("DUPLEVEL_CAPTION"),
    
    AGGLEVELUSAGE("AGGLEVELUSAGE"),
    
    ATTR_ASSOCIATE("ATTR_ASSOCIATE"),
    
    REFMEMBER("REFMEMBER"),
    
    GEN_RIGHTJOIN("GEN_RIGHTJOIN"),
    
    COMMENT("COMMENT"),
    
    SHARED_GEN("SHARED_GEN"),
    
    GEN_NATUREJOIN("GEN_NATUREJOIN"),
    
    GEN_LEFTJOIN("GEN_LEFTJOIN"),
    
    GEN_OTLMBR("GEN_OTLMBR"),
    
    COMMENT_EX("COMMENT_EX"),
    
    REFMEMBERID("REFMEMBERID"),
    
    SMARTLISTID("SMARTLISTID"),
    
    SMARTLISTTEXT("SMARTLISTTEXT"),
    
    FORMATSTRING("FORMATSTRING");

    private String value;

    GenerationTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GenerationTypeEnum fromValue(String value) {
      for (GenerationTypeEnum b : GenerationTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<GenerationTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GenerationTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GenerationTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return GenerationTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_GENERATION_TYPE = "generationType";
  @SerializedName(SERIALIZED_NAME_GENERATION_TYPE)
  private GenerationTypeEnum generationType;

  public static final String SERIALIZED_NAME_REFER = "refer";
  @SerializedName(SERIALIZED_NAME_REFER)
  private Integer refer;

  public static final String SERIALIZED_NAME_REFER_INDEX = "referIndex";
  @SerializedName(SERIALIZED_NAME_REFER_INDEX)
  private Integer referIndex;

  public static final String SERIALIZED_NAME_SHARED = "shared";
  @SerializedName(SERIALIZED_NAME_SHARED)
  private Integer shared;

  public static final String SERIALIZED_NAME_ATTRIBUTE_BUILD_PROPERTIES = "attributeBuildProperties";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_BUILD_PROPERTIES)
  private AttributeBuildProperties attributeBuildProperties;

  public static final String SERIALIZED_NAME_DIMENSION = "dimension";
  @SerializedName(SERIALIZED_NAME_DIMENSION)
  private String dimension;

  public static final String SERIALIZED_NAME_ATTRIBUTE_DIMENSION = "attributeDimension";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_DIMENSION)
  private String attributeDimension;

  public static final String SERIALIZED_NAME_ALIAS = "alias";
  @SerializedName(SERIALIZED_NAME_ALIAS)
  private String alias;

  public static final String SERIALIZED_NAME_END_INDEP_COLUMNS = "endIndepColumns";
  @SerializedName(SERIALIZED_NAME_END_INDEP_COLUMNS)
  private List<Integer> endIndepColumns = null;

  public static final String SERIALIZED_NAME_START_INDEP_COLUMNS = "startIndepColumns";
  @SerializedName(SERIALIZED_NAME_START_INDEP_COLUMNS)
  private List<Integer> startIndepColumns = null;

  /**
   * Gets or Sets generationProperty
   */
  @JsonAdapter(GenerationPropertyEnum.Adapter.class)
  public enum GenerationPropertyEnum {
    NONE("NONE"),
    
    CONSOLIDATIONTYPE("CONSOLIDATIONTYPE"),
    
    STORAGETYPE("STORAGETYPE"),
    
    TIMEBALANCE("TIMEBALANCE"),
    
    SKIP("SKIP"),
    
    VARIANCEREPORT("VARIANCEREPORT"),
    
    TWOPASSCALC("TWOPASSCALC"),
    
    HIERARCHYTYPE("HIERARCHYTYPE");

    private String value;

    GenerationPropertyEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static GenerationPropertyEnum fromValue(String value) {
      for (GenerationPropertyEnum b : GenerationPropertyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<GenerationPropertyEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GenerationPropertyEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GenerationPropertyEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return GenerationPropertyEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_GENERATION_PROPERTY = "generationProperty";
  @SerializedName(SERIALIZED_NAME_GENERATION_PROPERTY)
  private GenerationPropertyEnum generationProperty;

  public static final String SERIALIZED_NAME_GENERATION = "generation";
  @SerializedName(SERIALIZED_NAME_GENERATION)
  private Integer generation;

  public static final String SERIALIZED_NAME_IGNORE = "ignore";
  @SerializedName(SERIALIZED_NAME_IGNORE)
  private Boolean ignore;

  public static final String SERIALIZED_NAME_STATIC_FIELD = "staticField";
  @SerializedName(SERIALIZED_NAME_STATIC_FIELD)
  private Boolean staticField;


  public FieldDimBuildOptions parent(Integer parent) {
    
    this.parent = parent;
    return this;
  }

   /**
   * Get parent
   * @return parent
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getParent() {
    return parent;
  }


  public void setParent(Integer parent) {
    this.parent = parent;
  }


  public FieldDimBuildOptions generationType(GenerationTypeEnum generationType) {
    
    this.generationType = generationType;
    return this;
  }

   /**
   * Get generationType
   * @return generationType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public GenerationTypeEnum getGenerationType() {
    return generationType;
  }


  public void setGenerationType(GenerationTypeEnum generationType) {
    this.generationType = generationType;
  }


  public FieldDimBuildOptions refer(Integer refer) {
    
    this.refer = refer;
    return this;
  }

   /**
   * Get refer
   * @return refer
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRefer() {
    return refer;
  }


  public void setRefer(Integer refer) {
    this.refer = refer;
  }


  public FieldDimBuildOptions referIndex(Integer referIndex) {
    
    this.referIndex = referIndex;
    return this;
  }

   /**
   * Get referIndex
   * @return referIndex
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getReferIndex() {
    return referIndex;
  }


  public void setReferIndex(Integer referIndex) {
    this.referIndex = referIndex;
  }


  public FieldDimBuildOptions shared(Integer shared) {
    
    this.shared = shared;
    return this;
  }

   /**
   * Get shared
   * @return shared
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getShared() {
    return shared;
  }


  public void setShared(Integer shared) {
    this.shared = shared;
  }


  public FieldDimBuildOptions attributeBuildProperties(AttributeBuildProperties attributeBuildProperties) {
    
    this.attributeBuildProperties = attributeBuildProperties;
    return this;
  }

   /**
   * Get attributeBuildProperties
   * @return attributeBuildProperties
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AttributeBuildProperties getAttributeBuildProperties() {
    return attributeBuildProperties;
  }


  public void setAttributeBuildProperties(AttributeBuildProperties attributeBuildProperties) {
    this.attributeBuildProperties = attributeBuildProperties;
  }


  public FieldDimBuildOptions dimension(String dimension) {
    
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


  public FieldDimBuildOptions attributeDimension(String attributeDimension) {
    
    this.attributeDimension = attributeDimension;
    return this;
  }

   /**
   * Get attributeDimension
   * @return attributeDimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAttributeDimension() {
    return attributeDimension;
  }


  public void setAttributeDimension(String attributeDimension) {
    this.attributeDimension = attributeDimension;
  }


  public FieldDimBuildOptions alias(String alias) {
    
    this.alias = alias;
    return this;
  }

   /**
   * Get alias
   * @return alias
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getAlias() {
    return alias;
  }


  public void setAlias(String alias) {
    this.alias = alias;
  }


  public FieldDimBuildOptions endIndepColumns(List<Integer> endIndepColumns) {
    
    this.endIndepColumns = endIndepColumns;
    return this;
  }

  public FieldDimBuildOptions addEndIndepColumnsItem(Integer endIndepColumnsItem) {
    if (this.endIndepColumns == null) {
      this.endIndepColumns = new ArrayList<Integer>();
    }
    this.endIndepColumns.add(endIndepColumnsItem);
    return this;
  }

   /**
   * Get endIndepColumns
   * @return endIndepColumns
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Integer> getEndIndepColumns() {
    return endIndepColumns;
  }


  public void setEndIndepColumns(List<Integer> endIndepColumns) {
    this.endIndepColumns = endIndepColumns;
  }


  public FieldDimBuildOptions startIndepColumns(List<Integer> startIndepColumns) {
    
    this.startIndepColumns = startIndepColumns;
    return this;
  }

  public FieldDimBuildOptions addStartIndepColumnsItem(Integer startIndepColumnsItem) {
    if (this.startIndepColumns == null) {
      this.startIndepColumns = new ArrayList<Integer>();
    }
    this.startIndepColumns.add(startIndepColumnsItem);
    return this;
  }

   /**
   * Get startIndepColumns
   * @return startIndepColumns
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Integer> getStartIndepColumns() {
    return startIndepColumns;
  }


  public void setStartIndepColumns(List<Integer> startIndepColumns) {
    this.startIndepColumns = startIndepColumns;
  }


  public FieldDimBuildOptions generationProperty(GenerationPropertyEnum generationProperty) {
    
    this.generationProperty = generationProperty;
    return this;
  }

   /**
   * Get generationProperty
   * @return generationProperty
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public GenerationPropertyEnum getGenerationProperty() {
    return generationProperty;
  }


  public void setGenerationProperty(GenerationPropertyEnum generationProperty) {
    this.generationProperty = generationProperty;
  }


  public FieldDimBuildOptions generation(Integer generation) {
    
    this.generation = generation;
    return this;
  }

   /**
   * Get generation
   * @return generation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGeneration() {
    return generation;
  }


  public void setGeneration(Integer generation) {
    this.generation = generation;
  }


  public FieldDimBuildOptions ignore(Boolean ignore) {
    
    this.ignore = ignore;
    return this;
  }

   /**
   * Get ignore
   * @return ignore
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIgnore() {
    return ignore;
  }


  public void setIgnore(Boolean ignore) {
    this.ignore = ignore;
  }


  public FieldDimBuildOptions staticField(Boolean staticField) {
    
    this.staticField = staticField;
    return this;
  }

   /**
   * Get staticField
   * @return staticField
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStaticField() {
    return staticField;
  }


  public void setStaticField(Boolean staticField) {
    this.staticField = staticField;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldDimBuildOptions fieldDimBuildOptions = (FieldDimBuildOptions) o;
    return Objects.equals(this.parent, fieldDimBuildOptions.parent) &&
        Objects.equals(this.generationType, fieldDimBuildOptions.generationType) &&
        Objects.equals(this.refer, fieldDimBuildOptions.refer) &&
        Objects.equals(this.referIndex, fieldDimBuildOptions.referIndex) &&
        Objects.equals(this.shared, fieldDimBuildOptions.shared) &&
        Objects.equals(this.attributeBuildProperties, fieldDimBuildOptions.attributeBuildProperties) &&
        Objects.equals(this.dimension, fieldDimBuildOptions.dimension) &&
        Objects.equals(this.attributeDimension, fieldDimBuildOptions.attributeDimension) &&
        Objects.equals(this.alias, fieldDimBuildOptions.alias) &&
        Objects.equals(this.endIndepColumns, fieldDimBuildOptions.endIndepColumns) &&
        Objects.equals(this.startIndepColumns, fieldDimBuildOptions.startIndepColumns) &&
        Objects.equals(this.generationProperty, fieldDimBuildOptions.generationProperty) &&
        Objects.equals(this.generation, fieldDimBuildOptions.generation) &&
        Objects.equals(this.ignore, fieldDimBuildOptions.ignore) &&
        Objects.equals(this.staticField, fieldDimBuildOptions.staticField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parent, generationType, refer, referIndex, shared, attributeBuildProperties, dimension, attributeDimension, alias, endIndepColumns, startIndepColumns, generationProperty, generation, ignore, staticField);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FieldDimBuildOptions {\n");
    sb.append("    parent: ").append(toIndentedString(parent)).append("\n");
    sb.append("    generationType: ").append(toIndentedString(generationType)).append("\n");
    sb.append("    refer: ").append(toIndentedString(refer)).append("\n");
    sb.append("    referIndex: ").append(toIndentedString(referIndex)).append("\n");
    sb.append("    shared: ").append(toIndentedString(shared)).append("\n");
    sb.append("    attributeBuildProperties: ").append(toIndentedString(attributeBuildProperties)).append("\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    attributeDimension: ").append(toIndentedString(attributeDimension)).append("\n");
    sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
    sb.append("    endIndepColumns: ").append(toIndentedString(endIndepColumns)).append("\n");
    sb.append("    startIndepColumns: ").append(toIndentedString(startIndepColumns)).append("\n");
    sb.append("    generationProperty: ").append(toIndentedString(generationProperty)).append("\n");
    sb.append("    generation: ").append(toIndentedString(generation)).append("\n");
    sb.append("    ignore: ").append(toIndentedString(ignore)).append("\n");
    sb.append("    staticField: ").append(toIndentedString(staticField)).append("\n");
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

