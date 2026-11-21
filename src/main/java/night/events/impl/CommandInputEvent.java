/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;

public class CommandInputEvent
extends Event {
    private final String message;

    public String getMessage() {
        return this.message;
    }

    public CommandInputEvent(String message) {
        this.message = message;
    }
}

