/*
 * Decompiled with CFR 0.152.
 */
package night.gui.impl;

import java.awt.Color;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import night.Night;
import night.gui.ClickGuiScreen;
import night.gui.PingBypassScreen;
import night.gui.api.Button;
import night.gui.api.Frame;
import night.modules.impl.core.PingBypassModule;
import night.pingbypass.PingBypassFlags;
import night.utils.graphics.Renderer2D;

public class PingBypassButton
extends Button {
    private final PingBypassModule module;

    public PingBypassButton(PingBypassModule module, Frame parent, int height) {
        super(parent, height, "Left-click to open the PingBypass settings (IP, port, password, target server). Right-click to connect or disconnect.");
        this.module = module;
    }

    @Override
    public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        boolean hovered = this.isHovering(mouseX, mouseY);
        if (hovered && Night.CLICK_GUI.getDescriptionFrame().getDescription().isEmpty()) {
            Night.CLICK_GUI.getDescriptionFrame().setDescription(this.getDescription());
        }
        if (hovered) {
            Renderer2D.renderQuad(context, this.getX() + this.getPadding(), this.getY(), this.getX() + this.getWidth() - this.getPadding(), this.getY() + this.getHeight() - 1, new Color(255, 255, 255, 15));
        }
        if (!this.module.isToggled()) {
            Renderer2D.renderQuad(context, this.getX() + this.getPadding(), this.getY(), this.getX() + this.getWidth() - this.getPadding(), this.getY() + this.getHeight() - 1, new Color(20, 20, 25, 120));
        }
        Color separator = ClickGuiScreen.getButtonColor(this.getY(), 60);
        Renderer2D.renderQuad(context, this.getX() + this.getPadding(), this.getY() + this.getHeight() - 1, this.getX() + this.getWidth() - this.getPadding(), this.getY() + this.getHeight(), separator);
        String label = "PingBypass";
        if (!this.module.isToggled()) {
            label = "\u00a77" + label;
        }
        Night.FONT_MANAGER.drawTextWithShadow(context, label, this.getX() + this.getTextPadding(), this.getY() + 2, Color.WHITE);
        String status;
        Color statusColor;
        if (this.module.isToggled()) {
            if (PingBypassFlags.proxyForwardingActive) {
                status = this.module.proxyPing + "ms";
                statusColor = new Color(120, 255, 140);
            } else {
                status = "Connecting...";
                statusColor = new Color(255, 200, 90);
            }
        } else {
            status = "Config";
            statusColor = new Color(200, 200, 215, 170);
        }
        int statusX = this.getX() + this.getWidth() - this.getTextPadding() - 1 - Night.FONT_MANAGER.getWidth(status);
        Night.FONT_MANAGER.drawTextWithShadow(context, status, statusX, this.getY() + 2, statusColor);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.isHovering(mouseX, mouseY)) {
            return;
        }
        if (button == 0) {
            Screen parent = PingBypassButton.mc.gui.screen();
            PingBypassButton.mc.gui.setScreen(new PingBypassScreen(parent));
            this.playClickSound();
        } else if (button == 1) {
            this.module.setToggled(!this.module.isToggled());
            this.playClickSound();
        }
    }

    public PingBypassModule getModule() {
        return this.module;
    }
}
