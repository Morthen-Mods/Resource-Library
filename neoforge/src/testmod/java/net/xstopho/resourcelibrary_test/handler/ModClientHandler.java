package net.xstopho.resourcelibrary_test.handler;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

@EventBusSubscriber(modid = RLibTestConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModClientHandler {

    @SubscribeEvent
    public static void onClientInit(FMLClientSetupEvent event) {
        RLibTestConstants.clientInit();
    }
}
