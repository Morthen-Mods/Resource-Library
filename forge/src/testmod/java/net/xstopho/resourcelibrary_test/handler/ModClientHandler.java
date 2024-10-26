package net.xstopho.resourcelibrary_test.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

@Mod.EventBusSubscriber(modid = RLibTestConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientHandler {

    @SubscribeEvent
    public static void onClientInit(FMLClientSetupEvent event) {

    }
}
