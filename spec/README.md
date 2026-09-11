# Essbase REST API specifications

An archive of the OpenAPI (Swagger 2.0) specification Oracle ships with each
Essbase release, and generated reports showing what changed between them.

This lives here rather than alongside a product that consumes the API because
the question it answers - "when did this endpoint appear, and what shape is it
now?" - is the question this client has to answer every time it grows a method.

## Layout

| Path | What it is |
| --- | --- |
| `versions/essbase-<version>-swagger.json` | The specification as shipped, one per Essbase release |
| `diffs/README.md` | Index: what changed at each step, and every endpoint added, by version |
| `diffs/<old>-to-<new>.md` | The full report for one step |
| `diffs/coverage.md` | Every endpoint in the newest spec, and whether this client reaches it |
| `essbase_spec.py` | The analysis: loading, comparing, measuring coverage |
| `generate.py` | The rendering: Markdown into `diffs/`, and the Pages site |
| `openapi-diff/` | Optional wrapper around the openapi-diff CLI, for HTML reports and backward-compatibility verdicts |

Start at [diffs/README.md](diffs/README.md) for what changed, or
[diffs/coverage.md](diffs/coverage.md) for what is left to implement. Both are
also published to GitHub Pages - see "The published site" below.

## Archived versions

| Version | Endpoints | Models | SHA-256 of the file as received |
| --- | ---: | ---: | --- |
| 21.1 | 337 | 232 | `464033c2571a0b488ff4ffc9353f53f4eb35ec5f24e227502989b70145345667` |
| 21.4 | 343 | 238 | `1b72596764725465f93b39db6e0d63f28f500736aee7b8b20799453c67c15100` |
| 21.5 | 344 | 240 | `5f4230883dd46bad168a34f9e28e2bb8c71da14a7454b0913c288479c37a7d2b` |

"Endpoints" counts path/method pairs, so one path with a `GET` and a `DELETE`
counts twice; `paths` counts in the specs themselves are lower.

The checked-in files are pretty-printed with two-space indentation so that a
future version shows up as a readable git diff. Key order and content are
untouched - the checksums above are of the original bytes, before reformatting,
which is why they will not match `shasum` run on the files here.

Every spec declares `info.version` as `1.0` and an empty `info.title`, so a file
carries no usable record of which Essbase release it came from. The filename is
the only version marker; keep it accurate.

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

## Adding a version

1. Fetch the specification from a server of that version. The path has moved
   between releases - 26.1 serves OpenAPI 3.0.1 at `/rest/v1/openapi.json` - so
   take it from the running server rather than assuming one; the Essbase web
   interface links to its own REST API documentation, and the JSON behind that
   page is what to save.
2. Record its SHA-256 (`shasum -a 256 <file>`) for the table above.
3. Save it as `versions/essbase-<version>-swagger.json`, pretty-printed:

       python3 -c "import json,sys; d=json.load(open(sys.argv[1])); json.dump(d, open(sys.argv[2],'w'), indent=2)" raw.json spec/versions/essbase-21.7-swagger.json

4. Regenerate the reports and commit both the spec and the reports:

       python3 spec/generate.py

`generate.py` discovers whatever is in `versions/`, sorts by version number, and
diffs each adjacent pair, so no list of versions needs maintaining. Swagger 2.0
and OpenAPI 3.x are both understood, which matters from 26.1 on - request bodies
and `components/schemas` are normalised onto the 2.0 shape so a mixed archive
still compares cleanly.

## Regenerating the reports

    python3 spec/generate.py

Python 3 standard library only; no network access, no Maven, no other tools.
It rewrites every file under `diffs/`, so the reports and the specs stay in
step and a stale report shows up as an unexpected diff. CI enforces this: the
Pages workflow regenerates and fails if `diffs/` comes out different from what
was committed.

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

## The published site

`.github/workflows/pages.yml` deploys the same content to GitHub Pages on every
push to `main` that touches `spec/`, `src/main/java/`, or `pom.xml`:

| Path | What |
| --- | --- |
| `/` | What's new in each version, with the coverage summary |
| `/coverage.html` | The full coverage table, filterable by state, tag and text |
| `/<old>-to-<new>.html` | One version step in full |
| `/api/` | Javadoc for the public API (the generated client is excluded) |

Build it locally with:

    python3 spec/generate.py --site _site

Pages has to be enabled for the repository with "GitHub Actions" as the source
(Settings, then Pages) before the first deployment can succeed.

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
