---
paths:
  - "presentation/**/*.kt"
  - "app/src/main/**/*.kt"
---

When working with screen Composables or UI components:

## Theme tokens — `AppTheme`

Design values are centralized in `AppTheme` (`presentation/common/.../ui/theme/Theme.kt`), exposed through `CompositionLocalProvider`: `AppTheme.spacing`, `.colors`, `.typography`, `.icons`, `.animations`. The spacing scale (`AppSpacing.kt`) goes from `xxxs` (2dp) to `xxxl` (64dp).

**Always use the `AppTheme` token when the value exists in the scale *and* belongs to that token's domain: never a loose `.dp` or `Color(...)` when there is an equivalent, applicable token.**

### `AppSpacing` is for spacing, never for sizes

**`AppTheme.spacing` only applies to spacing: `padding`, `Arrangement.spacedBy`, `Spacer`. Never to a component's size** (`size`/`width`/`height`, radii, stroke widths), **even if the value matches a scale value exactly.**

A 16dp-tall divider is not "a `spacing.m` tall": it is a component that measures 16dp and happens to match. If the spacing scale is readjusted, spacings must move with it and sizes must not; using the token for a size turns that readjustment into a silent visual regression.

```kotlin
// ✅ spacing for spacing
Column(verticalArrangement = Arrangement.spacedBy(spacing.s)) { ... }
Row(modifier = Modifier.padding(horizontal = spacing.m)) { ... }

// ❌ spacing as a size, even though 16.dp == spacing.m and 24.dp == spacing.l
Box(modifier = Modifier.size(width = 1.dp, height = spacing.m))
Icon(modifier = Modifier.size(spacing.l))

// ✅ the size goes as a literal or a file constant
private val DIVIDER_HEIGHT = 16.dp
```

For a value that is NOT part of the scale, the fallback is a file-level `private val` constant, SCREAMING_SNAKE_CASE, not an `object`/`enum Layout` wrapping it. **Position: at the very top of the file, right after the imports**, not at the end next to the `@Preview`s nor between composables.

## Shared components — check `presentation:common` before creating one

Before creating an ad-hoc component, check `presentation/common/.../ui/composables/`:

- **`base/`**: structure and text. `Screen` (scaffold with `AppTopBar`, optional back button), `AppTopBar`, and the type scale in `Text.kt` (`Heading1`, `Heading2`, `BodyLargeBold`/`Regular`, `BodyMediumBold`/`Regular`, `BodySmallBold`/`Regular`, `ButtonText`).
- **`components/`**: controls. `AppButton`, `AppOutlinedButton`, `Dialog`, `DropdownSelector`, `HorizontalSeparator`.

Naming convention: `App*` for the design system's own controls and surfaces; descriptive names for the rest. Use the `Text.kt` type scale instead of a `Text` with a loose `style`. A component needed by two features goes to `presentation:common`; it is not duplicated.

## Selectors — the criterion is "anchored trigger", not "number of options"

- **`DropdownMenu`/`DropdownSelector`**: when the trigger is an anchored icon/field (3-dot menu, form field), regardless of the number of options.
- **`ModalBottomSheet`**: when the selection is screen-level, not anchored to a specific visual control (action sheet), even with only 2 options.
- **`Dialog`/`AlertDialog`**: only confirm/cancel, never to choose among several options.

## Dialog visibility state — `XxxDialogState`, never a `Boolean`

A screen that shows one or more dialogs models **which dialog is visible with a single state holder**, not with loose `var showXDialog by remember { mutableStateOf(false) }` (one flag per dialog contradicts the others, forces resetting N flags and does not carry the data the dialog needs).

1. **`enum class`** when no dialog needs its own data: `NONE` + one value per dialog.
2. **`sealed interface`** when some dialog needs parameters: `data object None` + one `data class`/`data object` per case.

- Name: `<ScreenName>DialogState`. `private`, declared at the end of the file (block 2 of "Declaration order inside the file"), right before the `@Preview`s.
- Stored with `var dialogState by remember { mutableStateOf<XxxDialogState>(XxxDialogState.None) }` (or `rememberSaveable` with the enum if it should survive screen recreation).
- **The block that renders the dialogs ALWAYS goes at the end of the screen `@Composable` body, after the content**: an exhaustive `when (dialogState)`, with `None`/`NONE -> Unit`.
- Open = `dialogState = XxxDialogState.Foo` (from a content callback or a `Command`); close = `dialogState = XxxDialogState.None` in the dialog's own `onDismiss`/callbacks.

```kotlin
@Composable
internal fun FooScreen(navigateUp: () -> Unit, navigateToHome: () -> Unit) {
    val viewModel: FooViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var dialogState by remember { mutableStateOf(FooDialogState.NONE) }

    CommandHandler(viewModel) { command ->
        when (command) {
            is FooCommand.SessionExpired -> dialogState = FooDialogState.SESSION_EXPIRED
        }
    }

    FooScreenContent(
        state = state,
        onBackClick = navigateUp,
        onInfoClick = { dialogState = FooDialogState.INFO },
    )

    when (dialogState) {                              // ← at the end, after the content
        FooDialogState.NONE -> Unit
        FooDialogState.INFO -> FooInfoDialog(onDismiss = { dialogState = FooDialogState.NONE })
        FooDialogState.SESSION_EXPIRED -> SessionExpiredDialog(
            onConfirm = { dialogState = FooDialogState.NONE; navigateToHome() },
        )
    }
}

// block 2 — at the end of the file, before the @Previews
private enum class FooDialogState { NONE, INFO, SESSION_EXPIRED }
```

## Conditional blocks by role/state

**Scope: only screen states or conditionals that fork large Composables.** When a Composable has variants by state/role (guest vs. signed in, free vs. premium) and each variant paints a substantial UI block, the goal is **one self-contained block per variant**: a single high-level conditional deciding which block to compose, never ternaries scattered per property inside a shared block.

```kotlin
// ✅ Self-contained blocks
@Composable
fun ProfileHeader(userInfo: UserInfo?) {
    if (userInfo == null) GuestProfileHeader() else LoggedProfileHeader(userInfo)
}

// ❌ Five ternaries on the same variant inside a single block
AppButton(
    text = if (isLoggedIn) text else stringResource(R.string.sign_in),
    onClick = if (isLoggedIn) onClick else onSignInClick,
    // ...
)
```

**When it does NOT apply**: a conditional that only picks a scalar value inside the same UI block (which `stringResource`, which color, which icon) does not fork the UI and must not be extracted into two Composables: extracting there adds indirection for nothing (KISS). The criterion is not "there is an `if`", but **what forks**: if the branches produce different UI structures, split into blocks; if they only change a value inside the same structure, keep it inline.

## Random/non-deterministic fallback — never inside the shared Composable that consumes it

When a reusable Composable receives a nullable parameter and generates a fallback when it is `null` (a random illustration, `System.currentTimeMillis()`, a UUID), that generation **must not live inside the shared Composable itself**: every call site would resolve its own value, and if the same logical data is shown on two screens of the same flow, each would show a different one.

**Right pattern**: extract the resolution into its own `@Composable remember…` function and resolve it **only once** at the screen's entry point; the resolved (non-nullable) value is passed down, both to the UI component and to any navigation callback that needs the same data.

```kotlin
@Composable
internal fun rememberResolvedIllustration(illustration: Illustration?): Illustration =
    remember(illustration) { illustration ?: illustrations.random() }

@Composable
internal fun HeroImage(illustration: Illustration) { /* only paints what it receives */ }
```

## Declaration order inside the file

**Do not declare classes or helper functions in the middle of the file, between Composables.** Mandatory order, top to bottom:

1. **The screen's visual Composables**: the entry point and the visual sub-components it uses, all together.
2. **The screen's classes**: `XxxScreenState` first, `XxxDialogState` next, the rest after that.
3. **Helper methods that paint no UI of their own**: pure Kotlin transformation functions, and `@Composable`s that only resolve a value (e.g. `val Foo.text @Composable get() = stringResource(...)`). If a `@Composable` paints something (even a single `Text`), it goes in block 1.
4. **`@Preview`**: always at the end of the file, `private`.

## Order of navigation callbacks in a screen Composable signature

Navigation parameters (`navigateUp`, `navigateToX`) go **together and at the end of the signature**, after input data and dependencies (`appScope`, `appSnackbarHostState`, …), in this fixed order:

1. **`navigateUp`**: always first.
2. **`navigateToHome`**: if present, right after.
3. **The remaining `navigateToX`**: alphabetical by callback name.

```kotlin
// ✅ Correct
@Composable
internal fun DetailScreen(
    itemId: ItemId,
    appSnackbarHostState: SnackbarHostState,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToEdit: (ItemId) -> Unit,
    navigateToShare: (ItemId) -> Unit,
)
```

**Same order at the call site** (the `appEntry` in `*NavEntries`): named arguments follow the signature's order.

`navigateUp` and `navigateToHome` are not interchangeable: `navigateUp` is "one step back in the back stack" (`safeRemoveLast`/`removeLastOrNavigateUp`); `navigateToHome` is "discard the whole flow and go back to the root" (`clearAndAdd(AppNavKey.Home.Main)`). The same screen can declare both.
