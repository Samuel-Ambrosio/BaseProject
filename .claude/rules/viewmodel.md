---
paths:
  - "**/*ViewModel*.kt"
---

When working with ViewModels:

## Base classes

Two base classes in `presentation:common` (`presentation/common/.../ui/base/`), the only justified inheritance in the project (see `engineering-principles.md`). Pick one depending on the kind of load:

- **`BaseViewModel<State, Command>`** (`BaseViewModel.kt`): "hot" state, a `MutableStateFlow` mutated explicitly with `emitState(newState)` or `updateState { old -> old.copy(...) }`.
- **`ColdStateBaseViewModel<State, Command>`** (`ColdStateBaseViewModel.kt`): "cold" state, computed in `abstract suspend fun FlowCollector<State>.onInitState()` over a retry flow with `flatMapLatest` + `stateIn(WhileSubscribed(5_000))`. Retrying is `retryInitState()`, which cancels the load in progress and runs `onInitState()` again.

Both expose the same contract:

- `state: StateFlow<State>`: the screen's persistent state.
- `command: SharedFlow<Command>` (through the `CommandSource` interface): one-shot events (navigation, snackbars), emitted with `emitCommand(...)` and consumed in the screen with `CommandHandler(viewModel) { command -> ... }` (a `LaunchedEffect(viewModel)` inside).
- `ioCoroutineScope`: `viewModelScope` on the IO dispatcher, for UseCase calls. Both base classes take the dispatcher through their constructor (`ioDispatcher`, `Dispatchers.IO` by default) and a concrete ViewModel exposes the same parameter, so tests can pass a `StandardTestDispatcher`.

### When to use each one

**A screen that only fetches a resource and lets the user retry → `ColdStateBaseViewModel`**: a detail or a list with `Loading`/`Ready`/`Failure`. Loading starts when the UI starts collecting `state`, not when the ViewModel is built, pauses 5 s after the UI stops collecting it, and retry is built in. This avoids premature loads and an `init { load() }` paired with a `retry()` that relaunches it by hand.

```kotlin
internal class DetailViewModel(
    private val itemId: ItemId,
    private val getItemUseCase: GetItemUseCase,
) : ColdStateBaseViewModel<DetailState, DetailCommand>(initialState = DetailState.Loading) {

    override suspend fun FlowCollector<DetailState>.onInitState() {
        emit(DetailState.Loading)
        getItemUseCase(itemId).fold(
            isSuccess = { item -> emit(DetailState.Ready(item = item)) },
            isFailure = { error -> emit(DetailState.Failure(error = error)) },
        )
    }

    fun retry() = retryInitState()
}
```

**`BaseViewModel`** remains the choice when the state is mutated by several independent user actions (a form, a paginated list, delete/save on already-loaded content). If a screen needs both, the cold state is not mutated from outside: model the change as an input of `onInitState()` (another combined `Flow`) or use `BaseViewModel`.

Do not add a third base class without a real consumer that justifies it; do not hand-reimplement in a concrete ViewModel what one of the two already provides either.

- **The text of an editable field never goes through `state`.** A `TextField` value has to update synchronously. If it arrives through the cold state (`flatMapLatest` → `stateIn` on the IO dispatcher), Compose writes stale values into the field and, when typing fast, characters get lost and reordered. That text lives in the screen (`var query by rememberSaveable { mutableStateOf("") }`); the ViewModel only loads the resource, and filtering is a function of the state (`Ready.filteredItems(query)`).
- **In tests**, the cold state needs a collector: pass a test `ioDispatcher` and launch `backgroundScope.launch { sut.state.collect() }` before `advanceUntilIdle()`.

## No `enum Screen` — use `State` + sealed `Command`

- **`State`** (`data class`, or a `sealed interface` with `Loading`/`Ready`/`Failure` when each case has its own, mutually exclusive data; see `feature-modules.md`): what is painted right now.
- **`Command`** (`sealed interface`): which one-shot action to trigger (navigate, show a snackbar). Example:

```kotlin
internal sealed interface DetailCommand {
    data class DeleteFailed(val error: Error) : DetailCommand
    data object NavigateUp : DetailCommand
}
```

A ViewModel with no one-shot actions declares `Nothing` as its `Command` type (`MainViewModel : ColdStateBaseViewModel<MainState, Nothing>`) instead of an empty `sealed interface`.

`State` and `Command` are declared in the ViewModel file, below the class, `internal`. With `ColdStateBaseViewModel`, `State` is usually the `sealed interface` form (`Loading`/`Ready`/`Failure`).

## ViewModel conventions

- **Naming**: `{Feature}ViewModel`, PascalCase, ViewModel suffix, `internal` visibility (not exposed outside the feature module).
- **Constructor injection with Koin**: UseCases as constructor parameters resolved with `get()` in the feature's Koin module (`viewModel { MainViewModel(getAllTodosUseCase = get()) }`), never resolved inside a method. A ViewModel does not depend on a Repository directly: it goes through a UseCase.
- **Obtaining it in Compose**: `val viewModel: FeatureViewModel = koinViewModel()` in the feature's entry Composable.
- **No Android dependencies**: no `Context`, `Resources` or `stringResource` in the ViewModel. Emit an `Error` or a `Command` and let the screen resolve the text (`Error.handleErrorMessage(resources)`).

## Navigation callbacks — ALWAYS non-null, ALWAYS required

Every navigation callback (`navigateToX: () -> Unit`, `navigateUp: () -> Unit`) is declared **non-null and without a default**, as a parameter of the screen Composable, never `(() -> Unit)?`.

```kotlin
// ✅ Correct
@Composable
internal fun MoreScreen(
    navigateUp: () -> Unit,
    navigateToSettings: () -> Unit,
)

// ❌ Wrong
navigateToX: (() -> Unit)? = null
navigateToX?.invoke()  // does not navigate, does not crash, nobody notices
```

Why: an optional navigation callback set to `null` is a silent broken link: if there is a visible button that triggers it, the callback MUST exist.

The **order** of these callbacks in the signature is in `compose-views.md` → "Order of navigation callbacks".

**Do not confuse them with legitimate optional UI lambdas**: `onSecondaryButtonClick: (() -> Unit)? = null` in a component that decides whether the secondary button is rendered at all is correct. There, `null` means "this button does not exist on this screen", not "the callback got lost": nullable only when `null` is a valid state (optional element), never when the element is always present in the UI.
