package net.xstopho.resourcelibrary.registration;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.xstopho.resourcelibrary.LibConstants;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = LibConstants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeResourcePackRegistry implements ResourcePackRegistry {

    private String modId;
    private static final Map<ResourceLocation, Component> RESOURCE_PACKS = new HashMap<>();

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
    public void register(@NotNull ResourceLocation packLocation, @NotNull String packDisplayName) {
        ResourceLocation packPath = ResourceLocation.fromNamespaceAndPath(packLocation.getNamespace(), "resourcepacks/" + packLocation.getPath());

        if (!RESOURCE_PACKS.containsKey(packPath)) {
            RESOURCE_PACKS.put(packPath, Component.literal(packDisplayName));
        } else LibConstants.LOG.error("Resourcepack '{}' with location '{}' is already registered!", packDisplayName, packLocation);
    }

    @SubscribeEvent
    public static void registerResourcePacks(AddPackFindersEvent event) {
        RESOURCE_PACKS.forEach((location, component) -> {
            event.addPackFinders(location, PackType.CLIENT_RESOURCES, component,
                    PackSource.BUILT_IN, false, Pack.Position.TOP);

            LibConstants.LOG.info("Registered Built-In Resourcepack: {}", component.getString());
        });
    }
}
