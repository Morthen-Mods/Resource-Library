package net.xstopho.resourcelibrary_test;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TestConstants.MOD_ID)
public class LibraryTest {

    public LibraryTest(FMLJavaModLoadingContext context) {
        TestConstants.commonInit();
    }

    @Mod.EventBusSubscriber(modid = TestConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientHandler {

        @SubscribeEvent
        public static void onClientInit(FMLClientSetupEvent event) {
            TestConstants.clientInit();
        }
    }

    @Mod.EventBusSubscriber(modid = TestConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeHandler {

        @SubscribeEvent
        public static void onJoin(PlayerEvent.PlayerLoggedInEvent event) {
            TestConstants.TEST_EVENT.invoker().onJoin(event.getEntity());        }
    }
}
