package net.morthen.resourcelibrary;

import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.morthen.resourcelibrary.components.LibDataComponents;
import net.morthen.resourcelibrary.modifier.ForgeLootTableModifier;
import net.morthen.resourcelibrary.network.ForgeNetworkHelper;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {

    public ResourceLibrary(FMLJavaModLoadingContext context) {
        LibDataComponents.init();

        EventBus<FMLCommonSetupEvent> commonSetup = FMLCommonSetupEvent.getBus(context.getModBusGroup());
        commonSetup.addListener(ForgeLootTableModifier::commonSetup);
        commonSetup.addListener(ForgeNetworkHelper::commonSetup);
    }
}
