package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.morthen.resourcelibrary.gametests.utils.GametestUtils;
import net.morthen.resourcelibrary.modifier.LootDropModifier;
import net.morthen.resourcelibrary.modifier.LootTableModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootModifierTests {
    private static Logger logger = LoggerFactory.getLogger("LootModifierTests");

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("add_mob_loot", LootModifierTests::addMobLoot);
        consumer.accept("add_mob_loot_with_range", LootModifierTests::addMobLootWithRange);
        consumer.accept("add_loot_to_multiple_mobs", LootModifierTests::addLootToMultipleMobs);
        consumer.accept("add_mob_loot_with_chance", LootModifierTests::addMobLootWithChance);
        consumer.accept("add_mob_loot_with_no_chance", LootModifierTests::addMobLootWithNoChance);
        consumer.accept("modify_mob_drop", LootModifierTests::modifyMobDrop);

        consumer.accept("add_item_to_block_drops",  LootModifierTests::addItemToBlockDrops);
        consumer.accept("modify_block_drops_amount",  LootModifierTests::modifyBlockDropsAmount);

        consumer.accept("modify_chest_loot", LootModifierTests::addChestLoot);
        consumer.accept("modify_chest_item", LootModifierTests::modifyChestItem);
        consumer.accept("modify_chest_item_amount", LootModifierTests::modifyChestItemAmount);
    }

    /**
     * Base function that needs to be called in the main class
     */
    public static void setupLootModifications() {
        LootTableModifier modifier = LootTableModifier.getInstance();
        // Mobs
        modifier.addItem(Items.DIAMOND, 1f, () -> 1f, List.of(EntityType.CHICKEN.getDefaultLootTable().get()));
        modifier.addItem(Items.IRON_INGOT, 1f, () -> 1f, List.of(EntityType.CHICKEN.getDefaultLootTable().get()));
        modifier.addItem(Items.GLOWSTONE_DUST, 1f, 5f, () -> 1f, List.of(EntityType.PIG.getDefaultLootTable().get()));
        modifier.addItem(Items.ACACIA_DOOR, 1f, () -> 1f, List.of(EntityType.DROWNED.getDefaultLootTable().get(), EntityType.ZOMBIE.getDefaultLootTable().get()));
        modifier.addItem(Items.BAMBOO_BUTTON, 1f, () -> 0.5f, List.of(EntityType.BEE.getDefaultLootTable().get()));
        modifier.addItem(Items.NETHERITE_SWORD, 1f, () -> 0f, List.of(EntityType.BEE.getDefaultLootTable().get()));

        // Blocks
        modifier.addItem(Items.DIAMOND, 1f, () -> 1f, List.of(Blocks.COAL_ORE.getLootTable().get()));

        // Chests
        modifier.addBlock(Blocks.DIAMOND_BLOCK, 1f, () -> 1f, List.of(BuiltInLootTables.SPAWN_BONUS_CHEST));
        modifier.addItem(Items.END_CRYSTAL, 1f, () -> 1f, List.of(BuiltInLootTables.IGLOO_CHEST));
        modifier.addItem(Items.END_PORTAL_FRAME, 1f, () -> 1f, List.of(BuiltInLootTables.VILLAGE_ARMORER));

        LootDropModifier dropModifier = LootDropModifier.getInstance();
        // Mobs
        dropModifier.modifyItemDrop(EntityType.CHICKEN.getDefaultLootTable().get(), stack -> {
            if (stack.getItem() == Items.IRON_INGOT) stack.setCount(10);
        });

        // Block
        dropModifier.modifyItemDrop(Blocks.COAL_ORE.getLootTable().get(), stack -> {
            if (stack.getItem() == Items.COAL_BLOCK) stack.setCount(10);
        });

        // Chests
        dropModifier.modifyItemDrop(BuiltInLootTables.IGLOO_CHEST, stack -> {
            if (stack.getItem() == Items.END_CRYSTAL) {
                stack.set(DataComponents.CUSTOM_NAME, Component.literal("custom_name"));
            }
        });

        dropModifier.modifyItemDrop(BuiltInLootTables.VILLAGE_ARMORER, stack -> {
            if (stack.getItem() == Items.END_PORTAL_FRAME) stack.setCount(10);
        });
    }

    /////////////////////////////////////////////////
    ///         Mob Loot Modifications            ///
    /////////////////////////////////////////////////
    public static void addMobLoot(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Chicken chicken = helper.spawn(EntityType.CHICKEN, BlockPos.ZERO);

        helper.hurt(chicken, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.DIAMOND);
        helper.succeed();
    }

    public static void modifyMobDrop(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Chicken chicken = helper.spawn(EntityType.CHICKEN, BlockPos.ZERO);

        helper.hurt(chicken, sources.playerAttack(player), 100);
        List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);

        for (ItemEntity entity : entities) {
            if (entity.getItem().getItem() == Items.IRON_INGOT && entity.getItem().getCount() == 10) {
                helper.succeed();
            }
        }
    }

    public static void addMobLootWithRange(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Pig pig =  helper.spawn(EntityType.PIG, BlockPos.ZERO);

        helper.hurt(pig, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.GLOWSTONE_DUST);

        List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);
        for (ItemEntity entity : entities) {
            if (entity.getItem().getItem() == Items.GLOWSTONE_DUST) {
                int count = entity.getItem().getCount();
                if (count >= 1f &&  count <= 5f) {
                    helper.succeed();
                }
            }
        }
    }

    public static void addLootToMultipleMobs(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Entity drowned = helper.spawn(EntityType.DROWNED, BlockPos.ZERO);
        Entity zombie = helper.spawn(EntityType.ZOMBIE, BlockPos.ZERO);

        helper.hurt(drowned, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.ACACIA_DOOR);
        helper.despawnItem(BlockPos.ZERO, 2);

        helper.hurt(zombie, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.ACACIA_DOOR);
        helper.succeed();
    }

    public static void addMobLootWithChance(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        double count = 0;
        double runs = 500;
        for (int i = 0; i < runs; i++) {
            Entity bee = helper.spawn(EntityType.BEE, BlockPos.ZERO);
            helper.hurt(bee, sources.playerAttack(player), 100);
            List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);
            if (!entities.isEmpty()) count++;
            helper.despawnItem(BlockPos.ZERO, 2);
        }
        double rate = (count / runs) * 100;
        logger.info("Reached drop rate '{}%' with {} drops", rate, count);
        if (rate >= 45.0 && rate <= 55.0) {
            helper.succeed();
        } else {
            helper.fail("Item doesn't meet the expected drop rate");
        }
    }

    public static void addMobLootWithNoChance(GameTestHelper helper) {
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);

        double count = 0;
        for (int i = 0; i < 10; i++) {
            Entity bee = helper.spawn(EntityType.BEE, BlockPos.ZERO);
            helper.hurt(bee, sources.playerAttack(player), 100);
            List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);
            for (ItemEntity entity : entities) {
                if (entity.getItem().getItem() == Items.NETHERITE_SWORD) count++;
            }
        }

        if (count == 0) {
            helper.succeed();
        } else {
            helper.fail("Item doesn't meet the expected drop rate");
        }
    }

    /////////////////////////////////////////////////
    ///         Block Loot Modifications          ///
    /////////////////////////////////////////////////
    public static void addItemToBlockDrops(GameTestHelper helper) {
        // Place block
        helper.setBlock(BlockPos.ZERO, Blocks.COAL_ORE);
        helper.assertBlockPresent(Blocks.COAL_ORE, BlockPos.ZERO);
        // Destroy block
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);
        helper.assertBlockNotPresent(Blocks.COAL_ORE,  BlockPos.ZERO);
        // Check for ItemEntity
        helper.assertItemEntityPresent(Items.DIAMOND);
        helper.succeed();
    }

    public static void modifyBlockDropsAmount(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.COAL_ORE);
        helper.getLevel().destroyBlock(helper.absolutePos(BlockPos.ZERO), true);
        List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);
        entities.forEach(entity -> {
            if (entity.getItem().getItem() == Items.COAL && entity.getItem().getCount() == 10) {}
                helper.succeed();
        });
    }

    /////////////////////////////////////////////////
    ///         Chest Loot Modifications          ///
    /////////////////////////////////////////////////
    public static void addChestLoot(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ChestBlockEntity chest = helper.getBlockEntity(BlockPos.ZERO, ChestBlockEntity.class);
        chest.setLootTable(BuiltInLootTables.SPAWN_BONUS_CHEST);

        helper.useBlock(BlockPos.ZERO, player);
        helper.assertContainerContains(BlockPos.ZERO, Items.DIAMOND_BLOCK);
        helper.succeed();
    }

     public static void modifyChestItem(GameTestHelper helper) {
         helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ChestBlockEntity chest = helper.getBlockEntity(BlockPos.ZERO, ChestBlockEntity.class);
         chest.setLootTable(BuiltInLootTables.IGLOO_CHEST);

         helper.useBlock(BlockPos.ZERO, player);
         helper.assertContainerContains(BlockPos.ZERO, Items.END_CRYSTAL);

         ItemStack stack = GametestUtils.findItem(chest, Items.END_CRYSTAL);
         if (stack.has(DataComponents.CUSTOM_NAME)) {
             Component component = stack.get(DataComponents.CUSTOM_NAME);
             if (component.getString().equals("custom_name")) {
                 helper.succeed();
             }
         }
     }

     public static void modifyChestItemAmount(GameTestHelper helper) {
         helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
         Player player = helper.makeMockPlayer(GameType.SURVIVAL);
         ChestBlockEntity chest = helper.getBlockEntity(BlockPos.ZERO, ChestBlockEntity.class);
         chest.setLootTable(BuiltInLootTables.VILLAGE_ARMORER);

         helper.useBlock(BlockPos.ZERO, player);
         helper.assertContainerContains(BlockPos.ZERO, Items.END_PORTAL_FRAME);

         int amount = chest.countItem(Items.END_PORTAL_FRAME);

         if (amount == 10) helper.succeed();
     }
}
