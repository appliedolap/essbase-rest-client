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
 * ReplaceInfo
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-09-15T10:08:15.807377400-05:00[America/Chicago]")
public class ReplaceInfo {
  public static final String SERIALIZED_NAME_FIND = "find";
  @SerializedName(SERIALIZED_NAME_FIND)
  private String find;

  public static final String SERIALIZED_NAME_REPLACE = "replace";
  @SerializedName(SERIALIZED_NAME_REPLACE)
  private String replace;

  public static final String SERIALIZED_NAME_MATCH_WHOLE_WORD = "matchWholeWord";
  @SerializedName(SERIALIZED_NAME_MATCH_WHOLE_WORD)
  private Boolean matchWholeWord;

  public static final String SERIALIZED_NAME_REPLACE_ALL = "replaceAll";
  @SerializedName(SERIALIZED_NAME_REPLACE_ALL)
  private Boolean replaceAll;

  public static final String SERIALIZED_NAME_CASE_SENSITIVE = "caseSensitive";
  @SerializedName(SERIALIZED_NAME_CASE_SENSITIVE)
  private Boolean caseSensitive;


  public ReplaceInfo find(String find) {
    
    this.find = find;
    return this;
  }

   /**
   * Get find
   * @return find
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFind() {
    return find;
  }


  public void setFind(String find) {
    this.find = find;
  }


  public ReplaceInfo replace(String replace) {
    
    this.replace = replace;
    return this;
  }

   /**
   * Get replace
   * @return replace
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getReplace() {
    return replace;
  }


  public void setReplace(String replace) {
    this.replace = replace;
  }


  public ReplaceInfo matchWholeWord(Boolean matchWholeWord) {
    
    this.matchWholeWord = matchWholeWord;
    return this;
  }

   /**
   * Get matchWholeWord
   * @return matchWholeWord
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getMatchWholeWord() {
    return matchWholeWord;
  }


  public void setMatchWholeWord(Boolean matchWholeWord) {
    this.matchWholeWord = matchWholeWord;
  }


  public ReplaceInfo replaceAll(Boolean replaceAll) {
    
    this.replaceAll = replaceAll;
    return this;
  }

   /**
   * Get replaceAll
   * @return replaceAll
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getReplaceAll() {
    return replaceAll;
  }


  public void setReplaceAll(Boolean replaceAll) {
    this.replaceAll = replaceAll;
  }


  public ReplaceInfo caseSensitive(Boolean caseSensitive) {
    
    this.caseSensitive = caseSensitive;
    return this;
  }

   /**
   * Get caseSensitive
   * @return caseSensitive
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getCaseSensitive() {
    return caseSensitive;
  }


  public void setCaseSensitive(Boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReplaceInfo replaceInfo = (ReplaceInfo) o;
    return Objects.equals(this.find, replaceInfo.find) &&
        Objects.equals(this.replace, replaceInfo.replace) &&
        Objects.equals(this.matchWholeWord, replaceInfo.matchWholeWord) &&
        Objects.equals(this.replaceAll, replaceInfo.replaceAll) &&
        Objects.equals(this.caseSensitive, replaceInfo.caseSensitive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(find, replace, matchWholeWord, replaceAll, caseSensitive);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReplaceInfo {\n");
    sb.append("    find: ").append(toIndentedString(find)).append("\n");
    sb.append("    replace: ").append(toIndentedString(replace)).append("\n");
    sb.append("    matchWholeWord: ").append(toIndentedString(matchWholeWord)).append("\n");
    sb.append("    replaceAll: ").append(toIndentedString(replaceAll)).append("\n");
    sb.append("    caseSensitive: ").append(toIndentedString(caseSensitive)).append("\n");
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

