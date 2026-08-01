package net.morthen.resourcelibrary;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.morthen.resourcelibrary.gametest.ForgeGametests;
import net.morthen.resourcelibrary.gametests.LootModifierTests;
import net.morthen.resourcelibrary.gametests.RecipeRemainderTest;
import net.morthen.resourcelibrary.gametests.RegistryTests;
import net.morthen.resourcelibrary.service.LibServices;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {

    public ResourceLibrary(FMLJavaModLoadingContext context) {

        if (LibServices.PLATFORM.isDev()) {
            RegistryTests.setupRegistry();
            LootModifierTests.setupModifier();
            RecipeRemainderTest.setupRemainder();

            ForgeGametests.GAMETESTS.register(context.getModBusGroup());
        }
    }
}
