package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.xstopho.resourcelibrary.items.RecipeRemainder;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final RegistryProvider<Item> ITEMS = RegistryProvider.get(BuiltInRegistries.ITEM, ResourceLibraryTest.MOD_ID);

    public static final RegistryObject<Item> RECIPE_REMAINDER = register("recipe_remainder", () -> new RecipeRemainingItem(new Item.Properties()));
    public static final RegistryObject<Item> IN_HAND_ITEM = register("in_hand_item");

    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        return ITEMS.register(id, item);
    }

    public static RegistryObject<Item> register(String id) {
        return register(id, () -> new Item(new Item.Properties()));
    }

    public static void init() {}

    private static class RecipeRemainingItem extends RecipeRemainder {

        public RecipeRemainingItem(Properties properties) {
            super(properties);
        }

        @Override
        public ItemStack getRemainingItem(ItemStack itemStack) {
            return ItemStack.EMPTY;
        }
    }
}
