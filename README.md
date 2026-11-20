# Android Template

### Project structure
Build configuration lives in the `build-logic` included build as convention plugins (`baseproject.*`), applied by each module through `plugins { id("baseproject.…") }`. Modules depend on each other through type-safe project accessors (`implementation(projects.domain.model)`).

![Convention plugins and module dependencies](docs/project-graph.png)

### Check dependencies
```bash
./gradlew dependencyUpdates --no-parallel
```
The versions plugin supports neither parallel execution nor the configuration cache, which the task opts out of.

### Run static analysis (Detekt)
```bash
./gradlew detekt
```
Add `-PdetektAutoCorrect=true` to let the ktlint ruleset fix issues in place. Shared rules live in `config/detekt/detekt.yml`.

### CI
Every push/PR to `master` runs `.github/workflows/ci.yml` on JDK 25: Detekt, unit tests, and a `dev` debug assemble.
To reproduce the same checks locally (Gradle provisions JDK 25 for the daemon and the toolchain if it is not installed, see `gradle/gradle-daemon-jvm.properties`):
```bash
./gradlew detekt testDevDebugUnitTest test assembleDevDebug --continue
```