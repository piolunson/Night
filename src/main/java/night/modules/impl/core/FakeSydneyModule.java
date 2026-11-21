package night.modules.impl.core;

import java.awt.Color;
import night.Night;
import night.modules.Module;
import night.modules.RegisterModule;
import night.settings.impl.BooleanSetting;
import night.settings.impl.ColorSetting;
import night.settings.impl.StringSetting;

/**
 * Makes the client's HUD and colors look like Sydney Legacy
 * (https://github.com/sydney-client/Sydney-Legacy).
 *
 * <p>Sydney's defaults, taken from its sources: accent color {@code 130, 202, 255} (its
 * {@code ColorModule}), watermark "{@code Sydney <version>+<revision>.<hash>}" without a logo, and the
 * welcomer "{@code Hello, [username]! :^)}". Everything this module changes is snapshotted on enable
 * and restored on disable, so toggling it does not permanently overwrite the user's settings.</p>
 */
@RegisterModule(name="FakeSydney", description="Makes the client's HUD and colors look like Sydney Legacy.", category=Module.Category.CORE, drawn=false)
public class FakeSydneyModule
extends Module {
    private static final Color SYDNEY_COLOR = new Color(130, 202, 255);
    private static final String SYDNEY_WELCOMER = "Hello, [username]! :^)";
    private static String versionOverride;

    public BooleanSetting colors = new BooleanSetting("Colors", "Uses Sydney's default accent color for the whole client.", true);
    public BooleanSetting watermark = new BooleanSetting("Watermark", "Renames the HUD watermark to Sydney and hides its logo.", true);
    public BooleanSetting welcomer = new BooleanSetting("Welcomer", "Uses Sydney's default welcomer message.", true);
    public BooleanSetting chatPrefix = new BooleanSetting("ChatPrefix", "Renames the chat prefix from Night to Sydney.", true);
    public StringSetting version = new StringSetting("Version", "The version rendered after the Sydney watermark.", "1.4.7");

    private ColorSetting.Color previousColor;
    private String previousChatWatermark;
    private String previousHudWatermark;
    private String previousWelcomerText;
    private boolean previousWelcomerEnabled;
    private boolean previousLogo;

    public static String getVersionOverride() {
        return versionOverride;
    }

    @Override
    public void onEnable() {
        if (Night.MODULE_MANAGER == null) {
            return;
        }
        versionOverride = this.version.getValue();

        if (this.colors.getValue()) {
            ColorModule colorModule = Night.MODULE_MANAGER.getModule(ColorModule.class);
            if (colorModule != null) {
                this.previousColor = colorModule.color.getValue();
                colorModule.color.setValue(new ColorSetting.Color(SYDNEY_COLOR, false, false));
            }
        }

        if (this.chatPrefix.getValue()) {
            CommandsModule commandsModule = Night.MODULE_MANAGER.getModule(CommandsModule.class);
            if (commandsModule != null) {
                this.previousChatWatermark = commandsModule.watermarkText.getValue();
                commandsModule.watermarkText.setValue("Sydney");
            }
        }

        HUDModule hud = Night.MODULE_MANAGER.getModule(HUDModule.class);
        if (hud == null) {
            return;
        }
        if (this.watermark.getValue()) {
            this.previousHudWatermark = hud.watermarkText.getValue();
            this.previousLogo = hud.watermarkLogo.getValue();
            hud.watermarkText.setValue("Sydney");
            hud.watermarkLogo.setValue(false);
        }
        if (this.welcomer.getValue()) {
            this.previousWelcomerEnabled = hud.welcomer.getValue();
            this.previousWelcomerText = hud.welcomerText.getValue();
            hud.welcomer.setValue(true);
            hud.welcomerText.setValue(SYDNEY_WELCOMER);
        }
    }

    @Override
    public void onDisable() {
        versionOverride = null;
        if (Night.MODULE_MANAGER == null) {
            return;
        }

        HUDModule hud = Night.MODULE_MANAGER.getModule(HUDModule.class);
        if (hud != null) {
            if (this.previousWelcomerText != null) {
                hud.welcomerText.setValue(this.previousWelcomerText);
                hud.welcomer.setValue(this.previousWelcomerEnabled);
                this.previousWelcomerText = null;
            }
            if (this.previousHudWatermark != null) {
                hud.watermarkText.setValue(this.previousHudWatermark);
                hud.watermarkLogo.setValue(this.previousLogo);
                this.previousHudWatermark = null;
            }
        }

        CommandsModule commandsModule = Night.MODULE_MANAGER.getModule(CommandsModule.class);
        if (commandsModule != null && this.previousChatWatermark != null) {
            commandsModule.watermarkText.setValue(this.previousChatWatermark);
            this.previousChatWatermark = null;
        }

        ColorModule colorModule = Night.MODULE_MANAGER.getModule(ColorModule.class);
        if (colorModule != null && this.previousColor != null) {
            colorModule.color.setValue(this.previousColor);
            this.previousColor = null;
        }
    }
}
