#!/bin/bash
# PostToolUse(Edit|Write): mark Kotlin/Gradle edits as needing build verification.
input=$(cat)
script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
root=$(CDPATH= cd -- "$script_dir/../.." && pwd)
marker="$root/.codex/.kt-dirty"

changed=$(printf '%s' "$input" | python3 -c '
import json
import re
import sys

try:
    payload = json.load(sys.stdin)
except Exception:
    print("")
    raise SystemExit(0)

tool_input = payload.get("tool_input") or {}
strings = []

def walk(value):
    if isinstance(value, str):
        strings.append(value)
    elif isinstance(value, dict):
        for child in value.values():
            walk(child)
    elif isinstance(value, list):
        for child in value:
            walk(child)

walk(tool_input)
text = "\n".join(strings)
print("yes" if re.search(r"\.(kt|kts)\b", text) else "")
' 2>/dev/null)

if [ "$changed" = "yes" ]; then
  mkdir -p "$root/.codex"
  touch "$marker"
fi

exit 0
