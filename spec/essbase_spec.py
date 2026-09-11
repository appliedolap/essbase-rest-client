"""Reading, comparing and measuring coverage of Essbase REST API specifications.

Pure analysis - nothing here writes output. `generate.py` renders what this
produces. Standard library only.

Two things are modelled:

* the difference between two specs, at the level of endpoints, parameters,
  responses and models, ignoring description and summary prose; and
* how much of the newest spec this client actually reaches, by reading the
  paths back out of the generated `client.api` classes and seeing which of
  their methods the hand-written layer calls.
"""

from __future__ import annotations

import json
import re
from pathlib import Path

SPEC_DIR = Path(__file__).resolve().parent
VERSIONS_DIR = SPEC_DIR / "versions"
REPO_ROOT = SPEC_DIR.parent
JAVA_ROOT = REPO_ROOT / "src" / "main" / "java" / "com" / "appliedolap" / "essbase"

# Either spelling of the suffix: Essbase served Swagger 2.0 through 21.7 and
# OpenAPI 3.0.1 from 26.1, and the filename should say which it is.
VERSION_FILE = re.compile(
    r"^essbase-(?P<version>\d+(?:\.\d+)*)-(?:swagger|openapi)\.json$"
)
METHODS = ("get", "put", "post", "delete", "patch", "head", "options")

Endpoint = tuple[str, str]  # (METHOD, path)


# --------------------------------------------------------------------------
# Loading and normalising
# --------------------------------------------------------------------------


def version_key(version: str) -> tuple[int, ...]:
    return tuple(int(part) for part in version.split("."))


def discover_specs() -> list[tuple[str, Path]]:
    """Return (version, path) for every archived spec, oldest version first."""
    found = []
    for path in sorted(VERSIONS_DIR.glob("*.json")):
        match = VERSION_FILE.match(path.name)
        if match:
            found.append((match.group("version"), path))
    found.sort(key=lambda item: version_key(item[0]))
    return found


def load_specs() -> list[tuple[str, dict]]:
    return [(version, json.loads(path.read_text())) for version, path in discover_specs()]


def schemas_of(spec: dict) -> dict:
    """Model definitions, from either Swagger 2.0 or OpenAPI 3.x."""
    if "definitions" in spec:
        return spec["definitions"]
    return spec.get("components", {}).get("schemas", {})


def operations_of(spec: dict) -> dict[Endpoint, dict]:
    """Map (METHOD, path) to its operation object."""
    operations = {}
    for path, item in (spec.get("paths") or {}).items():
        if not isinstance(item, dict):
            continue
        for method in METHODS:
            operation = item.get(method)
            if isinstance(operation, dict):
                # Path-level parameters apply to every operation under it.
                inherited = item.get("parameters") or []
                if inherited:
                    operation = dict(operation)
                    operation["parameters"] = inherited + (operation.get("parameters") or [])
                operations[(method.upper(), path)] = operation
    return operations


def type_signature(node: dict | None) -> str:
    """A short, comparable description of a schema or typed parameter."""
    if not isinstance(node, dict):
        return "?"
    if "$ref" in node:
        return node["$ref"].rsplit("/", 1)[-1]
    if "schema" in node:
        return type_signature(node["schema"])
    kind = node.get("type")
    if kind == "array":
        return f"array[{type_signature(node.get('items'))}]"
    if kind is None:
        return "object" if "properties" in node else "?"
    fmt = node.get("format")
    signature = f"{kind}({fmt})" if fmt else kind
    enum = node.get("enum")
    if enum:
        signature += "{" + ",".join(str(value) for value in enum) + "}"
    return signature


def _schema_signatures(content: dict | None) -> str:
    """One signature for an OpenAPI 3 `content` map.

    Media types are deliberately left out and the distinct schemas deduplicated,
    because Swagger 2.0 carries a single schema for the whole operation whatever
    its `consumes`/`produces` say. Keeping the media type here would make every
    body and every response look changed across the 2.0-to-3.0 boundary; the
    media types are compared separately, by `consumes_of` and `produces_of`.
    """
    signatures = sorted({type_signature(media) for media in (content or {}).values()})
    return " | ".join(signatures) if signatures else "(no content)"


def parameters_of(operation: dict) -> dict[tuple[str, str], str]:
    """Map (location, name) to a type signature, including the request body.

    The body is keyed as `("body", "")` from either spec version - a Swagger 2.0
    body parameter has a name, an OpenAPI 3 request body does not, so neither
    name can be the key without inventing a difference that is not there.
    """
    result = {}
    for parameter in operation.get("parameters") or []:
        if not isinstance(parameter, dict) or "name" not in parameter:
            continue
        if parameter.get("in") == "body":  # Swagger 2.0 body; handled below
            signature = type_signature(parameter)
            if parameter.get("required"):
                signature += " (required)"
            result[("body", "")] = signature
            continue
        key = (parameter.get("in", "?"), parameter["name"])
        signature = type_signature(parameter)
        if parameter.get("required"):
            signature += " (required)"
        result[key] = signature

    body = operation.get("requestBody")  # OpenAPI 3
    if isinstance(body, dict):
        signature = _schema_signatures(body.get("content"))
        if body.get("required"):
            signature += " (required)"
        result[("body", "")] = signature
    return result


def responses_of(operation: dict) -> dict[str, str]:
    """Map status code to the type signature of what it returns."""
    result = {}
    for code, response in (operation.get("responses") or {}).items():
        if not isinstance(response, dict):
            continue
        if "content" in response:  # OpenAPI 3
            result[str(code)] = _schema_signatures(response["content"])
        elif "schema" in response:
            result[str(code)] = type_signature(response["schema"])
        else:
            result[str(code)] = "(no content)"
    return result


# `None` from these means "this spec does not say", which is different from "no
# media types". Swagger 2.0 declares `produces` on an operation even when every
# response is empty; OpenAPI 3 records media types only on a response that has a
# body, so a 204-only operation genuinely carries none. Treating the second as an
# empty list would report a media-type change on every such endpoint across the
# 2.0-to-3.0 boundary - 119 of them between 21.5 and its OpenAPI 3 form - none of
# which is a change to the API. Unknown on either side means no comparison.


def consumes_of(operation: dict) -> list[str] | None:
    """Request media types, from `consumes` (2.0) or the request body (3.x)."""
    if "consumes" in operation:
        return sorted(operation.get("consumes") or [])
    body = operation.get("requestBody")
    if isinstance(body, dict) and body.get("content"):
        return sorted(body["content"])
    return None


def produces_of(operation: dict) -> list[str] | None:
    """Response media types, from `produces` (2.0) or the responses (3.x)."""
    if "produces" in operation:
        return sorted(operation.get("produces") or [])
    media_types = set()
    for response in (operation.get("responses") or {}).values():
        if isinstance(response, dict):
            media_types.update(response.get("content") or {})
    return sorted(media_types) or None


def properties_of(schema: dict) -> dict[str, str]:
    """Map property name to a type signature, marking required ones."""
    required = set(schema.get("required") or [])
    result = {}
    for name, node in (schema.get("properties") or {}).items():
        signature = type_signature(node)
        if name in required:
            signature += " (required)"
        result[name] = signature
    return result


def describe(operation: dict) -> str:
    """The best one-line label a spec offers for an operation."""
    return (operation.get("summary") or operation.get("operationId") or "").strip()


def tag_of(operation: dict) -> str:
    """The first tag on an operation - Oracle uses these to group the API."""
    tags = operation.get("tags") or []
    return tags[0] if tags else "Untagged"


# --------------------------------------------------------------------------
# Comparing
# --------------------------------------------------------------------------


def compare_maps(old: dict, new: dict) -> tuple[list, list, list]:
    """Split two key/signature maps into added, removed and changed keys."""
    added = [key for key in new if key not in old]
    removed = [key for key in old if key not in new]
    changed = [key for key in new if key in old and old[key] != new[key]]
    return added, removed, changed


def diff_operations(old_spec: dict, new_spec: dict) -> dict:
    old_ops = operations_of(old_spec)
    new_ops = operations_of(new_spec)

    added = sorted(key for key in new_ops if key not in old_ops)
    removed = sorted(key for key in old_ops if key not in new_ops)

    changed = []
    for key in sorted(set(old_ops) & set(new_ops)):
        old_op, new_op = old_ops[key], new_ops[key]
        notes = []

        old_params, new_params = parameters_of(old_op), parameters_of(new_op)
        p_added, p_removed, p_changed = compare_maps(old_params, new_params)
        for location, name in sorted(p_added):
            notes.append(
                f"added parameter `{name}` in `{location}` - {new_params[(location, name)]}"
            )
        for location, name in sorted(p_removed):
            notes.append(
                f"removed parameter `{name}` in `{location}` - {old_params[(location, name)]}"
            )
        for location, name in sorted(p_changed):
            notes.append(
                f"parameter `{name}` in `{location}`: "
                f"{old_params[(location, name)]} -> {new_params[(location, name)]}"
            )

        old_responses, new_responses = responses_of(old_op), responses_of(new_op)
        r_added, r_removed, r_changed = compare_maps(old_responses, new_responses)
        for code in sorted(r_added):
            notes.append(f"added response `{code}` - {new_responses[code]}")
        for code in sorted(r_removed):
            notes.append(f"removed response `{code}` - {old_responses[code]}")
        for code in sorted(r_changed):
            notes.append(f"response `{code}`: {old_responses[code]} -> {new_responses[code]}")

        for field, reader in (("consumes", consumes_of), ("produces", produces_of)):
            old_value, new_value = reader(old_op), reader(new_op)
            if old_value is None or new_value is None:
                continue  # one side does not record it; see the note above
            if old_value != new_value:
                notes.append(
                    f"`{field}`: {', '.join(old_value) or '(none)'} -> "
                    f"{', '.join(new_value) or '(none)'}"
                )

        if notes:
            changed.append((key, notes))

    return {
        "added": [(key, describe(new_ops[key])) for key in added],
        "removed": [(key, describe(old_ops[key])) for key in removed],
        "changed": changed,
    }


def diff_models(old_spec: dict, new_spec: dict) -> dict:
    old_models = schemas_of(old_spec)
    new_models = schemas_of(new_spec)

    added = sorted(name for name in new_models if name not in old_models)
    removed = sorted(name for name in old_models if name not in new_models)

    changed = []
    for name in sorted(set(old_models) & set(new_models)):
        old_props = properties_of(old_models[name])
        new_props = properties_of(new_models[name])
        p_added, p_removed, p_changed = compare_maps(old_props, new_props)
        notes = []
        for prop in sorted(p_added):
            notes.append(f"added `{prop}` - {new_props[prop]}")
        for prop in sorted(p_removed):
            notes.append(f"removed `{prop}` - {old_props[prop]}")
        for prop in sorted(p_changed):
            notes.append(f"`{prop}`: {old_props[prop]} -> {new_props[prop]}")
        if notes:
            changed.append((name, notes))

    return {
        "added": [(name, len(properties_of(new_models[name]))) for name in added],
        "removed": [(name, len(properties_of(old_models[name]))) for name in removed],
        "changed": changed,
    }


def build_diffs(specs: list[tuple[str, dict]]) -> list[dict]:
    """Diff every adjacent pair of specs, oldest step first."""
    return [
        {
            "old": old_version,
            "new": new_version,
            "old_spec": old_spec,
            "new_spec": new_spec,
            "operations": diff_operations(old_spec, new_spec),
            "models": diff_models(old_spec, new_spec),
        }
        for (old_version, old_spec), (new_version, new_spec) in zip(specs, specs[1:])
    ]


def first_seen(specs: list[tuple[str, dict]]) -> dict[Endpoint, str]:
    """Map each endpoint to the oldest archived version that contains it."""
    seen = {}
    for version, spec in specs:
        for key in operations_of(spec):
            seen.setdefault(key, version)
    return seen


# --------------------------------------------------------------------------
# Coverage: what of the spec does this client actually reach?
# --------------------------------------------------------------------------

# The generated `native` client builds each request in a private
# `<method>RequestBuilder` that assigns the spec path verbatim - placeholders
# and all - and then names the verb. Both are recovered by reading the source,
# which keeps this honest: it measures the code, not a hand-kept list.
REQUEST_BUILDER = re.compile(r"^\s*private HttpRequest\.Builder (\w+)RequestBuilder\(", re.M)
LOCAL_VAR_PATH = re.compile(r'^\s*String localVarPath = "([^"]*)"', re.M)
HTTP_METHOD = re.compile(r'localVarRequestBuilder\.method\("(\w+)"')

# Some endpoints are reached without the generated client at all, through the
# NativeHttp helper - streaming and file transfer, mostly. Those call sites are
# found by name, and any path literal on them is read off. A site that assembles
# its path from pieces (EssCubeImpl's "/applications/" + name + ...) yields no
# literal and is reported as unattributed rather than quietly ignored.
NATIVE_HTTP_CALL = re.compile(r"NativeHttp\.(?:request|withQuery)\(")
PATH_LITERAL = re.compile(r'"(/[a-zA-Z0-9_\-{}/]*)"')


def normalise_path(path: str) -> str:
    """Path with placeholder names blanked, so {application} matches {applicationName}."""
    return re.sub(r"\{[^}]*\}", "{}", path)


def generated_endpoints(java_root: Path = JAVA_ROOT) -> dict[Endpoint, list[str]]:
    """Map (METHOD, path) to the generated client methods that call it."""
    api_dir = java_root / "client" / "api"
    result: dict[Endpoint, list[str]] = {}
    for source in sorted(api_dir.glob("*.java")):
        text = source.read_text()
        builders = list(REQUEST_BUILDER.finditer(text))
        for index, builder in enumerate(builders):
            end = builders[index + 1].start() if index + 1 < len(builders) else len(text)
            body = text[builder.start() : end]
            path_match = LOCAL_VAR_PATH.search(body)
            method_match = HTTP_METHOD.search(body)
            if path_match and method_match:
                key = (method_match.group(1).upper(), path_match.group(1))
                result.setdefault(key, []).append(f"{source.stem}.{builder.group(1)}")
    return result


def wrapper_sources(java_root: Path = JAVA_ROOT) -> dict[str, str]:
    """Every hand-written source under com.appliedolap.essbase, keyed by path."""
    return {
        str(source.relative_to(REPO_ROOT)): source.read_text()
        for source in sorted(java_root.rglob("*.java"))
        if "client" not in source.relative_to(java_root).parts
    }


def build_coverage(specs: list[tuple[str, dict]], java_root: Path = JAVA_ROOT) -> dict:
    """Measure the newest archived spec against what the client reaches.

    Each endpoint lands in one of three states:

    * `wrapped`        - the hand-written API calls a generated method for it;
    * `generated`      - a generated method exists but nothing calls it, so
                         exposing it is a matter of writing a wrapper; or
    * `not_generated`  - it is absent from the spec the client was generated
                         from, so it needs a regeneration before anything else.
    """
    newest_version, newest_spec = specs[-1]
    operations = operations_of(newest_spec)
    seen = first_seen(specs)
    oldest_version = specs[0][0]

    generated = generated_endpoints(java_root)
    by_normalised = {normalise_path(path): (method, path) for method, path in generated}

    sources = wrapper_sources(java_root)
    hand_written = "\n".join(sources.values())
    # Matched as `.method(` rather than a bare name: a generated method is always
    # invoked on an api object, so this catches every real call while ignoring a
    # hand-written method that merely shares the name - EssServerImpl declares its
    # own getInstanceDetails(), which AboutEssbaseApi also happens to define.
    called = {
        name.split(".", 1)[1]
        for names in generated.values()
        for name in names
        if re.search(rf"\.{re.escape(name.split('.', 1)[1])}\s*\(", hand_written)
    }

    # Paths the hand-written layer requests itself, without the generated client.
    # Only literals that name a real endpoint count; the rest are unattributed.
    known = {normalise_path(path) for _, path in operations}
    direct: set[str] = set()
    unattributed = 0
    for text in sources.values():
        for call in NATIVE_HTTP_CALL.finditer(text):
            statement = text[call.start() : text.find(";", call.start())]
            resolved = {
                normalise_path(literal.group(1))
                for literal in PATH_LITERAL.finditer(statement)
            } & known
            if resolved:
                direct |= resolved
            else:
                unattributed += 1

    entries = []
    for key in sorted(operations, key=lambda item: (item[1], item[0])):
        method, path = key
        operation = operations[key]

        match = generated.get(key)
        if match is None:
            fallback = by_normalised.get(normalise_path(path))
            if fallback and fallback[0] == method:
                match = generated[fallback]

        reached_directly = normalise_path(path) in direct
        if match:
            wrapped = reached_directly or any(
                name.split(".", 1)[1] in called for name in match
            )
            state = "wrapped" if wrapped else "generated"
        elif reached_directly:
            match = []
            state = "wrapped"
        else:
            match = []
            state = "not_generated"

        added = seen.get(key, newest_version)
        entries.append(
            {
                "method": method,
                "path": path,
                "summary": describe(operation),
                "tag": tag_of(operation),
                "state": state,
                "generated_methods": match,
                "added": f"{oldest_version} or earlier" if added == oldest_version else added,
            }
        )

    # Generated methods pointing at endpoints the newest spec no longer has.
    stale = sorted(
        {
            (method, path)
            for method, path in generated
            if normalise_path(path) not in known
        },
        key=lambda item: (item[1], item[0]),
    )

    return {
        "version": newest_version,
        "oldest": oldest_version,
        "entries": entries,
        "stale": [(method, path, generated[(method, path)]) for method, path in stale],
        "direct": sorted(direct),
        "unattributed": unattributed,
        "counts": {
            state: sum(1 for entry in entries if entry["state"] == state)
            for state in ("wrapped", "generated", "not_generated")
        },
    }
