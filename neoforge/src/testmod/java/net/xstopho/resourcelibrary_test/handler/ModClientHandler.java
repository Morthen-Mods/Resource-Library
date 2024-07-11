package net.xstopho.resourcelibrary_test.handler;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.xstopho.resourcelibrary.rendering.item.ItemModelRenderHelper;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

@EventBusSubscriber(modid = ResourceLibraryTest.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModClientHandler {

    @SubscribeEvent
    public static void onClientInit(FMLClientSetupEvent event) {
        ItemModelRenderHelper.registerItemModel(ItemRegistry.IN_HAND_ITEM.get(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, "in_hand/in_hand_item"));
    }
}
