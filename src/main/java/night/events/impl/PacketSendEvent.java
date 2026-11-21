/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.network.protocol.Packet
 */
package night.events.impl;

import net.minecraft.network.protocol.Packet;
import night.events.Event;

public class PacketSendEvent
extends Event {
    private final Packet<?> packet;

    public Packet<?> getPacket() {
        return this.packet;
    }

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public static class Post
    extends Event {
        private final Packet<?> packet;

        public Packet<?> getPacket() {
            return this.packet;
        }

        public Post(Packet<?> packet) {
            this.packet = packet;
        }
    }
}

