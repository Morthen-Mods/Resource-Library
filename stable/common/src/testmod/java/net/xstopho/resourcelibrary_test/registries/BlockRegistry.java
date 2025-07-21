package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.TestConstants;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(BuiltInRegistries.BLOCK, TestConstants.MOD_ID);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(id, block);
        ItemRegistry.register(id, () -> new BlockItem(toReturn.get(), new Item.Properties()));
        return toReturn;
    }

    public static RegistryObject<Block> register(String id) {
        return register(id, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    }

    public static void init() {}
}
