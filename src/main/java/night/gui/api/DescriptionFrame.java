/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 */
package night.gui.api;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import night.Night;
import night.gui.ClickGuiScreen;
import night.modules.impl.core.ClickGuiModule;
import night.utils.graphics.Renderer2D;
import night.utils.text.FormattingUtils;

public class DescriptionFrame {
    private String description = "";
    private int x;
    private int y;
    private int width;
    private int height;
    private int dragX = 0;
    private int dragY = 0;
    private int textPadding = 3;
    private boolean dragging = false;

    public DescriptionFrame(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        if (this.dragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
        ClickGuiModule clickGui = Night.MODULE_MANAGER.getModule(ClickGuiModule.class);
        boolean glow = clickGui.glowAccent.getValue();
        Renderer2D.renderQuad(context, this.x, this.y, this.x + this.width, this.y + this.height, ClickGuiScreen.getButtonColor(this.y, 55));
        if (glow) {
            Renderer2D.renderQuad(context, this.x, this.y, this.x + this.width, this.y + 1, ClickGuiScreen.getButtonColor(this.y, 220));
        }
        Color accentColor = ClickGuiScreen.getButtonColor(this.y, 220);
        Renderer2D.renderQuad(context, this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, accentColor);
        Night.FONT_MANAGER.drawTextWithShadow(context, "\u2139 Description", this.x + this.textPadding, this.y + 2, Color.WHITE);
        String searchHint = "[Ctrl+F]";
        Night.FONT_MANAGER.drawTextWithShadow(context, searchHint, this.x + this.width - this.textPadding - Night.FONT_MANAGER.getWidth(searchHint), this.y + 2, new Color(130, 130, 140));
        List<String> wrappedText = this.description.isEmpty() ? List.of("Hover over a module or setting to view details.", "Ctrl+F to search") : FormattingUtils.wrapText(this.description, this.width - this.textPadding * 2);
        int contentHeight = wrappedText.size() * Night.FONT_MANAGER.getHeight() + 6;
        Renderer2D.renderQuad(context, this.x, this.y + this.height, this.x + this.width, this.y + this.height + contentHeight, new Color(14, 13, 20, 195));
        int i = 0;
        for (String s : wrappedText) {
            Color textCol = this.description.isEmpty() ? (i == 1 ? new Color(125, 125, 135) : new Color(140, 140, 150)) : Color.WHITE;
            Night.FONT_MANAGER.drawTextWithShadow(context, s, this.x + this.textPadding, this.y + this.height + 3 + Night.FONT_MANAGER.getHeight() * i, textCol);
            ++i;
        }
        Color borderColor = ClickGuiScreen.getButtonColor(this.y, 130);
        Renderer2D.renderOutline(context, this.x, this.y, this.x + this.width, this.y + this.height + contentHeight, borderColor);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHovering(mouseX, mouseY) && button == 0) {
            this.dragging = true;
            this.dragX = (int)(mouseX - (double)this.getX());
            this.dragY = (int)(mouseY - (double)this.getY());
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }

    public boolean isHovering(double mouseX, double mouseY) {
        return (double)this.x <= mouseX && (double)this.y <= mouseY && (double)(this.x + this.width) > mouseX && (double)(this.y + this.height) > mouseY;
    }

    public String getDescription() {
        return this.description;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDragX() {
        return this.dragX;
    }

    public int getDragY() {
        return this.dragY;
    }

    public int getTextPadding() {
        return this.textPadding;
    }

    public boolean isDragging() {
        return this.dragging;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDragX(int dragX) {
        this.dragX = dragX;
    }

    public void setDragY(int dragY) {
        this.dragY = dragY;
    }

    public void setTextPadding(int textPadding) {
        this.textPadding = textPadding;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}

