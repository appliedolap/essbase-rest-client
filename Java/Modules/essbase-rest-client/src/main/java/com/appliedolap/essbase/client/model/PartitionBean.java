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
import com.appliedolap.essbase.client.model.AreaBean;
import com.appliedolap.essbase.client.model.ConnectionInfoBean;
import com.appliedolap.essbase.client.model.Link;
import com.appliedolap.essbase.client.model.MemberMappingBean;
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
 * PartitionBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-29T21:46:59.899155-05:00[America/Indiana/Indianapolis]")
public class PartitionBean {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;

  public static final String SERIALIZED_NAME_UPDATABLE = "updatable";
  @SerializedName(SERIALIZED_NAME_UPDATABLE)
  private Boolean updatable;

  public static final String SERIALIZED_NAME_IS_NEW = "isNew";
  @SerializedName(SERIALIZED_NAME_IS_NEW)
  private Boolean isNew;

  public static final String SERIALIZED_NAME_LOCKED = "locked";
  @SerializedName(SERIALIZED_NAME_LOCKED)
  private Boolean locked;

  public static final String SERIALIZED_NAME_SOURCE_INFO = "sourceInfo";
  @SerializedName(SERIALIZED_NAME_SOURCE_INFO)
  private ConnectionInfoBean sourceInfo;

  public static final String SERIALIZED_NAME_TARGET_INFO = "targetInfo";
  @SerializedName(SERIALIZED_NAME_TARGET_INFO)
  private ConnectionInfoBean targetInfo;

  public static final String SERIALIZED_NAME_AREAS = "areas";
  @SerializedName(SERIALIZED_NAME_AREAS)
  private List<AreaBean> areas = null;

  public static final String SERIALIZED_NAME_MAPPINGS = "mappings";
  @SerializedName(SERIALIZED_NAME_MAPPINGS)
  private List<MemberMappingBean> mappings = null;

  public static final String SERIALIZED_NAME_ERROR_MESSAGE = "errorMessage";
  @SerializedName(SERIALIZED_NAME_ERROR_MESSAGE)
  private String errorMessage;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public PartitionBean id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public PartitionBean type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  public PartitionBean updatable(Boolean updatable) {
    
    this.updatable = updatable;
    return this;
  }

   /**
   * Get updatable
   * @return updatable
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getUpdatable() {
    return updatable;
  }


  public void setUpdatable(Boolean updatable) {
    this.updatable = updatable;
  }


  public PartitionBean isNew(Boolean isNew) {
    
    this.isNew = isNew;
    return this;
  }

   /**
   * Get isNew
   * @return isNew
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIsNew() {
    return isNew;
  }


  public void setIsNew(Boolean isNew) {
    this.isNew = isNew;
  }


  public PartitionBean locked(Boolean locked) {
    
    this.locked = locked;
    return this;
  }

   /**
   * Get locked
   * @return locked
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getLocked() {
    return locked;
  }


  public void setLocked(Boolean locked) {
    this.locked = locked;
  }


  public PartitionBean sourceInfo(ConnectionInfoBean sourceInfo) {
    
    this.sourceInfo = sourceInfo;
    return this;
  }

   /**
   * Get sourceInfo
   * @return sourceInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ConnectionInfoBean getSourceInfo() {
    return sourceInfo;
  }


  public void setSourceInfo(ConnectionInfoBean sourceInfo) {
    this.sourceInfo = sourceInfo;
  }


  public PartitionBean targetInfo(ConnectionInfoBean targetInfo) {
    
    this.targetInfo = targetInfo;
    return this;
  }

   /**
   * Get targetInfo
   * @return targetInfo
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ConnectionInfoBean getTargetInfo() {
    return targetInfo;
  }


  public void setTargetInfo(ConnectionInfoBean targetInfo) {
    this.targetInfo = targetInfo;
  }


  public PartitionBean areas(List<AreaBean> areas) {
    
    this.areas = areas;
    return this;
  }

  public PartitionBean addAreasItem(AreaBean areasItem) {
    if (this.areas == null) {
      this.areas = new ArrayList<AreaBean>();
    }
    this.areas.add(areasItem);
    return this;
  }

   /**
   * Get areas
   * @return areas
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<AreaBean> getAreas() {
    return areas;
  }


  public void setAreas(List<AreaBean> areas) {
    this.areas = areas;
  }


  public PartitionBean mappings(List<MemberMappingBean> mappings) {
    
    this.mappings = mappings;
    return this;
  }

  public PartitionBean addMappingsItem(MemberMappingBean mappingsItem) {
    if (this.mappings == null) {
      this.mappings = new ArrayList<MemberMappingBean>();
    }
    this.mappings.add(mappingsItem);
    return this;
  }

   /**
   * Get mappings
   * @return mappings
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<MemberMappingBean> getMappings() {
    return mappings;
  }


  public void setMappings(List<MemberMappingBean> mappings) {
    this.mappings = mappings;
  }


  public PartitionBean errorMessage(String errorMessage) {
    
    this.errorMessage = errorMessage;
    return this;
  }

   /**
   * Get errorMessage
   * @return errorMessage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getErrorMessage() {
    return errorMessage;
  }


  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }


  public PartitionBean links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public PartitionBean addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<Link>();
    }
    this.links.add(linksItem);
    return this;
  }

   /**
   * Get links
   * @return links
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Link> getLinks() {
    return links;
  }


  public void setLinks(List<Link> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartitionBean partitionBean = (PartitionBean) o;
    return Objects.equals(this.id, partitionBean.id) &&
        Objects.equals(this.type, partitionBean.type) &&
        Objects.equals(this.updatable, partitionBean.updatable) &&
        Objects.equals(this.isNew, partitionBean.isNew) &&
        Objects.equals(this.locked, partitionBean.locked) &&
        Objects.equals(this.sourceInfo, partitionBean.sourceInfo) &&
        Objects.equals(this.targetInfo, partitionBean.targetInfo) &&
        Objects.equals(this.areas, partitionBean.areas) &&
        Objects.equals(this.mappings, partitionBean.mappings) &&
        Objects.equals(this.errorMessage, partitionBean.errorMessage) &&
        Objects.equals(this.links, partitionBean.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, updatable, isNew, locked, sourceInfo, targetInfo, areas, mappings, errorMessage, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartitionBean {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    updatable: ").append(toIndentedString(updatable)).append("\n");
    sb.append("    isNew: ").append(toIndentedString(isNew)).append("\n");
    sb.append("    locked: ").append(toIndentedString(locked)).append("\n");
    sb.append("    sourceInfo: ").append(toIndentedString(sourceInfo)).append("\n");
    sb.append("    targetInfo: ").append(toIndentedString(targetInfo)).append("\n");
    sb.append("    areas: ").append(toIndentedString(areas)).append("\n");
    sb.append("    mappings: ").append(toIndentedString(mappings)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

