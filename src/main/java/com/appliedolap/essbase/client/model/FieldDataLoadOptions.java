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
 * FieldDataLoadOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class FieldDataLoadOptions {
  public static final String SERIALIZED_NAME_REFER_DIMENSION = "referDimension";
  @SerializedName(SERIALIZED_NAME_REFER_DIMENSION)
  private String referDimension;

  public static final String SERIALIZED_NAME_REFER_NUMBER = "referNumber";
  @SerializedName(SERIALIZED_NAME_REFER_NUMBER)
  private Integer referNumber;

  public static final String SERIALIZED_NAME_REFER_OPTION = "referOption";
  @SerializedName(SERIALIZED_NAME_REFER_OPTION)
  private Integer referOption;

  public static final String SERIALIZED_NAME_IGNORE = "ignore";
  @SerializedName(SERIALIZED_NAME_IGNORE)
  private Boolean ignore;

  public static final String SERIALIZED_NAME_SCALE = "scale";
  @SerializedName(SERIALIZED_NAME_SCALE)
  private Boolean scale;

  public static final String SERIALIZED_NAME_USE_REFERENCE = "useReference";
  @SerializedName(SERIALIZED_NAME_USE_REFERENCE)
  private Boolean useReference;

  public static final String SERIALIZED_NAME_DATA = "data";
  @SerializedName(SERIALIZED_NAME_DATA)
  private Boolean data;

  public static final String SERIALIZED_NAME_SCALINGFACTOR = "scalingfactor";
  @SerializedName(SERIALIZED_NAME_SCALINGFACTOR)
  private Double scalingfactor;

  /**
   * Gets or Sets storeType
   */
  @JsonAdapter(StoreTypeEnum.Adapter.class)
  public enum StoreTypeEnum {
    MIN("MIN"),
    
    MAX("MAX"),
    
    AVG("AVG"),
    
    SUM("SUM"),
    
    COUNT("COUNT");

    private String value;

    StoreTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StoreTypeEnum fromValue(String value) {
      for (StoreTypeEnum b : StoreTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StoreTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StoreTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StoreTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StoreTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STORE_TYPE = "storeType";
  @SerializedName(SERIALIZED_NAME_STORE_TYPE)
  private StoreTypeEnum storeType;

  public static final String SERIALIZED_NAME_STORE_TYPE_COUNT_MISSING = "storeTypeCountMissing";
  @SerializedName(SERIALIZED_NAME_STORE_TYPE_COUNT_MISSING)
  private Boolean storeTypeCountMissing;


  public FieldDataLoadOptions referDimension(String referDimension) {
    
    this.referDimension = referDimension;
    return this;
  }

   /**
   * Get referDimension
   * @return referDimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getReferDimension() {
    return referDimension;
  }


  public void setReferDimension(String referDimension) {
    this.referDimension = referDimension;
  }


  public FieldDataLoadOptions referNumber(Integer referNumber) {
    
    this.referNumber = referNumber;
    return this;
  }

   /**
   * Get referNumber
   * @return referNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getReferNumber() {
    return referNumber;
  }


  public void setReferNumber(Integer referNumber) {
    this.referNumber = referNumber;
  }


  public FieldDataLoadOptions referOption(Integer referOption) {
    
    this.referOption = referOption;
    return this;
  }

   /**
   * Get referOption
   * @return referOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getReferOption() {
    return referOption;
  }


  public void setReferOption(Integer referOption) {
    this.referOption = referOption;
  }


  public FieldDataLoadOptions ignore(Boolean ignore) {
    
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


  public FieldDataLoadOptions scale(Boolean scale) {
    
    this.scale = scale;
    return this;
  }

   /**
   * Get scale
   * @return scale
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getScale() {
    return scale;
  }


  public void setScale(Boolean scale) {
    this.scale = scale;
  }


  public FieldDataLoadOptions useReference(Boolean useReference) {
    
    this.useReference = useReference;
    return this;
  }

   /**
   * Get useReference
   * @return useReference
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getUseReference() {
    return useReference;
  }


  public void setUseReference(Boolean useReference) {
    this.useReference = useReference;
  }


  public FieldDataLoadOptions data(Boolean data) {
    
    this.data = data;
    return this;
  }

   /**
   * Get data
   * @return data
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getData() {
    return data;
  }


  public void setData(Boolean data) {
    this.data = data;
  }


  public FieldDataLoadOptions scalingfactor(Double scalingfactor) {
    
    this.scalingfactor = scalingfactor;
    return this;
  }

   /**
   * Get scalingfactor
   * @return scalingfactor
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getScalingfactor() {
    return scalingfactor;
  }


  public void setScalingfactor(Double scalingfactor) {
    this.scalingfactor = scalingfactor;
  }


  public FieldDataLoadOptions storeType(StoreTypeEnum storeType) {
    
    this.storeType = storeType;
    return this;
  }

   /**
   * Get storeType
   * @return storeType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public StoreTypeEnum getStoreType() {
    return storeType;
  }


  public void setStoreType(StoreTypeEnum storeType) {
    this.storeType = storeType;
  }


  public FieldDataLoadOptions storeTypeCountMissing(Boolean storeTypeCountMissing) {
    
    this.storeTypeCountMissing = storeTypeCountMissing;
    return this;
  }

   /**
   * Get storeTypeCountMissing
   * @return storeTypeCountMissing
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getStoreTypeCountMissing() {
    return storeTypeCountMissing;
  }


  public void setStoreTypeCountMissing(Boolean storeTypeCountMissing) {
    this.storeTypeCountMissing = storeTypeCountMissing;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldDataLoadOptions fieldDataLoadOptions = (FieldDataLoadOptions) o;
    return Objects.equals(this.referDimension, fieldDataLoadOptions.referDimension) &&
        Objects.equals(this.referNumber, fieldDataLoadOptions.referNumber) &&
        Objects.equals(this.referOption, fieldDataLoadOptions.referOption) &&
        Objects.equals(this.ignore, fieldDataLoadOptions.ignore) &&
        Objects.equals(this.scale, fieldDataLoadOptions.scale) &&
        Objects.equals(this.useReference, fieldDataLoadOptions.useReference) &&
        Objects.equals(this.data, fieldDataLoadOptions.data) &&
        Objects.equals(this.scalingfactor, fieldDataLoadOptions.scalingfactor) &&
        Objects.equals(this.storeType, fieldDataLoadOptions.storeType) &&
        Objects.equals(this.storeTypeCountMissing, fieldDataLoadOptions.storeTypeCountMissing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(referDimension, referNumber, referOption, ignore, scale, useReference, data, scalingfactor, storeType, storeTypeCountMissing);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FieldDataLoadOptions {\n");
    sb.append("    referDimension: ").append(toIndentedString(referDimension)).append("\n");
    sb.append("    referNumber: ").append(toIndentedString(referNumber)).append("\n");
    sb.append("    referOption: ").append(toIndentedString(referOption)).append("\n");
    sb.append("    ignore: ").append(toIndentedString(ignore)).append("\n");
    sb.append("    scale: ").append(toIndentedString(scale)).append("\n");
    sb.append("    useReference: ").append(toIndentedString(useReference)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    scalingfactor: ").append(toIndentedString(scalingfactor)).append("\n");
    sb.append("    storeType: ").append(toIndentedString(storeType)).append("\n");
    sb.append("    storeTypeCountMissing: ").append(toIndentedString(storeTypeCountMissing)).append("\n");
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

