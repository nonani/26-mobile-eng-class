#!/bin/bash
# Stop: block finishing after Kotlin/Gradle edits until a Gradle verification command ran.
input=$(cat)
script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
root=$(CDPATH= cd -- "$script_dir/../.." && pwd)
marker="$root/.codex/.kt-dirty"

active=$(printf '%s' "$input" | python3 -c '
import json
import sys

try:
    payload = json.load(sys.stdin)
except Exception:
    print("")
    raise SystemExit(0)

print(payload.get("stop_hook_active", False))
' 2>/dev/null)

if [ "$active" != "True" ] && [ "$active" != "true" ] && [ -f "$marker" ]; then
  echo "Kotlin/Gradle files were modified but no Gradle verification ran afterwards. Run the gradle-build-check skill, for example ./gradlew :app:assembleDebug, before finishing. If a build is genuinely unnecessary, delete .codex/.kt-dirty and finish." >&2
  exit 2
fi

printf '{}\n'
exit 0
