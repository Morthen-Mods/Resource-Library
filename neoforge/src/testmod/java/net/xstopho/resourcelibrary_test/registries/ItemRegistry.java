package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.get(ResourceLibraryTest.MOD_ID, BuiltInRegistries.ITEM);

    public static final RegistryObject<Item> RECIPE_REMAINDER = register("recipe_remainder");
    public static final RegistryObject<Item> IN_HAND_ITEM = register("in_hand_item");

    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        return ITEMS.register(id, item);
    }

    public static RegistryObject<Item> register(String id) {
        return register(id, () -> new Item(new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath(ResourceLibraryTest.MOD_ID, id)))));
    }

    public static void init() {}
}
