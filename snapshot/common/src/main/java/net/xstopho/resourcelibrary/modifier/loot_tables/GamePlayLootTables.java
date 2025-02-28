package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class GamePlayLootTables {
    public static final ResourceKey<LootTable> ARMADILLO_SHED = createKey("gameplay/armadillo_shed");
    public static final ResourceKey<LootTable> CAT_MORNING_GIFT = createKey("gameplay/cat_morning_gift");
    public static final ResourceKey<LootTable> CHICKEN_LAY = createKey("gameplay/chicken_lay");
    public static final ResourceKey<LootTable> FISHING = createKey("gameplay/fishing");
    public static final ResourceKey<LootTable> FISHING_FISH = createKey("gameplay/fishing/fish");
    public static final ResourceKey<LootTable> FISHING_JUNK = createKey("gameplay/fishing/junk");
    public static final ResourceKey<LootTable> FISHING_TREASURE = createKey("gameplay/fishing/treasure");
    public static final ResourceKey<LootTable> ARMORER_GIFT = createKey("gameplay/hero_of_the_village/armorer_gift");
    public static final ResourceKey<LootTable> BABY_GIFT = createKey("gameplay/hero_of_the_village/baby_gift");
    public static final ResourceKey<LootTable> BUTCHER_GIFT = createKey("gameplay/hero_of_the_village/butcher_gift");
    public static final ResourceKey<LootTable> CARTOGRAPHER_GIFT = createKey("gameplay/hero_of_the_village/cartographer_gift");
    public static final ResourceKey<LootTable> CLERIC_GIFT = createKey("gameplay/hero_of_the_village/cleric_gift");
    public static final ResourceKey<LootTable> FARMER_GIFT = createKey("gameplay/hero_of_the_village/farmer_gift");
    public static final ResourceKey<LootTable> FISHERMAN_GIFT = createKey("gameplay/hero_of_the_village/fisherman_gift");
    public static final ResourceKey<LootTable> FLETCHER_GIFT = createKey("gameplay/hero_of_the_village/fletcher_gift");
    public static final ResourceKey<LootTable> LEATHERWORKER_GIFT = createKey("gameplay/hero_of_the_village/leatherworker_gift");
    public static final ResourceKey<LootTable> LIBRARIAN_GIFT = createKey("gameplay/hero_of_the_village/librarian_gift");
    public static final ResourceKey<LootTable> MASON_GIFT = createKey("gameplay/hero_of_the_village/mason_gift");
    public static final ResourceKey<LootTable> SHEPHERD_GIFT = createKey("gameplay/hero_of_the_village/shepherd_gift");
    public static final ResourceKey<LootTable> TOOLSMITH_GIFT = createKey("gameplay/hero_of_the_village/toolsmith_gift");
    public static final ResourceKey<LootTable> UNEMPLOYED_GIFT = createKey("gameplay/hero_of_the_village/unemployed_gift");
    public static final ResourceKey<LootTable> WEAPONSMITH_GIFT = createKey("gameplay/hero_of_the_village/weaponsmith_gift");
    public static final ResourceKey<LootTable> PANDA_SNEEZE = createKey("gameplay/panda_sneeze");
    public static final ResourceKey<LootTable> PIGLIN_BARTERING = createKey("gameplay/piglin_bartering");
    public static final ResourceKey<LootTable> SNIFFER_DIGGING = createKey("gameplay/sniffer_digging");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }
}
