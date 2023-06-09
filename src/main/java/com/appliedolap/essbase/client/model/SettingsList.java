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
import com.appliedolap.essbase.client.model.BufferSettings;
import com.appliedolap.essbase.client.model.CacheSettings;
import com.appliedolap.essbase.client.model.CalculationSettings;
import com.appliedolap.essbase.client.model.CompressionSettings;
import com.appliedolap.essbase.client.model.GeneralSettings;
import com.appliedolap.essbase.client.model.Link;
import com.appliedolap.essbase.client.model.StartupSettings;
import com.appliedolap.essbase.client.model.TransactionSettings;
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
 * SettingsList
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class SettingsList {
  public static final String SERIALIZED_NAME_GENERAL = "general";
  @SerializedName(SERIALIZED_NAME_GENERAL)
  private GeneralSettings general;

  public static final String SERIALIZED_NAME_STARTUP = "startup";
  @SerializedName(SERIALIZED_NAME_STARTUP)
  private StartupSettings startup;

  public static final String SERIALIZED_NAME_CALCULATION = "calculation";
  @SerializedName(SERIALIZED_NAME_CALCULATION)
  private CalculationSettings calculation;

  public static final String SERIALIZED_NAME_BUFFERS = "buffers";
  @SerializedName(SERIALIZED_NAME_BUFFERS)
  private BufferSettings buffers;

  public static final String SERIALIZED_NAME_COMPRESSION = "compression";
  @SerializedName(SERIALIZED_NAME_COMPRESSION)
  private List<CompressionSettings> compression = null;

  public static final String SERIALIZED_NAME_CACHES = "caches";
  @SerializedName(SERIALIZED_NAME_CACHES)
  private CacheSettings caches;

  public static final String SERIALIZED_NAME_TRANSACTIONS = "transactions";
  @SerializedName(SERIALIZED_NAME_TRANSACTIONS)
  private TransactionSettings transactions;

  public static final String SERIALIZED_NAME_LINKS = "links";
  @SerializedName(SERIALIZED_NAME_LINKS)
  private List<Link> links = null;


  public SettingsList general(GeneralSettings general) {
    
    this.general = general;
    return this;
  }

   /**
   * Get general
   * @return general
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public GeneralSettings getGeneral() {
    return general;
  }


  public void setGeneral(GeneralSettings general) {
    this.general = general;
  }


  public SettingsList startup(StartupSettings startup) {
    
    this.startup = startup;
    return this;
  }

   /**
   * Get startup
   * @return startup
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public StartupSettings getStartup() {
    return startup;
  }


  public void setStartup(StartupSettings startup) {
    this.startup = startup;
  }


  public SettingsList calculation(CalculationSettings calculation) {
    
    this.calculation = calculation;
    return this;
  }

   /**
   * Get calculation
   * @return calculation
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CalculationSettings getCalculation() {
    return calculation;
  }


  public void setCalculation(CalculationSettings calculation) {
    this.calculation = calculation;
  }


  public SettingsList buffers(BufferSettings buffers) {
    
    this.buffers = buffers;
    return this;
  }

   /**
   * Get buffers
   * @return buffers
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BufferSettings getBuffers() {
    return buffers;
  }


  public void setBuffers(BufferSettings buffers) {
    this.buffers = buffers;
  }


  public SettingsList compression(List<CompressionSettings> compression) {
    
    this.compression = compression;
    return this;
  }

  public SettingsList addCompressionItem(CompressionSettings compressionItem) {
    if (this.compression == null) {
      this.compression = new ArrayList<CompressionSettings>();
    }
    this.compression.add(compressionItem);
    return this;
  }

   /**
   * Get compression
   * @return compression
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<CompressionSettings> getCompression() {
    return compression;
  }


  public void setCompression(List<CompressionSettings> compression) {
    this.compression = compression;
  }


  public SettingsList caches(CacheSettings caches) {
    
    this.caches = caches;
    return this;
  }

   /**
   * Get caches
   * @return caches
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public CacheSettings getCaches() {
    return caches;
  }


  public void setCaches(CacheSettings caches) {
    this.caches = caches;
  }


  public SettingsList transactions(TransactionSettings transactions) {
    
    this.transactions = transactions;
    return this;
  }

   /**
   * Get transactions
   * @return transactions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public TransactionSettings getTransactions() {
    return transactions;
  }


  public void setTransactions(TransactionSettings transactions) {
    this.transactions = transactions;
  }


  public SettingsList links(List<Link> links) {
    
    this.links = links;
    return this;
  }

  public SettingsList addLinksItem(Link linksItem) {
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
    SettingsList settingsList = (SettingsList) o;
    return Objects.equals(this.general, settingsList.general) &&
        Objects.equals(this.startup, settingsList.startup) &&
        Objects.equals(this.calculation, settingsList.calculation) &&
        Objects.equals(this.buffers, settingsList.buffers) &&
        Objects.equals(this.compression, settingsList.compression) &&
        Objects.equals(this.caches, settingsList.caches) &&
        Objects.equals(this.transactions, settingsList.transactions) &&
        Objects.equals(this.links, settingsList.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(general, startup, calculation, buffers, compression, caches, transactions, links);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SettingsList {\n");
    sb.append("    general: ").append(toIndentedString(general)).append("\n");
    sb.append("    startup: ").append(toIndentedString(startup)).append("\n");
    sb.append("    calculation: ").append(toIndentedString(calculation)).append("\n");
    sb.append("    buffers: ").append(toIndentedString(buffers)).append("\n");
    sb.append("    compression: ").append(toIndentedString(compression)).append("\n");
    sb.append("    caches: ").append(toIndentedString(caches)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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

