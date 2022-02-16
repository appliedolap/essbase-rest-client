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
import com.appliedolap.essbase.client.model.ColumnSuppression;
import com.appliedolap.essbase.client.model.FormulaRetention;
import com.appliedolap.essbase.client.model.RowSuppression;
import com.appliedolap.essbase.client.model.ZoomIn;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * Preferences
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class Preferences {
  public static final String SERIALIZED_NAME_MAX_ROWS = "maxRows";
  @SerializedName(SERIALIZED_NAME_MAX_ROWS)
  private Long maxRows;

  /**
   * Gets or Sets indentation
   */
  @JsonAdapter(IndentationEnum.Adapter.class)
  public enum IndentationEnum {
    NONE("NONE"),
    
    SUBITEMS("SUBITEMS"),
    
    TOTALS("TOTALS");

    private String value;

    IndentationEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static IndentationEnum fromValue(String value) {
      for (IndentationEnum b : IndentationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<IndentationEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final IndentationEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public IndentationEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return IndentationEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_INDENTATION = "indentation";
  @SerializedName(SERIALIZED_NAME_INDENTATION)
  private IndentationEnum indentation;

  public static final String SERIALIZED_NAME_NAVIGATE = "navigate";
  @SerializedName(SERIALIZED_NAME_NAVIGATE)
  private Boolean navigate;

  public static final String SERIALIZED_NAME_INCLUDE_SELECTION = "includeSelection";
  @SerializedName(SERIALIZED_NAME_INCLUDE_SELECTION)
  private Boolean includeSelection;

  public static final String SERIALIZED_NAME_REPEAT_MEMBER_LABELS = "repeatMemberLabels";
  @SerializedName(SERIALIZED_NAME_REPEAT_MEMBER_LABELS)
  private Boolean repeatMemberLabels;

  public static final String SERIALIZED_NAME_WITHIN_SELECTED_GROUP = "withinSelectedGroup";
  @SerializedName(SERIALIZED_NAME_WITHIN_SELECTED_GROUP)
  private Boolean withinSelectedGroup;

  public static final String SERIALIZED_NAME_REMOVE_UN_SELECTED_GROUP = "removeUnSelectedGroup";
  @SerializedName(SERIALIZED_NAME_REMOVE_UN_SELECTED_GROUP)
  private Boolean removeUnSelectedGroup;

  public static final String SERIALIZED_NAME_INCLUDE_DESCRIPTION_LABEL = "includeDescriptionLabel";
  @SerializedName(SERIALIZED_NAME_INCLUDE_DESCRIPTION_LABEL)
  private Boolean includeDescriptionLabel;

  public static final String SERIALIZED_NAME_MISSING_TEXT = "missingText";
  @SerializedName(SERIALIZED_NAME_MISSING_TEXT)
  private String missingText;

  public static final String SERIALIZED_NAME_NO_ACCESS_TEXT = "noAccessText";
  @SerializedName(SERIALIZED_NAME_NO_ACCESS_TEXT)
  private String noAccessText;

  public static final String SERIALIZED_NAME_FORMULA_RETENTION = "formulaRetention";
  @SerializedName(SERIALIZED_NAME_FORMULA_RETENTION)
  private FormulaRetention formulaRetention;

  public static final String SERIALIZED_NAME_MAX_COLUMNS = "maxColumns";
  @SerializedName(SERIALIZED_NAME_MAX_COLUMNS)
  private Long maxColumns;

  public static final String SERIALIZED_NAME_ROW_SUPRESSION = "rowSupression";
  @SerializedName(SERIALIZED_NAME_ROW_SUPRESSION)
  private RowSuppression rowSupression;

  public static final String SERIALIZED_NAME_COLUMN_SUPRESSION = "columnSupression";
  @SerializedName(SERIALIZED_NAME_COLUMN_SUPRESSION)
  private ColumnSuppression columnSupression;

  public static final String SERIALIZED_NAME_CELL_TEXT = "cellText";
  @SerializedName(SERIALIZED_NAME_CELL_TEXT)
  private Boolean cellText;

  public static final String SERIALIZED_NAME_ZOOM_IN = "zoomIn";
  @SerializedName(SERIALIZED_NAME_ZOOM_IN)
  private ZoomIn zoomIn;


  public Preferences maxRows(Long maxRows) {
    
    this.maxRows = maxRows;
    return this;
  }

   /**
   * Get maxRows
   * @return maxRows
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getMaxRows() {
    return maxRows;
  }


  public void setMaxRows(Long maxRows) {
    this.maxRows = maxRows;
  }


  public Preferences indentation(IndentationEnum indentation) {
    
    this.indentation = indentation;
    return this;
  }

   /**
   * Get indentation
   * @return indentation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public IndentationEnum getIndentation() {
    return indentation;
  }


  public void setIndentation(IndentationEnum indentation) {
    this.indentation = indentation;
  }


  public Preferences navigate(Boolean navigate) {
    
    this.navigate = navigate;
    return this;
  }

   /**
   * Get navigate
   * @return navigate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getNavigate() {
    return navigate;
  }


  public void setNavigate(Boolean navigate) {
    this.navigate = navigate;
  }


  public Preferences includeSelection(Boolean includeSelection) {
    
    this.includeSelection = includeSelection;
    return this;
  }

   /**
   * Get includeSelection
   * @return includeSelection
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIncludeSelection() {
    return includeSelection;
  }


  public void setIncludeSelection(Boolean includeSelection) {
    this.includeSelection = includeSelection;
  }


  public Preferences repeatMemberLabels(Boolean repeatMemberLabels) {
    
    this.repeatMemberLabels = repeatMemberLabels;
    return this;
  }

   /**
   * Get repeatMemberLabels
   * @return repeatMemberLabels
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getRepeatMemberLabels() {
    return repeatMemberLabels;
  }


  public void setRepeatMemberLabels(Boolean repeatMemberLabels) {
    this.repeatMemberLabels = repeatMemberLabels;
  }


  public Preferences withinSelectedGroup(Boolean withinSelectedGroup) {
    
    this.withinSelectedGroup = withinSelectedGroup;
    return this;
  }

   /**
   * Get withinSelectedGroup
   * @return withinSelectedGroup
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getWithinSelectedGroup() {
    return withinSelectedGroup;
  }


  public void setWithinSelectedGroup(Boolean withinSelectedGroup) {
    this.withinSelectedGroup = withinSelectedGroup;
  }


  public Preferences removeUnSelectedGroup(Boolean removeUnSelectedGroup) {
    
    this.removeUnSelectedGroup = removeUnSelectedGroup;
    return this;
  }

   /**
   * Get removeUnSelectedGroup
   * @return removeUnSelectedGroup
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getRemoveUnSelectedGroup() {
    return removeUnSelectedGroup;
  }


  public void setRemoveUnSelectedGroup(Boolean removeUnSelectedGroup) {
    this.removeUnSelectedGroup = removeUnSelectedGroup;
  }


  public Preferences includeDescriptionLabel(Boolean includeDescriptionLabel) {
    
    this.includeDescriptionLabel = includeDescriptionLabel;
    return this;
  }

   /**
   * Get includeDescriptionLabel
   * @return includeDescriptionLabel
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getIncludeDescriptionLabel() {
    return includeDescriptionLabel;
  }


  public void setIncludeDescriptionLabel(Boolean includeDescriptionLabel) {
    this.includeDescriptionLabel = includeDescriptionLabel;
  }


  public Preferences missingText(String missingText) {
    
    this.missingText = missingText;
    return this;
  }

   /**
   * Get missingText
   * @return missingText
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getMissingText() {
    return missingText;
  }


  public void setMissingText(String missingText) {
    this.missingText = missingText;
  }


  public Preferences noAccessText(String noAccessText) {
    
    this.noAccessText = noAccessText;
    return this;
  }

   /**
   * Get noAccessText
   * @return noAccessText
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getNoAccessText() {
    return noAccessText;
  }


  public void setNoAccessText(String noAccessText) {
    this.noAccessText = noAccessText;
  }


  public Preferences formulaRetention(FormulaRetention formulaRetention) {
    
    this.formulaRetention = formulaRetention;
    return this;
  }

   /**
   * Get formulaRetention
   * @return formulaRetention
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public FormulaRetention getFormulaRetention() {
    return formulaRetention;
  }


  public void setFormulaRetention(FormulaRetention formulaRetention) {
    this.formulaRetention = formulaRetention;
  }


  public Preferences maxColumns(Long maxColumns) {
    
    this.maxColumns = maxColumns;
    return this;
  }

   /**
   * Get maxColumns
   * @return maxColumns
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getMaxColumns() {
    return maxColumns;
  }


  public void setMaxColumns(Long maxColumns) {
    this.maxColumns = maxColumns;
  }


  public Preferences rowSupression(RowSuppression rowSupression) {
    
    this.rowSupression = rowSupression;
    return this;
  }

   /**
   * Get rowSupression
   * @return rowSupression
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public RowSuppression getRowSupression() {
    return rowSupression;
  }


  public void setRowSupression(RowSuppression rowSupression) {
    this.rowSupression = rowSupression;
  }


  public Preferences columnSupression(ColumnSuppression columnSupression) {
    
    this.columnSupression = columnSupression;
    return this;
  }

   /**
   * Get columnSupression
   * @return columnSupression
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ColumnSuppression getColumnSupression() {
    return columnSupression;
  }


  public void setColumnSupression(ColumnSuppression columnSupression) {
    this.columnSupression = columnSupression;
  }


  public Preferences cellText(Boolean cellText) {
    
    this.cellText = cellText;
    return this;
  }

   /**
   * Get cellText
   * @return cellText
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getCellText() {
    return cellText;
  }


  public void setCellText(Boolean cellText) {
    this.cellText = cellText;
  }


  public Preferences zoomIn(ZoomIn zoomIn) {
    
    this.zoomIn = zoomIn;
    return this;
  }

   /**
   * Get zoomIn
   * @return zoomIn
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ZoomIn getZoomIn() {
    return zoomIn;
  }


  public void setZoomIn(ZoomIn zoomIn) {
    this.zoomIn = zoomIn;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Preferences preferences = (Preferences) o;
    return Objects.equals(this.maxRows, preferences.maxRows) &&
        Objects.equals(this.indentation, preferences.indentation) &&
        Objects.equals(this.navigate, preferences.navigate) &&
        Objects.equals(this.includeSelection, preferences.includeSelection) &&
        Objects.equals(this.repeatMemberLabels, preferences.repeatMemberLabels) &&
        Objects.equals(this.withinSelectedGroup, preferences.withinSelectedGroup) &&
        Objects.equals(this.removeUnSelectedGroup, preferences.removeUnSelectedGroup) &&
        Objects.equals(this.includeDescriptionLabel, preferences.includeDescriptionLabel) &&
        Objects.equals(this.missingText, preferences.missingText) &&
        Objects.equals(this.noAccessText, preferences.noAccessText) &&
        Objects.equals(this.formulaRetention, preferences.formulaRetention) &&
        Objects.equals(this.maxColumns, preferences.maxColumns) &&
        Objects.equals(this.rowSupression, preferences.rowSupression) &&
        Objects.equals(this.columnSupression, preferences.columnSupression) &&
        Objects.equals(this.cellText, preferences.cellText) &&
        Objects.equals(this.zoomIn, preferences.zoomIn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxRows, indentation, navigate, includeSelection, repeatMemberLabels, withinSelectedGroup, removeUnSelectedGroup, includeDescriptionLabel, missingText, noAccessText, formulaRetention, maxColumns, rowSupression, columnSupression, cellText, zoomIn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Preferences {\n");
    sb.append("    maxRows: ").append(toIndentedString(maxRows)).append("\n");
    sb.append("    indentation: ").append(toIndentedString(indentation)).append("\n");
    sb.append("    navigate: ").append(toIndentedString(navigate)).append("\n");
    sb.append("    includeSelection: ").append(toIndentedString(includeSelection)).append("\n");
    sb.append("    repeatMemberLabels: ").append(toIndentedString(repeatMemberLabels)).append("\n");
    sb.append("    withinSelectedGroup: ").append(toIndentedString(withinSelectedGroup)).append("\n");
    sb.append("    removeUnSelectedGroup: ").append(toIndentedString(removeUnSelectedGroup)).append("\n");
    sb.append("    includeDescriptionLabel: ").append(toIndentedString(includeDescriptionLabel)).append("\n");
    sb.append("    missingText: ").append(toIndentedString(missingText)).append("\n");
    sb.append("    noAccessText: ").append(toIndentedString(noAccessText)).append("\n");
    sb.append("    formulaRetention: ").append(toIndentedString(formulaRetention)).append("\n");
    sb.append("    maxColumns: ").append(toIndentedString(maxColumns)).append("\n");
    sb.append("    rowSupression: ").append(toIndentedString(rowSupression)).append("\n");
    sb.append("    columnSupression: ").append(toIndentedString(columnSupression)).append("\n");
    sb.append("    cellText: ").append(toIndentedString(cellText)).append("\n");
    sb.append("    zoomIn: ").append(toIndentedString(zoomIn)).append("\n");
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

