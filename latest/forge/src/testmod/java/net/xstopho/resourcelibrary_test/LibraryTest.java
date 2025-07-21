package net.xstopho.resourcelibrary_test;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestConstants.MOD_ID)
public class LibraryTest {

    public LibraryTest(FMLJavaModLoadingContext context) {
        TestConstants.commonInit();
    }

    @Mod.EventBusSubscriber(modid = TestConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeHandler {

        @SubscribeEvent
        public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
            TestConstants.TEST_EVENT.invoker().onJoin(event.getEntity());
        }
    }
}
