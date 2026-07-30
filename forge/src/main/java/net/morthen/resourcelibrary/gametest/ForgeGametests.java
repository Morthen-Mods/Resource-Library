package net.morthen.resourcelibrary.gametest;

import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.gametests.LootModifierTests;

import java.util.function.Consumer;

public class ForgeGametests {
    public static final DeferredRegister<Consumer<GameTestHelper>> GAMETESTS = DeferredRegister.create(Registries.TEST_FUNCTION, LibConstants.MOD_ID);

    private static void registerTest(String name, Consumer<GameTestHelper> consumer) {
        GAMETESTS.register(name, () -> consumer);
    }

    static {
        LootModifierTests.init(ForgeGametests::registerTest);
    }
}
