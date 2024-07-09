package net.xstopho.resourcelibrary.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.xstopho.resourcelibrary.service.CoreServices;

public class TagHelper {

    public static TagKey<Item> createItemTag(String id) {
        return createPlatformTag(Registries.ITEM, id);
    }

    public static TagKey<Block> createBlockTag(String id) {
        return createPlatformTag(Registries.BLOCK, id);
    }

    public static TagKey<Enchantment> createEnchantmentTag(String id) {
        return createPlatformTag(Registries.ENCHANTMENT, id);
    }

    public static TagKey<Fluid> createFluidTag(String id) {
        return createPlatformTag(Registries.FLUID, id);
    }

    public static TagKey<BlockEntityType<?>> createBlockEntityTypeTag(String id) {
        return createPlatformTag(Registries.BLOCK_ENTITY_TYPE, id);
    }

    public static TagKey<EntityType<?>> createEntityTypeTag(String id) {
        return createPlatformTag(Registries.ENTITY_TYPE, id);
    }

    public static <T> TagKey<T> createPlatformTag(ResourceKey<? extends Registry<T>> registry, String id) {
        return switch (CoreServices.getPlatform()) {
            case FABRIC, NEOFORGE -> createTag(registry, "c", id);
            case FORGE -> createTag(registry, "forge", id);
            case NO_LOADER -> throw new IllegalStateException("No Mod Loader defined or you are in an non Mod Environment!");
        };
    }

    static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> registry, String prefix, String id) {
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(prefix, id));
    }
}
