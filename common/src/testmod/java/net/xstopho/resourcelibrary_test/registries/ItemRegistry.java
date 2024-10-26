package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.RLibTestConstants;
import net.xstopho.resourcelibrary_test.items.TestCraftingRemainder;

import java.util.function.Supplier;

public class ItemRegistry {

    public static final RegistryProvider<Item> ITEMS = RegistryProvider.get(RLibTestConstants.MOD_ID, BuiltInRegistries.ITEM);

    public static final RegistryObject<Item> TEST_ITEM = register("test_item");
    public static final RegistryObject<Item> TEST_RECIPE_REMAINDER = register("test_recipe_remainder",
            () -> new TestCraftingRemainder(RLibTestConstants.baseItemProperties("test_recipe_remainder").durability(100)));

    private static RegistryObject<Item> register(String id, Supplier<Item> item) {
        return ITEMS.register(id, item);
    }

    private static RegistryObject<Item> register(String id) {
        Item.Properties base = RLibTestConstants.baseItemProperties(id);
        return register(id, () -> new Item(base));
    }

    public static void init() {}
}
