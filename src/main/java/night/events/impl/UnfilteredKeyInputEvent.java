/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class UnfilteredKeyInputEvent
extends Event {
    private final int key;
    private final int scancode;
    private final int action;
    private final int modifiers;

    public int getKey() {
        return this.key;
    }

    public int getScancode() {
        return this.scancode;
    }

    public int getAction() {
        return this.action;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public UnfilteredKeyInputEvent(int key, int scancode, int action, int modifiers) {
        this.key = key;
        this.scancode = scancode;
        this.action = action;
        this.modifiers = modifiers;
    }
}

