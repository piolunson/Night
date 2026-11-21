/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class UnfilteredMouseInputEvent
extends Event {
    private final int button;
    private final int action;
    private final int mods;

    public int getButton() {
        return this.button;
    }

    public int getAction() {
        return this.action;
    }

    public int getMods() {
        return this.mods;
    }

    public UnfilteredMouseInputEvent(int button, int action, int mods) {
        this.button = button;
        this.action = action;
        this.mods = mods;
    }
}

