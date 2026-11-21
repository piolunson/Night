/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.LevelRenderer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package night.mixins;

import net.minecraft.client.renderer.LevelRenderer;
import night.Night;
import night.modules.impl.visuals.NoRenderModule;
import night.pingbypass.PingBypassFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LevelRenderer.class})
public class LevelRendererMixin {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void night$resetItemCounter(CallbackInfo info) {
        NoRenderModule noRender = Night.MODULE_MANAGER.getModule(NoRenderModule.class);
        if (noRender != null) {
            noRender.resetItemCounter();
        }
    }

    /**
     * Light PingBypass proxy: skip the world render entirely. The client still ticks, loads
     * chunks and runs modules, but no world geometry is built or drawn, so slow VPS GPUs
     * cannot starve the tick loop.
     */
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void night$skipWorldRenderInLightProxy(CallbackInfo info) {
        if (PingBypassFlags.lightProxy) {
            info.cancel();
        }
    }
}

