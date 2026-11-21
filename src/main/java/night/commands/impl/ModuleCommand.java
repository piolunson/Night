/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 */
package night.commands.impl;

import java.awt.Color;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import night.Night;
import night.commands.Command;
import night.commands.RegisterCommand;
import night.modules.Module;
import night.settings.Setting;
import night.settings.impl.BindSetting;
import night.settings.impl.BooleanSetting;
import night.settings.impl.CategorySetting;
import night.settings.impl.ColorSetting;
import night.settings.impl.ModeSetting;
import night.settings.impl.NumberSetting;
import night.settings.impl.StringSetting;
import night.settings.impl.WhitelistSetting;
import night.utils.chat.ChatUtils;
import night.utils.color.ColorUtils;
import night.utils.input.KeyboardUtils;
import night.utils.minecraft.IdentifierUtils;
import night.utils.miscellaneous.ListUtils;

@RegisterCommand(name="module", tag="Module", description="Allows you to view and change this module's settings.", syntax="<[setting]> <[...]> | <list|reset>")
public class ModuleCommand
extends Command {
    private final Module module;

    public ModuleCommand(Module module) {
        this.module = module;
        this.setName(module.getName().toLowerCase());
        this.setTag(module.getName());
    }

    @Override
    public List<String> getSuggestions(String[] args) {
        String channel;
        if (args.length == 0) {
            ArrayList<String> names = new ArrayList<String>();
            names.add("list");
            names.add("reset");
            for (Setting setting : this.module.getSettings()) {
                if (setting instanceof CategorySetting) continue;
                names.add(setting.getName().toLowerCase());
            }
            return names;
        }
        if (args.length == 1) {
            Setting uncastedSetting = this.module.getSetting(args[0]);
            if (uncastedSetting == null) {
                return List.of();
            }
            Setting setting = uncastedSetting;
            Objects.requireNonNull(setting);
            Setting setting2 = setting;
            int n = 0;
            return switch ((Object)setting2) {
                case BooleanSetting __p0 -> {
                    BooleanSetting ignored = (BooleanSetting)setting2;
                    yield List.of("true", "false", "reset");
                }
                case NumberSetting __p1 -> {
                    NumberSetting ignored = (NumberSetting)setting2;
                    yield List.of("reset");
                }
                case ModeSetting __p2 -> {
                    ModeSetting setting_2 = (ModeSetting)setting2;
                    ArrayList<String> modes = new ArrayList<String>(setting_2.getModes());
                    modes.add("reset");
                    modes.add("list");
                    yield modes;
                }
                case StringSetting __p3 -> {
                    StringSetting ignored = (StringSetting)setting2;
                    yield List.of("force-reset");
                }
                case BindSetting __p4 -> {
                    BindSetting ignored = (BindSetting)setting2;
                    yield List.of("reset");
                }
                case ColorSetting __p5 -> {
                    ColorSetting ignored = (ColorSetting)setting2;
                    yield List.of("red", "green", "blue", "alpha", "sync", "rainbow", "code", "reset");
                }
                case WhitelistSetting __p6 -> {
                    WhitelistSetting ignored = (WhitelistSetting)setting2;
                    yield List.of("add", "del", "clear", "list");
                }
                default -> List.of();
            };
        }
        if (args.length == 2 && this.module.getSetting(args[0]) instanceof ColorSetting && ((channel = args[1].toLowerCase()).equals("sync") || channel.equals("rainbow"))) {
            return List.of("true", "false");
        }
        return List.of();
    }

    @Override
    public void execute(String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                if (this.module.getSettings().isEmpty()) {
                    Night.CHAT_MANAGER.tagged("This module currently has no registered settings.", this.module.getName(), "module-cmd-" + this.getName() + "-list");
                } else {
                    StringBuilder builder = new StringBuilder();
                    int index = 0;
                    for (Setting setting : this.module.getSettings()) {
                        if (setting instanceof CategorySetting) continue;
                        builder.append(ChatUtils.getSecondary()).append(setting.getName()).append(ChatUtils.getPrimary()).append(" [").append(ChatUtils.getSecondary());
                        if (setting instanceof BooleanSetting) {
                            builder.append(((BooleanSetting)setting).getValue());
                        }
                        if (setting instanceof NumberSetting) {
                            builder.append(((NumberSetting)setting).getValue());
                        }
                        if (setting instanceof ModeSetting) {
                            builder.append(((ModeSetting)setting).getValue());
                        }
                        if (setting instanceof StringSetting) {
                            builder.append(((StringSetting)setting).getValue());
                        }
                        if (setting instanceof BindSetting) {
                            builder.append(KeyboardUtils.getKeyName(((BindSetting)setting).getValue()).toUpperCase());
                        }
                        if (setting instanceof WhitelistSetting) {
                            builder.append("...");
                        }
                        if (setting instanceof ColorSetting) {
                            builder.append("RGBA(").append(((ColorSetting)setting).getColor().getRed()).append(ChatUtils.getPrimary()).append(", ").append(ChatUtils.getSecondary()).append(((ColorSetting)setting).getColor().getGreen()).append(ChatUtils.getPrimary()).append(", ").append(ChatUtils.getSecondary()).append(((ColorSetting)setting).getColor().getBlue()).append(ChatUtils.getPrimary()).append(", ").append(ChatUtils.getSecondary()).append(((ColorSetting)setting).getColor().getAlpha()).append(")").append(ChatUtils.getPrimary()).append(",").append(ChatUtils.getSecondary()).append(" SyRa(").append(((ColorSetting)setting).isSync()).append(ChatUtils.getPrimary()).append(", ").append(ChatUtils.getSecondary()).append(((ColorSetting)setting).isRainbow()).append(")");
                        }
                        builder.append(ChatUtils.getPrimary()).append("]").append(ChatUtils.getSecondary()).append(index + 1 == this.module.getSettings().size() ? "" : ", ");
                        ++index;
                    }
                    Night.CHAT_MANAGER.message(String.valueOf(ChatUtils.getSecondary()) + this.module.getName() + " " + String.valueOf(ChatUtils.getPrimary()) + "[" + String.valueOf(ChatUtils.getSecondary()) + this.module.getSettings().size() + String.valueOf(ChatUtils.getPrimary()) + "]: " + String.valueOf(ChatUtils.getSecondary()) + String.valueOf(builder), "module-cmd-" + this.getName() + "-list");
                }
            } else if (args[0].equalsIgnoreCase("reset")) {
                this.module.resetValues();
                Night.CHAT_MANAGER.tagged("Successfully reset all of the module's settings.", this.module.getName(), "module-cmd-" + this.getName());
            } else {
                Setting uncastedSetting = this.module.getSetting(args[0]);
                if (uncastedSetting == null) {
                    Night.CHAT_MANAGER.tagged("Could not find the setting specified.", this.module.getName(), "module-cmd-" + this.getName());
                    return;
                }
                args = Arrays.copyOfRange(args, 1, args.length);
                Setting setting = uncastedSetting;
                Objects.requireNonNull(setting);
                Setting setting2 = setting;
                int n = 0;
                switch ((Object)setting2) {
                    case BooleanSetting __p0: {
                        BooleanSetting setting3 = (BooleanSetting)setting2;
                        if (setting3 == this.module.chatNotify) {
                            Night.CHAT_MANAGER.tagged("This setting is the module's main ChatNotify setting. Please use the " + String.valueOf(ChatUtils.getPrimary()) + "chatnotify" + String.valueOf(ChatUtils.getSecondary()) + " command instead.", this.module.getName(), this.getName());
                            return;
                        }
                        if (setting3 == this.module.drawn) {
                            Night.CHAT_MANAGER.tagged("This setting is the module's main Drawn setting. Please use the " + String.valueOf(ChatUtils.getPrimary()) + "drawn" + String.valueOf(ChatUtils.getSecondary()) + " command instead.", this.module.getName(), this.getName());
                            return;
                        }
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("reset")) {
                                setting3.resetValue();
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting3.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            setting3.setValue(Boolean.parseBoolean(args[0]));
                            Night.CHAT_MANAGER.tagged("Successfully set " + String.valueOf(ChatUtils.getPrimary()) + setting3.getName() + String.valueOf(ChatUtils.getSecondary()) + " to " + String.valueOf(ChatUtils.getPrimary()) + setting3.getValue() + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting3.getName().toLowerCase() + " <[value]|reset>");
                        break;
                    }
                    case NumberSetting __p1: {
                        NumberSetting setting4 = (NumberSetting)setting2;
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("reset")) {
                                setting4.resetValue();
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting4.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            try {
                                switch (setting4.getType()) {
                                    case LONG: {
                                        setting4.setValue(Long.parseLong(args[0]));
                                        break;
                                    }
                                    case DOUBLE: {
                                        setting4.setValue(Double.parseDouble(args[0]));
                                        break;
                                    }
                                    case FLOAT: {
                                        setting4.setValue(Float.valueOf(Float.parseFloat(args[0])));
                                        break;
                                    }
                                    default: {
                                        setting4.setValue(Integer.parseInt(args[0]));
                                    }
                                }
                                Night.CHAT_MANAGER.tagged("Successfully set " + String.valueOf(ChatUtils.getPrimary()) + setting4.getName() + String.valueOf(ChatUtils.getSecondary()) + " to " + String.valueOf(ChatUtils.getPrimary()) + String.valueOf(setting4.getValue()) + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                            }
                            catch (NumberFormatException exception) {
                                Night.CHAT_MANAGER.tagged("Please input a valid " + String.valueOf(ChatUtils.getPrimary()) + setting4.getType().name().toLowerCase() + String.valueOf(ChatUtils.getSecondary()) + " number.", this.module.getName(), "module-cmd-" + this.getName());
                            }
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting4.getName().toLowerCase() + " <[value]|reset>");
                        break;
                    }
                    case ModeSetting __p2: {
                        ModeSetting setting5 = (ModeSetting)setting2;
                        if (args.length >= 1) {
                            if (args[0].equalsIgnoreCase("reset") && setting5.getModes().stream().noneMatch("reset"::equalsIgnoreCase) || args[0].equalsIgnoreCase("force-reset")) {
                                setting5.resetValue();
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting5.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            if (args[0].equalsIgnoreCase("list") && setting5.getModes().stream().noneMatch("list"::equalsIgnoreCase) || args[0].equalsIgnoreCase("force-list")) {
                                if (setting5.getModes().isEmpty()) {
                                    Night.CHAT_MANAGER.tagged(String.valueOf(ChatUtils.getPrimary()) + setting5.getName() + String.valueOf(ChatUtils.getSecondary()) + " currently has no values registered.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                StringBuilder modesString = new StringBuilder();
                                int index = 0;
                                for (String str : setting5.getModes()) {
                                    modesString.append(ChatUtils.getSecondary()).append(str).append(index + 1 == setting5.getModes().size() ? "" : ", ");
                                    ++index;
                                }
                                Night.CHAT_MANAGER.tagged(String.valueOf(ChatUtils.getSecondary()) + setting5.getName() + " " + String.valueOf(ChatUtils.getPrimary()) + "[" + String.valueOf(ChatUtils.getSecondary()) + setting5.getModes().size() + String.valueOf(ChatUtils.getPrimary()) + "]: " + String.valueOf(ChatUtils.getSecondary()) + String.valueOf(modesString), this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            if (args[0].equalsIgnoreCase("reset") && setting5.getModes().stream().anyMatch("reset"::equalsIgnoreCase)) {
                                Night.CHAT_MANAGER.info("If you would like to reset this setting's value, write \"" + String.valueOf(ChatUtils.getPrimary()) + "force-reset" + String.valueOf(ChatUtils.getSecondary()) + "\" instead.");
                            }
                            if (args[0].equalsIgnoreCase("list") && setting5.getModes().stream().anyMatch("list"::equalsIgnoreCase)) {
                                Night.CHAT_MANAGER.info("If you would like to view a list of valid values for this setting, write \"" + String.valueOf(ChatUtils.getPrimary()) + "force-list" + String.valueOf(ChatUtils.getSecondary()) + "\" instead.");
                            }
                            StringBuilder builder = new StringBuilder();
                            int index = 0;
                            for (String str : args) {
                                builder.append(str).append(index + 1 == args.length ? "" : " ");
                                ++index;
                            }
                            if (setting5.getModes().stream().anyMatch(builder.toString()::equalsIgnoreCase)) {
                                setting5.setValue(setting5.getModes().get(ListUtils.getIndex(setting5.getModes(), builder.toString())));
                                Night.CHAT_MANAGER.tagged("Successfully set " + String.valueOf(ChatUtils.getPrimary()) + setting5.getName() + String.valueOf(ChatUtils.getSecondary()) + " to " + String.valueOf(ChatUtils.getPrimary()) + setting5.getValue() + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            Night.CHAT_MANAGER.tagged("Please input a valid value for this setting.", this.module.getName(), "module-cmd-" + this.getName());
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting5.getName().toLowerCase() + " <[value]|reset|list>");
                        break;
                    }
                    case StringSetting __p3: {
                        StringSetting setting6 = (StringSetting)setting2;
                        if (args.length >= 1) {
                            if (args[0].equalsIgnoreCase("force-reset")) {
                                setting6.setValue(setting6.getDefaultValue());
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting6.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            if (args[0].equalsIgnoreCase("reset")) {
                                Night.CHAT_MANAGER.info("If you would like to reset this setting's value, write \"" + String.valueOf(ChatUtils.getPrimary()) + "force-reset" + String.valueOf(ChatUtils.getSecondary()) + "\" instead.");
                            }
                            StringBuilder builder = new StringBuilder();
                            int index = 0;
                            for (String str : args) {
                                builder.append(str).append(index + 1 == args.length ? "" : " ");
                                ++index;
                            }
                            setting6.setValue(builder.toString());
                            Night.CHAT_MANAGER.tagged("Successfully set " + String.valueOf(ChatUtils.getPrimary()) + setting6.getName() + String.valueOf(ChatUtils.getSecondary()) + " to " + String.valueOf(ChatUtils.getPrimary()) + setting6.getValue() + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting6.getName().toLowerCase() + " <[value]|reset>");
                        break;
                    }
                    case BindSetting __p4: {
                        BindSetting setting7 = (BindSetting)setting2;
                        if (uncastedSetting == this.module.bind) {
                            Night.CHAT_MANAGER.tagged("This setting is the module's main toggle keybind. Please use the " + String.valueOf(ChatUtils.getPrimary()) + "bind" + String.valueOf(ChatUtils.getSecondary()) + " command instead.", this.module.getName(), "module-cmd-" + this.getName());
                            return;
                        }
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("reset")) {
                                setting7.resetValue();
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting7.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            int key = 0;
                            try {
                                key = KeyboardUtils.getKeyNumber(args[0]);
                            }
                            catch (IllegalArgumentException str) {
                                // empty catch block
                            }
                            setting7.setValue(key);
                            Night.CHAT_MANAGER.tagged("Successfully set " + String.valueOf(ChatUtils.getPrimary()) + setting7.getName() + String.valueOf(ChatUtils.getSecondary()) + " to " + String.valueOf(ChatUtils.getPrimary()) + KeyboardUtils.getKeyName(setting7.getValue()).toUpperCase() + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting7.getName().toLowerCase() + " <[value]|reset>");
                        break;
                    }
                    case ColorSetting __p5: {
                        ColorSetting setting8 = (ColorSetting)setting2;
                        if (args.length == 2) {
                            int parsedValue = 0;
                            if (args[0].equalsIgnoreCase("red") || args[0].equalsIgnoreCase("green") || args[0].equalsIgnoreCase("blue") || args[0].equalsIgnoreCase("alpha")) {
                                try {
                                    parsedValue = Math.clamp((long)Integer.parseInt(args[1]), 0, 255);
                                }
                                catch (NumberFormatException ignored) {
                                    Night.CHAT_MANAGER.tagged("Please input a valid number for the " + String.valueOf(ChatUtils.getPrimary()) + args[0].toLowerCase() + String.valueOf(ChatUtils.getSecondary()) + " value.", this.module.getName());
                                    return;
                                }
                            }
                            String valueName = "";
                            Object newValue = "";
                            if (args[0].equalsIgnoreCase("red")) {
                                setting8.setColor(new Color(parsedValue, setting8.getValue().getColor().getGreen(), setting8.getValue().getColor().getBlue(), setting8.getValue().getColor().getAlpha()));
                                valueName = "red";
                                newValue = String.valueOf(setting8.getValue().getColor().getRed());
                            } else if (args[0].equalsIgnoreCase("green")) {
                                setting8.setColor(new Color(setting8.getValue().getColor().getRed(), parsedValue, setting8.getValue().getColor().getBlue(), setting8.getValue().getColor().getAlpha()));
                                valueName = "green";
                                newValue = String.valueOf(setting8.getValue().getColor().getGreen());
                            } else if (args[0].equalsIgnoreCase("blue")) {
                                setting8.setColor(new Color(setting8.getValue().getColor().getRed(), setting8.getValue().getColor().getGreen(), parsedValue, setting8.getValue().getColor().getAlpha()));
                                valueName = "blue";
                                newValue = String.valueOf(setting8.getValue().getColor().getBlue());
                            } else if (args[0].equalsIgnoreCase("alpha")) {
                                setting8.setColor(new Color(setting8.getValue().getColor().getRed(), setting8.getValue().getColor().getGreen(), setting8.getValue().getColor().getBlue(), parsedValue));
                                valueName = "alpha";
                                newValue = String.valueOf(setting8.getValue().getColor().getAlpha());
                            } else if (args[0].equalsIgnoreCase("sync")) {
                                setting8.setSync(Boolean.parseBoolean(args[1]));
                                valueName = "sync";
                                newValue = String.valueOf(setting8.isSync());
                            } else if (args[0].equalsIgnoreCase("rainbow")) {
                                setting8.setRainbow(Boolean.parseBoolean(args[1]));
                                valueName = "rainbow";
                                newValue = String.valueOf(setting8.isRainbow());
                            } else if (args[0].equalsIgnoreCase("code")) {
                                if (!ColorUtils.isValidColorCode(args[1])) {
                                    Night.CHAT_MANAGER.tagged("Please input a valid color code.", this.module.getName(), "module-cmd-" + this.getName());
                                    return;
                                }
                                try {
                                    Color decoded = Color.decode((args[1].startsWith("#") ? "" : "#") + args[1]);
                                    setting8.setColor(new Color(decoded.getRed(), decoded.getGreen(), decoded.getBlue()));
                                }
                                catch (NumberFormatException exception) {
                                    Night.CHAT_MANAGER.tagged("Please input a valid color code.", this.module.getName(), "module-cmd-" + this.getName());
                                    return;
                                }
                                valueName = "color";
                                newValue = "rgba(" + setting8.getValue().getColor().getRed() + ", " + setting8.getValue().getColor().getGreen() + ", " + setting8.getValue().getColor().getBlue() + ", " + setting8.getValue().getColor().getAlpha() + ")";
                            } else {
                                Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting8.getName().toLowerCase() + " <red|green|blue|alpha|sync|rainbow|code> <[input]> | <reset>");
                            }
                            Night.CHAT_MANAGER.tagged("Successfully set the " + String.valueOf(ChatUtils.getPrimary()) + valueName + String.valueOf(ChatUtils.getSecondary()) + " value to " + String.valueOf(ChatUtils.getPrimary()) + (String)newValue + String.valueOf(ChatUtils.getSecondary()) + ".", this.module.getName(), "module-cmd-" + this.getName());
                            break;
                        }
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("reset")) {
                                setting8.resetValue();
                                Night.CHAT_MANAGER.tagged("Successfully reset the " + String.valueOf(ChatUtils.getPrimary()) + setting8.getName() + String.valueOf(ChatUtils.getSecondary()) + " setting.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting8.getName().toLowerCase() + " <red|green|blue|alpha|sync|rainbow|code> <[input]> | <reset>");
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting8.getName().toLowerCase() + " <red|green|blue|alpha|sync|rainbow|code> <[input]> | <reset>");
                        break;
                    }
                    case WhitelistSetting __p6: {
                        WhitelistSetting setting9 = (WhitelistSetting)setting2;
                        if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("add")) {
                                if (setting9.getType() == WhitelistSetting.Type.ITEMS) {
                                    Item item = IdentifierUtils.getItem(args[1]);
                                    if (item == null) {
                                        Night.CHAT_MANAGER.tagged("Please input a valid item ID.", this.module.getName(), "module-cmd-" + this.getName());
                                        return;
                                    }
                                    if (setting9.isWhitelistContains(item)) {
                                        Night.CHAT_MANAGER.tagged(String.valueOf(ChatUtils.getPrimary()) + item.getName(item.getDefaultInstance()).getString() + String.valueOf(ChatUtils.getSecondary()) + " is already on the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                        break;
                                    }
                                    setting9.add(item);
                                    Night.CHAT_MANAGER.tagged("Successfully added " + String.valueOf(ChatUtils.getPrimary()) + item.getName(item.getDefaultInstance()).getString() + String.valueOf(ChatUtils.getSecondary()) + " to the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                if (setting9.getType() == WhitelistSetting.Type.BLOCKS) {
                                    Block block = IdentifierUtils.getBlock(args[1]);
                                    if (block == null) {
                                        Night.CHAT_MANAGER.tagged("Please input a valid block ID.", this.module.getName(), "module-cmd-" + this.getName());
                                        return;
                                    }
                                    if (setting9.isWhitelistContains(block)) {
                                        Night.CHAT_MANAGER.tagged(String.valueOf(ChatUtils.getPrimary()) + block.getName().getString() + String.valueOf(ChatUtils.getSecondary()) + " is already on the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                        break;
                                    }
                                    setting9.add(block);
                                    Night.CHAT_MANAGER.tagged("Successfully added " + String.valueOf(ChatUtils.getPrimary()) + block.getName().getString() + String.valueOf(ChatUtils.getSecondary()) + " to the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                Night.CHAT_MANAGER.error("Something went wrong while detecting the setting's type.");
                                break;
                            }
                            if (args[0].equalsIgnoreCase("del")) {
                                Block block;
                                Item item;
                                if (setting9.getType() == WhitelistSetting.Type.ITEMS && (item = IdentifierUtils.getItem(args[1])) != null && setting9.isWhitelistContains(item)) {
                                    setting9.remove(item);
                                    Night.CHAT_MANAGER.tagged("Successfully removed " + String.valueOf(ChatUtils.getPrimary()) + item.getName(item.getDefaultInstance()).getString() + String.valueOf(ChatUtils.getSecondary()) + " from the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                if (setting9.getType() == WhitelistSetting.Type.BLOCKS && (block = IdentifierUtils.getBlock(args[1])) != null && setting9.isWhitelistContains(block)) {
                                    setting9.remove(block);
                                    Night.CHAT_MANAGER.tagged("Successfully removed " + String.valueOf(ChatUtils.getPrimary()) + block.getName().getString() + String.valueOf(ChatUtils.getSecondary()) + " from the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                Night.CHAT_MANAGER.tagged(String.valueOf(ChatUtils.getPrimary()) + args[1] + String.valueOf(ChatUtils.getSecondary()) + " is not on the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting9.getName().toLowerCase() + " <add|del> <[id]> | <list|clear>");
                            break;
                        }
                        if (args.length == 1) {
                            if (args[0].equalsIgnoreCase("clear")) {
                                setting9.getWhitelist().clear();
                                Night.CHAT_MANAGER.tagged("Successfully cleared the " + String.valueOf(ChatUtils.getPrimary()) + setting9.getName() + String.valueOf(ChatUtils.getSecondary()) + " whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                break;
                            }
                            if (args[0].equalsIgnoreCase("list")) {
                                if (setting9.getWhitelist().isEmpty()) {
                                    Night.CHAT_MANAGER.tagged("There are currently no " + (setting9.getType() == WhitelistSetting.Type.ITEMS ? "items" : "blocks") + " on the whitelist.", this.module.getName(), "module-cmd-" + this.getName());
                                    break;
                                }
                                StringBuilder whitelist = new StringBuilder();
                                int index = 0;
                                for (Object object : setting9.getWhitelist()) {
                                    Object object2 = null;
                                    whitelist.append(ChatUtils.getSecondary());
                                    Objects.requireNonNull(object);
                                    int n2 = 0;
                                    switch ((Object)object2) {
                                        case BooleanSetting __p0: {
                                            Block block = (Block)object2;
                                            whitelist.append(block.getName().getString());
                                            break;
                                        }
                                        case NumberSetting __p1: {
                                            Item item = (Item)object2;
                                            whitelist.append(item.getName(item.getDefaultInstance()).getString());
                                            break;
                                        }
                                        default: {
                                            whitelist.append("Invalid");
                                        }
                                    }
                                    whitelist.append(ChatUtils.getPrimary()).append(index + 1 == setting9.getWhitelist().size() ? "" : ", ");
                                    ++index;
                                }
                                Night.CHAT_MANAGER.message(String.valueOf(ChatUtils.getSecondary()) + setting9.getName() + " " + String.valueOf(ChatUtils.getPrimary()) + "[" + String.valueOf(ChatUtils.getSecondary()) + setting9.getWhitelist().size() + String.valueOf(ChatUtils.getPrimary()) + "]: " + String.valueOf(ChatUtils.getSecondary()) + String.valueOf(whitelist), "module-cmd-" + this.getName());
                                break;
                            }
                            Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting9.getName().toLowerCase() + " <add|del> <[id]> | <clear|list>");
                            break;
                        }
                        Night.CHAT_MANAGER.info(this.module.getName().toLowerCase() + " " + setting9.getName().toLowerCase() + " <add|del> <[id]> | <clear|list>");
                        break;
                    }
                    default: {
                        this.messageSyntax();
                    }
                }
            }
        } else {
            this.messageSyntax();
        }
    }
}

