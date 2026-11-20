# Instructions for Claude Code

Multi-module Android template (Kotlin, Jetpack Compose, Clean Architecture, Koin). The root `README.md` is the source of truth for the stack, the module structure and the steps to create a new app from the template: do not duplicate it here; if it gets out of date, fix it there.

## Language

- **Claude documentation and configuration are always written in English**: this file, `.claude/rules/`, `.claude/agents/`, `.claude/skills/`, hook scripts and their messages, and any other file under `.claude/`. This applies even when the conversation happens in another language.
- **Reply to the user in the language they write in.** The user may write in any language; answer in that same language.

## Contextual rules

The rules in `.claude/rules/` load automatically based on the file being touched (`paths:` frontmatter):

`domain-layer`, `api-services`, `viewmodel`, `feature-modules`, `compose-views`, `navigation`, `koin-modules`, `analytics`, `sdk-integrations`, `logging`, `build-system`, `ci-cd`.

`engineering-principles.md` and `proactive-critique.md` have no `paths:`: they load always, in every session.

## Agents

| Agent | When to use it |
| --- | --- |
| `reviewer` | Validate the quality of a change before commit/PR against `.claude/rules/`. Only reports, does not fix |

## Hooks

- `debug-log-guard.sh` (`PreToolUse` on `Edit|Write`): blocks `println(` and `android.util.Log.*` in Kotlin files (see `rules/logging.md`). If it blocks something that should be allowed, review the hook; do not work around it.

## Verification before considering a change done

Same command as CI (`.github/workflows/ci.yml`):

```bash
./gradlew detekt testDevDebugUnitTest test assembleDevDebug --continue
```

A green build does not prove the app starts: if the change touches startup, the Koin graph, R8 or the manifest, test it on an emulator (see `engineering-principles.md`).

## Autonomy and decisions

**Decision making**: when an approach fails or unexpected complexity shows up, present the options to the user and wait for their decision. Do not discard alternatives, revert changes or take the "easy way out" unilaterally. "It can't be done" is followed by "how do you want to proceed?", never by a direct action.

**Anthropic server errors**: if an agent or subagent fails because of an API error (529 overloaded, 500, rate limit), do not retry automatically: tell the user and wait for instructions.

## Subagents — restricted use

Launch an exploratory search subagent only when the search really spans many unknown files or separate areas of the codebase; for a targeted search, use `grep`/`find` directly. The `reviewer` agent is invoked by task type, not by number of files.

## Git

- Main branch: `master` (CI runs on it).
- Commit messages in English, one imperative sentence that reads on its own (`Migrate buildSrc to build-logic convention plugins`), as in the existing history.
- Changes to `.claude/` go in a separate commit from code changes, so they can be identified and reverted on their own.
