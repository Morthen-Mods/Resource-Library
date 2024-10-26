package net.xstopho.resourcelibrary_test.handler;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

@EventBusSubscriber(modid = RLibTestConstants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientHandler {

    @SubscribeEvent
    public static void registerClientTickEvent(ClientTickEvent.Post event) {

    }
}
