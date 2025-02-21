package net.xstopho.resourcelibrary_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(TestConstants.MOD_ID)
public class LibraryTest {

    public LibraryTest(IEventBus eventBus) {
        TestConstants.commonInit();
    }

    @EventBusSubscriber(modid = TestConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class NeoforgeHandler {

        @SubscribeEvent
        public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
            TestConstants.TEST_JOIN_EVENT.invoker().onJoin(event.getEntity());
        }
    }

}
