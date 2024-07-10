package net.xstopho.resourcelibrary_test.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.datagen.ResourceModelProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

public class ModelProv extends ResourceModelProvider {
    public ModelProv(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        createHorizontalBlock(blockStateModelGenerator, BlockRegistry.TEST_FURNACE_LIKE_BLOCK.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ItemRegistry.TEST_RECIPE_REMAINDER.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ItemRegistry.TEST_IN_HAND_ITEM.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        createInHandItemModel(itemModelGenerator, ItemRegistry.TEST_IN_HAND_ITEM.get(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, "item/in_hand/handheld_large"));
    }
}