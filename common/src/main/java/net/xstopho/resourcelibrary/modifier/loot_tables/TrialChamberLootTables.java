package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class TrialChamberLootTables {
    public static final ResourceKey<LootTable> CHEST_CORRIDOR = createKey("chests/trial_chambers/corridor");
    public static final ResourceKey<LootTable> CHEST_ENTRANCE = createKey("chests/trial_chambers/entrance");
    public static final ResourceKey<LootTable> CHEST_INTERSECTION = createKey("chests/trial_chambers/intersection");
    public static final ResourceKey<LootTable> CHEST_INTERSECTION_BARREL = createKey("chests/trial_chambers/intersection_barrel");
    public static final ResourceKey<LootTable> CHEST_REWARD = createKey("chests/trial_chambers/reward");
    public static final ResourceKey<LootTable> CHEST_REWARD_COMMON = createKey("chests/trial_chambers/reward_common");
    public static final ResourceKey<LootTable> CHEST_REWARD_RARE = createKey("chests/trial_chambers/reward_rare");
    public static final ResourceKey<LootTable> CHEST_REWARD_UNIQUE = createKey("chests/trial_chambers/reward_unique");
    public static final ResourceKey<LootTable> CHEST_OMINOUS = createKey("chests/trial_chambers/reward_ominous");
    public static final ResourceKey<LootTable> CHEST_OMINOUS_COMMON = createKey("chests/trial_chambers/reward_ominous_common");
    public static final ResourceKey<LootTable> CHEST_OMINOUS_RARE = createKey("chests/trial_chambers/reward_ominous_rare");
    public static final ResourceKey<LootTable> CHEST_OMINOUS_UNIQUE = createKey("chests/trial_chambers/reward_ominous_unique");
    public static final ResourceKey<LootTable> CHEST_SUPPLY = createKey("chests/trial_chambers/supply");

    public static final ResourceKey<LootTable> DISPENSER_CHAMBER = createKey("dispensers/trial_chambers/chamber");
    public static final ResourceKey<LootTable> DISPENSER_CORRIDOR = createKey("dispensers/trial_chambers/corridor");
    public static final ResourceKey<LootTable> DISPENSER_WATER = createKey("dispensers/trial_chambers/water");

    public static final ResourceKey<LootTable> EQUIPMENT_GENERAL = createKey("equipment/trial_chamber");
    public static final ResourceKey<LootTable> EQUIPMENT_MELEE = createKey("equipment/trial_chamber_melee");
    public static final ResourceKey<LootTable> EQUIPMENT_RANGED = createKey("equipment/trial_chamber_ranged");

    public static final ResourceKey<LootTable> POTS_CORRIDOR = createKey("pots/trial_chambers/corridor");

    public static final ResourceKey<LootTable> SPAWNER_CONSUMABLES = createKey("spawners/trial_chamber/consumables");
    public static final ResourceKey<LootTable> SPAWNER_ITEMS_WHEN_OMINOUS = createKey("spawners/trial_chamber/items_to_drop_when_ominous");
    public static final ResourceKey<LootTable> SPAWNER_KEY = createKey("spawners/trial_chambers/key");

    public static final ResourceKey<LootTable> SPAWNER_OMINOUS_CONSUMABLES = createKey("spawners/ominous/trial_chamber/consumables");
    public static final ResourceKey<LootTable> SPAWNER_OMINOUS_KEY = createKey("spawners/ominous/trial_chambers/key");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }
}
