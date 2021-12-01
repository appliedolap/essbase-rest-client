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
import com.appliedolap.essbase.client.model.JAXBElementObject;
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
 * OtlEditMain
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class OtlEditMain {
  public static final String SERIALIZED_NAME_EDIT_ACTIONS = "editActions";
  @SerializedName(SERIALIZED_NAME_EDIT_ACTIONS)
  private List<JAXBElementObject> editActions = null;

  public static final String SERIALIZED_NAME_OTL_VERSION = "otlVersion";
  @SerializedName(SERIALIZED_NAME_OTL_VERSION)
  private Integer otlVersion;

  public static final String SERIALIZED_NAME_VALIDATE = "validate";
  @SerializedName(SERIALIZED_NAME_VALIDATE)
  private Boolean validate;

  public static final String SERIALIZED_NAME_VALIDATE_FORMULAS = "validateFormulas";
  @SerializedName(SERIALIZED_NAME_VALIDATE_FORMULAS)
  private Boolean validateFormulas;

  public static final String SERIALIZED_NAME_KEEP_TRANSACTION = "keepTransaction";
  @SerializedName(SERIALIZED_NAME_KEEP_TRANSACTION)
  private Boolean keepTransaction;

  /**
   * Gets or Sets restructOption
   */
  @JsonAdapter(RestructOptionEnum.Adapter.class)
  public enum RestructOptionEnum {
    ALL_DATA("ALL_DATA"),
    
    NO_DATA("NO_DATA"),
    
    LOW_DATA("LOW_DATA"),
    
    IN_DATA("IN_DATA");

    private String value;

    RestructOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static RestructOptionEnum fromValue(String value) {
      for (RestructOptionEnum b : RestructOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<RestructOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final RestructOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public RestructOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return RestructOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_RESTRUCT_OPTION = "restructOption";
  @SerializedName(SERIALIZED_NAME_RESTRUCT_OPTION)
  private RestructOptionEnum restructOption;

  public static final String SERIALIZED_NAME_JSONFORMAT_LOG = "jsonformatLog";
  @SerializedName(SERIALIZED_NAME_JSONFORMAT_LOG)
  private Boolean jsonformatLog;


  public OtlEditMain editActions(List<JAXBElementObject> editActions) {
    
    this.editActions = editActions;
    return this;
  }

  public OtlEditMain addEditActionsItem(JAXBElementObject editActionsItem) {
    if (this.editActions == null) {
      this.editActions = new ArrayList<JAXBElementObject>();
    }
    this.editActions.add(editActionsItem);
    return this;
  }

   /**
   * Get editActions
   * @return editActions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<JAXBElementObject> getEditActions() {
    return editActions;
  }


  public void setEditActions(List<JAXBElementObject> editActions) {
    this.editActions = editActions;
  }


  public OtlEditMain otlVersion(Integer otlVersion) {
    
    this.otlVersion = otlVersion;
    return this;
  }

   /**
   * Get otlVersion
   * @return otlVersion
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getOtlVersion() {
    return otlVersion;
  }


  public void setOtlVersion(Integer otlVersion) {
    this.otlVersion = otlVersion;
  }


  public OtlEditMain validate(Boolean validate) {
    
    this.validate = validate;
    return this;
  }

   /**
   * Get validate
   * @return validate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getValidate() {
    return validate;
  }


  public void setValidate(Boolean validate) {
    this.validate = validate;
  }


  public OtlEditMain validateFormulas(Boolean validateFormulas) {
    
    this.validateFormulas = validateFormulas;
    return this;
  }

   /**
   * Get validateFormulas
   * @return validateFormulas
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getValidateFormulas() {
    return validateFormulas;
  }


  public void setValidateFormulas(Boolean validateFormulas) {
    this.validateFormulas = validateFormulas;
  }


  public OtlEditMain keepTransaction(Boolean keepTransaction) {
    
    this.keepTransaction = keepTransaction;
    return this;
  }

   /**
   * Get keepTransaction
   * @return keepTransaction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getKeepTransaction() {
    return keepTransaction;
  }


  public void setKeepTransaction(Boolean keepTransaction) {
    this.keepTransaction = keepTransaction;
  }


  public OtlEditMain restructOption(RestructOptionEnum restructOption) {
    
    this.restructOption = restructOption;
    return this;
  }

   /**
   * Get restructOption
   * @return restructOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public RestructOptionEnum getRestructOption() {
    return restructOption;
  }


  public void setRestructOption(RestructOptionEnum restructOption) {
    this.restructOption = restructOption;
  }


  public OtlEditMain jsonformatLog(Boolean jsonformatLog) {
    
    this.jsonformatLog = jsonformatLog;
    return this;
  }

   /**
   * Get jsonformatLog
   * @return jsonformatLog
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getJsonformatLog() {
    return jsonformatLog;
  }


  public void setJsonformatLog(Boolean jsonformatLog) {
    this.jsonformatLog = jsonformatLog;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OtlEditMain otlEditMain = (OtlEditMain) o;
    return Objects.equals(this.editActions, otlEditMain.editActions) &&
        Objects.equals(this.otlVersion, otlEditMain.otlVersion) &&
        Objects.equals(this.validate, otlEditMain.validate) &&
        Objects.equals(this.validateFormulas, otlEditMain.validateFormulas) &&
        Objects.equals(this.keepTransaction, otlEditMain.keepTransaction) &&
        Objects.equals(this.restructOption, otlEditMain.restructOption) &&
        Objects.equals(this.jsonformatLog, otlEditMain.jsonformatLog);
  }

  @Override
  public int hashCode() {
    return Objects.hash(editActions, otlVersion, validate, validateFormulas, keepTransaction, restructOption, jsonformatLog);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OtlEditMain {\n");
    sb.append("    editActions: ").append(toIndentedString(editActions)).append("\n");
    sb.append("    otlVersion: ").append(toIndentedString(otlVersion)).append("\n");
    sb.append("    validate: ").append(toIndentedString(validate)).append("\n");
    sb.append("    validateFormulas: ").append(toIndentedString(validateFormulas)).append("\n");
    sb.append("    keepTransaction: ").append(toIndentedString(keepTransaction)).append("\n");
    sb.append("    restructOption: ").append(toIndentedString(restructOption)).append("\n");
    sb.append("    jsonformatLog: ").append(toIndentedString(jsonformatLog)).append("\n");
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

