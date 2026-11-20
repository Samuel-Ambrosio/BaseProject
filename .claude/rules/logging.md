---
paths:
  - "**/*.kt"
---

# Logging

The project uses `AppLogger` (`domain/model/.../utils/debug/AppLogger.kt`) as the single logging entry point. **Never use `println(...)` or `android.util.Log.*` directly in application code.**

## API

```kotlin
import com.samuelav.domain.model.utils.debug.AppLogger

AppLogger.v("verbose message")
AppLogger.d("debug message")
AppLogger.i("info message")
AppLogger.w("warning message")
AppLogger.e("error message")

// With an associated throwable
AppLogger.e(throwable, "additional context")
```

`AppLogger` buffers messages until a backend is assigned (`AppLogger.logger = TimberLogger` in `App.onCreate()`), so it is safe to call from any module, including `domain:*` (pure Kotlin), before the `Application` finishes initializing.

The backend is `TimberLogger` (`app/.../utils/debug/TimberLogger.kt`), which forwards to Timber; in release, `CrashReportingTree` sends logs to Crashlytics (enabled when Firebase is configured, see the README).

## Mandatory rules

- **FORBIDDEN**: `println(...)`.
- **FORBIDDEN**: `android.util.Log.*`, except inside the logging backend itself (`CrashReportingTree`, which only uses the `Log.ERROR` constant). If you need new logging, use `AppLogger`.
- **FORBIDDEN**: leaving temporary investigation `.d`/`.v` logs in a final PR: remove them before the final commit. To find them easily, give them a recognizable prefix (`AppLogger.d("[DEBUG] ...")`).

The `.claude/hooks/debug-log-guard.sh` hook blocks `println(` and `Log.x(` when writing Kotlin files.

## Why

`println()`/`Log.*` bypass the configured backend (Timber → console/Crashlytics) and the startup buffer: a log emitted before `App.onCreate()` configures the backend is lost, whereas with `AppLogger` it is buffered and forwarded as soon as the backend is ready.
