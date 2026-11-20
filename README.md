# Android Template

## Creating a new app from this template
Work through this list top to bottom. Everything marked *optional* can stay as it is.

### 1. Identity (required)
- [ ] `gradle.properties` → `app.name` (launcher label; the Gradle root project uses it without spaces) and `app.applicationId` (Play Store id).
- [ ] `app/build.gradle.kts` → `versionCode` / `versionName`.
- [ ] *Optional* — package prefix `com.samuelav`: it is the author's namespace, not the app's, so it does not need to change. To change it, rename the source folders of every module, `build-logic/.../Namespaces.kt`, the `com.samuelav.buildlogic` package and the rules in `app/proguard-rules.pro`.

### 2. Branding (required)
- [ ] Launcher icon: `presentation/common/src/main/res/mipmap-*/ic_launcher*` and `drawable(-night)/ic_launcher_{foreground,background}.xml` (Android Studio → *New → Image Asset*). The foreground drawable is also the splash icon (`app/src/main/res/values/themes.xml`).
- [ ] Colors and theme: `presentation/common/src/main/res/values/colors.xml`, `themes.xml` and `ui/theme/*.kt`.
- [ ] Strings in `presentation/common/src/main/res/values/strings.xml` (and `values-es/`): `app_email_contact` (used by the "contact" mail action), `app_share_link` (Play Store link), `developed_by`.

### 3. Backend (required)
- [ ] `API_BASE_PATH` per environment (`dev` / `pre` / `pro`) in `build-logic/src/main/kotlin/convention.android.flavors.gradle.kts`.
- [ ] Replace the sample *Todo* feature (`jsonplaceholder`): `TodoService`, `NetworkTodo`, `TodoRemoteDataSource(Impl)`, `TodoRepository(Impl)`, `GetAllTodosUseCase`, `Todo` and the home screen that uses it.
- [ ] Remove error strings left over from a previous app: `result_error_balance`, `result_error_exchanges`, `result_error_market_prices`.

### 4. Firebase — Analytics + Crashlytics (required before release)
The Firebase SDKs are already dependencies; until this is done Analytics/Crashlytics calls will fail at runtime.
- [ ] Create the Firebase project, register `app.applicationId` and copy `google-services.json` into `app/`.
- [ ] `app/build.gradle.kts` → uncomment `alias(libs.plugins.googleServices)` and `alias(libs.plugins.crashlytics)`.
- [ ] `app/src/main/kotlin/com/samuelav/app/App.kt` → uncomment `FirebaseApp.initializeApp`, the `CrashReportingTree` and `Timber.plant`.

### 5. Ads — AdMob (optional)
- [ ] Create the AdMob app and a banner ad unit.
- [ ] `app/src/main/AndroidManifest.xml` → uncomment the `com.google.android.gms.ads.APPLICATION_ID` meta-data and set the AdMob app id.
- [ ] `strings.xml` → `ad_id_banner` with the banner ad unit id.
- [ ] `app/build.gradle.kts` → uncomment `implementation(libs.google.services.ads)`, and add it to `presentation/features/advert/build.gradle.kts` too.
- [ ] `presentation/features/advert/.../AdvertBanner.kt` → uncomment the `AdView` block (and its imports). Not using ads: delete the `:presentation:features:advert` module instead.

### 6. Local storage (optional)
- [ ] Room: uncomment `data/sourcesImpl/local/.../AppRoomDatabase.kt` and the provider in `di/LocalModule.kt`, then add entities and DAOs.
- [ ] DataStore file name: `"data-store"` in `di/LocalModule.kt`.

### 7. Release (required before publishing)
- [ ] Add a `signingConfig` for `release` (keystore kept out of git, e.g. read from `local.properties` or CI secrets).
- [ ] Review `app/proguard-rules.pro` with a minified `assembleProRelease` build.
- [ ] CI runs on `master` (`.github/workflows/ci.yml`); change the branch if yours differs.


### Project structure
Build configuration lives in the `build-logic` included build as convention plugins (`convention.*`), applied by each module through `plugins { id("convention.…") }`. Modules depend on each other through type-safe project accessors (`implementation(projects.domain.model)`).

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