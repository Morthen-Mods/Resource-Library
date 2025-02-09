package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class ArchaeologyLootTables {

    //TODO: Auf multiversion umstellen und LootModifier änderungen übernehmen 

    public static final ResourceKey<LootTable> DESERT_PYRAMID = createKey("archaeology/desert_pyramid");
    public static final ResourceKey<LootTable> DESERT_WELL = createKey("archaeology/desert_well");
    public static final ResourceKey<LootTable> OCEAN_RUIN_COLD = createKey("archaeology/ocean_ruin_cold");
    public static final ResourceKey<LootTable> OCEAN_RUIN_WARM = createKey("archaeology/ocean_ruin_warm");
    public static final ResourceKey<LootTable> TRAIL_RUINS_COMMON = createKey("archaeology/trail_ruins_common");
    public static final ResourceKey<LootTable> TRAIL_RUINS_RARE = createKey("archaeology/trail_ruins_rare");

    private static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }
}
