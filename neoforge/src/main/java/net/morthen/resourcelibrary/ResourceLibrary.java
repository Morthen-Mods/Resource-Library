package net.morthen.resourcelibrary;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametest.NeoforgeGametest;
import net.morthen.resourcelibrary.gametests.LootTableModifierTests;
import net.morthen.resourcelibrary.service.CoreServices;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {
    public static final DeferredRegister<Consumer<GameTestHelper>> GAMETEST = DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, LibConstants.MOD_ID);

    public ResourceLibrary(IEventBus eventBus) {

        // loads the gametests only in dev environment
        if (CoreServices.PLATFORM.isDev()) {
            GAMETEST.register(eventBus);

            // Test Setups, only needed when events are involved
            LootTableModifierTests.setupLootModifications();

            LootTableModifierTests.init(NeoforgeGametest::registerTest);
            eventBus.addListener(NeoforgeGametest::registerTests);
        }
    }
}