/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.network.Connection
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.RegistryFriendlyByteBuf
 *  net.minecraft.network.protocol.game.GameProtocols
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package night.pingbypass.server;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.GameProtocols;
import night.Night;
import night.events.SubscribeEvent;
import night.events.impl.PlayerUpdateEvent;
import night.pingbypass.PingBypassFlags;
import night.pingbypass.handler.PbPlayHandler;
import night.pingbypass.server.S2CForwarder;
import night.pingbypass.server.WorldStateReplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferRehook {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferRehook.class);

    public TransferRehook() {
        Night.EVENT_HANDLER.subscribe(this);
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        Connection tracked;
        if (Night.PINGBYPASS_CONFIG == null || !Night.PINGBYPASS_CONFIG.isServer()) {
            return;
        }
        if (!PingBypassFlags.proxyForwardingActive) {
            return;
        }
        if (Night.PROXY_SERVER == null) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.getConnection() == null || mc.player == null || mc.level == null) {
            return;
        }
        Connection current = mc.getConnection().getConnection();
        if (current == (tracked = Night.PROXY_SERVER.getServerConnection()) || !current.isConnected()) {
            return;
        }
        LOGGER.warn("[PB] Proxy's own server connection changed underneath us (transfer/backend switch) -- rewiring {} connected client(s)", (Object)Night.PROXY_SERVER.getConnections().size());
        Night.PROXY_SERVER.setServerConnection(current);
        RegistryAccess registry = mc.level.registryAccess();
        for (Connection clientConnection : Night.PROXY_SERVER.getConnections()) {
            PacketListener packetListener;
            if (!clientConnection.isConnected() || !((packetListener = clientConnection.getPacketListener()) instanceof PbPlayHandler)) continue;
            PbPlayHandler oldHandler = (PbPlayHandler)packetListener;
            GameProfile profile = oldHandler.getProfile();
            try {
                clientConnection.setupOutboundProtocol(GameProtocols.CLIENTBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)registry)));
                S2CForwarder s2cForwarder = new S2CForwarder(clientConnection);
                s2cForwarder.start();
                int initialTeleportId = WorldStateReplay.replay(clientConnection, registry);
                PbPlayHandler newHandler = new PbPlayHandler(Night.PROXY_SERVER, clientConnection, profile, s2cForwarder, initialTeleportId);
                night.pingbypass.ProtocolUtils.setupInbound(clientConnection, GameProtocols.SERVERBOUND_TEMPLATE.bind(RegistryFriendlyByteBuf.decorator((RegistryAccess)registry), () -> false), (PacketListener)newHandler);
                LOGGER.info("[PB] Rewired {} onto the new server connection after transfer", (Object)profile.name());
            }
            catch (Exception e) {
                LOGGER.error("[PB] Failed to rewire {} after transfer", (Object)profile.name(), (Object)e);
            }
        }
    }
}

