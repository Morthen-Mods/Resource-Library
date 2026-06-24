package net.xstopho.resourcelibrary.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

public class TagUtil {
    public static TagKey<Item> createItemTag(String id) {
        return createTag(Registries.ITEM, id);
    }

    public static TagKey<Block> createBlockTag(String id) {
        return createTag(Registries.BLOCK, id);
    }

    public static TagKey<Enchantment> createEnchantmentTag(String id) {
        return createTag(Registries.ENCHANTMENT, id);
    }

    public static TagKey<Fluid> createFluidTag(String id) {
        return createTag(Registries.FLUID, id);
    }

    public static TagKey<BlockEntityType<?>> createBlockEntityTypeTag(String id) {
        return createTag(Registries.BLOCK_ENTITY_TYPE, id);
    }

    public static TagKey<EntityType<?>> createEntityTypeTag(String id) {
        return createTag(Registries.ENTITY_TYPE, id);
    }

    static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> registry, String id) {
        return TagKey.create(registry, Identifier.fromNamespaceAndPath("c", id));
    }
}
