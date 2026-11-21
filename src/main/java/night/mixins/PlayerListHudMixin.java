/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.components.PlayerTabOverlay
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.scores.PlayerTeam
 *  net.minecraft.world.scores.Team
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Constant
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.ModifyConstant
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package night.mixins;

import java.util.ArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import night.Night;
import night.modules.impl.miscellaneous.ExtraTabModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerTabOverlay.class})
public abstract class PlayerListHudMixin {
    @Shadow
    private Component decorateName(PlayerInfo entry, MutableComponent name) {
        throw new UnsupportedOperationException();
    }

    @ModifyConstant(method={"getPlayerInfos"}, constant={@Constant(longValue=80L)}, require=0)
    private long extraTabLimit(long original) {
        ExtraTabModule module = Night.MODULE_MANAGER.getModule(ExtraTabModule.class);
        return module.isToggled() ? module.limit.getValue().longValue() : original;
    }

    @Inject(method={"getNameForDisplay"}, at={@At(value="HEAD")}, cancellable=true)
    private void getPlayerName(PlayerInfo entry, CallbackInfoReturnable<Component> info) {
        if (Night.MODULE_MANAGER.getModule(ExtraTabModule.class).isToggled() && Night.MODULE_MANAGER.getModule(ExtraTabModule.class).friends.getValue() && Night.FRIEND_MANAGER.contains(entry.getProfile().name())) {
            if (entry.getTabListDisplayName() != null) {
                MutableComponent text = Component.empty();
                ArrayList<MutableComponent> parts = new ArrayList<MutableComponent>();
                parts.add(entry.getTabListDisplayName().plainCopy());
                for (Component sibling : entry.getTabListDisplayName().getSiblings()) {
                    parts.add(sibling.plainCopy());
                }
                for (Component component : parts) {
                    if (component.getString().equals(entry.getProfile().name())) {
                        text.append((Component)Component.literal((String)entry.getProfile().name()).withStyle(ChatFormatting.AQUA));
                        continue;
                    }
                    if (component.getString().equals("] " + entry.getProfile().name())) {
                        text.append((Component)Component.literal((String)"] ").withStyle(ChatFormatting.WHITE).append((Component)Component.literal((String)entry.getProfile().name()).withStyle(ChatFormatting.AQUA)));
                        continue;
                    }
                    text.append(component);
                }
                info.setReturnValue(this.decorateName(entry, text));
                return;
            }
            info.setReturnValue(this.decorateName(entry, PlayerTeam.formatNameForTeam((Team)entry.getTeam(), (Component)Component.literal((String)entry.getProfile().name()))));
        }
    }
}

