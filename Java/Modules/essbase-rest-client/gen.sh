#!/bin/sh

java -jar openapi-generator-cli-5.1.1.jar generate \
	-i src/main/resources/processed.json \
	--api-package com.appliedolap.essbase.client.api \
	--package-name com.appliedolap.essbase.client \
	--model-package com.appliedolap.essbase.client.model \
	--group-id com.appliedolap.essbase \
	--artifact-id essbase-rest-client \
	-g java \
	--output target

cp -R target/src/main/java/* ./src/main/java

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