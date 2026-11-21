/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;
import night.modules.Module;
import night.utils.rotations.Rotation;

public class ClientRotationEvent
extends Event {
    private final Rotation rotation;
    private Module owner;

    public ClientRotationEvent(Rotation rotation) {
        this.rotation = rotation;
    }

    public void setYaw(float yaw) {
        this.setCancelled(true);
        this.rotation.setYaw(yaw);
    }

    public void setPitch(float pitch) {
        this.setCancelled(true);
        this.rotation.setPitch(pitch);
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public Module getOwner() {
        return this.owner;
    }

    public void setOwner(Module owner) {
        this.owner = owner;
    }
}

