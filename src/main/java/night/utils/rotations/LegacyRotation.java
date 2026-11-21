/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package night.utils.rotations;

import night.modules.Module;

public class LegacyRotation {
    private float yaw;
    private float pitch;
    private final Module module;
    private final int priority;
    private long time;

    public LegacyRotation(float yaw, float pitch, int priority) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.module = null;
        this.priority = priority;
        this.time = System.currentTimeMillis();
    }

    public LegacyRotation(float yaw, float pitch, Module module, int priority) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.module = module;
        this.priority = priority;
        this.time = System.currentTimeMillis();
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Module getModule() {
        return this.module;
    }

    public int getPriority() {
        return this.priority;
    }

    public long getTime() {
        return this.time;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

