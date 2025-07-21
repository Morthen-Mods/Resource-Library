package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ChestLootTables {

    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT = createKey("chests/abandoned_mineshaft");
    public static final ResourceKey<LootTable> ANCIENT_CITY = createKey("chests/ancient_city");
    public static final ResourceKey<LootTable> ANCIENT_CITY_ICE_BOX = createKey("chests/ancient_city_ice_box");
    public static final ResourceKey<LootTable> BASTION_BRIDGE = createKey("chests/bastion_bridge");
    public static final ResourceKey<LootTable> BASTION_HOGLIN_STABLE = createKey("chests/bastion_hoglin_stable");
    public static final ResourceKey<LootTable> BASTION_OTHER = createKey("chests/bastion_other");
    public static final ResourceKey<LootTable> BASTION_TREASURE = createKey("chests/bastion_treasure");
    public static final ResourceKey<LootTable> BURIED_TREASURE = createKey("chests/buried_treasure");
    public static final ResourceKey<LootTable> DESERT_PYRAMID = createKey("chests/desert_pyramid");
    public static final ResourceKey<LootTable> END_CITY_TREASURE = createKey("chests/end_city_treasure");
    public static final ResourceKey<LootTable> IGLOO_CHEST = createKey("chests/igloo_chest");
    public static final ResourceKey<LootTable> JUNGLE_TEMPLE = createKey("chests/jungle_temple");
    public static final ResourceKey<LootTable> JUNGLE_TEMPLE_DISPENSER = createKey("chests/jungle_temple_dispenser");
    public static final ResourceKey<LootTable> NETHER_BRIDGE = createKey("chests/nether_bridge");
    public static final ResourceKey<LootTable> PILLAGER_OUTPOST = createKey("chests/pillager_outpost");
    public static final ResourceKey<LootTable> RUINED_PORTAL = createKey("chests/ruined_portal");
    public static final ResourceKey<LootTable> SHIPWRECK_MAP = createKey("chests/shipwreck_map");
    public static final ResourceKey<LootTable> SHIPWRECK_SUPPLY = createKey("chests/shipwreck_supply");
    public static final ResourceKey<LootTable> SHIPWRECK_TREASURE = createKey("chests/shipwreck_treasure");
    public static final ResourceKey<LootTable> SIMPLE_DUNGEON = createKey("chests/simple_dungeon");
    public static final ResourceKey<LootTable> SPAWN_BONUS_CHEST = createKey("chests/spawn_bonus_chest");
    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR = createKey("chests/stronghold_corridor");
    public static final ResourceKey<LootTable> STRONGHOLD_CROSSING = createKey("chests/stronghold_crossing");
    public static final ResourceKey<LootTable> STRONGHOLD_LIBRARY = createKey("chests/stronghold_library");
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_BIG = createKey("chests/underwater_ruin_big");
    public static final ResourceKey<LootTable> UNDERWATER_RUIN_SMALL = createKey("chests/underwater_ruin_small");
    public static final ResourceKey<LootTable> VILLAGE_ARMORER = createKey("chests/village/village_armorer");
    public static final ResourceKey<LootTable> VILLAGE_BUTCHER = createKey("chests/village/village_butcher");
    public static final ResourceKey<LootTable> VILLAGE_CARTOGRAPHER = createKey("chests/village/village_cartographer");
    public static final ResourceKey<LootTable> VILLAGE_DESERT_HOUSE = createKey("chests/village/village_desert_house");
    public static final ResourceKey<LootTable> VILLAGE_FISHER = createKey("chests/village/village_fisher");
    public static final ResourceKey<LootTable> VILLAGE_FLETCHER = createKey("chests/village/village_fletcher");
    public static final ResourceKey<LootTable> VILLAGE_MASON = createKey("chests/village/village_mason");
    public static final ResourceKey<LootTable> VILLAGE_PLAINS_HOUSE = createKey("chests/village/village_plains_house");
    public static final ResourceKey<LootTable> VILLAGE_SAVANNA_HOUSE = createKey("chests/village/village_savanna_house");
    public static final ResourceKey<LootTable> VILLAGE_SHEPHERD = createKey("chests/village/village_shepherd");
    public static final ResourceKey<LootTable> VILLAGE_SNOWY_HOUSE = createKey("chests/village/village_snowy_house");
    public static final ResourceKey<LootTable> VILLAGE_TAIGA_HOUSE = createKey("chests/village/village_taiga_house");
    public static final ResourceKey<LootTable> VILLAGE_TANNERY = createKey("chests/village/village_tannery");
    public static final ResourceKey<LootTable> VILLAGE_TEMPLE = createKey("chests/village/village_temple");
    public static final ResourceKey<LootTable> VILLAGE_TOOLSMITH = createKey("chests/village/village_toolsmith");
    public static final ResourceKey<LootTable> VILLAGE_WEAPONSMITH = createKey("chests/village/village_weaponsmith");
    public static final ResourceKey<LootTable> WOODLAND_MANSION = createKey("chests/woodland_mansion");


    private static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }
}
