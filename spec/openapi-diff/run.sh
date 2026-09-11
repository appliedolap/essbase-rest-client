#!/usr/bin/env bash
#
# Run the openapi-diff CLI over two archived specs, for the things spec/diff.py
# deliberately does not do: an HTML report, and a verdict on whether the newer
# spec is backward compatible with the older one.
#
# Usage:  spec/openapi-diff/run.sh <old-version> <new-version> [output.html]
# Example: spec/openapi-diff/run.sh 21.4 21.5
#
# Output is written to the scratch directory below and is not checked in - the
# reports under spec/diffs/ are the ones meant to be read and reviewed.

set -euo pipefail

here="$(cd "$(dirname "$0")" && pwd)"
versions="$here/../versions"
scratch="$here/target"

if [ "$#" -lt 2 ]; then
	echo "Usage: $0 <old-version> <new-version> [output.html]" >&2
	echo "Available versions:" >&2
	ls "$versions" | sed -n 's/^essbase-\(.*\)-swagger\.json$/  \1/p' >&2
	exit 1
fi

old="$versions/essbase-$1-swagger.json"
new="$versions/essbase-$2-swagger.json"
out="${3:-$scratch/$1-to-$2.html}"

for spec in "$old" "$new"; do
	if [ ! -f "$spec" ]; then
		echo "No such spec: $spec" >&2
		exit 1
	fi
done

mkdir -p "$scratch"

# Resolving the CLI needs Maven Central the first time; after that it is cached.
if [ ! -f "$scratch/classpath.txt" ]; then
	echo "Resolving openapi-diff..."
	mvn -q -B -f "$here/pom.xml" dependency:build-classpath \
		-Dmdep.outputFile="$scratch/classpath.txt"
fi

java -cp "$(cat "$scratch/classpath.txt")" \
	org.openapitools.openapidiff.cli.Main "$old" "$new" --html "$out"

echo
echo "HTML report: $out"
