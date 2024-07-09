package net.xstopho.resourcelibrary.test;

import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.rendering.item.ItemModelRenderHelper;

public class LibraryTest {

    public static void init() {
        TestBlocks.init();
        TestItems.init();

        TestCreativeTab.init();
    }

    public static void initClient() {
        ItemModelRenderHelper.registerItemModel(TestItems.TEST_IN_HAND_ITEM.get(), ResourceLocation.fromNamespaceAndPath(LibConstants.MOD_ID, "in_hand/test_in_hand_item"));
    }
}
