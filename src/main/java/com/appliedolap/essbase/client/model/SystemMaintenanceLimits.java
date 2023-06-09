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
import com.appliedolap.essbase.client.model.SystemMaintainableResource;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * SystemMaintenanceLimits
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:22:09.429372-05:00[America/Indiana/Indianapolis]")
public class SystemMaintenanceLimits {
  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private SystemMaintainableResource disk;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private SystemMaintainableResource ram;


  public SystemMaintenanceLimits disk(SystemMaintainableResource disk) {
    
    this.disk = disk;
    return this;
  }

   /**
   * Get disk
   * @return disk
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SystemMaintainableResource getDisk() {
    return disk;
  }


  public void setDisk(SystemMaintainableResource disk) {
    this.disk = disk;
  }


  public SystemMaintenanceLimits ram(SystemMaintainableResource ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * Get ram
   * @return ram
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public SystemMaintainableResource getRam() {
    return ram;
  }


  public void setRam(SystemMaintainableResource ram) {
    this.ram = ram;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemMaintenanceLimits systemMaintenanceLimits = (SystemMaintenanceLimits) o;
    return Objects.equals(this.disk, systemMaintenanceLimits.disk) &&
        Objects.equals(this.ram, systemMaintenanceLimits.ram);
  }

  @Override
  public int hashCode() {
    return Objects.hash(disk, ram);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemMaintenanceLimits {\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
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

