/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DynamicOps
 *  net.minecraft.core.LayeredRegistryAccess
 *  net.minecraft.core.Registry$PendingTags
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistryAccess$Frozen
 *  net.minecraft.core.RegistrySynchronization
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket
 *  net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket
 *  net.minecraft.resources.RegistryDataLoader
 *  net.minecraft.resources.RegistryOps
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.RegistryLayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.repository.KnownPack
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.repository.ServerPacksSource
 *  net.minecraft.server.packs.resources.MultiPackResourceManager
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.tags.TagLoader
 *  net.minecraft.tags.TagNetworkSerialization
 *  net.minecraft.world.level.WorldDataConfiguration
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package night.pingbypass.server;

import com.mojang.serialization.DynamicOps;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Stream;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.configuration.ClientboundRegistryDataPacket;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.world.level.WorldDataConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistryCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryCache.class);
    private static final Executor SYNC_EXECUTOR = Runnable::run;
    private final List<Packet<?>> registryPackets = new ArrayList();
    private final List<KnownPack> knownPacks = new ArrayList<KnownPack>();
    private LayeredRegistryAccess<RegistryLayer> registries;
    private RegistryAccess registryManager;
    private boolean loaded;
    private volatile boolean loadAttempted;

    public void load() {
        LOGGER.info("Loading vanilla registries for proxy...");
        long start = System.currentTimeMillis();
        try {
            PackRepository packRepository = ServerPacksSource.createVanillaTrustedRepository();
            MinecraftServer.configurePackRepository((PackRepository)packRepository, (WorldDataConfiguration)WorldDataConfiguration.DEFAULT, (boolean)true, (boolean)true);
            List packs = packRepository.openAllSelected();
            try (MultiPackResourceManager resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, packs);){
                LayeredRegistryAccess initialLayers = RegistryLayer.createRegistryAccess();
                List staticTags = TagLoader.loadTagsForExistingRegistries((ResourceManager)resourceManager, (RegistryAccess)initialLayers.getLayer(RegistryLayer.STATIC));
                RegistryAccess.Frozen worldgenLoadContext = initialLayers.getAccessForLoading(RegistryLayer.WORLDGEN);
                List worldgenContextRegistries = TagLoader.buildUpdatedLookups((RegistryAccess.Frozen)worldgenLoadContext, (List)staticTags);
                RegistryAccess.Frozen worldgenRegistries = (RegistryAccess.Frozen)RegistryDataLoader.load((ResourceManager)resourceManager, (List)worldgenContextRegistries, (List)RegistryDataLoader.WORLDGEN_REGISTRIES, (Executor)SYNC_EXECUTOR).join();
                List dimensionContextRegistries = Stream.concat(worldgenContextRegistries.stream(), worldgenRegistries.listRegistries()).toList();
                RegistryAccess.Frozen dimensionRegistries = (RegistryAccess.Frozen)RegistryDataLoader.load((ResourceManager)resourceManager, dimensionContextRegistries, (List)RegistryDataLoader.DIMENSION_REGISTRIES, (Executor)SYNC_EXECUTOR).join();
                this.registries = initialLayers.replaceFrom(RegistryLayer.WORLDGEN, new RegistryAccess.Frozen[]{worldgenRegistries, dimensionRegistries});
                for (Object pendingObj : staticTags) {
                    ((Registry.PendingTags)pendingObj).apply();
                }
                this.registryManager = this.registries.compositeAccess();
                this.knownPacks.addAll(resourceManager.listPacks().flatMap(pack -> pack.knownPackInfo().stream()).toList());
                RegistryOps ops = this.registries.compositeAccess().createSerializationContext((DynamicOps)NbtOps.INSTANCE);
                RegistrySynchronization.packRegistries((DynamicOps)ops, (RegistryAccess)this.registries.getAccessFrom(RegistryLayer.WORLDGEN), Set.of(), (key, entries) -> this.registryPackets.add((Packet<?>)new ClientboundRegistryDataPacket(key, entries)));
                this.registryPackets.add((Packet<?>)new ClientboundUpdateTagsPacket(TagNetworkSerialization.serializeTagsToNetwork(this.registries)));
                this.loaded = true;
            }
            long elapsed = System.currentTimeMillis() - start;
            LOGGER.info("Loaded {} registry packets in {}ms", this.registryPackets.size(), (Object)elapsed);
        }
        catch (Exception e) {
            LOGGER.error("Failed to load vanilla registries", (Throwable)e);
        }
    }

    public boolean isLoaded() {
        if (!this.loaded && !this.loadAttempted) {
            this.loadAttempted = true;
            this.load();
        }
        return this.loaded;
    }

    public List<Packet<?>> getRegistryPackets() {
        return this.registryPackets;
    }

    public List<KnownPack> getKnownPacks() {
        return this.knownPacks;
    }

    public LayeredRegistryAccess<RegistryLayer> getRegistries() {
        return this.registries;
    }

    public RegistryAccess getRegistryManager() {
        return this.registryManager;
    }
}

