#!/bin/bash

rm temp.json >/dev/null 2>&1 || echo "No temp file to delete, skipping"

cp formatted.json temp.json

# Add securityDefinitions and security for basic auth by default.
cat temp.json | jq '. += ({securityDefinitions: {basicAuth: {type: "basic"}}, security: [{"basicAuth": []}]})' > json.tmp && mv json.tmp temp.json

# Essbase REST API seems to be case-sensitive. An example error message when using the default upper-case enums is:
#
#   Cannot deserialize value of type `oracle.essbase.restws.services.main.grid.Action` from String \"ZOOMIN\":
#   value not one of declared Enum instance names: [removeonly, keeponly, pivot, submit, pivotToPOV, refresh, zoomin, zoomout]
cat temp.json | jq '.definitions.GridOperation.properties.action.enum = ["zoomin", "zoomout", "keeponly", "removeonly", "refresh", "pivot", "pivotToPOV", "submit"]' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions.ZoomIn.properties.ancestor.enum = ["top", "bottom"]' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions.ZoomIn.properties.mode.enum = ["children", "descendents", "base"]' > json.tmp && mv json.tmp temp.json

# The Drillthrough Reports get endpoint nominally models a response with the following:
#  "responses": {
#      "200": {
#          "description": "<p><strong>OK</strong></p><p>The drill through reports were retrieved successfully. Returns the links to get, edit, or delete the reports.</p>",
#          "schema": {
#              "type": "array",
#              "items": {
#                  "$ref": "#/definitions/ReportList"
#              }
#          }
#      },
#
# The response should actually be like this though:
#  "schema": {
#    "$ref": "#/definitions/ReportList"
#   }
#
# This fixes the response model so that it is a ReportList instead of a List<ReportList>, such as in DrillThroughReportsApi's
#   public ReportList drillThroughReportsGetReports(String applicationName, String databaseName) throws ApiException {

cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/reports".get.responses."200".schema = {"$ref": "#/definitions/ReportList"}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/applications/{applicationName}/configurations".get.responses."200".schema = {"$ref": "#/definitions/ApplicationConfigList"}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/dimensions".get.responses."200".schema = {"$ref": "#/definitions/DimensionList"}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/scripts".get.responses."200".schema = {"$ref": "#/definitions/ScriptList"}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/scripts".get.responses."200".schema = {"$ref": "#/definitions/ScriptList"}' > json.tmp && mv json.tmp temp.json

# Fix the consumes for the application datasource stream endpoints
cat temp.json | jq '.paths."/applications/{applicationName}/datasources/query/stream".post.consumes = ["application/json"]' > json.tmp && mv json.tmp temp.json

# Fix the consumes for the datasource stream endpoints
cat temp.json | jq '.paths."/datasources/query/stream".post.consumes = ["application/json"]' > json.tmp && mv json.tmp temp.json

# Return types for variables are not a List<VariableList>, they are a VariableList
cat temp.json | jq '.paths."/variables".get.responses."200".schema = {"$ref": "#/definitions/VariableList"}' > json.tmp && mv json.tmp temp.json
cat temp.json | jq '.paths."/applications/{applicationName}/variables".get.responses."200".schema = {"$ref": "#/definitions/VariableList"}' > json.tmp && mv json.tmp temp.json
cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/variables".get.responses."200".schema = {"$ref": "#/definitions/VariableList"}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/sessions".get.responses."200".schema = {"type": "array","items": {"$ref": "#/definitions/SessionAttributes"}}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/about/instance".get.responses = {"200": {"description": "successful operation", "schema": { "$ref": "#/definitions/AboutInstance" }}}' > json.tmp && mv json.tmp temp.json

# The about instance default definition doesn't model a response at all, so we create and stick one in here
cat temp.json | jq '.definitions."AboutInstance" = {
  "type": "object",
  "properties": {
    "provisioningSupported": {
      "type": "boolean"
    },
    "resetPasswordSupported": {
      "type": "boolean"
    },
    "easInstalled": {
      "type": "boolean"
    }
  },
  "xml": {
    "name": "aboutInstance"
  }
}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.paths."/urls".get.responses."200".schema = { "$ref": "#/definitions/EssbaseURLList" }' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions."EssbaseURL" = {
  "type": "object",
  "properties": {
    "application": {
      "type": "string"
    },
    "url": {
      "type": "string"
    }
  }
}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions."EssbaseURLList" = {
  "type": "object",
  "properties": {
    "items": {
      "type": "array",
      "items": {
        "$ref": "#/definitions/EssbaseURL"
      }
    }
  }
}' > json.tmp && mv json.tmp temp.json

# The get files definition doesn't model a strongly typed response, so create new object/collection types and add them to the definitions list.
cat temp.json | jq '.paths."/files/{path}".get.responses."200".schema = { "$ref": "#/definitions/FileCollectionResponse" }' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions."FileCollectionResponse" = {
  "type": "object",
  "properties": {
    "count": {
      "type": "integer",
      "format": "int64"
    },
    "items": {
      "type": "array",
      "items": {
        "$ref": "#/definitions/FileBean"
      }
    },
    "totalResults": {
      "type": "integer",
      "format": "int64"
    },
    "hasMore": {
      "type": "boolean"
    },
    "limit": {
      "type": "integer",
      "format": "int64"
    },
    "properties": {
      "type": "object",
      "additionalProperties": {
        "type": "string"
      }
    },
    "offset": {
      "type": "integer",
      "format": "int64"
    }
  }
}' > json.tmp && mv json.tmp temp.json

cat temp.json | jq '.definitions."FileBean" = {
  "type": "object",
  "properties": {
    "name": {
      "type": "string"
    },
    "fullPath": {
      "type": "string"
    },
    "type": {
      "type": "string"
    },
    "permissions": {
      "type": "object",
      "properties": {
          "addFolder": {
            "type": "boolean"
          },
          "addFile": {
            "type": "boolean"
          }
      }
    },
    "links": {
      "type": "array",
      "items": {
        "$ref": "#/definitions/Link"
      }
    }
  },
  "xml": {
    "name": "File"
  }
}' > json.tmp && mv json.tmp temp.json


# Return types for generations are not a List<GenerationLevelList>, they are a GenerationLevelList
cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/dimensions/{dimensionName}/generations".get.responses."200".schema = {"$ref": "#/definitions/GenerationLevelList"}' > json.tmp && mv json.tmp temp.json
cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/dimensions/{dimensionName}/levels".get.responses."200".schema = {"$ref": "#/definitions/GenerationLevelList"}' > json.tmp && mv json.tmp temp.json

# Return types for locked objects are not a List<LockObjectList>, they are a LockObjectList
cat temp.json | jq '.paths."/applications/{applicationName}/databases/{databaseName}/locks/objects".get.responses."200".schema = {"$ref": "#/definitions/LockObjectList"}' > json.tmp && mv json.tmp temp.json

cp temp.json src/main/resources/processed.json

#mvn clean install -DskipTests