package net.morthen.resourcelibrary;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibConstants {
    public static final String MOD_ID = "resourcelibrary";
    public static final String MOD_NAME = "Resource Library";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceKey<LootTable> createLootTableKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace(id));
    }
}
