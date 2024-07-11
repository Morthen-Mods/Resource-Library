package net.xstopho.resourcelibrary_test.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.xstopho.resourcelibrary.datagen.ResourceItemModelProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

public class ItemModelProv extends ResourceItemModelProvider {
    public ItemModelProv(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ResourceLibraryTest.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ItemRegistry.RECIPE_REMAINDER.get());
        simpleItem(ItemRegistry.IN_HAND_ITEM.get());
        createInHandItem(ItemRegistry.IN_HAND_ITEM.get(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, "item/in_hand/handheld_large"));
    }
}