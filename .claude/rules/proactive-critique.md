# Proactive critique — MANDATORY

These rules apply in **every session**, without exception.

---

## Main rule

**NEVER perpetuate an existing pattern just because it already exists in the code.**

The current code may be wrong. Something being in the project does not make it correct, even less so in a template, where part of the code is only a sample. Before implementing, documenting or recommending, always evaluate against the documented principles, not against what is there.

---

## When to flag a problem — MANDATORY

Flag proactively, **before writing code or documentation**, when any of these is detected:

- **Separation of concerns violated**: a module, class or file mixes different responsibilities (e.g. DTO → Domain mapping inside `data:repositoryImpl` instead of `data:sourcesImpl:*`).
- **Wrong naming**: the name does not reflect the real role of the type, file or module according to the project's conventions.
- **Wrong placement**: a file lives in a Gradle module that does not match its layer. See `engineering-principles.md` ("Clean Architecture — Dependency Rule and module placement") for the criterion, and "Codebase precedent before theory" to look for the established pattern before treating it as ambiguous.
- **SOLID violation**: especially SRP and DIP.
- **Local pattern that does not scale**: something works for the current case but will break silently as it grows (e.g. a `data class State` that masks a nullable field with `?: ""` instead of propagating it).
- **Shotgun Surgery**: one conceptual change forces touching several files because the procedure is duplicated instead of having a single source (violates DRY).
- **Primitive Obsession**: a domain value (an id, a token) travels as a loose `String`/`Int` instead of a value class/dedicated type when one already exists for that concept.
- **Feature Envy**: a method uses another type's data/properties more than its own: the logic belongs there, not here.
- **Speculative Generality**: an abstraction, parameter or module with no real consumer today, "in case we need it later". See `engineering-principles.md` → "Vocabulary of patterns already in use" for what NOT to add without evidence.

---

## How to flag it

1. **State the concrete problem**: what it violates and why it is a problem.
2. **Propose the right alternative**: with judgment, not as a list of equivalent options. If one option is objectively better, say so directly.
3. **Wait for confirmation** before implementing if the problem affects a design decision the user must make.

Doing what was asked is not enough if what was asked is wrong. The responsibility is to flag it.

---

## What is NOT a valid justification

- `"It's already done like this elsewhere in the project"`: existing code may be wrong.
- `"It's the current pattern"`: current patterns are the starting point for improvement, not the standard.
- `"That's how the template does it"`: the template's sample code (the *Todo* feature, `NetworkTodo` with non-nullable fields) demonstrates the wiring; it does not set the business rules.
- `"Nobody asked me"`: the criterion applies without the user having to ask.

---

## Tone

Direct and professional. Do not apologize for flagging a problem. Do not soften it with "maybe" or "it could be that". If something is wrong, say it: **it is wrong, and this is why**.
