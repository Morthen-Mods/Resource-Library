package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class GamePlayLootTables {
    public static final ResourceKey<LootTable> FISHING_FISH = createKey("fishing/fish");
    public static final ResourceKey<LootTable> FISHING_JUNK = createKey("fishing/junk");
    public static final ResourceKey<LootTable> FISHING_TREASURE = createKey("fishing/treasure");

    public static final ResourceKey<LootTable> ARMORER_GIFT = createKey("hero_of_the_village/armorer_gift");
    public static final ResourceKey<LootTable> BABY_GIFT = createKey("hero_of_the_village/baby_gift");
    public static final ResourceKey<LootTable> BUTCHER_GIFT = createKey("hero_of_the_village/butcher_gift");
    public static final ResourceKey<LootTable> CARTOGRAPHER_GIFT = createKey("hero_of_the_village/cartographer_gift");
    public static final ResourceKey<LootTable> CLERIC_GIFT = createKey("hero_of_the_village/cleric_gift");
    public static final ResourceKey<LootTable> FARMER_GIFT = createKey("hero_of_the_village/farmer_gift");
    public static final ResourceKey<LootTable> FISHERMAN_GIFT = createKey("hero_of_the_village/fisherman_gift");
    public static final ResourceKey<LootTable> FLETCHER_GIFT = createKey("hero_of_the_village/fletcher_gift");
    public static final ResourceKey<LootTable> LEATHERWORKER_GIFT = createKey("hero_of_the_village/leatherworker_gift");
    public static final ResourceKey<LootTable> LIBRARIAN_GIFT = createKey("hero_of_the_village/librarian_gift");
    public static final ResourceKey<LootTable> MASON_GIFT = createKey("hero_of_the_village/mason_gift");
    public static final ResourceKey<LootTable> SHEPARD_GIFT = createKey("hero_of_the_village/shepard_gift");
    public static final ResourceKey<LootTable> TOOLSMITH_GIFT = createKey("hero_of_the_village/toolsmith_gift");
    public static final ResourceKey<LootTable> UNEMPLOYED_GIFT = createKey("hero_of_the_village/unemployed_gift");
    public static final ResourceKey<LootTable> WEAPONSMITH_GIFT = createKey("hero_of_the_village/weaponsmith_gift");

    public static final ResourceKey<LootTable> ARMADILLO_SHED = createKey("armadillo_shed");
    public static final ResourceKey<LootTable> CAT_MORNING_GIFT = createKey("cat_morning_gift");
    public static final ResourceKey<LootTable> CHICKEN_LAY = createKey("chicken_lay");
    public static final ResourceKey<LootTable> FISHING = createKey("fishing");
    public static final ResourceKey<LootTable> PANDA_SNEEZE = createKey("panda_sneeze");
    public static final ResourceKey<LootTable> PIGLIN_BARTERING = createKey("piglin_bartering");
    public static final ResourceKey<LootTable> SNIFFER_DIGING = createKey("sniffer_digging");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("gameplay/" + id));
    }
}
