package net.morthen.resourcelibrary_test;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.Blocks;
import net.morthen.resourcelibrary.event.LootTableModifierCallback;
import net.morthen.resourcelibrary.event.ResourceEvent;
import net.morthen.resourcelibrary.modifier.LootTableModifier;
import net.morthen.resourcelibrary.modifier.loot_tables.ChestLootTables;
import net.morthen.resourcelibrary.modifier.loot_tables.EntityLootTables;
import net.morthen.resourcelibrary.registration.ResourcePackRegistry;
import net.morthen.resourcelibrary_test.registries.BlockRegistry;
import net.morthen.resourcelibrary_test.registries.CreativeTabRegistry;
import net.morthen.resourcelibrary_test.registries.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestConstants {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    private static final LootTableModifier modifier = LootTableModifier.getInstance();

    public static final ResourceEvent<Join> TEST_EVENT = new ResourceEvent<>(
            joins -> player -> joins.forEach(join -> join.onJoin(player)));

    public interface Join {
        void onJoin(Player player);
    }

    public static void commonInit() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();

        ResourcePackRegistry resourcePackRegistry = ResourcePackRegistry.getInstance(MOD_ID);
        resourcePackRegistry.register("resource_nether_ores_x32", "Resource Nether Ores x32");
        resourcePackRegistry.register(Identifier.fromNamespaceAndPath(MOD_ID, "resource_nether_ores_x64"), "Resource Nether Ores x64");

        TEST_EVENT.register(player -> player.sendSystemMessage(Component.literal("Simple Event Test")));
        TEST_EVENT.register(player -> player.sendSystemMessage(Component.literal("Simple Test, to test if multiple registered Events get triggered correctly")));
        TEST_EVENT.register(player -> player.sendOverlayMessage(Component.literal("Test Actionbar Message")));


        modifier.addItems(Blocks.DIAMOND_BLOCK, 1f, () ->1f, List.of(ChestLootTables.SPAWN_BONUS_CHEST, EntityLootTables.ZOMBIE));
        LootTableModifierCallback.MODIFY.register(stack -> {
            if (stack.getItem() == Blocks.DIAMOND_BLOCK.asItem()) {
                stack.set(DataComponents.LORE, new ItemLore(List.of(
                        Component.literal("This is a Test!")
                )));
            }
        });
    }
}
