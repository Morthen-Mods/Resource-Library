package net.xstopho.resourcelibrary_test.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

@Mod.EventBusSubscriber(modid = RLibTestConstants.MOD_ID, value = Dist.CLIENT)
public class ForgeClientHandler {

    @SubscribeEvent
    public static void registerClientTickEvent(TickEvent.ClientTickEvent.Post event) {

    }
}
