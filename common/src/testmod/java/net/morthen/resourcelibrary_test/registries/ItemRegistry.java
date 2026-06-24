package net.morthen.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.registration.RegistryProvider;
import net.morthen.resourcelibrary_test.TestConstants;
import net.morthen.resourcelibrary_test.items.TestCraftingRemainder;

import java.util.function.Function;

public class ItemRegistry {

    private static final RegistryProvider<Item> ITEMS = RegistryProvider.get(TestConstants.MOD_ID, BuiltInRegistries.ITEM);

    public static final RegistryObject<Item> TEST_ITEM = register("test_item", Item::new);
    public static final RegistryObject<Item> TEST_RECIPE_REMAINDER = register("recipe_remainder", properties -> new TestCraftingRemainder(properties, 100));

    public static RegistryObject<Item> register(String id, Function<Item.Properties, Item> function, Item.Properties properties) {
        return ITEMS.register(id, () -> function.apply(properties.setId(createKey(id))));
    }

    public static RegistryObject<Item> register(String id, Function<Item.Properties, Item> function) {
        return register(id, function, new Item.Properties());
    }

    private static ResourceKey<Item> createKey(String id) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ITEMS.getModId(), id));
    }

    public static void init() {}
}
