package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.Items;
import net.xstopho.resourcelibrary.modifier.LootTableModifier;
import net.xstopho.resourcelibrary.modifier.loot_tables.ChestLootTables;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLibraryTest implements ModInitializer {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();

        LootTableModifier modifier = LootTableModifier.getInstance();
        modifier.addItems(Items.DIAMOND_BLOCK, 1f, () -> 1f, ChestLootTables.SPAWN_BONUS_CHEST);
    }
}
