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
 * MeasureOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class MeasureOptions {
  public static final String SERIALIZED_NAME_CURRENCY_CATEGORY = "currencyCategory";
  @SerializedName(SERIALIZED_NAME_CURRENCY_CATEGORY)
  private String currencyCategory;

  public static final String SERIALIZED_NAME_CURRENCY_NAME = "currencyName";
  @SerializedName(SERIALIZED_NAME_CURRENCY_NAME)
  private String currencyName;

  /**
   * Gets or Sets currencyConversion
   */
  @JsonAdapter(CurrencyConversionEnum.Adapter.class)
  public enum CurrencyConversionEnum {
    EXISTING("EXISTING"),
    
    NONE("NONE"),
    
    CATEGORY("CATEGORY"),
    
    NO_CONVERSION("NO_CONVERSION");

    private String value;

    CurrencyConversionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CurrencyConversionEnum fromValue(String value) {
      for (CurrencyConversionEnum b : CurrencyConversionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CurrencyConversionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CurrencyConversionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CurrencyConversionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CurrencyConversionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CURRENCY_CONVERSION = "currencyConversion";
  @SerializedName(SERIALIZED_NAME_CURRENCY_CONVERSION)
  private CurrencyConversionEnum currencyConversion;

  /**
   * Gets or Sets skip
   */
  @JsonAdapter(SkipEnum.Adapter.class)
  public enum SkipEnum {
    NONE("NONE"),
    
    NA("NA"),
    
    MISSING("MISSING"),
    
    ZERO("ZERO"),
    
    MISSING_ZERO("MISSING_ZERO");

    private String value;

    SkipEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SkipEnum fromValue(String value) {
      for (SkipEnum b : SkipEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<SkipEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SkipEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SkipEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return SkipEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SKIP = "skip";
  @SerializedName(SERIALIZED_NAME_SKIP)
  private SkipEnum skip;

  /**
   * Gets or Sets timeBalanceOption
   */
  @JsonAdapter(TimeBalanceOptionEnum.Adapter.class)
  public enum TimeBalanceOptionEnum {
    EXISTING("EXISTING"),
    
    NONE("NONE"),
    
    FIRST("FIRST"),
    
    LAST("LAST"),
    
    AVERAGE("AVERAGE");

    private String value;

    TimeBalanceOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TimeBalanceOptionEnum fromValue(String value) {
      for (TimeBalanceOptionEnum b : TimeBalanceOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TimeBalanceOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TimeBalanceOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TimeBalanceOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TimeBalanceOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TIME_BALANCE_OPTION = "timeBalanceOption";
  @SerializedName(SERIALIZED_NAME_TIME_BALANCE_OPTION)
  private TimeBalanceOptionEnum timeBalanceOption;

  /**
   * Gets or Sets varianceReporting
   */
  @JsonAdapter(VarianceReportingEnum.Adapter.class)
  public enum VarianceReportingEnum {
    EXISTING("EXISTING"),
    
    NON_EXPENSE("NON_EXPENSE"),
    
    EXPENSE("EXPENSE");

    private String value;

    VarianceReportingEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static VarianceReportingEnum fromValue(String value) {
      for (VarianceReportingEnum b : VarianceReportingEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<VarianceReportingEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final VarianceReportingEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public VarianceReportingEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return VarianceReportingEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_VARIANCE_REPORTING = "varianceReporting";
  @SerializedName(SERIALIZED_NAME_VARIANCE_REPORTING)
  private VarianceReportingEnum varianceReporting;

  public static final String SERIALIZED_NAME_TWO_PASS = "twoPass";
  @SerializedName(SERIALIZED_NAME_TWO_PASS)
  private Boolean twoPass;


  public MeasureOptions currencyCategory(String currencyCategory) {
    
    this.currencyCategory = currencyCategory;
    return this;
  }

   /**
   * Get currencyCategory
   * @return currencyCategory
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCurrencyCategory() {
    return currencyCategory;
  }


  public void setCurrencyCategory(String currencyCategory) {
    this.currencyCategory = currencyCategory;
  }


  public MeasureOptions currencyName(String currencyName) {
    
    this.currencyName = currencyName;
    return this;
  }

   /**
   * Get currencyName
   * @return currencyName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCurrencyName() {
    return currencyName;
  }


  public void setCurrencyName(String currencyName) {
    this.currencyName = currencyName;
  }


  public MeasureOptions currencyConversion(CurrencyConversionEnum currencyConversion) {
    
    this.currencyConversion = currencyConversion;
    return this;
  }

   /**
   * Get currencyConversion
   * @return currencyConversion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CurrencyConversionEnum getCurrencyConversion() {
    return currencyConversion;
  }


  public void setCurrencyConversion(CurrencyConversionEnum currencyConversion) {
    this.currencyConversion = currencyConversion;
  }


  public MeasureOptions skip(SkipEnum skip) {
    
    this.skip = skip;
    return this;
  }

   /**
   * Get skip
   * @return skip
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SkipEnum getSkip() {
    return skip;
  }


  public void setSkip(SkipEnum skip) {
    this.skip = skip;
  }


  public MeasureOptions timeBalanceOption(TimeBalanceOptionEnum timeBalanceOption) {
    
    this.timeBalanceOption = timeBalanceOption;
    return this;
  }

   /**
   * Get timeBalanceOption
   * @return timeBalanceOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TimeBalanceOptionEnum getTimeBalanceOption() {
    return timeBalanceOption;
  }


  public void setTimeBalanceOption(TimeBalanceOptionEnum timeBalanceOption) {
    this.timeBalanceOption = timeBalanceOption;
  }


  public MeasureOptions varianceReporting(VarianceReportingEnum varianceReporting) {
    
    this.varianceReporting = varianceReporting;
    return this;
  }

   /**
   * Get varianceReporting
   * @return varianceReporting
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public VarianceReportingEnum getVarianceReporting() {
    return varianceReporting;
  }


  public void setVarianceReporting(VarianceReportingEnum varianceReporting) {
    this.varianceReporting = varianceReporting;
  }


  public MeasureOptions twoPass(Boolean twoPass) {
    
    this.twoPass = twoPass;
    return this;
  }

   /**
   * Get twoPass
   * @return twoPass
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTwoPass() {
    return twoPass;
  }


  public void setTwoPass(Boolean twoPass) {
    this.twoPass = twoPass;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MeasureOptions measureOptions = (MeasureOptions) o;
    return Objects.equals(this.currencyCategory, measureOptions.currencyCategory) &&
        Objects.equals(this.currencyName, measureOptions.currencyName) &&
        Objects.equals(this.currencyConversion, measureOptions.currencyConversion) &&
        Objects.equals(this.skip, measureOptions.skip) &&
        Objects.equals(this.timeBalanceOption, measureOptions.timeBalanceOption) &&
        Objects.equals(this.varianceReporting, measureOptions.varianceReporting) &&
        Objects.equals(this.twoPass, measureOptions.twoPass);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currencyCategory, currencyName, currencyConversion, skip, timeBalanceOption, varianceReporting, twoPass);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MeasureOptions {\n");
    sb.append("    currencyCategory: ").append(toIndentedString(currencyCategory)).append("\n");
    sb.append("    currencyName: ").append(toIndentedString(currencyName)).append("\n");
    sb.append("    currencyConversion: ").append(toIndentedString(currencyConversion)).append("\n");
    sb.append("    skip: ").append(toIndentedString(skip)).append("\n");
    sb.append("    timeBalanceOption: ").append(toIndentedString(timeBalanceOption)).append("\n");
    sb.append("    varianceReporting: ").append(toIndentedString(varianceReporting)).append("\n");
    sb.append("    twoPass: ").append(toIndentedString(twoPass)).append("\n");
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

