package net.xstopho.resourcelibrary_test.handler;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;

@Mod.EventBusSubscriber(modid = ResourceLibraryTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeHandler {
    @SubscribeEvent
    public static void registerLoginEvents(PlayerEvent.PlayerLoggedInEvent event) {

    }
}
