package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.RLibTestConstants;

import java.util.function.Supplier;

public class BlockRegistry {

    private static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(RLibTestConstants.MOD_ID, BuiltInRegistries.BLOCK);

    public static final RegistryObject<Block> TEST_BLOCK = register("test_block");

    private static RegistryObject<Block> register(String id, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(id, block);

        Item.Properties base = RLibTestConstants.baseItemProperties(id);
        ItemRegistry.ITEMS.register(id, () -> new BlockItem(toReturn.get(), base));

        return toReturn;
    }

    private static RegistryObject<Block> register(String id) {
        BlockBehaviour.Properties base = RLibTestConstants.baseBlockProperties(id);
        return register(id, () -> new Block(base));
    }

    public static void init() {}
}
