package net.morthen.resourcelibrary.registration;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;
import net.morthen.resourcelibrary.LibConstants;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, value = Dist.CLIENT)
public class ForgeResourcePackRegistry implements ResourcePackRegistry {

    private String modId;
    private static final Map<Identifier, Component> RESOURCE_PACKS = new HashMap<>();

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
        Identifier packPath = Identifier.fromNamespaceAndPath(packLocation.getNamespace(), "resourcepacks/" + packLocation.getPath());

        if (!RESOURCE_PACKS.containsKey(packPath)) {
            RESOURCE_PACKS.put(packPath, Component.literal(packDisplayName));
        } else LibConstants.LOG.error("Resourcepack '{}' with location '{}' is already registered!", packDisplayName, packLocation);
    }

    @SubscribeEvent
    public static void registerResourcePacks(AddPackFindersEvent event) {
        RESOURCE_PACKS.forEach((location, component) -> {
            addPackFinders(event, location, PackType.CLIENT_RESOURCES, component,
                    PackSource.BUILT_IN, false, Pack.Position.TOP);

            LibConstants.LOG.info("Registered Built-In Resourcepack: {}", component.getString());
        });
    }

    /**
     * Port of NeoForge's {@code AddPackFindersEvent#addPackFinders}, which Forge does not provide.
     * Registers a pack located under the `resources/` folder of the mod with the namespace of {@code packLocation}.
     */
    private static void addPackFinders(AddPackFindersEvent event, Identifier packLocation, PackType packType, Component packNameDisplay,
                                       PackSource packSource, boolean alwaysActive, Pack.Position packPosition) {
        if (event.getPackType() != packType) return;

        IModInfo modInfo = ModList.getModContainerById(packLocation.getNamespace()).orElseThrow(
                () -> new IllegalArgumentException("Mod not found: " + packLocation.getNamespace())).getModInfo();
        Path resourcePath = modInfo.getOwningFile().getFile().findResource(packLocation.getPath());

        Pack pack = Pack.readMetaAndCreate(
                new PackLocationInfo("mod/" + packLocation, packNameDisplay, packSource,
                        Optional.of(new KnownPack("forge", "mod/" + packLocation, modInfo.getVersion().toString()))),
                new PathPackResources.PathResourcesSupplier(resourcePath),
                packType,
                new PackSelectionConfig(alwaysActive, packPosition, false));

        if (pack == null) {
            LibConstants.LOG.error("Failed to read pack metadata for '{}' at '{}'", packNameDisplay.getString(), resourcePath);
            return;
        }

        event.addRepositorySource(consumer -> consumer.accept(pack));
    }
}
