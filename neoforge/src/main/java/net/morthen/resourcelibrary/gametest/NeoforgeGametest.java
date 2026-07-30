package net.morthen.resourcelibrary.gametest;

import com.google.common.collect.Maps;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.ResourceLibrary;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.function.Consumer;

public class NeoforgeGametest {
    private static final Map<String, ResourceKey<Consumer<GameTestHelper>>> TEST_FUNCTION_MAP = Maps.newHashMap();

    public static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "default"));

        for (Map.Entry<String, ResourceKey<Consumer<GameTestHelper>>> entry : TEST_FUNCTION_MAP.entrySet()) {
            event.registerTest(
                    Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, entry.getKey()),
                    new FunctionGameTestInstance(entry.getValue(),
                            new TestData<>(env, Identifier.withDefaultNamespace("empty"), 100, 0, true)));
        }
    }

    public static void registerTest(String testId, Consumer<GameTestHelper> consumer) {
        DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> test = ResourceLibrary.GAMETEST.register(testId, () -> consumer);
        TEST_FUNCTION_MAP.putIfAbsent(testId, test.getKey());
    }
}
