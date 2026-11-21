/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  org.joml.Matrix3x2fStack
 */
package night.events.impl;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import night.events.Event;
import org.joml.Matrix3x2fStack;

public class RenderOverlayEvent
extends Event {
    private final GuiGraphicsExtractor context;
    private final float tickDelta;

    public Matrix3x2fStack getMatrices() {
        return this.context.pose();
    }

    public GuiGraphicsExtractor getContext() {
        return this.context;
    }

    public float getTickDelta() {
        return this.tickDelta;
    }

    public RenderOverlayEvent(GuiGraphicsExtractor context, float tickDelta) {
        this.context = context;
        this.tickDelta = tickDelta;
    }
}

