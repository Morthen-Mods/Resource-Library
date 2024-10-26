package net.xstopho.resourcelibrary_test.modifier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.modifier.LootTableModifier;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

public class TestLootModifier {

    private static final LootTableModifier modifier = LootTableModifier.getInstance();

    private static ResourceKey<LootTable> key(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }

    public static void init() {
        modifier.addLoot(ItemRegistry.TEST_ITEM, 1f, 1f, key("entities/cow"));
        modifier.addLoot(ItemRegistry.TEST_RECIPE_REMAINDER, 1f, 1f, key("entities/cow"));
    }
}
