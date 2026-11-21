# Security report — Night 26.2

Audit performed on the sources reconstructed from the `Night-src_out-build-26.2.zip` archive (archive SHA-256: `041c9bb559e0761fe959d9e67523d51bcb260998c650af2dc900d709cdf670a4`). Scope: the Java sources, resources, and the Gradle/Fabric configuration. Method: static review (grep for high-risk APIs plus manual analysis of every hit), without running the mod in game.

## Summary

| Category | Result |
|---|---|
| Process execution (`Runtime.exec`, `ProcessBuilder`) | **none** |
| Native library loading (`System.load`, `System.loadLibrary`, JNI) | **none** |
| Remote class loading and definition | the `PayloadClassLoader` class and `AesCrypto` exist, but there are **no `.enc` files** and the loader is never instantiated — dead code |
| Browser / wallet / session theft | **no** paths to browser profiles, crypto wallets, `Local Storage` or `login data` |
| Uploading files from disk | **none**, apart from images the user picks in the GUI (image module) |
| Telemetry | **none active** — `NightUserManager.poll()` is an empty method (the thread polls every 30 s but sends nothing) |
| Local data written | configuration, accounts, lists, kits, scan dumps — all inside the game directory |
| Overall risk | **medium** — no malicious code, but this is a cheating client and it stores account tokens in plaintext |

## 1. No execution of external code

Patterns checked (0 hits in production code):

- `Runtime.getRuntime().exec`, `ProcessBuilder`, `new ProcessBuilder`
- `System.load`, `System.loadLibrary`, `native` methods
- `ScriptEngine`, `Nashorn`, `Groovy`, `eval`
- `ObjectInputStream` deserialisation of network data
- `Base64` + `Cipher` on the startup path

The only `System.setProperty` hits are `java.awt.headless=false` (needed for GUI images).

### `PayloadClassLoader` / `AesCrypto`

`night/core/PayloadClassLoader.java` can decrypt a `<class>.enc` resource with an AES key and define it through `defineClass`. That is a classic payload-hiding mechanism, **but**:

- there is not a single `.enc` file in `src/main/resources`,
- `PayloadClassLoader` is never instantiated (`new PayloadClassLoader` — 0 hits),
- `AesCrypto` is used only by that loader.

Conclusion: the functionality is inactive in this build. If `.enc` files ever appear in a future build, treat them as untrusted.

## 2. Network connections in the code

| Endpoint | When | What it sends |
|---|---|---|
| `login.microsoftonline.com/consumers/oauth2/v2.0/devicecode` and `/token` | Microsoft account sign-in (device-code OAuth) | OAuth client id, device code |
| `user.auth.xboxlive.com`, `xsts.auth.xboxlive.com` | Microsoft account sign-in | MSA token → XBL/XSTS |
| `api.minecraftservices.com/authentication/login_with_xbox`, `/minecraft/profile` | Microsoft account sign-in | XSTS token, then the profile request |
| `wss://nightclient.info.vn/irc`, `ws://nightclient.info.vn/irc`, `ws://103.78.2.201/irc` | the **IRC** module, when enabled | player name and IRC message content |
| `nightclient.info.vn` (`NightUserManager`) | client start, thread every 30 s | **nothing** — `poll()` is empty |
| Discord webhook (URL supplied by the user in the **StashFinder** module) | stash detected | alert content + `<@userID>` ping |
| `ServerSocket(port)` in the **EURobotics** module | module enabled | local control socket (bot/external client) |
| `ServerSocket` proxy in the **PingBypass** module (default port 25565) | module enabled | local TCP proxy to the given MC server |
| updater | — | `new URL("")` and an empty `SECRET_KEY` → the updater is disabled |

The mod does not send data to any server unless the relevant module is enabled or an account is signed in. Keep in mind that **IRC and StashFinder** do send data outside when active.

## 3. Data written locally

Everything lives in the game directory (`config`/`night`):

- module configuration and binds (`ConfigManager`),
- `accounts.json` — accounts and **session tokens in plaintext**,
- `webhook.json` — the Discord webhook URL,
- `chestscan/*.json`, `stashfinder/*.json` — scan results,
- `rekit/*.json` plus the last kit — item sets,
- images picked in the GUI.

`accounts.json` holds credentials in plaintext. Do not sync the game directory to a cloud drive and do not send it to third parties.

The mod also copies screenshots to the system clipboard ("screenshot to clipboard" feature) — the clipboard is readable by other applications.

## 4. Permissions and compliance

Night is a cheat/utility client: it modifies movement, rotations and player actions in ways that are not allowed on most public servers. Use it only where the rules permit it (private servers, singleplayer, testing). The authors and the person building this JAR are not responsible for bans resulting from its use.

## 5. Artefact integrity

| File | SHA-256 |
|---|---|
| `Night-src_out-build-26.2.zip` (original) | `041c9bb559e0761fe959d9e67523d51bcb260998c650af2dc900d709cdf670a4` |
| `night-26.2-34a4ce8.jar` (this build) | `e65030bb14ee306b136b6ff6318718a1257d70a8748f2981ce1d7176e34649f0` |
| `night-26.2-34a4ce8-sources.jar` | `307988d989f5c7667fb5c0cdfde69f8e277bb3dd87997dd7e6469cc373fe0c3d` |

The build is reproducible: both archives use fixed timestamps and sorted entries, and the file name contains the commit (`night-26.2-<hash>.jar`). The same commit produces identical checksums — verify them with another `./gradlew build` and `build/libs/SHA256SUMS.txt`.
