@echo off

:: Pinned generator flags:
::   --library native              -> uses java.net.http.HttpClient and Jackson (no OkHttp)
::   useJakartaEe=true             -> @jakarta.annotation.Generated (jakarta.* aligned)
::   hideGenerationTimestamp=true  -> stable, diff-friendly output
::   openApiNullable=false         -> avoid pulling in jackson-databind-nullable
::
:: Run process.sh (or equivalent) first to regenerate src\main\resources\processed.json.

java -jar openapi-generator-cli-7.10.0.jar generate ^
  --input-spec .\src\main\resources\processed.json ^
  --generator-name java ^
  --library native ^
  --api-package com.appliedolap.essbase.client.api ^
  --package-name com.appliedolap.essbase.client ^
  --model-package com.appliedolap.essbase.client.model ^
  --group-id com.appliedolap.essbase ^
  --artifact-id essbase-rest-client ^
  --additional-properties=useJakartaEe=true,hideGenerationTimestamp=true,openApiNullable=false ^
  --output .\target

xcopy .\target\src\main\java\* .\src\main\java\ /E /H /Y
