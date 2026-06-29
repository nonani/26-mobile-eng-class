#!/bin/bash
# Stop: Kotlin 수정 후 gradle 검증 없이 턴을 끝내려 하면 차단하고 빌드 검증을 요구한다.
# stop_hook_active=true(이미 본 훅 때문에 재개된 턴)면 무한 루프 방지를 위해 통과시킨다.
input=$(cat)
active=$(printf '%s' "$input" | python3 -c "import sys,json; print(json.load(sys.stdin).get('stop_hook_active', False))" 2>/dev/null)
marker="${CLAUDE_PROJECT_DIR:-.}/.claude/.kt-dirty"
if [ "$active" != "True" ] && [ -f "$marker" ]; then
  echo "Kotlin/Gradle files were modified but no gradle build ran afterwards. Run the gradle-build-check skill (e.g. ./gradlew :app:assembleDebug) before finishing. If a build is genuinely unnecessary (comment-only change), delete .claude/.kt-dirty and finish." >&2
  exit 2
fi
exit 0
