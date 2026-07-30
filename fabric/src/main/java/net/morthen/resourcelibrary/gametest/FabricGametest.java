package net.morthen.resourcelibrary.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametests.LootModifierTests;
import net.morthen.resourcelibrary.gametests.RegistryTests;

public class FabricGametest {

    /////////////////////////////////////////////////
    ///              Registry Tests               ///
    /////////////////////////////////////////////////
    @GameTest
    public void registerTestItem(GameTestHelper helper) {

        RegistryTests.registerTestItem(helper);
    }

    @GameTest
    public void registerTestBlock(GameTestHelper helper) {

        RegistryTests.registerTestBlock(helper);
    }


    /////////////////////////////////////////////////
    ///           Mob Loot Modifications          ///
    /////////////////////////////////////////////////
    @GameTest
    public void addMobLoot(GameTestHelper helper) {

        LootModifierTests.addMobLoot(helper);
    }

    @GameTest
    public void addMobLootWithRange(GameTestHelper helper) {
        LootModifierTests.addMobLootWithRange(helper);
    }

    @GameTest
    public void modifyMobDrop(GameTestHelper helper) {

        LootModifierTests.modifyMobDrop(helper);
    }

    /////////////////////////////////////////////////
    ///          Chest Loot Modifications         ///
    /////////////////////////////////////////////////
    @GameTest
    public void addChestLoot(GameTestHelper helper) {

        LootModifierTests.addChestLoot(helper);
    }


    @GameTest
    public void modifyChestItem(GameTestHelper helper) {

        LootModifierTests.modifyChestItem(helper);
    }

    @GameTest
    public void modifyChestItemAmount(GameTestHelper helper) {

        LootModifierTests.modifyChestItemAmount(helper);
    }
}
