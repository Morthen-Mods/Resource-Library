package net.xstopho.resourcelibrary_test;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(LibraryTestConstants.MOD_ID)
public class LibraryTest {

    public LibraryTest() {
        LibraryTestConstants.commonInit();
    }

    @EventBusSubscriber(modid = LibraryTestConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModClientHandler {

        @SubscribeEvent
        public static void onClientInit(FMLClientSetupEvent event) {
            LibraryTestConstants.clientInit();
        }
    }
}
