---
paths:
  - "domain/**/*.kt"
---

When working in the Domain layer (`domain:model`, `domain:repository`, `domain:useCase`: pure Kotlin, no Android dependencies):

## Order inside a model file and `fake*` variables

- **The class that names the file always comes first.** Helper types go below it, even if the class uses them: its id value class (`TodoId` below `Todo`), enums and private extensions.
- **`fake*` variables (used in Previews and tests) are declared in the file of the class they belong to**, in `domain:model`, as a public `val fakeX` at the end of the file. They never go in the file of the Composable that uses them in its Preview.

## Optionality in domain entities

Full optionality and DTO → Domain mapping rules are in `api-services.md`.

Summary for Domain:

- Fields the backend may not send → nullable type (`String?`, `Int?`, `Double?`). NEVER `String` defaulting to `""` or `Int` defaulting to `0`.
- Fields always present by contract → non-nullable type.
- **⚠️ MANDATORY**: optionality is a business decision. ALWAYS ask the user explicitly before defining it: do not assume which fields are critical without having verified the real contract first.

## Anti-dead-code check

**BEFORE adding a field to a domain entity**, check that at least one consumer (ViewModel, UseCase, Composable) is going to use it:

1. Search for usages with `grep -rn "\.fieldName" --include="*.kt" .` (excluding the definition and the mapper).
2. If there is NO consumer → **do NOT add the field**. A field with no consumer is dead code.
3. If the field will be needed "in the future" but not now → do not create it. It is added when a real consumer exists (YAGNI).

**The same criterion applies the other way round, to an existing field**: if during any task you find that an existing field has no consumers (same search, no results outside the definition and the mapper), it is dead code: delete it. No need to ask for this part (it is an objective check, not a business one).

## Domain errors — `Error`, not exceptions

Operations that can fail return `Result<Error, T>` (`domain/model/.../utils/Result.kt` and `Error.kt`); they never throw exceptions towards Presentation. If a business case needs its own error, add a case to the `sealed class Error` (and its text in `Error.handleErrorMessage`, `presentation:common`) instead of a loose `String` or a generic `Error.Unknown`.

## Domain enums with backend values

Full pattern with mapping examples in `api-services.md` ("Backend → Domain enum mapping").

Summary for Domain:

1. The domain type is never a Kotlin `enum class` consumed directly by Moshi when its values come from the backend: use a `sealed class`/`sealed interface` with an explicit `Unknown`/fallback case and a `valueOf(value: String)` that falls back to `Unknown` in the `else` branch.
2. The mapping (DTO ↔ Domain) lives in `data:sourcesImpl:remote`, never in Domain.
3. NEVER compare strings by hand scattered across the code (`if (value == "done")`): the translation lives in a single `when` with an `else` branch.
