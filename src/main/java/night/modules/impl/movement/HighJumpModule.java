/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.Window
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity
 *  net.minecraft.world.phys.Vec3
 */
package night.modules.impl.movement;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.Vec3;
import night.events.SubscribeEvent;
import night.events.impl.PlayerUpdateEvent;
import night.modules.Module;
import night.modules.RegisterModule;
import night.settings.Setting;
import night.settings.impl.BooleanSetting;
import night.settings.impl.NumberSetting;

@RegisterModule(name="HighJump", description="Launches you upward when a shulker box next to you opens.", category=Module.Category.MOVEMENT)
public class HighJumpModule
extends Module {
    private static final double SPEED_PER_POINT = 1.5;
    private static final double NEAR_HORIZONTAL = 1.0;
    private static final double NEAR_VERTICAL = 2.0;
    private static final double FAST_VERTICAL = 30.0;
    private static final double APEX_DROP = 0.04;
    public NumberSetting strength = new NumberSetting("Strength", "Strength", "Launch power. 1 ~ 26 blocks, 3 ~ 104, 5 ~ 200. 0 = off.", new Setting.Visibility(), 5, 0, 5, 1);
    public BooleanSetting infinite = new BooleanSetting("Infinite", "Hold space when the jump peaks to launch again with the same power.", false);
    private boolean launched;
    private double prevY = Double.NaN;

    @Override
    public void onEnable() {
        this.launched = false;
        this.prevY = Double.NaN;
    }

    @SubscribeEvent
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.getNull()) {
            return;
        }
        double lastDy = HighJumpModule.mc.player.getY() - this.prevY;
        this.prevY = HighJumpModule.mc.player.getY();
        double speed = (double)this.strength.getValue().intValue() * 1.5;
        if (speed <= 0.0) {
            return;
        }
        if (HighJumpModule.mc.player.onGround()) {
            this.launched = false;
        }
        if (HighJumpModule.mc.player.fallDistance == 0.0 && this.openingShulkerNearby()) {
            this.setMotionY(speed);
            this.launched = true;
            return;
        }
        if (this.infinite.getValue() && this.launched && !HighJumpModule.mc.player.onGround() && lastDy < -0.04 && InputConstants.isKeyDown((Window)mc.getWindow(), (int)32)) {
            this.setMotionY(speed);
        }
    }

    private void setMotionY(double y) {
        Vec3 v = HighJumpModule.mc.player.getDeltaMovement();
        HighJumpModule.mc.player.setDeltaMovement(v.x, y, v.z);
    }

    private boolean openingShulkerNearby() {
        double maxDy = HighJumpModule.mc.player.getDeltaMovement().y > 1.0 ? 30.0 : 2.0;
        BlockPos base = HighJumpModule.mc.player.blockPosition();
        int reach = (int)Math.ceil(maxDy) + 1;
        for (BlockPos pos : BlockPos.betweenClosed((BlockPos)base.offset(-2, -reach, -2), (BlockPos)base.offset(2, 2, 2))) {
            double dz;
            double dx;
            ShulkerBoxBlockEntity box;
            float progress;
            BlockEntity blockEntity = HighJumpModule.mc.level.getBlockEntity(pos);
            if (!(blockEntity instanceof ShulkerBoxBlockEntity) || (progress = (box = (ShulkerBoxBlockEntity)blockEntity).getProgress(1.0f)) <= 0.0f || progress >= 1.0f || Math.sqrt((dx = HighJumpModule.mc.player.getX() - ((double)pos.getX() + 0.5)) * dx + (dz = HighJumpModule.mc.player.getZ() - ((double)pos.getZ() + 0.5)) * dz) > 1.0 || !(Math.abs(HighJumpModule.mc.player.getY() - ((double)pos.getY() + 0.5)) <= maxDy)) continue;
            return true;
        }
        return false;
    }

    @Override
    public String getMetaData() {
        return (this.infinite.getValue() ? "Infinite " : "") + this.strength.getValue().intValue();
    }
}

