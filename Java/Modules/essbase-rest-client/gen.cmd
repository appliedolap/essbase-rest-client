@echo off

:: Generate the java code from the processed.json
java -jar openapi-generator-cli-5.1.1.jar generate ^
  --input-spec .\src\main\resources\processed.json ^
  --api-package com.appliedolap.essbase.client.api ^
  --package-name com.appliedolap.essbase.client ^
  --model-package com.appliedolap.essbase.client.model ^
  --group-id com.appliedolap.essbase ^
  --artifact-id essbase-rest-client ^
  --generator-name java ^
  --output .\target

:: Copy the output to source
xcopy .\target\src\main\java\* .\src\main\java\ /E /H /Y
