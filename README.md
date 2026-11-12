# Drop Multiplier

An auxiliary mod that can modify the drop rate of game items — for players who want to
slack off in casual play or challenge the limits of their graphics card.

* **This branch**: Minecraft `1.21.1` · Forge `52.1.21` · mod `1.0.1`
* **Requires**: Minecraft 1.21.1 + Forge 52.1.21 (or a newer 1.21.1 build)

## Supported versions

| Minecraft | Forge | Mod version | Status |
|-----------|-------|-------------|--------|
| 1.12.2 | 14.23.5.2859 | 1.3.0 | 可用 |
| 1.13.2 | 25.0.223 | 1.0.1 | 可用 |
| 1.14.2 | 26.0.63 | 1.0.2 | 可用 |
| 1.14.3 | 27.0.60 | 1.0.2 | 可用 |
| 1.14.4 | 28.2.30 | 1.0.1 | 可用 |
| 1.15 | 29.0.4 | 1.0.1 | 可用 |
| 1.15.1 | 30.0.51 | 1.0.1 | 可用 |
| 1.16.1 | 32.0.108 | 2.1.2 | 可用 |
| 1.16.2 | 33.0.61 | 2.2.2 | 可用 |
| 1.16.3 | 34.1.42 | 2.3.1 | 可用 |
| 1.16.4 | 35.1.37 | 1.0.1 | 可用 |
| 1.16.5 | 36.2.42 | 2.5.5 | 可用 |
| 1.17.1 | 37.1.1 | 1.0.2 | 可用 |
| 1.18 | 38.0.17 | 1.0.2 | 可用 |
| 1.18.1 | 39.1.2 | 1.0.2 | 可用 |
| 1.18.2 | 40.3.12 | 1.0.2 | 可用 |
| 1.19 | 41.1.0 | 1.0.2 | 可用 |
| 1.19.1 | 42.0.9 | 1.0.2 | 可用 |
| 1.19.2 | 43.5.2 | 1.0.2 | 可用 |
| 1.19.3 | 44.1.23 | 1.0.2 | 可用 |
| 1.19.4 | 45.4.5 | 1.0.2 | 可用 |
| 1.20.1 | 47.4.29 | 5.2.2 | 可用 |
| 1.20.2 | 48.1.0 | 1.0.1 | 可用 |
| 1.20.3 | 49.0.2 | 1.0.1 | 可用 |
| 1.20.4 | 49.2.13 | 1.0.1 | 可用 |
| 1.20.6 | 50.2.15 | 1.0.1 | 可用 |
| 1.21 | 51.0.33 | 1.0.1 | 可用 |
| 1.21.1 | 52.1.21 | 1.0.1 | 可用 |
| 1.21.3 | 53.1.16 | 1.0.1 | 可用 |

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
