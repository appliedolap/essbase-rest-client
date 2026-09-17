#!/usr/bin/env bash
#
# Patches Oracle's published specification into one the generator can actually use, and writes the
# result to src/main/resources/processed.json.
#
# Everything here is a defect in the specification, not a shape preference: an operation the spec
# types as returning a list of lists when it returns one object, a request body the spec says is
# */* when the server only accepts JSON, a model missing properties the server sends. Each patch
# says what is wrong and what it should be.
#
# Targets OpenAPI 3.0 (Essbase 26.1 and later). The 21.x specifications are Swagger 2.0, where the
# same corrections live at .definitions and .responses."200".schema rather than
# .components.schemas and .responses."200".content."application/json".schema; git history has that
# version if one is ever needed again.
#
#   ./process.sh [spec.json]

set -uo pipefail

SOURCE="${1:-spec/versions/essbase-26.1-openapi.json}"
TARGET="src/main/resources/processed.json"
WORK="$(mktemp -t essbase-spec)"
trap 'rm -f "$WORK" "$WORK.next"' EXIT

applied=0
skipped=0
failed=0

# patch <description> <precondition> <program>
#
# The precondition is why this exists. jq assignment *creates* whatever path is missing, so a patch
# written against the wrong shape does not fail - it silently grows a parallel structure the
# generator then ignores. That is exactly how running the Swagger 2.0 version of this script against
# an OpenAPI 3 spec "succeeded" while producing a .definitions block of ten entries that nothing
# referenced. Every patch here has to state what it expects to find, and a premise that no longer
# holds is reported rather than papered over: Oracle does fix things, and a correction applied on
# top of a fix is how you reintroduce a bug.
patch() {
    local description="$1" precondition="$2" program="$3"

    local holds
    holds="$(jq -r "$precondition" "$WORK" 2>&1)"
    if [[ "$holds" != "true" ]]; then
        if [[ "$holds" == "false" ]]; then
            printf '  - no longer needed: %s\n' "$description"
            skipped=$((skipped + 1))
        else
            printf '  ! precondition could not be evaluated: %s\n      %s\n' "$description" "$holds"
            failed=$((failed + 1))
        fi
        return
    fi

    if ! jq "$program" "$WORK" > "$WORK.next" 2>"$WORK.err"; then
        printf '  ! failed: %s\n      %s\n' "$description" "$(cat "$WORK.err")"
        failed=$((failed + 1))
        return
    fi
    mv "$WORK.next" "$WORK"
    printf '  applied: %s\n' "$description"
    applied=$((applied + 1))
}

# Shorthands. A response in OpenAPI 3 carries one schema per media type, and Essbase declares both
# application/json and application/xml on nearly everything - so a correction that touched only the
# JSON one would leave the XML one contradicting it.
retype_response() {  # retype_response <description> <path> <verb> <schema>
    patch "$1" \
        "(.paths.\"$2\".$3.responses.\"200\".content // {}) as \$c
         | (\$c | length > 0) and (\$c | map_values(.schema) | any(. != $4))" \
        ".paths.\"$2\".$3.responses.\"200\".content |= map_values(.schema = $4)"
}

json_request() {  # json_request <path> <verb>
    patch "$2 $1 consumes JSON, not */*" \
        ".paths.\"$1\".$2.requestBody.content | has(\"*/*\")" \
        ".paths.\"$1\".$2.requestBody.content |= {\"application/json\": .[\"*/*\"]}"
}

echo "Processing $SOURCE"
cp "$SOURCE" "$WORK"

jq -e '(.openapi // "") | type == "string" and startswith("3.")' "$WORK" >/dev/null 2>&1 || {
    echo "$SOURCE is not an OpenAPI 3 document; this script would silently corrupt it." >&2
    exit 1
}

echo
echo "── Names the generator has to live with ─────────────────────────────────"

# Four operationIds were used twice, which the generator rejects outright. Oracle has since
# disambiguated them - by appending _1 to one of each pair, which generates methods called
# getAliases_1. Restore the names these had when this library was first built, so the same endpoint
# is reached by the same method name across both generations.
patch "setActiveAlias is not another getAliases" \
    '.paths."/applications/{applicationName}/databases/{databaseName}/aliases/setActiveAlias".put.operationId | endswith("_1")' \
    '.paths."/applications/{applicationName}/databases/{databaseName}/aliases/setActiveAlias".put.operationId = "Applications.setActiveAlias"'

patch "compressioninfo is not the compression settings" \
    '.paths."/applications/{applicationName}/databases/{databaseName}/settings/compressioninfo".get.operationId == "DatabaseSettingsStatistics.getCompressSettings"' \
    '.paths."/applications/{applicationName}/databases/{databaseName}/settings/compressioninfo".get.operationId = "DatabaseSettingsStatistics.getCompressionInfo"'

patch "the drill-through list endpoint is not getReports" \
    '.paths."/applications/{applicationName}/databases/{databaseName}/dtreports/list".post.operationId == "DrillThroughReports.getReports"' \
    '.paths."/applications/{applicationName}/databases/{databaseName}/dtreports/list".post.operationId = "DrillThroughReports.listReports"'

patch "the custom-delimited stream is its own operation" \
    '.paths."/datasources/customdelimited/query/stream".post.operationId == "GlobalDatasources.getDataStream"' \
    '.paths."/datasources/customdelimited/query/stream".post.operationId = "GlobalDatasources.getCustomDelimitedDataStream"'

patch "and the plain stream keeps the plain name" \
    '.paths."/datasources/query/stream".post.operationId | endswith("_1")' \
    '.paths."/datasources/query/stream".post.operationId = "GlobalDatasources.getDataStream"'

patch "the drill-through report list keeps the plain name" \
    '.paths."/applications/{applicationName}/databases/{databaseName}/reports".get.operationId | endswith("_1")' \
    '.paths."/applications/{applicationName}/databases/{databaseName}/reports".get.operationId = "DrillThroughReports.getReports"'

patch "and so does the compression settings endpoint" \
    '.paths."/applications/{applicationName}/databases/{databaseName}/settings/compression".get.operationId | endswith("_1")' \
    '.paths."/applications/{applicationName}/databases/{databaseName}/settings/compression".get.operationId = "DatabaseSettingsStatistics.getCompressSettings"'

# Two query parameters on this operation carry their *description* in the name field -
# "<p>Connection name.</p>" where "connectionName" belongs. Oracle has fixed the two path
# parameters that made the spec invalid; these two remain, and generate method arguments named
# after a paragraph tag.
patch "/outline/{app}/{cube}/xml query parameters are named after their descriptions" \
    '.paths."/outline/{app}/{cube}/xml".post.parameters[2].name | test("<")' \
    '.paths."/outline/{app}/{cube}/xml".post.parameters[2].name = "connectionName"
     | .paths."/outline/{app}/{cube}/xml".post.parameters[3].name = "connectionApplicationName"'

# The spec defines both "Datasource" and "DataSource" - different models differing in one letter's
# case. Java tolerates that; a case-insensitive filesystem does not, so on macOS or Windows the two
# collapse into one file and the generator emits a class whose name and filename disagree.
# Datasource is what nine references point at; DataSource has one, and a completely different
# shape, so DataSource is the one renamed. Here rather than with --model-name-mappings, which the
# generator applies only after it has decided filenames.
patch "DataSource and Datasource cannot share a filename" \
    '.components.schemas | has("DataSource") and has("Datasource")' \
    '.components.schemas.DataSourceDefinition = .components.schemas.DataSource
     | del(.components.schemas.DataSource)
     | walk(if type == "string" and . == "#/components/schemas/DataSource"
            then "#/components/schemas/DataSourceDefinition" else . end)'

echo
echo "── Collections typed as a list of the collection ────────────────────────"

# Each of these returns one XList object holding the items. The spec types them as an array *of*
# XList, so the generated method answers List<VariableList> where one VariableList was sent.
for entry in \
    "/applications/{applicationName}/configurations|get|ApplicationConfigList" \
    "/applications/{applicationName}/databases/{databaseName}/reports|get|ReportList" \
    "/applications/{applicationName}/databases/{databaseName}/dimensions|get|DimensionList" \
    "/applications/{applicationName}/databases/{databaseName}/dimensions/{dimensionName}/generations|get|GenerationLevelList" \
    "/applications/{applicationName}/databases/{databaseName}/dimensions/{dimensionName}/levels|get|GenerationLevelList" \
    "/applications/{applicationName}/databases/{databaseName}/locks/objects|get|LockObjectList" \
    "/applications/{applicationName}/databases/{databaseName}/scripts|get|ScriptList" \
    "/applications/{applicationName}/databases/{databaseName}/variables|get|VariableList" \
    "/applications/{applicationName}/variables|get|VariableList" \
    "/variables|get|VariableList" \
    "/applications/{applicationName}/configurationkeys|get|ApplicationConfigList" \
    "/applications/{applicationName}/databases/{databaseName}/filters|get|FilterList" \
    "/applications/{applicationName}/databases/{databaseName}/filters/{filterName}/permissions|get|UserGroupProvisionInfoList" \
    "/applications/{applicationName}/databases/{databaseName}/locks/blocks|get|LockBlockList" \
    "/applications/{applicationName}/databases/{databaseName}/scripts/{scriptName}/permissions|get|UserGroupProvisionInfoList" \
    "/applications/{applicationName}/databases/{databaseName}/scripts/{scriptName}/rtsv|get|RTSVList" \
    "/properties|get|PropertyList"
do
    IFS='|' read -r p v model <<< "$entry"
    patch "$p answers one $model, not a list of them" \
        "[.paths.\"$p\".$v.responses.\"200\".content[].schema.type] | all(. == \"array\")" \
        ".paths.\"$p\".$v.responses.\"200\".content |= map_values(.schema = {\"\$ref\": \"#/components/schemas/$model\"})"
done

echo
echo "── Responses typed as something they are not ────────────────────────────"

# /sessions has the opposite problem: it answers an array of SessionAttributes, and the spec says
# one of them.
retype_response "/sessions answers many sessions, not one" \
    "/sessions" get '{"type": "array", "items": {"$ref": "#/components/schemas/SessionAttributes"}}'

# /about/instance is typed as About - name, version, build - which is what /about answers. The
# instance endpoint reports what the deployment supports, and nothing in the spec describes it.
patch "define AboutInstance, which the spec has no model for" \
    '.components.schemas | has("AboutInstance") | not' \
    '.components.schemas.AboutInstance = {
        "type": "object",
        "properties": {
            "provisioningSupported": { "type": "boolean" },
            "resetPasswordSupported": { "type": "boolean" },
            "easInstalled": { "type": "boolean" }
        },
        "xml": { "name": "aboutInstance" }
     }'
retype_response "/about/instance is not the /about payload" \
    "/about/instance" get '{"$ref": "#/components/schemas/AboutInstance"}'

# /urls is typed as Preference, whose only property is links.
patch "define the URL models /urls answers with" \
    '.components.schemas | has("EssbaseURLList") | not' \
    '.components.schemas.EssbaseURL = {
        "type": "object",
        "properties": { "application": { "type": "string" }, "url": { "type": "string" } }
     }
     | .components.schemas.EssbaseURLList = {
        "type": "object",
        "properties": {
            "items": { "type": "array", "items": { "$ref": "#/components/schemas/EssbaseURL" } }
        }
     }'
retype_response "/urls answers URLs, not a Preference" \
    "/urls" get '{"$ref": "#/components/schemas/EssbaseURLList"}'

# The catalogue listing is typed CollectionResponse, whose items are an untyped object - so the
# files come back as List<Object> with every field lost. CollectionResponse is used by /files and
# /files/{path} and nothing else, so it can simply be told what it holds.
patch "define the file model the catalogue listing holds" \
    '.components.schemas | has("FileBean") | not' \
    '.components.schemas.FileBean = {
        "type": "object",
        "properties": {
            "name": { "type": "string" },
            "fullPath": { "type": "string" },
            "type": { "type": "string" },
            "permissions": {
                "type": "object",
                "properties": {
                    "addFolder": { "type": "boolean" },
                    "addFile": { "type": "boolean" }
                }
            },
            "links": { "type": "array", "items": { "$ref": "#/components/schemas/Link" } }
        },
        "xml": { "name": "File" }
     }'
patch "a catalogue listing holds files, not untyped objects" \
    '.components.schemas.CollectionResponse.properties.items.items | has("$ref") | not' \
    '.components.schemas.CollectionResponse.properties.items.items = {"$ref": "#/components/schemas/FileBean"}'

echo
echo "── Operations whose only answer is a links envelope ─────────────────────"

# Adding or removing a group member is typed as returning a UserBean and returns no such thing: the
# body is a links envelope and nothing else. That would merely be untidy, except the client appends
# links=none to every request, which empties the envelope - so the generated method deserializes an
# empty body into UserBean and throws "No content to map due to end-of-input" on a call the server
# answered 200. Dropping the response makes them void, which is what they are.
for entry in \
    "/groups/{id}/members/users|post" \
    "/groups/{id}/members/users|delete" \
    "/groups/{id}/members/groups|post" \
    "/groups/{id}/members/groups|delete"
do
    IFS='|' read -r p v <<< "$entry"
    patch "$v $p answers nothing once links are suppressed" \
        "[.paths.\"$p\".$v.responses.\"200\".content[].schema.\"\$ref\"] | any(. != null)" \
        "del(.paths.\"$p\".$v.responses.\"200\".content)"
done

echo
echo "── Fields the server names differently from the specification ───────────"

# A datasource's columns arrive and depart as "Column", capitalised - the specification calls the
# property "column" and puts the capitalised spelling in its xml.name, which is only half the story
# for a JSON API. Sending the lowercase one is not rejected as unknown; the server accepts the
# request and then fails it with "No column information found for datasource", which points at the
# columns being absent rather than at their being spelled wrong.
#
# Not corrected here: this same model's "delimeter". It is misspelled, and the server does return the
# correct "delimiter" when read - but the two are not one field. Writing "delimeter" normalises the
# value ("," becomes "Comma") and produces a datasource that queries; writing "delimiter" stores the
# character as given and produces one that answers NullPointerException at query time. Fixing the
# spelling would turn working code into broken code.
patch "a datasource's columns are Column, capitalised" \
    '.components.schemas.ColumnsType.properties | has("column")' \
    '.components.schemas.ColumnsType.properties.Column = .components.schemas.ColumnsType.properties.column
     | del(.components.schemas.ColumnsType.properties.column)
     | .components.schemas.ColumnsType.required = ["Column"]'

echo
echo "── Request bodies ──────────────────────────────────────────────────────"

# Declared */*, which makes the generator send Content-Type: */*, which Essbase rejects.
json_request "/applications/{application}/databases/{database}/mdx" post
json_request "/applications/{applicationName}/databases/{databaseName}/grid" post
json_request "/applications/{applicationName}/datasources/query/stream" post
json_request "/datasources/query" post
json_request "/datasources/query/stream" post
json_request "/preferences/grid" put

echo
echo "── Models that do not match what the server sends ───────────────────────"

# MemberBean arrives with these three and the spec does not mention them, so they are dropped on
# deserialization.
patch "MemberBean is missing three properties the server sends" \
    '.components.schemas.MemberBean.properties | has("uda") | not' \
    '.components.schemas.MemberBean.properties += {
        "uda": { "type": "array", "items": { "type": "string" } },
        "dataStorageType": { "type": "string" },
        "parentName": { "type": "string" }
     }'

# Ten of Datasource's twenty properties are marked required, including sheet and startRow, which
# only apply to a spreadsheet source. The generator turns required into a constructor argument and
# a non-null check, so a JDBC datasource cannot be built at all.
patch "most of Datasource's required properties are not required" \
    '.components.schemas.Datasource.required | length > 3' \
    '.components.schemas.Datasource.required = ["columns", "connection", "type"]'

echo
echo "── Authentication ──────────────────────────────────────────────────────"

# The spec declares no security scheme whatsoever, so the generated client has nowhere to put
# credentials.
patch "declare the basic authentication the server actually uses" \
    '.components | has("securitySchemes") | not' \
    '.components.securitySchemes = { "basicAuth": { "type": "http", "scheme": "basic" } }
     | .security = [ { "basicAuth": [] } ]'

echo
printf '%s applied, %s no longer needed, %s failed\n' "$applied" "$skipped" "$failed"

if [[ "$failed" -gt 0 ]]; then
    echo "Not writing $TARGET while any patch is failing." >&2
    exit 1
fi

cp "$WORK" "$TARGET"
echo "Wrote $TARGET"
