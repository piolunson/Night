/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package night.events.impl;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import night.events.Event;

public class AttackEntityEvent
extends Event {
    private final Player player;
    private final Entity target;

    public Player getPlayer() {
        return this.player;
    }

    public Entity getTarget() {
        return this.target;
    }

    public AttackEntityEvent(Player player, Entity target) {
        this.player = player;
        this.target = target;
    }
}

