/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  lombok.Generated
 *  net.minecraft.resources.Identifier
 */
package night.modules.impl.core;

import net.minecraft.resources.Identifier;
import night.modules.Module;
import night.modules.RegisterModule;
import night.settings.impl.BooleanSetting;
import night.settings.impl.ModeSetting;

@RegisterModule(name="Capes", description="Gives you a Night cape: pick a design, mirror it on other players or wear it on your elytra.", category=Module.Category.CORE, toggled=true, drawn=false)
public class CapesModule
extends Module {
    public static final String[] CAPE_DESIGNS = new String[]{"Night", "Aurora", "Circuit", "Ember", "Sakura", "Prism", "Static", "Void", "None"};

    public ModeSetting cape = new ModeSetting("Cape", "Cape design applied to your skin.", "Night", CAPE_DESIGNS);
    public BooleanSetting elytra = new BooleanSetting("Elytra", "Wears the cape design on your elytra as well.", false);
    public BooleanSetting others = new BooleanSetting("Others", "Shows the cape on every other player too (client-side only).", false);

    public Identifier getCapeTexture() {
        return capeTextureFor(this.cape.getValue());
    }

    public static Identifier capeTextureFor(String design) {
        if (design == null || design.equalsIgnoreCase("None")) {
            return null;
        }
        if (design.equalsIgnoreCase("Night")) {
            return Identifier.fromNamespaceAndPath((String)"night", (String)"cape");
        }
        for (String known : CAPE_DESIGNS) {
            if (known.equalsIgnoreCase(design)) {
                return Identifier.fromNamespaceAndPath((String)"night", (String)("capes/" + known.toLowerCase()));
            }
        }
        return null;
    }
}
