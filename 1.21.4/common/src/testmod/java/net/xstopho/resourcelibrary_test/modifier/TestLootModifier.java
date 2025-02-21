package net.xstopho.resourcelibrary_test.modifier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.modifier.LootTableModifier;
import net.xstopho.resourcelibrary.modifier.loot_tables.ChestLootTables;
import net.xstopho.resourcelibrary.modifier.loot_tables.EntityLootTables;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;

import java.util.List;

public class TestLootModifier {

    private static final LootTableModifier modifier = LootTableModifier.getInstance();

    private static ResourceKey<LootTable> key(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }

    public static void init() {
        modifier.addItems(Blocks.DIAMOND_BLOCK, 1f, () ->1f, List.of(ChestLootTables.SPAWN_BONUS_CHEST));
    }
}
