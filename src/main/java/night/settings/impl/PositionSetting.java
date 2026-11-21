/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.settings.impl;

import night.settings.Setting;

public class PositionSetting
extends Setting {
    private float x = 0.0f;
    private float y = 0.0f;

    public PositionSetting(String name, String description) {
        super(name, name, description, new Setting.Visibility());
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void resetValue() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

