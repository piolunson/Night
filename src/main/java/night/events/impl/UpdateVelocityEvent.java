/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.world.phys.Vec3
 */
package night.events.impl;

import net.minecraft.world.phys.Vec3;
import night.events.Event;

public class UpdateVelocityEvent
extends Event {
    private final Vec3 movementInput;
    private final float speed;
    private Vec3 velocity;

    public Vec3 getMovementInput() {
        return this.movementInput;
    }

    public float getSpeed() {
        return this.speed;
    }

    public Vec3 getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vec3 velocity) {
        this.velocity = velocity;
    }

    public UpdateVelocityEvent(Vec3 movementInput, float speed) {
        this.movementInput = movementInput;
        this.speed = speed;
    }
}

