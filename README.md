# Drop Multiplier

An auxiliary mod that can modify the drop rate of game items — for players who want to
slack off in casual play or challenge the limits of their graphics card.

* **This branch**: Minecraft `1.21.7` · Forge `NeoForge 21.7.25-beta` · mod `1.21.7-1.0.1`
* **Requires**: Minecraft 1.21.7 + Forge NeoForge 21.7.25-beta (or a newer 1.21.7 build)

## Supported versions

| Minecraft | Loader | Version | Mod version | Status |
|-----------|--------|---------|-------------|--------|
| 1.12.2 | Forge | 14.23.5.2859 | 1.12.2-1.3.0 | 可用 |
| 1.13.2 | Forge | 25.0.223 | 1.13.2-1.0.1 | 可用 |
| 1.14.2 | Forge | 26.0.63 | 1.14.2-1.0.2 | 可用 |
| 1.14.3 | Forge | 27.0.60 | 1.14.3-1.0.2 | 可用 |
| 1.14.4 | Forge | 28.2.30 | 1.14.4-1.0.1 | 可用 |
| 1.15 | Forge | 29.0.4 | 1.15-1.0.1 | 可用 |
| 1.15.1 | Forge | 30.0.51 | 1.15.1-1.0.1 | 可用 |
| 1.16.1 | Forge | 32.0.108 | 1.16.1-2.1.2 | 可用 |
| 1.16.2 | Forge | 33.0.61 | 1.16.2-2.2.2 | 可用 |
| 1.16.3 | Forge | 34.1.42 | 1.16.3-2.3.1 | 可用 |
| 1.16.4 | Forge | 35.1.37 | 1.16.4-1.0.1 | 可用 |
| 1.16.5 | Forge | 36.2.42 | 1.16.5-2.5.5 | 可用 |
| 1.17.1 | Forge | 37.1.1 | 1.17.1-1.0.2 | 可用 |
| 1.18 | Forge | 38.0.17 | 1.18-1.0.2 | 可用 |
| 1.18.1 | Forge | 39.1.2 | 1.18.1-1.0.2 | 可用 |
| 1.18.2 | Forge | 40.3.12 | 1.18.2-1.0.2 | 可用 |
| 1.19 | Forge | 41.1.0 | 1.19-1.0.2 | 可用 |
| 1.19.1 | Forge | 42.0.9 | 1.19.1-1.0.2 | 可用 |
| 1.19.2 | Forge | 43.5.2 | 1.19.2-1.0.2 | 可用 |
| 1.19.3 | Forge | 44.1.23 | 1.19.3-1.0.2 | 可用 |
| 1.19.4 | Forge | 45.4.5 | 1.19.4-1.0.2 | 可用 |
| 1.20.1 | Forge | 47.4.29 | 1.20.1-5.2.2 | 可用 |
| 1.20.2 | Forge | 48.1.0 | 1.20.2-1.0.1 | 可用 |
| 1.20.3 | Forge | 49.0.2 | 1.20.3-1.0.1 | 可用 |
| 1.20.4 | Forge | 49.2.13 | 1.20.4-1.0.1 | 可用 |
| 1.20.6 | Forge | 50.2.15 | 1.20.6-1.0.1 | 可用 |
| 1.21 | Forge | 51.0.33 | 1.21-1.0.1 | 可用 |
| 1.21.1 | Forge | 52.1.21 | 1.21.1-1.0.1 | 可用 |
| 1.21.3 | Forge | 53.1.16 | 1.21.3-1.0.1 | 可用 |
| 1.21.4 | NeoForge | NeoForge 21.4.123 | 1.21.4-1.0.1 | 可用 |
| 1.21.5 | NeoForge | NeoForge 21.5.101 | 1.21.5-1.0.1 | 可用 |
| 1.21.6 | NeoForge | NeoForge 21.6.20-beta | 1.21.6-1.0.1 | 可用 |
| 1.21.7 | NeoForge | NeoForge 21.7.25-beta | 1.21.7-1.0.1 | 可用 |
| 1.21.8 | NeoForge | NeoForge 21.8.57 | 1.21.8-1.0.1 | 可用 |
| 1.21.9 | NeoForge | NeoForge 21.9.16-beta | 1.21.9-1.0.1 | 可用 |
| 1.21.10 | NeoForge | NeoForge 21.10.64 | 1.21.10-1.0.1 | 可用 |
| 1.21.11 | NeoForge | NeoForge 21.11.48 | 1.21.11-1.0.1 | 可用 |
| 26.1 | NeoForge | NeoForge 26.1.0.19-beta | 26.1-1.0.1 | 可用 |
| 26.1.1 | NeoForge | NeoForge 26.1.1.15-beta | 26.1.1-1.0.1 | 可用 |
| 26.1.2 | NeoForge | NeoForge 26.1.2.126 | 26.1.2-1.0.1 | 可用 |
| 26.2 | NeoForge | NeoForge 26.2.0.93 | 26.2-1.0.1 | 可用 |
| 26.3 | NeoForge | NeoForge 26.3.0.102 | 26.3-1.0.1 | 可用 |

Every version above is built, started on a dedicated server and exercised through the
`/dropmultiplier` command (info / set / enable / reset).

> Versions after 1.21.3 (1.21.4+, 26.x) are not published yet: the current ForgeGradle
> toolchain fails on their MCP setup, so no reliable jar can be produced here yet.

## Features

* Mob drops, block drops, loot-table drops (chests, fishing) and experience are multiplied
* `/dropmultiplier <value>` sets the multiplier, `/dropmultiplier info` shows the state
* `/dropmultiplier enable|disable|reset` toggles or resets it
* Whitelist / blacklist for loot tables and mobs
* Press `.` in game to toggle (client side)
* Config file `config/dropmultiplier-common.toml`

## Building

```bash
./gradlew build
```

The jar is written to `build/libs/`.
