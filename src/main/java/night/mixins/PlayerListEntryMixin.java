/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.core.ClientAsset$ResourceTexture
 *  net.minecraft.core.ClientAsset$Texture
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.PlayerSkin
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package night.mixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import night.Night;
import night.modules.impl.core.CapesModule;
import night.utils.IMinecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerInfo.class})
public class PlayerListEntryMixin
implements IMinecraft {
    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method={"getSkin"}, at={@At(value="TAIL")}, cancellable=true)
    private void getSkinTextures(CallbackInfoReturnable<PlayerSkin> info) {
        if (Night.MODULE_MANAGER == null || this.profile == null || PlayerListEntryMixin.mc.player == null) {
            return;
        }
        CapesModule capes = Night.MODULE_MANAGER.getModule(CapesModule.class);
        if (capes == null || !capes.isToggled()) {
            return;
        }
        Identifier identifier = capes.getCapeTexture();
        if (identifier == null) {
            return;
        }
        boolean self = this.profile.id() != null && this.profile.id().equals(PlayerListEntryMixin.mc.player.getGameProfile().id());
        if (!self && !capes.others.getValue()) {
            return;
        }
        PlayerSkin skin = (PlayerSkin)info.getReturnValue();
        if (skin == null) {
            return;
        }
        ClientAsset.Texture cape = new ClientAsset.ResourceTexture(identifier);
        ClientAsset.Texture elytra = capes.elytra.getValue() ? cape : skin.elytra();
        info.setReturnValue(new PlayerSkin(skin.body(), cape, elytra, skin.model(), skin.secure()));
    }
}
