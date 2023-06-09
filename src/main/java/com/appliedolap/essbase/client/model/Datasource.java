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
import com.appliedolap.essbase.client.model.FileProperties;
import com.appliedolap.essbase.client.model.SQLProperties;
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
 * Datasource
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class Datasource {
  public static final String SERIALIZED_NAME_HEADER = "header";
  @SerializedName(SERIALIZED_NAME_HEADER)
  private String header;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_TOKENS = "tokens";
  @SerializedName(SERIALIZED_NAME_TOKENS)
  private List<String> tokens = null;

  /**
   * Gets or Sets tokensCombineOption
   */
  @JsonAdapter(TokensCombineOptionEnum.Adapter.class)
  public enum TokensCombineOptionEnum {
    AND("AND"),
    
    OR("OR");

    private String value;

    TokensCombineOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TokensCombineOptionEnum fromValue(String value) {
      for (TokensCombineOptionEnum b : TokensCombineOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TokensCombineOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TokensCombineOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TokensCombineOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TokensCombineOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TOKENS_COMBINE_OPTION = "tokensCombineOption";
  @SerializedName(SERIALIZED_NAME_TOKENS_COMBINE_OPTION)
  private TokensCombineOptionEnum tokensCombineOption;

  public static final String SERIALIZED_NAME_SQL_PROPERTIES = "sqlProperties";
  @SerializedName(SERIALIZED_NAME_SQL_PROPERTIES)
  private SQLProperties sqlProperties;

  public static final String SERIALIZED_NAME_FILE_PROPERTIES = "fileProperties";
  @SerializedName(SERIALIZED_NAME_FILE_PROPERTIES)
  private FileProperties fileProperties;


  public Datasource header(String header) {
    
    this.header = header;
    return this;
  }

   /**
   * Get header
   * @return header
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHeader() {
    return header;
  }


  public void setHeader(String header) {
    this.header = header;
  }


  public Datasource name(String name) {
    
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


  public Datasource tokens(List<String> tokens) {
    
    this.tokens = tokens;
    return this;
  }

  public Datasource addTokensItem(String tokensItem) {
    if (this.tokens == null) {
      this.tokens = new ArrayList<String>();
    }
    this.tokens.add(tokensItem);
    return this;
  }

   /**
   * Get tokens
   * @return tokens
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getTokens() {
    return tokens;
  }


  public void setTokens(List<String> tokens) {
    this.tokens = tokens;
  }


  public Datasource tokensCombineOption(TokensCombineOptionEnum tokensCombineOption) {
    
    this.tokensCombineOption = tokensCombineOption;
    return this;
  }

   /**
   * Get tokensCombineOption
   * @return tokensCombineOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TokensCombineOptionEnum getTokensCombineOption() {
    return tokensCombineOption;
  }


  public void setTokensCombineOption(TokensCombineOptionEnum tokensCombineOption) {
    this.tokensCombineOption = tokensCombineOption;
  }


  public Datasource sqlProperties(SQLProperties sqlProperties) {
    
    this.sqlProperties = sqlProperties;
    return this;
  }

   /**
   * Get sqlProperties
   * @return sqlProperties
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SQLProperties getSqlProperties() {
    return sqlProperties;
  }


  public void setSqlProperties(SQLProperties sqlProperties) {
    this.sqlProperties = sqlProperties;
  }


  public Datasource fileProperties(FileProperties fileProperties) {
    
    this.fileProperties = fileProperties;
    return this;
  }

   /**
   * Get fileProperties
   * @return fileProperties
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public FileProperties getFileProperties() {
    return fileProperties;
  }


  public void setFileProperties(FileProperties fileProperties) {
    this.fileProperties = fileProperties;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Datasource datasource = (Datasource) o;
    return Objects.equals(this.header, datasource.header) &&
        Objects.equals(this.name, datasource.name) &&
        Objects.equals(this.tokens, datasource.tokens) &&
        Objects.equals(this.tokensCombineOption, datasource.tokensCombineOption) &&
        Objects.equals(this.sqlProperties, datasource.sqlProperties) &&
        Objects.equals(this.fileProperties, datasource.fileProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(header, name, tokens, tokensCombineOption, sqlProperties, fileProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Datasource {\n");
    sb.append("    header: ").append(toIndentedString(header)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    tokens: ").append(toIndentedString(tokens)).append("\n");
    sb.append("    tokensCombineOption: ").append(toIndentedString(tokensCombineOption)).append("\n");
    sb.append("    sqlProperties: ").append(toIndentedString(sqlProperties)).append("\n");
    sb.append("    fileProperties: ").append(toIndentedString(fileProperties)).append("\n");
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

