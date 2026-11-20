---
paths:
  - "**/*Navigation*.kt"
  - "**/*NavKey*.kt"
  - "**/*NavEntries*.kt"
  - "**/*NavHost*.kt"
  - "**/*NavDisplay*.kt"
---

## Navigation 3 across the whole app

The app uses `androidx.navigation3`. Do not introduce Navigation2 (`androidx.navigation.compose`, safeargs).

- **Back stack**: `AppState.navBackStack` (`NavBackStack<NavKey>`, `rememberNavBackStack(AppNavKey.Home.Main)`), created in `rememberAppState()`.
- **Host**: `AppNavHost` (`app/.../ui/AppNavHost.kt`) mounts an `AppNavDisplay` (`presentation/common/.../app/navigation/AppNavDisplay.kt`) over that back stack, with the saved-state and per-entry `ViewModelStore` decorators.
- **Destinations**: `AppNavKey` (`presentation/common/.../app/navigation/AppNavKey.kt`), `sealed interface AppNavKey : NavKey`, grouped by section (`Home`, `Search`, `More`), each destination a `@Serializable` `data object`/`data class`.
- **Registration**: each feature registers its destinations in an `EntryProviderScope<NavKey>.{x}NavEntries(...)` extension (`homeNavEntries` in `HomeNavigation.kt`) with `appEntry<AppNavKey.…> { … }`, and `AppNavHost` calls it.
- **Bottom bar**: `NavItem` + `AppConfiguration.navItems`.

## Back stack extensions

`Navigation3Extensions.kt` (`presentation/common/.../extensions/`) holds `appEntry` and the operations on `NavBackStack<NavKey>`: `safeAdd`, `safeRemoveLast`, `removeLastOrNavigateUp`, `clearAndAdd`, `removeLastAndAdd`, `removeLastInstancesOf`, `removeUntilInstanceAndReplaceIt`. Always use these extensions to move the stack; if an operation is missing, add it there (with its test) instead of manipulating the list by hand at the call site. The stack is always a `NavBackStack` (a `StateObject`), never a plain `MutableList`: `NavDisplay` would not recompose.

Screens do not receive the back stack: they receive callbacks (`navigateUp`, `navigateToX`), and `*NavEntries` builds them with these extensions (see `viewmodel.md` and `compose-views.md`).

## Rules

- **Screen identity**: Navigation 3 identifies each entry by its key (`contentKey`, class + `toString()` by default in `appEntry`). Two equal keys share ViewModel and saved state, and pushing them again does **not** create a new screen. That is why `safeAdd` does not push a key that is already on top. Do not assume a screen's `remember` or ViewModel is recreated when navigating again with the same arguments.
- **Per-destination transitions**: `metadata = { NavDisplay.transitionSpec { AppAnimations.slideHorizontally } + NavDisplay.popTransitionSpec { AppAnimations.popSlideHorizontally } }` in the `appEntry`. Without metadata, `AppNavDisplay`'s default fade.
- **Arguments**: always as a typed property of the key, `@Serializable` end to end (the back stack is saved in the Activity state and restored after recreation or process death; `@Transient` for computed properties). Never a separate argument store (a `String → Any?` map, a shared ViewModel to pass data).
- **What does not go in a key**: large objects or objects that come from the backend (a whole entity, a list of results): pass an id and let the screen load it, with a `Loading`/`Ready`/`Failure` state and real retry, also when the data was in an in-memory cache that does not survive process death. Credentials: never, neither as an argument nor inside another type; they live in memory, in the flow's ViewModel.
- **Single-use result** the origin screen must show (a snackbar): launched at the origin with `AppState.appScope`.

## Nested flows: when to keep them and when to flatten

A multi-screen flow in a feature can be a nested `AppNavDisplay` with its own private `sealed interface XxxNavKey : NavKey` hierarchy and `rememberNavBackStack`. Nesting loses nothing compared to the global back stack:

- **Predictive back**: `NavDisplay` only captures back when its stack has screens behind; at the flow's root, the global `AppNavDisplay` picks it up and animates the whole flow. A screen's own `BackHandler` takes priority over both and loses the gesture animation, same as with a flattened flow.
- **State restoration**: the inner back stack is saved in the outer entry's state, and the inner ViewModels hang off its `ViewModelStore`: they are destroyed when the flow closes.

**Keep nested** a flow that shares state between its screens (Navigation 3 has no shared parent scope between entries of the global back stack) or that restarts as a whole. **Flatten** an inner screen (its key moves to `AppNavKey`) only when it has to be a direct destination of the global back stack: another feature navigates to it or it shows the bottom bar. Flattening by default moves the feature's private keys into `presentation:common` without a consumer asking for it.
