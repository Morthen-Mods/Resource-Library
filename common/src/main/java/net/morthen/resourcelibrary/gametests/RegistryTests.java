package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.gametests.objects.TestBlock;
import net.morthen.resourcelibrary.gametests.objects.TestItem;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.registration.RegistryProvider;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/*
 * Test ideas for the loader-agnostic RegistryProvider/RegistryObject system.
 * Each item below is a candidate gametest (or plain unit test where no world/helper is needed).
 *
 * Registration basics
 * - registerTestItem / registerTestBlock (existing): object is spawnable/placeable after registration.
 * - registerReturnsNonNullObject: RegistryObject#get() is never null right after register() returns.
 * - registerDoesNotThrowForUniqueIds: registering distinct ids in the same provider succeeds without exceptions.
 * - registerDuplicateIdThrowsOrOverwrites: registering the same objectId twice - assert the actual/expected
 *   behavior (exception vs overwrite), since this can differ between Fabric's Registry.register and
 *   Forge/NeoForge's DeferredRegister and should be pinned down explicitly.
 * - registerAfterRegistryFrozen: attempting to register once the underlying vanilla Registry is frozen
 *   (post bootstrap) fails the same way on every loader.
 *
 * Identity / metadata correctness
 * - getIdMatchesModIdAndObjectId: RegistryObject#getId() equals Identifier.fromNamespaceAndPath(modId, objectId).
 * - getResourceKeyMatchesRegistry: RegistryObject#getResourceKey() points at the correct vanilla Registry key
 *   and resolves back to the same object via registry.get(resourceKey).
 * - getModIdMatchesProviderModId: RegistryProvider#getModId() returns the id passed to RegistryProvider.get().
 * - registryObjectEqualsUnderlyingRegistryEntry: the object returned by get() is reference-equal (or logically
 *   equal) to the one found via BuiltInRegistries lookup by the same Identifier.
 *
 * Collection / bookkeeping
 * - getEntriesContainsAllRegisteredObjects: after N registrations, getEntries() has size N and contains each one.
 * - getEntriesIsPerProviderNotGlobal: two providers for different registries (ITEMS vs BLOCKS) don't leak
 *   entries into each other's getEntries().
 * - getEntriesReflectsRegistrationOrder (if order is a documented guarantee) or explicitly asserts it is NOT
 *   guaranteed, to prevent accidental reliance on ordering.
 *
 * Cross-registry / cross-mod isolation
 * - sameObjectIdDifferentModIds: "test_item" registered under two different modIds produces two distinct
 *   RegistryObjects with distinct Identifiers, neither clobbering the other.
 * - sameObjectIdDifferentRegistries: "test_item" registered in ITEMS and a differently-typed registry (e.g.
 *   a custom registry) does not collide.
 * - multipleProvidersSameRegistrySameModId: calling RegistryProvider.get() twice for the same modId/registry
 *   pair either returns independent providers that both work, or a shared one - document and test the choice.
 *
 * Loader-specific timing (relevant because Forge/NeoForge back this with DeferredRegister, Fabric with a
 * direct Registry.register call)
 * - registryObjectUsableImmediatelyAfterRegister: get(), getId(), getResourceKey() all work without waiting
 *   for a registry-event callback, regardless of loader.
 * - registerBeforeAndAfterCommonSetup: registration performed at mod-init time is visible by the time
 *   gametests run (sanity check that the deferred-register bus fired before world load on Forge/NeoForge).
 *
 * Error handling / edge cases
 * - registerWithNullSupplierThrowsNPE (or documented behavior).
 * - registerWithBlankOrInvalidObjectIdThrows: ids with illegal characters for an Identifier path fail fast
 *   with a clear exception instead of a cryptic one deeper in the vanilla registry code.
 * - registerObjectSupplierThrowsPropagates: an exception thrown inside objectSupplier.get() propagates out of
 *   register() rather than being swallowed, so mod authors see registration failures immediately.
 *
 * Functional smoke tests per registry type (beyond item/block already covered)
 * - registerTestEntityType / registerTestBlockEntityType / registerTestSoundEvent / registerTestMenuType, etc.,
 *   each spawned/used once to confirm the generic provider works uniformly across differently-shaped registries.
 */
public class RegistryTests {
    static final HashMap<String, RegistryObject<?>> registry = new HashMap<>();

    private static final RegistryProvider<Item> ITEMS = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.ITEM);
    private static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.BLOCK);

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("register_test_item", RegistryTests::registerTestItem);
        consumer.accept("register_test_block", RegistryTests::registerTestBlock);
    }

    public static void setupRegistrySystem() {
        registry.put("test_item", ITEMS.register("test_item", () -> new TestItem("test_item")));
        registry.put("test_block", BLOCKS.register("test_block", () -> new TestBlock("test_block")));
    }

    public static void registerTestItem(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("test_item");

        helper.spawnItem(item.get(), BlockPos.ZERO);
        helper.assertItemEntityPresent(item.get());
        helper.succeed();
    }

    public static void registerTestBlock(GameTestHelper helper) {
        RegistryObject<Block> block = (RegistryObject<Block>) registry.get("test_block");

        helper.setBlock(BlockPos.ZERO, block.get());
        helper.assertBlockPresent(block.get(),  BlockPos.ZERO);
        helper.succeed();
    }
}
