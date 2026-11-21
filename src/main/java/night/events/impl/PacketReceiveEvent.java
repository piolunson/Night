/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.Packet
 */
package night.events.impl;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import night.events.Event;

public class PacketReceiveEvent
extends Event {
    private final Packet<?> packet;
    private final Connection connection;

    public PacketReceiveEvent(Packet<?> packet) {
        this(packet, null);
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public PacketReceiveEvent(Packet<?> packet, Connection connection) {
        this.packet = packet;
        this.connection = connection;
    }
}

