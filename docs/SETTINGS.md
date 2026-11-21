# Night 26.2 — kompletny opis ustawień
Dokument wygenerowany automatycznie z kodu źródłowego (`src/main/java/night/modules`). Zawiera **148 modułów** i **1538 ustawień**.
## Jak czytać tabele
- **Ustawienie** — nazwa widoczna w GUI i używana w komendach (`<module> <setting> <value>`).
- **Typ** — klasa ustawienia: `Boolean` (przełącznik), `Number` (suwak/liczba), `Mode` (lista wyboru), `String` (tekst), `Color` (kolor), `Bind` (klawisz), `Whitelist` (lista bloków/itemów), `Image`, `Position`, `Category` (nagłówek grupy).
- **Domyślnie** — wartość startowa.
- **Zakres / opcje** — dopuszczalny zakres liczbowy lub lista dostępnych wartości.
- **Grupa** — ustawienie widoczne tylko gdy wskazane ustawienie przyjmuje podane wartości.
- **Tag** — krótka etykieta wyświetlana w GUI obok nazwy.

## Kategoria: COMBAT (32 modułów)

### AntiPhase
Places a climbable block (Scaffolding, Ladder, Vines) directly at a target enemy's feet.

Plik: `night/modules/impl/combat/AntiPhaseModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Block` | Mode | Scaffolding | Scaffolding, Ladder, Vines |  |  | The item placed at the enemy's feet. |
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to the selected item. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |
| `Range` | Number |  |  |  |  | The maximum distance at which enemies will be targeted. |
| `WallRange` | Number |  |  |  |  | The maximum distance at which enemies will be targeted through walls (no line of sight required). |
| `OnlyHole` | Boolean | false |  |  |  | Only places while the enemy is already standing in a hole (feet surrounded by blocks). |
| `PauseOnEat` | Boolean | false |  |  |  | Pauses placing while eating or using items. |

### AutoArmor
Automatically equips the best armor.

Plik: `night/modules/impl/combat/AutoArmorModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Health` | Mode | Highest | Highest, Lowest, Any |  |  | The health priority to apply. |
| `ElytraPriority` | Boolean | true |  |  |  | Prioritizes elytra over armor pieces. |
| `Preserve` | Boolean | false |  |  |  | Preserve low health armor to avoid it from breaking. |
| `PreserveHealth` | Number |  |  | preserve = true |  | The minimum health of armor to preserve it. |
| `SafeRange` | Number | 0 | 0 – 10 | preserve = true |  | Range to check for nearby players before re-equipping preserved armor. |
| `Elytra` | Boolean | false |  |  |  | Equips an elytra instead of a chestplate. |
| `ElytraBind` | Bind |  | 0 |  |  | Keybind that toggles Elytra on/off. |
| `SmartElytra` | Boolean | false |  |  |  | Chooses when to enable elytra in a more convenient way. |
| `HeadMode` | Mode | Prot | Blast, Prot |  |  | Which protection enchant Head armor is ranked by. |
| `ChestMode` | Mode | Prot | Blast, Prot |  |  | Which protection enchant Chest armor is ranked by. |
| `LegsMode` | Mode | Prot | Blast, Prot |  |  | Which protection enchant Legs armor is ranked by. |
| `FeetMode` | Mode | Prot | Blast, Prot |  |  | Which protection enchant Feet armor is ranked by. |

### AutoBowRelease
Automatically releases your bow after a certain amount of time has passed.

Plik: `night/modules/impl/combat/AutoBowReleaseModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Ticks` | Number | 3 | 0 – 20 |  |  | The number of ticks that have to be waited for before releasing the bow. |

### AutoCrystal
Automatically places and attacks crystals to annihilate your opponents.

Plik: `night/modules/impl/combat/AutoCrystalModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Attack` | Category |  |  |  |  | The category for settings related to attacking crystals. |
| `Attack` | Boolean | true |  |  | Enabled | Automatically attacks crystals that are deemed safe. |
| `AttackSpeed` | Number |  |  |  | Speed | The speed at which crystals will be attacked. |
| `AttackRange` | Number | 4.5 | 0.0 – 8.0 |  | Range | The maximum distance at which crystals will be attacked. |
| `AttackWallsRange` | Number | 4.5 | 0.0 – 8.0 |  | WallsRange | The maximum distance at which crystals will be attacked through walls. |
| `AntiWeakness` | Mode | None | None, Normal, Silent |  |  | Allows you to attack crystals when weaknessed. |
| `Instant` | Boolean | true |  |  |  | Instantly attacks crystals once they spawn. |
| `Inhibit` | Boolean | true |  |  |  | Prevents excessive attacks on crystals by blacklisting crystals when attacking them. |
| `Place` | Category |  |  |  |  | The category for settings related to placing crystals. |
| `Place` | Boolean | true |  |  | Enabled | Automatically places crystals on positions that are deemed safe and lethal enough. |
| `PlaceSpeed` | Number |  |  |  | Speed | The speed at which crystals will be placed. |
| `PlaceRange` | Number | 4.5 | 0.0 – 8.0 |  | Range | The maximum distance at which positions will be placed on. |
| `PlaceWallsRange` | Number | 4.5 | 0.0 – 8.0 |  | WallsRange | The maximum distance at which positions will be placed on through walls. |
| `Placements` | Mode | Native | Native, Protocol |  |  | The version of the game that will be used for crystal placement calculations. |
| `BlockDestruction` | Boolean | true |  |  |  | Places crystals on top of mined blocks in order to damage opponents. |
| `Switch` | Mode | None | None, Normal, Silent, AltSwap |  |  | Automatically switches to a crystal if you aren't currently holding one. |
| `SwapBack` | Boolean | false |  | autoSwitch = Normal |  | Switches back to the item you were holding before the module started switching to crystals, once the module is disabled. |
| `SwapDelay` | Number | 0 | 0 – 20 |  | Delay | The delay in ticks after swapping before placing or attacking crystals. |
| `Miscellaneous` | Category |  |  |  |  | The category for all miscellaneous settings. |
| `Sequential` | Mode | Strong | None, Strict, Strong |  |  | The sequence that the module's processes will be run in. |
| `Rotate` | Mode | Normal | None, Normal, Packet, Silent |  |  | Automatically rotates to the crystal whenever attacking or placing. |
| `Swing` | Mode | Default | Default, None, Packet, Mainhand, Offhand, Both |  |  | The hand that will be used for swinging. |
| `YawStep` | Boolean | false |  |  |  | Performs your rotations over multiple ticks. |
| `YawStepThreshold` | Number | 75 | 1 – 180 | yawStep = true | Threshold | The threshold in order for yaw to be modified. |
| `Raytrace` | Boolean | false |  |  |  | Avoids attacking or placing any crystals through walls. |
| `LosDebug` | Boolean | false |  |  | Debug | Prints once a second why the block you are aiming at passes/fails the place gates (LOS steps + damage). |
| `Target` | Mode | All | All, Nearest, Farthest, Health |  |  | Which player to target when multiple are in range -- narrowing this to one collapses the per-position player scan from O(players) to O(1), the actual fix for multitarget FPS drops. |
| `Extrapolation` | Number | 0 | 0 – 20 |  |  | Extrapolates the target's position to calculate positions ahead of time. |
| `EnemyRange` | Number | 10.0 | 0.0 – 24.0 |  |  | The maximum distance at which enemies can be at. |
| `IgnoreNaked` | Boolean | false |  |  |  | Ignores naked players (even if wearing only elytra), but still targets them if they are holding an End Crystal. |
| `ChestBreak` | Boolean | false |  |  |  | Prevents other players from getting obsidian from ender chests by destroying the dropped items. |
| `MineIgnore` | Boolean | false |  |  |  | Pre-places a crystal on the block SpeedMine is about to break, and detonates it the instant that block is gone. |
| `MineIgnoreTicks` | Number | 3 | 0 – 10 | mineIgnore = true | Tick | How many ticks before the block breaks to place the crystal. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `PauseOnSecondaryMine` | Boolean | true |  |  |  | Pauses AutoCrystal for one tick while SpeedMine's secondary block is about to break, so it finishes first. |
| `AwaitMine` | Boolean | false |  |  |  | Pauses AutoCrystal when AutoMine selects a target block so AutoMine can lock and mine properly. |
| `AwaitMineTicks` | Number | 2 | 1 – 10 | awaitMine = true | Ticks | The number of ticks to wait for AutoMine to lock target. |
| `PauseOnPearl` | Boolean | true |  |  |  | Pauses AutoCrystal when Phase is active, KeyAction pearl is used, or when throwing an ender pearl manually. |
| `PauseOnXP` | Boolean | true |  |  |  | Pauses AutoCrystal when throwing experience bottles manually or when KeyAction XP is active. |
| `GameLoop` | Boolean | false |  |  |  | Runs the module on loop instead of ticks. |
| `LoopDelay` | Number | 50 | 0 – 1000 | gameLoop = true |  | The delay that has to be waited out before running the module again. |
| `WhileEating` | Mode | Both | None, Attack, Place, Both |  |  | Places and attacks crystal while eating or using items. |
| `Prediction` | Category |  |  |  |  | The category for settings related to attack prediction. |
| `GodSync` | Boolean | false |  |  |  | Makes the attacking way faster by predicting entity IDs. |
| `Predictions` | Number | 10 | 1 – 20 |  |  | The amount of predictions that will be done after placing. |
| `Offset` | Number | 0 | 0 – 2 |  |  | The amount that the last entity ID should be offset by. |
| `GodSwing` | Mode | Normal | None, Normal, Strict |  | Swing | The swinging that will be done for each predicted attack. |
| `Fast` | Boolean | false |  |  |  | Improves the speed of the prediction calculations at the cost of stability. |
| `AntiKick` | Boolean | false |  |  |  | Prevents you from getting kicked by attacking invalid entity IDs. |
| `KickThreshold` | Number | 5 | 1 – 10 | antiKick = true | Threshold | The tick threshold for the kick prevention. |
| `Faceplace` | Category |  |  |  |  | The category for settings relating to faceplacing. |
| `FaceplaceMode` | Mode | Dynamic | None, Dynamic, Always |  | Mode | The checks that will be done in order to faceplace. |
| `FaceplaceSpeed` | Mode | Normal | Normal, Custom |  | Speed | The speed that players will be faceplaced at. |
| `FaceplaceDelay` | Number | 11 | 0 – 20 | facePlaceSpeed = Custom | Delay | The ticks that have to be waited for before faceplacing again. |
| `HealthPlace` | Boolean | true |  | facePlaceMode = Dynamic |  | Whether or not to faceplace when the target's health is low. |
| `Health` | Number |  |  | healthPlace = true |  | The health that the target needs to be at in order for the module to start faceplacing. |
| `ArmorPlace` | Boolean | true |  | facePlaceMode = Dynamic |  | Whether or not to faceplace when the target's armor is low on durability. |
| `Percentage` | Number | 10 | 1 – 100 | armorPlace = true |  | The percentage that one of the target's armor pieces need to be at in order to start faceplacing. |
| `BasePlace` | Category |  |  |  |  | The category for settings related to placing base obsidian blocks for elevated crystal attacks. |
| `BasePlace` | Boolean | true |  |  | Enabled | Automatically places obsidian base blocks under/next to targets. |
| `BaseSwitch` | Mode | Silent | None, Normal, Silent, AltSwap |  | Switch | Switch mode for placing base blocks. |
| `BaseAirPlace` | Boolean | false |  |  | AirPlace | Allows placing base blocks in the air without neighboring blocks. |
| `BaseStrictDirection` | Boolean | false |  |  | StrictDirection | Only places using directions that face you. |
| `BaseDamage` | Number | 6.0 | 0.0 – 36.0 |  | Min Damage | The minimum damage a crystal on the base must deal to the target. |
| `BaseRatio` | Number | 1.5 | 1.0 – 3.0 |  | Ratio | Damage ratio required over existing standard crystal placement to trigger baseplace. |
| `BaseRadius` | Number | 3.0 | 1.0 – 6.0 |  | Scan Radius | The horizontal radius around the target to scan for base placements. |
| `BaseRange` | Number | 4.5 | 0.0 – 6.0 |  | Range | The maximum distance at which base blocks will be placed. |
| `BaseDelay` | Number | 0 | 0 – 10 |  | Delay | Delay in ticks between placing base blocks. |
| `BaseRotate` | Boolean | false |  |  | Rotate | Rotates when placing base blocks. |
| `Damage` | Category |  |  |  |  | The category for settings related to damage calculations. |
| `MinimumDamage` | Number | 6.0 | 0.0 – 36.0 |  | Minimum | The minimum damage that has to be dealt to enemies. |
| `MaximumSelfDamage` | Number | 10.0 | 0.0 – 36.0 |  | MaximumSelf | The maximum damage that can be dealt to you by crystals. |
| `LethalMultiplier` | Number |  |  |  |  | The amount of crystals that the target has to be killed by in order to ignore minimum damage. |
| `AntiSuicide` | Boolean | true |  |  |  | Prevents crystals from accidentally killing you when you're low on health. |
| `IgnoreTerrain` | Boolean | true |  |  |  | Ignores terrain that can be destroyed when calculating damage. |
| `Render` | Category |  |  |  |  | Contains all of the settings relating to position rendering. |
| `Animation` | Mode | Static | Static, Slide |  |  | The animation that will be applied to the rendering. |
| `Mode` | Mode | Fade | Fade, Shrink |  |  | The mode for the auto crystal render. |
| `Duration` | Number | 300 | 0 – 1000 |  |  | The duration for the place render. |
| `Smoothness` | Number | 1 | 0 – 20 |  |  | The smoothness for the slide while place position is changing. |
| `RenderMode` | Mode | Both | None, Fill, Outline, Both |  |  | The rendering that will be applied to the target position. |
| `FillColorUp` | Color | ColorUtils.getDefaultFillColor() |  | renderMode = Fill, Both |  | The color that will be used for the fill gradiant upper part rendering. |
| `FillColorDown` | Color | ColorUtils.getDefaultFillColor() |  | renderMode = Fill, Both |  | The color that will be used for the fill gradiant lower part rendering. |
| `OutlineColorUp` | Color | ColorUtils.getDefaultOutlineColor() |  | renderMode = Outline, Both |  | The color that will be used for the outline gradiant upper part rendering. |
| `OutlineColorDown` | Color | ColorUtils.getDefaultOutlineColor() |  | renderMode = Outline, Both |  | The color that will be used for the outline gradiant lower part rendering. |
| `RenderDamage` | Boolean | false |  |  | Damage | Renders the damage that the position will do to the opponent. |
| `Icon` | Boolean | false |  |  |  | Renders a customizable crystal icon on the rendered position. |
| `IconScale` | Number | 3 | 1 – 5 | icon = true |  | The scaling that will be applied to the crystal icon rendering. |
| `IconRadius` | Number |  |  | icon = true |  | The difference between the outer circle and the inner circle. |
| `IconColor` | Color | ColorUtils.getDefaultColor() |  | icon = true |  | The color that will be used for the crystal icon rendering. |
| `Shader` | Mode | None |  |  |  | The animated shader that will be drawn on the target position instead of a flat color. |
| `ShaderSpeed` | Number |  |  | shader = SHADER_ACTIVE_MODES | Speed | The speed at which the shader animates. |
| `ShaderOpacity` | Number | 100 | 0 – 100 | shader = SHADER_ACTIVE_MODES | Opacity | The opacity of the shader rendering. |
| `ShaderDistanceScaling` | Boolean | false |  | shader = SHADER_ACTIVE_MODES | DistanceScaling | Scales the shader pattern by your distance to the position. |
| `ShaderStep` | Number |  |  | shader = Gradient | Step | The size of the gradient bands. |
| `ShaderColor1` | Color | ColorSetting.Color(Color(255, 0, 255, 255), false, false) |  | shader = Gradient |  | The first gradient color. |
| `ShaderColor2` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | shader = Gradient |  | The second gradient color. |
| `ShaderColor3` | Color | ColorSetting.Color(Color(0, 255, 0, 255), false, false) |  | shader = Gradient |  | The third gradient color. |
| `ShaderColor4` | Color | ColorSetting.Color(Color(0, 0, 255, 255), false, false) |  | shader = Gradient |  | The fourth gradient color. |
| `ShaderGlowColor` | Color | ColorSetting.Color(Color(255, 0, 255, 255), false, false) |  | shader = Glowing | GlowColor | The color that the glow shader will be tinted with. |

### AutoLog
Logs out automatically to avoid dying.

Plik: `night/modules/impl/combat/AutoLogModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `HealthCheck` | Boolean | false |  |  |  | Checks if you are at a specific health to log out. |
| `Health` | Number | 10 | 0 – 20 | healthCheck = true |  | The health the player must be at to log out. |
| `TotemCheck` | Boolean | true |  |  |  | Checks if you ran out of totems to be able to log out. |
| `Totems` | Number | 2 | 0 – 9 | totemCheck = true |  | The amount of totems to have in your inventory to log out. |
| `SelfDisable` | Boolean | true |  |  |  | Toggles off the module after logging out. |

### AutoMace
Elytra-boosts above the target, free-falls in, and mace-smashes on repeat.

Plik: `night/modules/impl/combat/AutoMaceModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number | 20.0 | 5.0 – 50.0 |  |  | Search radius to find a target. |
| `MinHeight` | Number | 15.0 | 3.0 – 30.0 |  |  | Vertical height above the target to reach before diving. |
| `AttackRange` | Number | 4.0 | 1.0 – 6.0 |  |  | Distance to the target that triggers the mace attack. |
| `Angle` | Number | 60.0 | 0.0 – 90.0 |  | Angle | Flight angle relative to the target: 0 = horizontal, 90 = straight vertical. |
| `SmartTerrain` | Boolean | true |  |  |  | Predicts terrain ahead during ascend/dive and steepens the boost to fly over/around it instead of Angle's fixed value. |
| `TerrainLookAhead` | Number | 6.0 | 2.0 – 15.0 |  |  | SmartTerrain: how many steps ahead along the boost line are checked for obstacles. |
| `TerrainClimbRate` | Number | 4.0 | 1.0 – 15.0 |  |  | SmartTerrain: pitch (degrees) added per tick while the line ahead is blocked. |
| `TerrainMaxClimb` | Number | 40.0 | 10.0 – 85.0 |  |  | SmartTerrain: max pitch lift before falling back to the escape sweep. |
| `FakeFly` | Boolean | false |  |  |  | Keeps real armor on and cycles the elytra in/out (GrimV3 trick) instead of wearing it the whole combo. Opt-in, reliability unverified. |
| `Swing` | Boolean | true |  |  |  | Whether to swing your hand when attacking. |
| `Swap` | Mode | Silent | None, Normal, Silent |  |  | Weapon switch mode. |
| `Render` | Boolean | true |  |  |  | Renders an indicator around the current target. |
| `RenderMode` | Mode | Circle | Box, Circle, Both | render = true |  | Target render mode. |
| `Entities` | Category |  |  |  |  | Target entity filter settings. |
| `Players` | Boolean | true |  |  |  | Target player entities. |
| `Friends` | Boolean | false |  |  |  | Target friends. |
| `Hostiles` | Boolean | false |  |  |  | Target hostile mobs. |
| `Neutrals` | Boolean | false |  |  |  | Target neutral mobs. |
| `Animals` | Boolean | false |  |  |  | Target passive animal mobs. |
| `TargetColor` | Color | ColorUtils.getDefaultFillColor() |  | render = true |  | Color of the target render box. |

### AutoMine
Manages which blocks SpeedMine should mine to defeat your opponents.

Plik: `night/modules/impl/combat/AutoMineModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Priority` | Mode | Leg | Leg, Head |  |  | Which area of the target to prioritize mining. |
| `Logic` | Mode | Grim | Grim, NCP |  |  | Auto mining targeting logic. |
| `Raytrace` | Boolean | false |  |  |  | Only mines blocks that are in line of sight (visible). |
| `CivBreak` | Boolean | false |  | raytrace = true |  | Priority=Leg only: when Raytrace can't see any leg-level surround block, mines the block above it instead. Turn on AutoCrystal's own MineIgnore setting for the crystal pre-place/detonate combo -- this module only builds+mines the obsidian. |
| `Rotate` | Mode | Grim | None, Normal, Grim | upFallback = true |  | The rotation mode when placing the Up-fallback obsidian. |
| `IgnoreNaked` | Boolean | false |  |  |  | Ignore Naked, even that player wears elytra only |
| `AvoidSharing` | Boolean | true |  |  |  | Avoids mining blocks that are part of your own surround or safety ring. |
| `Terrain` | Boolean | false |  |  |  | Automatically mines terrain blocks to place obsidian when target has no crystal base or is fluid-covered. |
| `TerrainPlace` | Boolean | true |  | terrain = true |  | Automatically places obsidian once the terrain block is broken. |
| `IronPickaxe` | Boolean | false |  |  |  | Uses iron pickaxe after obsidian matures to prevent obsidian item drops. |
| `AntiCrawl` | Boolean | true |  |  |  | While crawling, mines the block above/below your feet to stand back up instead of staying trapped. |
| `Face` | Boolean | true |  |  |  | Mines the block stuck in your face to rescue you from face traps or phased blocks. |

### AutoPot
Throws a splash potion under/above you to keep whitelisted potions' effects topped up.

Plik: `night/modules/impl/combat/AutoPotModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | WhiteList | WhiteList, BlackList |  |  | WhiteList = keep these potions' effects topped up. BlackList = keep every potion's effects topped up except these. |
| `Whitelist` | Whitelist | WhitelistSetting.Type.POTIONS |  |  |  | Potions this mode's WhiteList/BlackList compares against. |
| `Amplifier` | Number | 1 | 0 – 4 |  |  | Minimum amplifier level required before an effect counts as "already have it". |
| `Rotate` | Boolean | false |  |  |  | Rotates to look straight up/down before throwing (nami: real rotation, waits for it to land before throwing). |
| `Throw` | Mode | Under | Above, Under | rotate = true |  | Where the potion lands relative to you. |
| `Swap` | Mode | Silent | Normal, Silent |  |  | How the potion slot gets selected before throwing. |
| `NoTarget` | Boolean | false |  |  |  | Only throws while no enemy is nearby. |
| `OnlyPhased` | Boolean | false |  |  |  | Only throws while phased into a block. |
| `SelfToggle` | Boolean | true |  |  |  | Turns the module off once every whitelisted effect is already topped up. |
| `Range` | Number | 32 | 4 – 64 | whenNoTarget = true |  | How close an enemy has to be for NoTarget to see them. |

### AutoTotem
Automatically puts a specified item in your offhand slot.

Plik: `night/modules/impl/combat/AutoTotemModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Item` | Mode | Totem | Totem, Crystal, Gapple |  |  | The item that will be placed in your offhand slot when safety conditions are met. |
| `Health` | Number | 16 | 0 – 36 | item = Crystal, Gapple |  | The health at which a totem will be prioritized. |
| `ElytraCheck` | Boolean | true |  |  |  | Prioritizes a totem whenever you're wearing an elytra. |
| `FallDistance` | Number |  |  |  |  | The fall distance at which the module will prioritize a totem. |
| `UseGapple` | Boolean | true |  |  |  | Switches to a golden apple in your offhand based on main hand items when health is safe. |
| `Sword` | Boolean | true |  | useGapple = true |  | Switches to a gapple when holding a sword or axe in main hand. |
| `Pickaxe` | Boolean | true |  | useGapple = true |  | Switches to a gapple when holding a pickaxe in main hand. |
| `Totem` | Boolean | false |  | useGapple = true |  | Switches to a gapple when holding a totem in main hand. |
| `LethalOverride` | Boolean | false |  | useGapple = true |  | Overrides any necessity for a totem when gappling. |
| `NoTotemGap` | Boolean | false |  |  |  | Swap your gap into offhand when no totems left |
| `1.17+` | Boolean | true |  |  |  | 1.17+ uses the SWAP inventory action, while having it off uses inventory clicks. |
| `SmartMine` | Boolean | false |  | item = Crystal |  | Switches to a crystal whenever you start mining and a totem when you aren't mining. |
| `AntiMace` | Boolean | false |  |  |  | Switches to a totem if a player near you is trying to smash attack you with a mace. |
| `MaceRange` | Number |  |  | antiMace = true |  | The distance at which an enemy has to be in with a mace in order to swap to a totem. |
| `Debug` | Boolean | false |  |  |  | Logs why you died and why the totem swap failed at that moment. |

### AutoTrap
Automatically places blocks around you to prevent other people from getting inside your hole.

Plik: `night/modules/impl/combat/AutoTrapModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Mode` | Mode | Full | Partial, Full |  |  | The offsets that will be used when trapping. |
| `Head` | Boolean | true |  | mode = Full |  | Whether or not to cover the block on the players head. |
| `NoFeet` | Boolean | false |  |  |  | Skips the new floor-level ring around the target's feet entirely -- walls and head (if enabled) still trap as before. |
| `DisableAfterFeet` | Boolean | false |  | noFeet = false |  | Stops re-trapping the floor-level feet ring once it has successfully placed there at least once since the module was enabled -- walls/head keep trapping normally. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `BPT` | Number | 4 | 1 – 8 |  |  | The maximum number of blocks to place per tick. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `EnemyRange` | Number |  |  |  |  | The maximum distance at which the target should be at. |
| `Await` | Boolean | false |  |  |  | Waits for blocks to be registered by the client before placing on them. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing blocks. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `AirPlace` | Boolean | false |  |  |  | Places blocks directly in the air with no real neighbor needed, skipping the support-scaffold bootstrap entirely instead of only using it as a last resort. |
| `Attack` | Boolean | false |  |  |  | Attacks any crystal that interferes with your trap. |
| `AttackRotate` | Boolean | true |  | attack = true | Rotate | Rotates toward the crystal before attacking it. |
| `AttackRange` | Number |  |  | attack = true | Range | The maximum range at which crystals will be attacked. |
| `AttackAge` | Number | 5 | 0 – 20 | attack = true | Age | The minimum age (in ticks) a crystal must reach before being attacked. |
| `AttackMultiTask` | Boolean | true |  | attack = true | Multitask | Allows attacking while an item is already in use. |
| `AttackSwing` | Boolean | true |  | attack = true | Swing | Sends a swing packet whenever attacking a crystal. |
| `HoleCheck` | Boolean | true |  |  |  | Checks if the target is in a hole or not before placing. |
| `AntiStep` | Boolean | false |  |  |  | Adds additional blocks that prevent the player from stepping out of the hole. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `IgnoreNaked` | Boolean | false |  |  |  | Ignore Naked, even that player wears elytra only |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module whenever you run out of items to place with. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### AutoWeb
Automatically places webs on other people's feet.

Plik: `night/modules/impl/combat/AutoWebModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `Delay` | Number | 0 | 0 – 20 |  |  | The amount of ticks that have to be waited for between placements. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `EnemyRange` | Number |  |  |  |  | The maximum distance at which the target should be at. |
| `Extrapolation` | Number | 0 | 0 – 20 |  |  | Extrapolates the target's position to calculate positions ahead of time. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `AirPlace` | Boolean | false |  |  |  | Lets you place webs on air. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `HoleCheck` | Boolean | true |  |  |  | Checks if the target is in a hole or not before placing. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module whenever you run out of items to place with. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### BedAura
Automatically places and detonates beds in the Nether and End.

Plik: `night/modules/impl/combat/BedAuraModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Targeting` | Category |  |  |  |  | Settings related to target selection. |
| `Target` | Mode | Closest | Closest, Furthest, Health |  | Mode | How targets are sorted. |
| `TargetRange` | Number | 8.0 | 1.0 – 16.0 |  | TargetRange | The range within which players are considered targets. |
| `MaxTargets` | Number | 3.0 | 1.0 – 8.0 |  | MaxTargets | Maximum number of targets to evaluate simultaneously. |
| `BedRange` | Number | 4.0 | 1.0 – 8.0 |  | BedRange | Search cube radius around the target to look for placement spots. |
| `IgnoreFriends` | Boolean | true |  |  |  | Ignore friends when targeting. |
| `Damage` | Category |  |  |  |  | Damage calculation and safety settings. |
| `MinDamage` | Number | 6.0 | 0.0 – 36.0 |  | MinDamage | Minimum predicted damage to target. |
| `MaxSelfDamage` | Number | 6.0 | 0.0 – 36.0 |  | MaxSelfDamage | Maximum allowed damage to yourself. |
| `MinHealth` | Number | 4.0 | 0.0 – 36.0 |  | MinHealth | Do not act if your health (+absorption) is at or below this. |
| `AntiSuicide` | Boolean | true |  |  |  | Cancel if self damage drops health below MinHealth. |
| `Balance` | Number | 0.0 | 0.0 – 10.0 |  | Balance | Minimum target to self damage ratio (0 = off). |
| `Predict` | Boolean | true |  |  |  | Extrapolate target velocity for damage estimation. |
| `Behavior` | Category |  |  |  |  | Settings for placement and detonation behavior. |
| `Delay` | Number | 100 | 0 – 500 |  | Delay | Delay in milliseconds between actions. |
| `Range` | Number | 4.5 | 1.0 – 6.0 |  | Range | Direct reach for placing and detonating beds. |
| `WallsRange` | Number | 4.0 | 0.0 – 6.0 |  | WallsRange | Reach through walls / obstacles. |
| `SwapMode` | Mode | Silent | Silent, Normal, Alt |  | SwapMode | How to swap to the bed and non-bed item. |
| `Rotate` | Boolean | true |  |  |  | Rotate towards bed before placing and detonating. |
| `StrictDirection` | Boolean | false |  |  |  | Enforce strict raycast line of sight on placement face. |
| `AirPlace` | Boolean | false |  |  |  | Allow placing beds without a solid supporting block below. |
| `PlaceOnFeet` | Boolean | true |  |  |  | Allow bed head to overlap target entity hitbox for maximum damage. |
| `PauseOnEat` | Boolean | true |  |  |  | Pause while eating or consuming items. |
| `AutoCraft` | Category |  |  |  |  | Automated bed crafting from wool and planks. |
| `AutoCraft` | Boolean | true |  |  |  | Enable automated bed crafting. |
| `AutoCraftBind` | Bind |  | -1 |  |  | Keybind to toggle AutoCraft. |
| `AutoPlace` | Boolean | true |  |  |  | Place and open a crafting table when out of beds. |
| `AutoClose` | Boolean | true |  |  |  | Close the crafting table GUI automatically when crafting is finished. |
| `Render` | Category |  |  |  |  | Placement preview rendering. |
| `Render` | Boolean | true |  |  |  | Render preview box at target bed placement. |
| `Interpolate` | Boolean | true |  |  |  | Smooth linear grow-in animation for preview box. |
| `RenderMode` | Mode | Both | Fill, Outline, Both |  | Mode | Render mode for preview box. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  |  | FillColor | Color used for fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  |  | OutlineColor | Color used for outline rendering. |

### Blocker
Places blocks to stop enemies from placing crystals.

Plik: `night/modules/impl/combat/BlockerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `Limit` | Number | 4 | 1 – 20 |  |  | The number of blocks that can be placed per tick. |
| `Delay` | Number | 0 | 0 – 20 |  |  | The amount of ticks that have to be waited for between placements. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `CrystalDestruction` | Boolean | true |  |  |  | Destroys any crystals that interfere with block placement. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `Feet` | Boolean | true |  |  |  | Places on feet level blocks. |
| `Head` | Boolean | true |  |  |  | Places on head level blocks. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### CombatPlace
Automatically places blocks in front of moving targets to block them.

Plik: `night/modules/impl/combat/CombatPlace.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Flatten` | Boolean | true |  |  |  | Place blocks under the target. |
| `Mode` | Mode | Two | None, One, Two, Three |  |  | Prediction mode. |
| `Priority` | Mode | Closest | Closest, Health |  |  | Target selection priority. |
| `PredictTicks` | Number | 2 | 1 – 8 |  |  | Prediction scale. |
| `MinKMH` | Number | 20 | 1 – 40 |  |  | Min speed to trigger blocker. |
| `Limit` | Number | 4 | 1 – 20 |  |  | The maximum number of blocks that can be placed per tick. |
| `Delay` | Number | 0 | 0 – 20 |  |  | The delay in ticks between placements. |
| `Strict` | Boolean | true |  |  |  | Prevent placement if entities intersect. |
| `Safety` | Boolean | true |  |  |  | Prevent trapping yourself. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which blocks will be placed. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `CrystalDestruction` | Boolean | true |  |  |  | Destroys any crystals that interfere with block placement. |

### Criticals
Changes player movement for always landing critical hits.

Plik: `night/modules/impl/combat/CriticalsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Packet | Packet, Grim, GrimNew |  |  | The method that will be used to achieve critical hits. |
| `OnlyPhased` | Boolean | true |  | mode = Grim, GrimNew |  | Only crits when you are phased into blocks. |
| `OnlyStandingStill` | Boolean | true |  | mode = Grim, GrimNew |  | Only crits when you are standing still. |
| `HeadCovered` | Boolean | true |  | mode = Grim, GrimNew |  | Only crits when there is a block covering your head. |

### HoleFill
Automatically places blocks inside of holes to prevent others from getting inside of them.

Plik: `night/modules/impl/combat/HoleFillModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Mode` | Mode | Normal | Normal, Smart |  |  | The offsets that will be used when trapping. |
| `Webs` | Boolean | false |  |  |  | Use webs to holefill instead of blocks. |
| `SelfWeb` | Boolean | false |  |  |  | Includes your own hole when holefilling with webs. |
| `Refill` | Boolean | false |  |  |  | Fills the hole you just exited. |
| `WaitUntilExited` | Boolean | true |  | refill = true |  | Waits until your hitbox completely leaves the hole before refilling. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `BPT` | Number | 4 | 1 – 8 |  |  | The maximum number of blocks to place per tick. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `EnemyRange` | Number |  |  | mode = Smart |  | The maximum distance at which the target should be at. |
| `SmartRange` | Number |  |  | mode = Smart |  | The distance at which the holes will have to be away from the target. |
| `Safety` | Boolean | true |  |  |  | Prevents holes close to you from getting filled. |
| `SafetyRange` | Number |  |  | safety = true |  | The maximum distance the holes can be at to be prevented from getting filled. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `Attack` | Boolean | false |  |  |  | Attacks any crystal that interferes with hole filling. |
| `AttackRotate` | Boolean | true |  | attack = true | Rotate | Rotates toward the crystal before attacking it. |
| `AttackRange` | Number |  |  | attack = true | Range | The maximum range at which crystals will be attacked. |
| `AttackAge` | Number | 5 | 0 – 20 | attack = true | Age | The minimum age (in ticks) a crystal must reach before being attacked. |
| `AttackMultiTask` | Boolean | true |  | attack = true | Multitask | Allows attacking while an item is already in use. |
| `AttackSwing` | Boolean | true |  | attack = true | Swing | Sends a swing packet whenever attacking a crystal. |
| `DoubleHoles` | Boolean | false |  |  |  | Whether or not to fill double holes. |
| `HoleCheck` | Boolean | true |  |  |  | Checks if the target isn't in a hole before placing. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module whenever you run out of items to place with. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### KillAura
Automatically attacks optimal targets using Nami's combat engine.

Plik: `night/modules/impl/combat/KillAuraModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number | 3.0 | 1.0 – 6.0 |  |  | The reach distance to entities. |
| `StanceAbuse` | Boolean | false |  |  |  | Abuses Grim stance eye positions to optimize reach. |
| `Delay` | Number | 0.92 | 0.0 – 1.0 |  |  | Attack cooldown delay multiplier. |
| `Swap` | Mode | Require | None, Require, Normal, Silent |  |  | Weapon switch mode. |
| `OnlyGapHold` | Boolean | false |  | swap = Silent |  | Only attacks using silent swap when holding a golden apple in main hand. |
| `OnlyTotemHold` | Boolean | false |  | swap = Silent |  | Only attacks using silent swap when holding a totem in main hand. |
| `PauseOnEat` | Boolean | false |  |  |  | Pauses attacking while eating or using items. |
| `TPS` | Mode | Average | None, Average |  |  | TPS synchronization mode for attack cooldown. |
| `Multitask` | Boolean | true |  |  |  | Allows attacking while eating or using items. |
| `Sprinting` | Mode | None | None, Packet |  |  | Sprint management during attacks. |
| `Rotate` | Mode | Normal | None, Normal, Hold, Silent |  |  | Rotation mode. |
| `Swing` | Boolean | true |  |  |  | Whether to swing your hand when attacking. |
| `Render` | Boolean | true |  |  |  | Renders an indicator around the current target. |
| `RenderMode` | Mode | Circle | Box, Circle, Both | render = true |  | Target render mode. |
| `Entities` | Category |  |  |  |  | Target entity filter settings. |
| `Players` | Boolean | true |  |  |  | Target player entities. |
| `Friends` | Boolean | false |  |  |  | Target friends. |
| `IgnoreNaked` | Boolean | false |  |  |  | Ignore Naked, even that player wears elytra only |
| `Hostiles` | Boolean | false |  |  |  | Target hostile mobs (Zombies, Skeletons, Spiders, Piglins, etc.). |
| `Neutrals` | Boolean | false |  |  |  | Target neutral mobs (Endermen, Zombie Piglins, etc.). |
| `Animals` | Boolean | false |  |  |  | Target passive animal mobs. |
| `Projectiles` | Boolean | true |  |  |  | Target shulker bullets & fireballs. |
| `TargetColor` | Color | ColorUtils.getDefaultFillColor() |  | render = true |  | Color of the target render box. |

### KillSay
Automatically sends a message when you kill someone.

Plik: `night/modules/impl/combat/KillSayModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Kills` | Boolean | true |  |  |  | Sends a message when you kill someone. |
| `Pops` | Boolean | false |  |  |  | Sends a message when you pop someone. |
| `Mode` | Mode | Default | Default, Custom |  |  | The mode for the messages. |
| `KillFile` | String | killfile.txt |  |  |  | The name of the file containing the kill messages. |
| `PopFile` | String | popfile.txt |  |  |  | The name of the file containing the pop messages. |
| `Delay` | Number | 5 | 0 – 10 |  |  | The delay for the announcer. |
| `Clientside` | Boolean | false |  |  |  | Sends the messages only on your side. |
| `GreenText` | Boolean | false |  |  |  | Makes your message green. |
| `KillStreak` | Boolean | false |  |  |  | Keeps track of your kills. |
| `SuicideIgnore` | Boolean | false |  |  |  | Doesn't reset kill streak when you suicide. |

### MaceSpoof
Spoof fall height for mace damage.

Plik: `night/modules/impl/combat/MaceSpoofModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Vanilla | Vanilla, NCP, Swap |  |  | Mode of spoofing. |
| `NoCrystal` | Boolean | true |  | mode = Swap |  | Don't apply on crystal attack. |
| `InventorySwap` | Boolean | false |  | mode = Swap |  | Use inventory swap (vs hotbar swap). |
| `OnlyGround` | Boolean | true |  | mode = Vanilla |  | Only spoof when on ground or flying. |
| `Height` | Number | 25.0 | 1.0 – 2000.0 | mode = Vanilla |  | Fake fall height. |

### Mainhand
Automatically manages totems and golden apples in your mainhand.

Plik: `night/modules/impl/combat/MainhandModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Slot` | Number | 1 | 1 – 9 |  |  | The hotbar slot (1-9) to keep a totem in. |
| `Delay` | Number | 0 | 0 – 2000 |  |  | Delay (ms) before refilling a totem to hotbar. |
| `UseGapple` | Boolean | true |  |  |  | Switches to golden apple when right clicking while holding a totem in mainhand. |
| `LethalOverride` | Boolean | false |  | useGapple = true |  | Allows eating a golden apple regardless of health when no enemies are in visual range. |
| `Health` | Number | 16 | 0 – 36 |  |  | The health at which a totem will be prioritized. |
| `ElytraCheck` | Boolean | true |  |  |  | Prioritizes a totem whenever you're wearing an elytra. |
| `FallDistance` | Number |  |  |  |  | The fall distance at which the module will prioritize a totem. |
| `AntiMace` | Boolean | false |  |  |  | Switches to a totem if a player near you is trying to smash attack you with a mace. |
| `MaceRange` | Number |  |  | antiMace = true |  | The distance at which an enemy has to be in with a mace in order to swap to a totem. |

### ObsidianFarmer
Places and mines ender chests using SpeedMine.

Plik: `night/modules/impl/combat/ObsidianFarmerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing and mining blocks. |
| `StrictDirection` | Boolean | false |  |  |  | Only places and mines using directions that face you. |
| `AirPlace` | Boolean | false |  |  |  | Places blocks in the air without needing neighboring blocks. |
| `CrystalDestruction` | Boolean | true |  |  |  | Destroys any crystals that interfere with block placement. |
| `Range` | Number | 5.0 | 1.0 – 6.0 |  |  | The maximum range at which blocks will be placed. |
| `MineRange` | Number | 5.0 | 1.0 – 8.0 |  |  | The maximum range at which ender chests will be mined. |
| `WallsRange` | Number | 3.5 | 1.0 – 8.0 |  |  | The maximum range through walls for mining. |
| `Delay` | Number | 2 | 0 – 20 |  |  | The delay in ticks between placements. |
| `MineDelay` | Number | 1 | 0 – 20 |  |  | The delay in ticks before mining. |
| `RetryDelay` | Number | 1 | 1 – 20 |  |  | The delay in ticks before retrying a failed mine attempt. |
| `RequirePacketMine` | Boolean | true |  |  |  | Only mines when SpeedMine is enabled. |
| `Support` | Boolean | true |  |  |  | Places a supporting block under the ender chest if needed. |
| `Swing` | Boolean | true |  |  |  | Swings your hand when starting to break blocks. |
| `WhileEating` | Boolean | true |  |  |  | Places and mines blocks while eating. |
| `OnlyGround` | Boolean | false |  |  |  | Only works while on ground. |
| `Render` | Boolean | true |  |  |  | Whether or not to render place and mine positions. |

### OpponentScaffold
Automatically places blocks under non-friended players to prevent them from falling.

Plik: `night/modules/impl/combat/OpponentScaffold.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `Limit` | Number | 4 | 1 – 20 |  |  | The number of blocks that can be placed per tick. |
| `Delay` | Number | 0 | 0 – 20 |  |  | The amount of ticks that have to be waited for between placements. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which blocks will be placed at. |
| `EnemyRange` | Number |  |  |  |  | The maximum distance at which the target should be at. |
| `Extrapolation` | Number | 0 | 0 – 20 |  |  | Extrapolates the target's position to predict where they will move. |
| `Await` | Boolean | false |  |  |  | Waits for blocks to be registered by the client before placing on them. |
| `Rotate` | Boolean | true |  |  |  | Whether or not you should rotate when you place blocks. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `CrystalDestruction` | Boolean | true |  |  |  | Destroys any crystals that interfere with block placement. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module whenever you run out of items to place with. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### PearlCatcher
Places obsidian in the air to stop players pearls from reaching their destination.

Plik: `night/modules/impl/combat/PearlCatcher.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `EnemyRange` | Number |  |  |  |  | The maximum distance at which the target should be at. |
| `Distance` | Number |  |  |  |  | The distance at which the obisidan block will be placed from the player. |
| `HoleCheck` | Boolean | false |  |  |  | Only self traps whenever you are in a hole. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module whenever you run out of items to place with. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### PearlChase
Automatically throws an Ender Pearl to chase enemy pearl throws with trajectory prediction and obstacle avoidance.

Plik: `night/modules/impl/combat/PearlChaseModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Target` | Mode | Nearest | Lowest, Furthest, Nearest |  |  | Priority used to select which enemy to chase. |
| `IgnoreNaked` | Boolean | false |  |  |  | Ignore naked players, even if wearing only elytra. |
| `Rotate` | Mode | Silent | None, Normal, Silent |  |  | Rotation mode used before throwing the pearl. |
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to ender pearls. |
| `Offset` | Number | 0.0 | -10.0 – 10.0 |  |  | Distance offset relative to target landing position along flight path (positive = ahead, negative = behind). |
| `Distance` | Number | 10.0 | 0.0 – 50.0 |  |  | Minimum horizontal travel distance that enemy will move for pearl to be chased. |
| `ArcMode` | Mode | Smart | Smart, Low, High |  |  | Trajectory selection mode: Smart (low if clear, high if blocked), Low (fast direct), High (maximum lob). |
| `AvoidObstacles` | Boolean | true |  |  |  | Calculates alternate high arcs or yaw offsets if direct path is blocked by terrain. |
| `EnemyRange` | Number | 64.0 | 8.0 – 128.0 |  |  | Maximum range to enemy player throwing pearl. |
| `Delay` | Number | 0 | 0 – 20 |  |  | Delay in ticks before throwing the pearl. |
| `WhileEating` | Boolean | true |  |  |  | Allows throwing pearl while eating. |
| `ItemDisable` | Boolean | false |  |  |  | Toggles off module if no ender pearls are found in inventory. |
| `Render` | Boolean | true |  |  |  | Renders predicted landing position and pearl trajectory line. |
| `RenderTime` | Number | 3.0 | 0.5 – 10.0 |  |  | Duration in seconds to render trajectory after throwing. |

### PistonCrystal
Places piston + redstone to push a crystal into targets.

Plik: `night/modules/impl/combat/PistonCrystalModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `MinDamage` | Number |  |  |  |  | Minimum damage to attempt a sequence |
| `MaxLocalDamage` | Number |  |  |  |  | Max self-damage allowed |
| `Delay` | Number | 0 | 0 – 10 |  |  | Delay between place actions (ticks) |
| `Sort` | Mode | LowestDistance | LowestDistance, LowestHealth, CloseAngle |  |  | How to pick target |
| `TargetRange` | Number |  |  |  |  | Max target range |
| `InAirTarget` | Boolean | true |  |  |  | Target players in the air |
| `BreakCrystal` | Boolean | true |  |  |  | Attack crystal after placing it |
| `AutoBreak` | Boolean | true |  |  |  | Automatically breaks leftover redstone/piston with pickaxe after crystal explosion. |
| `PlaceRange` | Number |  |  |  |  | Range to place blocks |
| `BreakRange` | Number |  |  |  |  | Range to break crystals |
| `Rotate` | Mode | Silent | None, Normal, Silent |  |  | Silently rotate to placed blocks |
| `Grim` | Boolean | true |  |  |  | YawDeceive - filter dir by player facing & override piston yaw |
| `Swap` | Mode | Silent |  |  |  | How to swap to required items |
| `AirPlace` | Boolean | false |  |  |  | Allow placing when not on ground |
| `Safety` | Boolean | true |  |  |  | Skip if crystal would kill self |
| `BlockDestruction` | Boolean | false |  |  |  | Account for explosion block destruction |
| `SelfExtrapolate` | Boolean | false |  |  |  | Extrapolate self position for self-damage calc |
| `ExtrapolationTicks` | Number | 0 | 0 – 10 |  |  | Ticks to extrapolate enemy position |
| `AssumeBestArmor` | Boolean | false |  |  |  | Assume target has Prot 4 netherite |
| `AutoSupport` | Boolean | true |  |  |  | Automatically places obsidian support blocks when no platform exists. |
| `PauseOnEat` | Boolean | true |  |  |  | Pauses the module while eating or using an item. |
| `StrictDirection` | Boolean | false |  |  |  | Use raytrace to verify line-of-sight before placing piston/redstone (required on servers that check raytrace). |

### PistonKick
Pushes players out of holes or solid blocks using pistons.

Plik: `night/modules/impl/combat/PistonKickModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 1 | 0 – 10 |  |  | Delay between pushes (ticks) |
| `PushDelay` | Number | 150 | 50 – 500 |  |  | Duration to hold piston extended before retracting (ms) |
| `Power` | Mode | Redstone | Redstone, Lever |  |  | How the piston is powered. |
| `AirPlace` | Boolean | false |  |  |  | Allow placing when not on ground. |
| `1.12` | Boolean | true |  |  |  | Place piston and power in the same tick. |
| `TargetRange` | Number |  |  |  |  | Target range. |
| `InAirTarget` | Boolean | true |  |  |  | Target players in the air. |
| `Rotate` | Mode | Silent | None, Normal, Silent |  |  | Silently rotate when placing. |
| `BreakCrystal` | Boolean | false |  |  |  | Break end crystals blocking piston path. |
| `PlaceRange` | Number |  |  |  |  | Block place range. |
| `BreakRange` | Number |  |  | breakCrystal = true |  | Crystal break range. |
| `MaxLocalDamage` | Number |  |  | breakCrystal = true |  | Max self-damage allowed to break a crystal. |
| `Safety` | Boolean | true |  | breakCrystal = true |  | Never break a crystal that would kill you. |
| `Swap` | Mode | Silent |  |  |  | How to swap to required items. |
| `Consecutive` | Boolean | true |  |  |  | Keep targeting the same player consecutively. |
| `Merge` | Boolean | false |  |  |  | Burst piston and power packets together. |
| `PauseOnEat` | Boolean | false |  |  |  | Pause while eating or using items. |
| `StrictDirection` | Boolean | false |  |  |  | Use raytrace to verify line-of-sight before placing piston/redstone (required on servers that check raytrace). |
| `Render` | Boolean | true |  |  |  | Render placed blocks using RendersModule. |

### SelfBow
Automatically shoots arrows at you in order to give yourself potion effects.

Plik: `night/modules/impl/combat/SelfBowModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Manual` | Boolean | false |  |  |  | Whether or not to do the self bow manually. |
| `Switch` | Mode | Normal | None, Normal |  |  | The mode that will be used for switching slots. |
| `ChargeTime` | Number | 4 | 0 – 20 |  |  | The amount of ticks that the module will be charging the bow for. |
| `EffectCycle` | Boolean | false |  |  |  | Fires multiple arrows in case of having more than one arrow type. |

### SelfFill
Automatically places a block in the spot that you were previously in.

Plik: `night/modules/impl/combat/SelfFillModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `JumpMode` | Mode | Normal | Normal, Packet |  |  | The mode that will be used for jumping. |
| `Burrow` | Mode | None | None, Bypass | jumpMode = Packet |  | Teleports you inside of the block that was placed. |
| `ObsidianOnly` | Boolean | false |  |  |  | Only places using obsidian. |
| `Rotate` | Boolean | true |  |  |  | Whether or not to rotate when placing the block. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `CrystalDestruction` | Boolean | true |  |  |  | Whether or not to destroy crystals that obstruct the block's placement. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### SelfTrap
Automatically places blocks around you to prevent other people from getting inside your hole.

Plik: `night/modules/impl/combat/SelfTrapModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Mode` | Mode | Partial | Partial, Full |  |  | The offsets that will be used when trapping. |
| `Head` | Boolean | true |  | mode = Full |  | Whether or not to cover the block on the players head. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `BPT` | Number | 4 | 1 – 8 |  |  | The maximum number of blocks to place per tick. |
| `Await` | Boolean | false |  |  |  | Waits for blocks to be registered by the client before placing on them. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing blocks. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `AirPlace` | Boolean | false |  |  |  | Places blocks in the air without needing neighboring blocks. |
| `Attack` | Boolean | false |  |  |  | Attacks any crystal that interferes with your trap. |
| `AttackRotate` | Boolean | true |  | attack = true | Rotate | Rotates toward the crystal before attacking it. |
| `AttackRange` | Number |  |  | attack = true | Range | The maximum range at which crystals will be attacked. |
| `AttackAge` | Number | 5 | 0 – 20 | attack = true | Age | The minimum age (in ticks) a crystal must reach before being attacked. |
| `AttackMultiTask` | Boolean | true |  | attack = true | Multitask | Allows attacking while an item is already in use. |
| `AttackSwing` | Boolean | true |  | attack = true | Swing | Sends a swing packet whenever attacking a crystal. |
| `AntiStep` | Boolean | false |  |  |  | Adds additional blocks that prevent anyone from stepping out of the hole. |
| `AntiBomb` | Boolean | false |  |  |  | Places an extra block above your head to prevent you from getting bombed. |
| `HoleCheck` | Boolean | false |  |  |  | Only self traps whenever you are in a hole. |
| `AutoJump` | Mode | Grim | None, NCP, Grim |  |  | Automatically jumps to place high blocks when on-ground eye height is too low for StrictDirection. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

### SpearKill
Lunges at players and attacks with a spear/trident.

Plik: `night/modules/impl/combat/SpearKillModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number |  |  |  |  | Range to search for players. |
| `WallRange` | Number |  |  |  |  | Range to search through walls. |
| `HitRange` | Number |  |  |  |  | Range to send spear attacks. |
| `Delay` | Number | 100 | 0 – 1000 |  |  | Minimum delay in milliseconds between attacks. |
| `AttackDelay` | Boolean | true |  |  |  | Uses vanilla attack cooldown before attacking. |
| `AttackSpeed` | Number |  |  | attackDelay = false |  | Attacks per second when AttackDelay is off. |
| `AutoSwap` | Mode | Normal | Off, Normal, Silent |  |  | Swaps to a spear before attacking. |
| `Rotate` | Boolean | true |  |  |  | Rotates to the target before lunging and attacking. |
| `Lunge` | Boolean | true |  |  |  | Pushes you toward the target. |
| `LungeSpeed` | Number |  |  | lunge = true |  | Horizontal lunge speed. |
| `LungeY` | Number |  |  | lunge = true |  | Upward velocity added while lunging. |
| `StopDistance` | Number |  |  | lunge = true |  | Stops lunging inside this distance. |
| `LungeDelay` | Number | 150 | 0 – 1000 | lunge = true |  | Delay in milliseconds between lunges. |
| `Swing` | Boolean | true |  |  |  | Swings the hand after attacking. |
| `Render` | Boolean | true |  |  |  | Renders the current SpearKill target. |
| `RenderLine` | Boolean | true |  | render = true |  | Renders a line to the current target. |
| `FillColor` | Color | ColorSetting.Color(Color(255, 210, 90, 45), false, false) |  | render = true |  | Fill color for the target render. |
| `LineColor` | Color | ColorSetting.Color(Color(255, 210, 90, 180), false, false) |  | render = true |  | Outline and line color for the target render. |

### Suicide
Makes all of the combat modules target you so that you die.

Plik: `night/modules/impl/combat/SuicideModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `OffhandOverride` | Boolean | true |  |  |  | Changes the target offhand item in order to make it easier to die. |
| `DeathDisable` | Boolean | true |  |  |  | Disables the module once you have died. |
| `LoginDisable` | Boolean | true |  |  |  | Disables the module when you logged in. |

### Surround
Automatically places blocks at your feet to prevent crystal damage.

Plik: `night/modules/impl/combat/SurroundModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Timing` | Mode | Sequential | Vanilla, Sequential |  |  | The timing that will be used in replacing broken surround blocks. |
| `BPT` | Number | 4 | 1 – 8 |  |  | The maximum number of blocks to place per tick. |
| `Range` | Number | 5.0 | 0.0 – 12.0 |  |  | The maximum range at which the blocks will be placed at. |
| `Await` | Boolean | false |  |  |  | Waits for blocks to be registered by the client before placing on them. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing surround blocks. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `AirPlace` | Boolean | false |  |  |  | Places blocks in the air without needing neighboring blocks. |
| `Attack` | Boolean | false |  |  |  | Attacks any crystal that interferes with your surround. |
| `AttackRotate` | Boolean | true |  | attack = true | Rotate | Rotates toward the crystal before attacking it. |
| `AttackRange` | Number |  |  | attack = true | Range | The maximum range at which crystals will be attacked. |
| `AttackAge` | Number | 5 | 0 – 20 | attack = true | Age | The minimum age (in ticks) a crystal must reach before being attacked. |
| `AttackMultiTask` | Boolean | true |  | attack = true | Multitask | Allows attacking while an item is already in use. |
| `AttackSwing` | Boolean | true |  | attack = true | Swing | Sends a swing packet whenever attacking a crystal. |
| `Center` | Boolean | false |  |  |  | Puts you in the center of the block when you surround. |
| `Floor` | Boolean | true |  |  |  | Places blocks under your feet as well. |
| `UnderFeet` | Boolean | true |  | floor = true |  | Replaces the block directly under your feet when it breaks. |
| `Extension` | Boolean | true |  |  |  | Extends the surround if there are entities obstructing block placement. |
| `Blocker` | Boolean | true |  |  |  | Pre-places extra blocks where an enemy is digging toward you. |
| `Up` | Boolean | true |  | blocker = true |  | Blocker: places above/below the edge being mined through. |
| `Corner` | Boolean | true |  | blocker = true |  | Blocker: places the 2 corner cells left/right of the edge being mined through. |
| `Edge` | Boolean | true |  | blocker = true |  | Blocker: places the cell straight out in front of the edge being mined through. |
| `AdvoidHelp` | Boolean | true |  |  |  | Skips placements that would also wall in a nearby enemy. |
| `AdvoidHelpHealth` | Number |  |  | advoidHelp = true |  | Below this health, place every block regardless -- ignores AdvoidHelp entirely. |
| `WhileEating` | Boolean | true |  |  |  | Places blocks normally while eating. |
| `Advanced` | Boolean | false |  | whileEating = false |  | While WhileEating is off, keeps placing through an eat pause as long as your surround still has an open gap (stops once sealed, or if you're stuck inside a block). |
| `CenterOnTP` | Boolean | true |  |  |  | Centers you if you have just teleported to surround against crystals easier. |
| `Predict` | Boolean | true |  |  |  | Replaces a surround block instantly the moment we see the server's own break packet for it, instead of waiting for the next normal placement cycle. |
| `SelfDisable` | Boolean | false |  |  |  | Toggles off the module once it is finished with placing. |
| `ItemDisable` | Boolean | true |  |  |  | Toggles off the module when no valid items are found in your hotbar/inventory. |
| `JumpDisable` | Boolean | true |  |  |  | Toggles off the module whenever your Y level changes. |
| `PearlDisable` | Boolean | false |  |  |  | Toggles off surround whenever you throw or land an ender pearl. |
| `ChorusDisable` | Boolean | false |  |  |  | Toggles off surround whenever you eat a chorus fruit. |
| `RubberbandDisable` | Boolean | false |  |  |  | Toggles off surround whenever you get rubberbanded/setback by the server. |
| `StepToggle` | Boolean | false |  |  |  | Toggles the step module when you surround. |
| `SpeedToggle` | Boolean | false |  |  |  | Toggles the speed module when you surround. |
| `TickShiftToggle` | Boolean | false |  |  |  | Toggles the TickShift module when you surround. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |

## Kategoria: CORE (17 modułów)

### Capes
Applies the Night cape to yourself and to other users.

Plik: `night/modules/impl/core/CapesModule.java`

_Moduł nie posiada własnych ustawień._

### ClickGui
Allows you to change and interact with the client's modules and settings through a GUI.

Plik: `night/modules/impl/core/ClickGuiModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Sounds` | Boolean | true |  |  |  | Plays Minecraft UI sounds when interacting with the client's GUI. |
| `Blur` | Boolean | true |  |  |  | Whether or not to blur the background behind the GUI. |
| `ScrollSpeed` | Number | 1 | 1 – 10 |  |  | The speed at which the scrolling of the frames will be at. |
| `Scale` | Number |  |  |  | Scale | The overall size of the ClickGUI. Applies next time you open the GUI. |
| `Color` | Color | ColorSetting.Color(Color(160, 120, 255), false, false) |  |  |  | The color that will be used in the GUI. |
| `FillMode` | Mode | Default |  |  |  | How a toggled module's row background fills. |
| `NeekeriSpeed` | Number |  |  | fillMode = SHADER_FILL_MODES | Speed | The speed at which the fill animates. |
| `NeekeriOpacity` | Number | 90 | 0 – 100 | fillMode = SHADER_FILL_MODES | Opacity | The opacity of the fill. |
| `Background` | Mode | Aurora |  |  |  | Shader effect rendered behind the GUI. |
| `BgSpeed` | Number |  |  | background = ACTIVE_BG_MODES | Speed | The speed at which the background shader animates. |
| `BgOpacity` | Number | 65 | 0 – 100 | background = ACTIVE_BG_MODES | Opacity | The opacity of the background shader. |
| `GlowAccent` | Boolean | true |  |  |  | Renders glowing accent lines and header highlights. |
| `CategoryIcons` | Boolean | true |  |  |  | Displays modern icons in frame headers. |
| `Triangles` | Boolean | false |  |  |  | Displays triangle collapse/expand indicators for modules and categories. |
| `Switches` | Boolean | false |  |  |  | Displays toggle switch buttons for modules and settings. |

### Color
Manages the client's global color system.

Plik: `night/modules/impl/core/ColorModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Color` | Color | ColorSetting.Color(Color(160, 120, 255), false, false) |  |  |  | The global color that is used in sync and in most of the elements. |
| `Rainbow` | Category |  |  |  |  | The category containing all settings related to rainbow coloring. |
| `RainbowSpeed` | Number |  |  |  | Speed | The speed that rainbow colors will be cycling at. |
| `RainbowSaturation` | Number |  |  |  | Saturation | The saturation value of the rainbow color. |
| `RainbowBrightness` | Number |  |  |  | Brightness | The brightness value of the rainbow color. |
| `RainbowScale` | Number |  |  |  | Scale | The scale / density of the rainbow bands. |

### Commands
Manages the client's command and chat message system.

Plik: `night/modules/impl/core/CommandsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Watermark` | Category |  |  |  |  | The category that contains all of the settings for the chat watermark. |
| `Watermark` | Boolean | true |  |  | Enabled | Whether or not to have the client's watermark show up before every chat message. |
| `WatermarkText` | String | Night |  |  | Text | The text that will be shown as the watermark. |
| `Opening` | String | [ |  |  |  | The symbol that will be placed before the watermark text. |
| `Closing` | String | ] |  |  |  | The symbol that will be placed after the watermark text. |
| `PrimaryWatermarkColor` | Mode | Client |  |  | Primary | The primary color for the watermark. |
| `SecondaryWatermarkColor` | Mode | Dark Gray |  |  | Secondary | The secondary color for the watermark. |
| `Message` | Category |  |  |  |  | The category that contains all of the settings for the chat messages themselves. |
| `PrimaryMessageColor` | Mode | Client |  |  | Primary | The primary color for the message. |
| `SecondaryMessageColor` | Mode | White |  |  | Secondary | The secondary color for the message. |

### Font
Manages the client and the game's font rendering.

Plik: `night/modules/impl/core/FontModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `CustomFont` | Category |  |  |  |  | The category for settings relating to custom fonts. |
| `CustomFont` | Boolean | false |  |  | Enabled | Enables custom font rendering. |
| `Name` | String | Verdana |  |  |  | The name of the font that will be rendered. |
| `Size` | Number | 18 | 8 – 48 |  |  | The size of the custom font that will be rendered. |
| `Style` | Mode | Plain | Plain, Bold, Italic, BoldItalic |  |  | The style that will be used in the font's rendering. |
| `Global` | Boolean | false |  |  |  | Applies the custom font rendering on every part of the game. |
| `Offsets` | Category |  |  |  |  | Allows you to offset the custom font rendering to make it render perfectly. |
| `XOffset` | Number | 0 | -10 – 10 |  |  | The offset that will be applied to the font on the X axis. |
| `YOffset` | Number | 0 | -10 – 10 |  |  | The offset that will be applied to the font on the Y axis. |
| `WidthOffset` | Number | 0 | -10 – 10 |  |  | The offset that will be applied to the font on the X axis. |
| `HeightOffset` | Number | 0 | -10 – 10 |  |  | The font's offset on the Y axis. |
| `Shadows` | Category |  |  |  |  | The category for settings related to font shadows. |
| `ShadowMode` | Mode | Default | None, Default, Custom |  | Mode | The way that the shadow will be rendered. |
| `ShadowOffset` | Number |  |  | shadowMode = Custom | Offset | The distance of the shadow from the text being rendered. |
| `Glow` | Boolean | false |  |  |  | Renders a glow shader effect for HUD text only. |

### Friend
Allows you to manage the client's friend system.

Plik: `night/modules/impl/core/FriendModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `MiddleClick` | Boolean | true |  |  |  | Adds whichever entity you middle click to your friends list. |
| `FriendlyFire` | Boolean | false |  |  |  | Disables the friend system and allows the attacking of friends. |
| `FriendMessage` | Boolean | false |  |  |  | Sends a message to a player whenever you add them to your friends list. |
| `Content` | String | /msg $player I have just added you to my friends list! |  | friendMessage = true |  | The message that will be sent to the people you add. $player is placeholder for their name. |

### HUD
Renders information about the game and the client on the screen.

Plik: `night/modules/impl/core/HUDModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Watermark` | Category |  |  |  |  | The settings for the client's watermark. |
| `Watermark` | Boolean | true |  |  | Enabled | Renders the client's name and version at the top left. |
| `WatermarkText` | String | Night |  |  | Component | The client name that will be rendered. |
| `WatermarkLogo` | Boolean | true |  |  | Logo | Renders the client's logo before the watermark text, scaled to the text's own line height. |
| `WatermarkVersion` | Boolean | true |  |  | Version | Renders the client's version after the name. |
| `WatermarkMinecraftVersion` | Boolean | false |  |  | MinecraftVersion | Renders the client's minecraft version after the version. |
| `WatermarkVersionMode` | Mode | GitHash | GitHash, Nightly | watermarkVersion = true | Revision | What to render next to the version: the git revision/hash, or a nightly-build date stamp. |
| `WatermarkSync` | Boolean | false |  |  | ColorSync | Uses the client's color for the version. |
| `Welcomer` | Category |  |  |  |  | The settings for the client's welcomer. |
| `Welcomer` | Boolean | true |  |  | Enabled | Renders a nice welcome message directed to you. |
| `WelcomerText` | String | Decompiled By \u00a7f@piolunson |  |  | Component | The message that will be rendered. |
| `WelcomerSync` | Boolean | false |  |  | ColorSync | Uses the client's color for the username. |
| `ModuleList` | Category |  |  |  |  | The settings for the client's list of enabled modules. |
| `ModuleList` | Boolean | true |  |  | Enabled | Renders every enabled module in an organized list. |
| `ModuleListMode` | Mode | Pro | Pro, Newbie |  | Mode | The display mode for the module list. |
| `MetaData` | Boolean | true |  |  |  | Whether or not to show module metadata in the module list. |
| `BindOnly` | Boolean | false |  |  |  | Only lists modules that have a keybind set. |
| `ModuleColor` | Mode | Default | Default, Rainbow, Random |  | Color | The color mode for the modules on the list. |
| `ModuleListSorting` | Mode | Width | Width, Alphabetical |  | Sorting | The sorting for the modules on the list. |
| `Player Radar` | Category |  |  |  |  | The settings for the client's list of players in render distance. |
| `PlayerRadar` | Boolean | true |  |  | Enabled | Renders the name of every player in render distance. |
| `PlayerRadarLimit` | Number | 8 | 0 – 100 |  | Limit | The maximum amount of players that will be listed. Setting it to 0 means removing the limiter. |
| `PlayerRadarSorting` | Mode | Distance | None, Distance, Alphabetical |  | Sorting | The sorting for the players on the radar. |
| `PlayerRadarAntiBot` | Boolean | true |  |  | AntiBot | Prevents bots from being listed on the radar. |
| `PlayerIcons` | Boolean | true |  |  | Icons | Renders the player's head icon next to their name. |
| `PlayerRadarDistance` | Boolean | true |  |  | Distance | Renders the distance between you and the player. |
| `PlayerRadarEntityID` | Boolean | false |  |  | EntityID | Renders the player's entity ID. |
| `PlayerRadarGameMode` | Boolean | true |  |  | GameType | Renders the player's current gamemode. |
| `PlayerRadarPing` | Boolean | true |  |  | Ping | Renders the player's current latency. |
| `PlayerRadarHealth` | Boolean | true |  |  | Health | Renders the player's current health. |
| `PlayerRadarTotems` | Boolean | true |  |  | Totems | Renders the amount of totems that the player has popped. |
| `PlayerRadarTurtlePot` | Boolean | true |  |  | TurtlePotIndicator | Indicates if the player has Resistance 3 or higher. |
| `Items` | Category |  |  |  |  | The settings for information about items in your inventory and specific item counters. |
| `Armor` | Boolean | true |  |  |  | Renders the armor you're currently wearing and its status. |
| `ArmorDurability` | Mode | Both | None, Bar, Percentage, Both | armor = true | Durability | The way that the durability will be rendered in. |
| `TotemCounter` | Boolean | true |  |  |  | Renders the amount of totems that you have in your inventory. |
| `CrystalCounter` | Boolean | true |  |  |  | Renders the amount of crystals that you have in your inventory. |
| `XPCounter` | Boolean | true |  |  |  | Renders the amount of totems that you have in your inventory. |
| `CounterChatOffset` | Boolean | false |  |  | ChatOffset | Offsets the crystal and XP counter's positions whenever the chat is open. |
| `ItemsSmooth` | Boolean | true |  |  | Smooth | Smoothly animates item counts when they change (sliding/lerping). |
| `Inventory` | Category |  |  |  |  | The settings for displaying the inventory on the HUD. |
| `Inventory` | Boolean | false |  |  | Enabled | Renders your inventory (excluding hotbar) on the screen even when closed. |
| `InventoryBackground` | Boolean | true |  |  | Background | Renders a dark background behind the inventory grid. |
| `InventorySlotBackground` | Boolean | true |  |  | SlotBoxes | Renders subtle slot boxes for each inventory slot. |
| `InventoryAlpha` | Number | 140.0 | 0.0 – 255.0 |  | Alpha | Background opacity (0-255). |
| `InventoryPosition` | Position |  |  |  |  | Drag offset for the inventory HUD element. |
| `Information` | Category |  |  |  |  | The settings for information about the game and the client. |
| `Health` | Boolean | false |  |  |  | Renders your current health in the middle of the screen. |
| `Ping` | Boolean | true |  |  |  | Renders your current latency to the server in milliseconds. |
| `TPS` | Boolean | true |  |  |  | Renders the server's current tick-rate. |
| `FPS` | Boolean | true |  |  |  | Renders the game's frames per second counter. |
| `Durability` | Boolean | true |  |  |  | Renders your held item durability. |
| `Speed` | Mode | Kilometers | None, Meters, Kilometers |  |  | Renders the speed that you are currently moving at. |
| `Uptime` | Boolean | false |  |  |  | Renders the uptime of the client. |
| `ServerBrand` | Boolean | false |  |  |  | Renders the brand of the server that you are currently on. |
| `InformationSync` | Boolean | true |  |  | ColorSync | Uses the client's color for the information elements. |
| `InformationChatOffset` | Boolean | true |  |  | ChatOffset | Offsets the rendering when the chat is open. |
| `Potions` | Category |  |  |  |  | The settings for information about potion effects and their status. |
| `Potions` | Boolean | true |  |  | Enabled | Renders the name and status of every potion effect you have. |
| `PotionIcons` | Boolean | true |  |  | Icons | Whether or not to render the icons next to the potion's name. |
| `PotionColor` | Mode | Enhanced | Vanilla, Enhanced, Client |  | Color | The color that will be used in rendering the potion's text. |
| `PotionSorting` | Mode | Alphabetical | None, Width, Alphabetical |  | Sorting | The sorting for the potion effects rendered. |
| `VanillaPotions` | Mode | Hide | Keep, Move, Hide |  |  | The way that the vanilla potion icons will be handled. |
| `Position` | Category |  |  |  |  | The settings for information about your current position and velocity. |
| `Coordinates` | Boolean | true |  |  |  | Renders your current coordinates. |
| `NetherCoordinates` | Boolean | true |  | coordinates = true |  | Renders your current coordinates in the alternate dimension. |
| `FakeCoords` | Boolean | false |  |  |  | Fakes your coordinates to prevent leaking. |
| `FakeThreshold` | Number | 1000 | 0 – 100000 | fakeCoords = true | Threshold | Minimum coordinate value required to start faking. |
| `Direction` | Boolean | true |  |  |  | Renders the current direction that you are facing. |
| `PositionSync` | Boolean | true |  |  | ColorSync | Uses the client's color for the position elements. |
| `PositionChatOffset` | Boolean | true |  |  | ChatOffset | Offsets the text when the chat is open. |
| `Color` | Category |  |  |  |  | The settings for the coloring of the text. |
| `Color` | Mode | Default | Default, Rainbow, Wave, Custom |  |  | The color that will be applied to the text. |
| `CustomColor` | Color | ColorUtils.getDefaultColor() |  | colorMode = Custom |  | The color that will be used for the Custom mode. |
| `Rainbow` | Mode | Vertical | Vertical, Horizontal | colorMode = Rainbow |  | The mode for the HUD Rainbow. |
| `RainbowOffset` | Number |  |  | colorMode = Rainbow, Wave | Offset | The offset that will be applied to the rainbow. |
| `Inversion` | Boolean | false |  |  |  | Inverts primary and secondary colors. |
| `TextGlow` | Boolean | false |  |  | Glow | Renders a bloom glow effect for HUD text. |
| `InformationElement` | Boolean | true |  |  |  | Whether the ping/fps/tps/etc information block is shown at all. See HUDEditor. |
| `WatermarkPosition` | Position |  |  |  |  | Drag offset for the watermark HUD element. |
| `WelcomerPosition` | Position |  |  |  |  | Drag offset for the welcomer HUD element. |
| `ModuleListPosition` | Position |  |  |  |  | Drag offset for the module list HUD element. |
| `PlayerRadarPosition` | Position |  |  |  |  | Drag offset for the player radar HUD element. |
| `ArmorPosition` | Position |  |  |  |  | Drag offset for the armor HUD element. |
| `TotemCounterPosition` | Position |  |  |  |  | Drag offset for the totem counter HUD element. |
| `CrystalCounterPosition` | Position |  |  |  |  | Drag offset for the crystal counter HUD element. |
| `XPCounterPosition` | Position |  |  |  |  | Drag offset for the XP counter HUD element. |
| `InformationPosition` | Position |  |  |  |  | Drag offset for the information HUD element. |
| `CoordinatesPosition` | Position |  |  |  |  | Drag offset for the coordinates HUD element. |
| `PotionsPosition` | Position |  |  |  |  | Drag offset for the potions HUD element. |
| `MusicHUDPosition` | Position |  |  |  |  | Drag offset for the Music HUD element. |
| `MusicHUD` | Category |  |  |  |  | Settings for the Music HUD. |
| `MusicHUD` | Boolean | true |  |  | Enabled | Toggle Music HUD display. |
| `MusicHudBarAlpha` | Number | 160.0 | 0.0 – 255.0 |  | BarAlpha | Transparency of the visualizer bars (0-255). |
| `MusicHudGradientBars` | Boolean | true |  |  | GradientBars | Gradient colored visualizer bars. |
| `MusicHudBackground` | Mode | LiquidGlass | LiquidGlass, Blur, Default |  | Background | Background style of the HUD. |
| `MusicHudBlurIntensity` | Number | 3.0 | 0.0 – 50.0 |  | BlurIntensity | Intensity of the blur effect. |
| `MusicHudCompactMode` | Boolean | false |  |  | CompactMode | Collapse the HUD, limiting its width. |
| `MusicHudDisk` | Boolean | true |  |  | Disk | Enable to show the spinning record. |
| `MusicHudUltraDisk` | Boolean | false |  |  | UltraDisk | Only display the record, hide everything else. |
| `MusicHudDiskSize` | Number | 100.0 | 30.0 – 300.0 |  | DiskSize | Size of the music disk (Ultra Disk). |
| `MusicHudTextBloom` | Boolean | true |  |  | TextBloom | Text bloom glow effect. |
| `MusicHudTextBloomPlus` | Boolean | true |  |  | TextBloomPlus | Enhanced Skia text bloom glow. |
| `MusicHudColor` | Color | ColorUtils.getDefaultColor() |  |  | Color | Accent color used for the visualizer. |
| `MusicHudButtonEffect` | Mode | Gradient | Splash, Topo, Gradient |  | ButtonEffect | Visual effect when clicking buttons. |

### HUDEditor
Lets you drag-and-drop and toggle individual HUD elements directly on screen.

Plik: `night/modules/impl/core/HUDEditorModule.java`

_Moduł nie posiada własnych ustawień._

### IRC
Internet Relay Chat with other Night Client users.

Plik: `night/modules/impl/core/IRCModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Prefix` | String | $ |  |  |  | The prefix character for IRC messages in chat. |
| `ShowPrefix` | Boolean | true |  |  |  | Show [Night] tag on IRC messages. |
| `PrimaryColor` | Mode | Client |  |  |  | Color of username and tag. |
| `SecondaryColor` | Mode | White |  |  |  | Color of IRC message text. |
| `NotifyConnect` | Boolean | false |  |  |  | Show connection status notifications in chat. |

### IgnoreNaked
Ignore Naked, even that player wears elytra only

Plik: `night/modules/impl/core/IgnoreNakedModule.java`

_Moduł nie posiada własnych ustawień._

### Menu
Replaces the default title screen with the client's custom main menu screen.

Plik: `night/modules/impl/core/MenuModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `MainMenu` | Category |  |  |  |  | The category for settings related to the main menu. |
| `MainMenu` | Boolean | true |  |  | Enabled | Replaces Minecraft's default main menu with a customizable one. |

### NoMiddleClick
Prevents middle clicking from picking blocks into your hand.

Plik: `night/modules/impl/core/NoMiddleClickModule.java`

_Moduł nie posiada własnych ustawień._

### Patch
Server/anticheat-specific hotfixes.

Plik: `night/modules/impl/core/PatchModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `GrimAttackVelocity` | Boolean | false |  |  |  | Stops your own sprint and movement from being cut after landing extra knockback on a hit. |
| `SilentSwapFix` | Boolean | true |  |  |  | Stops hotbar slot desync and ghost item duplication during silent switch. |

### PingBypass
Connects to a PingBypass proxy server for low-latency combat.

Plik: `night/modules/impl/core/PingBypassModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `IP` | String | 127.0.0.1 |  |  |  | The IP address of the PingBypass proxy server. |
| `Port` | Number | 25565 | 1 – 65535 |  |  | The port of the PingBypass proxy server. |
| `Password` | String |  |  |  |  | The password for the PingBypass proxy server. |
| `Server` | String |  |  |  |  | The target Minecraft server to join through the proxy (e.g. mc.hypixel.net or mc.hypixel.net:25565). |

### RPC
Enables Discord Rich Presence for the client.

Plik: `night/modules/impl/core/RPCModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `UUID` | Boolean | true |  |  |  | Whether or not to show your client UID on the Discord presence. |
| `Details` | Mode | Random | Custom, Random |  |  | The mode for the discord presence details. |
| `CustomDetails` | String | Beyond the Gotham's darkness. |  | detailsMode = Custom |  | Custom RPC text. |
| `Image` | Mode | Image | Logo, Image, Animated |  |  | Which large image to show on the Rich Presence card. |
| `AnimatedDelay` | Number | 1500 | 0 – 10000 |  |  | Milliseconds between each frame swap. |

### Renders
Manages the client world renders.

Plik: `night/modules/impl/core/RendersModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Fade | Fade, Shrink |  |  | The mode for the place render. |
| `Duration` | Number | 300 | 0 – 1000 |  |  | The duration for the place render. |
| `RenderMode` | Mode | Both | Fill, Outline, Both |  |  | The rendering that will be applied to the blocks highlighted. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | renderMode = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | renderMode = Outline, Both |  | The color used for the outline rendering. |

### Rotations
Manages the client's rotation system.

Plik: `night/modules/impl/core/RotationsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `MovementFix` | Boolean | false |  |  |  | Makes your movement in accordance with your yaw. |
| `SnapBack` | Boolean | false |  |  |  | Reverts rotations to previous values after rotating. |
| `Jitter` | Mode | None | None, Grim, Normal |  |  | Adds random noise to sent rotations so they're never bit-for-bit identical, defeating aim-consistency checks like Grim's AimModulo360. |

## Kategoria: MISCELLANEOUS (22 modułów)

### Announcer
Announces your actions in chat.

Plik: `night/modules/impl/miscellaneous/AnnouncerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Watermark` | String | Night |  |  |  | The client name that will be used in the announcer. |
| `Language` | Mode | English | English, German, French, Japanese, Finnish, Russian, Spanish, Swedish, Turkish, Dutch, Greek, Chinese, Italian, Norwegian, Romanian, Czech, Portuguese, Slovenian, Polish, Korean, Lithuanian, Indonesian, Hungarian, Random |  |  | The language that will be used for the announcer. |
| `Delay` | Number | 5 | 0 – 30 |  |  | The delay for the announcer. |
| `Clientside` | Boolean | false |  |  |  | Sends the messages only on your side. |
| `GreenText` | Boolean | false |  |  |  | Makes your message green. |
| `Distance` | Boolean | true |  |  |  | Announces your distance travelled in chat. |
| `BlocksMined` | Boolean | true |  |  |  | Announces blocks mined in chat. |
| `BlocksPlaced` | Boolean | true |  |  |  | Announces blocks placed in chat. |
| `Eating` | Boolean | true |  |  |  | Announces when you eat in chat. |

### AutoEscape
Eats a chorus fruit to escape as soon as you get trapped on all sides.

Plik: `night/modules/impl/miscellaneous/AutoEscapeModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `SwapBack` | Boolean | true |  |  |  | Switches back to the item you were previously holding after eating the chorus fruit. |
| `Cooldown` | Number | 1000 | 0 – 5000 |  |  | The amount of time that has to pass before escaping again. |
| `AutoDisable` | Boolean | false |  |  |  | Disables the module after it escapes once. |

### AutoLogin
Automatically logs in on cracked servers.

Plik: `night/modules/impl/miscellaneous/AutoLoginModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Password` | String | password |  |  |  | The password to use when logging in. |

### AutoReconnect
Automatically reconnects you to a server after a specified time period.

Plik: `night/modules/impl/miscellaneous/AutoReconnectModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 5 | 0 – 20 |  |  | The amount of seconds that have to pass before reconnecting. |

### AutoRespawn
Makes it so that you respawn immediately after dying.

Plik: `night/modules/impl/miscellaneous/AutoRespawnModule.java`

_Moduł nie posiada własnych ustawień._

### BetterChat
Improves the default minecraft chat.

Plik: `night/modules/impl/miscellaneous/BetterChatModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `NoIndicators` | Boolean | false |  |  |  | Removes indicators from the chat. |
| `Offset` | Number | 0 | -100 – 100 |  |  | The offset for the chat on the X axis. |
| `Animation` | Boolean | false |  |  |  | Adds in an animation when adding new messages. |
| `Delay` | Number | 200 | 0 – 300 | animation = true |  | The delay of the animation. |
| `TextAlpha` | Number | 255 | 0 – 255 |  |  | The alpha of the chat text. |
| `Timestamps` | Boolean | true |  |  |  | Adds timestamps to every chat message. |
| `Opening` | String | < |  | timestamps = true |  | The symbol that will be placed before the timestamp text. |
| `Closing` | String | > |  | timestamps = true |  | The symbol that will be placed after the timestamp text. |
| `Background` | Mode | Default | Default, Clear, Custom |  |  | The background mode for the chat. |
| `Color` | Color | ColorSetting.Color(Color(0, 0, 0, 127), false, false) |  | background = Custom |  | The color of the chat background. |
| `Highlight` | Boolean | true |  |  |  | Highlights your name in incoming chat messages. |
| `HighlightColor` | Color | ColorSetting.Color(Color(85, 255, 255), false, false) |  | highlight = true |  | The color used to highlight your name in chat. |
| `HighlightSound` | Boolean | true |  | highlight = true |  | Plays a ding notification sound when your name is mentioned in chat. |

### ExtraTab
Extends the size of the player list.

Plik: `night/modules/impl/miscellaneous/ExtraTabModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Limit` | Number | 1000 | 1 – 1000 |  |  | The maximum amount of players that will be listed. |
| `Friends` | Boolean | true |  |  |  | Highlights your friends on the player list. |

### FOVModifier
Gives you more customizability for the games FOV.

Plik: `night/modules/impl/miscellaneous/FOVModifierModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `FOV` | Number | 120 | 50 – 150 |  |  | The FOV you want to use. |

### FakePlayer
Spawns in a fake player entity that you can use to test modules on.

Plik: `night/modules/impl/miscellaneous/FakePlayerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Name` | String | Dummy |  |  |  | The name that will be assigned to the fake player. |
| `Health` | Number |  |  |  |  | The amount of health that will be assigned to the fake player. |
| `Absorption` | Number | 16 | 0 – 16 |  |  | The amount of absorption that will be assigned to the fake player. |
| `Movement` | Category |  |  |  |  | The category that contains settings related to movement. |
| `Movement` | Mode | None | None, Random |  | Mode | The mode that will be used for the fake player's movement. |
| `Velocity` | Number |  |  |  | Velocity | The velocity to apply on the fake player. |
| `ChangeDirection` | Boolean | false |  |  |  | Changes the player's direction when a specified amount of time has passed. |
| `Timeout` | Number | 5 | 1 – 15 | changeDirection = true |  | The amount of time that it takes for the player to change direction. |

### FastLatency
Makes ping resolving much faster.

Plik: `night/modules/impl/miscellaneous/FastLatencyModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 0 |  |  |  | The amount of milliseconds that have to be waited for before resolving your ping again. |
| `SpikeNotifier` | Boolean | false |  |  |  | Notifies you in chat whenever your ping spikes. |
| `Threshold` | Number | 30 | 0 – 1000 | spikeNotifier = true |  | The amount of milliseconds that your ping has to increase for before notifying you. |

### Handshake
Spoofs your client handshake to make the server think that you are playing on a different client.

Plik: `night/modules/impl/miscellaneous/HandshakeModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Brand` | String | vanilla |  |  |  | The brand that the server will think you are playing on. |

### LagNotify
Notifies you when you lag.

Plik: `night/modules/impl/miscellaneous/LagNotifyModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Server` | Boolean | true |  |  |  | Notifies you when the server stops responding. |
| `Lagback` | Boolean | true |  |  |  | Notifies you when you lagback. |
| `Color` | Color | ColorUtils.getDefaultOutlineColor() |  |  |  | The color of the notification text. |

### MouseFix
Fixes multiple mouse issues.

Plik: `night/modules/impl/miscellaneous/MouseFixModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `CustomDebounce` | Boolean | true |  |  |  | Implements a custom debounce timer on mouse inputs. |

### NoPacketKick
Prevents you from being kicked from the server due to Netty, packet decoding, or codec exceptions.

Plik: `night/modules/impl/miscellaneous/NoPacketKickModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `LogChat` | Boolean | true |  |  |  | Notifies you in chat when a corrupted or invalid packet is caught and suppressed. |
| `LogConsole` | Boolean | true |  |  |  | Prints the full exception stacktrace to the console log. |
| `OnlyDecoder` | Boolean | false |  |  |  | Only suppresses decoder and codec errors, allowing critical socket disconnects through. |

### NoSoundLag
Prevents lagging caused by a large amount of sounds being played.

Plik: `night/modules/impl/miscellaneous/NoSoundLagModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Armor` | Boolean | true |  |  |  | Prevents lagging caused by armor sounds. |
| `Withers` | Boolean | true |  |  |  | Prevents lagging caused by wither sounds. |
| `Ghasts` | Boolean | true |  |  |  | Prevents lagging caused by ghast sounds. |
| `Piston` | Boolean | true |  |  |  | Prevents lagging caused by piston sounds. |
| `Dispenser` | Boolean | true |  |  |  | Prevents lagging caused by dispenser sounds. |

### Notifications
Notifies you in chat whenever something significant happens.

Plik: `night/modules/impl/miscellaneous/NotificationsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `TotemPops` | Boolean | true |  |  |  | Notifies you in chat whenever a player pops a totem. |
| `VisualRange` | Boolean | false |  |  |  | Notifies you in chat whenever a player enters your render distance. |
| `PearlThrows` | Boolean | true |  |  |  | Notifies you in chat whenever a player throws a pearl. |

### Scaffold
Places blocks under the player to bridge automatically.

Plik: `night/modules/impl/miscellaneous/ScaffoldModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to blocks. |
| `Range` | Number | 4.0 | 1.0 – 6.0 |  |  | Range to place blocks. |
| `KeepY` | Boolean | false |  |  |  | Maintains the player's Y level while moving. |
| `Downwards` | Boolean | true |  |  |  | Places blocks below your feet when sneaking. |
| `Tower` | Boolean | true |  |  |  | Quickly towers up when jumping. |
| `TowerSpeed` | Number | 0.42 | 0.1 – 1.0 | tower = true |  | Vertical tower speed. |
| `Rotate` | Boolean | true |  |  |  | Rotates towards block placements. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions facing you. |
| `AirPlace` | Boolean | false |  |  |  | Places blocks in the air without support. |
| `Delay` | Number | 0 | 0 – 10 |  |  | Delay in ticks between placements. |
| `Render` | Boolean | true |  |  |  | Renders placed positions. |

### Screenshot
Automatically copies screenshots to clipboard when pressing F2.

Plik: `night/modules/impl/miscellaneous/ScreenshotModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Clipboard` | Boolean | true |  |  |  | Copies screenshot image directly to your clipboard. |
| `CopyFile` | Boolean | true |  |  |  | Also includes file flavor for pasting into file explorers. |
| `Notify` | Boolean | true |  |  |  | Sends a confirmation message in chat when copied. |
| `OpenFolder` | Boolean | false |  |  |  | Automatically opens the screenshots folder. |

### ShulkerInfo
Renders a preview of the contents of the shulker you are hovering.

Plik: `night/modules/impl/miscellaneous/ShulkerInfoModule.java`

_Moduł nie posiada własnych ustawień._

### Sounds
Plays custom sounds when something happens. Put .wav files in .minecraft/Night/Client/

Plik: `night/modules/impl/miscellaneous/SoundsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Kills` | Category |  |  |  |  | The kills category for the sounds. |
| `KillSound` | Boolean | true |  |  |  | Play sounds when you kill a player. |
| `KillVolume` | Number |  |  |  | Volume | The volume for the kill sounds. |
| `KillName` | String | killsound.wav |  |  | Name | The name of the kill sound file. Put it in .minecraft/Night/Client/ |
| `Hits` | Category |  |  |  |  | The hits category for the sounds. |
| `HitSound` | Boolean | true |  |  |  | Play sounds when you hit an entity. |
| `HitVolume` | Number |  |  |  | Volume | The volume for the hit sounds. |
| `HitName` | String | hitsound.wav |  |  | Name | The name of the hit sound file. Put it in .minecraft/Night/Client/ |

### Spammer
Spams messages in chat from a text file.

Plik: `night/modules/impl/miscellaneous/SpammerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `FileName` | String | spammer.txt |  |  |  | The name of the spammer text file. |
| `Delay` | Number | 5 | 0 – 30 |  |  | The delay for the announcer. |
| `GreenText` | Boolean | false |  |  |  | Makes your message green. |
| `Shuffled` | Boolean | false |  |  |  | Sends the spammer messages out of order. |

### Welcomer
Sends a message when a player joins or leaves the server.

Plik: `night/modules/impl/miscellaneous/WelcomerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Joins` | Boolean | true |  |  |  | Sends messages when a player joins the server |
| `Leaves` | Boolean | true |  |  |  | Sends messages when a player leaves the server |
| `Clientside` | Boolean | false |  |  |  | Sends the messages only on your side. |
| `Unicode` | Boolean | false |  |  |  | Uses prettier unicode icons instead of normal arrows. |
| `GreenText` | Boolean | false |  |  |  | Makes your message green. |
| `Delay` | Number | 5 | 0 – 30 |  |  | The delay for the announcer. |

## Kategoria: MOVEMENT (25 modułów)

### Anchor
Pulls you toward your nearest hole when looking down.

Plik: `night/modules/impl/movement/Anchor.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Pitch` | Number | 60 | 0 – 90 |  |  | Minimum pitch angle to activate anchoring. |
| `Doubles` | Boolean | true |  |  |  | Include 2x1 holes when anchoring. |
| `StopMotion` | Boolean | true |  |  |  | Stop horizontal movement while anchoring. |
| `FastFall` | Boolean | true |  |  |  | Accelerate fall speed while anchoring. |
| `FallSpeed` | Number |  |  |  |  | Speed multiplier for falling. |
| `AlreadyInHole` | Boolean | true |  |  |  | Don't anchor if already in a hole. |
| `CheckTerrainHole` | Boolean | false |  |  |  | Don't anchor toward terrain holes. |
| `CheckBurrowed` | Boolean | true |  |  |  | Don't anchor if burrowed. |

### AntiVoid
Prevents you from falling into the void.

Plik: `night/modules/impl/movement/AntiVoidModule.java`

_Moduł nie posiada własnych ustawień._

### ArrowControls
Allows you to control the camera using your arrow keys.

Plik: `night/modules/impl/movement/ArrowControlsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Speed` | Number |  |  |  |  | The speed at which the camera will be moving at. |
| `Inventory` | Boolean | true |  |  |  | Allows you to control the camera when a screen is opened. |

### AutoWalk
Holds forward (or backward) movement every tick. Auto-pauses while Baritone is actively driving movement (e.g. EBounce+'s ObstaclePassing) so the two don't fight over input.

Plik: `night/modules/impl/movement/AutoWalkModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Backward` | Boolean | false |  |  |  | Walk backward instead of forward. |

### BounceDebug
Prints ElytraFly Bounce speed/recast diagnostics to chat -- toggle independently of ElytraFly's own settings.

Plik: `night/modules/impl/movement/ElytraBounceDebugModule.java`

_Moduł nie posiada własnych ustawień._

### ElytraFly
Allows you to fly using an elytra without fireworks.

Plik: `night/modules/impl/movement/ElytraFlyModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | ControlRocket | Control, ControlRocket, Bounce |  |  | The mode that will be used for elytra flying. |
| `Horizontal` | Number |  |  | mode = Control |  | The speed at which you will be flying horizontally. |
| `Vertical` | Number |  |  | mode = Control |  | The speed at which you will be flying vertically. |
| `MoveVertically` | Boolean | true |  | mode = Control |  | Whether or not to allow for vertical movement. |
| `Takeoff` | Boolean | true |  | mode = Bounce |  | Automatically jumps and initiates gliding when grounded with an elytra equipped. |
| `AutoPitch` | Boolean | true |  | mode = Bounce |  | Automatically pitches the player's rotation down while gliding to bounce at faster speeds. |
| `Pitch` | Number |  |  | mode = Bounce |  | The pitch angle to look down when auto pitch is active. |
| `MinimizePackets` | Boolean | true |  | mode = Bounce |  | Send the bare minimum to start flying, nothing extra. |
| `FlagPause` | Number |  |  | mode = Bounce |  | How long (in ticks) to pause if the server flags you for a movement check. |
| `FakeLag` | Boolean | true |  | mode = Bounce |  | Emulates the player lagging to allow flying in 1x2 tunnels. |
| `PassObstacles` | Boolean | false |  | mode = Bounce |  | Automatically paths around obstacles in the flight line using Baritone. |
| `ObstacleLookAhead` | Number |  |  | mode = Bounce |  | Blocks to look ahead for obstacles / step by when pathing around one. |
| `StandUpToPass` | Boolean | true |  | mode = Bounce |  | Stops gliding (stands up) while Baritone navigates around an obstacle, then automatically takes off again once clear. |
| `PutOnElytra` | Boolean | true |  | mode = Bounce |  | Safety net: if the elytra ends up off the chest slot, immediately equip it back. |
| `Bypass` | Boolean | false |  | mode = Bounce |  | Bypass for 2b and other servers (may be) |
| `BypassTicks` | Number |  |  | bounceBypass = true |  | How many ticks before/after the recast to blink. |
| `Control` | Mode | Key | Key, Mouse | mode = ControlRocket |  | Steering mode: Key (WASD flight + FreeLook camera) or Mouse (fly where camera looks). |
| `TurnSpeed` | Number |  |  | mode = ControlRocket |  | Maximum rotation change per tick. |
| `FireworksDelay` | Number | 0 | 0 – 3 | mode = ControlRocket |  | Delay between every fire |
| `FlipFlopMode` | Mode | Full | Full, WithFirework, None | mode = ControlRocket |  | How to hold position when not pressing movement keys. |
| `Inventory` | Boolean | true |  | mode = ControlRocket |  | Allows using fireworks from anywhere in the inventory, not just the hotbar. |
| `GrimV3` | Boolean | true |  | mode = ControlRocket |  | Cycles the elytra out of the chest slot so it never loses durability. |
| `GrimDelay` | Number |  |  | mode = ControlRocket |  | Gliding ticks between each GrimV3 swap-out. |
| `MuteElytra` | Boolean | true |  | mode = ControlRocket |  | Mutes the armor-equip sound when ControlRocket swaps between elytra and chestplate. |

### EntityControl
Allows you to control and fly with ridden entities.

Plik: `night/modules/impl/movement/EntityControlModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Fly` | Boolean | true |  |  |  | Allows you to fly with your vehicle. |
| `Speed` | Number | 5.0 | 0.1 – 50.0 |  |  | Horizontal flying speed. |
| `VerticalSpeed` | Number | 6.0 | 0.0 – 20.0 |  |  | Vertical flying speed. |
| `FallSpeed` | Number | 0.1 | 0.0 – 50.0 |  |  | Glide down speed when no vertical key is pressed. |
| `Control` | Boolean | true |  |  |  | Steer entities even without a saddle. |
| `NoSync` | Boolean | false |  |  |  | Cancels server vehicle sync packets to prevent rubberbanding. |

### FakeLag
Chokes sent packets to look like you are lagging.

Plik: `night/modules/impl/movement/FakeLagModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Choke` | Number | 2 | 1 – 5 |  |  | The delay to choke packets for. |
| `OnlyOnHoleLeave` | Boolean | false |  |  |  | Only choke packets when leaving a hole. |
| `AutoDisable` | Number | 0 | 0 – 1000 |  | AutoDisable | Delay (ms) after FakeLag is enabled before it disables itself. 0 = off. |
| `RenderMode` | Mode | Both | None, Fill, Outline, Both |  |  | The rendering that will be applied to the target entity. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color that will be used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color that will be used for the outline rendering. |

### FastClimb
Modifies your climbing speed on ladders, scaffolding, vines and other climbables.

Plik: `night/modules/impl/movement/FastClimbModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Speed` | Number |  |  |  | Speed | How much of vanilla's climb speed to use. Ignore walks past climbables like normal blocks, 1 is vanilla's normal speed. |

### FastSwim
Modifies your swim speed in water and lava.

Plik: `night/modules/impl/movement/FastSwimModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `H-Water` | Number |  |  |  |  | Horizontal swim speed multiplier in water. |
| `V-Water` | Number |  |  |  |  | Vertical (up/down) swim speed multiplier in water. |
| `H-Lava` | Number |  |  |  |  | Horizontal swim speed multiplier in lava. |
| `V-Lava` | Number |  |  |  |  | Vertical (up/down) swim speed multiplier in lava. |

### FastWeb
Allows you to move quickly through webs.

Plik: `night/modules/impl/movement/FastWebModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Normal | Normal, Ignore, Strong |  |  | The method that will be used to speed you through webs. |
| `Speed` | Number | 3.0 | 0.1 – 10.0 | mode = Normal |  | The speed at which you will fall through webs. |
| `Horizontal` | Number | 2.5 | 0.1 – 5.0 | mode = Strong |  | The speed at which you will move horizontally. |
| `Vertical` | Number | 2.5 | 0.1 – 5.0 | mode = Strong |  | The speed at which you will move vertically. |
| `Sneak` | Boolean | false |  | mode = Normal |  | Only bypasses web slowdown when sneaking. |
| `Grim` | Boolean | false |  |  |  | Includes bypasses for the Grim anticheat. |

### HighJump
Launches you upward when a shulker box next to you opens.

Plik: `night/modules/impl/movement/HighJumpModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Strength` | Number | 5 | 0 – 5 |  | Strength | Launch power. 1 ~ 26 blocks, 3 ~ 104, 5 ~ 200. 0 = off. |
| `Infinite` | Boolean | false |  |  |  | Hold space when the jump peaks to launch again with the same power. |

### HitboxDesync
Precisely offsets your position to glitch out Minecraft hitbox calculations.

Plik: `night/modules/impl/movement/HitboxDesyncModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Alternating` | Boolean | false |  |  |  | Modifies your position in such a way that it messes with other clients. |
| `Minimal` | Boolean | false |  | alternating = true |  | Makes alternating minimal, only required on certain servers. |
| `Specific` | Boolean | false |  | alternating = true |  | Specific alternating mode, only required against certain clients. |
| `Close` | Boolean | false |  |  |  | Whether or not to have the surround place blocks on desynced positions. |
| `SelfDisable` | Boolean | true |  | alternating = false |  | Toggles off the module after having desynced your hitbox. |
| `JumpDisable` | Boolean | true |  | alternating = true |  | Toggles off the module whenever your Y level changes. |

### HoleSnap
Pulls you toward your nearest hole.

Plik: `night/modules/impl/movement/HoleSnapModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number | 5 | 1 – 8 |  |  | Range for the holes. |
| `DoubleHoles` | Boolean | true |  |  |  | Whether or not to snap you to double holes. |
| `QuadHoles` | Boolean | true |  |  |  | Whether or not to snap you to quad holes. |
| `Step` | Boolean | false |  |  |  | Keeps snapping to the next nearest hole instead of stopping once you've reached one. |
| `FillHole` | Boolean | false |  | step = true |  | Fills every cell of the hole you just stepped out of, once you've fully left its footprint (needs Step). |
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to necessary items. |
| `Rotate` | Boolean | true |  |  |  | Sends a packet rotation whenever placing a block. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `CrystalDestruction` | Boolean | true |  |  |  | Destroys any crystals that interfere with block placement. |
| `Render` | Boolean | true |  |  |  | Whether or not to render the place position. |
| `Timer` | Number |  |  |  |  | The tick-speed multiplier to run at while HoleSnap is active (1.0 = normal speed). |
| `RenderTarget` | Boolean | true |  |  |  | Draws an outline around the hole HoleSnap is currently walking toward. |
| `RenderTargetColor` | Color | ColorUtils.getDefaultOutlineColor() |  | renderTarget = true |  | The color of the target hole outline. |

### InventoryControl
Allows you to control things such as movement and your camera while a GUI is open.

Plik: `night/modules/impl/movement/InventoryControlModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Movement` | Boolean | true |  |  |  | Allows you to control movement. |
| `Portals` | Boolean | true |  |  |  | Allows you to interact with GUIs while inside of a portal. |
| `DragClick` | Boolean | true |  |  |  | Hold shift + left-click and drag over slots to move each item you pass over. |
| `GrimV2` | Boolean | false |  |  |  | Bypasses inventory-while-moving checks on GrimV2/NCP servers. |

### NoFall
Prevents you from taking fall damage.

Plik: `night/modules/impl/movement/NoFallModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Packet | Packet, Grim |  |  | The method that will be used in preventing fall damage. |

### NoJumpDelay
Removes the delay that slows down your jumping.

Plik: `night/modules/impl/movement/NoJumpDelayModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Ticks` | Number | 1 | 0 – 20 |  |  | The amount of ticks that have to be waited for before jumping again. |

### NoSlow
Removes the slowness effect that you receive when doing certain actions.

Plik: `night/modules/impl/movement/NoSlowModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Items` | Boolean | true |  |  |  | Removes the slowness effect from eating or using items. |
| `Mode` | Mode | Normal | Normal, Strict, GrimV2, GrimV3 |  |  | Item slowdown bypass mode. |
| `Crawl` | Boolean | false |  |  |  | Removes the slowness effect from crawling. |
| `Sneak` | Boolean | false |  |  |  | Removes the slowness effect from sneaking. |
| `SoulSand` | Boolean | false |  |  |  | Removes the slowness effect from walking on soul sand. |
| `SlimeBlocks` | Boolean | false |  |  |  | Removes the slowness effect from walking on slime blocks. |
| `HoneyBlocks` | Boolean | false |  |  |  | Removes the slowness effect from walking on honey blocks. |

### Phase
Throws an ender pearl to phase into blocks. KingMC Crawl mode requires crawling away from the target block before throwing.

Plik: `night/modules/impl/movement/PhaseModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Logic` | Mode | KingMC | KingMC, Grim, NCP |  |  | Phase bypass targeting logic: KingMC (bedrock = Grim behavior, crawl requires crawling away from block before throwing), Grim, or NCP. |
| `Swap` | Mode | Silent |  |  |  | The mode that will be used for switching to the pearl before throwing it. |
| `Crawl` | Boolean | false |  |  |  | Crawl phase mode. In KingMC mode, crawl away from the target block before throwing. |
| `AntiVoid` | Boolean | true |  |  |  | Prevents phasing into or through blocks that lead directly into the void. |
| `Debug` | Boolean | false |  |  |  | Chat-prints pos/yaw/pitch/target for every throw, so a failed (popped-up) attempt can be matched back to its exact numbers. |
| `NCP-Mode` | Mode | FireCharge | FireCharge, FlintAndSteel, Cobweb | logic = NCP |  | Item used to bypass pearl distance checks on NCP. |
| `Switch` | Mode | Silent | Normal, Silent, AltPickup, AltSwap | logic = NCP |  | The mode that will be used for switching to the bypass item. |
| `AutoRemove` | Boolean | true |  | logic = NCP |  | Automatically removes the bypass block/fire once you have phased. |

### ReverseStep
Makes it so that you fall down instantly at a specified height.

Plik: `night/modules/impl/movement/ReverseStepModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Height` | Number | 3.0 | 0.0 – 12.0 |  |  | The maximum height at which instant falling will be applied to. |

### Speed
Makes it so that you move faster than normal.

Plik: `night/modules/impl/movement/SpeedModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Strafe | Vanilla, Strafe, StrafeStrict, Grim |  |  | The method that will be used to increase your speed. |
| `VanillaSpeed` | Number | 10.0 | 0.0 – 20.0 | mode = Vanilla | Speed | The speed that will be applied to your movement. |
| `VanillaOnGround` | Boolean | false |  | mode = Vanilla | OnGround | Only applies the speed when you are on ground. |
| `UseTimer` | Boolean | false |  | mode = Strafe, StrafeStrict |  | Adds a timer multiplier when strafing. |
| `Bypass` | Boolean | true |  | useTimer = true |  | Allows you to use timer on certain servers. |
| `Threshold` | Number | 25 | 15 – 30 | timerBypass = true |  | The threshold value for the timer bypass. |
| `TimerMultiplier` | Number |  |  | useTimer = true | Multiplier | The timer multiplier that will be applied to the timer. |
| `SpeedInLiquid` | Boolean | false |  | mode = Strafe, StrafeStrict |  | Increases your speed while in water or lava. |
| `Boost` | Boolean | false |  | mode = Strafe, StrafeStrict |  | Uses explosion knockback to boost your speed. |
| `BoostMultiplier` | Number | 1.0 | 0.01 – 5.0 | boost = true | Multiplier | Scales the explosion knockback boost. |
| `BoostCap` | Number | 5.0 | 0.0 – 10.0 | boost = true | Cap | Maximum boost value that can be applied. |
| `AutoJump` | Boolean | false |  | mode = Grim |  | Automatically jumps for you when on ground. |

### Sprint
Makes it so that you are always sprinting when possible.

Plik: `night/modules/impl/movement/SprintModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Rage | Legit, Rage, Instant, Grim |  |  | The limits to when you can be sprinting. |
| `InstantSpeed` | Number |  |  | mode = Instant |  | Per-tick horizontal speed to move at (blocks/tick) when Mode is Instant. |
| `Water` | Boolean | true |  | mode = Instant |  | Keeps applying the Instant speed override while in water. |
| `Lava` | Boolean | false |  | mode = Instant |  | Keeps applying the Instant speed override while in lava. |
| `PauseOnSetback` | Boolean | true |  |  |  | Briefly pauses sprinting when receiving a setback from the server to allow GrimAC buffer to decay. |

### Step
Gives you the ability to instantly climb over a customizable amount of blocks.

Plik: `night/modules/impl/movement/StepModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Vanilla | Vanilla, NCP |  |  | The mode that will be used to climb over blocks. |
| `Height` | Number |  |  |  |  | The maximum height at which blocks can be climbed over. |
| `UseTimer` | Boolean | true |  | mode = NCP |  | Uses timer to slow you down while climbing. |

### TickShift
Manipulates minecraft timer to give you a small speed boost.

Plik: `night/modules/impl/movement/TickShiftModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `MaxTicks` | Number | 1 | 1 – 40 |  |  | The maximum amount of ticks that the boost will be charging for. |
| `Delay` | Number | 4 | 1 – 10 |  |  | The delay between each tick. |
| `Speed` | Number |  |  |  |  | The speed of charging each tick. |

### Velocity
Modifies the amount of knockback that you receive.

Plik: `night/modules/impl/movement/VelocityModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Normal | Normal, Walls, Cancel, Grim |  |  | The method that will be used to achieve the knockback modification. |
| `Horizontal` | Number | 0 | 0 – 100 | mode = Normal, Walls |  | The amount of horizontal knockback that you will receive. |
| `Vertical` | Number | 0 | 0 – 100 | mode = Normal, Walls |  | The amount of vertical knockback that you will receive. |
| `OnlyOnGround` | Boolean | false |  | mode = Walls |  | Only reduces knockback while phased AND on the ground. |
| `Explosions` | Boolean | true |  |  |  | Modifies knockback received from explosions. |
| `Pause` | Boolean | true |  | mode = Cancel, Grim |  | Pauses the velocity for a certain duration whenever you get rubberbanded. |
| `OnlyPhased` | Boolean | false |  | mode = Grim |  | Only reduces knockback while phased (Grim mode). |
| `OnlyCoveredHead` | Boolean | false |  | mode = Grim |  | Only reduces knockback when a block is above your head (Grim mode). |
| `FallDistance` | Number | 10.0 | 0.0 – 50.0 |  |  | Pause velocity if falling from more than this distance in blocks (0 to disable). |
| `Conceal` | Boolean | false |  |  |  | Ignores fake 0-0-0 velocity packets sent after a setback. |
| `AntiPush` | Category |  |  |  |  | Prevents certain things from pushing you. |
| `AntiPush` | Boolean | true |  |  | Entities | Prevents other entities from pushing you. |
| `AntiLiquidPush` | Boolean | false |  |  | Liquids | Prevents liquids from pushing you. |
| `AntiBlockPush` | Boolean | true |  |  | Blocks | Prevents you from being pushed outside of blocks. |
| `AntiFishingRod` | Boolean | false |  |  | FishingRods | Prevents fishing rods from pushing you. |

## Kategoria: PLAYER (22 modułów)

### AirPlace
Lets you place blocks in the air in servers that allow it.

Plik: `night/modules/impl/player/AirPlaceModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Grim` | Boolean | false |  |  |  | Swaps the block into your offhand for just the instant of each placement -- GrimAC's whole scaffolding check group (AirLiquidPlace, RotationPlace, PositionPlace...) only runs for MAIN_HAND placements, verified against its own decompiled source. |

### AntiHunger
Reduces the amount of hunger consumption.

Plik: `night/modules/impl/player/AntiHungerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Ground` | Boolean | true |  |  |  | Modifies movement packets to decrease hunger consumption. |
| `Sprint` | Boolean | true |  |  |  | Spoofs sprinting packets to decrease hunger consumption. |

### AutoShop
Automates the server /shop GUI to buy totems, end crystals, or exp bottles.

Plik: `night/modules/impl/player/AutoShopModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Totem` | Bind |  | 0 |  |  | Keybind to auto buy totems. |
| `TotemAmount` | Number | 1 | 1 – 10 |  |  | How many totems to buy. |
| `Crystal` | Bind |  | 0 |  |  | Keybind to auto buy end crystals. |
| `CrystalStacks` | Number | 1 | 1 – 10 |  |  | How many stacks (64) of end crystals to buy. |
| `Exp` | Bind |  | 0 |  |  | Keybind to auto buy exp bottles. |
| `ExpStacks` | Number | 1 | 1 – 10 |  |  | How many stacks (64) of exp bottles to buy. |
| `Obsidian` | Bind |  | 0 |  |  | Keybind to auto buy obsidian. |
| `ObsidianStacks` | Number | 1 | 1 – 10 |  |  | How many stacks (64) of obsidian to buy. |
| `EnderPearl` | Bind |  | 0 |  |  | Keybind to auto buy ender pearls. |
| `EnderPearlStacks` | Number | 1 | 1 – 10 |  |  | How many stacks (64) of ender pearls to buy. |
| `ActionDelay` | Number | 4 | 0 – 20 |  |  | Ticks between each shop GUI click. |

### ChestStealer
Automatically steals, dumps, or drops items to and from an open container.

Plik: `night/modules/impl/player/ChestStealerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Steal` | Boolean | true |  |  |  | Move matching items FROM the open container INTO your inventory. |
| `Mode` | Mode | All | WhiteList, BlackList, All | steal = true |  | WhiteList = steal items IN the list. BlackList = steal items NOT in the list. All = steal everything. |
| `List` | Whitelist | WhitelistSetting.Type.ITEMS |  | steal = true |  | Items to compare against. |
| `Dump` | Boolean | false |  |  |  | Move matching items FROM your inventory INTO the open container. |
| `Mode` | Mode | WhiteList | WhiteList, BlackList, All | dump = true |  | WhiteList = dump items IN the list. BlackList = dump items NOT in the list. All = dump everything. |
| `List` | Whitelist | WhitelistSetting.Type.ITEMS |  | dump = true |  | Items to compare against. |
| `Drop` | Boolean | false |  |  |  | Throw matching items OUT (Q / THROW). |
| `DropMode` | Mode | All | WhiteList, BlackList, All | drop = true | Mode | WhiteList = drop items IN the list. BlackList = drop items NOT in the list. All = drop everything. |
| `DropList` | Whitelist | WhitelistSetting.Type.ITEMS |  | drop = true | List | Items to compare against for drop. |
| `Interact` | Mode | Both | Hotbar, Inventory, Both |  |  | Which of YOUR slots Dump/Drop act on: Hotbar (0-8), Inventory (9-35), or Both. |
| `Delay` | Number | 1 | 0 – 20 |  |  | Tick delay between action passes. |
| `ActionsPerTick` | Number | 5 | 1 – 36 |  |  | Max slot actions per pass. |
| `ButtonMode` | Boolean | false |  |  |  | Adds vanilla Steal/Dump/Drop buttons at the top-right of the container/inventory GUI. |
| `IgnoreCustomName` | Boolean | false |  |  |  | Skip any container with a custom (non-vanilla) title such as a shop GUI, except shulker boxes. |

### FastPlace
Allows you to customize the tick delay between using items.

Plik: `night/modules/impl/player/FastPlaceModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Ticks` | Number | 1 | 0 – 20 |  |  | The amount of ticks that have to be waited for before using items again. |
| `IgnoreBlocks` | Boolean | true |  |  |  | Uses the default Minecraft delay when holding a block item. |
| `IgnoreFireworks` | Boolean | true |  |  |  | Uses the default Minecraft delay when holding fireworks. |
| `IgnorePearls` | Boolean | true |  |  |  | Uses the default Minecraft delay when holding pearls. |
| `IgnoreEquipment` | Boolean | true |  |  |  | Uses the default Minecraft delay when holding an equipment item. |

### InventoryCleaner
Auto-drops unwanted items from your inventory each tick.

Plik: `night/modules/impl/player/InventoryCleanerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | WhiteList | WhiteList, BlackList, All |  |  | WhiteList = drop items IN list. BlackList = drop items NOT in list. All = drop everything. |
| `Whitelist` | Whitelist | WhitelistSetting.Type.ITEMS |  |  |  | Items this mode's WhiteList/BlackList compares against. |
| `IgnoreHotbar` | Boolean | true |  |  |  | Skip hotbar slots (0-8) when cleaning. |
| `ThrowWorse` | Boolean | true |  |  |  | Drop lower-tier duplicate tools/armor of the same type (e.g. diamond+iron pickaxe -> drop iron). |
| `Others` | Boolean | false |  |  |  | Also drop from other open GUIs like Chest, Shulker, EnderChest. |
| `IgnoreCustomName` | Boolean | false |  |  |  | (With Others on) skip acting on custom-titled containers such as a shop GUI, except shulker boxes. Does not affect individual items. |
| `Delay` | Number | 1 | 0 – 20 |  |  | Tick delay between drop passes. |
| `ActionsPerTick` | Number | 5 | 1 – 20 |  |  | Max items to drop per pass. |

### InventorySorter
Auto-sorts your inventory into the active Rekit kit's layout.

Plik: `night/modules/impl/player/InventorySorterModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 1 | 0 – 10 |  |  | Tick delay. |
| `ActionsPerTick` | Number | 1 | 1 – 5 |  |  | Max actions per tick. |
| `IgnoreHotbar` | Boolean | false |  |  |  | Skip hotbar slots (0-8) when sorting. |

### KeyAction
Keybinds for throwing fireworks, pearls and experience bottles.

Plik: `night/modules/impl/player/KeyActionModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `FireWork` | Category |  |  |  |  | Keybind and settings for throwing a firework. |
| `FireWorkBind` | Bind |  | 0 |  | Bind | The key that throws a firework. Hold/ReverseHold repeat it every tick the bind is (or isn't) held. |
| `FireWorkSwitch` | Mode | Silent |  |  | Switch | The mode that will be used for automatically switching to fireworks. |
| `Pearls` | Category |  |  |  |  | Keybind and settings for throwing an ender pearl. |
| `PearlsBind` | Bind |  | 0 |  | Bind | The key that throws an ender pearl. Hold/ReverseHold repeat it every tick the bind is (or isn't) held. |
| `PearlsSwitch` | Mode | Silent |  |  | Switch | The mode that will be used for automatically switching to pearls. |
| `PearlsRotate` | Boolean | true |  |  | Rotate | Sends a packet rotation right before throwing the pearl. |
| `XP` | Category |  |  |  |  | Keybind and settings for throwing experience bottles. |
| `XPBind` | Bind |  | 0 |  | Bind | The key that starts/stops throwing experience bottles. Hold/ReverseHold keep the loop matched to whether the bind is (or isn't) held instead of toggling it. |
| `XPSwitch` | Mode | Silent |  |  | Switch | The mode that will be used for automatically switching to experience bottles. |
| `XPRotate` | Boolean | true |  |  | Rotate | Rotates down to your feet when throwing experience bottles. |
| `XPDelay` | Number | 1 | 0 – 20 |  | Delay | The delay in ticks between throwing experience bottles. |
| `XPRepeat` | Number | 1 | 1 – 15 |  | Repeat | Allows you to throw a lot more XP bottles at once. |
| `XPAntiWaste` | Mode | Avoid | None, Avoid, Disable |  | AntiWaste | How wasting of experience bottles should be prevented. |
| `XPItemDisable` | Boolean | true |  |  | ItemDisable | Automatically stops the XP loop when you run out of XP. |

### MultiTask
Allows you to interact with blocks while eating or using an item.

Plik: `night/modules/impl/player/MultiTaskModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Pearl` | Boolean | true |  |  |  | Don't stop eating when you throw a pearl with Phase / KeyAction. |
| `AutoTotem` | Boolean | true |  |  |  | Don't stop/reset your eating when AutoTotem swaps a totem into your offhand. |

### NoBreakDelay
Allows you to customize the delay between breaking blocks.

Plik: `night/modules/impl/player/NoBreakDelayModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 0 | 0 – 5 |  |  | The delay between block breaks in ticks (0 is no delay, 5 is vanilla). |

### NoEntityTrace
Allows you to interact with blocks, bypassing the entities between you and the block.

Plik: `night/modules/impl/player/NoEntityTraceModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `PickaxeOnly` | Boolean | false |  |  |  | Only bypasses entities if you're holding a pickaxe. |
| `AngryMobs` | Boolean | false |  |  |  | Only bypasses non-living entities (Crystals, Boats, Minecarts) and neutral/angerable mobs (Endermen, Piglins, Golems, etc.). |
| `IgnoredItem` | Whitelist | WhitelistSetting.Type.ITEMS |  |  |  | Do not bypasses entities if you're holding these items. |

### NoInteract
Prevents you from interacting with right-clickable blocks.

Plik: `night/modules/impl/player/NoInteractModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Sneak | Sneak, Disable |  |  | The way that right-clickable blocks will be ignored. |
| `Gapple` | Boolean | false |  |  |  | Only disables interactions when holding a golden apple in your main hand. |

### NoRotate
Prevents the server from forcing rotations on you.

Plik: `night/modules/impl/player/NoRotateModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `InBlocks` | Boolean | false |  |  |  | Whether or not to stop rotations whenever inside of a block. |
| `Spoof` | Boolean | false |  |  |  | Sends rotation packets once you have been rubberbanded. |

### Reach
Allows you to modify the distance at which you can interact with blocks.

Plik: `night/modules/impl/player/ReachModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Amount` | Number | 6.0 | 0.0 – 8.0 |  |  | The maximum distance at which you will be able to interact with blocks. |

### Rekit
Automatically regears from an ender chest shulker using a saved kit.

Plik: `night/modules/impl/player/RekitModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 1 | 0 – 10 |  |  | Tick delay between pull actions. |
| `Frequency` | Number | 1 | 1 – 5 |  |  | Max pull actions per tick. |
| `Auto` | Boolean | false |  |  |  | Auto pull shulkers from the ender chest. |
| `SilentContainer` | Boolean | false |  |  |  | Pull items without the container GUI showing on screen. |
| `SwapMode` | Mode | AltSwap |  |  |  | Swap type used when placing shulkers and swapping to a breaking tool in Auto mode. |
| `ConsiderShulkerName` | Boolean | false |  |  |  | Prefer the shulker whose custom name matches the active kit's name, overriding content-based scoring. |
| `IgnoreCustomName` | Boolean | false |  |  |  | Skip any container with a custom (non-vanilla) title such as a shop GUI, except shulker boxes. |
| `AutoPlace` | Bind |  | 0 |  |  | Keybind: places the shulker in your inventory, opens it, pulls kit items, closes, then breaks it. No ender chest -- one-shot version of Auto. |
| `Rotate` | Mode | Grim | None, Normal, Grim |  |  | The rotation mode when placing the shulker. |
| `StrictDirection` | Boolean | false |  |  |  | Only places using directions that face you. |
| `Pickaxe` | Mode | Efficiency | Efficiency, SilkTouch |  |  | Preferred pickaxe enchant when choosing between candidates. |
| `Helmet` | Mode | Prot | Blast, Prot |  |  | Preferred helmet enchant when choosing between candidates. |
| `Chestplate` | Mode | Prot | Blast, Prot |  |  | Preferred chestplate enchant when choosing between candidates. |
| `Leggings` | Mode | Prot | Blast, Prot |  |  | Preferred leggings enchant when choosing between candidates. |
| `Boots` | Mode | Prot | Blast, Prot |  |  | Preferred boots enchant when choosing between candidates. |

### Replenish
Automatically replenishes stacks in your hotbar with new ones when they meet a specified threshold.

Plik: `night/modules/impl/player/ReplenishModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Swap | Pickup, Swap, Quick |  |  | The mode that will be used for switching items. |
| `Threshold` | Number | 12 | 1 – 64 |  |  | The minimum amount of items in a stack before that stack is replaced. |
| `MinimumCount` | Number | 48 | 1 – 64 |  |  | The minimum amount of items that should be in the new stack. |
| `Unstackable` | Boolean | false |  |  |  | Replenishes items that cannot be stacked (like Totems). |
| `Inventory` | Boolean | false |  |  |  | Allows replenishing items while an inventory, container, or custom GUI is open. |

### RotationLock
Locks your rotation to a certain yaw and pitch.

Plik: `night/modules/impl/player/RotationLockModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Both | Yaw, Pitch, Both |  |  | Whether the pitch or yaw will be locked. |
| `Custom` | Boolean | false |  | mode = Yaw, Pitch, Both |  | Holds your locked angle steady (camera free to turn) and only re-locks, snapping straight to the next 45deg, once you've turned far enough past it -- instead of an always-fixed value. |
| `Pitch` | Number | 1 |  | mode = Pitch, Both | Pitch | The fixed degrees pitch locks to when Custom is off. |

### SpeedMine
Automatically mines blocks at a faster speed using packets.

Plik: `night/modules/impl/player/SpeedMineModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Switch` | Mode | Silent |  |  |  | The mode that will be used for automatically switching to the fastest item. |
| `Range` | Number | 6.0 | 0.0 – 8.0 |  |  | The maximum distance at which blocks will be mined. |
| `Speed` | Number | 1.0 | 0.7 – 1.0 |  |  | The speed at which the module will mine blocks. |
| `FarReach` | Boolean | false |  |  |  | Bypass for Duration 0.7 on Grim. Try this if normal AutoMine doesn't work. |
| `6b` | Boolean | false |  |  |  | Allows mining bedrock after 40 ticks and enables instant-mining when broken. |
| `Rotate` | Mode | Normal | None, Normal, Silent |  |  | Automatically rotates to the block when mining it. |
| `SwitchReset` | Boolean | true |  | switchMode = None, AltSwap, AltPickup |  | Resets the mining when switching slots. |
| `Double` | Boolean | false |  |  |  | Allows the mining of 2 blocks at the same time. |
| `Shift` | Boolean | true |  | doubleMine = true |  | Selects the block behind according to crosshair as secondary when clicking while holding shift. |
| `Await` | Mode | Off | Off, Dynamic, Always | doubleMine = true |  | Wait for blocks to break before swapping back. Off: don't wait. Dynamic: wait only while the secondary isn't close to done yet. Always: always wait. Works around real NCP's FastBreak check on some Paper servers (cancels doubleMine's secondary otherwise) -- costs mining throughput while waiting. |
| `Rebreak` | Mode | None | None, Fast, Instant |  |  | Automatically re-mines blocks once they have been replaced. |
| `InstantDelay` | Number | 0 | 0 – 20 | rebreak = Fast, Instant |  | The amount of time that has to pass before instantly mining blocks. |
| `InstantTimeout` | Number | 60 | 0 – 100 | rebreak = Fast, Instant |  | The amount of time that cancel instantly mine while no block to mine. |
| `Async` | Boolean | false |  | rebreak = Fast, Instant |  | Keeps instantly re-firing even while the target position is currently air, instead of waiting for it to solidify. |
| `OnGroundCheck` | Category |  |  |  |  | Claims onGround on outgoing movement packets while mining, bypassing the not-on-the-ground mining speed penalty. |
| `NCP` | Boolean | false |  |  |  | Enables the onGround claim below. Turn off for servers that don't penalize air/liquid mining. |
| `AirCheck` | Boolean | false |  |  |  | Claims onGround while airborne and mining. |
| `LiquidCheck` | Boolean | false |  |  |  | Claims onGround while in liquid and mining. |
| `Grim` | Boolean | false |  |  |  | Adds a bypass catered to the Grim anticheat. |
| `WhileEating` | Boolean | true |  |  |  | Mines blocks while eating. |
| `Mode` | Mode | All | All, WhiteList, BlackList |  |  | All = mine every block. WhiteList = mine only listed blocks. BlackList = mine every block except listed. |
| `List` | Whitelist | WhitelistSetting.Type.BLOCKS |  |  |  | Blocks the WhiteList/BlackList mode compares against. |
| `Render` | Category |  |  |  |  | The category containing all settings related to rendering. |
| `Render` | Mode | Both | None, Fill, Outline, Both |  | Mode | The rendering that will be applied to the blocks highlighted. |
| `Animation` | Mode | Expand | None, Expand, Rise | render = Fill, Outline, Both |  | The animation that will be used when rendering the block mining progress. |
| `Color` | Mode | Smooth | Static, Smooth, Custom | render = Fill, Outline, Both |  | The color that will be used when rendering the block mining. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | render = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | render = Outline, Both |  | The color used for the outline rendering. |
| `InstantRender` | Mode | None | None, Default, Custom |  | Instant | The color that will be used for rendering instantly mined blocks. |
| `InstantColor` | Color | ColorSetting.Color(Color(148, 0, 211), false, false) |  | instantRender = Custom |  | The custom color used for instantly mined blocks. |

### Swing
Allows you to modify the swing animation and the hand that will be used for swinging.

Plik: `night/modules/impl/player/SwingModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Hand` | Mode | Default | Default, None, Packet, Mainhand, Offhand, Both |  |  | The hand that will be used for swinging. |
| `NoPacket` | Boolean | false |  | hand = Default, Mainhand, Offhand, Both |  | Only swings clientside, meaning nobody will be able to tell if you're swinging or not. |
| `Speed` | Category |  |  |  |  | The category containing all settings related to swing speed. |
| `ModifySpeed` | Boolean | false |  |  | Enabled | Modifies the speed of your swing animation. |
| `Speed` | Number | 15 | 1 – 20 | modifySpeed = true | Amount | The speed at which your hand will be swinging. |
| `Translation` | Category |  |  |  |  | The category related to the swing translations. |
| `TranslateX` | Boolean | true |  |  | X | Enables the swing translation on the X axis. |
| `TranslateY` | Boolean | true |  |  | Y | Enables the swing translation on the Y axis. |
| `TranslateZ` | Boolean | true |  |  | Z | Enables the swing translation on the Z axis. |
| `Rotation` | Category |  |  |  |  | The category related to the swing rotations. |
| `RotationX` | Boolean | true |  |  | X | Enables the swing rotation on the X axis. |
| `RotationY` | Boolean | true |  |  | Y | Enables the swing rotation on the Y axis. |
| `RotationZ` | Boolean | true |  |  | Z | Enables the swing rotation on the Z axis. |

### ThrowItem
Throw out items that arent belong to your loaded kit. Useful when you need to loot gears during combat

Plik: `night/modules/impl/player/ThrowItemModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Delay` | Number | 1 | 0 – 20 |  |  | Tick delay between throw actions. |
| `ActionsPerTick` | Number | 3 | 1 – 20 |  |  | Maximum items to drop per pass. |
| `AutoDisable` | Boolean | false |  |  |  | Automatically disables the module when no items are left to throw. |
| `KeepKitItems` | Boolean | true |  |  |  | Keep items that belong to the loaded kit even if in a different slot. |
| `MinimumCount` | Boolean | false |  |  |  | Keep matching items only if their stack count reaches minimum count. |
| `MinCount` | Number | 16 | 1 – 64 | minimumCount = true |  | The minimum stack count to keep an item. |
| `IgnoreHotbar` | Boolean | false |  |  |  | Do not throw items from the hotbar (slots 0-8). |
| `OnlyInventory` | Boolean | true |  |  |  | Only throw items when in regular gameplay or InventoryScreen (not in Chests/Shulkers). |

### Timer
Makes your game run at a faster tick speed.

Plik: `night/modules/impl/player/TimerModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Multiplier` | Number |  |  |  |  | The multiplier that will be added to the game's speed. |

### XCarry
Allows you to carry items in your crafting slots.

Plik: `night/modules/impl/player/XCarryModule.java`

_Moduł nie posiada własnych ustawień._

## Kategoria: VISUALS (30 modułów)

### Ambience
Modifies the world ambient lighting and atmosphere color.

Plik: `night/modules/impl/visuals/AmbienceModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Color` | Color | ColorSetting.Color(Color(255, 128, 128, 255), true, false) |  |  |  | The ambience color applied to the world. |
| `UseSaturation` | Boolean | false |  |  |  | Overrides the saturation of the ambience color. |
| `Saturation` | Number |  |  | useSaturation = true |  | The custom saturation level. |
| `Lightmap` | Boolean | true |  |  |  | Modifies the world lightmap lighting. |
| `Sky` | Boolean | true |  |  |  | Tints sky lighting with ambience. |
| `Blocks` | Boolean | true |  |  |  | Tints block lighting with ambience. |

### ArrowTracers
Renders arrows towards players who are off screen.

Plik: `night/modules/impl/visuals/ArrowTracersModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Width` | Number |  |  |  |  | The width of the arrows being rendered. |
| `Height` | Number |  |  |  |  | The height of the arrows being rendered. |
| `Distance` | Number | 45 | 5 – 200 |  |  | The distance that will be between the crosshair and the arrows. |
| `AntiBot` | Boolean | false |  |  |  | Prevents bots from having arrow tracers rendered for them. |
| `OnScreen` | Boolean | false |  |  |  | Renders the arrow tracers even for players who are on screen. |
| `Alpha` | Mode | Fade | Default, Fade |  |  | The alpha that will be used in the arrow rendering. |
| `FadeDistance` | Number |  |  | alpha = Fade |  | The distance at which the arrows will start fading. |
| `Mode` | Mode | Both | Fill, Outline, Both |  |  | The rendering that will be applied to the arrows |
| `FillColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorSetting.Color(Color(0, 0, 0), true, false) |  | mode = Outline, Both |  | The color used for the outline rendering. |

### AspectRatio
Modifies the game's aspect ratio.

Plik: `night/modules/impl/visuals/AspectRatioModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Ratio` | Number |  |  |  |  | The aspect ratio that will be applied to the game's rendering. |

### Atmosphere
Modifies the world's atmosphere, such as time and color.

Plik: `night/modules/impl/visuals/AtmosphereModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `ModifyTime` | Boolean | true |  |  |  | Modifies the world's time. |
| `Time` | Number | 200 | -200 – 200 | modifyTime = true |  | The time that the world will be set to. |
| `ModifyFog` | Boolean | false |  |  |  | Modifies certain things about the world's fog. |
| `FogStart` | Number | 50 | 0 – 300 | modifyFog = true |  | The start value of the world's fog. |
| `FogEnd` | Number | 150 | 0 – 300 | modifyFog = true |  | The end value of the world's fog. |
| `FogColor` | Color | ColorUtils.getDefaultOutlineColor() |  | modifyFog = true |  | Modifies the color of the world's fog. |
| `ModifySky` | Boolean | false |  |  |  | Modifies the sky rendering. |
| `SkyMode` | Mode | Vanilla | Vanilla, Custom | modifySky = true |  | The sky rendering mode. |
| `SkyColor` | Color | ColorSetting.Color(Color(0, 0, 0, 255), true, false) |  | skyMode = Vanilla |  | The color of the vanilla sky. |
| `CustomSky` | Mode | Nebula | Nebula, Smoke | skyMode = Custom |  | The custom sky shader to use. |
| `Intensity` | Number |  |  | skyMode = Custom |  | The intensity of the sky shader. |
| `Speed` | Number |  |  | skyMode = Custom |  | The speed of the sky shader animation. |
| `SkyTint` | Color | ColorSetting.Color(Color(255, 255, 255), false, false) |  | skyMode = Custom |  | The tint of the sky shader. |
| `Weather` | Mode | Unchanged | Unchanged, Clear, Rain, Thunder, Snow, Dust |  |  | Overrides the world's weather (visual only, not sent to the server). |
| `StarGlow` | Category |  |  |  |  | The category for the star glow effect. |
| `StarGlow` | Boolean | false |  |  | Enabled | Adds a soft glow to vanilla sky stars. |
| `StarGlowIntensity` | Number |  |  |  | Intensity | How strong the glow is. |

### BlockHighlight
Replaces the default Minecraft block highlight with a more customizable one.

Plik: `night/modules/impl/visuals/BlockHighlightModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Animation` | Mode | Static | Static, Slide |  |  | The animation that will be applied to the rendering. |
| `Mode` | Mode | Outline | None, Fill, Outline, Both, Complex |  |  | The rendering that will be applied to the target block. |
| `Smoothness` | Number | 1 | 0 – 20 |  |  | The smoothness for the slide while target block is changing. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color that will be used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color that will be used for the outline rendering. |

### BreakHighlight
Renders blocks that are being mined by other players.

Plik: `night/modules/impl/visuals/BreakHighlightModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Outline | Fill, Outline, Both |  |  | The rendering that will be applied to the mine esp. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color used for the outline rendering. |
| `Name` | Boolean | true |  |  |  | Renders the name of the player mining the block. |
| `NameColor` | Color | ColorSetting.Color(WHITE, false, false) |  | name = true |  | The color used for the name text rendering. |
| `Glow` | Boolean | false |  | name = true |  | Renders a glow shader effect around the breaker name text. |
| `Scale` | Number | 30 | 10 – 100 | name = true |  | The scale of the name text. |

### Chams
Adds a customizable render on top of the default Minecraft rendering.

Plik: `night/modules/impl/visuals/ChamsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number |  |  |  |  | The maximum range at which chams will be rendered. |
| `Entities` | Category |  |  |  |  | The category for settings related to chams rendered on living entities. |
| `Players` | Boolean | true |  |  |  | Renders the chams on player entities. |
| `Hostiles` | Boolean | false |  |  |  | Renders the chams on hostile entities. |
| `Passives` | Boolean | false |  |  |  | Renders the chams on passive entities. |
| `EntityPulse` | Boolean | false |  |  | Pulse | Adds a pulsing effect to the chams opacity. |
| `EntityShine` | Boolean | false |  |  | Shine | Adds a shine effect to the chams. |
| `EntityGlint` | Boolean | false |  |  | Glint | Adds a scrolling texture overlay to living entities. |
| `EntityGlintColor` | Color | ColorSetting.Color(Color(255, 255, 255, 180), false, false) |  | entityGlint = true | GlintColor | Color tint for the entity glint overlay. |
| `EntityGlintSpeedU` | Number |  |  | entityGlint = true | GlintSpeedU | Horizontal scroll speed of entity glint. |
| `EntityGlintSpeedV` | Number |  |  | entityGlint = true | GlintSpeedV | Vertical scroll speed of entity glint. |
| `EntityGlintScale` | Number |  |  | entityGlint = true | GlintScale | Scale of the glint texture on entity models. |
| `EntityGlintDirection` | Mode | Diagonal | Diagonal, Horizontal, Vertical | entityGlint = true | GlintDirection | Projection mode for entity glint UVs. |
| `EntityMode` | Mode | Both | Fill, Outline, Both |  | Mode | The rendering that will be applied to living entities. |
| `EntityFillColor` | Color | ColorUtils.getDefaultFillColor() |  | entityMode = Fill, Both | FillColor | The color that will be used for the fill rendering. |
| `EntityOutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | entityMode = Outline, Both | OutlineColor | The color that will be used for the outline rendering. |
| `FriendMode` | Mode | Default | Default, Custom, Sync | entityMode = Fill, Both |  | The mode for the friend color. |
| `FriendFillColor` | Color | ColorSetting.Color(Color(85, 255, 255, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  | entityMode = Fill, Both | FriendFillColor | The color that will be used for the fill rendering on friends. |
| `FriendOutlineColor` | Color | ColorSetting.Color(Color(85, 255, 255, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  | entityMode = Outline, Both | FriendOutlineColor | The color that will be used for the outline rendering on friends. |
| `DamageModify` | Boolean | false |  | entityMode = Fill, Both |  | Changes the color of the chams when a player takes damage. |
| `DamageColor` | Color | ColorSetting.Color(Color(255, 0, 0), false, false) |  | damageModify = true |  | The color to apply on chams when a player takes damage. |
| `EntityOpacity` | Number | 100 | 0 – 100 |  | Opacity | How see-through the target's real skin renders (100 = untouched). |
| `Crystals` | Category |  |  |  |  | The category for settings related to crystal chams. |
| `Crystals` | Boolean | true |  |  | Enabled | Renders the chams on crystal entities. |
| `CrystalPulse` | Boolean | false |  |  | Pulse | Adds a pulsing effect to the chams opacity. |
| `CrystalShine` | Boolean | false |  |  | Shine | Adds a shine effect on crystal chams. |
| `CrystalGlint` | Boolean | false |  |  | Glint | Adds a scrolling texture overlay to crystals. |
| `CrystalGlintColor` | Color | ColorSetting.Color(Color(255, 255, 255, 180), false, false) |  | crystalGlint = true | GlintColor | Color tint for the crystal glint overlay. |
| `CrystalGlintSpeedU` | Number |  |  | crystalGlint = true | GlintSpeedU | Horizontal scroll speed of crystal glint. |
| `CrystalGlintSpeedV` | Number |  |  | crystalGlint = true | GlintSpeedV | Vertical scroll speed of crystal glint. |
| `CrystalGlintScale` | Number |  |  | crystalGlint = true | GlintScale | Scale of the glint texture on crystal models. |
| `CrystalGlintDirection` | Mode | Diagonal | Diagonal, Horizontal, Vertical | crystalGlint = true | GlintDirection | Projection mode for crystal glint UVs. |
| `CrystalMode` | Mode | Both | Fill, Outline, Both |  | Mode | The rendering that will be applied to crystal entities. |
| `CrystalFillColor` | Color | ColorUtils.getDefaultFillColor() |  | crystalMode = Fill, Both | FillColor | The color that will be used for the fill rendering. |
| `CrystalOutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | crystalMode = Outline, Both | OutlineColor | The color that will be used for the outline rendering. |
| `CrystalOpacity` | Number | 100 | 0 – 100 |  | Opacity | How see-through the crystal's real texture renders (100 = untouched). |
| `Vehicles` | Category |  |  |  |  | The category for settings related to boat/minecart chams. |
| `Vehicles` | Boolean | true |  |  | Enabled | Renders the chams on all boat and minecart types. |
| `VehiclePulse` | Boolean | false |  |  | Pulse | Adds a pulsing effect to the chams opacity. |
| `VehicleShine` | Boolean | false |  |  | Shine | Adds a shine effect on vehicle chams. |
| `VehicleMode` | Mode | Both | Fill, Outline, Both |  | Mode | The rendering that will be applied to boat/minecart entities. |
| `VehicleFillColor` | Color | ColorUtils.getDefaultFillColor() |  | vehicleMode = Fill, Both | FillColor | The color that will be used for the fill rendering. |
| `VehicleOutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | vehicleMode = Outline, Both | OutlineColor | The color that will be used for the outline rendering. |
| `VehicleOpacity` | Number | 100 | 0 – 100 |  | Opacity | How see-through the vehicle's real texture renders (100 = untouched). |

### ChestScan
Highlights opened chests by contents (empty/partial/full), with optional hopper-chain inference.

Plik: `night/modules/impl/visuals/ChestScanModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `ScanRadius` | Number | 64.0 | 8.0 – 128.0 |  |  | How far (in blocks) to render tracked chests and consider hopper chains. |
| `HopperChain` | Boolean | false |  |  |  | Smart mode to check chests linked to the bottom chest by hoppers. |
| `Mode` | Mode | Both | Both, Fill, Outline |  |  | Render mode for chests. |
| `Empty` | Category |  |  |  |  | Color for empty chests. |
| `EmptyFillColor` | Color | ColorSetting.Color(Color(0, 200, 0, 120), false, false) |  |  | Fill | Fill color for empty chests. |
| `EmptyOutlineColor` | Color | ColorSetting.Color(Color(0, 200, 0, 255), false, false) |  |  | Outline | Outline color for empty chests. |
| `Partial` | Category |  |  |  |  | Color for partially filled chests. |
| `PartialFillColor` | Color | ColorSetting.Color(Color(230, 200, 0, 120), false, false) |  |  | Fill | Fill color for partially filled chests. |
| `PartialOutlineColor` | Color | ColorSetting.Color(Color(230, 200, 0, 255), false, false) |  |  | Outline | Outline color for partially filled chests. |
| `Full` | Category |  |  |  |  | Color for full chests. |
| `FullFillColor` | Color | ColorSetting.Color(Color(220, 0, 0, 120), false, false) |  |  | Fill | Fill color for full chests. |
| `FullOutlineColor` | Color | ColorSetting.Color(Color(220, 0, 0, 255), false, false) |  |  | Outline | Outline color for full chests. |

### DeathEffects
Renders certain effects on players when they die or disconnect.

Plik: `night/modules/impl/visuals/DeathEffectsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Both | Lightning, Overlay, Meme, Both |  |  | The effect that will be rendered. |
| `LightningOnKill` | Boolean | true |  | mode = Lightning, Both |  | Spawn lightning bolt when a player dies near you. |
| `LightningOnDisconnect` | Boolean | true |  | mode = Lightning, Both |  | Spawn lightning bolt when a player disconnects near you. |
| `LightningAmount` | Number | 1 | 1 – 10 | mode = Lightning, Both |  | The amount of lightning bolts to spawn. |
| `DisconnectRange` | Number | 64.0 | 1.0 – 256.0 |  |  | Range to detect player disconnects. |
| `OverlayOnKill` | Boolean | true |  | mode = Overlay |  | Show freeze overlay when a player dies near you. |
| `OverlayOnDisconnect` | Boolean | true |  | mode = Overlay |  | Show freeze overlay when a player disconnects near you. |
| `OverlayDuration` | Number | 3000 | 100 – 10000 | mode = Overlay |  | Duration of the freeze overlay in milliseconds. |
| `OverlayFadeDuration` | Number | 2000 | 100 – 5000 | mode = Overlay |  | Fade duration of the freeze overlay in milliseconds. |
| `OverlayAlpha` | Number | 200 | 0 – 255 | mode = Overlay |  | Transparency of the freeze overlay (0-255, higher = more transparent). |
| `MemeOnKill` | Boolean | true |  | mode = Meme, Both |  | Show the meme effect when a player dies near you. |
| `MemeOnDisconnect` | Boolean | true |  | mode = Meme, Both |  | Show the meme effect when a player disconnects near you. |
| `MemeSize` | Number | 1.0 | 0.3 – 3.0 | mode = Meme, Both |  | Half-size of the circle/arrow, in blocks. |

### ESP
Renders a box ESP around entities that you have selected.

Plik: `night/modules/impl/visuals/ESPModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Players` | Boolean | true |  |  |  | Renders the box ESP on player entities. |
| `Hostiles` | Boolean | true |  |  |  | Renders the box ESP on hostile entities. |
| `Animals` | Boolean | true |  |  |  | Renders the box ESP on animal entities. |
| `Ambient` | Boolean | false |  |  |  | Renders the box ESP on ambient entities. |
| `Invisibles` | Boolean | true |  |  |  | Renders the box ESP on invisible entities. |
| `Items` | Boolean | true |  |  |  | Renders the box ESP on item entities. |
| `Others` | Boolean | false |  |  |  | Renders the box ESP on miscellaneous entities. |
| `Block` | Boolean | false |  |  |  | Renders a box ESP on whitelisted blocks (portals, ores, ...). |
| `Whitelist` | Whitelist | WhitelistSetting.Type.BLOCKS |  | blocks = true |  | Blocks to highlight. |
| `Mode` | Mode | Both | None, Fill, Outline, Both |  |  | The rendering that will be applied to the target entities. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color that will be used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color that will be used for the outline rendering. |

### Freecam
Allows you to move your camera anywhere you want without restriction.

Plik: `night/modules/impl/visuals/FreecamModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `HorizontalSpeed` | Number |  |  |  |  | The speed at which your camera will move horizontally. |
| `VerticalSpeed` | Number |  |  |  |  | The speed at which your camera will move vertically. |
| `Rotate` | Boolean | false |  |  |  | Rotates your real player model along with the camera. |
| `Shift` | Boolean | false |  |  |  | Allows moving down with shift even when a GUI is open. |

### FullBright
Gives you the ability to clearly see even when in the dark.

Plik: `night/modules/impl/visuals/FullBrightModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Gamma | Gamma, Potion |  |  | The way that will be used to change the game's brightness. |

### HoleESP
Highlights all holes in a specified radius.

Plik: `night/modules/impl/visuals/HoleESPModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number | 10 | 1 – 50 |  |  | The maximum range at which holes will be rendered. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `DoubleHoles` | Boolean | true |  |  |  | Whether or not to render the ESP on double holes. |
| `QuadHoles` | Boolean | true |  |  |  | Whether or not to render the ESP on quad holes. |
| `PartialHoles` | Boolean | true |  |  |  | Whether or not to render the ESP on partial (incomplete) holes. |
| `Fade` | Boolean | false |  |  |  | Fades the holes in and out based on your distance to them. |
| `Fill` | Mode | Normal | None, Normal, Gradient |  |  | The mode for the fill rendering on the hole boxes. |
| `FillHeight` | Number | 1.0 | -2.0 – 2.0 | fill = Normal, Gradient |  | The height of the fill rendering on the holes. |
| `Outline` | Mode | Normal | None, Normal, Gradient |  |  | The mode for the outline rendering on the hole boxes. |
| `OutlineHeight` | Number | 1.0 | -2.0 – 2.0 | outline = Normal, Gradient |  | The height of the outline rendering on the holes. |
| `Wireframe` | Boolean | false |  |  |  | Renders an X wireframe on the hole boxes. |
| `Safe` | Category |  |  |  |  | The category that contains the settings for coloring of safe (all-bedrock) holes. |
| `SafeFillColor` | Color | ColorSetting.Color(Color(0, 255, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering on safe holes. |
| `SafeOutlineColor` | Color | ColorSetting.Color(Color(0, 255, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering on safe holes. |
| `SafeWireframeColor` | Color | ColorSetting.Color(Color(0, 255, 0, 125), false, false) |  |  | Wireframe | The color for the wireframe rendering on safe holes. |
| `SafeShader` | Mode | None |  |  | Shader | The animated shader for safe holes. |
| `SafeShaderOpacity` | Number | 100 | 0 – 100 | safeShader = EspShader.SHADER_ACTIVE_MODES | Opacity | Safe hole shader opacity. |
| `SafeShaderSpeed` | Number | 1.0 | 0.1 – 10.0 | safeShader = EspShader.SHADER_ACTIVE_MODES | Speed | Safe hole shader speed. |
| `SafeShaderStep` | Number | 50.0 | 0.1 – 200.0 | safeShader = Gradient | Step | Safe hole gradient step size. |
| `SafeShaderColor1` | Color | ColorSetting.Color(Color(0, 255, 0, 255), false, false) |  | safeShader = Gradient |  | The first gradient color for safe holes. |
| `SafeShaderColor2` | Color | ColorSetting.Color(Color(0, 200, 100, 255), false, false) |  | safeShader = Gradient |  | The second gradient color for safe holes. |
| `SafeShaderColor3` | Color | ColorSetting.Color(Color(0, 255, 200, 255), false, false) |  | safeShader = Gradient |  | The third gradient color for safe holes. |
| `SafeShaderColor4` | Color | ColorSetting.Color(Color(100, 255, 0, 255), false, false) |  | safeShader = Gradient |  | The fourth gradient color for safe holes. |
| `SafeShaderGlowColor` | Color | ColorSetting.Color(Color(0, 255, 0, 255), false, false) |  | safeShader = Glowing | GlowColor | The glow tint color for safe holes. |
| `Unsafe` | Category |  |  |  |  | The category that contains the settings for coloring of unsafe (blast-proof but not pure bedrock) holes. |
| `UnsafeFillColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering on unsafe holes. |
| `UnsafeOutlineColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering on unsafe holes. |
| `UnsafeWireframeColor` | Color | ColorSetting.Color(Color(255, 0, 0, 125), false, false) |  |  | Wireframe | The color for the wireframe rendering on unsafe holes. |
| `UnsafeShader` | Mode | None |  |  | Shader | The animated shader for unsafe holes. |
| `UnsafeShaderOpacity` | Number | 100 | 0 – 100 | unsafeShader = EspShader.SHADER_ACTIVE_MODES | Opacity | Unsafe hole shader opacity. |
| `UnsafeShaderSpeed` | Number | 1.0 | 0.1 – 10.0 | unsafeShader = EspShader.SHADER_ACTIVE_MODES | Speed | Unsafe hole shader speed. |
| `UnsafeShaderStep` | Number | 50.0 | 0.1 – 200.0 | unsafeShader = Gradient | Step | Unsafe hole gradient step size. |
| `UnsafeShaderColor1` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | unsafeShader = Gradient |  | The first gradient color for unsafe holes. |
| `UnsafeShaderColor2` | Color | ColorSetting.Color(Color(255, 100, 0, 255), false, false) |  | unsafeShader = Gradient |  | The second gradient color for unsafe holes. |
| `UnsafeShaderColor3` | Color | ColorSetting.Color(Color(255, 0, 100, 255), false, false) |  | unsafeShader = Gradient |  | The third gradient color for unsafe holes. |
| `UnsafeShaderColor4` | Color | ColorSetting.Color(Color(200, 0, 0, 255), false, false) |  | unsafeShader = Gradient |  | The fourth gradient color for unsafe holes. |
| `UnsafeShaderGlowColor` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | unsafeShader = Glowing | GlowColor | The glow tint color for unsafe holes. |
| `Partial` | Category |  |  |  |  | The category that contains the settings for coloring of partial (incomplete) holes. |
| `PartialFillColor` | Color | ColorSetting.Color(Color(255, 255, 255, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering on partial holes. |
| `PartialOutlineColor` | Color | ColorSetting.Color(Color(255, 255, 255, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering on partial holes. |
| `PartialWireframeColor` | Color | ColorSetting.Color(Color(255, 255, 255, 125), false, false) |  |  | Wireframe | The color for the wireframe rendering on partial holes. |
| `PartialShader` | Mode | None |  |  | Shader | The animated shader for partial holes. |
| `PartialShaderOpacity` | Number | 100 | 0 – 100 | partialShader = EspShader.SHADER_ACTIVE_MODES | Opacity | Partial hole shader opacity. |
| `PartialShaderSpeed` | Number | 1.0 | 0.1 – 10.0 | partialShader = EspShader.SHADER_ACTIVE_MODES | Speed | Partial hole shader speed. |
| `PartialShaderStep` | Number | 50.0 | 0.1 – 200.0 | partialShader = Gradient | Step | Partial hole gradient step size. |
| `PartialShaderColor1` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  | partialShader = Gradient |  | The first gradient color for partial holes. |
| `PartialShaderColor2` | Color | ColorSetting.Color(Color(200, 200, 200, 255), false, false) |  | partialShader = Gradient |  | The second gradient color for partial holes. |
| `PartialShaderColor3` | Color | ColorSetting.Color(Color(220, 220, 220, 255), false, false) |  | partialShader = Gradient |  | The third gradient color for partial holes. |
| `PartialShaderColor4` | Color | ColorSetting.Color(Color(180, 180, 180, 255), false, false) |  | partialShader = Gradient |  | The fourth gradient color for partial holes. |
| `PartialShaderGlowColor` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  | partialShader = Glowing | GlowColor | The glow tint color for partial holes. |

### Icons
Renders icons for specific events.

Plik: `night/modules/impl/visuals/IconsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Chorus` | Category |  |  |  |  | The category for chorus icons. |
| `Chorus` | Boolean | true |  |  | Enabled | Renders an icon for chorus positions. |
| `ChorusColor` | Color | ColorSetting.Color(Color(192, 147, 212), false, false) |  |  | Color | The color for the chorus icons. |
| `Duration` | Number | 1500 | 0 – 5000 |  |  | The duration of icon renders. |
| `Pearls` | Category |  |  |  |  | The category for pearl icons. |
| `Pearls` | Boolean | true |  |  | Enabled | Renders an icon for pearl positions. |
| `PearlsColor` | Color | ColorSetting.Color(Color(30, 131, 89), false, false) |  |  | Color | The color for the pearl icons. |
| `Scale` | Number | 40 | 10 – 50 |  |  | The scaling that will be applied to the text ESP rendering. |

### LogoutSpot
Renders a frozen chams model and nametag where players logged out.

Plik: `night/modules/impl/visuals/LogoutSpotModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Both | Fill, Outline, Both |  |  | The rendering that will be applied to the logout chams. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color used for the outline rendering. |
| `Nametag` | Boolean | true |  |  |  | Renders player info above the logout spot. |
| `Health` | Boolean | true |  |  |  | Renders the health of the logged out player. |
| `TotemPops` | Boolean | true |  |  |  | Renders the popped totems count of the logged out player. |
| `Scale` | Number | 30 | 10 – 100 |  |  | The scaling applied to the nametag rendering. |

### MotionCamera
Smooths camera movement and adds smooth perspective transitions.

Plik: `night/modules/impl/visuals/MotionCameraModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `NoFirstPerson` | Boolean | true |  |  |  | Disables motion camera in first person. |
| `PearlException` | Boolean | true |  | noFirstPerson = true |  | Still smooths in 1st person for a moment after throwing an ender pearl (ignores NoFirstPerson). |
| `RubberbandException` | Boolean | true |  | noFirstPerson = true |  | Still smooths in 1st person for a moment after a rubberband/teleport correction (ignores NoFirstPerson). |
| `ExceptionDuration` | Number | 500 | 100 – 2000 | noFirstPerson = true |  | How long the pearl/rubberband exception window lasts, in ms. |
| `FirstPersonSpeed` | Number | 0.6 | 0.01 – 1.0 |  |  | Movement smoothness speed in first person. |
| `Speed` | Number | 0.3 | 0.01 – 1.0 |  |  | Movement smoothness speed in third person. |
| `SmoothPerspective` | Boolean | true |  |  |  | Smoothly transitions distance when switching between 1st and 3rd person. |
| `PerspectiveSpeed` | Number | 0.5 | 0.05 – 1.0 |  |  | Speed of perspective switch transition. |

### NameTags
Replaces the default Minecraft NameTag with a more visible and customizable one.

Plik: `night/modules/impl/visuals/NameTagsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `GameMode` | Boolean | false |  |  |  | Renders the player's gamemode. |
| `Ping` | Boolean | true |  |  |  | Renders the player's latency to the server. |
| `EntityID` | Boolean | false |  |  |  | Renders the player's entity ID. |
| `Health` | Boolean | true |  |  |  | Renders the player's health and absorption. |
| `TotemPops` | Boolean | true |  |  |  | Renders the amount of totems that the player has popped. |
| `AntiBot` | Boolean | false |  |  |  | Prevents bots from having nametags rendered for them. |
| `NightCheck` | Boolean | true |  |  |  | Adds an indicator next to the name of other Night Client users. |
| `Self` | Boolean | false |  |  |  | Also renders a nametag above your own player. |
| `Items` | Boolean | true |  |  |  | Renders the items that the player is wearing or holding. |
| `Enchantments` | Boolean | false |  |  |  | Renders the enchantments of the player's items. |
| `Durability` | Boolean | true |  |  |  | Renders the durability of the player's items. |
| `ItemName` | Boolean | true |  |  |  | Renders the name of the item that the player is currently holding. |
| `Glow` | Boolean | false |  |  |  | Renders a glow shader effect around nametag text. |
| `Scale` | Number | 30 | 10 – 100 |  |  | The scaling that will be applied to the nametag rendering. |
| `Border` | Mode | Both | None, Fill, Outline, Both |  |  | The border that will surround the text. |
| `FillColor` | Color | ColorSetting.Color(Color(0, 0, 0, 100), false, false) |  | border = Fill, Both |  | The color that will be used for the fill rendering. |
| `OutlineColor` | Color | ColorSetting.Color(Color(0, 0, 0, 100), false, false) |  | border = Outline, Both |  | The color that will be used for the outline rendering. |

### NoRender
Disables the rendering of certain things.

Plik: `night/modules/impl/visuals/NoRenderModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `HurtCamera` | Boolean | true |  |  |  | Disables the rendering of the hurt camera. |
| `Explosions` | Boolean | true |  |  |  | Disables the rendering of explosion particles. |
| `FireOverlay` | Boolean | true |  |  |  | Disables the rendering of the fire overlay. |
| `BlockOverlay` | Boolean | false |  |  |  | Disables the rendering of the block suffocation overlay. |
| `LiquidOverlay` | Boolean | false |  |  |  | Disables the rendering of the liquid overlay. |
| `SnowOverlay` | Boolean | false |  |  |  | Disables the rendering of the snow overlay. |
| `PumpkinOverlay` | Boolean | true |  |  |  | Disables the rendering of the pumpkin overlay. |
| `PortalOverlay` | Boolean | false |  |  |  | Disables the rendering of the portal overlay. |
| `TotemAnimation` | Boolean | false |  |  |  | Disables the rendering of the totem pop animation. |
| `TotemPop` | Boolean | false |  |  |  | Disables the rendering of totem pop particles. |
| `BossBar` | Boolean | false |  |  |  | Disables the rendering of the boss bar. |
| `Scoreboard` | Boolean | false |  |  |  | Disables the rendering of the scoreboard. |
| `Vignette` | Boolean | true |  |  |  | Disables the rendering of the vignette. |
| `Blindness` | Boolean | true |  |  |  | Disables the rendering of the blindness and darkness potion effects. |
| `Fog` | Boolean | false |  |  |  | Disables the rendering of the fog. |
| `SignText` | Boolean | false |  |  |  | Disables the rendering of sign text. |
| `Armor` | Boolean | false |  |  |  | Disables the rendering of armor. |
| `LimbSwing` | Boolean | false |  |  |  | Disables the rendering of limb swing animations. |
| `Corpses` | Boolean | false |  |  |  | Disables the rendering of corpses. |
| `Player` | Boolean | false |  |  |  | Disables the rendering of other players standing in the same spot as you. |
| `Background` | Boolean | false |  |  |  | Disables the dark background dimming behind inventory/other screens. |
| `TileEntities` | Mode | Never | Never, Distance, Always |  |  | Disables the rendering of tile entities, such as chests, when meeting requirements. |
| `TileDistance` | Number |  |  | tileEntities = Distance |  | The distance at which tile entities will stop rendering. |
| `Items` | Boolean | false |  |  |  | Limits how many dropped items are rendered in view. |
| `Limit` | Number | 100 | 0 – 100 | items = true |  | Max number of items to render per frame. |
| `Displays` | Boolean | false |  |  |  | Disables the rendering of Display entities (BlockDisplay, ItemDisplay, TextDisplay). |
| `ArmorStand` | Boolean | false |  |  |  | Disables the rendering of armor stands. |
| `Minecart` | Boolean | false |  |  |  | Disables the rendering of minecarts. |
| `Advancements` | Boolean | false |  |  |  | Disables the rendering of advancement/recipe toast popups. |
| `ItemName` | Boolean | false |  |  |  | Hides the item name popup that shows above the hotbar when switching items. |
| `Wither` | Boolean | false |  |  |  | Stops hearts turning black/withered when the player has Wither effect. |

### Particles
Custom particle effects for totem pops and entity hits.

Plik: `night/modules/impl/visuals/ParticlesModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Totem` | Category |  |  |  |  | Settings for totem pop particle effects. |
| `Totem` | Boolean | true |  |  | Enabled | Enables totem particle customization. |
| `TotemMode` | Mode | Vanilla | Vanilla, Custom |  | Mode | Particle mode for totem pops. |
| `TotemNumber` | Number | 15 | 1 – 100 |  | Number | Amount of particles per totem pop. |
| `VanillaColorA` | Color | ColorSetting.Color(Color(255, 255, 255), false, false) |  | totemMode = Vanilla | Color A | Primary particle color for vanilla totem pop. |
| `VanillaColorB` | Color | ColorSetting.Color(Color(255, 255, 255), false, false) |  | totemMode = Vanilla | Color B | Fade particle color for vanilla totem pop. |
| `CustomColorA` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  | totemMode = Custom | Color A | Primary particle color. |
| `CustomColorB` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  | totemMode = Custom | Color B | Secondary particle color. |
| `TotemGlow` | Boolean | true |  | totemMode = Custom | Glow | Adds a glow effect to totem particles. |
| `TotemPhysics` | Boolean | true |  | totemMode = Custom | Physics | Enables gravity and bouncing for totem particles. |
| `TotemVelocityX` | Number | 1.0 | -5.0 – 5.0 | totemMode = Custom | X | X velocity multiplier. |
| `TotemVelocityY` | Number | 1.0 | -5.0 – 5.0 | totemMode = Custom | Y | Y velocity multiplier. |
| `TotemVelocityZ` | Number | 1.0 | -5.0 – 5.0 | totemMode = Custom | Z | Z velocity multiplier. |
| `Hit` | Category |  |  |  |  | Settings for hit particle effects. |
| `Hit` | Boolean | true |  |  | Enabled | Enables hit particles. |
| `HitNumber` | Number | 15 | 1 – 100 |  | Number | Amount of particles per hit. |
| `HitColorA` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  |  | Color A | Primary particle color. |
| `HitColorB` | Color | ColorSetting.Color(Color(255, 255, 255, 255), false, false) |  |  | Color B | Secondary particle color. |
| `HitGlow` | Boolean | true |  |  | Glow | Adds a glow effect to hit particles. |
| `HitPhysics` | Boolean | false |  |  | Physics | Enables gravity and bouncing for hit particles. |

### PhaseESP
Renders ESP on blocks which have a safe base.

Plik: `night/modules/impl/visuals/PhaseESPModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Fill` | Mode | Normal | None, Normal |  |  | The mode for the fill rendering on the phase blocks. |
| `FillHeight` | Number | 1.0 | -2.0 – 2.0 | fill = Normal |  | The height of the fill rendering on the phase blocks. |
| `Outline` | Mode | Normal | None, Normal |  |  | The mode for the outline rendering on the phase blocks. |
| `OutlineHeight` | Number | 1.0 | -2.0 – 2.0 | outline = Normal |  | The height of the outline rendering on the phase blocks. |
| `Bedrock` | Category |  |  |  |  | The bedrock base category. |
| `Bedrock` | Boolean | true |  |  | Enabled | Renders blocks which have a bedrock base. |
| `BedrockFillColor` | Color | ColorSetting.Color(Color(0, 255, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering of bedrock base blocks. |
| `BedrockOutlineColor` | Color | ColorSetting.Color(Color(0, 255, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering of bedrock base blocks. |
| `Obsidian` | Category |  |  |  |  | The obsidian base category. |
| `Obsidian` | Boolean | true |  |  | Enabled | Renders blocks which have a obsidian base. |
| `ObsidianFillColor` | Color | ColorSetting.Color(Color(255, 255, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering of obsidian base blocks. |
| `ObsidianOutlineColor` | Color | ColorSetting.Color(Color(255, 255, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering of obsidian base blocks. |
| `Air` | Category |  |  |  |  | The air base category. |
| `Air` | Boolean | true |  |  | Enabled | Renders blocks which have a air base. |
| `AirFillColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  |  | Fill | The color for the fill rendering of air base blocks. |
| `AirOutlineColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  |  | Outline | The color for the outline rendering of air base blocks. |

### PopChams
Renders chams on a frozen snapshot of the entity's pose the moment they pop a totem.

Plik: `night/modules/impl/visuals/PopChamsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Duration` | Number | 1500 | 0 – 5000 |  |  | The duration for the pop chams fade. |
| `YOffset` | Number | 0 | -5 – 5 |  |  | The vertical float/rise offset for pop chams. |
| `Mode` | Mode | Both | Fill, Outline, Both |  |  | The rendering that will be applied to the pop chams. |
| `FillColor` | Color | ColorUtils.getDefaultFillColor() |  | mode = Fill, Both |  | The color used for the fill rendering. |
| `OutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | mode = Outline, Both |  | The color used for the outline rendering. |

### Shaders
Overlays specified entities with a customizable shader.

Plik: `night/modules/impl/visuals/ShadersModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number |  |  |  |  | The maximum range at which shaders will be rendered. |
| `Targets` | Category |  |  |  |  | The things that the shader rendering will be applied onto. |
| `Players` | Boolean | true |  |  |  | Renders the shader effect on player entities. |
| `Hands` | Boolean | true |  |  |  | Renders the shader effect on first-person hands. |
| `Hostiles` | Boolean | true |  |  |  | Renders the shader effect on hostile entities. |
| `Animals` | Boolean | true |  |  |  | Renders the shader effect on animal entities. |
| `Ambient` | Boolean | false |  |  |  | Renders the shader effect on ambient entities. |
| `Invisibles` | Boolean | true |  |  |  | Renders the shader effect on invisible entities. |
| `Items` | Boolean | true |  |  |  | Renders the shader effect on item entities. |
| `Crystals` | Boolean | true |  |  |  | Renders the shader effect on crystal entities. |
| `Pearl` | Boolean | true |  |  |  | Renders the shader effect on thrown ender pearls. |
| `Boat` | Boolean | true |  |  |  | Renders the shader effect on all boat types. |
| `Minecart` | Boolean | true |  |  |  | Renders the shader effect on all minecart types. |
| `Others` | Boolean | false |  |  |  | Renders the shader effect on miscellaneous entities. |
| `Mode` | Mode | Both | Fill, Outline, Both, Glow, Bloom |  |  | The rendering that will be applied to the target entities. |
| `GlowIntensity` | Number | 50 | 0 – 100 | mode = Glow | Glow intensity | How wide and how bright the glow around and over the target entities is. |
| `GlowColor` | Color | ColorSetting.Color(Color(255, 0, 255, 255), false, false) |  | mode = Glow |  | The color that will be used for the outer glow effect. |
| `BloomWidth` | Number | 3.0 | 1.0 – 10.0 | mode = Bloom | Width | Bloom outline width/radius. |
| `BloomGlowInside` | Boolean | true |  | mode = Bloom | GlowInside | Enables bloom/glow on inside of silhouette. |
| `BloomQuality` | Number | 1 | 1 – 4 | mode = Bloom | Quality | Glow sample step quality. |
| `BloomMultiplier` | Number | 1.0 | 0.1 – 5.0 | mode = Bloom | Multiplier | Glow brightness multiplier. |
| `BloomFillAlpha` | Number | 100 | 0 – 100 | mode = Bloom | FillAlpha | Alpha opacity of fill. |
| `BloomOutlineAlpha` | Number | 100 | 0 – 100 | mode = Bloom | OutlineAlpha | Alpha opacity of outline/glow. |
| `BloomFillMode` | Mode | Color | Color, Gradient, Rainbow | mode = Bloom | FillMode | Fill pattern mode. |
| `BloomGradientColor` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | mode = Bloom | GradientColor | Gradient target color. |
| `BloomGradientFactor` | Number | 200.0 | 10.0 – 500.0 | mode = Bloom | GradientFactor | Scale factor for gradient fill. |
| `Color` | Color | ColorUtils.getDefaultColor() |  |  |  | The color that will be used for the fill rendering. |
| `Friends` | Mode | Default | Default, Custom, Sync |  |  | The color that will be applied to friended entities. |
| `FriendColor` | Color | ColorSetting.Color(Color(85, 255, 255, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  | friends = Custom |  | The color that will be used for the shader effect on friends. |
| `Shader` | Mode | None |  |  |  | The animated shader that will be drawn on the target entities instead of a flat color. |
| `ShaderOpacity` | Number | 100 | 0 – 100 | shader = SHADER_ACTIVE_MODES | Opacity | The opacity of the animated shader pattern itself, independent of Color's own alpha. |
| `ShaderSpeed` | Number |  |  | shader = SHADER_ACTIVE_MODES | Speed | The speed at which the shader animates. |
| `ShaderStep` | Number |  |  | shader = Gradient | Step | The size of the gradient bands. |
| `ShaderColor1` | Color | ColorSetting.Color(Color(255, 0, 255, 255), false, false) |  | shader = Gradient |  | The first gradient color. |
| `ShaderColor2` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | shader = Gradient |  | The second gradient color. |
| `ShaderColor3` | Color | ColorSetting.Color(Color(0, 255, 0, 255), false, false) |  | shader = Gradient |  | The third gradient color. |
| `ShaderColor4` | Color | ColorSetting.Color(Color(0, 0, 255, 255), false, false) |  | shader = Gradient |  | The fourth gradient color. |
| `ShaderGlowColor` | Color | ColorSetting.Color(Color(255, 0, 255, 255), false, false) |  | shader = Glowing | GlowColor | The color that the glow shader will be tinted with. |
| `Image` | Image |  | "None" | shader = Image | Select | Select an image from the images directory to project onto targets. |

### StashFinder
Scans loaded chunks for loot stashes and storage animals, alerting via chat and Discord webhook.

Plik: `night/modules/impl/visuals/StashFinderModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Containers` | Category |  |  |  |  | Threshold settings for containers. |
| `Chests` | Number | 10 | 1 – 64 |  |  | Minimum chests required. |
| `Barrels` | Number | 10 | 1 – 64 |  |  | Minimum barrels required. |
| `Shulkers` | Number | 4 | 1 – 64 |  |  | Minimum shulker boxes required. |
| `EnderChests` | Number | 2 | 1 – 64 |  |  | Minimum ender chests required. |
| `Hoppers` | Number | 10 | 1 – 64 |  |  | Minimum hoppers required. |
| `Dispensers/Droppers` | Number | 10 | 1 – 64 |  |  | Minimum dispensers or droppers required. |
| `Furnaces` | Number | 10 | 1 – 64 |  |  | Minimum furnaces required. |
| `Crafters` | Number | 4 | 1 – 64 |  |  | Minimum crafters required. |
| `Animals` | Category |  |  |  |  | Threshold settings for storage animals. |
| `Donkeys` | Number | 2 | 1 – 16 |  |  | Minimum donkeys required. |
| `Llamas` | Number | 2 | 1 – 16 |  |  | Minimum llamas required. |
| `ChestBoats` | Number | 1 | 1 – 16 |  |  | Minimum chest boats required. |
| `General` | Category |  |  |  |  | General settings. |
| `IgnoreNatural` | Boolean | true |  |  |  | Ignores naturally generated structures. |
| `ChatNotify` | Boolean | true |  |  |  | Notifies in chat when a stash is found. |
| `RenderHighlight` | Boolean | true |  |  |  | Highlights found stash chunks in the world. |
| `HighlightColor` | Color | ColorSetting.Color(Color(0, 230, 120, 100), false, false) |  |  |  | Highlight color for stash chunks. |
| `Webhook` | Category |  |  |  |  | Discord webhook settings. |
| `SendWebhook` | Boolean | true |  |  |  | Sends stash alerts to Discord webhook. |
| `WebhookURL` | String |  |  |  |  | Discord webhook URL. |
| `UserID` | String |  |  |  |  | Discord User ID to ping. |

### TextESP
Renders text ESP on the world.

Plik: `night/modules/impl/visuals/TextESPModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Items` | Boolean | true |  |  |  | Renders text ESP on item entities. |
| `Mode` | Mode | WhiteList | WhiteList, BlackList, All | items = true |  | WhiteList = only listed items. BlackList = items NOT in list. All = render on every item. |
| `Whitelist` | Whitelist | WhitelistSetting.Type.ITEMS |  | items = true |  | Items the WhiteList/BlackList mode compares against. |
| `Pearls` | Boolean | true |  |  |  | Renders text ESP on pearl entities. |
| `Chorus` | Boolean | true |  |  |  | Renders text ESP on chorus sounds. |
| `Scale` | Number | 30 | 10 – 100 |  |  | The scaling that will be applied to the text ESP rendering. |
| `Color` | Color | ColorSetting.Color(WHITE, false, false) |  |  |  | The color that will be used for the text ESP rendering. |
| `Glow` | Boolean | false |  |  |  | Renders a glow shader effect around text ESP. |

### Tracers
Renders a line showing where other players are located.

Plik: `night/modules/impl/visuals/TracersModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `AntiBot` | Boolean | false |  |  |  | Prevents bots from having arrow tracers rendered for them. |
| `Mode` | Mode | Distance | Distance, Custom |  |  | The mode for the tracers color. |
| `Color` | Color | ColorUtils.getDefaultOutlineColor() |  |  |  | The color used for the fill rendering. |

### Trails
Renders smooth trails behind projectiles and throwables.

Plik: `night/modules/impl/visuals/TrailsModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Targets` | Category |  |  |  |  | The projectiles to render trails for. |
| `Pearls` | Boolean | true |  |  |  | Renders trails behind ender pearls. |
| `Arrows` | Boolean | true |  |  |  | Renders trails behind arrows. |
| `WitherSkull` | Boolean | true |  |  |  | Renders trails behind wither skulls. |
| `Egg` | Boolean | true |  |  |  | Renders trails behind eggs. |
| `Snowball` | Boolean | true |  |  |  | Renders trails behind snowballs. |
| `FireCharge` | Boolean | true |  |  |  | Renders trails behind fire charges. |
| `Fade` | Number | 1000 | 100 – 5000 |  |  | The duration in milliseconds before a trail segment fades away. |
| `Color` | Color | ColorUtils.getDefaultOutlineColor() |  |  |  | The color used for the trails. |

### Trajectories
Draws a predicted trajectory of where throwables will end up when you throw them.

Plik: `night/modules/impl/visuals/TrajectoriesModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `LineColor` | Color | ColorUtils.getDefaultOutlineColor() |  |  |  | The color of the trajectory line. |
| `Entities` | Category |  |  |  |  | The rendering that will be applied to entities hit by the trajectory. |
| `EntitiesMode` | Mode | Both | None, Fill, Outline, Both |  | Mode | The rendering that will be applied to the target entity. |
| `EntitiesFillColor` | Color | ColorUtils.getDefaultFillColor() |  | entitiesMode = Fill, Both | FillColor | The color that will be used for the fill rendering. |
| `EntitiesOutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | entitiesMode = Outline, Both | OutlineColor | The color that will be used for the outline rendering. |
| `Blocks` | Category |  |  |  |  | The rendering that will be applied to blocks hit by the trajectory. |
| `BlocksMode` | Mode | Both | None, Fill, Outline, Both |  | Mode | The rendering that will be applied to the target block. |
| `BlocksFillColor` | Color | ColorUtils.getDefaultFillColor() |  | blocksMode = Fill, Both | FillColor | The color that will be used for the fill rendering. |
| `BlocksOutlineColor` | Color | ColorUtils.getDefaultOutlineColor() |  | blocksMode = Outline, Both | OutlineColor | The color that will be used for the outline rendering. |

### ViewClip
Makes your camera clip through walls and allows you to change the camera's distance from yourself.

Plik: `night/modules/impl/visuals/ViewClipModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Extend` | Boolean | false |  |  |  | Changes the distance of the third person camera from yourself. |
| `Distance` | Number |  |  | extend = true |  | The distance of the third person camera from your character. |

### ViewModel
Modifies the position, scale and rotation of the player viewmodel.

Plik: `night/modules/impl/visuals/ViewModelModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Mode` | Mode | Each | Each, Both |  |  | Hand modification mode. |
| `MainhandSwap` | Boolean | true |  |  |  | Disables the drop down animation when switching mainhand items. |
| `OffhandSwap` | Boolean | true |  |  |  | Disables the drop down animation when switching offhand items. |
| `MainHand` | Category |  |  |  |  | Settings for main hand item rendering. |
| `ScaleMainX` | Number |  |  |  | Scale X | Scale on X axis. |
| `ScaleMainY` | Number |  |  |  | Scale Y | Scale on Y axis. |
| `ScaleMainZ` | Number |  |  |  | Scale Z | Scale on Z axis. |
| `PositionMainX` | Number |  |  |  | Pos X | Translation on X axis. |
| `PositionMainY` | Number |  |  |  | Pos Y | Translation on Y axis. |
| `PositionMainZ` | Number |  |  |  | Pos Z | Translation on Z axis. |
| `RotationMainX` | Number |  |  |  | Rot X | Rotation on X axis. |
| `RotationMainY` | Number |  |  |  | Rot Y | Rotation on Y axis. |
| `RotationMainZ` | Number |  |  |  | Rot Z | Rotation on Z axis. |
| `OffHand` | Category |  |  | mode = Each |  | Settings for off hand item rendering. |
| `ScaleOffX` | Number |  |  |  | Scale X | Scale on X axis. |
| `ScaleOffY` | Number |  |  |  | Scale Y | Scale on Y axis. |
| `ScaleOffZ` | Number |  |  |  | Scale Z | Scale on Z axis. |
| `PositionOffX` | Number |  |  |  | Pos X | Translation on X axis. |
| `PositionOffY` | Number |  |  |  | Pos Y | Translation on Y axis. |
| `PositionOffZ` | Number |  |  |  | Pos Z | Translation on Z axis. |
| `RotationOffX` | Number |  |  |  | Rot X | Rotation on X axis. |
| `RotationOffY` | Number |  |  |  | Rot Y | Rotation on Y axis. |
| `RotationOffZ` | Number |  |  |  | Rot Z | Rotation on Z axis. |
| `Eating` | Category |  |  |  |  | Settings for eating animation. |
| `EatAnimation` | Boolean | false |  |  |  | Modify eating animation position. |
| `EatX` | Number |  |  | eatAnimation = true | Eat X | Eating offset X. |
| `EatY` | Number |  |  | eatAnimation = true | Eat Y | Eating offset Y. |
| `EatingMultiplier` | Number |  |  |  | Eating Multiplier | Scales the eating bob: 1 = vanilla, 0 = no bobbing (only the bring-to-mouth motion stays). |

### VoidESP
Highlights any non-bedrock blocks that can drop you into the void.

Plik: `night/modules/impl/visuals/VoidESPModule.java`

| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |
|---|---|---|---|---|---|---|
| `Range` | Number | 10 | 1 – 50 |  |  | The maximum range at which void blocks will be rendered. |
| `Asynchronous` | Boolean | true |  |  |  | Performs calculations on separate threads. |
| `Fill` | Category |  |  |  |  | The category for settings related to fill rendering. |
| `Fill` | Mode | Normal | None, Normal, Gradient |  | Mode | The mode for the fill rendering on the void blocks. |
| `FillHeight` | Number | 1.0 | 0.0 – 2.0 | fill = Normal, Gradient | Height | The height of the fill rendering on the void blocks. |
| `FillColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultFillColor().getColor().getAlpha()), false, false) |  | fill = Normal, Gradient | Color | The color for the fill rendering on the void blocks. |
| `Outline` | Category |  |  |  |  | The category for settings related to outline rendering. |
| `Outline` | Mode | Normal | None, Normal, Gradient |  | Mode | The mode for the outline rendering on the void blocks. |
| `OutlineHeight` | Number | 1.0 | 0.0 – 2.0 | outline = Normal, Gradient | Height | The height of the outline rendering on the void blocks. |
| `OutlineColor` | Color | ColorSetting.Color(Color(255, 0, 0, ColorUtils.getDefaultOutlineColor().getColor().getAlpha()), false, false) |  | outline = Normal, Gradient | Color | The color for the outline rendering on the void blocks. |
| `Shader` | Category |  |  |  |  | The category for settings related to shader rendering. |
| `Shader` | Mode | None |  |  | Mode | The animated shader that will be rendered on the void blocks. |
| `ShaderOpacity` | Number | 100 | 0 – 100 | shader = EspShader.SHADER_ACTIVE_MODES | Opacity | Void block shader opacity. |
| `ShaderSpeed` | Number | 1.0 | 0.1 – 10.0 | shader = EspShader.SHADER_ACTIVE_MODES | Speed | Void block shader animation speed. |
| `ShaderStep` | Number | 50.0 | 0.1 – 200.0 | shader = Gradient | Step | Void block gradient step size. |
| `ShaderColor1` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | shader = Gradient |  | The first gradient color. |
| `ShaderColor2` | Color | ColorSetting.Color(Color(180, 0, 255, 255), false, false) |  | shader = Gradient |  | The second gradient color. |
| `ShaderColor3` | Color | ColorSetting.Color(Color(255, 0, 100, 255), false, false) |  | shader = Gradient |  | The third gradient color. |
| `ShaderColor4` | Color | ColorSetting.Color(Color(100, 0, 255, 255), false, false) |  | shader = Gradient |  | The fourth gradient color. |
| `ShaderGlowColor` | Color | ColorSetting.Color(Color(255, 0, 0, 255), false, false) |  | shader = Glowing | GlowColor | The glow tint color for void blocks. |

---

Liczba modułów: **148**, liczba ustawień: **1538**.
