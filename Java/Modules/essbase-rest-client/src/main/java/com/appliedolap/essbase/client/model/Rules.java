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
import com.appliedolap.essbase.client.model.ColumnOperation;
import com.appliedolap.essbase.client.model.DataLoadOptions;
import com.appliedolap.essbase.client.model.Datasource;
import com.appliedolap.essbase.client.model.DimBuildOptions;
import com.appliedolap.essbase.client.model.Dimension;
import com.appliedolap.essbase.client.model.EditorOptions;
import com.appliedolap.essbase.client.model.EssbaseInfo;
import com.appliedolap.essbase.client.model.Field;
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
 * Rules
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class Rules {
  public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS)
  private List<Dimension> dimensions = null;

  public static final String SERIALIZED_NAME_FIELDS = "fields";
  @SerializedName(SERIALIZED_NAME_FIELDS)
  private List<Field> fields = null;

  public static final String SERIALIZED_NAME_TIME_STAMP = "timeStamp";
  @SerializedName(SERIALIZED_NAME_TIME_STAMP)
  private Long timeStamp;

  public static final String SERIALIZED_NAME_DATA_SOURCE = "dataSource";
  @SerializedName(SERIALIZED_NAME_DATA_SOURCE)
  private Datasource dataSource;

  public static final String SERIALIZED_NAME_DIMENSION_BUILD_OPTIONS = "dimensionBuildOptions";
  @SerializedName(SERIALIZED_NAME_DIMENSION_BUILD_OPTIONS)
  private DimBuildOptions dimensionBuildOptions;

  public static final String SERIALIZED_NAME_DATA_LOAD_OPTIONS = "dataLoadOptions";
  @SerializedName(SERIALIZED_NAME_DATA_LOAD_OPTIONS)
  private DataLoadOptions dataLoadOptions;

  public static final String SERIALIZED_NAME_EDITOR_OPTIONS = "editorOptions";
  @SerializedName(SERIALIZED_NAME_EDITOR_OPTIONS)
  private EditorOptions editorOptions;

  /**
   * Gets or Sets encoding
   */
  @JsonAdapter(EncodingEnum.Adapter.class)
  public enum EncodingEnum {
    NONE("NONE"),
    
    NONUNICODE("NONUNICODE"),
    
    UTF8("UTF8");

    private String value;

    EncodingEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static EncodingEnum fromValue(String value) {
      for (EncodingEnum b : EncodingEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<EncodingEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final EncodingEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public EncodingEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return EncodingEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ENCODING = "encoding";
  @SerializedName(SERIALIZED_NAME_ENCODING)
  private EncodingEnum encoding;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_LOCALE = "locale";
  @SerializedName(SERIALIZED_NAME_LOCALE)
  private String locale;

  public static final String SERIALIZED_NAME_STUDIO = "studio";
  @SerializedName(SERIALIZED_NAME_STUDIO)
  private Boolean studio;

  public static final String SERIALIZED_NAME_BIBPM = "bibpm";
  @SerializedName(SERIALIZED_NAME_BIBPM)
  private Boolean bibpm;

  public static final String SERIALIZED_NAME_XOLAP = "xolap";
  @SerializedName(SERIALIZED_NAME_XOLAP)
  private Boolean xolap;

  public static final String SERIALIZED_NAME_FLAT_FILE_BASED = "flatFileBased";
  @SerializedName(SERIALIZED_NAME_FLAT_FILE_BASED)
  private Boolean flatFileBased;

  public static final String SERIALIZED_NAME_ESSBASE_INFO = "essbaseInfo";
  @SerializedName(SERIALIZED_NAME_ESSBASE_INFO)
  private EssbaseInfo essbaseInfo;

  public static final String SERIALIZED_NAME_COLUMN_OPERATIONS = "columnOperations";
  @SerializedName(SERIALIZED_NAME_COLUMN_OPERATIONS)
  private List<ColumnOperation> columnOperations = null;


  public Rules dimensions(List<Dimension> dimensions) {
    
    this.dimensions = dimensions;
    return this;
  }

  public Rules addDimensionsItem(Dimension dimensionsItem) {
    if (this.dimensions == null) {
      this.dimensions = new ArrayList<Dimension>();
    }
    this.dimensions.add(dimensionsItem);
    return this;
  }

   /**
   * Get dimensions
   * @return dimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Dimension> getDimensions() {
    return dimensions;
  }


  public void setDimensions(List<Dimension> dimensions) {
    this.dimensions = dimensions;
  }


  public Rules fields(List<Field> fields) {
    
    this.fields = fields;
    return this;
  }

  public Rules addFieldsItem(Field fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<Field>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

   /**
   * Get fields
   * @return fields
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Field> getFields() {
    return fields;
  }


  public void setFields(List<Field> fields) {
    this.fields = fields;
  }


  public Rules timeStamp(Long timeStamp) {
    
    this.timeStamp = timeStamp;
    return this;
  }

   /**
   * Get timeStamp
   * @return timeStamp
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getTimeStamp() {
    return timeStamp;
  }


  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }


  public Rules dataSource(Datasource dataSource) {
    
    this.dataSource = dataSource;
    return this;
  }

   /**
   * Get dataSource
   * @return dataSource
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Datasource getDataSource() {
    return dataSource;
  }


  public void setDataSource(Datasource dataSource) {
    this.dataSource = dataSource;
  }


  public Rules dimensionBuildOptions(DimBuildOptions dimensionBuildOptions) {
    
    this.dimensionBuildOptions = dimensionBuildOptions;
    return this;
  }

   /**
   * Get dimensionBuildOptions
   * @return dimensionBuildOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DimBuildOptions getDimensionBuildOptions() {
    return dimensionBuildOptions;
  }


  public void setDimensionBuildOptions(DimBuildOptions dimensionBuildOptions) {
    this.dimensionBuildOptions = dimensionBuildOptions;
  }


  public Rules dataLoadOptions(DataLoadOptions dataLoadOptions) {
    
    this.dataLoadOptions = dataLoadOptions;
    return this;
  }

   /**
   * Get dataLoadOptions
   * @return dataLoadOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DataLoadOptions getDataLoadOptions() {
    return dataLoadOptions;
  }


  public void setDataLoadOptions(DataLoadOptions dataLoadOptions) {
    this.dataLoadOptions = dataLoadOptions;
  }


  public Rules editorOptions(EditorOptions editorOptions) {
    
    this.editorOptions = editorOptions;
    return this;
  }

   /**
   * Get editorOptions
   * @return editorOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public EditorOptions getEditorOptions() {
    return editorOptions;
  }


  public void setEditorOptions(EditorOptions editorOptions) {
    this.editorOptions = editorOptions;
  }


  public Rules encoding(EncodingEnum encoding) {
    
    this.encoding = encoding;
    return this;
  }

   /**
   * Get encoding
   * @return encoding
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public EncodingEnum getEncoding() {
    return encoding;
  }


  public void setEncoding(EncodingEnum encoding) {
    this.encoding = encoding;
  }


  public Rules name(String name) {
    
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


  public Rules locale(String locale) {
    
    this.locale = locale;
    return this;
  }

   /**
   * Get locale
   * @return locale
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getLocale() {
    return locale;
  }


  public void setLocale(String locale) {
    this.locale = locale;
  }


  public Rules studio(Boolean studio) {
    
    this.studio = studio;
    return this;
  }

   /**
   * Get studio
   * @return studio
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStudio() {
    return studio;
  }


  public void setStudio(Boolean studio) {
    this.studio = studio;
  }


  public Rules bibpm(Boolean bibpm) {
    
    this.bibpm = bibpm;
    return this;
  }

   /**
   * Get bibpm
   * @return bibpm
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getBibpm() {
    return bibpm;
  }


  public void setBibpm(Boolean bibpm) {
    this.bibpm = bibpm;
  }


  public Rules xolap(Boolean xolap) {
    
    this.xolap = xolap;
    return this;
  }

   /**
   * Get xolap
   * @return xolap
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getXolap() {
    return xolap;
  }


  public void setXolap(Boolean xolap) {
    this.xolap = xolap;
  }


  public Rules flatFileBased(Boolean flatFileBased) {
    
    this.flatFileBased = flatFileBased;
    return this;
  }

   /**
   * Get flatFileBased
   * @return flatFileBased
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getFlatFileBased() {
    return flatFileBased;
  }


  public void setFlatFileBased(Boolean flatFileBased) {
    this.flatFileBased = flatFileBased;
  }


  public Rules essbaseInfo(EssbaseInfo essbaseInfo) {
    
    this.essbaseInfo = essbaseInfo;
    return this;
  }

   /**
   * Get essbaseInfo
   * @return essbaseInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public EssbaseInfo getEssbaseInfo() {
    return essbaseInfo;
  }


  public void setEssbaseInfo(EssbaseInfo essbaseInfo) {
    this.essbaseInfo = essbaseInfo;
  }


  public Rules columnOperations(List<ColumnOperation> columnOperations) {
    
    this.columnOperations = columnOperations;
    return this;
  }

  public Rules addColumnOperationsItem(ColumnOperation columnOperationsItem) {
    if (this.columnOperations == null) {
      this.columnOperations = new ArrayList<ColumnOperation>();
    }
    this.columnOperations.add(columnOperationsItem);
    return this;
  }

   /**
   * Get columnOperations
   * @return columnOperations
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ColumnOperation> getColumnOperations() {
    return columnOperations;
  }


  public void setColumnOperations(List<ColumnOperation> columnOperations) {
    this.columnOperations = columnOperations;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rules rules = (Rules) o;
    return Objects.equals(this.dimensions, rules.dimensions) &&
        Objects.equals(this.fields, rules.fields) &&
        Objects.equals(this.timeStamp, rules.timeStamp) &&
        Objects.equals(this.dataSource, rules.dataSource) &&
        Objects.equals(this.dimensionBuildOptions, rules.dimensionBuildOptions) &&
        Objects.equals(this.dataLoadOptions, rules.dataLoadOptions) &&
        Objects.equals(this.editorOptions, rules.editorOptions) &&
        Objects.equals(this.encoding, rules.encoding) &&
        Objects.equals(this.name, rules.name) &&
        Objects.equals(this.locale, rules.locale) &&
        Objects.equals(this.studio, rules.studio) &&
        Objects.equals(this.bibpm, rules.bibpm) &&
        Objects.equals(this.xolap, rules.xolap) &&
        Objects.equals(this.flatFileBased, rules.flatFileBased) &&
        Objects.equals(this.essbaseInfo, rules.essbaseInfo) &&
        Objects.equals(this.columnOperations, rules.columnOperations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimensions, fields, timeStamp, dataSource, dimensionBuildOptions, dataLoadOptions, editorOptions, encoding, name, locale, studio, bibpm, xolap, flatFileBased, essbaseInfo, columnOperations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rules {\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
    sb.append("    timeStamp: ").append(toIndentedString(timeStamp)).append("\n");
    sb.append("    dataSource: ").append(toIndentedString(dataSource)).append("\n");
    sb.append("    dimensionBuildOptions: ").append(toIndentedString(dimensionBuildOptions)).append("\n");
    sb.append("    dataLoadOptions: ").append(toIndentedString(dataLoadOptions)).append("\n");
    sb.append("    editorOptions: ").append(toIndentedString(editorOptions)).append("\n");
    sb.append("    encoding: ").append(toIndentedString(encoding)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
    sb.append("    studio: ").append(toIndentedString(studio)).append("\n");
    sb.append("    bibpm: ").append(toIndentedString(bibpm)).append("\n");
    sb.append("    xolap: ").append(toIndentedString(xolap)).append("\n");
    sb.append("    flatFileBased: ").append(toIndentedString(flatFileBased)).append("\n");
    sb.append("    essbaseInfo: ").append(toIndentedString(essbaseInfo)).append("\n");
    sb.append("    columnOperations: ").append(toIndentedString(columnOperations)).append("\n");
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

