/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.world.inventory.ContainerInput
 */
package night.modules.impl.player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.inventory.ContainerInput;
import night.Night;
import night.events.SubscribeEvent;
import night.events.impl.PlayerUpdateEvent;
import night.modules.Module;
import night.modules.RegisterModule;
import night.settings.impl.BindSetting;
import night.settings.impl.NumberSetting;
import night.utils.minecraft.InventoryUtils;

@RegisterModule(name="AutoShop", description="Automates the server /shop GUI to buy totems, end crystals, or exp bottles.", category=Module.Category.PLAYER, persistent=true)
public class AutoShopModule
extends Module {
    public BindSetting totem = new BindSetting("Totem", "Keybind to auto buy totems.", 0).disableHoldModes();
    public NumberSetting totemAmount = new NumberSetting("TotemAmount", "How many totems to buy.", 1, 1, 10);
    public BindSetting crystal = new BindSetting("Crystal", "Keybind to auto buy end crystals.", 0).disableHoldModes();
    public NumberSetting crystalStacks = new NumberSetting("CrystalStacks", "How many stacks (64) of end crystals to buy.", 1, 1, 10);
    public BindSetting exp = new BindSetting("Exp", "Keybind to auto buy exp bottles.", 0).disableHoldModes();
    public NumberSetting expStacks = new NumberSetting("ExpStacks", "How many stacks (64) of exp bottles to buy.", 1, 1, 10);
    public BindSetting obsidian = new BindSetting("Obsidian", "Keybind to auto buy obsidian.", 0).disableHoldModes();
    public NumberSetting obsidianStacks = new NumberSetting("ObsidianStacks", "How many stacks (64) of obsidian to buy.", 1, 1, 10);
    public BindSetting enderPearl = new BindSetting("EnderPearl", "Keybind to auto buy ender pearls.", 0).disableHoldModes();
    public NumberSetting enderPearlStacks = new NumberSetting("EnderPearlStacks", "How many stacks (64) of ender pearls to buy.", 1, 1, 10);
    public NumberSetting actionDelay = new NumberSetting("ActionDelay", "Ticks between each shop GUI click.", 4, 0, 20);
    private static final int SLOT_ITEM_TOTEM = 13;
    private static final int SLOT_ITEM_CRYSTAL = 10;
    private static final int SLOT_ITEM_EXP = 16;
    private static final int SLOT_ITEM_OBSIDIAN = 9;
    private static final int SLOT_ITEM_ENDERPEARL = 14;
    private static final int SLOT_SET_1 = 15;
    private static final int SLOT_SET_11 = 16;
    private static final int SLOT_CONFIRM = 23;
    private static final int STEP_TIMEOUT_TICKS = 100;
    private State state = State.IDLE;
    private final Queue<PurchaseOrder> orderQueue = new ConcurrentLinkedQueue<PurchaseOrder>();
    private PurchaseOrder currentOrder = null;
    private int ticks = 0;
    private int timeoutTicks = 0;
    private boolean quantitySet = false;

    public AutoShopModule() {
        this.bind.disableHoldModes();
    }

    public void handleKey(int key) {
        if (key == 0) {
            return;
        }
        if (this.totem.getValue() != 0 && key == this.totem.getValue()) {
            this.enqueueOrder(ShopMode.Totem, this.totemAmount.getValue().intValue());
        } else if (this.crystal.getValue() != 0 && key == this.crystal.getValue()) {
            this.enqueueOrder(ShopMode.Crystal, this.crystalStacks.getValue().intValue());
        } else if (this.exp.getValue() != 0 && key == this.exp.getValue()) {
            this.enqueueOrder(ShopMode.Exp, this.expStacks.getValue().intValue());
        } else if (this.obsidian.getValue() != 0 && key == this.obsidian.getValue()) {
            this.enqueueOrder(ShopMode.Obsidian, this.obsidianStacks.getValue().intValue());
        } else if (this.enderPearl.getValue() != 0 && key == this.enderPearl.getValue()) {
            this.enqueueOrder(ShopMode.EnderPearl, this.enderPearlStacks.getValue().intValue());
        }
    }

    public synchronized void enqueueOrder(ShopMode shopMode, int amount) {
        if (AutoShopModule.mc.player == null || mc.getConnection() == null) {
            return;
        }
        if (amount <= 0) {
            return;
        }
        this.orderQueue.add(new PurchaseOrder(shopMode, amount));
        if (this.state == State.IDLE) {
            this.currentOrder = this.orderQueue.poll();
            this.ticks = 0;
            this.timeoutTicks = 0;
            this.quantitySet = false;
            this.sendShopCommand();
            this.state = State.WAIT_SHOP;
        }
    }

    private void sendShopCommand() {
        if (mc.getConnection() != null) {
            mc.getConnection().sendCommand("shop open gear");
        }
    }

    public boolean isRunning() {
        return this.state != State.IDLE;
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        boolean screenOpen;
        if (this.state == State.IDLE) {
            return;
        }
        if (AutoShopModule.mc.player == null || AutoShopModule.mc.level == null) {
            this.abort("Player left world");
            return;
        }
        if (this.ticks < this.actionDelay.getValue().intValue()) {
            ++this.ticks;
            return;
        }
        this.ticks = 0;
        Screen currentScreen = AutoShopModule.mc.gui.screen();
        boolean screenOpen_2 = currentScreen instanceof AbstractContainerScreen && !(currentScreen instanceof InventoryScreen);
        if (this.state != State.WAIT_SHOP && !screenOpen_2) {
            this.abort("Shop GUI closed unexpectedly.");
            return;
        }
        switch (this.state.ordinal()) {
            case 1: {
                if (screenOpen_2) {
                    this.state = State.CLICK_ITEM;
                    this.timeoutTicks = 0;
                    break;
                }
                ++this.timeoutTicks;
                if (this.timeoutTicks % 20 == 0) {
                    this.sendShopCommand();
                }
                if (this.timeoutTicks <= 100) break;
                this.abort("Shop GUI never opened.");
                break;
            }
            case 2: {
                if (this.currentOrder == null) {
                    this.state = State.DECIDE;
                    return;
                }
                int slot = switch (this.currentOrder.mode.ordinal()) {
                    default -> throw new MatchException(null, null);
                    case 0 -> 13;
                    case 1 -> 10;
                    case 2 -> 16;
                    case 3 -> 9;
                    case 4 -> 14;
                };
                this.click(slot);
                this.state = State.DECIDE;
                break;
            }
            case 3: {
                if (this.currentOrder == null || this.currentOrder.remaining <= 0) {
                    if (!this.orderQueue.isEmpty()) {
                        this.currentOrder = this.orderQueue.poll();
                        this.quantitySet = false;
                        this.sendShopCommand();
                        this.state = State.WAIT_SHOP;
                        return;
                    }
                    this.finish();
                    return;
                }
                if (this.currentOrder.mode == ShopMode.Totem) {
                    this.state = State.CONFIRM;
                    break;
                }
                if (!this.quantitySet) {
                    this.state = State.SET_11;
                    break;
                }
                this.state = State.CONFIRM;
                break;
            }
            case 4: {
                this.click(this.currentOrder != null && this.currentOrder.mode == ShopMode.EnderPearl ? 15 : 16);
                this.quantitySet = true;
                this.state = State.CONFIRM;
                break;
            }
            case 5: {
                this.click(23);
                if (this.currentOrder != null) {
                    --this.currentOrder.remaining;
                }
                this.state = State.DECIDE;
                break;
            }
        }
    }

    private void finish() {
        this.state = State.IDLE;
        this.orderQueue.clear();
        this.currentOrder = null;
        if (AutoShopModule.mc.player != null) {
            AutoShopModule.mc.player.closeContainer();
        }
        Night.CHAT_MANAGER.tagged("Done.", this.getName());
    }

    private void abort(String reason) {
        this.state = State.IDLE;
        this.orderQueue.clear();
        this.currentOrder = null;
        if (AutoShopModule.mc.player != null) {
            AutoShopModule.mc.player.closeContainer();
        }
        Night.CHAT_MANAGER.tagged("Aborted: " + reason, this.getName());
    }

    private void click(int slot) {
        InventoryUtils.click(slot, 0, ContainerInput.PICKUP);
    }

    @Override
    public String getMetaData() {
        if (this.currentOrder != null) {
            return this.currentOrder.mode.name() + " " + this.currentOrder.remaining + (String)(this.orderQueue.isEmpty() ? "" : " (+" + this.orderQueue.size() + ")");
        }
        return "";
    }

    private static enum State {
        IDLE,
        WAIT_SHOP,
        CLICK_ITEM,
        DECIDE,
        SET_11,
        CONFIRM;

    }

    public static class PurchaseOrder {
        public final ShopMode mode;
        public int remaining;

        public PurchaseOrder(ShopMode mode, int remaining) {
            this.mode = mode;
            this.remaining = remaining;
        }
    }

    public static enum ShopMode {
        Totem,
        Crystal,
        Exp,
        Obsidian,
        EnderPearl;

    }
}

