package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.rendering.item.ItemModelRenderHelper;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

public class ResourceLibraryTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemModelRenderHelper.registerItemModel(ItemRegistry.TEST_IN_HAND_ITEM.get(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, "in_hand/in_hand_item"));
    }
}
