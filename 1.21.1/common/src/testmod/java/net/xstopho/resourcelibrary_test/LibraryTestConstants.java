package net.xstopho.resourcelibrary_test;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.xstopho.resourcelibrary.modifier.LootTableModifier;
import net.xstopho.resourcelibrary.modifier.loot_tables.ChestLootTables;
import net.xstopho.resourcelibrary.rendering.item.ItemModelRenderHelper;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LibraryTestConstants {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void commonInit() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();

        LootTableModifier modifier = LootTableModifier.getInstance();
        modifier.addItems(Items.DIAMOND_BLOCK, 1f, () -> 1f, List.of(ChestLootTables.SPAWN_BONUS_CHEST));
    }

    public static void clientInit() {
        ItemModelRenderHelper.registerItemModel(ItemRegistry.TEST_IN_HAND_ITEM.get(),
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "in_hand/in_hand_item"));
    }

    public interface Join {
        void onJoin(Player player);
    }
}
