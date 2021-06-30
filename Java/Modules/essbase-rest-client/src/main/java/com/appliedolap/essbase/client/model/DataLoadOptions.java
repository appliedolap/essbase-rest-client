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
 * DataLoadOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-30T09:44:26.987-07:00[America/Los_Angeles]")
public class DataLoadOptions {
  public static final String SERIALIZED_NAME_CLEAR_COMBINATIONS = "clearCombinations";
  @SerializedName(SERIALIZED_NAME_CLEAR_COMBINATIONS)
  private List<String> clearCombinations = null;

  /**
   * Gets or Sets option
   */
  @JsonAdapter(OptionEnum.Adapter.class)
  public enum OptionEnum {
    NONE("NONE"),
    
    OVERWRITE("OVERWRITE"),
    
    ADD("ADD"),
    
    SUBTRACT("SUBTRACT");

    private String value;

    OptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static OptionEnum fromValue(String value) {
      for (OptionEnum b : OptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<OptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final OptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public OptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return OptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_OPTION = "option";
  @SerializedName(SERIALIZED_NAME_OPTION)
  private OptionEnum option;

  public static final String SERIALIZED_NAME_REMOVE_ALL = "removeAll";
  @SerializedName(SERIALIZED_NAME_REMOVE_ALL)
  private Integer removeAll;

  public static final String SERIALIZED_NAME_SIGN_FLIP_DIMENSION = "signFlipDimension";
  @SerializedName(SERIALIZED_NAME_SIGN_FLIP_DIMENSION)
  private String signFlipDimension;

  public static final String SERIALIZED_NAME_SIGN_FLIP_U_D_A = "signFlipUDA";
  @SerializedName(SERIALIZED_NAME_SIGN_FLIP_U_D_A)
  private String signFlipUDA;


  public DataLoadOptions clearCombinations(List<String> clearCombinations) {
    
    this.clearCombinations = clearCombinations;
    return this;
  }

  public DataLoadOptions addClearCombinationsItem(String clearCombinationsItem) {
    if (this.clearCombinations == null) {
      this.clearCombinations = new ArrayList<String>();
    }
    this.clearCombinations.add(clearCombinationsItem);
    return this;
  }

   /**
   * Get clearCombinations
   * @return clearCombinations
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getClearCombinations() {
    return clearCombinations;
  }


  public void setClearCombinations(List<String> clearCombinations) {
    this.clearCombinations = clearCombinations;
  }


  public DataLoadOptions option(OptionEnum option) {
    
    this.option = option;
    return this;
  }

   /**
   * Get option
   * @return option
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public OptionEnum getOption() {
    return option;
  }


  public void setOption(OptionEnum option) {
    this.option = option;
  }


  public DataLoadOptions removeAll(Integer removeAll) {
    
    this.removeAll = removeAll;
    return this;
  }

   /**
   * Get removeAll
   * @return removeAll
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRemoveAll() {
    return removeAll;
  }


  public void setRemoveAll(Integer removeAll) {
    this.removeAll = removeAll;
  }


  public DataLoadOptions signFlipDimension(String signFlipDimension) {
    
    this.signFlipDimension = signFlipDimension;
    return this;
  }

   /**
   * Get signFlipDimension
   * @return signFlipDimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSignFlipDimension() {
    return signFlipDimension;
  }


  public void setSignFlipDimension(String signFlipDimension) {
    this.signFlipDimension = signFlipDimension;
  }


  public DataLoadOptions signFlipUDA(String signFlipUDA) {
    
    this.signFlipUDA = signFlipUDA;
    return this;
  }

   /**
   * Get signFlipUDA
   * @return signFlipUDA
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSignFlipUDA() {
    return signFlipUDA;
  }


  public void setSignFlipUDA(String signFlipUDA) {
    this.signFlipUDA = signFlipUDA;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataLoadOptions dataLoadOptions = (DataLoadOptions) o;
    return Objects.equals(this.clearCombinations, dataLoadOptions.clearCombinations) &&
        Objects.equals(this.option, dataLoadOptions.option) &&
        Objects.equals(this.removeAll, dataLoadOptions.removeAll) &&
        Objects.equals(this.signFlipDimension, dataLoadOptions.signFlipDimension) &&
        Objects.equals(this.signFlipUDA, dataLoadOptions.signFlipUDA);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clearCombinations, option, removeAll, signFlipDimension, signFlipUDA);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataLoadOptions {\n");
    sb.append("    clearCombinations: ").append(toIndentedString(clearCombinations)).append("\n");
    sb.append("    option: ").append(toIndentedString(option)).append("\n");
    sb.append("    removeAll: ").append(toIndentedString(removeAll)).append("\n");
    sb.append("    signFlipDimension: ").append(toIndentedString(signFlipDimension)).append("\n");
    sb.append("    signFlipUDA: ").append(toIndentedString(signFlipUDA)).append("\n");
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

