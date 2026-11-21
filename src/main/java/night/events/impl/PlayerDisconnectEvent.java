/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import java.util.UUID;
import night.events.Event;

public class PlayerDisconnectEvent
extends Event {
    private final UUID id;

    public PlayerDisconnectEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return this.id;
    }
}

