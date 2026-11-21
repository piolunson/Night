/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class ChangePitchEvent
extends Event {
    private final float pitch;

    public float getPitch() {
        return this.pitch;
    }

    public ChangePitchEvent(float pitch) {
        this.pitch = pitch;
    }
}

