package net.morthen.resourcelibrary.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametest.tests.LootModifierTests;
import net.morthen.resourcelibrary.gametest.tests.RecipeRemainderTest;
import net.morthen.resourcelibrary.gametest.tests.RegistryTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GametestConstants {
    public static final String MOD_ID = "resourcelibrary_gametest";
    public static final String MOD_NAME = "Resource Library Gametest";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void commonInit() {
        LootModifierTests.setupModifier();
        RecipeRemainderTest.setupRemainder();
        RegistryTests.setupRegistry();
    }

    public static void initTests(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        LootModifierTests.init(consumer);
        RecipeRemainderTest.init(consumer);
        RegistryTests.init(consumer);
    }
}
