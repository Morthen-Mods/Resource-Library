package net.xstopho.resourcelibrary_test;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.xstopho.resourcelibrary.event.ResourceEvent;
import net.xstopho.resourcelibrary.registration.ResourcePackRegistry;
import net.xstopho.resourcelibrary.rendering.ItemModelRenderer;
import net.xstopho.resourcelibrary_test.modifier.TestLootModifier;
import net.xstopho.resourcelibrary_test.registries.BlockRegistry;
import net.xstopho.resourcelibrary_test.registries.CreativeTabRegistry;
import net.xstopho.resourcelibrary_test.registries.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConstants {

    public static final String MOD_ID = "resourcelibrary_test";
    public static final String MOD_NAME = "Resource Library Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final ResourceEvent<Join> TEST_EVENT = new ResourceEvent<>(
            joins -> player -> joins.forEach(join -> join.onJoin(player)));

    public interface Join {
        void onJoin(Player player);
    }

    public static void commonInit() {
        BlockRegistry.init();
        ItemRegistry.init();

        CreativeTabRegistry.init();

        TestLootModifier.init();
        ResourcePackRegistry resourcePackRegistry = ResourcePackRegistry.getInstance(MOD_ID);
        resourcePackRegistry.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, "resource_nether_ores_x32"), "Resource Nether Ores x32");
        resourcePackRegistry.register(ResourceLocation.fromNamespaceAndPath(MOD_ID, "resource_nether_ores_x64"), "Resource Nether Ores x64");

        TEST_EVENT.register(player -> player.displayClientMessage(Component.literal("Simple Event Test"), false));
        TEST_EVENT.register(player -> player.displayClientMessage(Component.literal("Simple Test, to test if multiple registered Events get triggered correctly"), false));
        TEST_EVENT.register(player -> player.displayClientMessage(Component.literal("Test Actionbar Message"), true));
    }

    public static void clientInit() {
        ItemModelRenderer renderer = new ItemModelRenderer(MOD_ID);
        renderer.registerInHandModel(ItemRegistry.TEST_ITEM.get(), "test_item");
        renderer.registerGroundModel(ItemRegistry.TEST_ITEM.get(), "test_item");
        renderer.registerGuiModel(ItemRegistry.TEST_ITEM.get(), "test_item");
        renderer.registerFixedModel(ItemRegistry.TEST_ITEM.get(), "test_item");
    }
}
