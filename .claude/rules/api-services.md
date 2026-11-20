---
paths:
  - "data/sourcesImpl/remote/**/*.kt"
  - "data/repositoryImpl/**/*.kt"
  - "**/*Repository*.kt"
  - "**/*DataSource*.kt"
---

When working with Repositories, data sources or the `data:sourcesImpl:remote` models/mappers:

- Network DTOs (`Network*`, Retrofit/Moshi) and their mapping to Domain live exclusively in `data:sourcesImpl:remote`, never in `data:repositoryImpl`. `data:repositoryImpl` only orchestrates (local/remote/cache) and works with already-mapped Domain objects; it never touches a DTO.
- Retrofit services return `Result<Error, T>` directly (`suspend fun getTodos(): Result<Error, List<NetworkTodo>>`): `ResultCallAdapterFactory` makes this possible by translating HTTP codes and parsing/network exceptions into `Error`. Do not wrap a call in `try/catch` or return `Response<T>`: if an error case is missing, add it in `ResultResponseCall`.
- `Network*` classes, services and `Impl`s are `internal`: the module uses `explicitApi()` and only exposes its Koin module (`remoteModule`/`localModule`).
- Mapper naming: extension function `internal fun Network<X>.to<DomainName>(): DomainType`, next to the DTO for simple cases (`NetworkTodo.toTodo()` in `NetworkTodo.kt`) or in a dedicated `mapper/*.kt` file when the mapping is complex and multi-field.
- **Method order within the same resource group**: alphabetical within the cluster of the same resource, and the same order in the `interface`, its `Impl` and the Retrofit service.

## DTO field names — in English, with `@Json(name = …)`

`Network*` properties are written in correctly spelled English and camelCase, whatever the backend returns: misspelled words (`tittle`), underscores instead of camelCase (`end_date`) or names in a language other than English (`preis`). The real JSON name goes in `@Json(name = "…")`:

```kotlin
// ✅
@JsonClass(generateAdapter = true)
internal data class NetworkOffer(
    val id: Long?,
    @Json(name = "end_date") val endDate: String?,  // underscore, not camelCase
    @Json(name = "preis") val price: Double?,       // not in English
    @Json(name = "tittle") val title: String?,      // misspelled
)

// ❌ The DTO carries over the backend's naming
internal data class NetworkOffer(val end_date: String?, val preis: Double?, val tittle: String?)
```

- If the field already comes in English and camelCase, no annotation is needed.
- Form: `@Json(name = "…")` in front of the property, not `@field:Json`.
- **When you annotate a field, cover the decoding with a Moshi test** (`moshi.adapter(Network<X>::class.java).fromJson(json)`): a misspelled name in `@Json` does not fail at compile time; it silently leaves the field `null`.

## Caching in Repositories — `cachedRemoteResource` / `cachedRemoteResourceFlow`

When a Repository serves data from a cache (memory, Room or DataStore) and only goes to the remote when it does not have it, **use the helpers in `domain/repository/.../utils/CachedRemoteResult.kt`**. Do not hand-write `cache?.asSuccess() ?: remote().ifSuccess { save(it) }`.

```kotlin
// ✅ suspend, one-off read
override suspend fun getAll(): Result<Error, List<Todo>> =
    cachedRemoteResource(
        getFromLocal = todoLocalDataSource::getAll,
        getFromRemote = todoRemoteDataSource::getAll,
        saveToLocal = { todos -> todoLocalDataSource.save(todos = todos) },
    )
```

- **`cachedRemoteResource`** (`suspend`, returns `Result<Error, T>`):
  - Reads `getFromLocal()`. If it is `null`, or if `shouldFetch(cached)` is `true`, it calls `getFromRemote()`, saves with `saveToLocal` and returns what it reads again from `getFromLocal()`.
  - A remote failure is not saved, and an exception is logged with `AppLogger` and turned into `Error.Unknown`.
  - **`shouldFetch`** decides when to refresh even if there is a cached value (e.g. `{ it.isEmpty() }`).
  - There is a `<T, R>` overload for when the remote returns a different type from the one read from the cache.
- **`cachedRemoteResourceFlow`** returns a `Flow<Result<Error, T>>` and observes `getFromLocal(): Flow<T?>`. Use it when the cache is observable (Room, DataStore).
- **Careful when testing the Repository**: since the helper reads `getFromLocal()` again after saving, the cache fake/mock must return `null` on the first read and the data on the second.
- **A detail item goes through the helper too.** If saving it where the list is stored would break the list, the local source stores it separately (e.g. a map by id) and the detail read looks in the list first and then there. That way the stored list is not replaced by a single item.

## Data sources — `{X}RemoteDataSource` / `{X}LocalDataSource`

The contract lives in `data:sources` (`{X}RemoteDataSource`, `{X}LocalDataSource`) and its implementation in `data:sourcesImpl:remote`/`:local` (`{X}RemoteDataSourceImpl`, `{X}LocalDataSourceImpl`). The name says where the data comes from, not how it is stored: a local source is called `LocalDataSource` whether it stores in memory, DataStore or Room. Never `{X}CacheDataSource`.

## DTO → Domain mapping

Mandatory rules for `Network*` → Domain entity mappers:

1. **NEVER use `?: ""`, `?: false`, `?: 0`, `.orEmpty()`** to mask a nullable response field. If the backend does not send a field, the domain model reflects that absence with Kotlin's nullable type.
   - **Only exception — lists whose absence means "empty" by contract**: `.orEmpty()` on a **list** is allowed when the backend not sending it means, by contract, zero elements and not unknown data. Each element's fields still follow rules 2–5. If the list's absence could mean something else (partial error, field not supported by that backend version), the exception does not apply. Justify the case in the PR with the contract or the real request that backs it.
2. **Return nullable** (`-> DomainEntity?`) when the mapping can fail because critical data is missing (e.g. `id`).
3. **Short-circuit on critical fields** (`id`, fields required by business logic) with an early `return null` if they are missing, instead of letting an invalid value through.
4. **Carry nullability over to the domain model**: fields the backend may not send are `String?`/`Int?`/etc. in the entity, not defaulted to empty.
5. **For lists, use `.mapNotNull`** (not `.map`) to filter out entities that could not be mapped.

**⚠️ MANDATORY**: deciding which fields are required (non-nullable) vs. optional is a business decision. ALWAYS ask the user explicitly before defining it, after verifying the real contract (a real request to the backend, not just what the docs or the ticket say).

```kotlin
// ✅ Correct
internal fun NetworkThing.toThing(): Thing? {
    val id = id ?: return null
    return Thing(
        id = id,
        name = name,           // String? → String? (no ?:)
        imageUrl = imageUrl,   // String? → String? (no ?:)
    )
}

// ❌ Wrong
internal fun NetworkThing.toThing(): Thing =
    Thing(
        id = id ?: "",         // ← masks missing data
        name = name ?: "",     // ← the UI shows an empty text without knowing the data is missing
    )
```

Why: silent defaults cause downstream bugs (sending an empty id to an endpoint, blank cards in the UI without knowing data is missing). If the data does not come, the nullable travels up to the layer that decides what to show.

**About `NetworkTodo`**: the sample DTO declares all its fields non-nullable because `jsonplaceholder` always sends them. With Moshi codegen, a non-nullable field missing from the JSON throws `JsonDataException` and the whole response fails (`Error.JsonSyntax`). Do not copy it as a template for a real backend: decide nullability field by field from the contract.

## Backend → Domain enum mapping

**⚠️ MANDATORY**: when a response field contains enum-like values (strings that represent categories, types, states), the type is **never** a Kotlin `enum class` consumed directly through `@JsonClass(generateAdapter = true)`. The adapter generated by Moshi throws `JsonDataException` as soon as the backend sends an unknown value: there is no fallback, and parsing of the whole object fails.

Valid patterns:

**A — sealed class + manual `valueOf` with fallback** (recommended by default: the field arrives as a raw `String` in the DTO):

```kotlin
// Domain — domain:model
sealed class Status(val value: String) {
    data object Unknown : Status(UNKNOWN)
    data object Pending : Status(PENDING)
    // ...
    companion object {
        fun valueOf(value: String): Status = when (value.uppercase()) {
            PENDING -> Pending
            // ...
            else -> Unknown
        }
    }
}

// Data — data:sourcesImpl:remote
private fun NetworkThing.mapStatus() = status?.let { Status.valueOf(it) } ?: Status.Unknown
```

**B — `enum class` in the DTO + registered custom Moshi adapter**, when the DTO needs strong typing during parsing itself:

```kotlin
internal enum class NetworkPromotionType { AMOUNT, PERCENT, UNKNOWN }

internal class NetworkPromotionTypeAdapter {
    @FromJson
    fun fromJson(value: String): NetworkPromotionType =
        NetworkPromotionType.entries.firstOrNull { it.name == value.uppercase() } ?: NetworkPromotionType.UNKNOWN
}
```

and register it explicitly in `moshiProvider()` (`RemoteModule.kt`, `Moshi.Builder().add(NetworkPromotionTypeAdapter())`): unregistered, the `enum class` is left exposed with no safety net.

Why: both patterns are type-safe, resilient to new backend values (they fall into `Unknown`/`UNKNOWN` instead of breaking) and keep traceability with the real API contract.
