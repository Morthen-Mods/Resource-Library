package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ChestLootTables {
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT = createKey("abandoned_mineshaft");
    public static final ResourceKey<LootTable> ANCIENT_CITY = createKey("ancient_city");
    public static final ResourceKey<LootTable> ANCIENT_CITY_ICE_BOX = createKey("ancient_city_ice_box");
    public static final ResourceKey<LootTable> BASTION_BRIDGE = createKey("bastion_bridge");
    public static final ResourceKey<LootTable> BASTION_HOGLIN_STABLE = createKey("bastion_hoglin_stable");
    public static final ResourceKey<LootTable> BASTION_OTHER = createKey("bastion_other");
    public static final ResourceKey<LootTable> BASTION_TREASURE = createKey("bastion_treasure");
    public static final ResourceKey<LootTable> BURIED_TREASURE = createKey("buried_treasure");
    public static final ResourceKey<LootTable> DESERT_PYRAMID = createKey("desert_pyramid");
    public static final ResourceKey<LootTable> END_CITY_TREASURE = createKey("end_city_treasure");
    public static final ResourceKey<LootTable> IGLOO_CHEST = createKey("igloo_chest");
    public static final ResourceKey<LootTable> JUNGLE_TEMPLE = createKey("jungle_temple");
    public static final ResourceKey<LootTable> JUNGLE_TEMPLE_DISPENSER = createKey("jungle_temple_dispenser");
    public static final ResourceKey<LootTable> NETHER_BRIDGE = createKey("nether_bridge");
    public static final ResourceKey<LootTable> PILLAGER_OUTPOST = createKey("pillager_outpost");
    public static final ResourceKey<LootTable> RUINED_PORTAL = createKey("ruined_portal");
    public static final ResourceKey<LootTable> SHIPWRECK_MAP = createKey("shipwreck_map");
    public static final ResourceKey<LootTable> SHIPWRECK_SUPPLY = createKey("shipwreck_supply");
    public static final ResourceKey<LootTable> SHIPWRECK_TREASURE = createKey("shipwreck_treasure");
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON = createKey("simple_dungeon");
    public static final ResourceKey<LootTable> SPAWN_BONUS_CHEST = createKey("spawn_bonus_chest");
    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR = createKey("stronghold_corridor");
    public static final ResourceKey<LootTable> STRONGHOLD_CROSSING = createKey("stronghold_crossing");
    public static final ResourceKey<LootTable> STRONGHOLD_LIBRARY = createKey("stronghold_library");
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_BIG = createKey("underwater_ruin_big");
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_SMALL = createKey("underwater_ruin_small");
    public static final ResourceKey<LootTable> WOODLAND_MANSION = createKey("woodland_mansion");

    public static final ResourceKey<LootTable> VILLAGE_ARMORER = createKey("village/village_armorer");
    public static final ResourceKey<LootTable> VILLAGE_BUTCHER = createKey("village/village_butcher");
    public static final ResourceKey<LootTable> VILLAGE_CARTOGRAPHER = createKey("village/village_cartographer");
    public static final ResourceKey<LootTable> VILLAGE_DESERT_HOUSE = createKey("village/village_desert_house");
    public static final ResourceKey<LootTable> VILLAGE_FISHER = createKey("village/village_fisher");
    public static final ResourceKey<LootTable> VILLAGE_FLETCHER = createKey("village/village_fletcher");
    public static final ResourceKey<LootTable> VILLAGE_MASON = createKey("village/village_mason");
    public static final ResourceKey<LootTable> VILLAGE_PLAINS_HOUSE = createKey("village/village_plains_house");
    public static final ResourceKey<LootTable> VILLAGE_SAVANNA_HOUSE = createKey("village/village_savanna_house");
    public static final ResourceKey<LootTable> VILLAGE_SHEPARD = createKey("village/village_shepard");
    public static final ResourceKey<LootTable> VILLAGE_SNOWY_HOUSE = createKey("village/village_snowy_house");
    public static final ResourceKey<LootTable> VILLAGE_TAIGA_HOUSE = createKey("village/village_taiga_house");
    public static final ResourceKey<LootTable> VILLAGE_TANNERY = createKey("village/village_tannery");
    public static final ResourceKey<LootTable> VILLAGE_TEMPLE = createKey("village/village_temple");
    public static final ResourceKey<LootTable> VILLAGE_TOOLSMITH = createKey("village/village_toolsmith");
    public static final ResourceKey<LootTable> VILLAGE_WEAPONSMITH = createKey("village/village_weaponsmith");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("chests/" + id));
    }
}
