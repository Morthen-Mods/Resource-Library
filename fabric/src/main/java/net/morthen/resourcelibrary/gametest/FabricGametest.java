package net.morthen.resourcelibrary.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametests.LootModifierTests;

public class FabricGametest {
    @GameTest
    public void modifyMobLoot(GameTestHelper helper) {

        LootModifierTests.modifyMobLoot(helper);
    }

    @GameTest
    public void modifyChestLoot(GameTestHelper helper) {

        LootModifierTests.modifyChestLoot(helper);
    }

    @GameTest
    public void modifyMobDrop(GameTestHelper helper) {

        LootModifierTests.modifyMobDrop(helper);
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
