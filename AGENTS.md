# Essbase REST Client Development Guidelines

## Project and toolchain

- This is a Java 17 Maven library, a client for Essbase 21+'s REST API. There's no Maven Wrapper
  here - use the system `mvn`.
- Run `mvn clean verify` for normal validation. Do not activate the `release` profile unless
  publication was explicitly requested - it's gated on purpose, since it both signs artifacts and
  can publish to Maven Central.
- `com.appliedolap.essbase.client.*` (the `api` and `model` subpackages) is generated code - see
  "Regenerating the client" below. Don't hand-edit it; changes belong in `process.sh` or the
  generator invocation in `gen.sh`.
- Public API lives in `com.appliedolap.essbase` (interfaces) and `com.appliedolap.essbase.impl`
  (their implementations, one `Ess*Impl` per interface). Client code should program against the
  interfaces.

## Regenerating the client

1. `process.sh` massages the raw OpenAPI spec (`formatted.json`) into `src/main/resources/processed.json`,
   patching a handful of endpoints where the spec's response/consumes schemas are wrong or missing.
2. `gen.sh` (or `gen.cmd` on Windows) feeds that to `openapi-generator-cli`, using the native
   `java.net.http.HttpClient` library option (no OkHttp) and Jakarta EE annotations, then copies the
   generated sources into `src/main/java`.
3. Review the diff - the generator's output is deterministic but a spec change can still ripple
   into unrelated-looking model classes.

## Tests

Every test that touches a live server extends `scratch.AbstractEssbaseServerTest` (or calls
`com.appliedolap.essbase.ConnectionUtils.server()` directly), which reads connection details from
`~/essbase-test.properties` (`essbase.endpoint`, `essbase.username`, `essbase.password`). There is
no mocked/offline test path today - `mvn test` runs 0 tests by design.

- Tag every live test method with `@Category(com.appliedolap.essbase.testing.ReadOnlyIntegrationTest.class)`
  if it only reads server state, or `@Category(...DestructiveIntegrationTest.class)` if it
  creates, deletes, or otherwise mutates real objects. A class can mix both at the method level.
- Name the class `*IT.java`, not `*Test.java` - Failsafe (not Surefire) is what runs these, via
  `mvn verify -Pintegration-read-only` or `mvn verify -Pintegration-destructive
  -DallowDestructiveEssbaseTests=true`. The destructive profile refuses to run without that flag.
- CI does not run either profile - GitHub-hosted runners have no route to a real Essbase server.

## Publishing

- `release` profile: GPG-signs artifacts and pushes to Maven Central via
  `central-publishing-maven-plugin`, with `autoPublish=true`. Only activate when publication was
  explicitly requested.
- `waitUntil` is set to `validated`, not `published` - waiting for full publication can exceed the
  default poll timeout even when the deployment is going on to succeed, misreporting a good publish
  as a build failure. Don't change this back without a strong reason.
