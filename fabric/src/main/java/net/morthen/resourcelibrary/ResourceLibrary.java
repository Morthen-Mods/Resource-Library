package net.morthen.resourcelibrary;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.morthen.resourcelibrary.modifier.FabricLootTableModifier;

public class ResourceLibrary implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {

    @Override
    public void onInitialize() {
        FabricLootTableModifier.init();
    }

    // Client/server entrypoints run after the main entrypoints of all mods, so this is Fabric's "common setup"
    @Override
    public void onInitializeClient() {
        FabricLootTableModifier.postInit();
    }

    @Override
    public void onInitializeServer() {
        FabricLootTableModifier.postInit();
    }
}
