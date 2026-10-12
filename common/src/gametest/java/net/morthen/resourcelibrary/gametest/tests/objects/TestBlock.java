package net.morthen.resourcelibrary.gametest.tests.objects;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class TestBlock extends Block {
    public TestBlock(ResourceKey<Block> key) {
        super(Properties.ofFullCopy(Blocks.DIRT).setId(key));
    }
}
