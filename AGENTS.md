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

## Authentication

How a client proves who it is lives behind `EssAuthentication`, not inside `ApiClientFactory`. Four
strategies ship: `basic` (credentials on every request), `session` (credentials until the server
issues a session, then the session - the default), `sessionCookie` (an existing session, no
password anywhere), and `bearerToken`.

`sessionCookie` is the one with a reason to exist beyond tidiness. Essbase accepts its session
cookies on their own, with no `Authorization` header at all - verified against 21.7 by
`SessionCookieAuthenticationIT`. That is what makes a deployment behind an external identity
provider reachable: a federated user has no password this API can check, so the only way to act as
one is to present a session established elsewhere.

Whether a given server accepts a *bearer* token is a property of that deployment, not of this
library - an on-premises 21.7 instance rejects them outright. Test before relying on it.

When adding a strategy, put the header-shaping logic where `EssAuthenticationTest` can reach it
without a server. That suite is the only offline test coverage in this project.

`EssServer.signOff()` ends this client's own session (`DELETE /session`) - not to be confused with
`killSessions`, which ends other people's. It always tells the strategy the session is over, even
if the call failed, because a session that could not be signed off should not keep being presented.
A password-backed strategy then re-authenticates on the next call; a supplied session has nothing
to fall back on, which is the correct outcome.

`EssServer.getSessionExpiry()` reports when the session dies, and is empty while there is no
session - Essbase sends a `sessionExpiry` cookie even before one exists, dated in the past.

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
no mocked/offline test path today for anything that talks to a server. The exception is
`com.appliedolap.essbase.auth.EssAuthenticationTest`, which covers header construction and needs
no server, so `mvn test` does now run (and CI can run) that much.

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
