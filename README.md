# Night 26.2

Night to klient użytkowy (utility mod) dla **Minecrafta 26.2** na **Fabric**, nastawiony na serwery anarchiczne: combat (crystal aura, surround, piston aura, hole fill), movement, render, world oraz zestaw narzędzi administracyjnych.

Repozytorium zawiera pełne źródła projektu w standardowym układzie Fabric/Gradle — kod został odtworzony z opublikowanego archiwum `Night-src_out-build-26.2.zip`, naprawiony pod kątem kompilacji na Minecraft 26.2 i zbudowany od zera.

| Element | Wersja |
|---|---|
| Minecraft | 26.2 |
| Fabric Loader | 0.19.3+ |
| Fabric API | 0.161.0+26.2 |
| Java | 25 (Temurin 25.0.5.1+2) |
| Gradle | 9.9.2 (wrapper w repo) |
| Loom | 1.19.4 (`net.fabricmc.fabric-loom`, tryb no-remap) |

## Budowanie

Wymagany JDK 25 (Minecraft 26.2 nie jest obfuskowany, więc projekt nie używa mappings — Loom pracuje w trybie no-remap).

```bash
export JAVA_HOME=/ścieżka/do/jdk-25
./gradlew build
```

Wynik:

```
build/libs/night-26.2.jar           # mod (gotowy do wrzucenia do mods/)
build/libs/night-26.2-sources.jar   # źródła w JAR-ze
```

Sama kompilacja: `./gradlew compileJava`.

## Instalacja

1. Zainstaluj Fabric Loader 0.19.3+ dla Minecraft 26.2.
2. Wrzuć `night-26.2.jar` oraz **Fabric API** (`fabric-api-0.161.0+26.2.jar`) do `mods/`.
3. Uruchom profil Fabric. Mod wymaga Javy 25.

## Struktura projektu

```
src/main/java/night/
├── Night.java              # klasa główna, inicjalizacja managerów
├── accounts/               # konta Microsoft (device-code OAuth) i offline
├── managers/               # ModuleManager, ConfigManager, RotationManager, WorldManager, ...
├── modules/                # 148 modułów w kategoriach (combat, movement, player, visuals, ...)
├── settings/               # system ustawień (Boolean, Number, Mode, String, Color, Bind, Whitelist, ...)
├── events/                 # własny event bus + typy eventów
├── mixins/                 # mixiny i accessory do klas Minecrafta
├── pingbypass/             # proxy „ping bypass" (Netty) do serwerów z proxy
├── gui/                    # ClickGUI, HUD, ekrany (accounts, image select, whitelist)
├── utils/                  # matematyka, render, fonty, świat, inventory, rotacje
└── commands/               # komendy czatu (module, config, bind, friend, ...)
```

Zasoby: `src/main/resources/` (`fabric.mod.json`, `night.mixins.json`, `night.accesswidener`, assety GUI).

## Dokumentacja ustawień

[`docs/SETTINGS.md`](docs/SETTINGS.md) — wygenerowany z kodu opis **wszystkich 1538 ustawień w 148 modułach**: nazwa, typ, wartość domyślna, zakres lub lista opcji, warunek widoczności (grupa) oraz pełny opis każdego ustawienia.

Ustawieniami można sterować z czatu, np.:

```
<prefix>module <nazwa> <ustawienie> <wartość>
<prefix>module <nazwa> <ustawienie> reset
<prefix>module <nazwa> <ustawienie> list
```

## Bezpieczeństwo

[`docs/SECURITY.md`](docs/SECURITY.md) — raport z audytu źródeł: brak uruchamiania procesów, brak ładowania bibliotek natywnych, wykaz wszystkich połączeń sieciowych w kodzie, miejsca zapisu danych lokalnych oraz ocena ryzyka.

Najważniejsze punkty:

- brak `Runtime.exec` / `ProcessBuilder` / `System.load` w kodzie modu,
- brak aktywnego mechanizmu zaszyfrowanych payloadów (`PayloadClassLoader` istnieje, ale nie ma żadnych plików `.enc` i nie jest nigdzie tworzony),
- mod łączy się wyłącznie z endpointami opisanymi w raporcie (Microsoft/Xbox dla logowania, serwer IRC moda, opcjonalny webhook Discorda konfigurowany przez użytkownika, własny serwer proxy),
- tokeny kont są zapisywane lokalnie w `accounts.json` w postaci jawnej — traktuj ten plik jak hasło.

## Stan odtworzenia źródeł

Kod pochodzi z dekompilacji (CFR 0.152), dlatego przeszedł automatyczną i ręczną naprawę: usunięto adnotacje Lomboka i `javax.annotation`, odtworzono brakujące klasy (`night.accounts.Account`, `AccountType`, `MicrosoftAuth`, `night.managers.AccountManager`, accessor `MinecraftUserAccessor`), naprawiono setki artefaktów dekompilatora (casty, generyki, `switch` wzorcowe, zmienne lokalne, bloki try/finally). Projekt kompiluje się i buduje bez błędów; zachowanie moda nie było testowane w grze.

## Licencja i autorzy

- Autorzy oryginału (z `fabric.mod.json`): **thonsanlo**, **HumanBeing**, **EU** (baza).
- Plik `LICENSE` w repozytorium: **MIT** (Copyright 2026 godmodule). Metadane `fabric.mod.json` deklarują `GPL-3.0` — pozostawiono bez zmian, tak jak w oryginale; przy redystrybucji upewnij się, która licencja obowiązuje.
