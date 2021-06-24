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
import com.appliedolap.essbase.client.model.FieldDataLoadOptions;
import com.appliedolap.essbase.client.model.FieldDimBuildOptions;
import com.appliedolap.essbase.client.model.Filter;
import com.appliedolap.essbase.client.model.ReplaceInfo;
import com.appliedolap.essbase.client.model.Transform;
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
 * Field
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-23T11:48:17.898-07:00[America/Los_Angeles]")
public class Field {
  public static final String SERIALIZED_NAME_REJECT_FILTERS = "rejectFilters";
  @SerializedName(SERIALIZED_NAME_REJECT_FILTERS)
  private List<Filter> rejectFilters = null;

  public static final String SERIALIZED_NAME_SELECT_FILTERS = "selectFilters";
  @SerializedName(SERIALIZED_NAME_SELECT_FILTERS)
  private List<Filter> selectFilters = null;

  public static final String SERIALIZED_NAME_REPLACE_INFORMATION = "replaceInformation";
  @SerializedName(SERIALIZED_NAME_REPLACE_INFORMATION)
  private List<ReplaceInfo> replaceInformation = null;

  /**
   * Gets or Sets selectFilterJoinOption
   */
  @JsonAdapter(SelectFilterJoinOptionEnum.Adapter.class)
  public enum SelectFilterJoinOptionEnum {
    AND("AND"),
    
    OR("OR");

    private String value;

    SelectFilterJoinOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SelectFilterJoinOptionEnum fromValue(String value) {
      for (SelectFilterJoinOptionEnum b : SelectFilterJoinOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<SelectFilterJoinOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SelectFilterJoinOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SelectFilterJoinOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return SelectFilterJoinOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SELECT_FILTER_JOIN_OPTION = "selectFilterJoinOption";
  @SerializedName(SERIALIZED_NAME_SELECT_FILTER_JOIN_OPTION)
  private SelectFilterJoinOptionEnum selectFilterJoinOption;

  /**
   * Gets or Sets rejectFilterJoinOption
   */
  @JsonAdapter(RejectFilterJoinOptionEnum.Adapter.class)
  public enum RejectFilterJoinOptionEnum {
    AND("AND"),
    
    OR("OR");

    private String value;

    RejectFilterJoinOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static RejectFilterJoinOptionEnum fromValue(String value) {
      for (RejectFilterJoinOptionEnum b : RejectFilterJoinOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<RejectFilterJoinOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final RejectFilterJoinOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public RejectFilterJoinOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return RejectFilterJoinOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_REJECT_FILTER_JOIN_OPTION = "rejectFilterJoinOption";
  @SerializedName(SERIALIZED_NAME_REJECT_FILTER_JOIN_OPTION)
  private RejectFilterJoinOptionEnum rejectFilterJoinOption;

  public static final String SERIALIZED_NAME_DATE_FORMAT = "dateFormat";
  @SerializedName(SERIALIZED_NAME_DATE_FORMAT)
  private String dateFormat;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PREFIX = "prefix";
  @SerializedName(SERIALIZED_NAME_PREFIX)
  private String prefix;

  public static final String SERIALIZED_NAME_SUFFIX = "suffix";
  @SerializedName(SERIALIZED_NAME_SUFFIX)
  private String suffix;

  public static final String SERIALIZED_NAME_OPTION = "option";
  @SerializedName(SERIALIZED_NAME_OPTION)
  private byte[] option;

  public static final String SERIALIZED_NAME_CONVERT_SPACE_TO_UNDER_SCORE = "convertSpaceToUnderScore";
  @SerializedName(SERIALIZED_NAME_CONVERT_SPACE_TO_UNDER_SCORE)
  private Boolean convertSpaceToUnderScore;

  public static final String SERIALIZED_NAME_TRIM = "trim";
  @SerializedName(SERIALIZED_NAME_TRIM)
  private Boolean trim;

  public static final String SERIALIZED_NAME_WIDTH = "width";
  @SerializedName(SERIALIZED_NAME_WIDTH)
  private Double width;

  public static final String SERIALIZED_NAME_SMART_LIST = "smartList";
  @SerializedName(SERIALIZED_NAME_SMART_LIST)
  private String smartList;

  public static final String SERIALIZED_NAME_DIMENSION_BUILD_OPTIONS = "dimensionBuildOptions";
  @SerializedName(SERIALIZED_NAME_DIMENSION_BUILD_OPTIONS)
  private FieldDimBuildOptions dimensionBuildOptions;

  public static final String SERIALIZED_NAME_DATALOAD_OPTIONS = "dataloadOptions";
  @SerializedName(SERIALIZED_NAME_DATALOAD_OPTIONS)
  private FieldDataLoadOptions dataloadOptions;

  public static final String SERIALIZED_NAME_TRANSFORM = "transform";
  @SerializedName(SERIALIZED_NAME_TRANSFORM)
  private Transform transform;

  /**
   * Gets or Sets _case
   */
  @JsonAdapter(CaseEnum.Adapter.class)
  public enum CaseEnum {
    NOOP("NOOP"),
    
    LOWER_CASE("LOWER_CASE"),
    
    UPPER_CASE("UPPER_CASE"),
    
    FIRST_CAPITAL_CASE("FIRST_CAPITAL_CASE");

    private String value;

    CaseEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CaseEnum fromValue(String value) {
      for (CaseEnum b : CaseEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CaseEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CaseEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CaseEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CaseEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CASE = "case";
  @SerializedName(SERIALIZED_NAME_CASE)
  private CaseEnum _case;


  public Field rejectFilters(List<Filter> rejectFilters) {
    
    this.rejectFilters = rejectFilters;
    return this;
  }

  public Field addRejectFiltersItem(Filter rejectFiltersItem) {
    if (this.rejectFilters == null) {
      this.rejectFilters = new ArrayList<Filter>();
    }
    this.rejectFilters.add(rejectFiltersItem);
    return this;
  }

   /**
   * Get rejectFilters
   * @return rejectFilters
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Filter> getRejectFilters() {
    return rejectFilters;
  }


  public void setRejectFilters(List<Filter> rejectFilters) {
    this.rejectFilters = rejectFilters;
  }


  public Field selectFilters(List<Filter> selectFilters) {
    
    this.selectFilters = selectFilters;
    return this;
  }

  public Field addSelectFiltersItem(Filter selectFiltersItem) {
    if (this.selectFilters == null) {
      this.selectFilters = new ArrayList<Filter>();
    }
    this.selectFilters.add(selectFiltersItem);
    return this;
  }

   /**
   * Get selectFilters
   * @return selectFilters
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Filter> getSelectFilters() {
    return selectFilters;
  }


  public void setSelectFilters(List<Filter> selectFilters) {
    this.selectFilters = selectFilters;
  }


  public Field replaceInformation(List<ReplaceInfo> replaceInformation) {
    
    this.replaceInformation = replaceInformation;
    return this;
  }

  public Field addReplaceInformationItem(ReplaceInfo replaceInformationItem) {
    if (this.replaceInformation == null) {
      this.replaceInformation = new ArrayList<ReplaceInfo>();
    }
    this.replaceInformation.add(replaceInformationItem);
    return this;
  }

   /**
   * Get replaceInformation
   * @return replaceInformation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ReplaceInfo> getReplaceInformation() {
    return replaceInformation;
  }


  public void setReplaceInformation(List<ReplaceInfo> replaceInformation) {
    this.replaceInformation = replaceInformation;
  }


  public Field selectFilterJoinOption(SelectFilterJoinOptionEnum selectFilterJoinOption) {
    
    this.selectFilterJoinOption = selectFilterJoinOption;
    return this;
  }

   /**
   * Get selectFilterJoinOption
   * @return selectFilterJoinOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SelectFilterJoinOptionEnum getSelectFilterJoinOption() {
    return selectFilterJoinOption;
  }


  public void setSelectFilterJoinOption(SelectFilterJoinOptionEnum selectFilterJoinOption) {
    this.selectFilterJoinOption = selectFilterJoinOption;
  }


  public Field rejectFilterJoinOption(RejectFilterJoinOptionEnum rejectFilterJoinOption) {
    
    this.rejectFilterJoinOption = rejectFilterJoinOption;
    return this;
  }

   /**
   * Get rejectFilterJoinOption
   * @return rejectFilterJoinOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public RejectFilterJoinOptionEnum getRejectFilterJoinOption() {
    return rejectFilterJoinOption;
  }


  public void setRejectFilterJoinOption(RejectFilterJoinOptionEnum rejectFilterJoinOption) {
    this.rejectFilterJoinOption = rejectFilterJoinOption;
  }


  public Field dateFormat(String dateFormat) {
    
    this.dateFormat = dateFormat;
    return this;
  }

   /**
   * Get dateFormat
   * @return dateFormat
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDateFormat() {
    return dateFormat;
  }


  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }


  public Field name(String name) {
    
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


  public Field prefix(String prefix) {
    
    this.prefix = prefix;
    return this;
  }

   /**
   * Get prefix
   * @return prefix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPrefix() {
    return prefix;
  }


  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }


  public Field suffix(String suffix) {
    
    this.suffix = suffix;
    return this;
  }

   /**
   * Get suffix
   * @return suffix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSuffix() {
    return suffix;
  }


  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }


  public Field option(byte[] option) {
    
    this.option = option;
    return this;
  }

   /**
   * Get option
   * @return option
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public byte[] getOption() {
    return option;
  }


  public void setOption(byte[] option) {
    this.option = option;
  }


  public Field convertSpaceToUnderScore(Boolean convertSpaceToUnderScore) {
    
    this.convertSpaceToUnderScore = convertSpaceToUnderScore;
    return this;
  }

   /**
   * Get convertSpaceToUnderScore
   * @return convertSpaceToUnderScore
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getConvertSpaceToUnderScore() {
    return convertSpaceToUnderScore;
  }


  public void setConvertSpaceToUnderScore(Boolean convertSpaceToUnderScore) {
    this.convertSpaceToUnderScore = convertSpaceToUnderScore;
  }


  public Field trim(Boolean trim) {
    
    this.trim = trim;
    return this;
  }

   /**
   * Get trim
   * @return trim
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTrim() {
    return trim;
  }


  public void setTrim(Boolean trim) {
    this.trim = trim;
  }


  public Field width(Double width) {
    
    this.width = width;
    return this;
  }

   /**
   * Get width
   * @return width
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getWidth() {
    return width;
  }


  public void setWidth(Double width) {
    this.width = width;
  }


  public Field smartList(String smartList) {
    
    this.smartList = smartList;
    return this;
  }

   /**
   * Get smartList
   * @return smartList
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSmartList() {
    return smartList;
  }


  public void setSmartList(String smartList) {
    this.smartList = smartList;
  }


  public Field dimensionBuildOptions(FieldDimBuildOptions dimensionBuildOptions) {
    
    this.dimensionBuildOptions = dimensionBuildOptions;
    return this;
  }

   /**
   * Get dimensionBuildOptions
   * @return dimensionBuildOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public FieldDimBuildOptions getDimensionBuildOptions() {
    return dimensionBuildOptions;
  }


  public void setDimensionBuildOptions(FieldDimBuildOptions dimensionBuildOptions) {
    this.dimensionBuildOptions = dimensionBuildOptions;
  }


  public Field dataloadOptions(FieldDataLoadOptions dataloadOptions) {
    
    this.dataloadOptions = dataloadOptions;
    return this;
  }

   /**
   * Get dataloadOptions
   * @return dataloadOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public FieldDataLoadOptions getDataloadOptions() {
    return dataloadOptions;
  }


  public void setDataloadOptions(FieldDataLoadOptions dataloadOptions) {
    this.dataloadOptions = dataloadOptions;
  }


  public Field transform(Transform transform) {
    
    this.transform = transform;
    return this;
  }

   /**
   * Get transform
   * @return transform
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Transform getTransform() {
    return transform;
  }


  public void setTransform(Transform transform) {
    this.transform = transform;
  }


  public Field _case(CaseEnum _case) {
    
    this._case = _case;
    return this;
  }

   /**
   * Get _case
   * @return _case
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CaseEnum getCase() {
    return _case;
  }


  public void setCase(CaseEnum _case) {
    this._case = _case;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Field field = (Field) o;
    return Objects.equals(this.rejectFilters, field.rejectFilters) &&
        Objects.equals(this.selectFilters, field.selectFilters) &&
        Objects.equals(this.replaceInformation, field.replaceInformation) &&
        Objects.equals(this.selectFilterJoinOption, field.selectFilterJoinOption) &&
        Objects.equals(this.rejectFilterJoinOption, field.rejectFilterJoinOption) &&
        Objects.equals(this.dateFormat, field.dateFormat) &&
        Objects.equals(this.name, field.name) &&
        Objects.equals(this.prefix, field.prefix) &&
        Objects.equals(this.suffix, field.suffix) &&
        Arrays.equals(this.option, field.option) &&
        Objects.equals(this.convertSpaceToUnderScore, field.convertSpaceToUnderScore) &&
        Objects.equals(this.trim, field.trim) &&
        Objects.equals(this.width, field.width) &&
        Objects.equals(this.smartList, field.smartList) &&
        Objects.equals(this.dimensionBuildOptions, field.dimensionBuildOptions) &&
        Objects.equals(this.dataloadOptions, field.dataloadOptions) &&
        Objects.equals(this.transform, field.transform) &&
        Objects.equals(this._case, field._case);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rejectFilters, selectFilters, replaceInformation, selectFilterJoinOption, rejectFilterJoinOption, dateFormat, name, prefix, suffix, Arrays.hashCode(option), convertSpaceToUnderScore, trim, width, smartList, dimensionBuildOptions, dataloadOptions, transform, _case);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Field {\n");
    sb.append("    rejectFilters: ").append(toIndentedString(rejectFilters)).append("\n");
    sb.append("    selectFilters: ").append(toIndentedString(selectFilters)).append("\n");
    sb.append("    replaceInformation: ").append(toIndentedString(replaceInformation)).append("\n");
    sb.append("    selectFilterJoinOption: ").append(toIndentedString(selectFilterJoinOption)).append("\n");
    sb.append("    rejectFilterJoinOption: ").append(toIndentedString(rejectFilterJoinOption)).append("\n");
    sb.append("    dateFormat: ").append(toIndentedString(dateFormat)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    prefix: ").append(toIndentedString(prefix)).append("\n");
    sb.append("    suffix: ").append(toIndentedString(suffix)).append("\n");
    sb.append("    option: ").append(toIndentedString(option)).append("\n");
    sb.append("    convertSpaceToUnderScore: ").append(toIndentedString(convertSpaceToUnderScore)).append("\n");
    sb.append("    trim: ").append(toIndentedString(trim)).append("\n");
    sb.append("    width: ").append(toIndentedString(width)).append("\n");
    sb.append("    smartList: ").append(toIndentedString(smartList)).append("\n");
    sb.append("    dimensionBuildOptions: ").append(toIndentedString(dimensionBuildOptions)).append("\n");
    sb.append("    dataloadOptions: ").append(toIndentedString(dataloadOptions)).append("\n");
    sb.append("    transform: ").append(toIndentedString(transform)).append("\n");
    sb.append("    _case: ").append(toIndentedString(_case)).append("\n");
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

