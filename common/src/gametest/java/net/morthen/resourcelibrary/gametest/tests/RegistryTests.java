package net.morthen.resourcelibrary.gametest.tests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.morthen.resourcelibrary.gametest.tests.objects.TestBlock;
import net.morthen.resourcelibrary.gametest.tests.objects.TestItem;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.registration.RegistryProvider;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RegistryTests {
    private static final RegistryProvider<Item> ITEMS = RegistryProvider.create(LibConstants.MOD_ID, Registries.ITEM);
    // Created from the registry instance instead of its key, to cover both create(...) overloads
    private static final RegistryProvider<Block> BLOCKS = RegistryProvider.create(LibConstants.MOD_ID, BuiltInRegistries.BLOCK);
    // Second provider for the same modId/registry pair, used by multipleProvidersSameRegistrySameModId.
    private static final RegistryProvider<Item> SECOND_ITEMS_PROVIDER = RegistryProvider.create(LibConstants.MOD_ID, Registries.ITEM);

    private static RegistryObject<Item, TestItem> testItem;
    private static RegistryObject<Block, TestBlock> testBlock;
    private static RegistryObject<Item, TestItem> metadataItem;
    private static RegistryObject<Item, TestItem> uniqueIdOne;
    private static RegistryObject<Item, TestItem> uniqueIdTwo;
    private static RegistryObject<Block, TestBlock> providerIsolationBlock;
    private static RegistryObject<Item, TestItem> sharedIdItem;
    private static RegistryObject<Block, TestBlock> sharedIdBlock;
    private static RegistryObject<Item, TestItem> secondProviderItem;

    private static ResourceKey<Item> keyFactoryReceivedKey;
    private static RegistryObject<Item, TestItem> keyFactoryItem;

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("register_test_item", RegistryTests::registerTestItem);
        consumer.accept("register_test_block", RegistryTests::registerTestBlock);
        consumer.accept("register_returns_non_null_object", RegistryTests::registerReturnsNonNullObject);
        consumer.accept("register_does_not_throw_for_unique_ids", RegistryTests::registerDoesNotThrowForUniqueIds);
        consumer.accept("registry_object_usable_immediately_after_register", RegistryTests::registryObjectUsableImmediatelyAfterRegister);
        consumer.accept("register_with_key_factory_receives_registry_key", RegistryTests::registerWithKeyFactoryReceivesRegistryKey);

        consumer.accept("get_id_matches_mod_id_and_object_id", RegistryTests::getIdMatchesModIdAndObjectId);
        consumer.accept("get_resource_key_matches_registry", RegistryTests::getResourceKeyMatchesRegistry);
        consumer.accept("get_mod_id_matches_provider_mod_id", RegistryTests::getModIdMatchesProviderModId);
        consumer.accept("registry_object_equals_underlying_registry_entry", RegistryTests::registryObjectEqualsUnderlyingRegistryEntry);
        consumer.accept("get_holder_resolves_registered_object", RegistryTests::getHolderResolvesRegisteredObject);
        consumer.accept("is_bound_after_registration", RegistryTests::isBoundAfterRegistration);
        consumer.accept("registry_object_to_string_contains_id", RegistryTests::registryObjectToStringContainsId);

        consumer.accept("get_entries_contains_all_registered_objects", RegistryTests::getEntriesContainsAllRegisteredObjects);
        consumer.accept("get_entries_is_per_provider_not_global", RegistryTests::getEntriesIsPerProviderNotGlobal);
        consumer.accept("get_entries_is_unmodifiable", RegistryTests::getEntriesIsUnmodifiable);

        consumer.accept("same_object_id_different_registries", RegistryTests::sameObjectIdDifferentRegistries);
        consumer.accept("multiple_providers_same_registry_same_mod_id", RegistryTests::multipleProvidersSameRegistrySameModId);
    }

    public static void setupRegistry() {
        // registerTestItem / registerTestBlock
        testItem = ITEMS.register("test_item", TestItem::new);
        testBlock = BLOCKS.register("test_block", TestBlock::new);

        // registerReturnsNonNullObject / getIdMatchesModIdAndObjectId / getResourceKeyMatchesRegistry /
        // registryObjectEqualsUnderlyingRegistryEntry / registryObjectUsableImmediatelyAfterRegister
        metadataItem = ITEMS.register("metadata_item", TestItem::new);
        // getId()/getResourceKey() are safe to call here, before the world/registries finish loading:
        // on every loader the Identifier/ResourceKey is fixed at register() time. get() is NOT safe
        // here - on Forge/NeoForge the object is only added once the registry event fires later,
        // so it is exercised from the test body instead, once the world has loaded.
        if (metadataItem.getId() == null || metadataItem.getResourceKey() == null) {
            throw new IllegalStateException("RegistryObject#getId()/getResourceKey() were not usable immediately after register()");
        }

        // registerDoesNotThrowForUniqueIds / getEntriesContainsAllRegisteredObjects (Supplier overload)
        uniqueIdOne = ITEMS.register("unique_id_one", () -> new TestItem(itemKey("unique_id_one")));
        uniqueIdTwo = ITEMS.register("unique_id_two", () -> new TestItem(itemKey("unique_id_two")));

        // registerWithKeyFactoryReceivesRegistryKey
        keyFactoryItem = ITEMS.register("key_factory_item", key -> {
            keyFactoryReceivedKey = key;
            return new TestItem(key);
        });

        // getEntriesIsPerProviderNotGlobal
        providerIsolationBlock = BLOCKS.register("provider_isolation_block", TestBlock::new);

        // sameObjectIdDifferentRegistries
        sharedIdItem = ITEMS.register("shared_id", TestItem::new);
        sharedIdBlock = BLOCKS.register("shared_id", TestBlock::new);

        // multipleProvidersSameRegistrySameModId
        secondProviderItem = SECOND_ITEMS_PROVIDER.register("second_provider_item", TestItem::new);
    }

    private static ResourceKey<Item> itemKey(String id) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, id));
    }

    /////////////////////////////////////////////////
    ///              Registration basics           ///
    /////////////////////////////////////////////////

    public static void registerTestItem(GameTestHelper helper) {
        helper.spawnItem(testItem.get(), BlockPos.ZERO);
        helper.assertItemEntityPresent(testItem.get());
        helper.succeed();
    }

    public static void registerTestBlock(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, testBlock.get());
        helper.assertBlockPresent(testBlock.get(), BlockPos.ZERO);
        helper.succeed();
    }

    public static void registerReturnsNonNullObject(GameTestHelper helper) {
        helper.assertTrue(metadataItem.get() != null, "RegistryObject#get() returned null right after register() returned");
        helper.succeed();
    }

    public static void registerDoesNotThrowForUniqueIds(GameTestHelper helper) {
        helper.assertTrue(uniqueIdOne.get() != null && uniqueIdTwo.get() != null,
                "Registering distinct ids on the same provider did not both succeed");
        helper.succeed();
    }

    public static void registryObjectUsableImmediatelyAfterRegister(GameTestHelper helper) {
        // setupRegistry() already called getId()/getResourceKey() on this object the moment it was
        // registered (before the world loaded) and would have thrown if either weren't ready yet.
        // get() is checked here instead, since Forge/NeoForge only register it once the registry
        // event fires - by the time this gametest runs, that has already happened.
        helper.assertTrue(metadataItem.get() != null && metadataItem.getId() != null && metadataItem.getResourceKey() != null,
                "RegistryObject is not fully usable by the time the game test world has loaded");
        helper.succeed();
    }

    public static void registerWithKeyFactoryReceivesRegistryKey(GameTestHelper helper) {
        helper.assertValueEqual(keyFactoryReceivedKey, keyFactoryItem.getResourceKey(), "key passed to the factory");
        helper.assertValueEqual(BuiltInRegistries.ITEM.getResourceKey(keyFactoryItem.get()).orElse(null), keyFactoryItem.getResourceKey(),
                "registry key of the registered object");
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///          Identity / metadata correctness   ///
    /////////////////////////////////////////////////

    public static void getIdMatchesModIdAndObjectId(GameTestHelper helper) {
        helper.assertValueEqual(metadataItem.getId(), Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "metadata_item"), "RegistryObject#getId()");
        helper.succeed();
    }

    public static void getResourceKeyMatchesRegistry(GameTestHelper helper) {
        // RegistryObject<Item, TestItem> has a ResourceKey<Item>, so it can be used with the item registry directly
        helper.assertTrue(BuiltInRegistries.ITEM.getValue(metadataItem.getResourceKey()) == metadataItem.get(),
                "RegistryObject#getResourceKey() does not resolve back to the same object via the vanilla registry");
        helper.succeed();
    }

    public static void getModIdMatchesProviderModId(GameTestHelper helper) {
        helper.assertValueEqual(ITEMS.getModId(), LibConstants.MOD_ID, "RegistryProvider#getModId()");
        helper.succeed();
    }

    public static void registryObjectEqualsUnderlyingRegistryEntry(GameTestHelper helper) {
        helper.assertTrue(BuiltInRegistries.ITEM.getValue(metadataItem.getId()) == metadataItem.get(),
                "The registered object is not the same instance found via a BuiltInRegistries lookup by its Identifier");
        helper.succeed();
    }

    public static void getHolderResolvesRegisteredObject(GameTestHelper helper) {
        Holder<Item> holder = metadataItem.getHolder();

        helper.assertTrue(holder.isBound(), "RegistryObject#getHolder() returned an unbound holder");
        helper.assertTrue(holder.value() == metadataItem.get(), "RegistryObject#getHolder() does not hold the registered object");
        helper.assertTrue(holder.is(metadataItem.getResourceKey()), "RegistryObject#getHolder() has a different key");
        helper.assertTrue(holder == BuiltInRegistries.ITEM.wrapAsHolder(metadataItem.get()), "RegistryObject#getHolder() is not the registry's holder");
        helper.succeed();
    }

    public static void isBoundAfterRegistration(GameTestHelper helper) {
        helper.assertTrue(metadataItem.isBound(), "RegistryObject#isBound() is false although the object is registered");
        helper.succeed();
    }

    public static void registryObjectToStringContainsId(GameTestHelper helper) {
        helper.assertTrue(metadataItem.toString().contains(metadataItem.getId().toString()),
                "RegistryObject#toString() does not contain the id: " + metadataItem);
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///            Collection / bookkeeping        ///
    /////////////////////////////////////////////////

    public static void getEntriesContainsAllRegisteredObjects(GameTestHelper helper) {
        helper.assertTrue(ITEMS.getEntries().contains(uniqueIdOne) && ITEMS.getEntries().contains(uniqueIdTwo),
                "RegistryProvider#getEntries() is missing objects that were registered on it");
        helper.succeed();
    }

    public static void getEntriesIsPerProviderNotGlobal(GameTestHelper helper) {
        boolean leaked = ITEMS.getEntries().stream().anyMatch(entry -> entry.getId().equals(providerIsolationBlock.getId()));
        helper.assertFalse(leaked, "A Block registered on the BLOCKS provider leaked into the ITEMS provider's getEntries()");
        helper.succeed();
    }

    public static void getEntriesIsUnmodifiable(GameTestHelper helper) {
        try {
            ITEMS.getEntries().clear();
        } catch (UnsupportedOperationException e) {
            helper.assertTrue(ITEMS.getEntries().contains(testItem), "RegistryProvider#getEntries() lost entries");
            helper.succeed();
            return;
        }
        helper.fail("RegistryProvider#getEntries() can be modified from outside");
    }

    /////////////////////////////////////////////////
    ///       Cross-registry / provider isolation  ///
    /////////////////////////////////////////////////

    public static void sameObjectIdDifferentRegistries(GameTestHelper helper) {
        helper.assertValueEqual(sharedIdItem.getId(), sharedIdBlock.getId(), "the same objectId in different registries should still produce the same Identifier");
        helper.assertFalse(sharedIdItem.equals(sharedIdBlock), "RegistryObjects of different registries must not be equal");
        helper.assertTrue(sharedIdItem.get() != null && sharedIdBlock.get() != null,
                "Registering the same objectId in two different registries should not collide");
        helper.succeed();
    }

    public static void multipleProvidersSameRegistrySameModId(GameTestHelper helper) {
        helper.assertTrue(secondProviderItem.get() != null, "A second RegistryProvider for the same modId/registry pair failed to register");
        helper.assertTrue(BuiltInRegistries.ITEM.getValue(secondProviderItem.getId()) == secondProviderItem.get(),
                "The object registered through a second RegistryProvider instance is not visible in the shared vanilla registry");
        helper.succeed();
    }
}
