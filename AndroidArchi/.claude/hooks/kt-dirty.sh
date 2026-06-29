#!/bin/bash
# PostToolUse(Edit|Write): Kotlin/Gradle 소스가 수정되면 "빌드 미검증" 마커를 남긴다.
# stop-check.sh 가 이 마커를 보고 턴 종료를 막는다. build-clear.sh 가 gradle 실행 시 해제.
input=$(cat)
fp=$(printf '%s' "$input" | python3 -c "import sys,json; print(json.load(sys.stdin).get('tool_input',{}).get('file_path',''))" 2>/dev/null)
case "$fp" in
  *.kt|*.kts)
    touch "${CLAUDE_PROJECT_DIR:-.}/.claude/.kt-dirty"
    ;;
esac
exit 0
