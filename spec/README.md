# Essbase REST API specifications

An archive of the OpenAPI specification Oracle ships with each Essbase release -
Swagger 2.0 through 21.7, OpenAPI 3.0.1 from 26.1 - and generated reports
showing what changed between them.

This lives here rather than alongside a product that consumes the API because
the question it answers - "when did this endpoint appear, and what shape is it
now?" - is the question this client has to answer every time it grows a method.

## Layout

| Path | What it is |
| --- | --- |
| `versions/essbase-<version>-swagger.json` (or `-openapi.json`) | The specification as shipped, one per Essbase release |
| `diffs/README.md` | Index: what changed at each step, and every endpoint added, by version |
| `diffs/<old>-to-<new>.md` | The full report for one step |
| `diffs/coverage.md` | Every endpoint in the newest spec, and whether this client reaches it |
| `diffs/cross-language.md` | The same endpoints measured against both this client and EssSharp |
| `essbase_spec.py` | The analysis: loading, comparing, measuring coverage |
| `selftest.py` | Checks on the analysis itself, chiefly Swagger 2.0 / OpenAPI 3 symmetry |
| `generate.py` | The rendering: Markdown into `diffs/`, and the Pages site |
| `openapi-diff/` | Optional wrapper around the openapi-diff CLI, for HTML reports and backward-compatibility verdicts |

Start at [diffs/README.md](diffs/README.md) for what changed, or
[diffs/coverage.md](diffs/coverage.md) for what is left to implement. Both are
also published to GitHub Pages - see "The published site" below.

## Archived versions

| Version | Endpoints | Models | SHA-256 of the file as received |
| --- | ---: | ---: | --- |
| 21.1 | 337 | 232 | `464033c2571a0b488ff4ffc9353f53f4eb35ec5f24e227502989b70145345667` |
| 21.2 | 336 | 232 | `7c0be7e425c670e87a3942b881ae35cf0354f52aaaa416c94a57a70da326d88a` † |
| 21.3 | 335 | 231 | `6f5467f7dbc0f32b93ff35bfa057d1aec699ffd14d507ed08b2968acf9e6d1f5` † |
| 21.4 | 343 | 238 | `1b72596764725465f93b39db6e0d63f28f500736aee7b8b20799453c67c15100` |
| 21.5 | 344 | 240 | `5f4230883dd46bad168a34f9e28e2bb8c71da14a7454b0913c288479c37a7d2b` |
| 21.6 | 360 | 246 | `453ef2eea5c132d5bd5738b4e8c10ebaead74877a956780efcb2074c6711eb38` † |
| 21.7 | 361 | 246 | `2b1db62052effd85ec2be160dfb6576bd6b2436c1a6b80bbc39cb0e54fa64ea7` |
| 21.8 | 383 | 250 | `27b08a69be2940c1161ceb0add19dd9e3d9319a9d5507c59ad6e63bc06ee9537` † |
| 26.1 | 494 | 256 | `7c975012769b0710428faff5eed98b2261e3f72fec522e86b530c456cdeaa844` |

"Endpoints" counts path/method pairs, so one path with a `GET` and a `DELETE`
counts twice; `paths` counts in the specs themselves are lower.

The checked-in files are pretty-printed with two-space indentation so that a
future version shows up as a readable git diff. Key order and content are
untouched - the checksums above are of the original bytes, before reformatting,
which is why they will not match `shasum` run on the files here.

† 21.2, 21.3, 21.6 and 21.8 came from the sibling EssSharp repository's own
archive rather than from a server, so their checksums are of that copy and not
of the bytes Oracle served: EssSharp stores its specs minified, and the original
formatting is gone. The content is verified even though the provenance of the
bytes is a step removed - for all four versions the two archives share, the
files are identical once key order and whitespace are normalised, so the two
archives are captures of the same specifications. Replace these rows with a
server capture if one ever turns up.

The Swagger 2.0 files declare `info.version` as `1.0` with an empty `info.title`,
and 26.1 declares `V1` with the title "Essbase REST API" - neither records which
Essbase release it came from. The filename is the only version marker; keep it
accurate.

Adding a version re-pairs the chain, and `generate.py` deletes the reports for
steps that no longer exist as part of regenerating - adding 21.7 replaced the
21.5-to-26.1 report with 21.5-to-21.7 and 21.7-to-26.1. Without that the old
report would sit there looking current while describing a comparison nothing
makes any more, and because it is committed and simply never rewritten, the
staleness check in CI would not have caught it.

## Relationship to the generated client

The client is *not* generated from these files. It is generated from
`formatted.json` in the repository root, which `process.sh` patches into
`src/main/resources/processed.json` - see "Regenerating the client" in
[../AGENTS.md](../AGENTS.md).

`formatted.json` predates every spec archived here: it has 326 endpoints against
21.1's 337, and it still carries `/settings/resources`, which 21.1 had already
dropped. So the generated `com.appliedolap.essbase.client.*` layer describes an
API older than any version in `versions/`. That is worth knowing before
concluding that an endpoint is missing from Essbase when it is only missing from
the spec this client was built from.

Against 26.1 the gap is wide: 177 of its 494 endpoints have no generated method
at all, and seven generated methods point at endpoints 26.1 no longer has.
Regenerating from a current spec closes both at once, and is the single
highest-leverage change available to this library. `diffs/coverage.md` has the lists.

## Adding a version

1. Fetch the specification from a server of that version. The path has moved
   between releases - 26.1 serves OpenAPI 3.0.1 at `/rest/v1/openapi.json` - so
   take it from the running server rather than assuming one; the Essbase web
   interface links to its own REST API documentation, and the JSON behind that
   page is what to save.
2. Record its SHA-256 (`shasum -a 256 <file>`) for the table above.
3. Save it as `versions/essbase-<version>-swagger.json`, or `-openapi.json` if
   the server served OpenAPI 3, pretty-printed:

       python3 -c "import json,sys; d=json.load(open(sys.argv[1])); json.dump(d, open(sys.argv[2],'w'), indent=2)" raw.json spec/versions/essbase-21.7-swagger.json

4. Regenerate the reports and commit both the spec and the reports:

       python3 spec/generate.py

`generate.py` discovers whatever is in `versions/`, sorts by version number, and
diffs each adjacent pair, so no list of versions needs maintaining. Name the file
`-openapi.json` instead of `-swagger.json` when the server served OpenAPI 3, as
26.1 does; either spelling is picked up.

## Crossing the Swagger 2.0 / OpenAPI 3 boundary

Essbase served Swagger 2.0 through 21.7 and OpenAPI 3.0.1 from 26.1, so the
21.7-to-26.1 step changes format. Both are understood, and the difference is
normalised away rather than reported:

- a request body is compared as one entry whatever the spec calls it - Swagger
  2.0 gives it a parameter name, OpenAPI 3 does not;
- media types are compared separately from schemas, since 2.0 carries one schema
  per operation while 3.x carries one per media type; and
- `consumes`/`produces` are compared only where both specs record them. A
  response with no body carries no media type in OpenAPI 3 but is still covered
  by an operation's `produces` in 2.0, so treating the absence as "none" rather
  than "unrecorded" would flag a change on every such endpoint.

Getting this wrong is not subtle and not hypothetical: before it was fixed, a
spec compared against its own OpenAPI 3 form reported 336 of its 344 endpoints
as changed. `spec/selftest.py` asserts that comparison now reports nothing, for
every archived spec, and CI runs it.

## Regenerating the reports

    python3 spec/generate.py

Python 3 standard library only; no network access, no Maven, no other tools.
It rewrites every file under `diffs/`, so the reports and the specs stay in
step and a stale report shows up as an unexpected diff. CI enforces this: the
Pages workflow regenerates and fails if `diffs/` comes out different from what
was committed. The one exception is `cross-language.md`, which needs an
EssSharp checkout and is left untouched without one - see "The C# client".

`python3 spec/selftest.py` checks the analysis itself, and needs nothing either.
CI runs it before generating anything.

Description and summary text are deliberately ignored. Oracle rewords
documentation constantly, and a report that lists those changes buries the ones
that matter - an endpoint, a parameter, a response, or a model property
appearing, disappearing, or changing type.

## Coverage

[diffs/coverage.md](diffs/coverage.md) answers the other question: of everything
the newest archived API offers, what does this client already reach? Each
endpoint is one of three things.

| State | What to do about it |
| --- | --- |
| Exposed | Nothing - the hand-written API reaches it |
| Generated, not exposed | Write an `Ess*` wrapper; the generated method is already there |
| Not in the generated client | Regenerate from a current spec first, then wrap |

This is measured, not maintained by hand: `essbase_spec.py` reads the paths back
out of the generated `client/api/*.java` sources - the generator writes each
spec path into the request builder verbatim - and checks which of those methods
the hand-written layer calls.

Two limits are worth knowing. Endpoints reached through the `NativeHttp` helper
only count when the call site passes a literal path; the ones that assemble a
path from pieces at runtime cannot be attributed, so the exposed count is a
floor. And coverage says nothing about whether a wrapper is *good* - only that
one exists.

## The C# client

[EssSharp](https://github.com/appliedolap/EssSharp) is the sibling client for
the same API, built the same way: a generated client under a hand-written
`Ess*`/`IEss*` layer. That shared shape means the coverage measurement works on
it unchanged, and [diffs/cross-language.md](diffs/cross-language.md) reports
both at once - which answers the question neither client's own report can, of
where one has gone that the other has not.

As measured against 26.1: this client exposes 63 endpoints and EssSharp 101, of
which 52 overlap. EssSharp's generated client is a strict superset of this
one's, because it generates from 21.8 while this one still generates from a
spec older than 21.1 - so 66 endpoints are already reachable in C# that need a
regeneration here before they can be wrapped at all.

EssSharp lives in another repository, so the report is optional. `generate.py`
looks for a checkout in `--esssharp DIR`, then `$ESSSHARP_ROOT`, then a sibling
`../EssSharp`. Finding none it leaves `cross-language.md` exactly as committed
rather than deleting it or writing it empty: a clone of this repository on its
own must not produce a spurious diff, which the staleness check in CI would
read as a failure.

`selftest.py` checks the C# reader by requiring the endpoints it finds to match
one archived specification *exactly*, neither more nor fewer. That is a test of
the reader, not of EssSharp, and it earns its place: the first version of the
regex could not match a nested return type, so `Get<List<SessionAttributes>>`
and every other collection-returning endpoint went missing while the totals
still looked reasonable. Matching a spec exactly catches that; counting does not.

## The published site

**https://developer.dodecasoftware.com/essbase-rest-client/**

That host is not configured here: the custom domain belongs to the organisation
site (`appliedolap/appliedolap.github.io` carries the `CNAME`), and project
sites inherit it, which is why this repository's own Pages `cname` is empty and
why the URL carries the repository name as a path. `appliedolap.github.io/essbase-rest-client/`
redirects there.

`.github/workflows/pages.yml` deploys the following on every push to `main` that
touches `spec/`, `src/main/java/`, or `pom.xml`:

| Path | What |
| --- | --- |
| `/` | What's new in each version, with the coverage summary |
| `/coverage.html` | The full coverage table, filterable by state, tag and text |
| `/<old>-to-<new>.html` | One version step in full |
| `/api/` | Javadoc for the public API (the generated client is excluded) |

Build it locally with:

    python3 spec/generate.py --site _site

Pages is enabled for this repository with "GitHub Actions" as the source, so
Jekyll is not involved and there is no branch to deploy from. Do not add one of
GitHub's starter Pages workflows alongside this one; two of them contend over
the same deployment.

HTTPS serves correctly but "Enforce HTTPS" is off on both this repository and
the organisation site, so `appliedolap.github.io/essbase-rest-client/` redirects
to the `http` form of the custom domain. Prefer the `https` URL when linking.

## Backward-compatibility verdicts and HTML

For the two things `generate.py` does not do, there is a wrapper around the
[openapi-diff](https://github.com/OpenAPITools/openapi-diff) CLI:

    spec/openapi-diff/run.sh 21.4 21.5

It writes an HTML report under `spec/openapi-diff/target/` (ignored by git) and
prints whether the newer spec is backward compatible with the older one. It
needs Maven and, on first run, network access to resolve the CLI.

Its per-change detail is thinner than `generate.py`'s - where it reports that a
parameter "changed", `generate.py` reports what it changed from and to - so reach
for it for the verdict, not the detail.
