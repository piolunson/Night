/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.item.Items
 */
package night.modules.impl.combat;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import night.Night;
import night.events.SubscribeEvent;
import night.events.impl.PlayerPopEvent;
import night.events.impl.PlayerUpdateEvent;
import night.mixins.accessors.ClientPlayerInteractionManagerAccessor;
import night.modules.Module;
import night.modules.RegisterModule;
import night.modules.impl.combat.SuicideModule;
import night.modules.impl.miscellaneous.FakePlayerModule;
import night.pingbypass.PingBypassFlags;
import night.settings.impl.BooleanSetting;
import night.settings.impl.NumberSetting;
import night.utils.minecraft.EntityUtils;
import night.utils.minecraft.InventoryUtils;

@RegisterModule(name="Mainhand", description="Automatically manages totems and golden apples in your mainhand.", category=Module.Category.COMBAT)
public class MainhandModule
extends Module {
    public NumberSetting slot = new NumberSetting("Slot", "The hotbar slot (1-9) to keep a totem in.", 1, 1, 9);
    public NumberSetting delay = new NumberSetting("Delay", "Delay (ms) before refilling a totem to hotbar.", 0, 0, 2000);
    public BooleanSetting useGapple = new BooleanSetting("UseGapple", "Switches to golden apple when right clicking while holding a totem in mainhand.", true);
    public BooleanSetting lethalOverride = new BooleanSetting("LethalOverride", "Allows eating a golden apple regardless of health when no enemies are in visual range.", new BooleanSetting.Visibility(this.useGapple, true), false);
    public NumberSetting health = new NumberSetting("Health", "The health at which a totem will be prioritized.", 16, 0, 36);
    public BooleanSetting elytraCheck = new BooleanSetting("ElytraCheck", "Prioritizes a totem whenever you're wearing an elytra.", true);
    public NumberSetting fallDistance = new NumberSetting("FallDistance", "The fall distance at which the module will prioritize a totem.", Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(80.0f));
    public BooleanSetting antiMace = new BooleanSetting("AntiMace", "Switches to a totem if a player near you is trying to smash attack you with a mace.", false);
    public NumberSetting maceRange = new NumberSetting("MaceRange", "The distance at which an enemy has to be in with a mace in order to swap to a totem.", new BooleanSetting.Visibility(this.antiMace, true), Float.valueOf(12.0f), Float.valueOf(0.0f), Float.valueOf(24.0f));
    private long pendingRefillTime = 0L;
    private boolean eatingHotbar = false;
    private int previousHotbarSlot = -1;
    private boolean eatingInventory = false;
    private int swappedInvSlot = -1;
    private int swappedHotbarSlot = -1;
    public static volatile boolean hidingLegitInventory = false;

    @Override
    public void onEnable() {
        this.pendingRefillTime = 0L;
    }

    @SubscribeEvent(priority=0x7FFFFFFF)
    public void onPlayerPop(PlayerPopEvent event) {
        if (PingBypassFlags.isPingBypassActive()) {
            return;
        }
        if (event.getPlayer() != MainhandModule.mc.player || Night.MODULE_MANAGER.getModule(SuicideModule.class).isToggled()) {
            return;
        }
        if (!this.isToggled()) {
            return;
        }
        mc.execute(() -> {
            if (MainhandModule.mc.player == null || MainhandModule.mc.level == null) {
                return;
            }
            int targetSlot = this.slot.getValue().intValue() - 1;
            long d = this.delay.getValue().longValue();
            if (d <= 0L) {
                this.refill(targetSlot);
            } else {
                this.pendingRefillTime = System.currentTimeMillis() + d;
            }
        });
    }

    private void refill(int targetHotbarSlot) {
        if (MainhandModule.mc.player == null || MainhandModule.mc.level == null) {
            return;
        }
        if (PingBypassFlags.isPingBypassActive()) {
            return;
        }
        Screen currentScreen = MainhandModule.mc.gui.screen();
        if (currentScreen instanceof AbstractContainerScreen && !(currentScreen instanceof InventoryScreen) && !MainhandModule.mc.player.containerMenu.getCarried().isEmpty()) {
            return;
        }
        if (MainhandModule.mc.player.getInventory().getItem(targetHotbarSlot).getItem() == Items.TOTEM_OF_UNDYING) {
            return;
        }
        int totemSlot = this.findRefillTotem(targetHotbarSlot);
        if (totemSlot != -1) {
            InventoryUtils.swap("Swap", totemSlot, targetHotbarSlot);
        }
    }

    public boolean legitOwnsTotems() {
        return this.isToggled();
    }

    @SubscribeEvent(priority=0x7FFFFFFF)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        this.updateMainhand();
    }

    private void updateMainhand() {
        boolean canGapple;
        boolean isUsing;
        boolean isContainerScreen;
        if (MainhandModule.mc.player == null || MainhandModule.mc.level == null) {
            return;
        }
        if (PingBypassFlags.isPingBypassActive()) {
            return;
        }
        Screen currentScreen = MainhandModule.mc.gui.screen();
        boolean isContainerScreen_2 = currentScreen instanceof AbstractContainerScreen && !(currentScreen instanceof InventoryScreen);
        if (isContainerScreen_2 && !MainhandModule.mc.player.containerMenu.getCarried().isEmpty()) {
            return;
        }
        boolean isUsing_2 = MainhandModule.mc.options.keyUse.isDown() || MainhandModule.mc.player.isUsingItem();
        if (this.eatingHotbar) {
            if (!isUsing_2) {
                if (this.previousHotbarSlot != -1) {
                    MainhandModule.mc.player.getInventory().setSelectedSlot(this.previousHotbarSlot);
                    ((ClientPlayerInteractionManagerAccessor)(Object)MainhandModule.mc.gameMode).invokeSyncSelectedSlot();
                }
                this.eatingHotbar = false;
                this.previousHotbarSlot = -1;
            }
            return;
        }
        if (this.eatingInventory) {
            if (!isUsing_2) {
                if (this.swappedInvSlot != -1 && this.swappedHotbarSlot != -1) {
                    InventoryUtils.swap("Swap", this.swappedInvSlot, this.swappedHotbarSlot);
                }
                this.eatingInventory = false;
                this.swappedInvSlot = -1;
                this.swappedHotbarSlot = -1;
            }
            return;
        }
        boolean canGapple_2 = !this.needsTotem() || this.lethalOverride.getValue() && !this.hasEnemyInVisualRange();
        if (this.useGapple.getValue() && MainhandModule.mc.options.keyUse.isDown() && MainhandModule.mc.player.getMainHandItem().is(Items.TOTEM_OF_UNDYING) && canGapple_2) {
            int hotbarGapple = this.findGappleHotbar();
            if (hotbarGapple != -1) {
                this.previousHotbarSlot = MainhandModule.mc.player.getInventory().getSelectedSlot();
                MainhandModule.mc.player.getInventory().setSelectedSlot(hotbarGapple);
                ((ClientPlayerInteractionManagerAccessor)(Object)MainhandModule.mc.gameMode).invokeSyncSelectedSlot();
                this.eatingHotbar = true;
                return;
            }
            int invGapple = this.findGappleInventory();
            if (invGapple != -1) {
                this.swappedInvSlot = invGapple;
                this.swappedHotbarSlot = MainhandModule.mc.player.getInventory().getSelectedSlot();
                InventoryUtils.swap("Swap", invGapple, this.swappedHotbarSlot);
                this.eatingInventory = true;
                return;
            }
        }
        int targetSlot = this.slot.getValue().intValue() - 1;
        long now = System.currentTimeMillis();
        if (this.pendingRefillTime > 0L) {
            if (now >= this.pendingRefillTime) {
                this.pendingRefillTime = 0L;
                this.refill(targetSlot);
            }
        } else {
            boolean needRefill;
            boolean needRefill_2 = MainhandModule.mc.player.getInventory().getItem(targetSlot).getItem() != Items.TOTEM_OF_UNDYING;
            if (needRefill_2) {
                long d = this.delay.getValue().longValue();
                if (d <= 0L) {
                    this.refill(targetSlot);
                } else {
                    this.pendingRefillTime = now + d;
                }
            }
        }
        if (this.needsTotem()) {
            boolean slotHasTotem;
            int currentSelected = MainhandModule.mc.player.getInventory().getSelectedSlot();
            boolean slotHasTotem_2 = MainhandModule.mc.player.getInventory().getItem(targetSlot).getItem() == Items.TOTEM_OF_UNDYING;
            if (currentSelected != targetSlot || !slotHasTotem_2) {
                if (slotHasTotem_2) {
                    MainhandModule.mc.player.getInventory().setSelectedSlot(targetSlot);
                    ((ClientPlayerInteractionManagerAccessor)(Object)MainhandModule.mc.gameMode).invokeSyncSelectedSlot();
                } else {
                    int anyHotbarTotem = InventoryUtils.find(Items.TOTEM_OF_UNDYING, InventoryUtils.HOTBAR_START, InventoryUtils.HOTBAR_END);
                    if (anyHotbarTotem != -1 && currentSelected != anyHotbarTotem) {
                        MainhandModule.mc.player.getInventory().setSelectedSlot(anyHotbarTotem);
                        ((ClientPlayerInteractionManagerAccessor)(Object)MainhandModule.mc.gameMode).invokeSyncSelectedSlot();
                    }
                }
            }
        }
    }

    private int findRefillTotem(int target) {
        int invTotem = InventoryUtils.findInventory(Items.TOTEM_OF_UNDYING);
        if (invTotem != -1) {
            return invTotem;
        }
        for (int i = InventoryUtils.HOTBAR_START; i <= InventoryUtils.HOTBAR_END; ++i) {
            if (i == target || MainhandModule.mc.player.getInventory().getItem(i).getItem() != Items.TOTEM_OF_UNDYING) continue;
            return i;
        }
        return -1;
    }

    private int findGappleHotbar() {
        int egap = InventoryUtils.find(Items.ENCHANTED_GOLDEN_APPLE, InventoryUtils.HOTBAR_START, InventoryUtils.HOTBAR_END);
        if (egap != -1) {
            return egap;
        }
        return InventoryUtils.find(Items.GOLDEN_APPLE, InventoryUtils.HOTBAR_START, InventoryUtils.HOTBAR_END);
    }

    private int findGappleInventory() {
        int egap = InventoryUtils.find(Items.ENCHANTED_GOLDEN_APPLE, InventoryUtils.INVENTORY_START, InventoryUtils.INVENTORY_END);
        if (egap != -1) {
            return egap;
        }
        return InventoryUtils.find(Items.GOLDEN_APPLE, InventoryUtils.INVENTORY_START, InventoryUtils.INVENTORY_END);
    }

    public boolean shouldLockSlot() {
        if (!this.isToggled()) {
            return false;
        }
        if (MainhandModule.mc.player == null) {
            return false;
        }
        if (this.eatingHotbar || this.eatingInventory) {
            return false;
        }
        if (InventoryUtils.find(Items.TOTEM_OF_UNDYING) == -1) {
            return false;
        }
        return this.needsTotem();
    }

    private boolean needsTotem() {
        if (Night.MODULE_MANAGER.getModule(SuicideModule.class).isToggled() && Night.MODULE_MANAGER.getModule(SuicideModule.class).offhandOverride.getValue()) {
            return false;
        }
        if (MainhandModule.mc.player.getHealth() + MainhandModule.mc.player.getAbsorptionAmount() <= this.health.getValue().floatValue()) {
            return true;
        }
        if (MainhandModule.mc.player.fallDistance > (double)this.fallDistance.getValue().floatValue()) {
            return true;
        }
        if (this.elytraCheck.getValue() && MainhandModule.mc.player.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
            return true;
        }
        return this.antiMace.getValue() && MainhandModule.mc.level.players().stream().anyMatch(entity -> entity != MainhandModule.mc.player && !Night.FRIEND_MANAGER.contains(entity.getName().getString()) && MainhandModule.mc.player.distanceToSqr((Entity)entity) <= (double)Mth.square((float)this.maceRange.getValue().floatValue()) && entity.fallDistance >= 1.5 && entity.getMainHandItem().getItem().equals(Items.MACE));
    }

    private boolean hasEnemyInVisualRange() {
        if (MainhandModule.mc.level == null || MainhandModule.mc.player == null) {
            return false;
        }
        FakePlayerModule fakePlayer = Night.MODULE_MANAGER != null ? Night.MODULE_MANAGER.getModule(FakePlayerModule.class) : null;
        return MainhandModule.mc.level.players().stream().anyMatch(player -> player != MainhandModule.mc.player && !player.isDeadOrDying() && !EntityUtils.isGhost((Entity)player) && (fakePlayer == null || !fakePlayer.isToggled() || player != fakePlayer.getPlayer()) && !Night.FRIEND_MANAGER.contains(player.getName().getString()));
    }

    @Override
    public void onDisable() {
        if (this.eatingHotbar && MainhandModule.mc.player != null && this.previousHotbarSlot != -1) {
            MainhandModule.mc.player.getInventory().setSelectedSlot(this.previousHotbarSlot);
            ((ClientPlayerInteractionManagerAccessor)(Object)MainhandModule.mc.gameMode).invokeSyncSelectedSlot();
        }
        if (this.eatingInventory && MainhandModule.mc.player != null && this.swappedInvSlot != -1 && this.swappedHotbarSlot != -1) {
            InventoryUtils.swap("Swap", this.swappedInvSlot, this.swappedHotbarSlot);
        }
        this.eatingHotbar = false;
        this.previousHotbarSlot = -1;
        this.eatingInventory = false;
        this.swappedInvSlot = -1;
        this.swappedHotbarSlot = -1;
        this.pendingRefillTime = 0L;
    }

    @Override
    public String getMetaData() {
        return "Slot " + this.slot.getValue().intValue();
    }
}

