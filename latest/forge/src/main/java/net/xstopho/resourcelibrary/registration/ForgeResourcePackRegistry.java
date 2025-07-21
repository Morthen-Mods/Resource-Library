package net.xstopho.resourcelibrary.registration;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.xstopho.resourcelibrary.LibConstants;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeResourcePackRegistry implements ResourcePackRegistry {

    private String modId;
    private static final Map<ResourceLocation, Component> RESOURCE_PACKS = new HashMap<>();

    @Override
    public ResourcePackRegistry setModId(String modId) {
        this.modId = modId;
        return this;
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
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            RESOURCE_PACKS.forEach((location, component) -> {
                IModInfo modInfo = ModList.get().getModContainerById(location.getNamespace()).orElseThrow(
                        () -> new IllegalArgumentException("Mod not found: " + location.getNamespace())).getModInfo();
                Path resourcePath = modInfo.getOwningFile().getFile().findResource(location.getPath());
                ArtifactVersion version = modInfo.getVersion();

                Pack pack = Pack.readMetaAndCreate(
                      new PackLocationInfo("mod/" + location, component, PackSource.BUILT_IN, Optional.of(new KnownPack("forge", "mod/" + location, version.toString()))),
                        fromName(packLocationInfo -> new PathPackResources(packLocationInfo, resourcePath)), PackType.CLIENT_RESOURCES, new PackSelectionConfig(false, Pack.Position.TOP, false));

                event.addRepositorySource(consumer -> consumer.accept(pack));
                LibConstants.LOG.info("Registered Built-In Resourcepack: {}", component.getString());
            });
        }
    }
    public static Pack.ResourcesSupplier fromName(final Function<PackLocationInfo, PackResources> onName) {
        return new Pack.ResourcesSupplier() {
            public PackResources openPrimary(PackLocationInfo packLocationInfo) {
                return onName.apply(packLocationInfo);
            }

            public PackResources openFull(PackLocationInfo packLocationInfo, Pack.Metadata metadata) {
                return onName.apply(packLocationInfo);
            }
        };
    }
}
