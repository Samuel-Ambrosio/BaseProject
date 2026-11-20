---
paths:
  - "**/*Analytics*.kt"
  - "presentation/analytics/**/*.kt"
---

When working with analytics or tracking:

## Contract and event catalog

Everything lives in `presentation:analytics`:

- `ScreenAnalytics` (`base/`): the injected contract, `notifyAction(action: AnalyticsAction?)`. Its implementation (`ScreenAnalyticsImpl` → `CoreFirebaseAnalytics`) is `internal` and adds the common device parameters to every event (date, OS version, carrier, language, time zone).
- `AnalyticsAction(eventName, parameters)` and `AnalyticsEventParameter(name, value)` (`base/`): the base types of an event.
- Catalog per screen/feature in `ui/`: a `sealed class {Screen}ScreenAnalytics` with one object per event. Real example, `AdvertScreenAnalytics.kt`:

```kotlin
sealed class AdvertScreenAnalytics {
    object Click : AnalyticsAction(eventName = "advert_click")
}
```

An event with context declares it as its own parameter:

```kotlin
data class Delete(val itemName: String) : AnalyticsAction(
    eventName = "favourites_item_delete",
    parameters = listOf(AnalyticsEventParameter("item_name", itemName)),
)
```

Firebase is only imported in `presentation:analytics` (and in `app` for Crashlytics): features depend on `ScreenAnalytics`, never on `FirebaseAnalytics`.

## Event naming

snake_case format `{feature}_{subarea}_{action}` (`advert_click`, `favourites_item_delete`). The screen/feature context goes in the event name itself, not as a separate parameter; `CoreFirebaseAnalytics` already adds the common device parameters. Respect Firebase limits: name up to 40 characters, letters, digits and `_`, starting with a letter.

## Where to track

- **User actions**: inside the click-handler lambda passed to the child Composable, next to the real navigation/action it triggers, never in the ViewModel:

  ```kotlin
  val analytics: ScreenAnalytics = koinInject()
  // ...
  onItemClick = { item ->
      analytics.notifyAction(FavouritesScreenAnalytics.Detail)
      navigateToDetail(item.id)
  }
  ```

- **Screen views**: there is no helper today. If needed, fire it once inside a `LaunchedEffect(Unit)` at the screen's entry point; if it repeats across screens, extract it into a helper in `presentation:analytics` instead of copying it.
- **NEVER** in the ViewModel's `init`/constructor nor loose in a `@Composable` body without wrapping it in `LaunchedEffect`: it would fire again on every recomposition.
