package net.xstopho.resourcelibrary_test;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RLibTestConstants {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);


    public static Item.Properties baseItemProperties(String id) {
        return new Item.Properties()
                .setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, id)));
    }

    public static BlockBehaviour.Properties baseBlockProperties(String id) {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
                .setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, id)));
    }
}
