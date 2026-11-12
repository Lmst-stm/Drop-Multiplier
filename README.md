# Drop Multiplier

An auxiliary mod that can modify the drop rate of game items — for players who want to
slack off in casual play or challenge the limits of their graphics card.

* **This branch**: Minecraft `26.1.2` · Forge `NeoForge 26.1.2.126` · mod `1.0.1`
* **Requires**: Minecraft 26.1.2 + Forge NeoForge 26.1.2.126 (or newer 26.1.2 build)

## Supported versions

| Minecraft | Forge | Mod version | Status |
|-----------|-------|-------------|--------|
| 1.13.2 | 25.0.223 | 1.13.2-1.0.1 | OK |
| 1.14.2 | 26.0.63 | 1.14.2-1.0.2 | OK |
| 1.14.3 | 27.0.60 | 1.14.3-1.0.2 | OK |
| 1.14.4 | 28.2.30 | 1.14.4-1.0.1 | OK |
| 1.15 | 29.0.4 | 1.15-1.0.1 | OK |
| 1.15.1 | 30.0.51 | 1.15.1-1.0.1 | OK |
| 1.15.2 | 31.2.62 | 2.0.3 | OK |
| 1.16.1 | 32.0.108 | 1.16.1-2.1.2 | OK |
| 1.16.2 | 33.0.61 | 1.16.2-2.2.2 | OK |
| 1.16.3 | 34.1.42 | 1.16.3-2.3.1 | OK |
| 1.16.4 | 35.1.37 | 1.16.4-1.0.1 | OK |
| 1.16.5 | 36.2.42 | 1.16.5-2.5.5 | OK |
| 1.17.1 | 37.1.1 | 1.17.1-1.0.2 | OK |
| 1.18 | 38.0.17 | 1.18-1.0.2 | OK |
| 1.18.1 | 39.1.2 | 1.18.1-1.0.2 | OK |
| 1.18.2 | 40.3.12 | 1.18.2-1.0.2 | OK |
| 1.19 | 41.1.0 | 1.19-1.0.2 | OK |
| 1.19.1 | 42.0.9 | 1.19.1-1.0.2 | OK |
| 1.19.2 | 43.5.2 | 1.19.2-1.0.2 | OK |
| 1.19.3 | 44.1.23 | 1.19.3-1.0.2 | OK |
| 1.19.4 | 45.4.5 | 1.19.4-1.0.2 | OK |
| 1.20.1 | 47.4.29 | 1.20.1-5.2.2 | OK |
| 1.20.2 | 48.1.0 | 1.20.2-1.0.1 | OK |
| 1.20.3 | 49.0.2 | 1.20.3-1.0.1 | OK |
| 1.20.4 | 49.2.13 | 1.20.4-1.0.1 | OK |
| 1.20.6 | 50.2.15 | 1.20.6-1.0.1 | OK |
| 1.21 | 51.0.33 | 1.21-1.0.1 | OK |
| 1.21.1 | 52.1.21 | 1.21.1-1.0.1 | OK |
| 1.21.3 | 53.1.16 | 1.21.3-1.0.1 | OK |
| 1.21.4 | NeoForge 21.4.123 | 1.0.1 | built & server-tested (NeoForge) |
| 1.21.5 | NeoForge 21.5.101 | 1.0.1 | built & server-tested (NeoForge) |
| 1.21.6 | NeoForge 21.6.20-beta | 1.0.1 | built & server-tested (NeoForge beta) |
| 1.21.7 | NeoForge 21.7.25-beta | 1.0.1 | built & server-tested (NeoForge) |
| 1.21.8 | NeoForge 21.8.57 | 1.0.1 | built & server-tested (NeoForge) |
| 1.21.9 | NeoForge 21.9.16-beta | 1.0.1 | built & server-tested (NeoForge beta) |
| 1.21.10 | NeoForge 21.10.64 | 1.0.1 | built & server-tested (NeoForge) |
| 1.21.11 | NeoForge 21.11.48 | 1.0.1 | built & server-tested (NeoForge) |
| 26.1.2 | NeoForge 26.1.2.126 | 1.0.1 | built & server-tested (NeoForge, Java 25) |

## Features

* Mob drops, block drops, loot-table drops (chests, fishing, generic loot) and experience orbs
* Configurable multiplier, whitelist / blacklist per id
* In-game command `/dropmultiplier <multiplier>` with `info` / `enable` / `disable` / `toggle` / `reset`
* `.` key toggles the multiplier on the client
* Server-side authoritative: install the jar on both client and server

## Install

1. Install Minecraft 26.1.2 and Forge NeoForge 26.1.2.126.
2. Drop `dropmultiplier-1.0.1.jar` into `mods/` on the client **and** the server.
3. Start the game / server once; the config is written to `config/dropmultiplier-server.toml`.

## Commands

```
/dropmultiplier <multiplier>   set the multiplier (e.g. 2.5)
/dropmultiplier                show the current multiplier
/dropmultiplier info           show full status
/dropmultiplier enable|disable|toggle
/dropmultiplier reset          reset to the default value
```

## Config (`config/dropmultiplier-server.toml`)

| Key | Default | Meaning |
|-----|---------|---------|
| `dropMultiplier` | `2.0` | multiplier applied to drops |
| `multiplierEnabled` | `true` | master switch |
| `affectExperience` | `true` | scale experience orbs |
| `affectMobs` | `true` | scale mob drops |
| `affectBlocks` | `true` | scale block drops |
| `affectLoot` | `true` | scale loot tables (chests, fishing, …) |
| `whitelist` | `[]` | only these ids are scaled (empty = all) |
| `blacklist` | `[]` | never scale these ids |

## Build from source

```bash
# JDK 25 is required
./gradlew build
# -> build/libs/dropmultiplier-1.0.1.jar
```

## Versioning

Mod versions are `26.1.2-<semver>`, tags are `v<semver>+26.1.2`.
Every change to a version branch bumps the patch number and updates this README and
`build.gradle` / `gradle.properties` together.
