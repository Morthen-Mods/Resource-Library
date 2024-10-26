package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ModInitializer;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

public class ResourceLibraryTest implements ModInitializer {
    
    @Override
    public void onInitialize() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();
    }
}
