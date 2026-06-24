package net.morthen.resourcelibrary.registration;

import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.service.CoreServices;
import org.jetbrains.annotations.NotNull;

public interface ResourcePackRegistry {

    static ResourcePackRegistry getInstance(String modId) {
        return CoreServices.load(ResourcePackRegistry.class).setModId(modId);
    }

    ResourcePackRegistry setModId(String modId);

    String getModId();

    default void register(@NotNull String packName, @NotNull String displayName) {
        register(Identifier.fromNamespaceAndPath(this.getModId(), packName), displayName);
    }

    void register(@NotNull Identifier packLocation, @NotNull String displayName);
}
