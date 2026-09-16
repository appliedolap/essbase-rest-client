#!/bin/sh

# Pinned generator flags:
#   --library native              -> uses java.net.http.HttpClient and Jackson (no OkHttp)
#   useJakartaEe=true             -> @jakarta.annotation.Generated (jakarta.* aligned)
#   hideGenerationTimestamp=true  -> stable, diff-friendly output
#   openApiNullable=false         -> avoid pulling in jackson-databind-nullable
#
# Run process.sh first to regenerate src/main/resources/processed.json.

# The generator writes into target without clearing it, so a previous run's output survives and is
# copied back in alongside the new - which is how a Datasource0 from an earlier attempt reappeared.
rm -rf target/src/main/java

java -jar openapi-generator-cli-7.10.0.jar generate \
	-i src/main/resources/processed.json \
	-g java \
	--library native \
	--api-package com.appliedolap.essbase.client.api \
	--package-name com.appliedolap.essbase.client \
	--model-package com.appliedolap.essbase.client.model \
	--group-id com.appliedolap.essbase \
	--artifact-id essbase-rest-client \
	--additional-properties=useJakartaEe=true,hideGenerationTimestamp=true,openApiNullable=false \
	--output target

# Clear the generated packages before copying, rather than copying over the top. A tag or model
# renamed between spec versions otherwise leaves the old class behind: the api tag "Application Data
# Sources" became "Application Datasources", and on a case-insensitive filesystem the new content
# landed in the old ApplicationDataSourcesApi.java, giving a file whose name and class disagree.
rm -rf src/main/java/com/appliedolap/essbase/client
cp -R target/src/main/java/* ./src/main/java

# That clears the *working tree* but not the index. git has core.ignorecase=true on a Mac, so when
# a tag's casing changes it keeps the old path - leaving ApplicationDataSourcesApi.java holding
# class ApplicationDatasourcesApi, which compiles here and fails on any case-sensitive filesystem.
# After a regeneration, check for it and realign the index by hand:
#
#   git ls-files src/main/java/com/appliedolap/essbase/client | while read -r f; do
#       [ -e "$f" ] || continue
#       cls=$(basename "$f" .java)
#       grep -q "class $cls\b" "$f" || echo "MISMATCH: $f"
#   done
#
# then for each: git rm --cached <old>; git add <new>

# Alternate invocation example:
#java -jar openapi-generator-cli-5.2.0.jar generate \
#-i http://localhost:8090/v3/api-docs \
#-g java \
#-p dateLibrary=java8 \
#--library resttemplate  \
#--api-package com.appliedolap.dodeca.cloud.api \
#--package-name com.appliedolap.dodeca.cloud \
#--group-id com.appliedolap.dodeca.cloud \
#--model-package com.appliedolap.dodeca.cloud.model \
#--artifact-id dodeca-cloud-client2 \
#--artifact-version 1.0.0 \
#--strict-spec \
#--verbose
#-additional-properties=licenseUrl=foo,java8=true,dateLibrary=joda \
