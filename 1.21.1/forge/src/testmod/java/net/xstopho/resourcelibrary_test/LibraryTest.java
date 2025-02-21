package net.xstopho.resourcelibrary_test;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(LibraryTestConstants.MOD_ID)
public class LibraryTest {

    public LibraryTest() {
        LibraryTestConstants.commonInit();
    }

    @Mod.EventBusSubscriber(modid = LibraryTestConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientHandler {

        @SubscribeEvent
        public static void onClientInit(FMLClientSetupEvent event) {
            LibraryTestConstants.clientInit();
        }
    }

    @Mod.EventBusSubscriber(modid = LibraryTestConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModHandler {

        @SubscribeEvent
        public static void onClientInit(PlayerEvent.PlayerLoggedInEvent event) {
            LibraryTestConstants.TEST_EVENT.invoker().testInvoker(event.getEntity());
        }
    }
}
