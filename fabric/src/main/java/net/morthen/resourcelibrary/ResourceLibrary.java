package net.morthen.resourcelibrary;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.component.DataComponents;
import net.morthen.resourcelibrary.components.LibDataComponents;
import net.morthen.resourcelibrary.entity.FabricEntityDataHelper;
import net.morthen.resourcelibrary.modifier.FabricLootTableModifier;
import net.morthen.resourcelibrary.network.FabricNetworkHelper;

public class ResourceLibrary implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {

    @Override
    public void onInitialize() {
        LibDataComponents.init();
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.LORE, LibDataComponents.TOOLTIP);

        FabricLootTableModifier.init();
        FabricEntityDataHelper.registerEvents();
    }

    // Client/server entrypoints run after the main entrypoints of all mods, so this is Fabric's "common setup"
    @Override
    public void onInitializeClient() {
        FabricLootTableModifier.postInit();
        FabricNetworkHelper.postInit();
    }

    @Override
    public void onInitializeServer() {
        FabricLootTableModifier.postInit();
        FabricNetworkHelper.postInit();
    }
}
