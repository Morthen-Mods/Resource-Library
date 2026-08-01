package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.morthen.resourcelibrary.modifier.LootDropModifier;
import net.morthen.resourcelibrary.modifier.LootTableModifier;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootModifierTests {
    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("add_item_with_chance_zero_never_drops", LootModifierTests::addItemWithChanceZeroNeverDrops);
        consumer.accept("add_item_with_chance_one_always_drops", LootModifierTests::addItemWithChanceOneAlwaysDrops);
        consumer.accept("add_item_single_amount_overload", LootModifierTests::addItemSingleAmountOverload);
        consumer.accept("add_block_uses_item_form_of_block", LootModifierTests::addBlockUsesItemFormOfBlock);

        consumer.accept("addition_applies_to_all_listed_tables", LootModifierTests::additionAppliesToAllListedTables);
        consumer.accept("addition_does_not_affect_unlisted_tables", LootModifierTests::additionDoesNotAffectUnlistedTables);
        consumer.accept("multiple_additions_to_same_table_are_cumulative", LootModifierTests::multipleAdditionsToSameTableAreCumulative);
        consumer.accept("additions_do_not_remove_vanilla_loot", LootModifierTests::additionsDoNotRemoveVanillaLoot);
        consumer.accept("add_item_on_block_loot_table", LootModifierTests::addItemOnBlockLootTable);

        consumer.accept("modifier_ignores_non_matching_stacks", LootModifierTests::modifierIgnoresNonMatchingStacks);
        consumer.accept("modifier_applies_to_vanilla_entries_too", LootModifierTests::modifierAppliesToVanillaEntriesToo);
        consumer.accept("modifier_applies_to_loot_table_modifier_additions", LootModifierTests::modifierAppliesToLootTableModifierAdditions);
        consumer.accept("multiple_modifiers_on_same_table_all_run", LootModifierTests::multipleModifiersOnSameTableAllRun);
        consumer.accept("modifier_does_not_run_for_unlisted_table", LootModifierTests::modifierDoesNotRunForUnlistedTable);
        consumer.accept("modifier_runs_once_per_stack_not_per_roll", LootModifierTests::modifierRunsOncePerStackNotPerRoll);
    }

    public static void setupModifier() {
        LootTableModifier tableMod = LootTableModifier.getInstance();
        LootDropModifier dropMod = LootDropModifier.getInstance();

        // addItemWithChanceZeroNeverDrops
        tableMod.addItem(Items.NETHER_STAR, 1f, () -> 0f, List.of(EntityTypes.COW.getDefaultLootTable().get()));

        // addItemWithChanceOneAlwaysDrops
        tableMod.addItem(Items.EMERALD, 1f, () -> 1f, List.of(EntityTypes.SHEEP.getDefaultLootTable().get()));

        // addItemSingleAmountOverload
        tableMod.addItem(Items.QUARTZ, 3f, () -> 1f, List.of(EntityTypes.RABBIT.getDefaultLootTable().get()));

        // addBlockUsesItemFormOfBlock
        tableMod.addBlock(Blocks.EMERALD_BLOCK, 1f, () -> 1f, List.of(EntityTypes.WOLF.getDefaultLootTable().get()));

        // additionAppliesToAllListedTables
        tableMod.addItem(Items.LAPIS_LAZULI, 1f, () -> 1f,
                List.of(EntityTypes.OCELOT.getDefaultLootTable().get(), EntityTypes.CAT.getDefaultLootTable().get()));

        // additionDoesNotAffectUnlistedTables
        tableMod.addItem(Items.NETHERITE_INGOT, 1f, () -> 1f, List.of(EntityTypes.PANDA.getDefaultLootTable().get()));

        //multipleAdditionsToSameTableAreCumulative
        tableMod.addItem(Items.PRISMARINE_SHARD, 1f, () -> 1f, List.of(EntityTypes.LLAMA.getDefaultLootTable().get()));
        tableMod.addItem(Items.PRISMARINE_CRYSTALS, 1f, () -> 1f, List.of(EntityTypes.LLAMA.getDefaultLootTable().get()));

        // additionsDoNotRemoveVanillaLoot
        tableMod.addItem(Items.STICK, 1f, () -> 1f, List.of(Blocks.COAL_ORE.getLootTable().get()));

        // addItemOnBlockLootTable
        tableMod.addItem(Items.APPLE, 1f, () -> 1f, List.of(Blocks.IRON_ORE.getLootTable().get()));

        // modifierIgnoresNonMatchingStacks
        dropMod.modifyItemDrop(Blocks.GOLD_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.DIAMOND) stack.setCount(99);
        });

        // modifierAppliesToVanillaEntriesToo
        dropMod.modifyItemDrop(Blocks.EMERALD_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.EMERALD) stack.setCount(7);
        });

        // modifierAppliesToLootTableModifierAdditions
        tableMod.addItem(Items.GOLDEN_APPLE, 1f, () -> 1f, List.of(Blocks.COPPER_ORE.getLootTable().get()));
        dropMod.modifyItemDrop(Blocks.COPPER_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.GOLDEN_APPLE) stack.setCount(5);
        });

        // multipleModifiersOnSameTableAllRun
        dropMod.modifyItemDrop(Blocks.REDSTONE_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.REDSTONE) stack.setCount(20);
        });
        dropMod.modifyItemDrop(Blocks.REDSTONE_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.REDSTONE) stack.set(DataComponents.CUSTOM_NAME, Component.literal("generated_test_redstone"));
        });

        // modifierDoesNotRunForUnlistedTable
        dropMod.modifyItemDrop(Blocks.LAPIS_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.DIAMOND) stack.setCount(99);
        });

        // modifierRunsOncePerStackNotPerRoll
        dropMod.modifyItemDrop(Blocks.DIAMOND_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.DIAMOND) stack.setCount(stack.getCount() + 1);
        });
    }

    /////////////////////////////////////////////////
    ///     LootTableModifier - addItem/addBlock  ///
    /////////////////////////////////////////////////

    public static void addItemWithChanceZeroNeverDrops(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < 20; i++) {
            Entity cow = helper.spawn(EntityTypes.COW, BlockPos.ZERO);
            helper.hurt(cow, sources.playerAttack(player), 100);
        }
        helper.assertItemEntityNotPresent(Items.NETHER_STAR);
        helper.succeed();
    }

    public static void addItemWithChanceOneAlwaysDrops(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        for (int i = 0; i < 20; i++) {
            Entity sheep = helper.spawn(EntityTypes.SHEEP, BlockPos.ZERO);
            helper.hurt(sheep, sources.playerAttack(player), 100);
            helper.assertItemEntityPresent(Items.EMERALD);
            helper.despawnItem(BlockPos.ZERO, 2);
        }
        helper.succeed();
    }

    public static void addItemSingleAmountOverload(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Entity rabbit = helper.spawn(EntityTypes.RABBIT, BlockPos.ZERO);
        helper.hurt(rabbit, sources.playerAttack(player), 100);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            if (entity.getItem().getItem() == Items.QUARTZ && entity.getItem().getCount() == 3) {
                helper.succeed();
                return;
            }
        }
        helper.fail("Single-amount overload did not produce an exact count of 3");
    }

    public static void addBlockUsesItemFormOfBlock(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Entity wolf = helper.spawn(EntityTypes.WOLF, BlockPos.ZERO);
        helper.hurt(wolf, sources.playerAttack(player), 100);

        helper.assertItemEntityPresent(Items.EMERALD_BLOCK);
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///     LootTableModifier - table targeting   ///
    /////////////////////////////////////////////////

    public static void additionAppliesToAllListedTables(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        Entity ocelot = helper.spawn(EntityTypes.OCELOT, BlockPos.ZERO);
        helper.hurt(ocelot, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.LAPIS_LAZULI);
        helper.despawnItem(BlockPos.ZERO, 2);

        Entity cat = helper.spawn(EntityTypes.CAT, BlockPos.ZERO);
        helper.hurt(cat, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.LAPIS_LAZULI);
        helper.succeed();
    }

    public static void additionDoesNotAffectUnlistedTables(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Entity fox = helper.spawn(EntityTypes.FOX, BlockPos.ZERO);
        helper.hurt(fox, sources.playerAttack(player), 100);

        helper.assertItemEntityNotPresent(Items.NETHERITE_INGOT);
        helper.succeed();
    }

    public static void multipleAdditionsToSameTableAreCumulative(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Entity llama = helper.spawn(EntityTypes.LLAMA, BlockPos.ZERO);
        helper.hurt(llama, sources.playerAttack(player), 100);

        helper.assertItemEntityPresent(Items.PRISMARINE_SHARD);
        helper.assertItemEntityPresent(Items.PRISMARINE_CRYSTALS);
        helper.succeed();
    }

    public static void additionsDoNotRemoveVanillaLoot(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.COAL_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        helper.assertItemEntityPresent(Items.COAL);
        helper.assertItemEntityPresent(Items.STICK);
        helper.succeed();
    }

    public static void addItemOnBlockLootTable(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.IRON_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        helper.assertItemEntityPresent(Items.APPLE);
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///       LootDropModifier - basics            ///
    /////////////////////////////////////////////////

    public static void modifierIgnoresNonMatchingStacks(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.GOLD_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            if (entity.getItem().getItem() == Items.RAW_GOLD && entity.getItem().getCount() != 99) {
                helper.succeed();
                return;
            }
        }
        helper.fail("Modifier appears to have mutated a stack it should have ignored");
    }

    public static void modifierAppliesToVanillaEntriesToo(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.EMERALD_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            if (entity.getItem().getItem() == Items.EMERALD && entity.getItem().getCount() == 7) {
                helper.succeed();
                return;
            }
        }
        helper.fail("Modifier did not mutate the loot table's own vanilla entry");
    }

    public static void modifierAppliesToLootTableModifierAdditions(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.COPPER_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            if (entity.getItem().getItem() == Items.GOLDEN_APPLE && entity.getItem().getCount() == 5) {
                helper.succeed();
                return;
            }
        }
        helper.fail("Modifier did not see the item added by LootTableModifier for the same table");
    }

    public static void multipleModifiersOnSameTableAllRun(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.REDSTONE_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            ItemStack stack = entity.getItem();
            if (stack.getItem() == Items.REDSTONE && stack.getCount() == 20 && stack.has(DataComponents.CUSTOM_NAME)
                    && stack.get(DataComponents.CUSTOM_NAME).getString().equals("generated_test_redstone")) {
                helper.succeed();
                return;
            }
        }
        helper.fail("Not all registered modifiers were applied to the same drop pass");
    }

    public static void modifierDoesNotRunForUnlistedTable(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.DEEPSLATE_DIAMOND_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

        for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
            if (entity.getItem().getItem() == Items.DIAMOND && entity.getItem().getCount() != 99) {
                helper.succeed();
                return;
            }
        }
        helper.fail("A modifier registered for a different loot table leaked into this one");
    }

    public static void modifierRunsOncePerStackNotPerRoll(GameTestHelper helper) {
        for (int i = 0; i < 3; i++) {
            helper.setBlock(BlockPos.ZERO, Blocks.DIAMOND_ORE);
            helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);

            boolean found = false;
            for (ItemEntity entity : helper.getEntities(EntityTypes.ITEM)) {
                if (entity.getItem().getItem() == Items.DIAMOND) {
                    found = true;
                    if (entity.getItem().getCount() != 2) {
                        helper.fail("Modifier compounded across rolls instead of applying fresh to each stack");
                        return;
                    }
                }
            }
            if (!found) {
                helper.fail("Expected a diamond drop on roll " + i);
                return;
            }
            helper.despawnItem(BlockPos.ZERO, 2);
        }
        helper.succeed();
    }
}
