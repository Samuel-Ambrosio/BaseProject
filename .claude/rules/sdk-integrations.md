---
paths:
  - "presentation/analytics/**/*.kt"
  - "presentation/features/advert/**/*.kt"
  - "data/sourcesImpl/**/*.kt"
  - "app/src/main/**/*.kt"
---

When working with third-party SDK integrations (Firebase, AdMob, Retrofit/OkHttp, Room, DataStore):

## The boundary: never in `domain:*` or `data:repositoryImpl`

No third-party SDK import in `domain/**`, `data/sources/**` or `data/repositoryImpl/**`: they are pure Kotlin and only know contracts and domain models. Each SDK lives in the module that matches its layer:

| SDK | Where it lives | Why |
|---|---|---|
| Retrofit, OkHttp, Moshi | `data:sourcesImpl:remote` | Implements the `*RemoteDataSource` contracts from `data:sources` |
| Room, DataStore | `data:sourcesImpl:local` | Implements the `*LocalDataSource` contracts |
| Firebase Analytics | `presentation:analytics` | Behind the `ScreenAnalytics` contract |
| Firebase Crashlytics, Timber | `app` (composition root) | Cross-cutting startup infrastructure (`App.onCreate`), behind `Logger`/`AppLogger` |
| Google Mobile Ads | `presentation:features:advert` (+ `app` for initialization) | Isolated UI component; the whole module can be deleted |

Do not create a new module per SDK as a rule: if the product has its own data contract it goes in the matching `data:sourcesImpl:*`; if it is startup infrastructure it goes in `app`; if it is isolated UI, in its feature.

## Naming

1. **`{Contract}Impl`**: implementation of one of our contracts (`TodoRemoteDataSourceImpl`, `ScreenAnalyticsImpl`).
2. **`{Sdk}{Thing}`**: adapter from an SDK to one of our abstractions, without the `Impl` suffix (`TimberLogger : Logger`, `CoreFirebaseAnalytics`).

## DI and activation

Each integration exposes its `val {x}Module = module { ... }` in its Gradle module and is activated in `appComponent`. The pieces that need external configuration (Firebase with `google-services.json`, AdMob with its app id) come commented out with a `TODO`; the steps to enable them are in the README. When adding a new SDK that needs keys, follow the same pattern: disabled by default and documented in the README.

**NEVER** hardcode keys or secrets in Kotlin: they go in `local.properties`/CI secrets and arrive through `buildConfigField` or resources.
