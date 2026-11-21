package night.mixins.accessors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Exposes the otherwise final {@code Minecraft.user} field so the alt manager can
 * swap the active session without restarting the game.
 */
@Mixin(value={Minecraft.class})
public interface MinecraftUserAccessor {
    @Mutable
    @Accessor(value="user")
    void night$setUser(User user);
}
