package net.xstopho.resourcelibrary.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class ResourceBlockStateProvider extends BlockStateProvider {

    public ResourceBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    public void createFurnaceLikeBlock(Block block) {
        horizontalBlock(block, modifyBlockKey(block, "_side"), modifyBlockKey(block, ""), modifyBlockKey(block, "_top"));
    }

    private ResourceLocation modifyBlockKey(Block block, String texturePosition) {
        String[] parts = getBlockKey(block).toString().split(":");
        return ResourceLocation.fromNamespaceAndPath(parts[0], "block/" + parts[1] + texturePosition);
    }

    private ResourceLocation getBlockKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
