/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.core.BlockPos
 */
package night.events.impl;

import net.minecraft.core.BlockPos;
import night.events.Event;

public class BreakBlockEvent
extends Event {
    private final BlockPos pos;

    public BlockPos getPos() {
        return this.pos;
    }

    public BreakBlockEvent(BlockPos pos) {
        this.pos = pos;
    }
}

