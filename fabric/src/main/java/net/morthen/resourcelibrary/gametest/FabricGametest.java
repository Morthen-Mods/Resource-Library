package net.morthen.resourcelibrary.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.morthen.resourcelibrary.gametests.LootTableModifierTests;

public class FabricGametest {
    @GameTest
    public void modifyMobLoot(GameTestHelper helper) {
        LootTableModifierTests.modifyMobLoot(helper);
    }

    @GameTest
    public void modifyChestLoot(GameTestHelper helper) {
        LootTableModifierTests.modifyChestLoot(helper);
    }

    @GameTest
    public void modifyChestItem(GameTestHelper helper) {
        LootTableModifierTests.modifyChestItem(helper);
    }
}
