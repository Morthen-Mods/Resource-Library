package net.xstopho.resourcelibrary.registration;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.service.CoreServices;
import org.jetbrains.annotations.NotNull;

public interface ResourcePackRegistry {

    static ResourcePackRegistry getInstance(String modId) {
        return CoreServices.load(ResourcePackRegistry.class).setModId(modId);
    }

    ResourcePackRegistry setModId(String modId);

    String getModId();

    default void register(@NotNull String packName, @NotNull String displayName) {
        register(ResourceLocation.fromNamespaceAndPath(this.getModId(), packName), displayName);
    }

    void register(@NotNull ResourceLocation packLocation, @NotNull String displayName);
}
