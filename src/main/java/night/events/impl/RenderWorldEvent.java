/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  lombok.Generated
 */
package night.events.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import night.events.Event;

public class RenderWorldEvent
extends Event {
    private final PoseStack matrices;
    private final float tickDelta;

    public PoseStack getMatrices() {
        return this.matrices;
    }

    public float getTickDelta() {
        return this.tickDelta;
    }

    public RenderWorldEvent(PoseStack matrices, float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    public static class Post
    extends Event {
        private final PoseStack matrices;
        private final float tickDelta;

        public PoseStack getMatrices() {
            return this.matrices;
        }

        public float getTickDelta() {
            return this.tickDelta;
        }

        public Post(PoseStack matrices, float tickDelta) {
            this.matrices = matrices;
            this.tickDelta = tickDelta;
        }
    }
}

