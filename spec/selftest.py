#!/usr/bin/env python3
"""Checks on the spec analysis itself. Run with `python3 spec/selftest.py`.

The one that matters is symmetry. Essbase served Swagger 2.0 through 21.7 and
OpenAPI 3.0.1 from 26.1, so one step in the archive crosses formats, and the
analysis has to normalise the two well enough that the step reports the API
changes and nothing else. This converts each archived spec into its OpenAPI 3
form and asserts that diffing it against itself reports no change at all.

That is not a hypothetical. Before the normalisation was fixed, the same spec
compared against its own OpenAPI 3 form reported 336 of 344 endpoints as
changed - request bodies keyed by media type on one side and by parameter name
on the other, responses prefixed with a media type on one side only, and
`produces` read as empty rather than unrecorded for responses with no body.

Standard library only; no network, no server.
"""

from __future__ import annotations

import copy
import json
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parent))

import essbase_spec as spec  # noqa: E402

METHODS = spec.METHODS


def _reref(node):
    """Rewrite Swagger 2.0 definition refs to the OpenAPI 3 components path."""
    if isinstance(node, dict):
        return {
            key: (
                "#/components/schemas/" + value.rsplit("/", 1)[-1]
                if key == "$ref" and isinstance(value, str)
                else _reref(value)
            )
            for key, value in node.items()
        }
    if isinstance(node, list):
        return [_reref(value) for value in node]
    return node


def to_openapi3(source: dict) -> dict:
    """Convert a Swagger 2.0 spec into the OpenAPI 3 shape.

    Only the parts the analysis reads are converted, and deliberately the way a
    real OpenAPI 3 document does it: media types live on the request body and on
    each response, a response with no body carries no `content` at all, and
    operation-level `consumes`/`produces` do not exist.
    """
    result = {
        "openapi": "3.0.1",
        "info": source.get("info", {}),
        "paths": {},
        "components": {"schemas": _reref(source.get("definitions", {}))},
    }

    for path, item in source["paths"].items():
        new_item = {}
        for method in METHODS:
            operation = item.get(method)
            if not isinstance(operation, dict):
                continue
            operation = _reref(copy.deepcopy(operation))
            consumes = operation.pop("consumes", None) or ["application/json"]
            produces = operation.pop("produces", None) or ["application/json"]

            parameters, body = [], None
            for parameter in operation.get("parameters") or []:
                if parameter.get("in") == "body":
                    body = parameter
                    continue
                converted = {
                    key: value
                    for key, value in parameter.items()
                    if key in ("name", "in", "required", "description")
                }
                converted["schema"] = {
                    key: value
                    for key, value in parameter.items()
                    if key in ("type", "format", "enum", "items", "$ref")
                }
                parameters.append(converted)
            operation["parameters"] = parameters

            if body is not None:
                operation["requestBody"] = {
                    "content": {
                        media: {"schema": body.get("schema", {})} for media in consumes
                    }
                }
                if body.get("required"):
                    operation["requestBody"]["required"] = True

            responses = {}
            for code, response in (operation.get("responses") or {}).items():
                converted = {"description": response.get("description", "")}
                if "schema" in response:
                    converted["content"] = {
                        media: {"schema": response["schema"]} for media in produces
                    }
                responses[str(code)] = converted
            operation["responses"] = responses

            new_item[method] = operation
        result["paths"][path] = new_item

    return result


def _empty(operations: dict, models: dict) -> bool:
    return not any(
        (
            operations["added"],
            operations["removed"],
            operations["changed"],
            models["added"],
            models["removed"],
            models["changed"],
        )
    )


def _report(label: str, operations: dict, models: dict) -> bool:
    if _empty(operations, models):
        print(f"  ok    {label}")
        return True
    print(f"  FAIL  {label}")
    print(
        f"        endpoints +{len(operations['added'])} "
        f"-{len(operations['removed'])} ~{len(operations['changed'])}; "
        f"models +{len(models['added'])} -{len(models['removed'])} "
        f"~{len(models['changed'])}"
    )
    for key, notes in operations["changed"][:5]:
        print(f"        {key[0]} {key[1]}")
        for note in notes[:3]:
            print(f"          - {note}")
    return False


def _check_pruning() -> bool:
    """`prune_orphans` deletes files, so pin down exactly what it will touch.

    The hazard is not failing to delete an orphan; it is deleting `README.md`,
    `coverage.md`, or anything else that does not name a version step.
    """
    import generate
    import tempfile

    diffs = [{"old": "21.5", "new": "21.7"}, {"old": "21.7", "new": "26.1"}]
    keep = ["README.md", "coverage.md", "21.5-to-21.7.md", "21.7-to-26.1.md", "notes-to-self.md"]
    orphans = ["21.5-to-26.1.md", "21.1-to-21.5.md"]

    with tempfile.TemporaryDirectory() as raw:
        directory = Path(raw)
        for name in keep + orphans:
            (directory / name).write_text("x")
        removed = {path.name for path in generate.prune_orphans(directory, ".md", diffs)}
        survivors = {path.name for path in directory.glob("*.md")}

    problems = []
    if removed != set(orphans):
        problems.append(f"removed {sorted(removed)}, expected {sorted(orphans)}")
    if survivors != set(keep):
        problems.append(f"kept {sorted(survivors)}, expected {sorted(keep)}")

    if problems:
        print("  FAIL  prune_orphans")
        for problem in problems:
            print(f"        {problem}")
        return False
    print(f"  ok    removed {len(orphans)} orphans, kept {len(keep)} others")
    return True


def _check_csharp(specs: list[tuple[str, dict]]) -> bool:
    """The endpoints read out of EssSharp must be exactly one archived spec's.

    A generated client is a faithful image of the specification it came from, so
    the set of paths read back out of it should match one of the archived specs
    exactly - no more, no fewer. That makes this a real test of the reader
    rather than of EssSharp: the first version of the C# regex could not match a
    nested return type, so `Get<List<SessionAttributes>>("/sessions")` and every
    other collection-returning endpoint went missing, and the totals still
    looked plausible. An exact-match check catches that; a count does not.

    Skipped, not failed, when there is no EssSharp checkout - it lives in
    another repository and CI here has no reason to have one.
    """
    root = spec.find_esssharp()
    if root is None:
        print("  skip  no EssSharp checkout")
        return True

    generated = {
        (method, spec.normalise_path(path))
        for method, path in spec.csharp_generated_endpoints(root)
    }
    for version, document in specs:
        archived = {
            (method, spec.normalise_path(path))
            for method, path in spec.operations_of(document).keys()
        }
        if generated == archived:
            print(f"  ok    matches {version} exactly, {len(generated)} endpoints")
            return True

    closest, overlap = None, -1
    for version, document in specs:
        archived = {
            (method, spec.normalise_path(path))
            for method, path in spec.operations_of(document).keys()
        }
        if len(generated & archived) > overlap:
            closest, overlap = version, len(generated & archived)
    print(
        f"  FAIL  {len(generated)} endpoints match no archived spec exactly; "
        f"closest is {closest} with {overlap} in common"
    )
    return False


def main() -> int:
    specs = spec.load_specs()
    if not specs:
        print(f"No specs found in {spec.VERSIONS_DIR}", file=sys.stderr)
        return 1

    passed = True

    print("A spec compared against itself reports no change:")
    for version, document in specs:
        passed &= _report(
            f"{version} vs itself",
            spec.diff_operations(document, document),
            spec.diff_models(document, document),
        )

    print("A spec compared against its own OpenAPI 3 form reports no change:")
    for version, document in specs:
        if "swagger" not in document:
            print(f"  skip  {version} is already OpenAPI 3")
            continue
        converted = to_openapi3(document)
        passed &= _report(
            f"{version} vs {version} as OpenAPI 3",
            spec.diff_operations(document, converted),
            spec.diff_models(document, converted),
        )

    print("Pruning removes orphaned step reports and nothing else:")
    passed &= _check_pruning()

    print("Coverage accounts for every endpoint exactly once:")
    coverage = spec.build_coverage(specs)
    total = len(coverage["entries"])
    counted = sum(coverage["counts"].values())
    if total == counted == len(spec.operations_of(specs[-1][1])):
        print(f"  ok    {total} endpoints, {counted} classified")
    else:
        print(f"  FAIL  {total} entries but {counted} classified")
        passed = False

    print("The C# client reads back as exactly one archived specification:")
    passed &= _check_csharp(specs)

    print("PASS" if passed else "FAIL")
    return 0 if passed else 1


if __name__ == "__main__":
    sys.exit(main())
