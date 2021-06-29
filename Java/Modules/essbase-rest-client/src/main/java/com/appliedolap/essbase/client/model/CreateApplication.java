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
 * CreateApplication
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class CreateApplication {
  public static final String SERIALIZED_NAME_APPLICATION_NAME = "applicationName";
  @SerializedName(SERIALIZED_NAME_APPLICATION_NAME)
  private String applicationName;

  public static final String SERIALIZED_NAME_DATABASE_NAME = "databaseName";
  @SerializedName(SERIALIZED_NAME_DATABASE_NAME)
  private String databaseName;

  public static final String SERIALIZED_NAME_ALLOW_DUPLICATES = "allowDuplicates";
  @SerializedName(SERIALIZED_NAME_ALLOW_DUPLICATES)
  private Boolean allowDuplicates;

  public static final String SERIALIZED_NAME_ENABLE_SCENARIO = "enableScenario";
  @SerializedName(SERIALIZED_NAME_ENABLE_SCENARIO)
  private Boolean enableScenario;

  public static final String SERIALIZED_NAME_MEMBER_COUNT = "memberCount";
  @SerializedName(SERIALIZED_NAME_MEMBER_COUNT)
  private Integer memberCount;

  public static final String SERIALIZED_NAME_DATABASE_TYPE = "databaseType";
  @SerializedName(SERIALIZED_NAME_DATABASE_TYPE)
  private String databaseType;

  /**
   * Gets or Sets dbType
   */
  @JsonAdapter(DbTypeEnum.Adapter.class)
  public enum DbTypeEnum {
    NORMAL("NORMAL"),
    
    CURRENCY("CURRENCY"),
    
    ASO("ASO");

    private String value;

    DbTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DbTypeEnum fromValue(String value) {
      for (DbTypeEnum b : DbTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DbTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DbTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DbTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DbTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DB_TYPE = "dbType";
  @SerializedName(SERIALIZED_NAME_DB_TYPE)
  private DbTypeEnum dbType;

  /**
   * Gets or Sets appType
   */
  @JsonAdapter(AppTypeEnum.Adapter.class)
  public enum AppTypeEnum {
    NATIVE("NATIVE"),
    
    UTF8("UTF8");

    private String value;

    AppTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AppTypeEnum fromValue(String value) {
      for (AppTypeEnum b : AppTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AppTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AppTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AppTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AppTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_APP_TYPE = "appType";
  @SerializedName(SERIALIZED_NAME_APP_TYPE)
  private AppTypeEnum appType;

  public static final String SERIALIZED_NAME_MEMBER_PREFIX = "memberPrefix";
  @SerializedName(SERIALIZED_NAME_MEMBER_PREFIX)
  private String memberPrefix;


  public CreateApplication applicationName(String applicationName) {
    
    this.applicationName = applicationName;
    return this;
  }

   /**
   * Get applicationName
   * @return applicationName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplicationName() {
    return applicationName;
  }


  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }


  public CreateApplication databaseName(String databaseName) {
    
    this.databaseName = databaseName;
    return this;
  }

   /**
   * Get databaseName
   * @return databaseName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabaseName() {
    return databaseName;
  }


  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }


  public CreateApplication allowDuplicates(Boolean allowDuplicates) {
    
    this.allowDuplicates = allowDuplicates;
    return this;
  }

   /**
   * Get allowDuplicates
   * @return allowDuplicates
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowDuplicates() {
    return allowDuplicates;
  }


  public void setAllowDuplicates(Boolean allowDuplicates) {
    this.allowDuplicates = allowDuplicates;
  }


  public CreateApplication enableScenario(Boolean enableScenario) {
    
    this.enableScenario = enableScenario;
    return this;
  }

   /**
   * Get enableScenario
   * @return enableScenario
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getEnableScenario() {
    return enableScenario;
  }


  public void setEnableScenario(Boolean enableScenario) {
    this.enableScenario = enableScenario;
  }


  public CreateApplication memberCount(Integer memberCount) {
    
    this.memberCount = memberCount;
    return this;
  }

   /**
   * Get memberCount
   * @return memberCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getMemberCount() {
    return memberCount;
  }


  public void setMemberCount(Integer memberCount) {
    this.memberCount = memberCount;
  }


  public CreateApplication databaseType(String databaseType) {
    
    this.databaseType = databaseType;
    return this;
  }

   /**
   * Get databaseType
   * @return databaseType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabaseType() {
    return databaseType;
  }


  public void setDatabaseType(String databaseType) {
    this.databaseType = databaseType;
  }


  public CreateApplication dbType(DbTypeEnum dbType) {
    
    this.dbType = dbType;
    return this;
  }

   /**
   * Get dbType
   * @return dbType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DbTypeEnum getDbType() {
    return dbType;
  }


  public void setDbType(DbTypeEnum dbType) {
    this.dbType = dbType;
  }


  public CreateApplication appType(AppTypeEnum appType) {
    
    this.appType = appType;
    return this;
  }

   /**
   * Get appType
   * @return appType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AppTypeEnum getAppType() {
    return appType;
  }


  public void setAppType(AppTypeEnum appType) {
    this.appType = appType;
  }


  public CreateApplication memberPrefix(String memberPrefix) {
    
    this.memberPrefix = memberPrefix;
    return this;
  }

   /**
   * Get memberPrefix
   * @return memberPrefix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMemberPrefix() {
    return memberPrefix;
  }


  public void setMemberPrefix(String memberPrefix) {
    this.memberPrefix = memberPrefix;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateApplication createApplication = (CreateApplication) o;
    return Objects.equals(this.applicationName, createApplication.applicationName) &&
        Objects.equals(this.databaseName, createApplication.databaseName) &&
        Objects.equals(this.allowDuplicates, createApplication.allowDuplicates) &&
        Objects.equals(this.enableScenario, createApplication.enableScenario) &&
        Objects.equals(this.memberCount, createApplication.memberCount) &&
        Objects.equals(this.databaseType, createApplication.databaseType) &&
        Objects.equals(this.dbType, createApplication.dbType) &&
        Objects.equals(this.appType, createApplication.appType) &&
        Objects.equals(this.memberPrefix, createApplication.memberPrefix);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationName, databaseName, allowDuplicates, enableScenario, memberCount, databaseType, dbType, appType, memberPrefix);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateApplication {\n");
    sb.append("    applicationName: ").append(toIndentedString(applicationName)).append("\n");
    sb.append("    databaseName: ").append(toIndentedString(databaseName)).append("\n");
    sb.append("    allowDuplicates: ").append(toIndentedString(allowDuplicates)).append("\n");
    sb.append("    enableScenario: ").append(toIndentedString(enableScenario)).append("\n");
    sb.append("    memberCount: ").append(toIndentedString(memberCount)).append("\n");
    sb.append("    databaseType: ").append(toIndentedString(databaseType)).append("\n");
    sb.append("    dbType: ").append(toIndentedString(dbType)).append("\n");
    sb.append("    appType: ").append(toIndentedString(appType)).append("\n");
    sb.append("    memberPrefix: ").append(toIndentedString(memberPrefix)).append("\n");
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

