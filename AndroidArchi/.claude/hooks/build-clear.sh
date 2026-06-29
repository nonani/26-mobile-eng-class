#!/bin/bash
# PostToolUse(Bash): gradle 빌드/컴파일/테스트가 실행되면 빌드 미검증 마커를 해제한다.
# (실패한 빌드여도 해제 — 실패를 고치러 다시 편집하는 순간 kt-dirty.sh 가 마커를 다시 세운다)
input=$(cat)
cmd=$(printf '%s' "$input" | python3 -c "import sys,json; print(json.load(sys.stdin).get('tool_input',{}).get('command',''))" 2>/dev/null)
case "$cmd" in
  *gradlew*assemble*|*gradlew*compile*|*gradlew*test*|*gradlew*build*|*gradlew*check*)
    rm -f "${CLAUDE_PROJECT_DIR:-.}/.claude/.kt-dirty"
    ;;
esac
exit 0
