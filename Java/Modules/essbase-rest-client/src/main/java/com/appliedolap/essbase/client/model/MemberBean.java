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
import com.appliedolap.essbase.client.model.Link;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MemberBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class MemberBean {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DIMENSION_NAME = "dimensionName";
  @SerializedName(SERIALIZED_NAME_DIMENSION_NAME)
  private String dimensionName;

  public static final String SERIALIZED_NAME_NUMBER_OF_CHILDREN = "numberOfChildren";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_CHILDREN)
  private Integer numberOfChildren;

  public static final String SERIALIZED_NAME_LEVEL_NUMBER = "levelNumber";
  @SerializedName(SERIALIZED_NAME_LEVEL_NUMBER)
  private Integer levelNumber;

  public static final String SERIALIZED_NAME_GENERATION_NUMBER = "generationNumber";
  @SerializedName(SERIALIZED_NAME_GENERATION_NUMBER)
  private Integer generationNumber;

  public static final String SERIALIZED_NAME_ALIASES = "aliases";
  @SerializedName(SERIALIZED_NAME_ALIASES)
  private Map<String, String> aliases = null;

  public static final String SERIALIZED_NAME_ACTIVE_ALIAS_NAME = "activeAliasName";
  @SerializedName(SERIALIZED_NAME_ACTIVE_ALIAS_NAME)
  private String activeAliasName;

  public static final String SERIALIZED_NAME_MEMBER_HAS_UNIQUE_NAME = "memberHasUniqueName";
  @SerializedName(SERIALIZED_NAME_MEMBER_HAS_UNIQUE_NAME)
  private Boolean memberHasUniqueName;

  public static final String SERIALIZED_NAME_UNIQUE_NAME = "uniqueName";
  @SerializedName(SERIALIZED_NAME_UNIQUE_NAME)
  private String uniqueName;

  public static final String SERIALIZED_NAME_MEMBER_ID = "memberId";
  @SerializedName(SERIALIZED_NAME_MEMBER_ID)
  private String memberId;

  public static final String SERIALIZED_NAME_UNIQUE_ID = "uniqueId";
  @SerializedName(SERIALIZED_NAME_UNIQUE_ID)
  private String uniqueId;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    NONE("NONE"),
    
    NUMERIC("NUMERIC"),
    
    SMARTLIST("SMARTLIST"),
    
    DATE("DATE");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeEnum type;

  public static final String SERIALIZED_NAME_MEMBER_SOLVE_ORDER = "memberSolveOrder";
  @SerializedName(SERIALIZED_NAME_MEMBER_SOLVE_ORDER)
  private Integer memberSolveOrder;

  public static final String SERIALIZED_NAME_DESCENDANTS_COUNT = "descendantsCount";
  @SerializedName(SERIALIZED_NAME_DESCENDANTS_COUNT)
  private Long descendantsCount;

  public static final String SERIALIZED_NAME_PREVIOUS_SIBLINGS_COUNT = "previousSiblingsCount";
  @SerializedName(SERIALIZED_NAME_PREVIOUS_SIBLINGS_COUNT)
  private Integer previousSiblingsCount;

  public static final String SERIALIZED_NAME_DIMENSION = "dimension";
  @SerializedName(SERIALIZED_NAME_DIMENSION)
  private Boolean dimension;

  public static final String SERIALIZED_NAME_ATTRIBUTE = "attribute";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE)
  private Boolean attribute;

  public static final String SERIALIZED_NAME_ACCOUNT = "account";
  @SerializedName(SERIALIZED_NAME_ACCOUNT)
  private Boolean account;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public MemberBean name(String name) {
    
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


  public MemberBean dimensionName(String dimensionName) {
    
    this.dimensionName = dimensionName;
    return this;
  }

   /**
   * Get dimensionName
   * @return dimensionName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDimensionName() {
    return dimensionName;
  }


  public void setDimensionName(String dimensionName) {
    this.dimensionName = dimensionName;
  }


  public MemberBean numberOfChildren(Integer numberOfChildren) {
    
    this.numberOfChildren = numberOfChildren;
    return this;
  }

   /**
   * Get numberOfChildren
   * @return numberOfChildren
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfChildren() {
    return numberOfChildren;
  }


  public void setNumberOfChildren(Integer numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }


  public MemberBean levelNumber(Integer levelNumber) {
    
    this.levelNumber = levelNumber;
    return this;
  }

   /**
   * Get levelNumber
   * @return levelNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getLevelNumber() {
    return levelNumber;
  }


  public void setLevelNumber(Integer levelNumber) {
    this.levelNumber = levelNumber;
  }


  public MemberBean generationNumber(Integer generationNumber) {
    
    this.generationNumber = generationNumber;
    return this;
  }

   /**
   * Get generationNumber
   * @return generationNumber
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGenerationNumber() {
    return generationNumber;
  }


  public void setGenerationNumber(Integer generationNumber) {
    this.generationNumber = generationNumber;
  }


  public MemberBean aliases(Map<String, String> aliases) {
    
    this.aliases = aliases;
    return this;
  }

  public MemberBean putAliasesItem(String key, String aliasesItem) {
    if (this.aliases == null) {
      this.aliases = new HashMap<String, String>();
    }
    this.aliases.put(key, aliasesItem);
    return this;
  }

   /**
   * Get aliases
   * @return aliases
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Map<String, String> getAliases() {
    return aliases;
  }


  public void setAliases(Map<String, String> aliases) {
    this.aliases = aliases;
  }


  public MemberBean activeAliasName(String activeAliasName) {
    
    this.activeAliasName = activeAliasName;
    return this;
  }

   /**
   * Get activeAliasName
   * @return activeAliasName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getActiveAliasName() {
    return activeAliasName;
  }


  public void setActiveAliasName(String activeAliasName) {
    this.activeAliasName = activeAliasName;
  }


  public MemberBean memberHasUniqueName(Boolean memberHasUniqueName) {
    
    this.memberHasUniqueName = memberHasUniqueName;
    return this;
  }

   /**
   * Get memberHasUniqueName
   * @return memberHasUniqueName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getMemberHasUniqueName() {
    return memberHasUniqueName;
  }


  public void setMemberHasUniqueName(Boolean memberHasUniqueName) {
    this.memberHasUniqueName = memberHasUniqueName;
  }


  public MemberBean uniqueName(String uniqueName) {
    
    this.uniqueName = uniqueName;
    return this;
  }

   /**
   * Get uniqueName
   * @return uniqueName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUniqueName() {
    return uniqueName;
  }


  public void setUniqueName(String uniqueName) {
    this.uniqueName = uniqueName;
  }


  public MemberBean memberId(String memberId) {
    
    this.memberId = memberId;
    return this;
  }

   /**
   * Get memberId
   * @return memberId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMemberId() {
    return memberId;
  }


  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }


  public MemberBean uniqueId(String uniqueId) {
    
    this.uniqueId = uniqueId;
    return this;
  }

   /**
   * Get uniqueId
   * @return uniqueId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUniqueId() {
    return uniqueId;
  }


  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }


  public MemberBean type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public MemberBean memberSolveOrder(Integer memberSolveOrder) {
    
    this.memberSolveOrder = memberSolveOrder;
    return this;
  }

   /**
   * Get memberSolveOrder
   * @return memberSolveOrder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getMemberSolveOrder() {
    return memberSolveOrder;
  }


  public void setMemberSolveOrder(Integer memberSolveOrder) {
    this.memberSolveOrder = memberSolveOrder;
  }


  public MemberBean descendantsCount(Long descendantsCount) {
    
    this.descendantsCount = descendantsCount;
    return this;
  }

   /**
   * Get descendantsCount
   * @return descendantsCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDescendantsCount() {
    return descendantsCount;
  }


  public void setDescendantsCount(Long descendantsCount) {
    this.descendantsCount = descendantsCount;
  }


  public MemberBean previousSiblingsCount(Integer previousSiblingsCount) {
    
    this.previousSiblingsCount = previousSiblingsCount;
    return this;
  }

   /**
   * Get previousSiblingsCount
   * @return previousSiblingsCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getPreviousSiblingsCount() {
    return previousSiblingsCount;
  }


  public void setPreviousSiblingsCount(Integer previousSiblingsCount) {
    this.previousSiblingsCount = previousSiblingsCount;
  }


  public MemberBean dimension(Boolean dimension) {
    
    this.dimension = dimension;
    return this;
  }

   /**
   * Get dimension
   * @return dimension
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getDimension() {
    return dimension;
  }


  public void setDimension(Boolean dimension) {
    this.dimension = dimension;
  }


  public MemberBean attribute(Boolean attribute) {
    
    this.attribute = attribute;
    return this;
  }

   /**
   * Get attribute
   * @return attribute
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAttribute() {
    return attribute;
  }


  public void setAttribute(Boolean attribute) {
    this.attribute = attribute;
  }


  public MemberBean account(Boolean account) {
    
    this.account = account;
    return this;
  }

   /**
   * Get account
   * @return account
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAccount() {
    return account;
  }


  public void setAccount(Boolean account) {
    this.account = account;
  }


  public MemberBean links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public MemberBean addLinksItem(Link linksItem) {
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
    MemberBean memberBean = (MemberBean) o;
    return Objects.equals(this.name, memberBean.name) &&
        Objects.equals(this.dimensionName, memberBean.dimensionName) &&
        Objects.equals(this.numberOfChildren, memberBean.numberOfChildren) &&
        Objects.equals(this.levelNumber, memberBean.levelNumber) &&
        Objects.equals(this.generationNumber, memberBean.generationNumber) &&
        Objects.equals(this.aliases, memberBean.aliases) &&
        Objects.equals(this.activeAliasName, memberBean.activeAliasName) &&
        Objects.equals(this.memberHasUniqueName, memberBean.memberHasUniqueName) &&
        Objects.equals(this.uniqueName, memberBean.uniqueName) &&
        Objects.equals(this.memberId, memberBean.memberId) &&
        Objects.equals(this.uniqueId, memberBean.uniqueId) &&
        Objects.equals(this.type, memberBean.type) &&
        Objects.equals(this.memberSolveOrder, memberBean.memberSolveOrder) &&
        Objects.equals(this.descendantsCount, memberBean.descendantsCount) &&
        Objects.equals(this.previousSiblingsCount, memberBean.previousSiblingsCount) &&
        Objects.equals(this.dimension, memberBean.dimension) &&
        Objects.equals(this.attribute, memberBean.attribute) &&
        Objects.equals(this.account, memberBean.account) &&
        Objects.equals(this.links, memberBean.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dimensionName, numberOfChildren, levelNumber, generationNumber, aliases, activeAliasName, memberHasUniqueName, uniqueName, memberId, uniqueId, type, memberSolveOrder, descendantsCount, previousSiblingsCount, dimension, attribute, account, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MemberBean {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dimensionName: ").append(toIndentedString(dimensionName)).append("\n");
    sb.append("    numberOfChildren: ").append(toIndentedString(numberOfChildren)).append("\n");
    sb.append("    levelNumber: ").append(toIndentedString(levelNumber)).append("\n");
    sb.append("    generationNumber: ").append(toIndentedString(generationNumber)).append("\n");
    sb.append("    aliases: ").append(toIndentedString(aliases)).append("\n");
    sb.append("    activeAliasName: ").append(toIndentedString(activeAliasName)).append("\n");
    sb.append("    memberHasUniqueName: ").append(toIndentedString(memberHasUniqueName)).append("\n");
    sb.append("    uniqueName: ").append(toIndentedString(uniqueName)).append("\n");
    sb.append("    memberId: ").append(toIndentedString(memberId)).append("\n");
    sb.append("    uniqueId: ").append(toIndentedString(uniqueId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    memberSolveOrder: ").append(toIndentedString(memberSolveOrder)).append("\n");
    sb.append("    descendantsCount: ").append(toIndentedString(descendantsCount)).append("\n");
    sb.append("    previousSiblingsCount: ").append(toIndentedString(previousSiblingsCount)).append("\n");
    sb.append("    dimension: ").append(toIndentedString(dimension)).append("\n");
    sb.append("    attribute: ").append(toIndentedString(attribute)).append("\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
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

