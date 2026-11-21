/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerInputPacket
 *  net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket
 */
package night.managers;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import night.Night;
import night.events.SubscribeEvent;
import night.events.impl.PacketSendEvent;
import night.utils.IMinecraft;

public class PositionManager
implements IMinecraft {
    private double serverX;
    private double serverY;
    private double serverZ;
    private boolean serverOnGround;
    private boolean serverSprinting;
    private boolean serverSneaking;
    private int serverSlot;

    public PositionManager() {
        Night.EVENT_HANDLER.subscribe(this);
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (PositionManager.mc.player == null) {
            return;
        }
        Packet<?> raw = event.getPacket();
        if (raw instanceof ServerboundMovePlayerPacket) {
            ServerboundMovePlayerPacket move = (ServerboundMovePlayerPacket)raw;
            if (move.hasPosition()) {
                this.serverX = move.getX(PositionManager.mc.player.getX());
                this.serverY = move.getY(PositionManager.mc.player.getY());
                this.serverZ = move.getZ(PositionManager.mc.player.getZ());
            }
            this.serverOnGround = move.isOnGround();
        }
        if (raw instanceof ServerboundSetCarriedItemPacket) {
            this.serverSlot = ((ServerboundSetCarriedItemPacket)raw).getSlot();
        }
        if (raw instanceof ServerboundPlayerCommandPacket) {
            ServerboundPlayerCommandPacket command = (ServerboundPlayerCommandPacket)raw;
            switch (command.getAction()) {
                case START_SPRINTING -> this.serverSprinting = true;
                case STOP_SPRINTING -> this.serverSprinting = false;
            }
        }
        if (raw instanceof ServerboundPlayerInputPacket) {
            this.serverSneaking = ((ServerboundPlayerInputPacket)raw).input().shift();
        }
    }

    public double getServerX() {
        return PositionManager.mc.player != null ? PositionManager.mc.player.getX() : this.serverX;
    }

    public double getServerY() {
        return PositionManager.mc.player != null ? PositionManager.mc.player.getY() : this.serverY;
    }

    public double getServerZ() {
        return PositionManager.mc.player != null ? PositionManager.mc.player.getZ() : this.serverZ;
    }

    public boolean isServerOnGround() {
        return PositionManager.mc.player != null ? PositionManager.mc.player.onGround() : this.serverOnGround;
    }

    public boolean isServerSprinting() {
        return this.serverSprinting;
    }

    public boolean isServerSneaking() {
        return this.serverSneaking;
    }

    public int getServerSlot() {
        return this.serverSlot;
    }
}

