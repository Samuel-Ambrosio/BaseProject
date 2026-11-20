---
paths:
  - "presentation/features/**/*.kt"
---

When working in feature modules (`presentation:features:*`):

## Structure of a feature module

- `build.gradle.kts` with `id("convention.android.feature")` (it already brings `domain:model`, `domain:useCase`, `presentation:analytics` and `presentation:common`) and its `namespace` in `Namespaces.Presentation.Features` (`build-logic/.../Namespaces.kt`).
- `di/Feature{X}Module.kt` with `val feature{X}Module = module { viewModel { ... } }`, added to `appComponent` (`app/.../di/AppComponent.kt`).
- `ui/`: screens, ViewModels and `{X}Navigation.kt` with the `EntryProviderScope<NavKey>.{x}NavEntries(...)` extension that registers its destinations (see `navigation.md`).
- The module is registered in `settings.gradle.kts` and added as a dependency in `app/build.gradle.kts`.

## `Result<F, S>` at the UseCase boundary — not to be confused with the presentation `State`

`domain/model/.../utils/Result.kt` defines the wrapper returned by repositories and UseCases:

```kotlin
sealed class Result<out F, out S> {
    data object Loading : Result<Nothing, Nothing>()
    data class Success<S>(val data: S) : Result<Nothing, S>()
    data class Failure<F>(val error: F) : Result<F, Nothing>()
}
```

with helpers (`fold`, `mapSuccess`, `ifSuccess`, `successOrNull`, `failureOrNull`, `asSuccess`, `asFailure`). **There is no shared generic `UiState`**: each ViewModel collapses the `Result` it receives into its own `State`. There are two valid shapes; pick based on the case:

**1. `data class` with `isLoading`/`error`**: when loading/error/data coexist as flags over the same shape:

```kotlin
internal data class MainState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val todos: List<Todo> = emptyList(),
)
```

**2. `sealed interface` with `Loading`/`Ready`/`Failure`**: when each case needs its own, mutually exclusive data:

```kotlin
internal sealed interface DetailState {
    data object Loading : DetailState
    data class Ready(val item: Item) : DetailState
    data class Failure(val error: Error) : DetailState
}
```

Do not introduce a shared `UiState` that tries to unify both shapes without a real consumer justifying it (premature abstraction).

## Mandatory loading/success/error handling

Every screen that consumes a loading `State` **MUST** handle all 3 cases: never leave the error silent or the loading without feedback.

**Full-screen retry** (when the screen's main load fails): a full-screen error state with the message (`error.handleErrorMessage(resources)`) and a retry button (`AppButton`) whose `onClick` points to a real ViewModel method that invokes the UseCase again (`viewModel::retry`), never to an empty callback. If it repeats across screens, extract it into a component in `presentation:common` instead of duplicating it.

With the `sealed interface` shape (the usual one with `ColdStateBaseViewModel`, where `retry` is `retryInitState()`; see `viewmodel.md`), the equivalent is an exhaustive `when` over the `State`, one composable per case:

```kotlin
when (state) {
    is DetailState.Failure -> ErrorContent(error = state.error, onRetryClick = viewModel::retry)
    is DetailState.Loading -> LoadingContent()
    is DetailState.Ready -> DetailReadyContent(state = state, /* ... */)
}
```

**Snackbar** (when a one-off action on already-loaded content fails: delete, save; not the initial load): triggered from a ViewModel `Command` and shown with the app's `SnackbarHostState`. **It is not injected through Koin**: it is a single instance in `AppState.snackbarHostState` (`rememberAppState()`), passed down as a parameter from `*NavEntries` and received in the screen as `appSnackbarHostState`:

```kotlin
CommandHandler(viewModel) { command ->
    when (command) {
        is MainCommand.Error ->
            appSnackbarHostState.showSnackbar(message = command.error.handleErrorMessage(resources = resources))
    }
}
```

If a `Command` triggers navigation and also a snackbar that must outlive the screen that launched it, launch it in `AppState.appScope` (app lifetime), not in a scope of the screen itself, which may disappear halfway.

**How to choose**: if the failure prevents showing the main content → full-screen retry (`state.error`/`Failure`). If the content is already loaded and a secondary action fails → snackbar (`Command`). Do not mix both for the same kind of failure.

**Careful with the sample screen**: `MainScreen`/`MainViewModel` show the initial load error with a snackbar and do not paint `isLoading`. It is a minimal wiring sample, not the pattern to follow for a real screen.
