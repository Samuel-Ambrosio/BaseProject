---
paths:
  - "**/di/*Module.kt"
  - "**/di/AppComponent.kt"
---

When adding or touching a registration in a Koin module (`single`, `factory`, `viewModel`):

## One Koin module per Gradle module

Each Gradle module exposes a single `val {x}Module = module { ... }` in its `di/` package (`useCaseModule`, `repositoryModule`, `remoteModule`, `localModule`, `analyticsModule`, `feature{X}Module`), and `appComponent` (`app/.../di/AppComponent.kt`) gathers them. A new module is not active until it is added to `appComponent`.

## Alphabetical order by registered type

Registrations in a Koin module are sorted alphabetically by the **registered type**, not by when they were added or by affinity with the neighboring registration. A new registration goes in its alphabetical slot, never at the end of the module.

- **Sort key**: the type name without its suffix (`UseCase`, `Repository`, `DataSource`), compared case-sensitively. So `GetTodo` goes before `GetTodos`.
- **`single<Interface> { Impl(...) }`**: sorted by the interface, not the implementation.
- **Exception**: the infrastructure everything else needs (in `remoteModule`: interceptor, `OkHttpClient`, `Moshi`, `Retrofit`) goes first, as a block, followed by services and data sources in alphabetical order.

## Named arguments

Constructors are invoked with named arguments (`TodoRepositoryImpl(todoRemoteDataSource = get())`), as in the rest of the project: a positional `get()` breaks silently if the constructor is reordered and two parameters share a type.
