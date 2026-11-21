/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.JsonPrimitive
 *  lombok.Generated
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.phys.Vec3
 */
package night.managers;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.runtime.SwitchBootstraps;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Collections;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import night.Night;
import night.managers.WaypointManager;
import night.modules.Module;
import night.modules.impl.core.PingBypassModule;
import night.modules.impl.miscellaneous.FakePlayerModule;
import night.pingbypass.PingBypassFlags;
import night.settings.Setting;
import night.settings.impl.BindSetting;
import night.settings.impl.BooleanSetting;
import night.settings.impl.ColorSetting;
import night.settings.impl.ImageSetting;
import night.settings.impl.ModeSetting;
import night.settings.impl.NumberSetting;
import night.settings.impl.PositionSetting;
import night.settings.impl.StringSetting;
import night.settings.impl.WhitelistSetting;
import night.utils.minecraft.IdentifierUtils;
import night.utils.system.FileUtils;

public class ConfigManager {
    private String currentConfig = "default";
    private boolean loadingConfig = false;

    public ConfigManager() {
        this.loadConfig();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveConfig));
    }

    public void loadConfig() {
        block2: {
            try {
                this.migrateOldConfig();
                FileUtils.createDirectory("Night");
                FileUtils.createDirectory("Night/Configs");
                FileUtils.createDirectory("Night/Client");
                FileUtils.createDirectory("Night/Modules");
                FileUtils.createDirectory("Night/Binds");
                FileUtils.createDirectory("Night/Visuals");
                FileUtils.createDirectory("Night/GlobalConfigs");
                this.loadGeneral();
                this.loadWaypoints();
                this.loadModules(this.currentConfig);
            }
            catch (Exception exception) {
                Night.LOGGER.error("Failed to load the client's configuration!", (Throwable)exception);
                if (Night.CHAT_MANAGER == null) break block2;
                Night.CHAT_MANAGER.await("The configuration has not been loaded properly. Read the stacktrace for more information.");
            }
        }
    }

    private void migrateOldConfig() {
        try {
            Path oldDir = Paths.get("EUClient", new String[0]);
            Path newDir = Paths.get("Night", new String[0]);
            if (!(!Files.exists(oldDir, new LinkOption[0]) || Files.exists(newDir, new LinkOption[0]) && Files.exists(newDir.resolve("Configs"), new LinkOption[0]))) {
                Night.LOGGER.info("Migrating configuration from EUClient to Night...");
                ConfigManager.copyDirectory(oldDir, newDir);
            }
        }
        catch (Exception e) {
            Night.LOGGER.warn("Failed to migrate old EUClient configuration: {}", (Object)e.getMessage());
        }
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        try (Stream<Path> stream = Files.walk(source, new FileVisitOption[0]);){
            stream.forEach(src -> {
                try {
                    Path dest = target.resolve(source.relativize((Path)src));
                    if (Files.isDirectory(src, new LinkOption[0])) {
                        if (!Files.exists(dest, new LinkOption[0])) {
                            Files.createDirectories(dest, new FileAttribute[0]);
                        }
                    } else if (!Files.exists(dest, new LinkOption[0])) {
                        if (dest.getParent() != null && !Files.exists(dest.getParent(), new LinkOption[0])) {
                            Files.createDirectories(dest.getParent(), new FileAttribute[0]);
                        }
                        Files.copy(src, dest, StandardCopyOption.COPY_ATTRIBUTES);
                    }
                }
                catch (IOException e) {
                    Night.LOGGER.warn("Could not copy config file {}: {}", src, (Object)e.getMessage());
                }
            });
        }
    }

    public void saveConfig() {
        try {
            FileUtils.createDirectory("Night");
            FileUtils.createDirectory("Night/Configs");
            FileUtils.createDirectory("Night/Client");
            FileUtils.createDirectory("Night/Modules");
            FileUtils.createDirectory("Night/Binds");
            FileUtils.createDirectory("Night/Visuals");
            FileUtils.createDirectory("Night/GlobalConfigs");
            this.saveGeneral();
            this.saveWaypoints();
            this.saveModules(this.currentConfig);
        }
        catch (IOException exception) {
            Night.LOGGER.error("Failed to save the client's configuration!", (Throwable)exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadWaypoints() throws IOException {
        if (!FileUtils.fileExists("Night/Waypoints.json")) {
            return;
        }
        InputStream stream = Files.newInputStream(Paths.get("Night/Waypoints.json", new String[0]), new OpenOption[0]);
        try {
            JsonObject configObject;
            try {
                configObject = JsonParser.parseReader((Reader)new InputStreamReader(stream)).getAsJsonObject();
            }
            catch (IllegalStateException exception) {
                Night.LOGGER.error("Failed to load the client's Waypoint configuration!", (Throwable)exception);
                Night.CHAT_MANAGER.await("The Waypoint configuration has not been loaded properly. Read the stacktrace for more information.");
                if (Collections.singletonList(stream).get(0) != null) {
                    stream.close();
                }
                return;
            }
            if (configObject.has("Waypoints")) {
                for (JsonElement element : configObject.get("Waypoints").getAsJsonArray()) {
                    String[] args = element.getAsString().split(":");
                    if (Night.WAYPOINT_MANAGER.contains(args[0])) continue;
                    Night.WAYPOINT_MANAGER.add(args[0], new Vec3((double)Integer.parseInt(args[1]), (double)Integer.parseInt(args[2]), (double)Integer.parseInt(args[3])), args[4], args[5]);
                }
            }
        }
        finally {
            if (Collections.singletonList(stream).get(0) != null) {
                stream.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadGeneral() throws IOException {
        if (!FileUtils.fileExists("Night/General.json")) {
            return;
        }
        InputStream stream = Files.newInputStream(Paths.get("Night/General.json", new String[0]), new OpenOption[0]);
        try {
            JsonObject configObject;
            JsonElement parsed = JsonParser.parseReader((Reader)new InputStreamReader(stream));
            if (parsed == null || !parsed.isJsonObject()) {
                Night.LOGGER.warn("General.json is empty or invalid JSON.");
                return;
            }
            try {
                configObject = parsed.getAsJsonObject();
            }
            catch (Exception exception) {
                Night.LOGGER.error("Failed to load the client's General configuration!", (Throwable)exception);
                if (Night.CHAT_MANAGER != null) {
                    Night.CHAT_MANAGER.await("The General configuration has not been loaded properly. Read the stacktrace for more information.");
                }
                return;
            }
            if (configObject.has("Config")) {
                this.currentConfig = configObject.get("Config").getAsString();
            }
            if (configObject.has("Prefix") && Night.COMMAND_MANAGER != null) {
                Night.COMMAND_MANAGER.setPrefix(configObject.get("Prefix").getAsString());
            }
            if (configObject.has("Friends") && Night.FRIEND_MANAGER != null) {
                for (JsonElement element : configObject.get("Friends").getAsJsonArray()) {
                    if (Night.FRIEND_MANAGER.contains(element.getAsString())) continue;
                    Night.FRIEND_MANAGER.add(element.getAsString());
                }
            }
            if (configObject.has("Macros") && Night.MACRO_MANAGER != null) {
                for (JsonElement element : configObject.get("Macros").getAsJsonArray()) {
                    try {
                        String[] split = element.getAsString().split(":", 2);
                        if (split.length <= 1) continue;
                        int key = Integer.parseInt(split[0]);
                        String message = split[1];
                        Night.MACRO_MANAGER.add(message, key);
                    }
                    catch (NumberFormatException exception) {
                        Night.LOGGER.error("Failed to load the " + element.getAsString() + " macro!", (Throwable)exception);
                        if (Night.CHAT_MANAGER == null) continue;
                        Night.CHAT_MANAGER.await("The " + element.getAsString() + " macro has failed to load. Read the stacktrace for more information.");
                    }
                }
            }
        }
        finally {
            if (Collections.singletonList(stream).get(0) != null) {
                stream.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveGeneral() throws IOException {
        FileUtils.resetFile("Night/General.json");
        JsonObject configObject = new JsonObject();
        configObject.add("Config", (JsonElement)new JsonPrimitive(this.currentConfig));
        configObject.add("Prefix", (JsonElement)new JsonPrimitive(Night.COMMAND_MANAGER.getPrefix()));
        JsonArray friendsArray = new JsonArray();
        Night.FRIEND_MANAGER.getFriends().forEach(arg_0 -> ((JsonArray)friendsArray).add(arg_0));
        configObject.add("Friends", (JsonElement)friendsArray);
        JsonArray macroArray = new JsonArray();
        Night.MACRO_MANAGER.getMacros().forEach((key, value) -> macroArray.add(value + ":" + key));
        configObject.add("Macros", (JsonElement)macroArray);
        OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Night/General.json"), StandardCharsets.UTF_8);
        try {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)configObject.toString())));
        }
        finally {
            if (Collections.singletonList(writer).get(0) != null) {
                writer.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveWaypoints() throws IOException {
        FileUtils.resetFile("Night/Waypoints.json");
        JsonObject configObject = new JsonObject();
        JsonArray waypointArray = new JsonArray();
        for (WaypointManager.Waypoint waypoint : Night.WAYPOINT_MANAGER.getWaypoints()) {
            waypointArray.add(waypoint.getName() + ":" + (int)waypoint.getPos().x + ":" + (int)waypoint.getPos().y + ":" + (int)waypoint.getPos().z + ":" + waypoint.getDimension() + ":" + waypoint.getServer());
        }
        configObject.add("Waypoints", (JsonElement)waypointArray);
        OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Night/Waypoints.json"), StandardCharsets.UTF_8);
        try {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)configObject.toString())));
        }
        finally {
            if (Collections.singletonList(writer).get(0) != null) {
                writer.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadModules(String config) throws IOException {
        if (!FileUtils.fileExists("Night/Configs/" + config + ".json")) {
            return;
        }
        InputStream stream = Files.newInputStream(Paths.get("Night/Configs/" + config + ".json", new String[0]), new OpenOption[0]);
        try {
            PingBypassModule pbModule;
            JsonObject configObject;
            JsonElement parsed = JsonParser.parseReader((Reader)new InputStreamReader(stream));
            if (parsed == null || !parsed.isJsonObject()) {
                Night.LOGGER.warn("Module configuration is empty or invalid JSON: {}", (Object)config);
                return;
            }
            try {
                configObject = parsed.getAsJsonObject();
            }
            catch (Exception exception) {
                Night.LOGGER.error("Failed to load the client's Module configuration!", (Throwable)exception);
                if (Night.CHAT_MANAGER != null) {
                    Night.CHAT_MANAGER.await("The configuration for the Modules has not been loaded properly. Read the stacktrace for more information.");
                }
                return;
            }
            if (!configObject.has("Modules")) {
                return;
            }
            JsonObject modulesObject = configObject.get("Modules").getAsJsonObject();
            this.loadingConfig = true;
            try {
                this.loadModulesInner(modulesObject);
            }
            finally {
                this.loadingConfig = false;
            }
            this.currentConfig = config;
            try {
                this.saveGeneral();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (PingBypassFlags.proxyForwardingActive && Night.PINGBYPASS_CONFIG != null && !Night.PINGBYPASS_CONFIG.isServer() && (pbModule = Night.MODULE_MANAGER.getModule(PingBypassModule.class)) != null && pbModule.isToggled()) {
                pbModule.syncAllSettingsToProxy();
            }
        }
        finally {
            if (Collections.singletonList(stream).get(0) != null) {
                stream.close();
            }
        }
    }

    private void loadModulesInner(JsonObject modulesObject) {
        for (Module module : Night.MODULE_MANAGER.getModules()) {
            if (!modulesObject.has(module.getName())) {
                module.setToggled(false);
                module.resetValues();
                continue;
            }
            this.applyModuleJson(module, modulesObject.get(module.getName()).getAsJsonObject());
        }
    }

    public void applyModuleJson(Module module, JsonObject moduleObject) {
        this.applyModuleJson(module, moduleObject, true);
    }

    public void applyModuleJson(Module module, JsonObject moduleObject, boolean includeBinds) {
        boolean toggled;
        boolean toggled_2 = moduleObject.has("Status") && moduleObject.get("Status").getAsBoolean();
        if (!moduleObject.has("Settings")) {
            if (includeBinds) {
                module.resetValues();
            }
            module.setToggled(toggled_2, false);
            return;
        }
        JsonObject settingsObject = moduleObject.get("Settings").getAsJsonObject();
        for (Setting uncastedSetting : module.getSettings()) {
            Setting setting;
            Setting setting2;
            ColorSetting setting3;
            BindSetting setting4;
            ImageSetting setting5;
            StringSetting setting6;
            ModeSetting setting7;
            NumberSetting setting8;
            BooleanSetting setting9;
            Setting setting10 = uncastedSetting;
            int n;
            if (!includeBinds && uncastedSetting instanceof BindSetting) continue;
            JsonElement valueObject = settingsObject.get(uncastedSetting.getName());
            if (valueObject == null || !valueObject.isJsonPrimitive()) {
                Objects.requireNonNull(uncastedSetting);
                n = 0;
                switch ((Object)setting10) {
                    case BooleanSetting __p0: {
                        setting9 = (BooleanSetting)setting10;
                        setting9.resetValue();
                        break;
                    }
                    case NumberSetting __p1: {
                        setting8 = (NumberSetting)setting10;
                        setting8.resetValue();
                        break;
                    }
                    case ModeSetting __p2: {
                        setting7 = (ModeSetting)setting10;
                        setting7.resetValue();
                        break;
                    }
                    case StringSetting __p3: {
                        setting6 = (StringSetting)setting10;
                        setting6.resetValue();
                        break;
                    }
                    case ImageSetting __p4: {
                        setting5 = (ImageSetting)setting10;
                        setting5.resetValue();
                        break;
                    }
                    case BindSetting __p5: {
                        setting4 = (BindSetting)setting10;
                        setting4.resetValue();
                        break;
                    }
                    case ColorSetting __p6: {
                        setting3 = (ColorSetting)setting10;
                        setting3.resetValue();
                        break;
                    }
                    case WhitelistSetting __p7: {
                        setting2 = (WhitelistSetting)setting10;
                        ((WhitelistSetting)setting2).clear();
                        break;
                    }
                    case PositionSetting __p8: {
                        setting = (PositionSetting)setting10;
                        ((PositionSetting)setting).resetValue();
                        break;
                    }
                                    default: {
                        break;
                    }
}
                continue;
            }
            Objects.requireNonNull(uncastedSetting);
            n = 0;
            switch ((Object)setting10) {
                case BooleanSetting __p0: {
                    setting9 = (BooleanSetting)setting10;
                    if (valueObject.getAsJsonPrimitive().isBoolean()) {
                        setting9.setValue(valueObject.getAsBoolean());
                        break;
                    }
                    setting9.resetValue();
                    break;
                }
                case NumberSetting __p1: {
                    setting8 = (NumberSetting)setting10;
                    if (valueObject.getAsJsonPrimitive().isNumber()) {
                        setting8.setValue(valueObject.getAsNumber());
                        break;
                    }
                    setting8.resetValue();
                    break;
                }
                case ModeSetting __p2: {
                    setting7 = (ModeSetting)setting10;
                    setting7.setValue(valueObject.getAsString());
                    break;
                }
                case StringSetting __p3: {
                    setting6 = (StringSetting)setting10;
                    String str = valueObject.getAsString();
                    if (str != null && str.equalsIgnoreCase("EUClient")) {
                        str = "Night";
                    }
                    setting6.setValue(str);
                    break;
                }
                case ImageSetting __p4: {
                    setting5 = (ImageSetting)setting10;
                    setting5.setValue(valueObject.getAsString());
                    if (Night.IMAGE_MANAGER == null) break;
                    Night.IMAGE_MANAGER.setCurrentActiveImage(valueObject.getAsString());
                    break;
                }
                case BindSetting __p5: {
                    setting4 = (BindSetting)setting10;
                    if (valueObject.isJsonPrimitive() && valueObject.getAsJsonPrimitive().isNumber()) {
                        setting4.setValue(valueObject.getAsInt());
                        break;
                    }
                    String[] data = valueObject.getAsString().split(",", 2);
                    setting4.setValue(Integer.parseInt(data[0]));
                    if (data.length <= 1) break;
                    setting4.setMode(data[1]);
                    break;
                }
                case ColorSetting __p6: {
                    setting3 = (ColorSetting)setting10;
                    String[] data = valueObject.getAsString().split(",");
                    if (data.length != 6) break;
                    setting3.setColor(new Color(Math.clamp((long)Integer.parseInt(data[0]), 0, 255), Math.clamp((long)Integer.parseInt(data[1]), 0, 255), Math.clamp((long)Integer.parseInt(data[2]), 0, 255), Math.clamp((long)Integer.parseInt(data[3]), 0, 255)));
                    setting3.setSync(Boolean.parseBoolean(data[4]));
                    setting3.setRainbow(Boolean.parseBoolean(data[5]));
                    break;
                }
                case PositionSetting __p7: {
                    setting2 = (PositionSetting)setting10;
                    String[] data = valueObject.getAsString().split(",");
                    if (data.length != 2) break;
                    ((PositionSetting)setting2).set(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
                    break;
                }
                case WhitelistSetting __p8: {
                    setting = (WhitelistSetting)setting10;
                    ((WhitelistSetting)setting).clear();
                    String[] data = valueObject.getAsString().split(",");
                    if (data.length == 0) break;
                    for (String object : data) {
                        Block block;
                        if (((WhitelistSetting)setting).getType() == WhitelistSetting.Type.ITEMS) {
                            Item item = IdentifierUtils.getItem(object);
                            if (item == null) continue;
                            ((WhitelistSetting)setting).add(item);
                            continue;
                        }
                        if (((WhitelistSetting)setting).getType() != WhitelistSetting.Type.BLOCKS || (block = IdentifierUtils.getBlock(object)) == null) continue;
                        ((WhitelistSetting)setting).add(block);
                    }
                    break;
                }
                            default: {
                    break;
                }
}
        }
        module.setToggled(toggled_2, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveModules(String config) throws IOException {
        FileUtils.resetFile("Night/Configs/" + config + ".json");
        JsonObject configObject = new JsonObject();
        configObject.add("Config", (JsonElement)new JsonPrimitive(config));
        JsonObject modulesObject = new JsonObject();
        for (Module module : Night.MODULE_MANAGER.getModules()) {
            JsonObject moduleObject = this.serializeModuleJson(module);
            if (module instanceof FakePlayerModule) {
                moduleObject.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(false)));
            }
            modulesObject.add(module.getName(), (JsonElement)moduleObject);
        }
        configObject.add("Modules", (JsonElement)modulesObject);
        OutputStreamWriter writer = new OutputStreamWriter((OutputStream)new FileOutputStream("Night/Configs/" + config + ".json"), StandardCharsets.UTF_8);
        try {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(JsonParser.parseString((String)configObject.toString())));
            this.currentConfig = config;
            try {
                this.saveGeneral();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        finally {
            if (Collections.singletonList(writer).get(0) != null) {
                writer.close();
            }
        }
    }

    public JsonObject serializeModuleJson(Module module) {
        return this.serializeModuleJson(module, true);
    }

    public JsonObject serializeModuleJson(Module module, boolean includeBinds) {
        JsonObject moduleObject = new JsonObject();
        moduleObject.add("Status", (JsonElement)new JsonPrimitive(Boolean.valueOf(module.isToggled())));
        JsonObject settingsObject = new JsonObject();
        for (Setting uncastedSetting : module.getSettings()) {
            Setting setting = uncastedSetting;
            if (!includeBinds && uncastedSetting instanceof BindSetting) continue;
            Objects.requireNonNull(uncastedSetting);
            int n = 0;
            switch ((Object)setting) {
                case BooleanSetting __p0: {
                    BooleanSetting setting2 = (BooleanSetting)setting;
                    settingsObject.add(setting2.getName(), (JsonElement)new JsonPrimitive(Boolean.valueOf(setting2.getValue())));
                    break;
                }
                case NumberSetting __p1: {
                    NumberSetting setting3 = (NumberSetting)setting;
                    settingsObject.add(setting3.getName(), (JsonElement)new JsonPrimitive(setting3.getValue()));
                    break;
                }
                case ModeSetting __p2: {
                    ModeSetting setting4 = (ModeSetting)setting;
                    settingsObject.add(setting4.getName(), (JsonElement)new JsonPrimitive(setting4.getValue()));
                    break;
                }
                case StringSetting __p3: {
                    StringSetting setting5 = (StringSetting)setting;
                    settingsObject.add(setting5.getName(), (JsonElement)new JsonPrimitive(setting5.getValue()));
                    break;
                }
                case ImageSetting __p4: {
                    ImageSetting setting6 = (ImageSetting)setting;
                    settingsObject.add(setting6.getName(), (JsonElement)new JsonPrimitive(setting6.getValue()));
                    break;
                }
                case BindSetting __p5: {
                    BindSetting setting7 = (BindSetting)setting;
                    settingsObject.add(setting7.getName(), (JsonElement)new JsonPrimitive(setting7.getValue() + "," + setting7.getMode()));
                    break;
                }
                case ColorSetting __p6: {
                    ColorSetting setting8 = (ColorSetting)setting;
                    settingsObject.add(setting8.getName(), (JsonElement)new JsonPrimitive(setting8.getValue().getColor().getRed() + "," + setting8.getValue().getColor().getGreen() + "," + setting8.getValue().getColor().getBlue() + "," + setting8.getValue().getColor().getAlpha() + "," + setting8.isSync() + "," + setting8.isRainbow()));
                    break;
                }
                case WhitelistSetting __p7: {
                    WhitelistSetting setting9 = (WhitelistSetting)setting;
                    StringJoiner objects = new StringJoiner(",");
                    for (String id : setting9.getWhitelistIds()) {
                        objects.add(id);
                    }
                    settingsObject.add(setting9.getName(), (JsonElement)new JsonPrimitive(objects.toString()));
                    break;
                }
                case PositionSetting __p8: {
                    PositionSetting setting10 = (PositionSetting)setting;
                    settingsObject.add(setting10.getName(), (JsonElement)new JsonPrimitive(setting10.getX() + "," + setting10.getY()));
                    break;
                }
                            default: {
                    break;
                }
}
        }
        moduleObject.add("Settings", (JsonElement)settingsObject);
        return moduleObject;
    }

    public String getCurrentConfig() {
        return this.currentConfig;
    }

    public boolean isLoadingConfig() {
        return this.loadingConfig;
    }

    public void setCurrentConfig(String currentConfig) {
        this.currentConfig = currentConfig;
    }

    public void setLoadingConfig(boolean loadingConfig) {
        this.loadingConfig = loadingConfig;
    }
}

