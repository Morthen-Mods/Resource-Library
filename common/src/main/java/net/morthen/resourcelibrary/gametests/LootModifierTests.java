package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.morthen.resourcelibrary.modifier.LootDropModifier;
import net.morthen.resourcelibrary.modifier.LootTableModifier;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootModifierTests {
    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("modify_mob_loot", LootModifierTests::modifyMobLoot);
        consumer.accept("modify_chest_loot", LootModifierTests::modifyChestLoot);

        consumer.accept("modify_mob_drop", LootModifierTests::modifyMobDrop);
        consumer.accept("modify_chest_item", LootModifierTests::modifyChestItem);
    }

    //
    public static void setupLootModifications() {
        LootTableModifier modifier = LootTableModifier.getInstance();
        modifier.addItem(Items.DIAMOND, 1f, () -> 1f, List.of(EntityType.CHICKEN.getDefaultLootTable().get()));
        modifier.addItem(Items.IRON_INGOT, 1f, () -> 1f, List.of(EntityType.CHICKEN.getDefaultLootTable().get()));
        modifier.addBlock(Blocks.DIAMOND_BLOCK, 1f, () -> 1f, List.of(BuiltInLootTables.SPAWN_BONUS_CHEST));
        modifier.addItem(Items.END_CRYSTAL, 1f, () -> 1f, List.of(BuiltInLootTables.IGLOO_CHEST));

        LootDropModifier dropModifier = LootDropModifier.getInstance();
        dropModifier.modifyItemDrop(EntityType.CHICKEN.getDefaultLootTable().get(), stack -> {
            if (stack.getItem() == Items.IRON_INGOT) {
                stack.setCount(10);
            }
        });

        dropModifier.modifyItemDrop(BuiltInLootTables.IGLOO_CHEST, stack -> {
            if (stack.getItem() == Items.END_CRYSTAL) {
                stack.set(DataComponents.CUSTOM_NAME, Component.literal("custom_name"));
            }
        });
    }

    public static void modifyMobLoot(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Chicken chicken = helper.spawn(EntityType.CHICKEN, BlockPos.ZERO);
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());

        helper.hurt(chicken, sources.playerAttack(player), 100);
        helper.assertItemEntityPresent(Items.DIAMOND);
        helper.succeed();
    }

    public static void modifyChestLoot(GameTestHelper helper) {
        helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        ChestBlockEntity chest = helper.getBlockEntity(BlockPos.ZERO, ChestBlockEntity.class);
        chest.setLootTable(BuiltInLootTables.SPAWN_BONUS_CHEST);

        helper.useBlock(BlockPos.ZERO, player);
        helper.assertContainerContains(BlockPos.ZERO, Items.DIAMOND_BLOCK);
        helper.succeed();
    }

    public static void modifyMobDrop(GameTestHelper helper) {
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        Chicken chicken = helper.spawn(EntityType.CHICKEN, BlockPos.ZERO);
        DamageSources sources = new DamageSources(helper.getLevel().registryAccess());

        helper.hurt(chicken, sources.playerAttack(player), 100);
        List<ItemEntity> entities = helper.getEntities(EntityType.ITEM, BlockPos.ZERO, 0);

        for (ItemEntity entity : entities) {
            if (entity.getItem().getItem() == Items.IRON_INGOT && entity.getItem().getCount() == 10) {
                helper.succeed();
            }
        }
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
}
