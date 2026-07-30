package net.morthen.resourcelibrary.gametests.objects;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.morthen.resourcelibrary.LibConstants;

public class TestBlock extends Block {
    public TestBlock(String id) {
        super(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, id))));
    }
}
