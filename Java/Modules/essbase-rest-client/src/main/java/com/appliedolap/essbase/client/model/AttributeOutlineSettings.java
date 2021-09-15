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
 * AttributeOutlineSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class AttributeOutlineSettings {
  public static final String SERIALIZED_NAME_PREFIX_SUFFIX_VALUE = "prefixSuffixValue";
  @SerializedName(SERIALIZED_NAME_PREFIX_SUFFIX_VALUE)
  private String prefixSuffixValue;

  public static final String SERIALIZED_NAME_PREFIX_SUFFIX_SEPARATOR = "prefixSuffixSeparator";
  @SerializedName(SERIALIZED_NAME_PREFIX_SUFFIX_SEPARATOR)
  private String prefixSuffixSeparator;

  public static final String SERIALIZED_NAME_PREFIX_SUFFIX_FORMAT = "prefixSuffixFormat";
  @SerializedName(SERIALIZED_NAME_PREFIX_SUFFIX_FORMAT)
  private String prefixSuffixFormat;

  public static final String SERIALIZED_NAME_TRUE_MEMBER_NAME = "trueMemberName";
  @SerializedName(SERIALIZED_NAME_TRUE_MEMBER_NAME)
  private String trueMemberName;

  public static final String SERIALIZED_NAME_FALSE_MEMBER_NAME = "falseMemberName";
  @SerializedName(SERIALIZED_NAME_FALSE_MEMBER_NAME)
  private String falseMemberName;

  public static final String SERIALIZED_NAME_DATE_MEMBER_NAMES = "dateMemberNames";
  @SerializedName(SERIALIZED_NAME_DATE_MEMBER_NAMES)
  private String dateMemberNames;

  public static final String SERIALIZED_NAME_NUMERIC_RANGES_REPRESENT = "numericRangesRepresent";
  @SerializedName(SERIALIZED_NAME_NUMERIC_RANGES_REPRESENT)
  private String numericRangesRepresent;

  public static final String SERIALIZED_NAME_CALC_DIMENSION_NAME = "calcDimensionName";
  @SerializedName(SERIALIZED_NAME_CALC_DIMENSION_NAME)
  private String calcDimensionName;

  public static final String SERIALIZED_NAME_CALC_SUM_MEMBER = "calcSumMember";
  @SerializedName(SERIALIZED_NAME_CALC_SUM_MEMBER)
  private String calcSumMember;

  public static final String SERIALIZED_NAME_CALC_COUNT_MEMBER = "calcCountMember";
  @SerializedName(SERIALIZED_NAME_CALC_COUNT_MEMBER)
  private String calcCountMember;

  public static final String SERIALIZED_NAME_CALC_MINIMUM_MEMBER = "calcMinimumMember";
  @SerializedName(SERIALIZED_NAME_CALC_MINIMUM_MEMBER)
  private String calcMinimumMember;

  public static final String SERIALIZED_NAME_CALC_MAXIMUM_MEMBER = "calcMaximumMember";
  @SerializedName(SERIALIZED_NAME_CALC_MAXIMUM_MEMBER)
  private String calcMaximumMember;

  public static final String SERIALIZED_NAME_CALC_AVERAGE_MEMBER = "calcAverageMember";
  @SerializedName(SERIALIZED_NAME_CALC_AVERAGE_MEMBER)
  private String calcAverageMember;


  public AttributeOutlineSettings prefixSuffixValue(String prefixSuffixValue) {
    
    this.prefixSuffixValue = prefixSuffixValue;
    return this;
  }

   /**
   * Get prefixSuffixValue
   * @return prefixSuffixValue
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPrefixSuffixValue() {
    return prefixSuffixValue;
  }


  public void setPrefixSuffixValue(String prefixSuffixValue) {
    this.prefixSuffixValue = prefixSuffixValue;
  }


  public AttributeOutlineSettings prefixSuffixSeparator(String prefixSuffixSeparator) {
    
    this.prefixSuffixSeparator = prefixSuffixSeparator;
    return this;
  }

   /**
   * Get prefixSuffixSeparator
   * @return prefixSuffixSeparator
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPrefixSuffixSeparator() {
    return prefixSuffixSeparator;
  }


  public void setPrefixSuffixSeparator(String prefixSuffixSeparator) {
    this.prefixSuffixSeparator = prefixSuffixSeparator;
  }


  public AttributeOutlineSettings prefixSuffixFormat(String prefixSuffixFormat) {
    
    this.prefixSuffixFormat = prefixSuffixFormat;
    return this;
  }

   /**
   * Get prefixSuffixFormat
   * @return prefixSuffixFormat
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPrefixSuffixFormat() {
    return prefixSuffixFormat;
  }


  public void setPrefixSuffixFormat(String prefixSuffixFormat) {
    this.prefixSuffixFormat = prefixSuffixFormat;
  }


  public AttributeOutlineSettings trueMemberName(String trueMemberName) {
    
    this.trueMemberName = trueMemberName;
    return this;
  }

   /**
   * Get trueMemberName
   * @return trueMemberName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getTrueMemberName() {
    return trueMemberName;
  }


  public void setTrueMemberName(String trueMemberName) {
    this.trueMemberName = trueMemberName;
  }


  public AttributeOutlineSettings falseMemberName(String falseMemberName) {
    
    this.falseMemberName = falseMemberName;
    return this;
  }

   /**
   * Get falseMemberName
   * @return falseMemberName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFalseMemberName() {
    return falseMemberName;
  }


  public void setFalseMemberName(String falseMemberName) {
    this.falseMemberName = falseMemberName;
  }


  public AttributeOutlineSettings dateMemberNames(String dateMemberNames) {
    
    this.dateMemberNames = dateMemberNames;
    return this;
  }

   /**
   * Get dateMemberNames
   * @return dateMemberNames
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDateMemberNames() {
    return dateMemberNames;
  }


  public void setDateMemberNames(String dateMemberNames) {
    this.dateMemberNames = dateMemberNames;
  }


  public AttributeOutlineSettings numericRangesRepresent(String numericRangesRepresent) {
    
    this.numericRangesRepresent = numericRangesRepresent;
    return this;
  }

   /**
   * Get numericRangesRepresent
   * @return numericRangesRepresent
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getNumericRangesRepresent() {
    return numericRangesRepresent;
  }


  public void setNumericRangesRepresent(String numericRangesRepresent) {
    this.numericRangesRepresent = numericRangesRepresent;
  }


  public AttributeOutlineSettings calcDimensionName(String calcDimensionName) {
    
    this.calcDimensionName = calcDimensionName;
    return this;
  }

   /**
   * Get calcDimensionName
   * @return calcDimensionName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcDimensionName() {
    return calcDimensionName;
  }


  public void setCalcDimensionName(String calcDimensionName) {
    this.calcDimensionName = calcDimensionName;
  }


  public AttributeOutlineSettings calcSumMember(String calcSumMember) {
    
    this.calcSumMember = calcSumMember;
    return this;
  }

   /**
   * Get calcSumMember
   * @return calcSumMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcSumMember() {
    return calcSumMember;
  }


  public void setCalcSumMember(String calcSumMember) {
    this.calcSumMember = calcSumMember;
  }


  public AttributeOutlineSettings calcCountMember(String calcCountMember) {
    
    this.calcCountMember = calcCountMember;
    return this;
  }

   /**
   * Get calcCountMember
   * @return calcCountMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcCountMember() {
    return calcCountMember;
  }


  public void setCalcCountMember(String calcCountMember) {
    this.calcCountMember = calcCountMember;
  }


  public AttributeOutlineSettings calcMinimumMember(String calcMinimumMember) {
    
    this.calcMinimumMember = calcMinimumMember;
    return this;
  }

   /**
   * Get calcMinimumMember
   * @return calcMinimumMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcMinimumMember() {
    return calcMinimumMember;
  }


  public void setCalcMinimumMember(String calcMinimumMember) {
    this.calcMinimumMember = calcMinimumMember;
  }


  public AttributeOutlineSettings calcMaximumMember(String calcMaximumMember) {
    
    this.calcMaximumMember = calcMaximumMember;
    return this;
  }

   /**
   * Get calcMaximumMember
   * @return calcMaximumMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcMaximumMember() {
    return calcMaximumMember;
  }


  public void setCalcMaximumMember(String calcMaximumMember) {
    this.calcMaximumMember = calcMaximumMember;
  }


  public AttributeOutlineSettings calcAverageMember(String calcAverageMember) {
    
    this.calcAverageMember = calcAverageMember;
    return this;
  }

   /**
   * Get calcAverageMember
   * @return calcAverageMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCalcAverageMember() {
    return calcAverageMember;
  }


  public void setCalcAverageMember(String calcAverageMember) {
    this.calcAverageMember = calcAverageMember;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AttributeOutlineSettings attributeOutlineSettings = (AttributeOutlineSettings) o;
    return Objects.equals(this.prefixSuffixValue, attributeOutlineSettings.prefixSuffixValue) &&
        Objects.equals(this.prefixSuffixSeparator, attributeOutlineSettings.prefixSuffixSeparator) &&
        Objects.equals(this.prefixSuffixFormat, attributeOutlineSettings.prefixSuffixFormat) &&
        Objects.equals(this.trueMemberName, attributeOutlineSettings.trueMemberName) &&
        Objects.equals(this.falseMemberName, attributeOutlineSettings.falseMemberName) &&
        Objects.equals(this.dateMemberNames, attributeOutlineSettings.dateMemberNames) &&
        Objects.equals(this.numericRangesRepresent, attributeOutlineSettings.numericRangesRepresent) &&
        Objects.equals(this.calcDimensionName, attributeOutlineSettings.calcDimensionName) &&
        Objects.equals(this.calcSumMember, attributeOutlineSettings.calcSumMember) &&
        Objects.equals(this.calcCountMember, attributeOutlineSettings.calcCountMember) &&
        Objects.equals(this.calcMinimumMember, attributeOutlineSettings.calcMinimumMember) &&
        Objects.equals(this.calcMaximumMember, attributeOutlineSettings.calcMaximumMember) &&
        Objects.equals(this.calcAverageMember, attributeOutlineSettings.calcAverageMember);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prefixSuffixValue, prefixSuffixSeparator, prefixSuffixFormat, trueMemberName, falseMemberName, dateMemberNames, numericRangesRepresent, calcDimensionName, calcSumMember, calcCountMember, calcMinimumMember, calcMaximumMember, calcAverageMember);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AttributeOutlineSettings {\n");
    sb.append("    prefixSuffixValue: ").append(toIndentedString(prefixSuffixValue)).append("\n");
    sb.append("    prefixSuffixSeparator: ").append(toIndentedString(prefixSuffixSeparator)).append("\n");
    sb.append("    prefixSuffixFormat: ").append(toIndentedString(prefixSuffixFormat)).append("\n");
    sb.append("    trueMemberName: ").append(toIndentedString(trueMemberName)).append("\n");
    sb.append("    falseMemberName: ").append(toIndentedString(falseMemberName)).append("\n");
    sb.append("    dateMemberNames: ").append(toIndentedString(dateMemberNames)).append("\n");
    sb.append("    numericRangesRepresent: ").append(toIndentedString(numericRangesRepresent)).append("\n");
    sb.append("    calcDimensionName: ").append(toIndentedString(calcDimensionName)).append("\n");
    sb.append("    calcSumMember: ").append(toIndentedString(calcSumMember)).append("\n");
    sb.append("    calcCountMember: ").append(toIndentedString(calcCountMember)).append("\n");
    sb.append("    calcMinimumMember: ").append(toIndentedString(calcMinimumMember)).append("\n");
    sb.append("    calcMaximumMember: ").append(toIndentedString(calcMaximumMember)).append("\n");
    sb.append("    calcAverageMember: ").append(toIndentedString(calcAverageMember)).append("\n");
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

