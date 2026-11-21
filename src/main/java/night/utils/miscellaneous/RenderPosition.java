/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.core.BlockPos
 */
package night.utils.miscellaneous;

import net.minecraft.core.BlockPos;
import night.Night;
import night.modules.impl.core.RendersModule;
import night.utils.animations.Easing;

public class RenderPosition {
    private BlockPos pos;
    private long startTime;

    public RenderPosition(BlockPos pos) {
        this.pos = pos;
        this.startTime = System.currentTimeMillis();
    }

    public boolean equals(Object o) {
        if (o instanceof RenderPosition) {
            return ((RenderPosition)o).pos.equals(this.pos);
        }
        return false;
    }

    public float get() {
        return Easing.ease(1.0f - Easing.toDelta(this.startTime, Night.MODULE_MANAGER.getModule(RendersModule.class).duration.getValue().intValue()), Easing.Method.EASE_IN_CUBIC);
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public long getStartTime() {
        return this.startTime;
    }
}

