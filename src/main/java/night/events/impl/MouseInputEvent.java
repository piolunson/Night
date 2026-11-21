/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class MouseInputEvent
extends Event {
    private final int button;

    public int getButton() {
        return this.button;
    }

    public MouseInputEvent(int button) {
        this.button = button;
    }
}

