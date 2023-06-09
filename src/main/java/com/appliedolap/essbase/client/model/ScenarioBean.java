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
import com.appliedolap.essbase.client.model.ApproverBean;
import com.appliedolap.essbase.client.model.Link;
import com.appliedolap.essbase.client.model.ParticipantBean;
import com.appliedolap.essbase.client.model.ScriptBean;
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
 * ScenarioBean
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class ScenarioBean {
  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;

  public static final String SERIALIZED_NAME_SCENARIO_USER = "scenarioUser";
  @SerializedName(SERIALIZED_NAME_SCENARIO_USER)
  private Boolean scenarioUser;

  public static final String SERIALIZED_NAME_SCRIPTS = "scripts";
  @SerializedName(SERIALIZED_NAME_SCRIPTS)
  private List<ScriptBean> scripts = null;

  public static final String SERIALIZED_NAME_DATABASE = "database";
  @SerializedName(SERIALIZED_NAME_DATABASE)
  private String database;

  public static final String SERIALIZED_NAME_APPLICATION = "application";
  @SerializedName(SERIALIZED_NAME_APPLICATION)
  private String application;

  public static final String SERIALIZED_NAME_PARTICIPANTS = "participants";
  @SerializedName(SERIALIZED_NAME_PARTICIPANTS)
  private List<ParticipantBean> participants = null;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_CREATED_TIME = "createdTime";
  @SerializedName(SERIALIZED_NAME_CREATED_TIME)
  private Long createdTime;

  public static final String SERIALIZED_NAME_OVERDUE = "overdue";
  @SerializedName(SERIALIZED_NAME_OVERDUE)
  private Boolean overdue;

  public static final String SERIALIZED_NAME_SUBMITTED_TIME = "submittedTime";
  @SerializedName(SERIALIZED_NAME_SUBMITTED_TIME)
  private Long submittedTime;

  public static final String SERIALIZED_NAME_APPLIED_TIME = "appliedTime";
  @SerializedName(SERIALIZED_NAME_APPLIED_TIME)
  private Long appliedTime;

  public static final String SERIALIZED_NAME_REFRESHED_TIME = "refreshedTime";
  @SerializedName(SERIALIZED_NAME_REFRESHED_TIME)
  private Long refreshedTime;

  public static final String SERIALIZED_NAME_COMMENTS_COUNT = "commentsCount";
  @SerializedName(SERIALIZED_NAME_COMMENTS_COUNT)
  private Integer commentsCount;

  public static final String SERIALIZED_NAME_USE_CALCULATED_VALUES = "useCalculatedValues";
  @SerializedName(SERIALIZED_NAME_USE_CALCULATED_VALUES)
  private Boolean useCalculatedValues;

  public static final String SERIALIZED_NAME_OWNER = "owner";
  @SerializedName(SERIALIZED_NAME_OWNER)
  private String owner;

  public static final String SERIALIZED_NAME_APPROVERS = "approvers";
  @SerializedName(SERIALIZED_NAME_APPROVERS)
  private List<ApproverBean> approvers = null;

  public static final String SERIALIZED_NAME_SANDBOX = "sandbox";
  @SerializedName(SERIALIZED_NAME_SANDBOX)
  private String sandbox;

  public static final String SERIALIZED_NAME_DUE_DATE = "dueDate";
  @SerializedName(SERIALIZED_NAME_DUE_DATE)
  private Long dueDate;

  /**
   * Gets or Sets state
   */
  @JsonAdapter(StateEnum.Adapter.class)
  public enum StateEnum {
    NEW("NEW"),
    
    SUBMITTED("SUBMITTED"),
    
    APPROVED("APPROVED"),
    
    REJECTED("REJECTED"),
    
    APPLIED("APPLIED");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StateEnum fromValue(String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StateEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StateEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATE = "state";
  @SerializedName(SERIALIZED_NAME_STATE)
  private StateEnum state;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Long id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * Gets or Sets priority
   */
  @JsonAdapter(PriorityEnum.Adapter.class)
  public enum PriorityEnum {
    LOW("LOW"),
    
    MEDIUM("MEDIUM"),
    
    HIGH("HIGH");

    private String value;

    PriorityEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PriorityEnum fromValue(String value) {
      for (PriorityEnum b : PriorityEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<PriorityEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PriorityEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PriorityEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return PriorityEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_PRIORITY = "priority";
  @SerializedName(SERIALIZED_NAME_PRIORITY)
  private PriorityEnum priority;


  public ScenarioBean links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public ScenarioBean addLinksItem(Link linksItem) {
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


  public ScenarioBean scenarioUser(Boolean scenarioUser) {
    
    this.scenarioUser = scenarioUser;
    return this;
  }

   /**
   * Get scenarioUser
   * @return scenarioUser
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getScenarioUser() {
    return scenarioUser;
  }


  public void setScenarioUser(Boolean scenarioUser) {
    this.scenarioUser = scenarioUser;
  }


  public ScenarioBean scripts(List<ScriptBean> scripts) {
    
    this.scripts = scripts;
    return this;
  }

  public ScenarioBean addScriptsItem(ScriptBean scriptsItem) {
    if (this.scripts == null) {
      this.scripts = new ArrayList<ScriptBean>();
    }
    this.scripts.add(scriptsItem);
    return this;
  }

   /**
   * Get scripts
   * @return scripts
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ScriptBean> getScripts() {
    return scripts;
  }


  public void setScripts(List<ScriptBean> scripts) {
    this.scripts = scripts;
  }


  public ScenarioBean database(String database) {
    
    this.database = database;
    return this;
  }

   /**
   * Get database
   * @return database
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDatabase() {
    return database;
  }


  public void setDatabase(String database) {
    this.database = database;
  }


  public ScenarioBean application(String application) {
    
    this.application = application;
    return this;
  }

   /**
   * Get application
   * @return application
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getApplication() {
    return application;
  }


  public void setApplication(String application) {
    this.application = application;
  }


  public ScenarioBean participants(List<ParticipantBean> participants) {
    
    this.participants = participants;
    return this;
  }

  public ScenarioBean addParticipantsItem(ParticipantBean participantsItem) {
    if (this.participants == null) {
      this.participants = new ArrayList<ParticipantBean>();
    }
    this.participants.add(participantsItem);
    return this;
  }

   /**
   * Get participants
   * @return participants
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ParticipantBean> getParticipants() {
    return participants;
  }


  public void setParticipants(List<ParticipantBean> participants) {
    this.participants = participants;
  }


  public ScenarioBean description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ScenarioBean createdTime(Long createdTime) {
    
    this.createdTime = createdTime;
    return this;
  }

   /**
   * Get createdTime
   * @return createdTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getCreatedTime() {
    return createdTime;
  }


  public void setCreatedTime(Long createdTime) {
    this.createdTime = createdTime;
  }


  public ScenarioBean overdue(Boolean overdue) {
    
    this.overdue = overdue;
    return this;
  }

   /**
   * Get overdue
   * @return overdue
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getOverdue() {
    return overdue;
  }


  public void setOverdue(Boolean overdue) {
    this.overdue = overdue;
  }


  public ScenarioBean submittedTime(Long submittedTime) {
    
    this.submittedTime = submittedTime;
    return this;
  }

   /**
   * Get submittedTime
   * @return submittedTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getSubmittedTime() {
    return submittedTime;
  }


  public void setSubmittedTime(Long submittedTime) {
    this.submittedTime = submittedTime;
  }


  public ScenarioBean appliedTime(Long appliedTime) {
    
    this.appliedTime = appliedTime;
    return this;
  }

   /**
   * Get appliedTime
   * @return appliedTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getAppliedTime() {
    return appliedTime;
  }


  public void setAppliedTime(Long appliedTime) {
    this.appliedTime = appliedTime;
  }


  public ScenarioBean refreshedTime(Long refreshedTime) {
    
    this.refreshedTime = refreshedTime;
    return this;
  }

   /**
   * Get refreshedTime
   * @return refreshedTime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getRefreshedTime() {
    return refreshedTime;
  }


  public void setRefreshedTime(Long refreshedTime) {
    this.refreshedTime = refreshedTime;
  }


  public ScenarioBean commentsCount(Integer commentsCount) {
    
    this.commentsCount = commentsCount;
    return this;
  }

   /**
   * Get commentsCount
   * @return commentsCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getCommentsCount() {
    return commentsCount;
  }


  public void setCommentsCount(Integer commentsCount) {
    this.commentsCount = commentsCount;
  }


  public ScenarioBean useCalculatedValues(Boolean useCalculatedValues) {
    
    this.useCalculatedValues = useCalculatedValues;
    return this;
  }

   /**
   * Get useCalculatedValues
   * @return useCalculatedValues
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getUseCalculatedValues() {
    return useCalculatedValues;
  }


  public void setUseCalculatedValues(Boolean useCalculatedValues) {
    this.useCalculatedValues = useCalculatedValues;
  }


  public ScenarioBean owner(String owner) {
    
    this.owner = owner;
    return this;
  }

   /**
   * Get owner
   * @return owner
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getOwner() {
    return owner;
  }


  public void setOwner(String owner) {
    this.owner = owner;
  }


  public ScenarioBean approvers(List<ApproverBean> approvers) {
    
    this.approvers = approvers;
    return this;
  }

  public ScenarioBean addApproversItem(ApproverBean approversItem) {
    if (this.approvers == null) {
      this.approvers = new ArrayList<ApproverBean>();
    }
    this.approvers.add(approversItem);
    return this;
  }

   /**
   * Get approvers
   * @return approvers
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<ApproverBean> getApprovers() {
    return approvers;
  }


  public void setApprovers(List<ApproverBean> approvers) {
    this.approvers = approvers;
  }


  public ScenarioBean sandbox(String sandbox) {
    
    this.sandbox = sandbox;
    return this;
  }

   /**
   * Get sandbox
   * @return sandbox
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSandbox() {
    return sandbox;
  }


  public void setSandbox(String sandbox) {
    this.sandbox = sandbox;
  }


  public ScenarioBean dueDate(Long dueDate) {
    
    this.dueDate = dueDate;
    return this;
  }

   /**
   * Get dueDate
   * @return dueDate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getDueDate() {
    return dueDate;
  }


  public void setDueDate(Long dueDate) {
    this.dueDate = dueDate;
  }


  public ScenarioBean state(StateEnum state) {
    
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public StateEnum getState() {
    return state;
  }


  public void setState(StateEnum state) {
    this.state = state;
  }


  public ScenarioBean id(Long id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public ScenarioBean name(String name) {
    
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


  public ScenarioBean priority(PriorityEnum priority) {
    
    this.priority = priority;
    return this;
  }

   /**
   * Get priority
   * @return priority
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public PriorityEnum getPriority() {
    return priority;
  }


  public void setPriority(PriorityEnum priority) {
    this.priority = priority;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ScenarioBean scenarioBean = (ScenarioBean) o;
    return Objects.equals(this.links, scenarioBean.links) &&
        Objects.equals(this.scenarioUser, scenarioBean.scenarioUser) &&
        Objects.equals(this.scripts, scenarioBean.scripts) &&
        Objects.equals(this.database, scenarioBean.database) &&
        Objects.equals(this.application, scenarioBean.application) &&
        Objects.equals(this.participants, scenarioBean.participants) &&
        Objects.equals(this.description, scenarioBean.description) &&
        Objects.equals(this.createdTime, scenarioBean.createdTime) &&
        Objects.equals(this.overdue, scenarioBean.overdue) &&
        Objects.equals(this.submittedTime, scenarioBean.submittedTime) &&
        Objects.equals(this.appliedTime, scenarioBean.appliedTime) &&
        Objects.equals(this.refreshedTime, scenarioBean.refreshedTime) &&
        Objects.equals(this.commentsCount, scenarioBean.commentsCount) &&
        Objects.equals(this.useCalculatedValues, scenarioBean.useCalculatedValues) &&
        Objects.equals(this.owner, scenarioBean.owner) &&
        Objects.equals(this.approvers, scenarioBean.approvers) &&
        Objects.equals(this.sandbox, scenarioBean.sandbox) &&
        Objects.equals(this.dueDate, scenarioBean.dueDate) &&
        Objects.equals(this.state, scenarioBean.state) &&
        Objects.equals(this.id, scenarioBean.id) &&
        Objects.equals(this.name, scenarioBean.name) &&
        Objects.equals(this.priority, scenarioBean.priority);
  }

  @Override
  public int hashCode() {
    return Objects.hash(links, scenarioUser, scripts, database, application, participants, description, createdTime, overdue, submittedTime, appliedTime, refreshedTime, commentsCount, useCalculatedValues, owner, approvers, sandbox, dueDate, state, id, name, priority);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScenarioBean {\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    scenarioUser: ").append(toIndentedString(scenarioUser)).append("\n");
    sb.append("    scripts: ").append(toIndentedString(scripts)).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    participants: ").append(toIndentedString(participants)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
    sb.append("    overdue: ").append(toIndentedString(overdue)).append("\n");
    sb.append("    submittedTime: ").append(toIndentedString(submittedTime)).append("\n");
    sb.append("    appliedTime: ").append(toIndentedString(appliedTime)).append("\n");
    sb.append("    refreshedTime: ").append(toIndentedString(refreshedTime)).append("\n");
    sb.append("    commentsCount: ").append(toIndentedString(commentsCount)).append("\n");
    sb.append("    useCalculatedValues: ").append(toIndentedString(useCalculatedValues)).append("\n");
    sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
    sb.append("    approvers: ").append(toIndentedString(approvers)).append("\n");
    sb.append("    sandbox: ").append(toIndentedString(sandbox)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
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

