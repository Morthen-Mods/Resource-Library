package net.xstopho.resourcelibrary.registration;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.service.CoreServices;
import org.jetbrains.annotations.NotNull;

public interface ResourcePackRegistry {

    static ResourcePackRegistry getInstance(String modId) {
        return CoreServices.load(ResourcePackRegistry.class).setModId(modId);
    }

    ResourcePackRegistry setModId(String modId);

    void register(@NotNull ResourceLocation packLocation, @NotNull String packDisplayName);
}
