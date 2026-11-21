/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundAddEntityPacket
 *  net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket
 *  net.minecraft.network.protocol.game.ServerboundAttackPacket
 *  net.minecraft.network.protocol.game.ServerboundContainerClosePacket
 *  net.minecraft.network.protocol.game.ServerboundSwingPacket
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityTypes
 *  net.minecraft.world.entity.boss.enderdragon.EndCrystal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package night.modules.impl.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundSwingPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import night.Night;
import night.events.SubscribeEvent;
import night.events.impl.ClientDisconnectEvent;
import night.events.impl.PacketReceiveEvent;
import night.events.impl.PacketSendEvent;
import night.events.impl.PlayerJumpEvent;
import night.events.impl.PlayerMineEvent;
import night.events.impl.PlayerUpdateEvent;
import night.modules.Module;
import night.modules.RegisterModule;
import night.modules.impl.movement.HitboxDesyncModule;
import night.modules.impl.movement.SpeedModule;
import night.modules.impl.movement.StepModule;
import night.modules.impl.movement.TickShiftModule;
import night.modules.impl.player.AutoShopModule;
import night.modules.impl.player.KeyActionModule;
import night.settings.impl.BooleanSetting;
import night.settings.impl.ModeSetting;
import night.settings.impl.NumberSetting;
import night.utils.minecraft.EntityUtils;
import night.utils.minecraft.HoleUtils;
import night.utils.minecraft.InventoryUtils;
import night.utils.minecraft.PositionUtils;
import night.utils.minecraft.WorldUtils;
import night.utils.rotations.RotationUtils;

@RegisterModule(name="Surround", description="Automatically places blocks at your feet to prevent crystal damage.", category=Module.Category.COMBAT)
public class SurroundModule
extends Module {
    public ModeSetting autoSwitch = new ModeSetting("Switch", "The mode that will be used for automatically switching to necessary items.", "Silent", InventoryUtils.SWITCH_MODES);
    public ModeSetting timing = new ModeSetting("Timing", "The timing that will be used in replacing broken surround blocks.", "Sequential", new String[]{"Vanilla", "Sequential"});
    public NumberSetting bpt = new NumberSetting("BPT", "The maximum number of blocks to place per tick.", 4, 1, 8);
    public NumberSetting range = new NumberSetting("Range", "The maximum range at which the blocks will be placed at.", 5.0, 0.0, 12.0);
    public BooleanSetting await = new BooleanSetting("Await", "Waits for blocks to be registered by the client before placing on them.", false);
    public ModeSetting rotate = new ModeSetting("Rotate", "The rotation mode when placing surround blocks.", "Grim", new String[]{"None", "Normal", "Grim"});
    public BooleanSetting strictDirection = new BooleanSetting("StrictDirection", "Only places using directions that face you.", false);
    public BooleanSetting airPlace = new BooleanSetting("AirPlace", "Places blocks in the air without needing neighboring blocks.", false);
    public BooleanSetting attack = new BooleanSetting("Attack", "Attacks any crystal that interferes with your surround.", false);
    public BooleanSetting attackRotate = new BooleanSetting("AttackRotate", "Rotate", "Rotates toward the crystal before attacking it.", new BooleanSetting.Visibility(this.attack, true), true);
    public NumberSetting attackRange = new NumberSetting("AttackRange", "Range", "The maximum range at which crystals will be attacked.", new BooleanSetting.Visibility(this.attack, true), Float.valueOf(3.0f), Float.valueOf(1.0f), Float.valueOf(6.0f));
    public NumberSetting attackAge = new NumberSetting("AttackAge", "Age", "The minimum age (in ticks) a crystal must reach before being attacked.", new BooleanSetting.Visibility(this.attack, true), 5, 0, 20);
    public BooleanSetting attackMultiTask = new BooleanSetting("AttackMultiTask", "Multitask", "Allows attacking while an item is already in use.", new BooleanSetting.Visibility(this.attack, true), true);
    public BooleanSetting attackSwing = new BooleanSetting("AttackSwing", "Swing", "Sends a swing packet whenever attacking a crystal.", new BooleanSetting.Visibility(this.attack, true), true);
    public BooleanSetting center = new BooleanSetting("Center", "Puts you in the center of the block when you surround.", false);
    public BooleanSetting floor = new BooleanSetting("Floor", "Places blocks under your feet as well.", true);
    public BooleanSetting underFeet = new BooleanSetting("UnderFeet", "Replaces the block directly under your feet when it breaks.", new BooleanSetting.Visibility(this.floor, true), true);
    public BooleanSetting extension = new BooleanSetting("Extension", "Extends the surround if there are entities obstructing block placement.", true);
    public BooleanSetting blocker = new BooleanSetting("Blocker", "Pre-places extra blocks where an enemy is digging toward you.", true);
    public BooleanSetting blockerUp = new BooleanSetting("Up", "Blocker: places above/below the edge being mined through.", new BooleanSetting.Visibility(this.blocker, true), true);
    public BooleanSetting blockerCorner = new BooleanSetting("Corner", "Blocker: places the 2 corner cells left/right of the edge being mined through.", new BooleanSetting.Visibility(this.blocker, true), true);
    public BooleanSetting blockerEdge = new BooleanSetting("Edge", "Blocker: places the cell straight out in front of the edge being mined through.", new BooleanSetting.Visibility(this.blocker, true), true);
    public BooleanSetting advoidHelp = new BooleanSetting("AdvoidHelp", "Skips placements that would also wall in a nearby enemy.", true);
    public NumberSetting advoidHelpHealth = new NumberSetting("AdvoidHelpHealth", "Below this health, place every block regardless -- ignores AdvoidHelp entirely.", new BooleanSetting.Visibility(this.advoidHelp, true), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(20.0f));
    public BooleanSetting whileEating = new BooleanSetting("WhileEating", "Places blocks normally while eating.", true);
    public BooleanSetting advanced = new BooleanSetting("Advanced", "While WhileEating is off, keeps placing through an eat pause as long as your surround still has an open gap (stops once sealed, or if you're stuck inside a block).", new BooleanSetting.Visibility(this.whileEating, false), false);
    public BooleanSetting chorusCenter = new BooleanSetting("CenterOnTP", "Centers you if you have just teleported to surround against crystals easier.", true);
    public BooleanSetting predict = new BooleanSetting("Predict", "Replaces a surround block instantly the moment we see the server's own break packet for it, instead of waiting for the next normal placement cycle.", true);
    private int packetsSent = 0;
    private boolean isWorking = false;
    public BooleanSetting selfDisable = new BooleanSetting("SelfDisable", "Toggles off the module once it is finished with placing.", false);
    public BooleanSetting itemDisable = new BooleanSetting("ItemDisable", "Toggles off the module when no valid items are found in your hotbar/inventory.", true);
    public BooleanSetting jumpDisable = new BooleanSetting("JumpDisable", "Toggles off the module whenever your Y level changes.", true);
    public BooleanSetting pearlDisable = new BooleanSetting("PearlDisable", "Toggles off surround whenever you throw or land an ender pearl.", false);
    public BooleanSetting chorusDisable = new BooleanSetting("ChorusDisable", "Toggles off surround whenever you eat a chorus fruit.", false);
    public BooleanSetting rubberbandDisable = new BooleanSetting("RubberbandDisable", "Toggles off surround whenever you get rubberbanded/setback by the server.", false);
    public BooleanSetting stepToggle = new BooleanSetting("StepToggle", "Toggles the step module when you surround.", false);
    public BooleanSetting speedToggle = new BooleanSetting("SpeedToggle", "Toggles the speed module when you surround.", false);
    public BooleanSetting tickShiftToggle = new BooleanSetting("TickShiftToggle", "Toggles the TickShift module when you surround.", false);
    public BooleanSetting render = new BooleanSetting("Render", "Whether or not to render the place position.", true);
    private Set<BlockPos> targetPositions = new HashSet<BlockPos>();
    private BlockPos lastPosition = null;
    private int blocksPlaced = 0;
    private boolean awaitChorusCenter = false;
    private final Map<Integer, MineData> activeMines = new HashMap<Integer, MineData>();
    private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    private long lastPlacementTime = 0L;

    @Override
    public String getMetaData() {
        return "Packet: " + this.packetsSent;
    }

    @Override
    public void onEnable() {
        if (SurroundModule.mc.player == null || SurroundModule.mc.level == null) {
            return;
        }
        this.packetsSent = 0;
        this.lastPosition = PositionUtils.getFlooredPosition((Entity)SurroundModule.mc.player);
        this.awaitChorusCenter = false;
        this.activeMines.clear();
        if (this.stepToggle.getValue() && Night.MODULE_MANAGER.getModule(StepModule.class).isToggled()) {
            Night.MODULE_MANAGER.getModule(StepModule.class).setToggled(false);
        }
        if (this.speedToggle.getValue() && Night.MODULE_MANAGER.getModule(SpeedModule.class).isToggled()) {
            Night.MODULE_MANAGER.getModule(SpeedModule.class).setToggled(false);
        }
        if (this.tickShiftToggle.getValue() && Night.MODULE_MANAGER.getModule(TickShiftModule.class).isToggled()) {
            Night.MODULE_MANAGER.getModule(TickShiftModule.class).setToggled(false);
        }
        if (this.center.getValue()) {
            SurroundModule.mc.player.setPos((double)this.lastPosition.getX() + 0.5, (double)this.lastPosition.getY(), (double)this.lastPosition.getZ() + 0.5);
        }
        if (this.rotate.getValue().equalsIgnoreCase("Grim")) {
            Night.WORLD_MANAGER.setTimerMultiplier(1.08f);
        }
    }

    @Override
    public void onDisable() {
        Night.WORLD_MANAGER.setTimerMultiplier(1.0f);
        this.targetPositions.clear();
        this.lastPosition = null;
        this.isWorking = false;
        this.awaitChorusCenter = false;
        this.activeMines.clear();
        this.blocksPlaced = 0;
    }

    @SubscribeEvent
    public void onPlayerMine(PlayerMineEvent event) {
        if (!this.blocker.getValue() || SurroundModule.mc.player == null) {
            return;
        }
        if (event.getActorID() == SurroundModule.mc.player.getId()) {
            return;
        }
        boolean isSurround = false;
        for (BlockPos feet : this.ownFootCells()) {
            if (event.getPosition().getY() != feet.getY()) continue;
            int dx = event.getPosition().getX() - feet.getX();
            int dz = event.getPosition().getZ() - feet.getZ();
            if (Math.abs(dx) + Math.abs(dz) != 1) continue;
            isSurround = true;
            break;
        }
        if (!isSurround) {
            return;
        }
        MineData data = this.activeMines.get(event.getActorID());
        if (data == null || !data.pos.equals(event.getPosition())) {
            this.activeMines.put(event.getActorID(), new MineData(event.getPosition()));
        } else {
            data.lastSeenTime = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public void onPlayerJump(PlayerJumpEvent event) {
        if (this.jumpDisable.getValue()) {
            this.setToggled(false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        KeyActionModule keyAction;
        BlockItem blockItem;
        Item item;
        if (SurroundModule.mc.player == null || SurroundModule.mc.level == null) {
            return;
        }
        if (this.awaitChorusCenter) {
            BlockPos currentPos = PositionUtils.getFlooredPosition((Entity)SurroundModule.mc.player);
            SurroundModule.mc.player.setPos((double)currentPos.getX() + 0.5, SurroundModule.mc.player.getY(), (double)currentPos.getZ() + 0.5);
            this.lastPosition = currentPos;
            this.awaitChorusCenter = false;
        }
        if (this.jumpDisable.getValue() && (SurroundModule.mc.player.fallDistance > 2.0 || (Night.MODULE_MANAGER.getModule(StepModule.class).isToggled() || Night.MODULE_MANAGER.getModule(SpeedModule.class).isToggled()) && (this.lastPosition == null || this.lastPosition.getY() != PositionUtils.getFlooredPosition((Entity)SurroundModule.mc.player).getY()))) {
            this.setToggled(false);
            return;
        }
        if (!this.whileEating.getValue() && EntityUtils.isEating() && this.shouldPauseForEating()) {
            return;
        }
        this.blocksPlaced = 0;
        if (!(!this.autoSwitch.getValue().equalsIgnoreCase("None") || (item = SurroundModule.mc.player.getMainHandItem().getItem()) instanceof BlockItem && this.isBlastProof((blockItem = (BlockItem)item).getBlock()))) {
            if (this.itemDisable.getValue()) {
                Night.CHAT_MANAGER.tagged("You are currently not holding any blast-proof blocks.", this.getName());
                this.setToggled(false);
            }
            this.targetPositions.clear();
            return;
        }
        int slot = this.findBlastProofBlock(0, this.autoSwitch.getValue().equalsIgnoreCase("AltSwap") || this.autoSwitch.getValue().equalsIgnoreCase("AltPickup") ? 35 : 8);
        int previousSlot = SurroundModule.mc.player.getInventory().getSelectedSlot();
        if (slot == -1) {
            if (this.itemDisable.getValue()) {
                Night.CHAT_MANAGER.tagged("No blast-proof blocks could be found in your inventory.", this.getName());
                this.setToggled(false);
            }
            this.targetPositions.clear();
            return;
        }
        this.targetPositions = HoleUtils.getFeetPositions((Player)SurroundModule.mc.player, this.extension.getValue(), this.floor.getValue(), this.underFeet.getValue(), false);
        if (this.blocker.getValue()) {
            this.applyBlocker();
        }
        if (this.attack.getValue()) {
            this.attackTrapCrystals();
        }
        Set<BlockPos> helpBlockedPositions = this.advoidHelp.getValue() ? this.computeHelpBlocked() : Collections.emptySet();
        this.targetPositions.stream().filter(position -> SurroundModule.mc.player.distanceToSqr(Vec3.atCenterOf((Vec3i)position)) <= Mth.square((double)this.range.getValue().doubleValue())).filter(position -> !WorldUtils.isPlaceable(position) && WorldUtils.isInstantBreakable(position)).forEach(position -> WorldUtils.instantBreak(position, Direction.UP));
        HitboxDesyncModule module = Night.MODULE_MANAGER.getModule(HitboxDesyncModule.class);
        ArrayList<BlockPos> positions = new ArrayList<BlockPos>(this.targetPositions.stream().filter(position -> SurroundModule.mc.player.distanceToSqr(Vec3.atCenterOf((Vec3i)position)) <= Mth.square((double)this.range.getValue().doubleValue())).filter(position -> WorldUtils.isPlaceable(position, module != null && module.isToggled() && !module.close.getValue())).filter(position -> !helpBlockedPositions.contains(position)).toList());
        Vec3 predicted = SurroundModule.mc.player.position().add(SurroundModule.mc.player.getDeltaMovement().scale(0.5));
        positions.sort((a, b) -> {
            boolean bHasNeighbor;
            int yCmp = Integer.compare(a.getY(), b.getY());
            if (yCmp != 0) {
                return yCmp;
            }
            boolean aHasNeighbor = WorldUtils.getDirection(a, false) != null;
            boolean bHasNeighbor_2 = WorldUtils.getDirection(b, false) != null;
            if (aHasNeighbor != bHasNeighbor_2) {
                return aHasNeighbor ? -1 : 1;
            }
            return Double.compare(Vec3.atCenterOf((Vec3i)a).distanceToSqr(predicted), Vec3.atCenterOf((Vec3i)b).distanceToSqr(predicted));
        });
        KeyActionModule keyActionModule = keyAction = Night.MODULE_MANAGER != null ? Night.MODULE_MANAGER.getModule(KeyActionModule.class) : null;
        if (this.pearlDisable.getValue() && (keyAction != null && keyAction.isPearlActive() || SurroundModule.mc.player.isUsingItem() && SurroundModule.mc.player.getUseItem().getItem() == Items.ENDER_PEARL)) {
            this.setToggled(false);
            return;
        }
        if (this.chorusDisable.getValue() && SurroundModule.mc.player.isUsingItem() && SurroundModule.mc.player.getUseItem().getItem() == Items.CHORUS_FRUIT) {
            this.setToggled(false);
            return;
        }
        if (keyAction != null && keyAction.isPearlActive()) {
            return;
        }
        if (positions.isEmpty()) {
            if (this.selfDisable.getValue()) {
                this.setToggled(false);
            }
            return;
        }
        this.isWorking = true;
        boolean isGrim = this.rotate.getValue().equalsIgnoreCase("Grim");
        if (isGrim) {
            Night.ROTATION_MANAGER.beginBatchRotation();
        }
        try {
            InventoryUtils.switchSlot(this.autoSwitch.getValue(), slot, previousSlot);
            ArrayList<BlockPos> placedPositions = new ArrayList<BlockPos>();
            for (BlockPos position2 : positions) {
                Direction supportDir;
                Direction supportDir2;
                List<Direction> directions;
                if (this.blocksPlaced >= this.bpt.getValue().intValue()) break;
                if (this.attack.getValue()) {
                    for (EndCrystal crystal : SurroundModule.mc.level.getEntitiesOfClass(EndCrystal.class, new AABB(position2).inflate(0.5))) {
                        if (!crystal.isAlive()) continue;
                        mc.getConnection().send((Packet)new ServerboundAttackPacket(crystal.getId()));
                        if (!this.attackSwing.getValue()) continue;
                        mc.getConnection().send((Packet)new ServerboundSwingPacket(InteractionHand.MAIN_HAND));
                    }
                }
                if ((directions = WorldUtils.getDirections(position2, placedPositions, this.strictDirection.getValue())).isEmpty()) {
                    if (this.airPlace.getValue() && !this.strictDirection.getValue()) {
                        this.placeAirBlock(position2);
                        placedPositions.add(position2);
                        this.lastPlacementTime = System.currentTimeMillis();
                        ++this.blocksPlaced;
                        continue;
                    }
                    BlockPos support = WorldUtils.findSupportBlock(position2, placedPositions, this.strictDirection.getValue(), 2);
                    if (support == null || (supportDir2 = WorldUtils.getDirection(support, placedPositions, this.strictDirection.getValue())) == null || !WorldUtils.placeBlock(support, supportDir2, InteractionHand.MAIN_HAND, this.rotate.getValue(), true, this.render.getValue())) continue;
                    placedPositions.add(support);
                    this.lastPlacementTime = System.currentTimeMillis();
                    ++this.blocksPlaced;
                    continue;
                }
                boolean placed = false;
                java.util.Iterator<Direction> dirIter = directions.iterator();
                while (dirIter.hasNext()) {
                    Direction direction = dirIter.next();
                    if (!WorldUtils.placeBlock(position2, direction, InteractionHand.MAIN_HAND, this.rotate.getValue(), true, this.render.getValue())) continue;
                    placedPositions.add(position2);
                    this.lastPlacementTime = System.currentTimeMillis();
                    ++this.blocksPlaced;
                    placed = true;
                    break;
                }
                if (placed) continue;
                if (this.airPlace.getValue() && !this.strictDirection.getValue()) {
                    this.placeAirBlock(position2);
                    placedPositions.add(position2);
                    this.lastPlacementTime = System.currentTimeMillis();
                    ++this.blocksPlaced;
                    continue;
                }
                BlockPos support = WorldUtils.findSupportBlock(position2, placedPositions, this.strictDirection.getValue(), 2);
                if (support == null || (supportDir = WorldUtils.getDirection(support, placedPositions, this.strictDirection.getValue())) == null || !WorldUtils.placeBlock(support, supportDir, InteractionHand.MAIN_HAND, this.rotate.getValue(), true, this.render.getValue())) continue;
                placedPositions.add(support);
                this.lastPlacementTime = System.currentTimeMillis();
                ++this.blocksPlaced;
            }
            InventoryUtils.switchBack(this.autoSwitch.getValue(), slot, previousSlot);
        }
        finally {
            if (isGrim) {
                Night.ROTATION_MANAGER.endBatchRotation();
            }
            this.isWorking = false;
        }
    }

    private void placeAirBlock(BlockPos position) {
        WorldUtils.airPlaceBlock(position, InteractionHand.MAIN_HAND, this.rotate.getValue(), this.render.getValue());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @SubscribeEvent
    public void onPacketReceive(PacketReceiveEvent event) {
        ClientboundBlockUpdatePacket packet;
        Vec3 crystalPos;
        ClientboundAddEntityPacket addPacket;
        Vec3 currentPos;
        Packet<?> rawPacket;
        Object autoShop;
        if (SurroundModule.mc.player == null || SurroundModule.mc.level == null) {
            return;
        }
        Packet<?> packet2 = event.getPacket();
        if (packet2 instanceof ClientboundOpenScreenPacket) {
            ClientboundOpenScreenPacket openPacket = (ClientboundOpenScreenPacket)packet2;
            autoShop = Night.MODULE_MANAGER.getModule(AutoShopModule.class);
            if (autoShop != null && ((AutoShopModule)autoShop).isRunning()) {
                return;
            }
            if (this.isWorking || System.currentTimeMillis() - this.lastPlacementTime < 1000L || System.currentTimeMillis() - WorldUtils.lastInteractablePlaceTime < 1000L) {
                event.setCancelled(true);
                mc.getConnection().send((Packet)new ServerboundContainerClosePacket(openPacket.getContainerId()));
                return;
            }
        }
        if ((autoShop = event.getPacket()) instanceof ClientboundPlayerPositionPacket) {
            double dz;
            double dy;
            double dx;
            ClientboundPlayerPositionPacket posPacket = (ClientboundPlayerPositionPacket)autoShop;
            if (this.rubberbandDisable.getValue() && (currentPos = SurroundModule.mc.player.position()).distanceToSqr(dx = posPacket.change().position().x, dy = posPacket.change().position().y, dz = posPacket.change().position().z) >= 1.0) {
                this.setToggled(false);
                return;
            }
            if (this.chorusCenter.getValue() && SurroundModule.mc.player.isUsingItem() && SurroundModule.mc.player.getUseItem().getItem() == Items.CHORUS_FRUIT) {
                if (this.chorusDisable.getValue()) {
                    this.setToggled(false);
                    return;
                }
                this.awaitChorusCenter = true;
            }
        }
        if (this.attack.getValue() && (rawPacket = event.getPacket()) instanceof ClientboundAddEntityPacket && (addPacket = (ClientboundAddEntityPacket)rawPacket).getType() == EntityTypes.END_CRYSTAL) {
            crystalPos = new Vec3(addPacket.getX(), addPacket.getY(), addPacket.getZ());
            AABB crystalBox = new AABB(crystalPos.x - 1.0, crystalPos.y, crystalPos.z - 1.0, crystalPos.x + 1.0, crystalPos.y + 2.0, crystalPos.z + 1.0);
            boolean nearSurround = false;
            for (BlockPos pos : this.targetPositions) {
                if (!crystalBox.intersects(new AABB(pos)) && !(crystalPos.distanceToSqr(Vec3.atCenterOf((Vec3i)pos)) <= 4.0)) continue;
                nearSurround = true;
                break;
            }
            if (nearSurround && SurroundModule.mc.player.getEyePosition().distanceToSqr(crystalPos) <= Mth.square((double)this.attackRange.getValue().doubleValue()) && (this.attackMultiTask.getValue() || !SurroundModule.mc.player.isUsingItem())) {
                mc.getConnection().send((Packet)new ServerboundAttackPacket(addPacket.getId()));
                if (this.attackSwing.getValue()) {
                    mc.getConnection().send((Packet)new ServerboundSwingPacket(InteractionHand.MAIN_HAND));
                }
            }
        }
        if (!this.timing.getValue().equalsIgnoreCase("Sequential")) {
            return;
        }
        if (this.predict.getValue() && (rawPacket = event.getPacket()) instanceof ClientboundBlockUpdatePacket && (packet = (ClientboundBlockUpdatePacket)rawPacket).getBlockState().isAir() && this.targetPositions.contains(packet.getPos())) {
            boolean excludeSelf;
            if (this.blocksPlaced >= this.bpt.getValue().intValue()) {
                return;
            }
            if (!this.whileEating.getValue() && EntityUtils.isEating() && this.shouldPauseForEating()) {
                return;
            }
            HitboxDesyncModule module = Night.MODULE_MANAGER.getModule(HitboxDesyncModule.class);
            boolean excludeSelf_2 = module != null && module.isToggled() && !module.close.getValue();
            if (!WorldUtils.isPlaceable(packet.getPos(), excludeSelf_2)) {
                return;
            }
            int slot = this.findBlastProofBlock(0, this.autoSwitch.getValue().equalsIgnoreCase("AltSwap") || this.autoSwitch.getValue().equalsIgnoreCase("AltPickup") ? 35 : 8);
            int previousSlot = SurroundModule.mc.player.getInventory().getSelectedSlot();
            if (slot == -1) {
                return;
            }
            Direction direction = WorldUtils.getDirection(packet.getPos(), this.strictDirection.getValue());
            if (direction == null) {
                if (this.airPlace.getValue() && !this.strictDirection.getValue()) {
                    Night.ROTATION_MANAGER.beginBatchRotation();
                    try {
                        if (!InventoryUtils.switchSlot(this.autoSwitch.getValue(), slot, previousSlot)) {
                            return;
                        }
                        this.placeAirBlock(packet.getPos());
                        ++this.blocksPlaced;
                        this.lastPlacementTime = System.currentTimeMillis();
                        InventoryUtils.switchBack(this.autoSwitch.getValue(), slot, previousSlot);
                    }
                    finally {
                        Night.ROTATION_MANAGER.endBatchRotation();
                    }
                }
                return;
            }
            Night.ROTATION_MANAGER.beginBatchRotation();
            try {
                if (!InventoryUtils.switchSlot(this.autoSwitch.getValue(), slot, previousSlot)) {
                    return;
                }
                WorldUtils.placeBlock(packet.getPos(), direction, InteractionHand.MAIN_HAND, this.rotate.getValue(), true, this.render.getValue());
                ++this.blocksPlaced;
                this.lastPlacementTime = System.currentTimeMillis();
                InventoryUtils.switchBack(this.autoSwitch.getValue(), slot, previousSlot);
            }
            finally {
                Night.ROTATION_MANAGER.endBatchRotation();
            }
        }
    }

    private void attackTrapCrystals() {
        Night.ROTATION_MANAGER.beginBatchRotation();
        for (EndCrystal crystal : SurroundModule.mc.level.getEntitiesOfClass(EndCrystal.class, new AABB(SurroundModule.mc.player.blockPosition()).inflate(this.attackRange.getValue().doubleValue() + 3.0))) {
            if (!crystal.isAlive() || crystal.isRemoved() || crystal.tickCount < this.attackAge.getValue().intValue()) continue;
            AABB crystalBox = crystal.getBoundingBox();
            boolean threatens = false;
            for (BlockPos pos : this.targetPositions) {
                if (!SurroundModule.mc.level.getBlockState(pos).canBeReplaced() || !new AABB(pos).intersects(crystalBox)) continue;
                threatens = true;
                break;
            }
            if (!threatens || crystalBox.distanceToSqr(SurroundModule.mc.player.getEyePosition()) > this.attackRange.getValue().doubleValue() * this.attackRange.getValue().doubleValue() || !this.attackMultiTask.getValue() && SurroundModule.mc.player.isUsingItem()) continue;
            if (this.attackRotate.getValue()) {
                float[] rotations = RotationUtils.getRotations(crystal.getBoundingBox().getCenter());
                Night.ROTATION_MANAGER.silentRotate(rotations[0], rotations[1]);
                boolean insideBox = crystalBox.contains(SurroundModule.mc.player.getEyePosition());
                if (!insideBox && !WorldUtils.canSee(crystal.getBoundingBox().getCenter())) continue;
            }
            mc.getConnection().send((Packet)new ServerboundAttackPacket(crystal.getId()));
            if (!this.attackSwing.getValue()) continue;
            mc.getConnection().send((Packet)new ServerboundSwingPacket(InteractionHand.MAIN_HAND));
        }
        Night.ROTATION_MANAGER.endBatchRotation();
    }

    private void applyBlocker() {
        this.activeMines.entrySet().removeIf(e -> {
            MineData data = (MineData)e.getValue();
            return SurroundModule.mc.level.getEntity(((Integer)e.getKey()).intValue()) == null || SurroundModule.mc.level.getBlockState(data.pos).canBeReplaced() || System.currentTimeMillis() - data.lastSeenTime > 1000L;
        });
        for (MineData data : this.activeMines.values()) {
            ++data.ticks;
            if (data.ticks < 2 && System.currentTimeMillis() - data.startTime < 100L) continue;
            BlockPos mined = data.pos;
            for (BlockPos feet : this.ownFootCells()) {
                if (mined.getY() != feet.getY()) continue;
                int dx = mined.getX() - feet.getX();
                int dz = mined.getZ() - feet.getZ();
                if (Math.abs(dx) + Math.abs(dz) != 1) continue;
                if (this.blockerUp.getValue()) {
                    this.targetPositions.add(mined.above());
                    this.targetPositions.add(mined.below());
                }
                if (this.blockerCorner.getValue()) {
                    if (dx != 0) {
                        this.targetPositions.add(feet.offset(dx, 0, 1));
                        this.targetPositions.add(feet.offset(dx, 0, -1));
                    } else {
                        this.targetPositions.add(feet.offset(1, 0, dz));
                        this.targetPositions.add(feet.offset(-1, 0, dz));
                    }
                }
                if (!this.blockerEdge.getValue()) continue;
                if (dx != 0) {
                    this.targetPositions.add(feet.offset(dx * 2, 0, 0));
                    continue;
                }
                this.targetPositions.add(feet.offset(0, 0, dz * 2));
            }
        }
    }

    private Set<BlockPos> computeHelpBlocked() {
        Set<BlockPos> myFootCells = this.ownFootCells();
        if (SurroundModule.mc.player.getHealth() < this.advoidHelpHealth.getValue().floatValue()) {
            return Set.of();
        }
        HashSet<BlockPos> opponentRing = new HashSet<BlockPos>();
        for (Player player : SurroundModule.mc.level.players()) {
            if (player == SurroundModule.mc.player || Night.FRIEND_MANAGER.contains(player.getName().getString()) || SurroundModule.mc.player.distanceToSqr((Entity)player) > 25.0 || !SurroundModule.mc.level.getBlockState(player.blockPosition()).canBeReplaced()) continue;
            for (BlockPos cell : this.ownFootCells(player)) {
                for (Direction dir : HORIZONTALS) {
                    BlockPos adjacent = cell.relative(dir);
                    if (this.ownFootCells(player).contains(adjacent)) continue;
                    opponentRing.add(adjacent);
                }
                opponentRing.add(cell.below());
                opponentRing.add(cell);
            }
        }
        if (opponentRing.isEmpty()) {
            return Set.of();
        }
        HashSet<BlockPos> blocked = new HashSet<BlockPos>();
        for (BlockPos pos : opponentRing) {
            boolean nearMine = false;
            for (BlockPos cell : myFootCells) {
                if (!pos.equals(cell) && !pos.equals(cell.above()) && !pos.equals(cell.below()) && !pos.equals(cell.north()) && !pos.equals(cell.south()) && !pos.equals(cell.east()) && !pos.equals(cell.west())) continue;
                nearMine = true;
                break;
            }
            if (nearMine) continue;
            blocked.add(pos);
        }
        return blocked;
    }

    private Set<BlockPos> ownFootCells() {
        return this.ownFootCells((Player)SurroundModule.mc.player);
    }

    private Set<BlockPos> ownFootCells(Player player) {
        HashSet<BlockPos> cells = new HashSet<BlockPos>();
        AABB box = player.getBoundingBox();
        int y = player.blockPosition().getY();
        int x = (int)Math.floor(box.minX);
        while ((double)x < Math.ceil(box.maxX)) {
            int z = (int)Math.floor(box.minZ);
            while ((double)z < Math.ceil(box.maxZ)) {
                cells.add(new BlockPos(x, y, z));
                ++z;
            }
            ++x;
        }
        return cells;
    }

    @SubscribeEvent
    public void onClientDisconnect(ClientDisconnectEvent event) {
        this.setToggled(false);
    }

    private boolean shouldPauseForEating() {
        if (!this.advanced.getValue()) {
            return true;
        }
        if (EntityUtils.isPhased((Entity)SurroundModule.mc.player)) {
            return true;
        }
        HashSet<BlockPos> gapPositions = HoleUtils.getFeetPositions((Player)SurroundModule.mc.player, this.extension.getValue(), this.floor.getValue(), this.underFeet.getValue(), false);
        boolean canPlaceAny = gapPositions.stream().filter(WorldUtils::isPlaceable).anyMatch(pos -> WorldUtils.getDirection(pos, this.strictDirection.getValue()) != null || this.airPlace.getValue());
        return !canPlaceAny;
    }

    private boolean isBlastProof(Block block) {
        return block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.CRYING_OBSIDIAN || block == Blocks.NETHERITE_BLOCK || block == Blocks.RESPAWN_ANCHOR || block == Blocks.ANCIENT_DEBRIS || block == Blocks.ENCHANTING_TABLE || block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL;
    }

    private int findBlastProofBlock(int start, int end) {
        float bestHardness = -1.0f;
        int bestSlot = -1;
        for (int i = start; i <= end; ++i) {
            BlockItem item;
            Block block;
            Item item2 = SurroundModule.mc.player.getInventory().getItem(i).getItem();
            if (!(item2 instanceof BlockItem) || !this.isBlastProof(block = (item = (BlockItem)item2).getBlock())) continue;
            float hardness = block.defaultDestroyTime();
            if (hardness == -1.0f) {
                return i;
            }
            if (!(hardness > bestHardness)) continue;
            bestHardness = hardness;
            bestSlot = i;
        }
        return bestSlot;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent.Post event) {
        if (this.isWorking) {
            ++this.packetsSent;
        }
    }

    private static class MineData {
        final BlockPos pos;
        final long startTime;
        int ticks;
        long lastSeenTime;

        MineData(BlockPos pos) {
            this.pos = pos;
            this.startTime = System.currentTimeMillis();
            this.ticks = 0;
            this.lastSeenTime = System.currentTimeMillis();
        }
    }
}

