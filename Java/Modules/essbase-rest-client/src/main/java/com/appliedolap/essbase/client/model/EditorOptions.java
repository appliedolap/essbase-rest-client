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
 * EditorOptions
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-06-29T09:19:09.255-07:00[America/Los_Angeles]")
public class EditorOptions {
  public static final String SERIALIZED_NAME_APPLICATION = "application";
  @SerializedName(SERIALIZED_NAME_APPLICATION)
  private String application;

  public static final String SERIALIZED_NAME_DATABASE = "database";
  @SerializedName(SERIALIZED_NAME_DATABASE)
  private String database;

  public static final String SERIALIZED_NAME_SERVER = "server";
  @SerializedName(SERIALIZED_NAME_SERVER)
  private String server;

  public static final String SERIALIZED_NAME_OBJECT = "object";
  @SerializedName(SERIALIZED_NAME_OBJECT)
  private String _object;

  public static final String SERIALIZED_NAME_DATA_FILE = "dataFile";
  @SerializedName(SERIALIZED_NAME_DATA_FILE)
  private String dataFile;

  public static final String SERIALIZED_NAME_DATA_FILE_APPLICATION = "dataFileApplication";
  @SerializedName(SERIALIZED_NAME_DATA_FILE_APPLICATION)
  private String dataFileApplication;

  public static final String SERIALIZED_NAME_DATA_FILE_DATABASE = "dataFileDatabase";
  @SerializedName(SERIALIZED_NAME_DATA_FILE_DATABASE)
  private String dataFileDatabase;

  public static final String SERIALIZED_NAME_DATA_FILE_SERVER = "dataFileServer";
  @SerializedName(SERIALIZED_NAME_DATA_FILE_SERVER)
  private String dataFileServer;

  public static final String SERIALIZED_NAME_START_RECORD = "startRecord";
  @SerializedName(SERIALIZED_NAME_START_RECORD)
  private Integer startRecord;

  public static final String SERIALIZED_NAME_VIEW_COUNT = "viewCount";
  @SerializedName(SERIALIZED_NAME_VIEW_COUNT)
  private Integer viewCount;

  public static final String SERIALIZED_NAME_VIEW_GRID_LINES = "viewGridLines";
  @SerializedName(SERIALIZED_NAME_VIEW_GRID_LINES)
  private Boolean viewGridLines;

  public static final String SERIALIZED_NAME_VIEW_IGNORED = "viewIgnored";
  @SerializedName(SERIALIZED_NAME_VIEW_IGNORED)
  private Boolean viewIgnored;

  public static final String SERIALIZED_NAME_VIEW_RAW_DATA = "viewRawData";
  @SerializedName(SERIALIZED_NAME_VIEW_RAW_DATA)
  private Boolean viewRawData;

  public static final String SERIALIZED_NAME_VIEW_TOOLBAR = "viewToolbar";
  @SerializedName(SERIALIZED_NAME_VIEW_TOOLBAR)
  private Boolean viewToolbar;

  /**
   * Gets or Sets viewMode
   */
  @JsonAdapter(ViewModeEnum.Adapter.class)
  public enum ViewModeEnum {
    DIMBUILD("DIMBUILD"),
    
    DATALOAD("DATALOAD");

    private String value;

    ViewModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ViewModeEnum fromValue(String value) {
      for (ViewModeEnum b : ViewModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ViewModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ViewModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ViewModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ViewModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_VIEW_MODE = "viewMode";
  @SerializedName(SERIALIZED_NAME_VIEW_MODE)
  private ViewModeEnum viewMode;

  /**
   * Gets or Sets dataFileType
   */
  @JsonAdapter(DataFileTypeEnum.Adapter.class)
  public enum DataFileTypeEnum {
    NONE("NONE"),
    
    EXCEL("EXCEL"),
    
    LOTUS2("LOTUS2"),
    
    LOTUS3("LOTUS3"),
    
    LOTUS4("LOTUS4"),
    
    TEXT("TEXT");

    private String value;

    DataFileTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DataFileTypeEnum fromValue(String value) {
      for (DataFileTypeEnum b : DataFileTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DataFileTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DataFileTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DataFileTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DataFileTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DATA_FILE_TYPE = "dataFileType";
  @SerializedName(SERIALIZED_NAME_DATA_FILE_TYPE)
  private DataFileTypeEnum dataFileType;


  public EditorOptions application(String application) {
    
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


  public EditorOptions database(String database) {
    
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


  public EditorOptions server(String server) {
    
    this.server = server;
    return this;
  }

   /**
   * Get server
   * @return server
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getServer() {
    return server;
  }


  public void setServer(String server) {
    this.server = server;
  }


  public EditorOptions _object(String _object) {
    
    this._object = _object;
    return this;
  }

   /**
   * Get _object
   * @return _object
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getObject() {
    return _object;
  }


  public void setObject(String _object) {
    this._object = _object;
  }


  public EditorOptions dataFile(String dataFile) {
    
    this.dataFile = dataFile;
    return this;
  }

   /**
   * Get dataFile
   * @return dataFile
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDataFile() {
    return dataFile;
  }


  public void setDataFile(String dataFile) {
    this.dataFile = dataFile;
  }


  public EditorOptions dataFileApplication(String dataFileApplication) {
    
    this.dataFileApplication = dataFileApplication;
    return this;
  }

   /**
   * Get dataFileApplication
   * @return dataFileApplication
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDataFileApplication() {
    return dataFileApplication;
  }


  public void setDataFileApplication(String dataFileApplication) {
    this.dataFileApplication = dataFileApplication;
  }


  public EditorOptions dataFileDatabase(String dataFileDatabase) {
    
    this.dataFileDatabase = dataFileDatabase;
    return this;
  }

   /**
   * Get dataFileDatabase
   * @return dataFileDatabase
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDataFileDatabase() {
    return dataFileDatabase;
  }


  public void setDataFileDatabase(String dataFileDatabase) {
    this.dataFileDatabase = dataFileDatabase;
  }


  public EditorOptions dataFileServer(String dataFileServer) {
    
    this.dataFileServer = dataFileServer;
    return this;
  }

   /**
   * Get dataFileServer
   * @return dataFileServer
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDataFileServer() {
    return dataFileServer;
  }


  public void setDataFileServer(String dataFileServer) {
    this.dataFileServer = dataFileServer;
  }


  public EditorOptions startRecord(Integer startRecord) {
    
    this.startRecord = startRecord;
    return this;
  }

   /**
   * Get startRecord
   * @return startRecord
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getStartRecord() {
    return startRecord;
  }


  public void setStartRecord(Integer startRecord) {
    this.startRecord = startRecord;
  }


  public EditorOptions viewCount(Integer viewCount) {
    
    this.viewCount = viewCount;
    return this;
  }

   /**
   * Get viewCount
   * @return viewCount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getViewCount() {
    return viewCount;
  }


  public void setViewCount(Integer viewCount) {
    this.viewCount = viewCount;
  }


  public EditorOptions viewGridLines(Boolean viewGridLines) {
    
    this.viewGridLines = viewGridLines;
    return this;
  }

   /**
   * Get viewGridLines
   * @return viewGridLines
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getViewGridLines() {
    return viewGridLines;
  }


  public void setViewGridLines(Boolean viewGridLines) {
    this.viewGridLines = viewGridLines;
  }


  public EditorOptions viewIgnored(Boolean viewIgnored) {
    
    this.viewIgnored = viewIgnored;
    return this;
  }

   /**
   * Get viewIgnored
   * @return viewIgnored
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getViewIgnored() {
    return viewIgnored;
  }


  public void setViewIgnored(Boolean viewIgnored) {
    this.viewIgnored = viewIgnored;
  }


  public EditorOptions viewRawData(Boolean viewRawData) {
    
    this.viewRawData = viewRawData;
    return this;
  }

   /**
   * Get viewRawData
   * @return viewRawData
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getViewRawData() {
    return viewRawData;
  }


  public void setViewRawData(Boolean viewRawData) {
    this.viewRawData = viewRawData;
  }


  public EditorOptions viewToolbar(Boolean viewToolbar) {
    
    this.viewToolbar = viewToolbar;
    return this;
  }

   /**
   * Get viewToolbar
   * @return viewToolbar
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getViewToolbar() {
    return viewToolbar;
  }


  public void setViewToolbar(Boolean viewToolbar) {
    this.viewToolbar = viewToolbar;
  }


  public EditorOptions viewMode(ViewModeEnum viewMode) {
    
    this.viewMode = viewMode;
    return this;
  }

   /**
   * Get viewMode
   * @return viewMode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ViewModeEnum getViewMode() {
    return viewMode;
  }


  public void setViewMode(ViewModeEnum viewMode) {
    this.viewMode = viewMode;
  }


  public EditorOptions dataFileType(DataFileTypeEnum dataFileType) {
    
    this.dataFileType = dataFileType;
    return this;
  }

   /**
   * Get dataFileType
   * @return dataFileType
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public DataFileTypeEnum getDataFileType() {
    return dataFileType;
  }


  public void setDataFileType(DataFileTypeEnum dataFileType) {
    this.dataFileType = dataFileType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EditorOptions editorOptions = (EditorOptions) o;
    return Objects.equals(this.application, editorOptions.application) &&
        Objects.equals(this.database, editorOptions.database) &&
        Objects.equals(this.server, editorOptions.server) &&
        Objects.equals(this._object, editorOptions._object) &&
        Objects.equals(this.dataFile, editorOptions.dataFile) &&
        Objects.equals(this.dataFileApplication, editorOptions.dataFileApplication) &&
        Objects.equals(this.dataFileDatabase, editorOptions.dataFileDatabase) &&
        Objects.equals(this.dataFileServer, editorOptions.dataFileServer) &&
        Objects.equals(this.startRecord, editorOptions.startRecord) &&
        Objects.equals(this.viewCount, editorOptions.viewCount) &&
        Objects.equals(this.viewGridLines, editorOptions.viewGridLines) &&
        Objects.equals(this.viewIgnored, editorOptions.viewIgnored) &&
        Objects.equals(this.viewRawData, editorOptions.viewRawData) &&
        Objects.equals(this.viewToolbar, editorOptions.viewToolbar) &&
        Objects.equals(this.viewMode, editorOptions.viewMode) &&
        Objects.equals(this.dataFileType, editorOptions.dataFileType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(application, database, server, _object, dataFile, dataFileApplication, dataFileDatabase, dataFileServer, startRecord, viewCount, viewGridLines, viewIgnored, viewRawData, viewToolbar, viewMode, dataFileType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EditorOptions {\n");
    sb.append("    application: ").append(toIndentedString(application)).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
    sb.append("    server: ").append(toIndentedString(server)).append("\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    dataFile: ").append(toIndentedString(dataFile)).append("\n");
    sb.append("    dataFileApplication: ").append(toIndentedString(dataFileApplication)).append("\n");
    sb.append("    dataFileDatabase: ").append(toIndentedString(dataFileDatabase)).append("\n");
    sb.append("    dataFileServer: ").append(toIndentedString(dataFileServer)).append("\n");
    sb.append("    startRecord: ").append(toIndentedString(startRecord)).append("\n");
    sb.append("    viewCount: ").append(toIndentedString(viewCount)).append("\n");
    sb.append("    viewGridLines: ").append(toIndentedString(viewGridLines)).append("\n");
    sb.append("    viewIgnored: ").append(toIndentedString(viewIgnored)).append("\n");
    sb.append("    viewRawData: ").append(toIndentedString(viewRawData)).append("\n");
    sb.append("    viewToolbar: ").append(toIndentedString(viewToolbar)).append("\n");
    sb.append("    viewMode: ").append(toIndentedString(viewMode)).append("\n");
    sb.append("    dataFileType: ").append(toIndentedString(dataFileType)).append("\n");
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

