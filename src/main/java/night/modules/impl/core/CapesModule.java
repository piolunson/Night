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

@RegisterModule(name="Capes", description="Applies the Night cape to yourself and to other users.", category=Module.Category.CORE, toggled=true, drawn=false)
public class CapesModule
extends Module {
    private final Identifier capeTexture = Identifier.fromNamespaceAndPath((String)"night", (String)"cape");

    public Identifier getCapeTexture() {
        return this.capeTexture;
    }
}

