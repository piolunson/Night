/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket
 *  net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket
 *  net.minecraft.network.protocol.game.ClientboundGameEventPacket
 *  net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket
 *  net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket
 *  net.minecraft.network.protocol.game.ClientboundLoginPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Action
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Entry
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundRespawnPacket
 *  net.minecraft.network.protocol.game.ClientboundRotateHeadPacket
 *  net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket
 *  net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket
 *  net.minecraft.network.protocol.game.ClientboundSetExperiencePacket
 *  net.minecraft.network.protocol.game.ClientboundSetHealthPacket
 *  net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket
 *  net.minecraft.network.protocol.game.ClientboundSetTimePacket
 *  net.minecraft.network.protocol.game.CommonPlayerSpawnInfo
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PositionMoveRotation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.GameType
 *  net.minecraft.world.level.chunk.LevelChunk
 *  net.minecraft.world.level.chunk.status.ChunkStatus
 *  net.minecraft.world.phys.Vec3
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package night.pingbypass.server;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.Vec3;
import night.mixins.accessors.PlayerListS2CPacketAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldStateReplay {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldStateReplay.class);
    private static final Random RANDOM = new Random();

    public static int replay(Connection toClient, RegistryAccess registryAccess) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        ClientLevel world = mc.level;
        ClientPacketListener handler = mc.getConnection();
        if (player == null || world == null || handler == null) {
            LOGGER.error("Cannot replay world state: player/world/handler is null");
            return 0;
        }
        LOGGER.info("Replaying world state to client...");
        long start = System.currentTimeMillis();
        int initialTeleportId = -Math.abs(RANDOM.nextInt());
        if (initialTeleportId == 0) {
            initialTeleportId = -1;
        }
        GameType gameMode = mc.gameMode != null ? mc.gameMode.getPlayerMode() : GameType.SURVIVAL;
        GameType prevGameMode = mc.gameMode != null ? mc.gameMode.getPreviousPlayerMode() : null;
        Set dimensionIds = handler.levels();
        CommonPlayerSpawnInfo spawnInfo = WorldStateReplay.createSpawnInfo(world, gameMode, prevGameMode, player, registryAccess);
        WorldStateReplay.send(toClient, new ClientboundLoginPacket(player.getId(), world.getLevelData().isHardcore(), dimensionIds, 1, 16, 16, false, true, false, spawnInfo, false, false));
        WorldStateReplay.send(toClient, new ClientboundRespawnPacket(spawnInfo, (byte)0));
        WorldStateReplay.send(toClient, new ClientboundChangeDifficultyPacket(world.getLevelData().getDifficulty(), world.getLevelData().isDifficultyLocked()));
        WorldStateReplay.send(toClient, new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
        WorldStateReplay.send(toClient, new ClientboundSetHeldSlotPacket(player.getInventory().getSelectedSlot()));
        WorldStateReplay.sendPlayerInfo(toClient, handler);
        WorldStateReplay.sendLevelInfo(toClient, world);
        WorldStateReplay.send(toClient, new ClientboundGameEventPacket(ClientboundGameEventPacket.LEVEL_CHUNKS_LOAD_START, 0.0f));
        WorldStateReplay.sendChunks(toClient, player, world);
        WorldStateReplay.send(toClient, new ClientboundSetHealthPacket(player.getHealth(), player.getFoodData().getFoodLevel(), player.getFoodData().getSaturationLevel()));
        WorldStateReplay.send(toClient, new ClientboundSetExperiencePacket(player.experienceProgress, player.totalExperience, player.experienceLevel));
        WorldStateReplay.send(toClient, new ClientboundContainerSetContentPacket(player.inventoryMenu.containerId, player.inventoryMenu.incrementStateId(), (List)player.inventoryMenu.getItems(), player.inventoryMenu.getCarried()));
        WorldStateReplay.sendEntities(toClient, world, player);
        WorldStateReplay.send(toClient, new ClientboundPlayerPositionPacket(initialTeleportId, new PositionMoveRotation(player.position(), Vec3.ZERO, player.getYRot(), player.getXRot()), Set.of()));
        WorldStateReplay.send(toClient, new ClientboundSetEntityMotionPacket((Entity)player));
        long elapsed = System.currentTimeMillis() - start;
        LOGGER.info("Level state replay completed in {}ms (teleportId={})", (Object)elapsed, (Object)initialTeleportId);
        return initialTeleportId;
    }

    private static void sendLevelInfo(Connection toClient, ClientLevel world) {
        WorldStateReplay.send(toClient, new ClientboundInitializeBorderPacket(world.getWorldBorder()));
        WorldStateReplay.send(toClient, new ClientboundSetTimePacket(world.getLevelData().getGameTime(), Map.of()));
        WorldStateReplay.send(toClient, new ClientboundSetDefaultSpawnPositionPacket(world.getLevelData().getRespawnData()));
        if (world.isRaining()) {
            WorldStateReplay.send(toClient, new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0f));
            WorldStateReplay.send(toClient, new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, world.getRainLevel(1.0f)));
            WorldStateReplay.send(toClient, new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, world.getThunderLevel(1.0f)));
        }
    }

    private static void sendPlayerInfo(Connection toClient, ClientPacketListener handler) {
        Collection<PlayerInfo> playerList = handler.getOnlinePlayers();
        if (playerList.isEmpty()) {
            return;
        }
        ArrayList<ClientboundPlayerInfoUpdatePacket.Entry> entries = new ArrayList<ClientboundPlayerInfoUpdatePacket.Entry>();
        for (PlayerInfo info : playerList) {
            entries.add(new ClientboundPlayerInfoUpdatePacket.Entry(info.getProfile().id(), info.getProfile(), handler.getListedOnlinePlayers().contains(info), info.getLatency(), info.getGameMode(), info.getTabListDisplayName(), false, 0, null));
        }
        EnumSet<ClientboundPlayerInfoUpdatePacket.Action> actions = EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME);
        ClientboundPlayerInfoUpdatePacket packet = new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER), Collections.emptyList());
        ((PlayerListS2CPacketAccessor)(Object)packet).setActions(actions);
        ((PlayerListS2CPacketAccessor)(Object)packet).setEntries(entries);
        WorldStateReplay.send(toClient, packet);
        LOGGER.info("Sent {} player list entries to client", (Object)entries.size());
    }

    private static CommonPlayerSpawnInfo createSpawnInfo(ClientLevel world, GameType gameMode, GameType prevGameMode, LocalPlayer player, RegistryAccess registryAccess) {
        ResourceKey dimensionTypeKey = (ResourceKey)world.dimensionTypeRegistration().unwrapKey().orElseThrow();
        Holder.Reference dimensionType = registryAccess.lookupOrThrow(Registries.DIMENSION_TYPE).getOrThrow(dimensionTypeKey);
        return new CommonPlayerSpawnInfo((Holder)dimensionType, world.dimension(), 0L, gameMode, prevGameMode, world.isDebug(), false, player.getLastDeathLocation(), player.getPortalCooldown(), world.getSeaLevel());
    }

    private static void sendEntities(Connection toClient, ClientLevel world, LocalPlayer localPlayer) {
        int count = 0;
        for (Entity entity : world.entitiesForRendering()) {
            if (entity == localPlayer) continue;
            try {
                WorldStateReplay.send(toClient, new ClientboundAddEntityPacket(entity.getId(), entity.getUUID(), entity.getX(), entity.getY(), entity.getZ(), entity.getXRot(), entity.getYRot(), entity.getType(), 0, entity.getDeltaMovement(), (double)entity.getYHeadRot()));
                List entries = entity.getEntityData().getNonDefaultValues();
                if (entries != null && !entries.isEmpty()) {
                    WorldStateReplay.send(toClient, new ClientboundSetEntityDataPacket(entity.getId(), entries));
                }
                WorldStateReplay.send(toClient, new ClientboundSetEntityMotionPacket(entity));
                if (entity instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity)entity;
                    WorldStateReplay.send(toClient, new ClientboundRotateHeadPacket(entity, (byte)(living.getYHeadRot() * 256.0f / 360.0f)));
                    ArrayList<Pair<EquipmentSlot, ItemStack>> equipment = new ArrayList<Pair<EquipmentSlot, ItemStack>>();
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        ItemStack stack = living.getItemBySlot(slot);
                        if (stack.isEmpty()) continue;
                        equipment.add(new Pair<EquipmentSlot, ItemStack>(slot, stack));
                    }
                    if (!equipment.isEmpty()) {
                        WorldStateReplay.send(toClient, new ClientboundSetEquipmentPacket(entity.getId(), equipment));
                    }
                }
                ++count;
            }
            catch (Exception e) {
                LOGGER.warn("Failed to send entity {} (ID: {})", new Object[]{entity.getType().getDescriptionId(), entity.getId(), e});
            }
        }
        LOGGER.info("Sent {} entities to client", (Object)count);
    }

    private static void sendChunks(Connection toClient, LocalPlayer player, ClientLevel world) {
        int cx = player.chunkPosition().x();
        int cz = player.chunkPosition().z();
        WorldStateReplay.send(toClient, new ClientboundSetChunkCacheCenterPacket(cx, cz));
        int radius = Math.min((Integer)Minecraft.getInstance().options.renderDistance().get(), 8);
        int sent = 0;
        for (int z = cz - radius; z <= cz + radius; ++z) {
            for (int x = cx - radius; x <= cx + radius; ++x) {
                LevelChunk chunk = world.getChunkSource().getChunk(x, z, ChunkStatus.FULL, false);
                if (chunk == null) continue;
                WorldStateReplay.send(toClient, new ClientboundLevelChunkWithLightPacket(chunk, world.getChunkSource().getLightEngine(), null, null));
                ++sent;
            }
        }
        LOGGER.info("Sent {} chunks to client", (Object)sent);
    }

    private static void send(Connection connection, Packet<?> packet) {
        if (connection.isConnected()) {
            connection.send(packet);
        }
    }
}

