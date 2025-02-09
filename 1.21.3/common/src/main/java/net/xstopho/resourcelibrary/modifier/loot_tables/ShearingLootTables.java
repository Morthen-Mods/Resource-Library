package net.xstopho.resourcelibrary.modifier.loot_tables;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class ShearingLootTables {
    public static final ResourceKey<LootTable> MOOSHROOM_BROWN = createKey("mooshroom/brown");
    public static final ResourceKey<LootTable> MOOSHROOM_RED = createKey("mooshroom/red");

    public static final ResourceKey<LootTable> SHEEP_BLACK = createKey("sheep/black");
    public static final ResourceKey<LootTable> SHEEP_BLUE = createKey("sheep/blue");
    public static final ResourceKey<LootTable> SHEEP_BROWN = createKey("sheep/brown");
    public static final ResourceKey<LootTable> SHEEP_CYAN = createKey("sheep/cyan");
    public static final ResourceKey<LootTable> SHEEP_GRAY = createKey("sheep/gray");
    public static final ResourceKey<LootTable> SHEEP_GREEN = createKey("sheep/green");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_BLUE = createKey("sheep/light_blue");
    public static final ResourceKey<LootTable> SHEEP_LIGHT_GRAY = createKey("sheep/light_gray");
    public static final ResourceKey<LootTable> SHEEP_LIME = createKey("sheep/lime");
    public static final ResourceKey<LootTable> SHEEP_MAGENTA = createKey("sheep/magenta");
    public static final ResourceKey<LootTable> SHEEP_ORANGE = createKey("sheep/orange");
    public static final ResourceKey<LootTable> SHEEP_PINK = createKey("sheep/pink");
    public static final ResourceKey<LootTable> SHEEP_PURPLE = createKey("sheep/purple");
    public static final ResourceKey<LootTable> SHEEP_RED = createKey("sheep/red");
    public static final ResourceKey<LootTable> SHEEP_WHITE = createKey("sheep/white");
    public static final ResourceKey<LootTable> SHEEP_YELLOW = createKey("sheep/yellow");

    public static final ResourceKey<LootTable> BOGGED = createKey("bogged");
    public static final ResourceKey<LootTable> MOOSHROOM = createKey("mooshroom");
    public static final ResourceKey<LootTable> SHEEP = createKey("sheep");
    public static final ResourceKey<LootTable> SNOW_GOLEM = createKey("snow_golem");

    public static ResourceKey<LootTable> createKey(String id) {
        return ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace("shearing/" + id));
    }
}
