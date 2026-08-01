package net.morthen.resourcelibrary;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametest.NeoforgeGametest;
import net.morthen.resourcelibrary.gametests.LootModifierTests;
import net.morthen.resourcelibrary.gametests.RegistryTests;
import net.morthen.resourcelibrary.service.LibServices;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {
    public static final DeferredRegister<Consumer<GameTestHelper>> GAMETEST = DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, LibConstants.MOD_ID);

    public ResourceLibrary(IEventBus eventBus) {

        // loads the gametests only in dev environment
        if (LibServices.PLATFORM.isDev()) {
            GAMETEST.register(eventBus);

            // Test Setups, only needed when events are involved
            RegistryTests.setupRegistry();
            LootModifierTests.setupModifier();

            RegistryTests.init(NeoforgeGametest::registerTest);
            LootModifierTests.init(NeoforgeGametest::registerTest);
            eventBus.addListener(NeoforgeGametest::registerTests);
        }
    }
}