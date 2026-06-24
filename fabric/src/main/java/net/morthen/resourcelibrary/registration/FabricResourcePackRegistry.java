package net.morthen.resourcelibrary.registration;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.LibConstants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FabricResourcePackRegistry implements ResourcePackRegistry {

    private String modId;
    private final List<Identifier> RESOURCE_PACKS = new ArrayList<>();

    @Override
    public ResourcePackRegistry setModId(String modId) {
        this.modId = modId;
        return this;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void register(@NotNull Identifier packLocation, @NotNull String packDisplayName) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return;

        if (!RESOURCE_PACKS.contains(packLocation)) {
            RESOURCE_PACKS.add(packLocation);
            FabricLoader.getInstance().getModContainer(this.modId).ifPresent(modContainer -> {
                ResourceLoader.registerBuiltinPack(packLocation, modContainer, Component.literal(packDisplayName), PackActivationType.NORMAL);
                LibConstants.LOG.info("Registered Built-In Resourcepack: {}", packDisplayName);
            });
        } else LibConstants.LOG.error("Resourcepack '{}' with location '{}' is already registered!", packDisplayName, packLocation);
    }
}
