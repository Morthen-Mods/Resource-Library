package net.xstopho.resourcelibrary_test;

import net.neoforged.fml.common.Mod;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

@Mod(RLibTestConstants.MOD_ID)
public class ResourceLibraryTest {

    public ResourceLibraryTest() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();
    }
}
