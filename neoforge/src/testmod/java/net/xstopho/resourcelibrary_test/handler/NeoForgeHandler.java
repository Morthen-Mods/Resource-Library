package net.xstopho.resourcelibrary_test.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

@EventBusSubscriber(modid = RLibTestConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeHandler {
    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {

    }
}
