package net.morthen.resourcelibrary;

import net.fabricmc.api.ModInitializer;
import net.morthen.resourcelibrary.gametests.LootModifierTests;
import net.morthen.resourcelibrary.gametests.RegistryTests;
import net.morthen.resourcelibrary.service.LibServices;

public class ResourceLibrary implements ModInitializer {

    @Override
    public void onInitialize() {
        if (LibServices.PLATFORM.isDev()) {
            // Test Setups, only needed when events are involved
            RegistryTests.setupRegistry();
            LootModifierTests.setupModifier();
        }
    }
}
