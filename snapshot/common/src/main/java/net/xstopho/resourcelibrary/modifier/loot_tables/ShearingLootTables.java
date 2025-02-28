package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ShearingLootTables {
    public static final ResourceKey<LootTable> BOGGED = createKey("shearing/bogged");
    public static final ResourceKey<LootTable> MOOSHROOM = createKey("shearing/mooshroom");
    public static final ResourceKey<LootTable> MOOSHROOM_BROWN = createKey("shearing/mooshroom/brown");
    public static final ResourceKey<LootTable> MOOSHROOM_RED = createKey("shearing/mooshroom/red");
    public static final ResourceKey<LootTable> SHEEP = createKey("shearing/sheep");
    public static final ResourceKey<LootTable> SHEEP_BLACK = createKey("shearing/sheep/black");
    public static final ResourceKey<LootTable> SHEEP_BLUE = createKey("shearing/sheep/blue");
    public static final ResourceKey<LootTable> SHEEP_BROWN = createKey("shearing/sheep/brown");
    public static final ResourceKey<LootTable> SHEEP_CYAN = createKey("shearing/sheep/cyan");
    public static final ResourceKey<LootTable> SHEEP_GRAY = createKey("shearing/sheep/gray");
    public static final ResourceKey<LootTable> SHEEP_GREEN = createKey("shearing/sheep/green");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_BLUE = createKey("shearing/sheep/light_blue");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_GRAY = createKey("shearing/sheep/light_gray");
    public static final ResourceKey<LootTable> SHEEP_LIME = createKey("shearing/sheep/lime");
    public static final ResourceKey<LootTable> SHEEP_MAGENTA = createKey("shearing/sheep/magenta");
    public static final ResourceKey<LootTable> SHEEP_ORANGE = createKey("shearing/sheep/orange");
    public static final ResourceKey<LootTable> SHEEP_PINK = createKey("shearing/sheep/pink");
    public static final ResourceKey<LootTable> SHEEP_PURPLE = createKey("shearing/sheep/purple");
    public static final ResourceKey<LootTable> SHEEP_RED = createKey("shearing/sheep/red");
    public static final ResourceKey<LootTable> SHEEP_WHITE = createKey("shearing/sheep/white");
    public static final ResourceKey<LootTable> SHEEP_YELLOW = createKey("shearing/sheep/yellow");
    public static final ResourceKey<LootTable> SNOW_GOLEM = createKey("shearing/snow_golem");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(id));
    }
}
