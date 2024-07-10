package net.xstopho.resourcelibrary_test.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluids;
import net.xstopho.resourcelibrary.util.TagHelper;

import java.util.concurrent.CompletableFuture;

public class TagProv {
    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createItemTag("test_items")).add(Items.DIAMOND);
        }
    }

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {
        public BlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createBlockTag("test_blocks"))
                    .add(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
        }
    }

    public static class EnchantmentTags extends FabricTagProvider.EnchantmentTagProvider {
        public EnchantmentTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createEnchantmentTag("test_enchantment")).add(Enchantments.FORTUNE);
        }
    }

    public static class FluidTags extends FabricTagProvider.FluidTagProvider {

        public FluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createFluidTag("test_fluid")).add(Fluids.LAVA);
        }
    }

    public static class BlockEntityTypeTags extends FabricTagProvider.BlockEntityTypeTagProvider {

        public BlockEntityTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createBlockEntityTypeTag("test_block_entity_type")).add(BlockEntityType.BLAST_FURNACE);
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {

        public EntityTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(TagHelper.createEntityTypeTag("test_entity_type")).add(EntityType.ALLAY);
        }
    }
}
