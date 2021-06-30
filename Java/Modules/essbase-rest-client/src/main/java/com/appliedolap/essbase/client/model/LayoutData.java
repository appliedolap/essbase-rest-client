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
 * LayoutData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class LayoutData {
  public static final String SERIALIZED_NAME_VALUES = "values";
  @SerializedName(SERIALIZED_NAME_VALUES)
  private List<List<String>> values = null;

  public static final String SERIALIZED_NAME_TYPES = "types";
  @SerializedName(SERIALIZED_NAME_TYPES)
  private List<List<String>> types = null;

  public static final String SERIALIZED_NAME_TEXTS = "texts";
  @SerializedName(SERIALIZED_NAME_TEXTS)
  private List<List<String>> texts = null;

  public static final String SERIALIZED_NAME_DATA_FORMATS = "dataFormats";
  @SerializedName(SERIALIZED_NAME_DATA_FORMATS)
  private List<List<String>> dataFormats = null;

  public static final String SERIALIZED_NAME_STATUSES = "statuses";
  @SerializedName(SERIALIZED_NAME_STATUSES)
  private List<List<String>> statuses = null;

  public static final String SERIALIZED_NAME_FILTERS = "filters";
  @SerializedName(SERIALIZED_NAME_FILTERS)
  private List<List<String>> filters = null;

  public static final String SERIALIZED_NAME_ENUM_IDS = "enumIds";
  @SerializedName(SERIALIZED_NAME_ENUM_IDS)
  private List<List<String>> enumIds = null;


  public LayoutData values(List<List<String>> values) {
    
    this.values = values;
    return this;
  }

  public LayoutData addValuesItem(List<String> valuesItem) {
    if (this.values == null) {
      this.values = new ArrayList<List<String>>();
    }
    this.values.add(valuesItem);
    return this;
  }

   /**
   * Get values
   * @return values
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getValues() {
    return values;
  }


  public void setValues(List<List<String>> values) {
    this.values = values;
  }


  public LayoutData types(List<List<String>> types) {
    
    this.types = types;
    return this;
  }

  public LayoutData addTypesItem(List<String> typesItem) {
    if (this.types == null) {
      this.types = new ArrayList<List<String>>();
    }
    this.types.add(typesItem);
    return this;
  }

   /**
   * Get types
   * @return types
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getTypes() {
    return types;
  }


  public void setTypes(List<List<String>> types) {
    this.types = types;
  }


  public LayoutData texts(List<List<String>> texts) {
    
    this.texts = texts;
    return this;
  }

  public LayoutData addTextsItem(List<String> textsItem) {
    if (this.texts == null) {
      this.texts = new ArrayList<List<String>>();
    }
    this.texts.add(textsItem);
    return this;
  }

   /**
   * Get texts
   * @return texts
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getTexts() {
    return texts;
  }


  public void setTexts(List<List<String>> texts) {
    this.texts = texts;
  }


  public LayoutData dataFormats(List<List<String>> dataFormats) {
    
    this.dataFormats = dataFormats;
    return this;
  }

  public LayoutData addDataFormatsItem(List<String> dataFormatsItem) {
    if (this.dataFormats == null) {
      this.dataFormats = new ArrayList<List<String>>();
    }
    this.dataFormats.add(dataFormatsItem);
    return this;
  }

   /**
   * Get dataFormats
   * @return dataFormats
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getDataFormats() {
    return dataFormats;
  }


  public void setDataFormats(List<List<String>> dataFormats) {
    this.dataFormats = dataFormats;
  }


  public LayoutData statuses(List<List<String>> statuses) {
    
    this.statuses = statuses;
    return this;
  }

  public LayoutData addStatusesItem(List<String> statusesItem) {
    if (this.statuses == null) {
      this.statuses = new ArrayList<List<String>>();
    }
    this.statuses.add(statusesItem);
    return this;
  }

   /**
   * Get statuses
   * @return statuses
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getStatuses() {
    return statuses;
  }


  public void setStatuses(List<List<String>> statuses) {
    this.statuses = statuses;
  }


  public LayoutData filters(List<List<String>> filters) {
    
    this.filters = filters;
    return this;
  }

  public LayoutData addFiltersItem(List<String> filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<List<String>>();
    }
    this.filters.add(filtersItem);
    return this;
  }

   /**
   * Get filters
   * @return filters
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getFilters() {
    return filters;
  }


  public void setFilters(List<List<String>> filters) {
    this.filters = filters;
  }


  public LayoutData enumIds(List<List<String>> enumIds) {
    
    this.enumIds = enumIds;
    return this;
  }

  public LayoutData addEnumIdsItem(List<String> enumIdsItem) {
    if (this.enumIds == null) {
      this.enumIds = new ArrayList<List<String>>();
    }
    this.enumIds.add(enumIdsItem);
    return this;
  }

   /**
   * Get enumIds
   * @return enumIds
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<List<String>> getEnumIds() {
    return enumIds;
  }


  public void setEnumIds(List<List<String>> enumIds) {
    this.enumIds = enumIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LayoutData layoutData = (LayoutData) o;
    return Objects.equals(this.values, layoutData.values) &&
        Objects.equals(this.types, layoutData.types) &&
        Objects.equals(this.texts, layoutData.texts) &&
        Objects.equals(this.dataFormats, layoutData.dataFormats) &&
        Objects.equals(this.statuses, layoutData.statuses) &&
        Objects.equals(this.filters, layoutData.filters) &&
        Objects.equals(this.enumIds, layoutData.enumIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values, types, texts, dataFormats, statuses, filters, enumIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LayoutData {\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("    types: ").append(toIndentedString(types)).append("\n");
    sb.append("    texts: ").append(toIndentedString(texts)).append("\n");
    sb.append("    dataFormats: ").append(toIndentedString(dataFormats)).append("\n");
    sb.append("    statuses: ").append(toIndentedString(statuses)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    enumIds: ").append(toIndentedString(enumIds)).append("\n");
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

