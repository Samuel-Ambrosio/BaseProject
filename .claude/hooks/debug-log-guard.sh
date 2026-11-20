#!/bin/bash
# .claude/hooks/debug-log-guard.sh
# PreToolUse hook: blocks println() and raw android.util.Log.* calls in Kotlin
# files. Use AppLogger instead. Ref: .claude/rules/logging.md
#
# The logging backend itself (CrashReportingTree, TimberLogger) is excluded by
# path so maintaining it doesn't trip this guard.

INPUT=$(cat)

FILE_PATH=$(echo "$INPUT" | jq -r '.tool_input.file_path // .tool_input.path // ""')

# Only check Kotlin files
if [[ "$FILE_PATH" != *.kt ]]; then
    exit 0
fi

# Logging backend -- not a precedent to extend
case "$FILE_PATH" in
    *CrashReportingTree.kt|*TimberLogger.kt)
        exit 0 ;;
esac

CONTENT=$(echo "$INPUT" | jq -r '.tool_input.content // .tool_input.new_string // ""')

if echo "$CONTENT" | grep -qE '(^|[^A-Za-z0-9_.])println[[:space:]]*\('; then
    echo '{"continue": false, "stopReason": "❌ println() is forbidden in Kotlin.\n\nUse AppLogger: AppLogger.d(...), .i(...), .w(...), .e(...).\nprintln() bypasses the logging backend and the startup buffer.\nSee .claude/rules/logging.md"}'
    exit 0
fi

if echo "$CONTENT" | grep -qE '(^|[^A-Za-z0-9_.])Log\.(v|d|i|w|e)[[:space:]]*\('; then
    echo '{"continue": false, "stopReason": "❌ android.util.Log.* is forbidden in Kotlin outside the logging backend.\n\nUse AppLogger: AppLogger.d(...), .i(...), .w(...), .e(...).\nSee .claude/rules/logging.md"}'
    exit 0
fi

exit 0
