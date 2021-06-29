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