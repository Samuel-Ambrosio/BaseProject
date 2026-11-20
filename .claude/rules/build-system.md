---
paths:
  - "build-logic/**"
  - "gradle/libs.versions.toml"
  - "settings.gradle.kts"
  - "build.gradle.kts"
  - "**/build.gradle.kts"
  - "gradle.properties"
  - "**/proguard-rules.pro"
  - "**/META-INF/com.android.tools/r8/*.pro"
---

## Library versions — `gradle/libs.versions.toml`, never hardcoded

All versions (dependencies, plugins and SDK: `compileSdk`, `targetSdk`, `minSdk`, `jvmTarget`) are declared in `[versions]` of `gradle/libs.versions.toml` and referenced through `libs.xxx` in `build.gradle.kts` files, or through `libs.library("…")`/`libs.intVersion("…")` (`build-logic/.../VersionCatalog.kt`) in convention plugins. Never a literal version in a `build.gradle.kts`.

Plugins are declared once in the root `build.gradle.kts` with `apply false` (same classloader for all projects); `build-logic` only compiles against them (`compileOnly`).

To look for updates: `./gradlew dependencyUpdates --no-parallel`.

## Convention plugins (`build-logic/src/main/kotlin/`)

Chain of precompiled plugins (`plugins { id("convention.…") }`):

```
convention.detekt              (detekt + ktlint, config/detekt/detekt.yml)
   ▲
   ├── convention.kotlin.library        (pure Kotlin: domain:*, data:sources, data:repositoryImpl;
   │                                     coroutines + Koin, jvmToolchain)
   └── convention.android.base          (compileSdk/minSdk, desugaring, coroutines, Koin, JUnit/Espresso)
          ▲
          ├── convention.android.library          (+ com.android.library, serialization)
          │      ▲
          │      └── convention.android.compose.library   (+ convention.android.compose)
          │             ▲
          │             └── convention.android.feature    (+ domain:model, domain:useCase,
          │                                                 presentation:analytics, presentation:common)
          └── convention.android.application      (+ com.android.application, compose, flavors,
                                                    targetSdk, R8 in release)

convention.android.compose     (Compose BOM, Material 3, lifecycle, Navigation 3, koin-compose)
convention.android.flavors     (dev/pre/pro flavors with API_BASE_PATH; only app and data:sourcesImpl:remote)
```

- When creating a new module, apply the most specific plugin possible (`convention.android.feature` for a feature, not bare `convention.android.library`): it already brings the base dependencies.
- Android module namespaces go in `Namespaces.kt` (`build-logic/.../Namespaces.kt`), not as a string in the module.
- Inter-module dependencies use type-safe accessors (`implementation(projects.domain.model)`), not `project(":…")`, except inside `build-logic`, where they do not exist.
- A new module is registered in `include(...)` of `settings.gradle.kts`.
- Configuration that only one module needs goes in its `build.gradle.kts`, not in a convention plugin.

## Flavors and `BuildConfig`

`convention.android.flavors` defines the `environment` dimension (`dev`/`pre`/`pro`) with `API_BASE_PATH` and enables `buildConfig`. Only `app` and `data:sourcesImpl:remote` apply it: the rest of the libraries have no flavors and Gradle matches them anyway. Do not apply it to a module that does not read a per-environment value.

Never put a value that changes between builds (date, hash, counter) in a `buildConfigField`: it changes the module's ABI and forces recompiling every module that depends on it. Use a resource (`resValue`). The app identity (`app.name`, `app.applicationId`) is read from `gradle.properties`.

## Proguard/R8 — each rule in the module that needs it

The `app` release build minifies with R8 (`isMinifyEnabled = true` in `convention.android.application`). In full mode, a member-less `-keep class X` no longer keeps the no-arg constructor, and anything used only through reflection is removed unless something keeps it.

| What the rule needs | Where it goes |
| --- | --- |
| Code of one of our Android modules (`convention.android.library`) | `consumerProguardFiles("consumer-rules.pro")` in that module |
| Code of one of our JVM modules (`convention.kotlin.library`) | `src/main/resources/META-INF/com.android.tools/r8/<name>.pro` (ships inside the jar and R8 reads it as a consumer rule) |
| A third-party library whose rules are not enough, or something specific to the app | `app/proguard-rules.pro` |

Do not copy rules the library already ships in its jar (`META-INF/proguard/` or `META-INF/com.android.tools/`): check before adding one. To see which rules really apply: `app/build/outputs/mapping/proRelease/configuration.txt`. A green `assembleProRelease` does not prove the app starts: test the minified release on an emulator (see `engineering-principles.md`, "A green build does not guarantee correct runtime").

## Repositories — only in `settings.gradle.kts`

All repositories are declared in `dependencyResolutionManagement` of `settings.gradle.kts`, with `RepositoriesMode.FAIL_ON_PROJECT_REPOS`: a `repositories { }` block in a `build.gradle.kts` breaks the build. `google()` is only queried for `androidx.*`, `com.android.*` and `com.google.*` (`content { includeGroupByRegex(...) }`). A new third-party repository goes in an `exclusiveContent` block with the groups it serves, so that repo cannot serve another group and that group cannot come from another repo (dependency confusion).

## JVM — JDK 25

- The JDK that runs Gradle is pinned by `gradle/gradle-daemon-jvm.properties` for the IDE, the terminal and CI, over `JAVA_HOME`. Regenerate it with `./gradlew updateDaemonJvm --jvm-version=N` (that is why `settings.gradle.kts` applies `foojay-resolver-convention`).
- The toolchain and bytecode target come from `jvmTarget` in `libs.versions.toml`, applied in `convention.kotlin.library` and `convention.android.base`.
- When upgrading the JDK, change `jvmTarget`, `gradle-daemon-jvm.properties` and the `java-version` entries in `.github/workflows/ci.yml` together.
