package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
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

public class RegistryTests {
    static final HashMap<String, RegistryObject<?>> registry = new HashMap<>();

    private static final RegistryProvider<Item> ITEMS = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.ITEM);
    private static final RegistryProvider<Block> BLOCKS = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.BLOCK);
    // Second provider for the same modId/registry pair, used by multipleProvidersSameRegistrySameModId.
    private static final RegistryProvider<Item> SECOND_ITEMS_PROVIDER = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.ITEM);

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("register_test_item", RegistryTests::registerTestItem);
        consumer.accept("register_test_block", RegistryTests::registerTestBlock);
        consumer.accept("register_returns_non_null_object", RegistryTests::registerReturnsNonNullObject);
        consumer.accept("register_does_not_throw_for_unique_ids", RegistryTests::registerDoesNotThrowForUniqueIds);
        consumer.accept("registry_object_usable_immediately_after_register", RegistryTests::registryObjectUsableImmediatelyAfterRegister);

        consumer.accept("get_id_matches_mod_id_and_object_id", RegistryTests::getIdMatchesModIdAndObjectId);
        consumer.accept("get_resource_key_matches_registry", RegistryTests::getResourceKeyMatchesRegistry);
        consumer.accept("get_mod_id_matches_provider_mod_id", RegistryTests::getModIdMatchesProviderModId);
        consumer.accept("registry_object_equals_underlying_registry_entry", RegistryTests::registryObjectEqualsUnderlyingRegistryEntry);

        consumer.accept("get_entries_contains_all_registered_objects", RegistryTests::getEntriesContainsAllRegisteredObjects);
        consumer.accept("get_entries_is_per_provider_not_global", RegistryTests::getEntriesIsPerProviderNotGlobal);

        consumer.accept("same_object_id_different_registries", RegistryTests::sameObjectIdDifferentRegistries);
        consumer.accept("multiple_providers_same_registry_same_mod_id", RegistryTests::multipleProvidersSameRegistrySameModId);
    }

    public static void setupRegistry() {
        // registerTestItem / registerTestBlock
        registry.put("test_item", ITEMS.register("test_item", () -> new TestItem("test_item")));
        registry.put("test_block", BLOCKS.register("test_block", () -> new TestBlock("test_block")));

        // registerReturnsNonNullObject / getIdMatchesModIdAndObjectId / getResourceKeyMatchesRegistry /
        // registryObjectEqualsUnderlyingRegistryEntry / registryObjectUsableImmediatelyAfterRegister
        RegistryObject<Item> metadataItem = ITEMS.register("metadata_item", () -> new TestItem("metadata_item"));
        registry.put("metadata_item", metadataItem);
        // getId()/getResourceKey() are safe to call here, before the world/registries finish loading:
        // on every loader the Identifier/ResourceKey is fixed at register() time. get() is NOT safe
        // here - on Forge/NeoForge the wrapped value is only filled in once the registry-bake event
        // fires later, so it is exercised from the test body instead, once the world has loaded.
        if (metadataItem.getId() == null || metadataItem.getResourceKey() == null) {
            throw new IllegalStateException("RegistryObject#getId()/getResourceKey() were not usable immediately after register()");
        }

        // registerDoesNotThrowForUniqueIds / getEntriesContainsAllRegisteredObjects
        registry.put("unique_id_one", ITEMS.register("unique_id_one", () -> new TestItem("unique_id_one")));
        registry.put("unique_id_two", ITEMS.register("unique_id_two", () -> new TestItem("unique_id_two")));

        // getEntriesIsPerProviderNotGlobal
        registry.put("provider_isolation_block", BLOCKS.register("provider_isolation_block", () -> new TestBlock("provider_isolation_block")));

        // sameObjectIdDifferentRegistries
        registry.put("shared_id_item", ITEMS.register("shared_id", () -> new TestItem("shared_id")));
        registry.put("shared_id_block", BLOCKS.register("shared_id", () -> new TestBlock("shared_id")));

        // multipleProvidersSameRegistrySameModId
        registry.put("second_provider_item", SECOND_ITEMS_PROVIDER.register("second_provider_item", () -> new TestItem("second_provider_item")));
    }

    /////////////////////////////////////////////////
    ///              Registration basics           ///
    /////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public static void registerTestItem(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("test_item");

        helper.spawnItem(item.get(), BlockPos.ZERO);
        helper.assertItemEntityPresent(item.get());
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void registerTestBlock(GameTestHelper helper) {
        RegistryObject<Block> block = (RegistryObject<Block>) registry.get("test_block");

        helper.setBlock(BlockPos.ZERO, block.get());
        helper.assertBlockPresent(block.get(), BlockPos.ZERO);
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void registerReturnsNonNullObject(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("metadata_item");

        helper.assertTrue(item.get() != null, "RegistryObject#get() returned null right after register() returned");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void registerDoesNotThrowForUniqueIds(GameTestHelper helper) {
        RegistryObject<Item> one = (RegistryObject<Item>) registry.get("unique_id_one");
        RegistryObject<Item> two = (RegistryObject<Item>) registry.get("unique_id_two");

        helper.assertTrue(one.get() != null && two.get() != null,
                "Registering distinct ids on the same provider did not both succeed");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void registryObjectUsableImmediatelyAfterRegister(GameTestHelper helper) {
        // setupRegistry() already called getId()/getResourceKey() on this object the moment it was
        // registered (before the world loaded) and would have thrown if either weren't ready yet.
        // get() is checked here instead, since Forge/NeoForge only resolve it once the registry-bake
        // event fires - by the time this gametest runs, that has already happened.
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("metadata_item");

        helper.assertTrue(item.get() != null && item.getId() != null && item.getResourceKey() != null,
                "RegistryObject is not fully usable by the time the game test world has loaded");
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///          Identity / metadata correctness   ///
    /////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public static void getIdMatchesModIdAndObjectId(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("metadata_item");

        helper.assertValueEqual(item.getId(), Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "metadata_item"), "RegistryObject#getId()");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void getResourceKeyMatchesRegistry(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("metadata_item");

        helper.assertTrue(BuiltInRegistries.ITEM.getValue(item.getResourceKey()) == item.get(),
                "RegistryObject#getResourceKey() does not resolve back to the same object via the vanilla registry");
        helper.succeed();
    }

    public static void getModIdMatchesProviderModId(GameTestHelper helper) {
        helper.assertValueEqual(ITEMS.getModId(), LibConstants.MOD_ID, "RegistryProvider#getModId()");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void registryObjectEqualsUnderlyingRegistryEntry(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("metadata_item");

        helper.assertTrue(BuiltInRegistries.ITEM.getValue(item.getId()) == item.get(),
                "The registered object is not the same instance found via a BuiltInRegistries lookup by its Identifier");
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///            Collection / bookkeeping        ///
    /////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public static void getEntriesContainsAllRegisteredObjects(GameTestHelper helper) {
        RegistryObject<Item> one = (RegistryObject<Item>) registry.get("unique_id_one");
        RegistryObject<Item> two = (RegistryObject<Item>) registry.get("unique_id_two");

        helper.assertTrue(ITEMS.getEntries().contains(one) && ITEMS.getEntries().contains(two),
                "RegistryProvider#getEntries() is missing objects that were registered on it");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void getEntriesIsPerProviderNotGlobal(GameTestHelper helper) {
        RegistryObject<Block> block = (RegistryObject<Block>) registry.get("provider_isolation_block");

        boolean leaked = ITEMS.getEntries().stream().anyMatch(entry -> entry.getId().equals(block.getId()));
        helper.assertFalse(leaked, "A Block registered on the BLOCKS provider leaked into the ITEMS provider's getEntries()");
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///       Cross-registry / provider isolation  ///
    /////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public static void sameObjectIdDifferentRegistries(GameTestHelper helper) {
        RegistryObject<Item> item = (RegistryObject<Item>) registry.get("shared_id_item");
        RegistryObject<Block> block = (RegistryObject<Block>) registry.get("shared_id_block");

        helper.assertValueEqual(item.getId(), block.getId(), "the same objectId in different registries should still produce the same Identifier");
        helper.assertTrue(item.get() != null && block.get() != null,
                "Registering the same objectId in two different registries should not collide");
        helper.succeed();
    }

    @SuppressWarnings("unchecked")
    public static void multipleProvidersSameRegistrySameModId(GameTestHelper helper) {
        RegistryObject<Item> viaSecondProvider = (RegistryObject<Item>) registry.get("second_provider_item");

        helper.assertTrue(viaSecondProvider.get() != null, "A second RegistryProvider for the same modId/registry pair failed to register");
        helper.assertTrue(BuiltInRegistries.ITEM.getValue(viaSecondProvider.getId()) == viaSecondProvider.get(),
                "The object registered through a second RegistryProvider instance is not visible in the shared vanilla registry");
        helper.succeed();
    }
}
