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
 * CompactDesignationColumn
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class CompactDesignationColumn {
  public static final String SERIALIZED_NAME_ORIGINAL_HEADER_NAME = "originalHeaderName";
  @SerializedName(SERIALIZED_NAME_ORIGINAL_HEADER_NAME)
  private String originalHeaderName;

  public static final String SERIALIZED_NAME_OBJECT_NAME = "objectName";
  @SerializedName(SERIALIZED_NAME_OBJECT_NAME)
  private String objectName;

  public static final String SERIALIZED_NAME_REFERENCE_OBJECT_NAME = "referenceObjectName";
  @SerializedName(SERIALIZED_NAME_REFERENCE_OBJECT_NAME)
  private String referenceObjectName;

  public static final String SERIALIZED_NAME_OBJECT_TYPE = "objectType";
  @SerializedName(SERIALIZED_NAME_OBJECT_TYPE)
  private String objectType;

  public static final String SERIALIZED_NAME_FORMULA = "formula";
  @SerializedName(SERIALIZED_NAME_FORMULA)
  private String formula;

  public static final String SERIALIZED_NAME_DIMENSION_ALIAS = "dimensionAlias";
  @SerializedName(SERIALIZED_NAME_DIMENSION_ALIAS)
  private String dimensionAlias;

  public static final String SERIALIZED_NAME_EXTERNAL_DIM_NAME = "externalDimName";
  @SerializedName(SERIALIZED_NAME_EXTERNAL_DIM_NAME)
  private String externalDimName;

  public static final String SERIALIZED_NAME_SOLVE_ORDER = "solveOrder";
  @SerializedName(SERIALIZED_NAME_SOLVE_ORDER)
  private Integer solveOrder;


  public CompactDesignationColumn originalHeaderName(String originalHeaderName) {
    
    this.originalHeaderName = originalHeaderName;
    return this;
  }

   /**
   * Get originalHeaderName
   * @return originalHeaderName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getOriginalHeaderName() {
    return originalHeaderName;
  }


  public void setOriginalHeaderName(String originalHeaderName) {
    this.originalHeaderName = originalHeaderName;
  }


  public CompactDesignationColumn objectName(String objectName) {
    
    this.objectName = objectName;
    return this;
  }

   /**
   * Get objectName
   * @return objectName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getObjectName() {
    return objectName;
  }


  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }


  public CompactDesignationColumn referenceObjectName(String referenceObjectName) {
    
    this.referenceObjectName = referenceObjectName;
    return this;
  }

   /**
   * Get referenceObjectName
   * @return referenceObjectName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getReferenceObjectName() {
    return referenceObjectName;
  }


  public void setReferenceObjectName(String referenceObjectName) {
    this.referenceObjectName = referenceObjectName;
  }


  public CompactDesignationColumn objectType(String objectType) {
    
    this.objectType = objectType;
    return this;
  }

   /**
   * Get objectType
   * @return objectType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getObjectType() {
    return objectType;
  }


  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }


  public CompactDesignationColumn formula(String formula) {
    
    this.formula = formula;
    return this;
  }

   /**
   * Get formula
   * @return formula
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFormula() {
    return formula;
  }


  public void setFormula(String formula) {
    this.formula = formula;
  }


  public CompactDesignationColumn dimensionAlias(String dimensionAlias) {
    
    this.dimensionAlias = dimensionAlias;
    return this;
  }

   /**
   * Get dimensionAlias
   * @return dimensionAlias
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimensionAlias() {
    return dimensionAlias;
  }


  public void setDimensionAlias(String dimensionAlias) {
    this.dimensionAlias = dimensionAlias;
  }


  public CompactDesignationColumn externalDimName(String externalDimName) {
    
    this.externalDimName = externalDimName;
    return this;
  }

   /**
   * Get externalDimName
   * @return externalDimName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getExternalDimName() {
    return externalDimName;
  }


  public void setExternalDimName(String externalDimName) {
    this.externalDimName = externalDimName;
  }


  public CompactDesignationColumn solveOrder(Integer solveOrder) {
    
    this.solveOrder = solveOrder;
    return this;
  }

   /**
   * Get solveOrder
   * @return solveOrder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getSolveOrder() {
    return solveOrder;
  }


  public void setSolveOrder(Integer solveOrder) {
    this.solveOrder = solveOrder;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompactDesignationColumn compactDesignationColumn = (CompactDesignationColumn) o;
    return Objects.equals(this.originalHeaderName, compactDesignationColumn.originalHeaderName) &&
        Objects.equals(this.objectName, compactDesignationColumn.objectName) &&
        Objects.equals(this.referenceObjectName, compactDesignationColumn.referenceObjectName) &&
        Objects.equals(this.objectType, compactDesignationColumn.objectType) &&
        Objects.equals(this.formula, compactDesignationColumn.formula) &&
        Objects.equals(this.dimensionAlias, compactDesignationColumn.dimensionAlias) &&
        Objects.equals(this.externalDimName, compactDesignationColumn.externalDimName) &&
        Objects.equals(this.solveOrder, compactDesignationColumn.solveOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalHeaderName, objectName, referenceObjectName, objectType, formula, dimensionAlias, externalDimName, solveOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompactDesignationColumn {\n");
    sb.append("    originalHeaderName: ").append(toIndentedString(originalHeaderName)).append("\n");
    sb.append("    objectName: ").append(toIndentedString(objectName)).append("\n");
    sb.append("    referenceObjectName: ").append(toIndentedString(referenceObjectName)).append("\n");
    sb.append("    objectType: ").append(toIndentedString(objectType)).append("\n");
    sb.append("    formula: ").append(toIndentedString(formula)).append("\n");
    sb.append("    dimensionAlias: ").append(toIndentedString(dimensionAlias)).append("\n");
    sb.append("    externalDimName: ").append(toIndentedString(externalDimName)).append("\n");
    sb.append("    solveOrder: ").append(toIndentedString(solveOrder)).append("\n");
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

