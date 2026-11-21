/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.world.entity.player.Player
 */
package night.events.impl;

import net.minecraft.world.entity.player.Player;
import night.events.Event;

public class PlayerPopEvent
extends Event {
    private final Player player;
    private final int pops;

    public Player getPlayer() {
        return this.player;
    }

    public int getPops() {
        return this.pops;
    }

    public PlayerPopEvent(Player player, int pops) {
        this.player = player;
        this.pops = pops;
    }
}

