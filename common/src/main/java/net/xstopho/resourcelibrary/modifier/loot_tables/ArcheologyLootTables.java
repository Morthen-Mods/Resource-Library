package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ArcheologyLootTables {
    public static final ResourceKey<LootTable> DESERT_PYRAMID = createKey("desert_pyramid");
    public static final ResourceKey<LootTable> DESERT_WELL = createKey("desert_well");
    public static final ResourceKey<LootTable> OCEAN_RUIN_COLD = createKey("ocean_ruin_cold");
    public static final ResourceKey<LootTable> OCEAN_RUIN_WARM = createKey("ocean_ruin_warm");
    public static final ResourceKey<LootTable> TRAIL_RUINS_COMMON = createKey("trail_ruins_common");
    public static final ResourceKey<LootTable> TRAIL_RUINS_RARE = createKey("trail_ruins_rare");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("archeology/" + id));
    }
}
