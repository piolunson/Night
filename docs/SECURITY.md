# Raport bezpieczeństwa — Night 26.2

Audyt wykonany na źródłach odtworzonych z archiwum `Night-src_out-build-26.2.zip` (SHA-256 archiwum: `041c9bb559e0761fe959d9e67523d51bcb260998c650af2dc900d709cdf670a4`). Zakres: 628 plików źródłowych (562 klasy `.java`), zasoby, konfiguracja Gradle/Fabric. Metoda: przegląd statyczny kodu (grep po API wysokiego ryzyka + ręczna analiza trafień), bez uruchamiania moda w grze.

## Podsumowanie

| Kategoria | Wynik |
|---|---|
| Uruchamianie procesów (`Runtime.exec`, `ProcessBuilder`) | **brak** |
| Ładowanie bibliotek natywnych (`System.load`, `System.loadLibrary`, JNI) | **brak** |
| Zdalne ładowanie i definiowanie klas | klasa `PayloadClassLoader` + `AesCrypto` istnieją, ale **nie ma żadnych plików `.enc`** i loader nie jest nigdzie tworzony — martwy kod |
| Kradzież przeglądarek / portfeli / sesji | **brak** ścieżek do profili przeglądarek, portfeli kryptowalut, `Local Storage`, `login data` |
| Wysyłanie plików z dysku | **brak** poza obrazami wybranymi przez użytkownika w GUI (moduł obrazów) |
| Telemetria | **brak aktywnej** — `NightUserManager.poll()` jest pustą metodą (wątek odpytuje co 30 s, ale nic nie wysyła) |
| Zapis danych lokalnych | konfiguracja, konta, listy, kit-y, zrzuty skanów — w katalogu gry |
| Ryzyko ogólne | **średnie** — nie ma złośliwego kodu, ale mod jest klientem cheatującym i przechowuje tokeny konta w plaintext |

## 1. Brak wykonania kodu z zewnątrz

Sprawdzone wzorce (0 trafień w kodzie produkcyjnym):

- `Runtime.getRuntime().exec`, `ProcessBuilder`, `new ProcessBuilder`
- `System.load`, `System.loadLibrary`, `native` methods
- `ScriptEngine`, `Nashorn`, `Groovy`, `eval`
- deserializacja `ObjectInputStream` na danych z sieci
- `Base64` + `Cipher` w ścieżce startowej

Jedyne trafienia `System.setProperty` to `java.awt.headless=false` (potrzebne do obrazów w GUI).

### `PayloadClassLoader` / `AesCrypto`

`night/core/PayloadClassLoader.java` potrafi odszyfrować zasób `<klasa>.enc` kluczem AES i zdefiniować go przez `defineClass`. To klasyczny mechanizm ukrywania payloadu, **ale**:

- w `src/main/resources` nie ma ani jednego pliku `.enc`,
- `PayloadClassLoader` nie jest nigdzie instancjonowany (`new PayloadClassLoader` — 0 trafień),
- `AesCrypto` jest używany wyłącznie przez ten loader.

Wniosek: funkcjonalność jest nieaktywna w tym buildzie. Gdyby w przyszłym buildzie pojawiły się pliki `.enc`, należy je potraktować jako niezaufane.

## 2. Połączenia sieciowe w kodzie

| Endpoint | Kiedy | Co wysyła |
|---|---|---|
| `login.microsoftonline.com/consumers/oauth2/v2.0/devicecode` i `/token` | logowanie konta Microsoft (device-code OAuth) | identyfikator klienta OAuth, kod urządzenia |
| `user.auth.xboxlive.com`, `xsts.auth.xboxlive.com` | logowanie konta Microsoft | token MSA → XBL/XSTS |
| `api.minecraftservices.com/authentication/login_with_xbox`, `/minecraft/profile` | logowanie konta Microsoft | token XSTS, potem pobranie profilu |
| `wss://nightclient.info.vn/irc`, `ws://nightclient.info.vn/irc`, `ws://103.78.2.201/irc` | moduł **IRC** włączony przez użytkownika | nick gracza i treść wiadomości IRC |
| `nightclient.info.vn` (`NightUserManager`) | start klienta, wątek co 30 s | **nic** — `poll()` jest pusty |
| webhook Discorda (URL podawany przez użytkownika w module **StashFinder**) | wykrycie stasha | treść alertu + ping `<@userID>` |
| `ServerSocket(port)` w module **EURobotics** | moduł włączony | lokalny socket sterowania (bot/klient zewnętrzny) |
| `ServerSocket` proxy w module **PingBypass** (domyślnie port 25565) | moduł włączony | lokalny proxy TCP do wskazanego serwera MC |
| updater | — | `new URL("")` i pusty `SECRET_KEY` → aktualizator jest wyłączony |

Mod nie wysyła danych do żadnego serwera bez włączenia odpowiedniego modułu lub zalogowania konta. Warto jednak pamiętać, że **IRC i StashFinder** wysyłają dane na zewnątrz, gdy są aktywne.

## 3. Dane zapisywane lokalnie

Wszystko w katalogu gry (`config`/`night`):

- konfiguracja modułów i boundów (`ConfigManager`),
- `accounts.json` — konta i **tokeny sesji w postaci jawnej**,
- `webhook.json` — URL webhooka Discorda,
- `chestscan/*.json`, `stashfinder/*.json` — wyniki skanów,
- `rekit/*.json` + ostatni kit — zestawy itemów,
- obrazy wybrane w GUI.

`accounts.json` zawiera dane uwierzytelniające w plaintext. Nie synchronizuj katalogu gry z chmurą i nie wysyłaj go osobom trzecim.

Dodatkowo mod kopiuje zrzut ekranu do schowka systemowego (funkcja „screenshot to clipboard”) — schowek jest czytelny dla innych aplikacji.

## 4. Uprawnienia i zgodność

Night to klient typu „cheat/utility” — modyfikuje ruch, rotacje i akcje gracza w sposób niedozwolony na większości serwerów publicznych. Używaj go wyłącznie tam, gdzie regulamin na to pozwala (serwery prywatne, singleplayer, testy). Autorzy i osoba budująca ten JAR nie odpowiadają za bany wynikające z użycia.

## 5. Integralność artefaktów

| Plik | SHA-256 |
|---|---|
| `Night-src_out-build-26.2.zip` (oryginał) | `041c9bb559e0761fe959d9e67523d51bcb260998c650af2dc900d709cdf670a4` |
| `night-26.2.jar` (ten build) | `1808366269de673ffbfa3154e36a6a45702690c5a0b6ddac79e6b116d3c91162` |
| `night-26.2-sources.jar` | `3d970b083885ae900e532c45bc0c75ae8cb431366f9b58247590842778377608` |

Hash builda dotyczy artefaktu z tego konkretnego środowiska; przy ponownym budowaniu JAR będzie miał inny hash (Gradle nie jest deterministyczny domyślnie).
