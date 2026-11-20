---
paths:
  - ".github/workflows/*.yml"
  - "config/detekt/**"
---

When working with GitHub Actions workflows or the static analysis configuration:

## Current workflow — `ci.yml`

Runs on every `push` and PR to `master`, with `concurrency` cancelling previous runs of the same branch. Three parallel jobs on `ubuntu-latest`, Temurin 25 and `actions/setup-java`'s `cache: gradle`:

| Job | Command | Artifact |
|---|---|---|
| Detekt | `./gradlew detekt --continue` | `detekt-reports` (HTML) |
| Unit tests | `./gradlew testDevDebugUnitTest test --continue` | `test-reports` |
| Assemble debug | `./gradlew assembleDevDebug` | — |

The jobs that build Android only install `platform-tools` with `android-actions/setup-android`: Gradle downloads the platform it needs.

To reproduce it locally (same command as CI, in a single Gradle invocation):

```bash
./gradlew detekt testDevDebugUnitTest test assembleDevDebug --continue
```

If you change the CI commands, also update this line and the README's "Continuous integration" section: there cannot be two different sources of truth.

## Rules

- **JDK**: the workflows' `java-version` = `jvmTarget` in `libs.versions.toml` = `gradle/gradle-daemon-jvm.properties`. They change together.
- **Action versions**: pin the major version (`actions/checkout@v7`) and update them all at once.
- **NEVER hardcode secrets** in a workflow: always `${{ secrets.XXX }}`, passed through `env:` and not as command-line arguments.
- **Release signing**: does not exist yet. When added, the keystore and its passwords go in GitHub secrets, are decoded on the runner and never enter the repo.
- **Detekt**: shared rules live in `config/detekt/detekt.yml` (on top of the default config, `buildUponDefaultConfig`). Fix the code before relaxing a rule; if a rule is disabled, do it in the YAML with its reason, not with scattered `@Suppress`. `-PdetektAutoCorrect=true` lets the ktlint ruleset fix formatting.
