package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(ResourceLibraryTest.MOD_ID, BuiltInRegistries.BLOCK);

    public static final RegistryObject<Block> TEST_FURNACE_LIKE_BLOCK = register("furnace_like_block",
            () -> new FurnaceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FURNACE)
                    .setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, "furnace_like_block")))));

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(id, block);
        ItemRegistry.register(id, () -> new BlockItem(toReturn.get(), new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, id)))));
        return toReturn;
    }

    public static RegistryObject<Block> register(String id) {
        return register(id, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, id)))));
    }

    public static void init() {}
}
