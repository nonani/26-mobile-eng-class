#!/bin/bash
# PostToolUse(Bash): clear the dirty marker when a Gradle build/compile/test/check command ran.
input=$(cat)
script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
root=$(CDPATH= cd -- "$script_dir/../.." && pwd)
marker="$root/.codex/.kt-dirty"

cmd=$(printf '%s' "$input" | python3 -c '
import json
import sys

try:
    payload = json.load(sys.stdin)
except Exception:
    print("")
    raise SystemExit(0)

tool_input = payload.get("tool_input") or {}
print(tool_input.get("command") or tool_input.get("cmd") or "")
' 2>/dev/null)

case "$cmd" in
  *gradlew*assemble*|*gradlew*compile*|*gradlew*test*|*gradlew*build*|*gradlew*check*)
    rm -f "$marker"
    ;;
esac

exit 0
