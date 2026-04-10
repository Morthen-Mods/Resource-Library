package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.TestConstants;
import net.xstopho.resourcelibrary_test.blocks.TestCropBlock;

import java.util.function.Function;

public class BlockRegistry {

    private static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(TestConstants.MOD_ID, BuiltInRegistries.BLOCK);

    public static final RegistryObject<Block> TEST_BLOCK = register("test_block", TestCropBlock::new);

    private static RegistryObject<Block> register(String id, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockBehavior) {
        RegistryObject<Block> toReturn = BLOCKS.register(id, () -> function.apply(blockBehavior.setId(createKey(id))));

        ItemRegistry.register(id, properties -> new BlockItem(toReturn.get(), properties));
        return toReturn;
    }

    private static RegistryObject<Block> register(String id, Function<BlockBehaviour.Properties, Block> function) {
        return register(id, function, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    }

    private static ResourceKey<Block> createKey(String id) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(BLOCKS.getModId(), id));
    }

    public static void init() {}
}
