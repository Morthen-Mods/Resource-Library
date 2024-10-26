package net.xstopho.resourcelibrary_test;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RLibTestConstants.MOD_ID)
public class ResourceLibraryTest {

    public ResourceLibraryTest(FMLJavaModLoadingContext context) {
        RLibTestConstants.commonInit();
    }
}
