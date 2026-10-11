package net.morthen.resourcelibrary;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.morthen.resourcelibrary.modifier.ForgeLootTableModifier;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {

    public ResourceLibrary(FMLJavaModLoadingContext context) {
        FMLCommonSetupEvent.getBus(context.getModBusGroup()).addListener(ForgeLootTableModifier::commonSetup);
    }
}
