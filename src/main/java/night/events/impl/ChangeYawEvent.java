/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class ChangeYawEvent
extends Event {
    private final float yaw;

    public float getYaw() {
        return this.yaw;
    }

    public ChangeYawEvent(float yaw) {
        this.yaw = yaw;
    }
}

