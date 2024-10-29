package net.xstopho.resourcelibrary.registration;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.LibConstants;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FabricResourcePackRegistry implements ResourcePackRegistry {

    private String modId;
    private final List<ResourceLocation> RESOURCE_PACKS = new ArrayList<>();

    @Override
    public ResourcePackRegistry setModId(String modId) {
        this.modId = modId;
        return this;
    }

    @Override
    public void register(@NotNull ResourceLocation packLocation, @NotNull String packDisplayName) {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) {
            LibConstants.LOG.info("Resource Pack was not registered! Please check if you call the ResourcePackRegistry only on Client side!\nLocation: {}\nName: {}", packLocation, packDisplayName);
            return;
        }

        if (!RESOURCE_PACKS.contains(packLocation)) {
            RESOURCE_PACKS.add(packLocation);
            FabricLoader.getInstance().getModContainer(this.modId).ifPresent(modContainer -> {
                ResourceManagerHelper.registerBuiltinResourcePack(packLocation, modContainer, Component.literal(packDisplayName), ResourcePackActivationType.NORMAL);
            });
        } else throw new IllegalStateException("You try to register the resource pack '" + packLocation + "' twice!");
    }
}
