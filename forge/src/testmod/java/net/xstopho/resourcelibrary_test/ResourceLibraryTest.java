package net.xstopho.resourcelibrary_test;

import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ResourceLibraryTest.MOD_ID)
public class ResourceLibraryTest {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public ResourceLibraryTest() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();
    }
}
