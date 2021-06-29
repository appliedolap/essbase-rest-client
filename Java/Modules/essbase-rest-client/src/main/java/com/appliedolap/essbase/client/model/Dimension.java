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
import com.appliedolap.essbase.client.model.AttributeOptions;
import com.appliedolap.essbase.client.model.Level;
import com.appliedolap.essbase.client.model.MeasureOptions;
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
 * Dimension
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class Dimension {
  public static final String SERIALIZED_NAME_GENERATIONS = "generations";
  @SerializedName(SERIALIZED_NAME_GENERATIONS)
  private List<Level> generations = null;

  public static final String SERIALIZED_NAME_LEVELS = "levels";
  @SerializedName(SERIALIZED_NAME_LEVELS)
  private List<Level> levels = null;

  public static final String SERIALIZED_NAME_ALLOWASSOCIATION_CHANGES = "allowassociationChanges";
  @SerializedName(SERIALIZED_NAME_ALLOWASSOCIATION_CHANGES)
  private Boolean allowassociationChanges;

  public static final String SERIALIZED_NAME_ALLOW_FORMULA_CHANGES = "allowFormulaChanges";
  @SerializedName(SERIALIZED_NAME_ALLOW_FORMULA_CHANGES)
  private Boolean allowFormulaChanges;

  public static final String SERIALIZED_NAME_ALLOW_PROPERTY_CHANGES = "allowPropertyChanges";
  @SerializedName(SERIALIZED_NAME_ALLOW_PROPERTY_CHANGES)
  private Boolean allowPropertyChanges;

  public static final String SERIALIZED_NAME_ALLOW_U_D_A_CHANGES = "allowUDAChanges";
  @SerializedName(SERIALIZED_NAME_ALLOW_U_D_A_CHANGES)
  private Boolean allowUDAChanges;

  public static final String SERIALIZED_NAME_MEASURE_OPTIONS = "measureOptions";
  @SerializedName(SERIALIZED_NAME_MEASURE_OPTIONS)
  private MeasureOptions measureOptions;

  public static final String SERIALIZED_NAME_AGGREGATE_LEVEL_USAGE = "aggregateLevelUsage";
  @SerializedName(SERIALIZED_NAME_AGGREGATE_LEVEL_USAGE)
  private Integer aggregateLevelUsage;

  /**
   * Gets or Sets addMemberOption
   */
  @JsonAdapter(AddMemberOptionEnum.Adapter.class)
  public enum AddMemberOptionEnum {
    GENERATION("GENERATION"),
    
    SIBLING_LOWEST_LEVEL("SIBLING_LOWEST_LEVEL"),
    
    CHILD("CHILD"),
    
    SIBLING_MATCHING_STRING("SIBLING_MATCHING_STRING"),
    
    LEVEL("LEVEL"),
    
    PARENT_CHILD("PARENT_CHILD"),
    
    GEN_NULLS("GEN_NULLS"),
    
    LEVEL_NULLS("LEVEL_NULLS");

    private String value;

    AddMemberOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AddMemberOptionEnum fromValue(String value) {
      for (AddMemberOptionEnum b : AddMemberOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AddMemberOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AddMemberOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AddMemberOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AddMemberOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ADD_MEMBER_OPTION = "addMemberOption";
  @SerializedName(SERIALIZED_NAME_ADD_MEMBER_OPTION)
  private AddMemberOptionEnum addMemberOption;

  public static final String SERIALIZED_NAME_ATTRIBUTE_OPTIONS = "attributeOptions";
  @SerializedName(SERIALIZED_NAME_ATTRIBUTE_OPTIONS)
  private AttributeOptions attributeOptions;

  /**
   * Gets or Sets configOption
   */
  @JsonAdapter(ConfigOptionEnum.Adapter.class)
  public enum ConfigOptionEnum {
    EXISTING("EXISTING"),
    
    DENSE("DENSE"),
    
    SPARSE("SPARSE");

    private String value;

    ConfigOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ConfigOptionEnum fromValue(String value) {
      for (ConfigOptionEnum b : ConfigOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ConfigOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ConfigOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ConfigOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ConfigOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CONFIG_OPTION = "configOption";
  @SerializedName(SERIALIZED_NAME_CONFIG_OPTION)
  private ConfigOptionEnum configOption;

  /**
   * Gets or Sets unique
   */
  @JsonAdapter(UniqueEnum.Adapter.class)
  public enum UniqueEnum {
    EXISTING("EXISTING"),
    
    UNIQUE("UNIQUE"),
    
    DUPLICATE("DUPLICATE"),
    
    NONE("NONE");

    private String value;

    UniqueEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static UniqueEnum fromValue(String value) {
      for (UniqueEnum b : UniqueEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<UniqueEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final UniqueEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public UniqueEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return UniqueEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_UNIQUE = "unique";
  @SerializedName(SERIALIZED_NAME_UNIQUE)
  private UniqueEnum unique;

  /**
   * Gets or Sets hierarchyType
   */
  @JsonAdapter(HierarchyTypeEnum.Adapter.class)
  public enum HierarchyTypeEnum {
    EXISTING("EXISTING"),
    
    MULTIPLE("MULTIPLE"),
    
    STORED("STORED"),
    
    DYNAMIC("DYNAMIC");

    private String value;

    HierarchyTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static HierarchyTypeEnum fromValue(String value) {
      for (HierarchyTypeEnum b : HierarchyTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<HierarchyTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final HierarchyTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public HierarchyTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return HierarchyTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_HIERARCHY_TYPE = "hierarchyType";
  @SerializedName(SERIALIZED_NAME_HIERARCHY_TYPE)
  private HierarchyTypeEnum hierarchyType;

  /**
   * Gets or Sets sortOption
   */
  @JsonAdapter(SortOptionEnum.Adapter.class)
  public enum SortOptionEnum {
    NONE("NONE"),
    
    ASCENDING("ASCENDING"),
    
    DESCENDING("DESCENDING");

    private String value;

    SortOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static SortOptionEnum fromValue(String value) {
      for (SortOptionEnum b : SortOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<SortOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final SortOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public SortOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return SortOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_SORT_OPTION = "sortOption";
  @SerializedName(SERIALIZED_NAME_SORT_OPTION)
  private SortOptionEnum sortOption;

  /**
   * Gets or Sets storageType
   */
  @JsonAdapter(StorageTypeEnum.Adapter.class)
  public enum StorageTypeEnum {
    EXISTING("EXISTING"),
    
    STORE("STORE"),
    
    NEVER_SHARE("NEVER_SHARE"),
    
    LABEL_ONLY("LABEL_ONLY"),
    
    DYNAMIC_CALC_STORE("DYNAMIC_CALC_STORE"),
    
    DYNAMIC_CALC("DYNAMIC_CALC");

    private String value;

    StorageTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StorageTypeEnum fromValue(String value) {
      for (StorageTypeEnum b : StorageTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StorageTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StorageTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StorageTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StorageTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STORAGE_TYPE = "storageType";
  @SerializedName(SERIALIZED_NAME_STORAGE_TYPE)
  private StorageTypeEnum storageType;

  /**
   * Gets or Sets type
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    EXISTING("EXISTING"),
    
    NONE("NONE"),
    
    ACCOUNTS("ACCOUNTS"),
    
    TIME("TIME"),
    
    COUNTRY("COUNTRY"),
    
    ATTRIBUTES("ATTRIBUTES");

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

  /**
   * Gets or Sets updateOption
   */
  @JsonAdapter(UpdateOptionEnum.Adapter.class)
  public enum UpdateOptionEnum {
    MERGE("MERGE"),
    
    REMOVE_UNSPECIFIED("REMOVE_UNSPECIFIED");

    private String value;

    UpdateOptionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static UpdateOptionEnum fromValue(String value) {
      for (UpdateOptionEnum b : UpdateOptionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<UpdateOptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final UpdateOptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public UpdateOptionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return UpdateOptionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_UPDATE_OPTION = "updateOption";
  @SerializedName(SERIALIZED_NAME_UPDATE_OPTION)
  private UpdateOptionEnum updateOption;

  /**
   * Gets or Sets allowMoves
   */
  @JsonAdapter(AllowMovesEnum.Adapter.class)
  public enum AllowMovesEnum {
    NOTOK("NOTOK"),
    
    OK("OK"),
    
    IGNORE("IGNORE"),
    
    GEN2("GEN2"),
    
    NOTGEN2("NOTGEN2");

    private String value;

    AllowMovesEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AllowMovesEnum fromValue(String value) {
      for (AllowMovesEnum b : AllowMovesEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AllowMovesEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AllowMovesEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AllowMovesEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AllowMovesEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ALLOW_MOVES = "allowMoves";
  @SerializedName(SERIALIZED_NAME_ALLOW_MOVES)
  private AllowMovesEnum allowMoves;

  public static final String SERIALIZED_NAME_SOLVE_ORDER = "solveOrder";
  @SerializedName(SERIALIZED_NAME_SOLVE_ORDER)
  private Integer solveOrder;

  public static final String SERIALIZED_NAME_CREATE_ATTRIBUTE_MEMBERS = "createAttributeMembers";
  @SerializedName(SERIALIZED_NAME_CREATE_ATTRIBUTE_MEMBERS)
  private Boolean createAttributeMembers;

  public static final String SERIALIZED_NAME_SHARE = "share";
  @SerializedName(SERIALIZED_NAME_SHARE)
  private Boolean share;

  public static final String SERIALIZED_NAME_INCREMENTAL_SORT = "incrementalSort";
  @SerializedName(SERIALIZED_NAME_INCREMENTAL_SORT)
  private Boolean incrementalSort;

  public static final String SERIALIZED_NAME_AUTO_FIX_SHARED_MEMBER = "autoFixSharedMember";
  @SerializedName(SERIALIZED_NAME_AUTO_FIX_SHARED_MEMBER)
  private Boolean autoFixSharedMember;

  public static final String SERIALIZED_NAME_FLEXIBLE = "flexible";
  @SerializedName(SERIALIZED_NAME_FLEXIBLE)
  private Boolean flexible;

  public static final String SERIALIZED_NAME_MEMBER_NAME = "memberName";
  @SerializedName(SERIALIZED_NAME_MEMBER_NAME)
  private String memberName;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DIMENSION_SOLVE_ORDER = "dimensionSolveOrder";
  @SerializedName(SERIALIZED_NAME_DIMENSION_SOLVE_ORDER)
  private Integer dimensionSolveOrder;

  public static final String SERIALIZED_NAME_ADDED = "added";
  @SerializedName(SERIALIZED_NAME_ADDED)
  private Boolean added;


  public Dimension generations(List<Level> generations) {
    
    this.generations = generations;
    return this;
  }

  public Dimension addGenerationsItem(Level generationsItem) {
    if (this.generations == null) {
      this.generations = new ArrayList<Level>();
    }
    this.generations.add(generationsItem);
    return this;
  }

   /**
   * Get generations
   * @return generations
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Level> getGenerations() {
    return generations;
  }


  public void setGenerations(List<Level> generations) {
    this.generations = generations;
  }


  public Dimension levels(List<Level> levels) {
    
    this.levels = levels;
    return this;
  }

  public Dimension addLevelsItem(Level levelsItem) {
    if (this.levels == null) {
      this.levels = new ArrayList<Level>();
    }
    this.levels.add(levelsItem);
    return this;
  }

   /**
   * Get levels
   * @return levels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<Level> getLevels() {
    return levels;
  }


  public void setLevels(List<Level> levels) {
    this.levels = levels;
  }


  public Dimension allowassociationChanges(Boolean allowassociationChanges) {
    
    this.allowassociationChanges = allowassociationChanges;
    return this;
  }

   /**
   * Get allowassociationChanges
   * @return allowassociationChanges
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowassociationChanges() {
    return allowassociationChanges;
  }


  public void setAllowassociationChanges(Boolean allowassociationChanges) {
    this.allowassociationChanges = allowassociationChanges;
  }


  public Dimension allowFormulaChanges(Boolean allowFormulaChanges) {
    
    this.allowFormulaChanges = allowFormulaChanges;
    return this;
  }

   /**
   * Get allowFormulaChanges
   * @return allowFormulaChanges
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowFormulaChanges() {
    return allowFormulaChanges;
  }


  public void setAllowFormulaChanges(Boolean allowFormulaChanges) {
    this.allowFormulaChanges = allowFormulaChanges;
  }


  public Dimension allowPropertyChanges(Boolean allowPropertyChanges) {
    
    this.allowPropertyChanges = allowPropertyChanges;
    return this;
  }

   /**
   * Get allowPropertyChanges
   * @return allowPropertyChanges
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowPropertyChanges() {
    return allowPropertyChanges;
  }


  public void setAllowPropertyChanges(Boolean allowPropertyChanges) {
    this.allowPropertyChanges = allowPropertyChanges;
  }


  public Dimension allowUDAChanges(Boolean allowUDAChanges) {
    
    this.allowUDAChanges = allowUDAChanges;
    return this;
  }

   /**
   * Get allowUDAChanges
   * @return allowUDAChanges
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowUDAChanges() {
    return allowUDAChanges;
  }


  public void setAllowUDAChanges(Boolean allowUDAChanges) {
    this.allowUDAChanges = allowUDAChanges;
  }


  public Dimension measureOptions(MeasureOptions measureOptions) {
    
    this.measureOptions = measureOptions;
    return this;
  }

   /**
   * Get measureOptions
   * @return measureOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public MeasureOptions getMeasureOptions() {
    return measureOptions;
  }


  public void setMeasureOptions(MeasureOptions measureOptions) {
    this.measureOptions = measureOptions;
  }


  public Dimension aggregateLevelUsage(Integer aggregateLevelUsage) {
    
    this.aggregateLevelUsage = aggregateLevelUsage;
    return this;
  }

   /**
   * Get aggregateLevelUsage
   * @return aggregateLevelUsage
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAggregateLevelUsage() {
    return aggregateLevelUsage;
  }


  public void setAggregateLevelUsage(Integer aggregateLevelUsage) {
    this.aggregateLevelUsage = aggregateLevelUsage;
  }


  public Dimension addMemberOption(AddMemberOptionEnum addMemberOption) {
    
    this.addMemberOption = addMemberOption;
    return this;
  }

   /**
   * Get addMemberOption
   * @return addMemberOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AddMemberOptionEnum getAddMemberOption() {
    return addMemberOption;
  }


  public void setAddMemberOption(AddMemberOptionEnum addMemberOption) {
    this.addMemberOption = addMemberOption;
  }


  public Dimension attributeOptions(AttributeOptions attributeOptions) {
    
    this.attributeOptions = attributeOptions;
    return this;
  }

   /**
   * Get attributeOptions
   * @return attributeOptions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AttributeOptions getAttributeOptions() {
    return attributeOptions;
  }


  public void setAttributeOptions(AttributeOptions attributeOptions) {
    this.attributeOptions = attributeOptions;
  }


  public Dimension configOption(ConfigOptionEnum configOption) {
    
    this.configOption = configOption;
    return this;
  }

   /**
   * Get configOption
   * @return configOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ConfigOptionEnum getConfigOption() {
    return configOption;
  }


  public void setConfigOption(ConfigOptionEnum configOption) {
    this.configOption = configOption;
  }


  public Dimension unique(UniqueEnum unique) {
    
    this.unique = unique;
    return this;
  }

   /**
   * Get unique
   * @return unique
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public UniqueEnum getUnique() {
    return unique;
  }


  public void setUnique(UniqueEnum unique) {
    this.unique = unique;
  }


  public Dimension hierarchyType(HierarchyTypeEnum hierarchyType) {
    
    this.hierarchyType = hierarchyType;
    return this;
  }

   /**
   * Get hierarchyType
   * @return hierarchyType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public HierarchyTypeEnum getHierarchyType() {
    return hierarchyType;
  }


  public void setHierarchyType(HierarchyTypeEnum hierarchyType) {
    this.hierarchyType = hierarchyType;
  }


  public Dimension sortOption(SortOptionEnum sortOption) {
    
    this.sortOption = sortOption;
    return this;
  }

   /**
   * Get sortOption
   * @return sortOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SortOptionEnum getSortOption() {
    return sortOption;
  }


  public void setSortOption(SortOptionEnum sortOption) {
    this.sortOption = sortOption;
  }


  public Dimension storageType(StorageTypeEnum storageType) {
    
    this.storageType = storageType;
    return this;
  }

   /**
   * Get storageType
   * @return storageType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public StorageTypeEnum getStorageType() {
    return storageType;
  }


  public void setStorageType(StorageTypeEnum storageType) {
    this.storageType = storageType;
  }


  public Dimension type(TypeEnum type) {
    
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


  public Dimension updateOption(UpdateOptionEnum updateOption) {
    
    this.updateOption = updateOption;
    return this;
  }

   /**
   * Get updateOption
   * @return updateOption
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public UpdateOptionEnum getUpdateOption() {
    return updateOption;
  }


  public void setUpdateOption(UpdateOptionEnum updateOption) {
    this.updateOption = updateOption;
  }


  public Dimension allowMoves(AllowMovesEnum allowMoves) {
    
    this.allowMoves = allowMoves;
    return this;
  }

   /**
   * Get allowMoves
   * @return allowMoves
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public AllowMovesEnum getAllowMoves() {
    return allowMoves;
  }


  public void setAllowMoves(AllowMovesEnum allowMoves) {
    this.allowMoves = allowMoves;
  }


  public Dimension solveOrder(Integer solveOrder) {
    
    this.solveOrder = solveOrder;
    return this;
  }

   /**
   * Get solveOrder
   * @return solveOrder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getSolveOrder() {
    return solveOrder;
  }


  public void setSolveOrder(Integer solveOrder) {
    this.solveOrder = solveOrder;
  }


  public Dimension createAttributeMembers(Boolean createAttributeMembers) {
    
    this.createAttributeMembers = createAttributeMembers;
    return this;
  }

   /**
   * Get createAttributeMembers
   * @return createAttributeMembers
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getCreateAttributeMembers() {
    return createAttributeMembers;
  }


  public void setCreateAttributeMembers(Boolean createAttributeMembers) {
    this.createAttributeMembers = createAttributeMembers;
  }


  public Dimension share(Boolean share) {
    
    this.share = share;
    return this;
  }

   /**
   * Get share
   * @return share
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getShare() {
    return share;
  }


  public void setShare(Boolean share) {
    this.share = share;
  }


  public Dimension incrementalSort(Boolean incrementalSort) {
    
    this.incrementalSort = incrementalSort;
    return this;
  }

   /**
   * Get incrementalSort
   * @return incrementalSort
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIncrementalSort() {
    return incrementalSort;
  }


  public void setIncrementalSort(Boolean incrementalSort) {
    this.incrementalSort = incrementalSort;
  }


  public Dimension autoFixSharedMember(Boolean autoFixSharedMember) {
    
    this.autoFixSharedMember = autoFixSharedMember;
    return this;
  }

   /**
   * Get autoFixSharedMember
   * @return autoFixSharedMember
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAutoFixSharedMember() {
    return autoFixSharedMember;
  }


  public void setAutoFixSharedMember(Boolean autoFixSharedMember) {
    this.autoFixSharedMember = autoFixSharedMember;
  }


  public Dimension flexible(Boolean flexible) {
    
    this.flexible = flexible;
    return this;
  }

   /**
   * Get flexible
   * @return flexible
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getFlexible() {
    return flexible;
  }


  public void setFlexible(Boolean flexible) {
    this.flexible = flexible;
  }


  public Dimension memberName(String memberName) {
    
    this.memberName = memberName;
    return this;
  }

   /**
   * Get memberName
   * @return memberName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMemberName() {
    return memberName;
  }


  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }


  public Dimension name(String name) {
    
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


  public Dimension dimensionSolveOrder(Integer dimensionSolveOrder) {
    
    this.dimensionSolveOrder = dimensionSolveOrder;
    return this;
  }

   /**
   * Get dimensionSolveOrder
   * @return dimensionSolveOrder
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getDimensionSolveOrder() {
    return dimensionSolveOrder;
  }


  public void setDimensionSolveOrder(Integer dimensionSolveOrder) {
    this.dimensionSolveOrder = dimensionSolveOrder;
  }


  public Dimension added(Boolean added) {
    
    this.added = added;
    return this;
  }

   /**
   * Get added
   * @return added
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAdded() {
    return added;
  }


  public void setAdded(Boolean added) {
    this.added = added;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dimension dimension = (Dimension) o;
    return Objects.equals(this.generations, dimension.generations) &&
        Objects.equals(this.levels, dimension.levels) &&
        Objects.equals(this.allowassociationChanges, dimension.allowassociationChanges) &&
        Objects.equals(this.allowFormulaChanges, dimension.allowFormulaChanges) &&
        Objects.equals(this.allowPropertyChanges, dimension.allowPropertyChanges) &&
        Objects.equals(this.allowUDAChanges, dimension.allowUDAChanges) &&
        Objects.equals(this.measureOptions, dimension.measureOptions) &&
        Objects.equals(this.aggregateLevelUsage, dimension.aggregateLevelUsage) &&
        Objects.equals(this.addMemberOption, dimension.addMemberOption) &&
        Objects.equals(this.attributeOptions, dimension.attributeOptions) &&
        Objects.equals(this.configOption, dimension.configOption) &&
        Objects.equals(this.unique, dimension.unique) &&
        Objects.equals(this.hierarchyType, dimension.hierarchyType) &&
        Objects.equals(this.sortOption, dimension.sortOption) &&
        Objects.equals(this.storageType, dimension.storageType) &&
        Objects.equals(this.type, dimension.type) &&
        Objects.equals(this.updateOption, dimension.updateOption) &&
        Objects.equals(this.allowMoves, dimension.allowMoves) &&
        Objects.equals(this.solveOrder, dimension.solveOrder) &&
        Objects.equals(this.createAttributeMembers, dimension.createAttributeMembers) &&
        Objects.equals(this.share, dimension.share) &&
        Objects.equals(this.incrementalSort, dimension.incrementalSort) &&
        Objects.equals(this.autoFixSharedMember, dimension.autoFixSharedMember) &&
        Objects.equals(this.flexible, dimension.flexible) &&
        Objects.equals(this.memberName, dimension.memberName) &&
        Objects.equals(this.name, dimension.name) &&
        Objects.equals(this.dimensionSolveOrder, dimension.dimensionSolveOrder) &&
        Objects.equals(this.added, dimension.added);
  }

  @Override
  public int hashCode() {
    return Objects.hash(generations, levels, allowassociationChanges, allowFormulaChanges, allowPropertyChanges, allowUDAChanges, measureOptions, aggregateLevelUsage, addMemberOption, attributeOptions, configOption, unique, hierarchyType, sortOption, storageType, type, updateOption, allowMoves, solveOrder, createAttributeMembers, share, incrementalSort, autoFixSharedMember, flexible, memberName, name, dimensionSolveOrder, added);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dimension {\n");
    sb.append("    generations: ").append(toIndentedString(generations)).append("\n");
    sb.append("    levels: ").append(toIndentedString(levels)).append("\n");
    sb.append("    allowassociationChanges: ").append(toIndentedString(allowassociationChanges)).append("\n");
    sb.append("    allowFormulaChanges: ").append(toIndentedString(allowFormulaChanges)).append("\n");
    sb.append("    allowPropertyChanges: ").append(toIndentedString(allowPropertyChanges)).append("\n");
    sb.append("    allowUDAChanges: ").append(toIndentedString(allowUDAChanges)).append("\n");
    sb.append("    measureOptions: ").append(toIndentedString(measureOptions)).append("\n");
    sb.append("    aggregateLevelUsage: ").append(toIndentedString(aggregateLevelUsage)).append("\n");
    sb.append("    addMemberOption: ").append(toIndentedString(addMemberOption)).append("\n");
    sb.append("    attributeOptions: ").append(toIndentedString(attributeOptions)).append("\n");
    sb.append("    configOption: ").append(toIndentedString(configOption)).append("\n");
    sb.append("    unique: ").append(toIndentedString(unique)).append("\n");
    sb.append("    hierarchyType: ").append(toIndentedString(hierarchyType)).append("\n");
    sb.append("    sortOption: ").append(toIndentedString(sortOption)).append("\n");
    sb.append("    storageType: ").append(toIndentedString(storageType)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    updateOption: ").append(toIndentedString(updateOption)).append("\n");
    sb.append("    allowMoves: ").append(toIndentedString(allowMoves)).append("\n");
    sb.append("    solveOrder: ").append(toIndentedString(solveOrder)).append("\n");
    sb.append("    createAttributeMembers: ").append(toIndentedString(createAttributeMembers)).append("\n");
    sb.append("    share: ").append(toIndentedString(share)).append("\n");
    sb.append("    incrementalSort: ").append(toIndentedString(incrementalSort)).append("\n");
    sb.append("    autoFixSharedMember: ").append(toIndentedString(autoFixSharedMember)).append("\n");
    sb.append("    flexible: ").append(toIndentedString(flexible)).append("\n");
    sb.append("    memberName: ").append(toIndentedString(memberName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dimensionSolveOrder: ").append(toIndentedString(dimensionSolveOrder)).append("\n");
    sb.append("    added: ").append(toIndentedString(added)).append("\n");
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

