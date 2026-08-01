package net.morthen.resourcelibrary.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametests.LootModifierTests;
import net.morthen.resourcelibrary.gametests.RegistryTests;

public class FabricGametest {

    /////////////////////////////////////////////////
    ///              Registration basics           ///
    /////////////////////////////////////////////////
    @GameTest
    public void registerTestItem(GameTestHelper helper) {
        RegistryTests.registerTestItem(helper);
    }

    @GameTest
    public void registerTestBlock(GameTestHelper helper) {
        RegistryTests.registerTestBlock(helper);
    }

    @GameTest
    public void registerReturnsNonNullObject(GameTestHelper helper) {
        RegistryTests.registerReturnsNonNullObject(helper);
    }

    @GameTest
    public void registerDoesNotThrowForUniqueIds(GameTestHelper helper) {
        RegistryTests.registerDoesNotThrowForUniqueIds(helper);
    }

    @GameTest
    public void registryObjectUsableImmediatelyAfterRegister(GameTestHelper helper) {
        RegistryTests.registryObjectUsableImmediatelyAfterRegister(helper);
    }

    /////////////////////////////////////////////////
    ///          Identity / metadata correctness   ///
    /////////////////////////////////////////////////
    @GameTest
    public void getIdMatchesModIdAndObjectId(GameTestHelper helper) {
        RegistryTests.getIdMatchesModIdAndObjectId(helper);
    }

    @GameTest
    public void getResourceKeyMatchesRegistry(GameTestHelper helper) {
        RegistryTests.getResourceKeyMatchesRegistry(helper);
    }

    @GameTest
    public void getModIdMatchesProviderModId(GameTestHelper helper) {
        RegistryTests.getModIdMatchesProviderModId(helper);
    }

    @GameTest
    public void registryObjectEqualsUnderlyingRegistryEntry(GameTestHelper helper) {
        RegistryTests.registryObjectEqualsUnderlyingRegistryEntry(helper);
    }

    /////////////////////////////////////////////////
    ///            Collection / bookkeeping        ///
    /////////////////////////////////////////////////
    @GameTest
    public void getEntriesContainsAllRegisteredObjects(GameTestHelper helper) {
        RegistryTests.getEntriesContainsAllRegisteredObjects(helper);
    }

    @GameTest
    public void getEntriesIsPerProviderNotGlobal(GameTestHelper helper) {
        RegistryTests.getEntriesIsPerProviderNotGlobal(helper);
    }

    /////////////////////////////////////////////////
    ///       Cross-registry / provider isolation  ///
    /////////////////////////////////////////////////
    @GameTest
    public void sameObjectIdDifferentRegistries(GameTestHelper helper) {
        RegistryTests.sameObjectIdDifferentRegistries(helper);
    }

    @GameTest
    public void multipleProvidersSameRegistrySameModId(GameTestHelper helper) {
        RegistryTests.multipleProvidersSameRegistrySameModId(helper);
    }

    /////////////////////////////////////////////////
    ///     LootTableModifier - addItem/addBlock  ///
    /////////////////////////////////////////////////
    @GameTest
    public void addItemWithChanceZeroNeverDrops(GameTestHelper helper) {
        LootModifierTests.addItemWithChanceZeroNeverDrops(helper);
    }

    @GameTest
    public void addItemWithChanceOneAlwaysDrops(GameTestHelper helper) {
        LootModifierTests.addItemWithChanceOneAlwaysDrops(helper);
    }

    @GameTest
    public void addItemSingleAmountOverload(GameTestHelper helper) {
        LootModifierTests.addItemSingleAmountOverload(helper);
    }

    @GameTest
    public void addBlockUsesItemFormOfBlock(GameTestHelper helper) {
        LootModifierTests.addBlockUsesItemFormOfBlock(helper);
    }

    /////////////////////////////////////////////////
    ///     LootTableModifier - table targeting   ///
    /////////////////////////////////////////////////
    @GameTest
    public void additionAppliesToAllListedTables(GameTestHelper helper) {
        LootModifierTests.additionAppliesToAllListedTables(helper);
    }

    @GameTest
    public void additionDoesNotAffectUnlistedTables(GameTestHelper helper) {
        LootModifierTests.additionDoesNotAffectUnlistedTables(helper);
    }

    @GameTest
    public void multipleAdditionsToSameTableAreCumulative(GameTestHelper helper) {
        LootModifierTests.multipleAdditionsToSameTableAreCumulative(helper);
    }

    @GameTest
    public void additionsDoNotRemoveVanillaLoot(GameTestHelper helper) {
        LootModifierTests.additionsDoNotRemoveVanillaLoot(helper);
    }

    @GameTest
    public void addItemOnBlockLootTable(GameTestHelper helper) {
        LootModifierTests.addItemOnBlockLootTable(helper);
    }

    /////////////////////////////////////////////////
    ///       LootDropModifier - basics            ///
    /////////////////////////////////////////////////
    @GameTest
    public void modifierIgnoresNonMatchingStacks(GameTestHelper helper) {
        LootModifierTests.modifierIgnoresNonMatchingStacks(helper);
    }

    @GameTest
    public void modifierAppliesToVanillaEntriesToo(GameTestHelper helper) {
        LootModifierTests.modifierAppliesToVanillaEntriesToo(helper);
    }

    @GameTest
    public void modifierAppliesToLootTableModifierAdditions(GameTestHelper helper) {
        LootModifierTests.modifierAppliesToLootTableModifierAdditions(helper);
    }

    @GameTest
    public void multipleModifiersOnSameTableAllRun(GameTestHelper helper) {
        LootModifierTests.multipleModifiersOnSameTableAllRun(helper);
    }

    @GameTest
    public void modifierDoesNotRunForUnlistedTable(GameTestHelper helper) {
        LootModifierTests.modifierDoesNotRunForUnlistedTable(helper);
    }

    @GameTest
    public void modifierRunsOncePerStackNotPerRoll(GameTestHelper helper) {
        LootModifierTests.modifierRunsOncePerStackNotPerRoll(helper);
    }
}
