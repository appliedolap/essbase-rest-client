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
import com.appliedolap.essbase.client.model.DVUpdatedCell;
import com.appliedolap.essbase.client.model.GridDimension;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DVGrid
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class DVGrid {
  public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS)
  private List<GridDimension> dimensions = null;

  public static final String SERIALIZED_NAME_COLUMNS = "columns";
  @SerializedName(SERIALIZED_NAME_COLUMNS)
  private Integer columns;

  public static final String SERIALIZED_NAME_ROWS = "rows";
  @SerializedName(SERIALIZED_NAME_ROWS)
  private Integer rows;

  public static final String SERIALIZED_NAME_FILTERS = "filters";
  @SerializedName(SERIALIZED_NAME_FILTERS)
  private Map<String, String> filters = null;

  public static final String SERIALIZED_NAME_UPDATED_CELLS = "updatedCells";
  @SerializedName(SERIALIZED_NAME_UPDATED_CELLS)
  private List<DVUpdatedCell> updatedCells = null;

  public static final String SERIALIZED_NAME_ROW_HEADER_LEVELS = "rowHeaderLevels";
  @SerializedName(SERIALIZED_NAME_ROW_HEADER_LEVELS)
  private Integer rowHeaderLevels;

  public static final String SERIALIZED_NAME_COLUMN_HEADER_LEVELS = "columnHeaderLevels";
  @SerializedName(SERIALIZED_NAME_COLUMN_HEADER_LEVELS)
  private Integer columnHeaderLevels;

  public static final String SERIALIZED_NAME_GETN_ROW_HEADER_LEVELS = "getnRowHeaderLevels";
  @SerializedName(SERIALIZED_NAME_GETN_ROW_HEADER_LEVELS)
  private Integer getnRowHeaderLevels;


  public DVGrid dimensions(List<GridDimension> dimensions) {
    
    this.dimensions = dimensions;
    return this;
  }

  public DVGrid addDimensionsItem(GridDimension dimensionsItem) {
    if (this.dimensions == null) {
      this.dimensions = new ArrayList<GridDimension>();
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

  public List<GridDimension> getDimensions() {
    return dimensions;
  }


  public void setDimensions(List<GridDimension> dimensions) {
    this.dimensions = dimensions;
  }


  public DVGrid columns(Integer columns) {
    
    this.columns = columns;
    return this;
  }

   /**
   * Get columns
   * @return columns
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getColumns() {
    return columns;
  }


  public void setColumns(Integer columns) {
    this.columns = columns;
  }


  public DVGrid rows(Integer rows) {
    
    this.rows = rows;
    return this;
  }

   /**
   * Get rows
   * @return rows
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRows() {
    return rows;
  }


  public void setRows(Integer rows) {
    this.rows = rows;
  }


  public DVGrid filters(Map<String, String> filters) {
    
    this.filters = filters;
    return this;
  }

  public DVGrid putFiltersItem(String key, String filtersItem) {
    if (this.filters == null) {
      this.filters = new HashMap<String, String>();
    }
    this.filters.put(key, filtersItem);
    return this;
  }

   /**
   * Get filters
   * @return filters
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Map<String, String> getFilters() {
    return filters;
  }


  public void setFilters(Map<String, String> filters) {
    this.filters = filters;
  }


  public DVGrid updatedCells(List<DVUpdatedCell> updatedCells) {
    
    this.updatedCells = updatedCells;
    return this;
  }

  public DVGrid addUpdatedCellsItem(DVUpdatedCell updatedCellsItem) {
    if (this.updatedCells == null) {
      this.updatedCells = new ArrayList<DVUpdatedCell>();
    }
    this.updatedCells.add(updatedCellsItem);
    return this;
  }

   /**
   * Get updatedCells
   * @return updatedCells
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<DVUpdatedCell> getUpdatedCells() {
    return updatedCells;
  }


  public void setUpdatedCells(List<DVUpdatedCell> updatedCells) {
    this.updatedCells = updatedCells;
  }


  public DVGrid rowHeaderLevels(Integer rowHeaderLevels) {
    
    this.rowHeaderLevels = rowHeaderLevels;
    return this;
  }

   /**
   * Get rowHeaderLevels
   * @return rowHeaderLevels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRowHeaderLevels() {
    return rowHeaderLevels;
  }


  public void setRowHeaderLevels(Integer rowHeaderLevels) {
    this.rowHeaderLevels = rowHeaderLevels;
  }


  public DVGrid columnHeaderLevels(Integer columnHeaderLevels) {
    
    this.columnHeaderLevels = columnHeaderLevels;
    return this;
  }

   /**
   * Get columnHeaderLevels
   * @return columnHeaderLevels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getColumnHeaderLevels() {
    return columnHeaderLevels;
  }


  public void setColumnHeaderLevels(Integer columnHeaderLevels) {
    this.columnHeaderLevels = columnHeaderLevels;
  }


  public DVGrid getnRowHeaderLevels(Integer getnRowHeaderLevels) {
    
    this.getnRowHeaderLevels = getnRowHeaderLevels;
    return this;
  }

   /**
   * Get getnRowHeaderLevels
   * @return getnRowHeaderLevels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGetnRowHeaderLevels() {
    return getnRowHeaderLevels;
  }


  public void setGetnRowHeaderLevels(Integer getnRowHeaderLevels) {
    this.getnRowHeaderLevels = getnRowHeaderLevels;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DVGrid dvGrid = (DVGrid) o;
    return Objects.equals(this.dimensions, dvGrid.dimensions) &&
        Objects.equals(this.columns, dvGrid.columns) &&
        Objects.equals(this.rows, dvGrid.rows) &&
        Objects.equals(this.filters, dvGrid.filters) &&
        Objects.equals(this.updatedCells, dvGrid.updatedCells) &&
        Objects.equals(this.rowHeaderLevels, dvGrid.rowHeaderLevels) &&
        Objects.equals(this.columnHeaderLevels, dvGrid.columnHeaderLevels) &&
        Objects.equals(this.getnRowHeaderLevels, dvGrid.getnRowHeaderLevels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimensions, columns, rows, filters, updatedCells, rowHeaderLevels, columnHeaderLevels, getnRowHeaderLevels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DVGrid {\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
    sb.append("    rows: ").append(toIndentedString(rows)).append("\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    updatedCells: ").append(toIndentedString(updatedCells)).append("\n");
    sb.append("    rowHeaderLevels: ").append(toIndentedString(rowHeaderLevels)).append("\n");
    sb.append("    columnHeaderLevels: ").append(toIndentedString(columnHeaderLevels)).append("\n");
    sb.append("    getnRowHeaderLevels: ").append(toIndentedString(getnRowHeaderLevels)).append("\n");
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

