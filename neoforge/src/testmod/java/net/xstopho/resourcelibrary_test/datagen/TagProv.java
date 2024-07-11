package net.xstopho.resourcelibrary_test.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.xstopho.resourcelibrary.util.TagHelper;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagProv {

    public static class BlockTags extends BlockTagsProvider {

        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ResourceLibraryTest.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(TagHelper.createBlockTag("test_block")).add(Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE);
        }
    }

    public static class ItemTags extends ItemTagsProvider {

        public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper fileHelper) {
            super(output, lookupProvider, blockTagProvider, ResourceLibraryTest.MOD_ID, fileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(TagHelper.createItemTag("test_item")).add(Items.DIAMOND);
        }
    }

    public static class EnchantmentTags extends EnchantmentTagsProvider {

        public EnchantmentTags(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(TagHelper.createEnchantmentTag("test_enchantment")).add(Enchantments.FORTUNE);
        }
    }

    public static class FluidTags extends FluidTagsProvider {

        public FluidTags(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, completableFuture, ResourceLibraryTest.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(TagHelper.createFluidTag("test_fluid")).add(Fluids.LAVA);
        }
    }
}