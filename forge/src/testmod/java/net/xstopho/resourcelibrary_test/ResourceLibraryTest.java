package net.xstopho.resourcelibrary_test;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

@Mod(RLibTestConstants.MOD_ID)
public class ResourceLibraryTest {

    public ResourceLibraryTest(FMLJavaModLoadingContext context) {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();
    }
}
