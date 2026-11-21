/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.events.impl;

import night.events.Event;
import night.settings.Setting;

public class SettingChangeEvent
extends Event {
    private final Setting setting;

    public Setting getSetting() {
        return this.setting;
    }

    public SettingChangeEvent(Setting setting) {
        this.setting = setting;
    }
}

