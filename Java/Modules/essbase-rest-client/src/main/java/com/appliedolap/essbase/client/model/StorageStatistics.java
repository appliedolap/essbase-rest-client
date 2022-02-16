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
import com.appliedolap.essbase.client.model.StatisticsDimensions;
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
 * StorageStatistics
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class StorageStatistics {
  public static final String SERIALIZED_NAME_DIMENSIONS = "dimensions";
  @SerializedName(SERIALIZED_NAME_DIMENSIONS)
  private List<StatisticsDimensions> dimensions = null;

  public static final String SERIALIZED_NAME_MAX_KEY_LENGTH_BITS = "maxKeyLengthBits";
  @SerializedName(SERIALIZED_NAME_MAX_KEY_LENGTH_BITS)
  private Integer maxKeyLengthBits;

  public static final String SERIALIZED_NAME_MAX_KEY_LENGTH_BYTES = "maxKeyLengthBytes";
  @SerializedName(SERIALIZED_NAME_MAX_KEY_LENGTH_BYTES)
  private Integer maxKeyLengthBytes;

  public static final String SERIALIZED_NAME_NUMBER_OF_INPUT_LEVEL_CELLS = "numberOfInputLevelCells";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_INPUT_LEVEL_CELLS)
  private Integer numberOfInputLevelCells;

  public static final String SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_DATA_SLICES = "numberOfIncrementalDataSlices";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_DATA_SLICES)
  private Integer numberOfIncrementalDataSlices;

  public static final String SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_INPUT_CELLS = "numberOfIncrementalInputCells";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_INPUT_CELLS)
  private Integer numberOfIncrementalInputCells;

  public static final String SERIALIZED_NAME_NUMBER_OF_AGGREGATE_VIEWS = "numberOfAggregateViews";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_AGGREGATE_VIEWS)
  private Integer numberOfAggregateViews;

  public static final String SERIALIZED_NAME_NUMBER_OF_AGGREGATE_CELLS = "numberOfAggregateCells";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_AGGREGATE_CELLS)
  private Integer numberOfAggregateCells;

  public static final String SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_AGGREGATE_CELLS = "numberOfIncrementalAggregateCells";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_INCREMENTAL_AGGREGATE_CELLS)
  private Integer numberOfIncrementalAggregateCells;

  public static final String SERIALIZED_NAME_COST_OF_QUERYING_INCREMENTAL_DATA = "costOfQueryingIncrementalData";
  @SerializedName(SERIALIZED_NAME_COST_OF_QUERYING_INCREMENTAL_DATA)
  private Double costOfQueryingIncrementalData;

  public static final String SERIALIZED_NAME_INPUT_LEVEL_DATA_SIZE = "inputLevelDataSize";
  @SerializedName(SERIALIZED_NAME_INPUT_LEVEL_DATA_SIZE)
  private Integer inputLevelDataSize;

  public static final String SERIALIZED_NAME_AGGREGATE_DATA_SIZE = "aggregateDataSize";
  @SerializedName(SERIALIZED_NAME_AGGREGATE_DATA_SIZE)
  private Integer aggregateDataSize;

  public static final String SERIALIZED_NAME_NUMBER_OF_EXISTING_BLOCKS = "numberOfExistingBlocks";
  @SerializedName(SERIALIZED_NAME_NUMBER_OF_EXISTING_BLOCKS)
  private Double numberOfExistingBlocks;

  public static final String SERIALIZED_NAME_BLOCK_SIZE = "blockSize";
  @SerializedName(SERIALIZED_NAME_BLOCK_SIZE)
  private Integer blockSize;

  public static final String SERIALIZED_NAME_POTENTIAL_NUMBER_OF_BLOCKS = "potentialNumberOfBlocks";
  @SerializedName(SERIALIZED_NAME_POTENTIAL_NUMBER_OF_BLOCKS)
  private Double potentialNumberOfBlocks;

  public static final String SERIALIZED_NAME_EXISTING_LEVEL_ZERO_BLOCKS = "existingLevelZeroBlocks";
  @SerializedName(SERIALIZED_NAME_EXISTING_LEVEL_ZERO_BLOCKS)
  private Double existingLevelZeroBlocks;

  public static final String SERIALIZED_NAME_EXISTING_UPPER_LEVEL_BLOCKS = "existingUpperLevelBlocks";
  @SerializedName(SERIALIZED_NAME_EXISTING_UPPER_LEVEL_BLOCKS)
  private Double existingUpperLevelBlocks;

  public static final String SERIALIZED_NAME_BLOCK_DENSITY = "blockDensity";
  @SerializedName(SERIALIZED_NAME_BLOCK_DENSITY)
  private Double blockDensity;

  public static final String SERIALIZED_NAME_PERCENTAGE_OF_MAXIMUM_BLOCKS_EXISTING = "percentageOfMaximumBlocksExisting";
  @SerializedName(SERIALIZED_NAME_PERCENTAGE_OF_MAXIMUM_BLOCKS_EXISTING)
  private Double percentageOfMaximumBlocksExisting;

  public static final String SERIALIZED_NAME_COMPRESSION_RATIO = "compressionRatio";
  @SerializedName(SERIALIZED_NAME_COMPRESSION_RATIO)
  private Double compressionRatio;

  public static final String SERIALIZED_NAME_AVERAGE_CLUSTERING_RATIO = "averageClusteringRatio";
  @SerializedName(SERIALIZED_NAME_AVERAGE_CLUSTERING_RATIO)
  private Double averageClusteringRatio;

  public static final String SERIALIZED_NAME_PAGE_FILE_SIZE = "pageFileSize";
  @SerializedName(SERIALIZED_NAME_PAGE_FILE_SIZE)
  private Long pageFileSize;

  public static final String SERIALIZED_NAME_INDEX_FILE_SIZE = "indexFileSize";
  @SerializedName(SERIALIZED_NAME_INDEX_FILE_SIZE)
  private Long indexFileSize;


  public StorageStatistics dimensions(List<StatisticsDimensions> dimensions) {
    
    this.dimensions = dimensions;
    return this;
  }

  public StorageStatistics addDimensionsItem(StatisticsDimensions dimensionsItem) {
    if (this.dimensions == null) {
      this.dimensions = new ArrayList<StatisticsDimensions>();
    }
    this.dimensions.add(dimensionsItem);
    return this;
  }

   /**
   * Get dimensions
   * @return dimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<StatisticsDimensions> getDimensions() {
    return dimensions;
  }


  public void setDimensions(List<StatisticsDimensions> dimensions) {
    this.dimensions = dimensions;
  }


  public StorageStatistics maxKeyLengthBits(Integer maxKeyLengthBits) {
    
    this.maxKeyLengthBits = maxKeyLengthBits;
    return this;
  }

   /**
   * Get maxKeyLengthBits
   * @return maxKeyLengthBits
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getMaxKeyLengthBits() {
    return maxKeyLengthBits;
  }


  public void setMaxKeyLengthBits(Integer maxKeyLengthBits) {
    this.maxKeyLengthBits = maxKeyLengthBits;
  }


  public StorageStatistics maxKeyLengthBytes(Integer maxKeyLengthBytes) {
    
    this.maxKeyLengthBytes = maxKeyLengthBytes;
    return this;
  }

   /**
   * Get maxKeyLengthBytes
   * @return maxKeyLengthBytes
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getMaxKeyLengthBytes() {
    return maxKeyLengthBytes;
  }


  public void setMaxKeyLengthBytes(Integer maxKeyLengthBytes) {
    this.maxKeyLengthBytes = maxKeyLengthBytes;
  }


  public StorageStatistics numberOfInputLevelCells(Integer numberOfInputLevelCells) {
    
    this.numberOfInputLevelCells = numberOfInputLevelCells;
    return this;
  }

   /**
   * Get numberOfInputLevelCells
   * @return numberOfInputLevelCells
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfInputLevelCells() {
    return numberOfInputLevelCells;
  }


  public void setNumberOfInputLevelCells(Integer numberOfInputLevelCells) {
    this.numberOfInputLevelCells = numberOfInputLevelCells;
  }


  public StorageStatistics numberOfIncrementalDataSlices(Integer numberOfIncrementalDataSlices) {
    
    this.numberOfIncrementalDataSlices = numberOfIncrementalDataSlices;
    return this;
  }

   /**
   * Get numberOfIncrementalDataSlices
   * @return numberOfIncrementalDataSlices
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfIncrementalDataSlices() {
    return numberOfIncrementalDataSlices;
  }


  public void setNumberOfIncrementalDataSlices(Integer numberOfIncrementalDataSlices) {
    this.numberOfIncrementalDataSlices = numberOfIncrementalDataSlices;
  }


  public StorageStatistics numberOfIncrementalInputCells(Integer numberOfIncrementalInputCells) {
    
    this.numberOfIncrementalInputCells = numberOfIncrementalInputCells;
    return this;
  }

   /**
   * Get numberOfIncrementalInputCells
   * @return numberOfIncrementalInputCells
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfIncrementalInputCells() {
    return numberOfIncrementalInputCells;
  }


  public void setNumberOfIncrementalInputCells(Integer numberOfIncrementalInputCells) {
    this.numberOfIncrementalInputCells = numberOfIncrementalInputCells;
  }


  public StorageStatistics numberOfAggregateViews(Integer numberOfAggregateViews) {
    
    this.numberOfAggregateViews = numberOfAggregateViews;
    return this;
  }

   /**
   * Get numberOfAggregateViews
   * @return numberOfAggregateViews
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfAggregateViews() {
    return numberOfAggregateViews;
  }


  public void setNumberOfAggregateViews(Integer numberOfAggregateViews) {
    this.numberOfAggregateViews = numberOfAggregateViews;
  }


  public StorageStatistics numberOfAggregateCells(Integer numberOfAggregateCells) {
    
    this.numberOfAggregateCells = numberOfAggregateCells;
    return this;
  }

   /**
   * Get numberOfAggregateCells
   * @return numberOfAggregateCells
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfAggregateCells() {
    return numberOfAggregateCells;
  }


  public void setNumberOfAggregateCells(Integer numberOfAggregateCells) {
    this.numberOfAggregateCells = numberOfAggregateCells;
  }


  public StorageStatistics numberOfIncrementalAggregateCells(Integer numberOfIncrementalAggregateCells) {
    
    this.numberOfIncrementalAggregateCells = numberOfIncrementalAggregateCells;
    return this;
  }

   /**
   * Get numberOfIncrementalAggregateCells
   * @return numberOfIncrementalAggregateCells
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getNumberOfIncrementalAggregateCells() {
    return numberOfIncrementalAggregateCells;
  }


  public void setNumberOfIncrementalAggregateCells(Integer numberOfIncrementalAggregateCells) {
    this.numberOfIncrementalAggregateCells = numberOfIncrementalAggregateCells;
  }


  public StorageStatistics costOfQueryingIncrementalData(Double costOfQueryingIncrementalData) {
    
    this.costOfQueryingIncrementalData = costOfQueryingIncrementalData;
    return this;
  }

   /**
   * Get costOfQueryingIncrementalData
   * @return costOfQueryingIncrementalData
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getCostOfQueryingIncrementalData() {
    return costOfQueryingIncrementalData;
  }


  public void setCostOfQueryingIncrementalData(Double costOfQueryingIncrementalData) {
    this.costOfQueryingIncrementalData = costOfQueryingIncrementalData;
  }


  public StorageStatistics inputLevelDataSize(Integer inputLevelDataSize) {
    
    this.inputLevelDataSize = inputLevelDataSize;
    return this;
  }

   /**
   * Get inputLevelDataSize
   * @return inputLevelDataSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getInputLevelDataSize() {
    return inputLevelDataSize;
  }


  public void setInputLevelDataSize(Integer inputLevelDataSize) {
    this.inputLevelDataSize = inputLevelDataSize;
  }


  public StorageStatistics aggregateDataSize(Integer aggregateDataSize) {
    
    this.aggregateDataSize = aggregateDataSize;
    return this;
  }

   /**
   * Get aggregateDataSize
   * @return aggregateDataSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAggregateDataSize() {
    return aggregateDataSize;
  }


  public void setAggregateDataSize(Integer aggregateDataSize) {
    this.aggregateDataSize = aggregateDataSize;
  }


  public StorageStatistics numberOfExistingBlocks(Double numberOfExistingBlocks) {
    
    this.numberOfExistingBlocks = numberOfExistingBlocks;
    return this;
  }

   /**
   * Get numberOfExistingBlocks
   * @return numberOfExistingBlocks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getNumberOfExistingBlocks() {
    return numberOfExistingBlocks;
  }


  public void setNumberOfExistingBlocks(Double numberOfExistingBlocks) {
    this.numberOfExistingBlocks = numberOfExistingBlocks;
  }


  public StorageStatistics blockSize(Integer blockSize) {
    
    this.blockSize = blockSize;
    return this;
  }

   /**
   * Get blockSize
   * @return blockSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getBlockSize() {
    return blockSize;
  }


  public void setBlockSize(Integer blockSize) {
    this.blockSize = blockSize;
  }


  public StorageStatistics potentialNumberOfBlocks(Double potentialNumberOfBlocks) {
    
    this.potentialNumberOfBlocks = potentialNumberOfBlocks;
    return this;
  }

   /**
   * Get potentialNumberOfBlocks
   * @return potentialNumberOfBlocks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getPotentialNumberOfBlocks() {
    return potentialNumberOfBlocks;
  }


  public void setPotentialNumberOfBlocks(Double potentialNumberOfBlocks) {
    this.potentialNumberOfBlocks = potentialNumberOfBlocks;
  }


  public StorageStatistics existingLevelZeroBlocks(Double existingLevelZeroBlocks) {
    
    this.existingLevelZeroBlocks = existingLevelZeroBlocks;
    return this;
  }

   /**
   * Get existingLevelZeroBlocks
   * @return existingLevelZeroBlocks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getExistingLevelZeroBlocks() {
    return existingLevelZeroBlocks;
  }


  public void setExistingLevelZeroBlocks(Double existingLevelZeroBlocks) {
    this.existingLevelZeroBlocks = existingLevelZeroBlocks;
  }


  public StorageStatistics existingUpperLevelBlocks(Double existingUpperLevelBlocks) {
    
    this.existingUpperLevelBlocks = existingUpperLevelBlocks;
    return this;
  }

   /**
   * Get existingUpperLevelBlocks
   * @return existingUpperLevelBlocks
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getExistingUpperLevelBlocks() {
    return existingUpperLevelBlocks;
  }


  public void setExistingUpperLevelBlocks(Double existingUpperLevelBlocks) {
    this.existingUpperLevelBlocks = existingUpperLevelBlocks;
  }


  public StorageStatistics blockDensity(Double blockDensity) {
    
    this.blockDensity = blockDensity;
    return this;
  }

   /**
   * Get blockDensity
   * @return blockDensity
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getBlockDensity() {
    return blockDensity;
  }


  public void setBlockDensity(Double blockDensity) {
    this.blockDensity = blockDensity;
  }


  public StorageStatistics percentageOfMaximumBlocksExisting(Double percentageOfMaximumBlocksExisting) {
    
    this.percentageOfMaximumBlocksExisting = percentageOfMaximumBlocksExisting;
    return this;
  }

   /**
   * Get percentageOfMaximumBlocksExisting
   * @return percentageOfMaximumBlocksExisting
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getPercentageOfMaximumBlocksExisting() {
    return percentageOfMaximumBlocksExisting;
  }


  public void setPercentageOfMaximumBlocksExisting(Double percentageOfMaximumBlocksExisting) {
    this.percentageOfMaximumBlocksExisting = percentageOfMaximumBlocksExisting;
  }


  public StorageStatistics compressionRatio(Double compressionRatio) {
    
    this.compressionRatio = compressionRatio;
    return this;
  }

   /**
   * Get compressionRatio
   * @return compressionRatio
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getCompressionRatio() {
    return compressionRatio;
  }


  public void setCompressionRatio(Double compressionRatio) {
    this.compressionRatio = compressionRatio;
  }


  public StorageStatistics averageClusteringRatio(Double averageClusteringRatio) {
    
    this.averageClusteringRatio = averageClusteringRatio;
    return this;
  }

   /**
   * Get averageClusteringRatio
   * @return averageClusteringRatio
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getAverageClusteringRatio() {
    return averageClusteringRatio;
  }


  public void setAverageClusteringRatio(Double averageClusteringRatio) {
    this.averageClusteringRatio = averageClusteringRatio;
  }


  public StorageStatistics pageFileSize(Long pageFileSize) {
    
    this.pageFileSize = pageFileSize;
    return this;
  }

   /**
   * Get pageFileSize
   * @return pageFileSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getPageFileSize() {
    return pageFileSize;
  }


  public void setPageFileSize(Long pageFileSize) {
    this.pageFileSize = pageFileSize;
  }


  public StorageStatistics indexFileSize(Long indexFileSize) {
    
    this.indexFileSize = indexFileSize;
    return this;
  }

   /**
   * Get indexFileSize
   * @return indexFileSize
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getIndexFileSize() {
    return indexFileSize;
  }


  public void setIndexFileSize(Long indexFileSize) {
    this.indexFileSize = indexFileSize;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StorageStatistics storageStatistics = (StorageStatistics) o;
    return Objects.equals(this.dimensions, storageStatistics.dimensions) &&
        Objects.equals(this.maxKeyLengthBits, storageStatistics.maxKeyLengthBits) &&
        Objects.equals(this.maxKeyLengthBytes, storageStatistics.maxKeyLengthBytes) &&
        Objects.equals(this.numberOfInputLevelCells, storageStatistics.numberOfInputLevelCells) &&
        Objects.equals(this.numberOfIncrementalDataSlices, storageStatistics.numberOfIncrementalDataSlices) &&
        Objects.equals(this.numberOfIncrementalInputCells, storageStatistics.numberOfIncrementalInputCells) &&
        Objects.equals(this.numberOfAggregateViews, storageStatistics.numberOfAggregateViews) &&
        Objects.equals(this.numberOfAggregateCells, storageStatistics.numberOfAggregateCells) &&
        Objects.equals(this.numberOfIncrementalAggregateCells, storageStatistics.numberOfIncrementalAggregateCells) &&
        Objects.equals(this.costOfQueryingIncrementalData, storageStatistics.costOfQueryingIncrementalData) &&
        Objects.equals(this.inputLevelDataSize, storageStatistics.inputLevelDataSize) &&
        Objects.equals(this.aggregateDataSize, storageStatistics.aggregateDataSize) &&
        Objects.equals(this.numberOfExistingBlocks, storageStatistics.numberOfExistingBlocks) &&
        Objects.equals(this.blockSize, storageStatistics.blockSize) &&
        Objects.equals(this.potentialNumberOfBlocks, storageStatistics.potentialNumberOfBlocks) &&
        Objects.equals(this.existingLevelZeroBlocks, storageStatistics.existingLevelZeroBlocks) &&
        Objects.equals(this.existingUpperLevelBlocks, storageStatistics.existingUpperLevelBlocks) &&
        Objects.equals(this.blockDensity, storageStatistics.blockDensity) &&
        Objects.equals(this.percentageOfMaximumBlocksExisting, storageStatistics.percentageOfMaximumBlocksExisting) &&
        Objects.equals(this.compressionRatio, storageStatistics.compressionRatio) &&
        Objects.equals(this.averageClusteringRatio, storageStatistics.averageClusteringRatio) &&
        Objects.equals(this.pageFileSize, storageStatistics.pageFileSize) &&
        Objects.equals(this.indexFileSize, storageStatistics.indexFileSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimensions, maxKeyLengthBits, maxKeyLengthBytes, numberOfInputLevelCells, numberOfIncrementalDataSlices, numberOfIncrementalInputCells, numberOfAggregateViews, numberOfAggregateCells, numberOfIncrementalAggregateCells, costOfQueryingIncrementalData, inputLevelDataSize, aggregateDataSize, numberOfExistingBlocks, blockSize, potentialNumberOfBlocks, existingLevelZeroBlocks, existingUpperLevelBlocks, blockDensity, percentageOfMaximumBlocksExisting, compressionRatio, averageClusteringRatio, pageFileSize, indexFileSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StorageStatistics {\n");
    sb.append("    dimensions: ").append(toIndentedString(dimensions)).append("\n");
    sb.append("    maxKeyLengthBits: ").append(toIndentedString(maxKeyLengthBits)).append("\n");
    sb.append("    maxKeyLengthBytes: ").append(toIndentedString(maxKeyLengthBytes)).append("\n");
    sb.append("    numberOfInputLevelCells: ").append(toIndentedString(numberOfInputLevelCells)).append("\n");
    sb.append("    numberOfIncrementalDataSlices: ").append(toIndentedString(numberOfIncrementalDataSlices)).append("\n");
    sb.append("    numberOfIncrementalInputCells: ").append(toIndentedString(numberOfIncrementalInputCells)).append("\n");
    sb.append("    numberOfAggregateViews: ").append(toIndentedString(numberOfAggregateViews)).append("\n");
    sb.append("    numberOfAggregateCells: ").append(toIndentedString(numberOfAggregateCells)).append("\n");
    sb.append("    numberOfIncrementalAggregateCells: ").append(toIndentedString(numberOfIncrementalAggregateCells)).append("\n");
    sb.append("    costOfQueryingIncrementalData: ").append(toIndentedString(costOfQueryingIncrementalData)).append("\n");
    sb.append("    inputLevelDataSize: ").append(toIndentedString(inputLevelDataSize)).append("\n");
    sb.append("    aggregateDataSize: ").append(toIndentedString(aggregateDataSize)).append("\n");
    sb.append("    numberOfExistingBlocks: ").append(toIndentedString(numberOfExistingBlocks)).append("\n");
    sb.append("    blockSize: ").append(toIndentedString(blockSize)).append("\n");
    sb.append("    potentialNumberOfBlocks: ").append(toIndentedString(potentialNumberOfBlocks)).append("\n");
    sb.append("    existingLevelZeroBlocks: ").append(toIndentedString(existingLevelZeroBlocks)).append("\n");
    sb.append("    existingUpperLevelBlocks: ").append(toIndentedString(existingUpperLevelBlocks)).append("\n");
    sb.append("    blockDensity: ").append(toIndentedString(blockDensity)).append("\n");
    sb.append("    percentageOfMaximumBlocksExisting: ").append(toIndentedString(percentageOfMaximumBlocksExisting)).append("\n");
    sb.append("    compressionRatio: ").append(toIndentedString(compressionRatio)).append("\n");
    sb.append("    averageClusteringRatio: ").append(toIndentedString(averageClusteringRatio)).append("\n");
    sb.append("    pageFileSize: ").append(toIndentedString(pageFileSize)).append("\n");
    sb.append("    indexFileSize: ").append(toIndentedString(indexFileSize)).append("\n");
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

