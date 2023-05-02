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
 * MemberMappingBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class MemberMappingBean {
  public static final String SERIALIZED_NAME_SOURCE_MEMBER = "sourceMember";
  @SerializedName(SERIALIZED_NAME_SOURCE_MEMBER)
  private String sourceMember;

  public static final String SERIALIZED_NAME_TARGET_MEMBER = "targetMember";
  @SerializedName(SERIALIZED_NAME_TARGET_MEMBER)
  private String targetMember;


  public MemberMappingBean sourceMember(String sourceMember) {
    
    this.sourceMember = sourceMember;
    return this;
  }

   /**
   * Get sourceMember
   * @return sourceMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSourceMember() {
    return sourceMember;
  }


  public void setSourceMember(String sourceMember) {
    this.sourceMember = sourceMember;
  }


  public MemberMappingBean targetMember(String targetMember) {
    
    this.targetMember = targetMember;
    return this;
  }

   /**
   * Get targetMember
   * @return targetMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getTargetMember() {
    return targetMember;
  }


  public void setTargetMember(String targetMember) {
    this.targetMember = targetMember;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MemberMappingBean memberMappingBean = (MemberMappingBean) o;
    return Objects.equals(this.sourceMember, memberMappingBean.sourceMember) &&
        Objects.equals(this.targetMember, memberMappingBean.targetMember);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceMember, targetMember);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MemberMappingBean {\n");
    sb.append("    sourceMember: ").append(toIndentedString(sourceMember)).append("\n");
    sb.append("    targetMember: ").append(toIndentedString(targetMember)).append("\n");
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
