/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;
import night.modules.Module;

public class ToggleModuleEvent
extends Event {
    private final Module module;
    private final boolean state;

    public ToggleModuleEvent(Module module, boolean state) {
        this.module = module;
        this.state = state;
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isState() {
        return this.state;
    }
}

