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
import java.util.ArrayList;
import java.util.List;

/**
 * DimCompactDesignation
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class DimCompactDesignation {
  public static final String SERIALIZED_NAME_QUERY = "query";
  @SerializedName(SERIALIZED_NAME_QUERY)
  private String query;

  public static final String SERIALIZED_NAME_ESSBASE_CONNECTION = "essbaseConnection";
  @SerializedName(SERIALIZED_NAME_ESSBASE_CONNECTION)
  private String essbaseConnection;

  public static final String SERIALIZED_NAME_GEN_NAMES = "genNames";
  @SerializedName(SERIALIZED_NAME_GEN_NAMES)
  private List<String> genNames = null;

  public static final String SERIALIZED_NAME_COL_NAMES = "colNames";
  @SerializedName(SERIALIZED_NAME_COL_NAMES)
  private List<String> colNames = null;

  public static final String SERIALIZED_NAME_DIM_GEN_COLUMNS = "dimGenColumns";
  @SerializedName(SERIALIZED_NAME_DIM_GEN_COLUMNS)
  private List<Integer> dimGenColumns = null;

  public static final String SERIALIZED_NAME_ATTRIBUTE = "attribute";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE)
  private List<Integer> attribute = null;

  public static final String SERIALIZED_NAME_DIM_JOIN = "dimJoin";
  @SerializedName(SERIALIZED_NAME_DIM_JOIN)
  private String dimJoin;

  public static final String SERIALIZED_NAME_DIM_NAME = "dimName";
  @SerializedName(SERIALIZED_NAME_DIM_NAME)
  private String dimName;

  public static final String SERIALIZED_NAME_ATT_NAMES = "attNames";
  @SerializedName(SERIALIZED_NAME_ATT_NAMES)
  private List<String> attNames = null;

  /**
   * Gets or Sets columnTypes
   */
  @JsonAdapter(ColumnTypesEnum.Adapter.class)
  public enum ColumnTypesEnum {
    TEXT("TEXT"),
    
    INTEGER("INTEGER"),
    
    FLOAT("FLOAT"),
    
    TIME("TIME"),
    
    DATE("DATE"),
    
    BOOLEAN("BOOLEAN"),
    
    EMPTY("EMPTY"),
    
    UNKNOWN("UNKNOWN"),
    
    OUT_OF_RANGE("OUT_OF_RANGE");

    private String value;

    ColumnTypesEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ColumnTypesEnum fromValue(String value) {
      for (ColumnTypesEnum b : ColumnTypesEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ColumnTypesEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ColumnTypesEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ColumnTypesEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ColumnTypesEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_COLUMN_TYPES = "columnTypes";
  @SerializedName(SERIALIZED_NAME_COLUMN_TYPES)
  private List<ColumnTypesEnum> columnTypes = null;

  public static final String SERIALIZED_NAME_UNIQ_COUNT = "uniqCount";
  @SerializedName(SERIALIZED_NAME_UNIQ_COUNT)
  private List<Integer> uniqCount = null;

  public static final String SERIALIZED_NAME_HEADER_TEXT = "headerText";
  @SerializedName(SERIALIZED_NAME_HEADER_TEXT)
  private List<String> headerText = null;

  public static final String SERIALIZED_NAME_FKCOLUMN_NUMBER = "fkcolumnNumber";
  @SerializedName(SERIALIZED_NAME_FKCOLUMN_NUMBER)
  private Integer fkcolumnNumber;


  public DimCompactDesignation query(String query) {
    
    this.query = query;
    return this;
  }

   /**
   * Get query
   * @return query
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getQuery() {
    return query;
  }


  public void setQuery(String query) {
    this.query = query;
  }


  public DimCompactDesignation essbaseConnection(String essbaseConnection) {
    
    this.essbaseConnection = essbaseConnection;
    return this;
  }

   /**
   * Get essbaseConnection
   * @return essbaseConnection
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getEssbaseConnection() {
    return essbaseConnection;
  }


  public void setEssbaseConnection(String essbaseConnection) {
    this.essbaseConnection = essbaseConnection;
  }


  public DimCompactDesignation genNames(List<String> genNames) {
    
    this.genNames = genNames;
    return this;
  }

  public DimCompactDesignation addGenNamesItem(String genNamesItem) {
    if (this.genNames == null) {
      this.genNames = new ArrayList<String>();
    }
    this.genNames.add(genNamesItem);
    return this;
  }

   /**
   * Get genNames
   * @return genNames
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getGenNames() {
    return genNames;
  }


  public void setGenNames(List<String> genNames) {
    this.genNames = genNames;
  }


  public DimCompactDesignation colNames(List<String> colNames) {
    
    this.colNames = colNames;
    return this;
  }

  public DimCompactDesignation addColNamesItem(String colNamesItem) {
    if (this.colNames == null) {
      this.colNames = new ArrayList<String>();
    }
    this.colNames.add(colNamesItem);
    return this;
  }

   /**
   * Get colNames
   * @return colNames
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getColNames() {
    return colNames;
  }


  public void setColNames(List<String> colNames) {
    this.colNames = colNames;
  }


  public DimCompactDesignation dimGenColumns(List<Integer> dimGenColumns) {
    
    this.dimGenColumns = dimGenColumns;
    return this;
  }

  public DimCompactDesignation addDimGenColumnsItem(Integer dimGenColumnsItem) {
    if (this.dimGenColumns == null) {
      this.dimGenColumns = new ArrayList<Integer>();
    }
    this.dimGenColumns.add(dimGenColumnsItem);
    return this;
  }

   /**
   * Get dimGenColumns
   * @return dimGenColumns
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Integer> getDimGenColumns() {
    return dimGenColumns;
  }


  public void setDimGenColumns(List<Integer> dimGenColumns) {
    this.dimGenColumns = dimGenColumns;
  }


  public DimCompactDesignation attribute(List<Integer> attribute) {
    
    this.attribute = attribute;
    return this;
  }

  public DimCompactDesignation addAttributeItem(Integer attributeItem) {
    if (this.attribute == null) {
      this.attribute = new ArrayList<Integer>();
    }
    this.attribute.add(attributeItem);
    return this;
  }

   /**
   * Get attribute
   * @return attribute
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Integer> getAttribute() {
    return attribute;
  }


  public void setAttribute(List<Integer> attribute) {
    this.attribute = attribute;
  }


  public DimCompactDesignation dimJoin(String dimJoin) {
    
    this.dimJoin = dimJoin;
    return this;
  }

   /**
   * Get dimJoin
   * @return dimJoin
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimJoin() {
    return dimJoin;
  }


  public void setDimJoin(String dimJoin) {
    this.dimJoin = dimJoin;
  }


  public DimCompactDesignation dimName(String dimName) {
    
    this.dimName = dimName;
    return this;
  }

   /**
   * Get dimName
   * @return dimName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimName() {
    return dimName;
  }


  public void setDimName(String dimName) {
    this.dimName = dimName;
  }


  public DimCompactDesignation attNames(List<String> attNames) {
    
    this.attNames = attNames;
    return this;
  }

  public DimCompactDesignation addAttNamesItem(String attNamesItem) {
    if (this.attNames == null) {
      this.attNames = new ArrayList<String>();
    }
    this.attNames.add(attNamesItem);
    return this;
  }

   /**
   * Get attNames
   * @return attNames
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getAttNames() {
    return attNames;
  }


  public void setAttNames(List<String> attNames) {
    this.attNames = attNames;
  }


  public DimCompactDesignation columnTypes(List<ColumnTypesEnum> columnTypes) {
    
    this.columnTypes = columnTypes;
    return this;
  }

  public DimCompactDesignation addColumnTypesItem(ColumnTypesEnum columnTypesItem) {
    if (this.columnTypes == null) {
      this.columnTypes = new ArrayList<ColumnTypesEnum>();
    }
    this.columnTypes.add(columnTypesItem);
    return this;
  }

   /**
   * Get columnTypes
   * @return columnTypes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ColumnTypesEnum> getColumnTypes() {
    return columnTypes;
  }


  public void setColumnTypes(List<ColumnTypesEnum> columnTypes) {
    this.columnTypes = columnTypes;
  }


  public DimCompactDesignation uniqCount(List<Integer> uniqCount) {
    
    this.uniqCount = uniqCount;
    return this;
  }

  public DimCompactDesignation addUniqCountItem(Integer uniqCountItem) {
    if (this.uniqCount == null) {
      this.uniqCount = new ArrayList<Integer>();
    }
    this.uniqCount.add(uniqCountItem);
    return this;
  }

   /**
   * Get uniqCount
   * @return uniqCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Integer> getUniqCount() {
    return uniqCount;
  }


  public void setUniqCount(List<Integer> uniqCount) {
    this.uniqCount = uniqCount;
  }


  public DimCompactDesignation headerText(List<String> headerText) {
    
    this.headerText = headerText;
    return this;
  }

  public DimCompactDesignation addHeaderTextItem(String headerTextItem) {
    if (this.headerText == null) {
      this.headerText = new ArrayList<String>();
    }
    this.headerText.add(headerTextItem);
    return this;
  }

   /**
   * Get headerText
   * @return headerText
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getHeaderText() {
    return headerText;
  }


  public void setHeaderText(List<String> headerText) {
    this.headerText = headerText;
  }


  public DimCompactDesignation fkcolumnNumber(Integer fkcolumnNumber) {
    
    this.fkcolumnNumber = fkcolumnNumber;
    return this;
  }

   /**
   * Get fkcolumnNumber
   * @return fkcolumnNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getFkcolumnNumber() {
    return fkcolumnNumber;
  }


  public void setFkcolumnNumber(Integer fkcolumnNumber) {
    this.fkcolumnNumber = fkcolumnNumber;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DimCompactDesignation dimCompactDesignation = (DimCompactDesignation) o;
    return Objects.equals(this.query, dimCompactDesignation.query) &&
        Objects.equals(this.essbaseConnection, dimCompactDesignation.essbaseConnection) &&
        Objects.equals(this.genNames, dimCompactDesignation.genNames) &&
        Objects.equals(this.colNames, dimCompactDesignation.colNames) &&
        Objects.equals(this.dimGenColumns, dimCompactDesignation.dimGenColumns) &&
        Objects.equals(this.attribute, dimCompactDesignation.attribute) &&
        Objects.equals(this.dimJoin, dimCompactDesignation.dimJoin) &&
        Objects.equals(this.dimName, dimCompactDesignation.dimName) &&
        Objects.equals(this.attNames, dimCompactDesignation.attNames) &&
        Objects.equals(this.columnTypes, dimCompactDesignation.columnTypes) &&
        Objects.equals(this.uniqCount, dimCompactDesignation.uniqCount) &&
        Objects.equals(this.headerText, dimCompactDesignation.headerText) &&
        Objects.equals(this.fkcolumnNumber, dimCompactDesignation.fkcolumnNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(query, essbaseConnection, genNames, colNames, dimGenColumns, attribute, dimJoin, dimName, attNames, columnTypes, uniqCount, headerText, fkcolumnNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DimCompactDesignation {\n");
    sb.append("    query: ").append(toIndentedString(query)).append("\n");
    sb.append("    essbaseConnection: ").append(toIndentedString(essbaseConnection)).append("\n");
    sb.append("    genNames: ").append(toIndentedString(genNames)).append("\n");
    sb.append("    colNames: ").append(toIndentedString(colNames)).append("\n");
    sb.append("    dimGenColumns: ").append(toIndentedString(dimGenColumns)).append("\n");
    sb.append("    attribute: ").append(toIndentedString(attribute)).append("\n");
    sb.append("    dimJoin: ").append(toIndentedString(dimJoin)).append("\n");
    sb.append("    dimName: ").append(toIndentedString(dimName)).append("\n");
    sb.append("    attNames: ").append(toIndentedString(attNames)).append("\n");
    sb.append("    columnTypes: ").append(toIndentedString(columnTypes)).append("\n");
    sb.append("    uniqCount: ").append(toIndentedString(uniqCount)).append("\n");
    sb.append("    headerText: ").append(toIndentedString(headerText)).append("\n");
    sb.append("    fkcolumnNumber: ").append(toIndentedString(fkcolumnNumber)).append("\n");
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

