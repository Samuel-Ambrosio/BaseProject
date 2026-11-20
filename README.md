# Android Template

### Check dependencies
```bash
./gradlew dependencyUpdates
```

### Generate module dependency graph
```bash
./gradlew generateDependencyGraphModules
```

### Generate dependencies graph
```bash
./gradlew generateDependencyGraph
```

### Run static analysis (Detekt)
```bash
./gradlew detekt
```
Add `-PdetektAutoCorrect=true` to let the ktlint ruleset fix issues in place. Shared rules live in `config/detekt/detekt.yml`.

### CI
Every push/PR to `master` runs `.github/workflows/ci.yml` on JDK 25: Detekt, unit tests, and a `dev` debug assemble.
To reproduce the same checks locally (requires JDK 25, e.g. Android Studio's bundled JBR):
```bash
./gradlew detekt testDevDebugUnitTest test assembleDevDebug --continue
```