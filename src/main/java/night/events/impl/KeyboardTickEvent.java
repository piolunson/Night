/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class KeyboardTickEvent
extends Event {
    private float movementForward;
    private float movementSideways;

    public KeyboardTickEvent(float movementForward, float movementSideways) {
        this.movementForward = movementForward;
        this.movementSideways = movementSideways;
    }

    public float getMovementForward() {
        return this.movementForward;
    }

    public float getMovementSideways() {
        return this.movementSideways;
    }

    public void setMovementForward(float movementForward) {
        this.movementForward = movementForward;
    }

    public void setMovementSideways(float movementSideways) {
        this.movementSideways = movementSideways;
    }
}

