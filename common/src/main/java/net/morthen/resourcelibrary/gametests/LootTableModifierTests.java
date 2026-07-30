package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.morthen.resourcelibrary.event.LootTableModifierCallback;
import net.morthen.resourcelibrary.modifier.LootTableModifier;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootTableModifierTests {
    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("modify_mob_loot", LootTableModifierTests::modifyMobLoot);
        consumer.accept("modify_chest_loot", LootTableModifierTests::modifyChestLoot);
        consumer.accept("modify_chest_item", LootTableModifierTests::modifyChestItem);
    }

    //
    public static void setupLootModifications() {
        LootTableModifier modifier = LootTableModifier.getInstance();
        modifier.addItem(Items.DIAMOND, 1f, () -> 1f, List.of(EntityType.CHICKEN.getDefaultLootTable().get()));
        modifier.addBlock(Blocks.DIAMOND_BLOCK, 1f, () -> 1f, List.of(BuiltInLootTables.SPAWN_BONUS_CHEST));
        modifier.addItem(Items.NETHERITE_SWORD, 1f, () -> 1f, List.of(BuiltInLootTables.IGLOO_CHEST));
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

    public static void modifyChestItem(GameTestHelper helper) {
        Holder<Enchantment> mending = GametestUtils.getEnchantment(helper, Enchantments.MENDING);

        LootTableModifierCallback.MODIFY.register(stack -> {
            if (stack.getItem() == Items.NETHERITE_SWORD) {
                stack.enchant(mending, 1);
            }
        });

        helper.setBlock(BlockPos.ZERO, Blocks.CHEST);
        ChestBlockEntity chest = helper.getBlockEntity(BlockPos.ZERO, ChestBlockEntity.class);
        chest.setLootTable(BuiltInLootTables.IGLOO_CHEST);

        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        helper.useBlock(BlockPos.ZERO, player);

        helper.assertContainerContains(BlockPos.ZERO, Items.NETHERITE_SWORD);

        ItemStack sword = GametestUtils.findItem(chest, Items.NETHERITE_SWORD);

        helper.succeedIf(() -> {
            if (sword.isEmpty()) {
                throw new GameTestAssertException(Component.literal("Netherite sword not found in chest"), 100);
            }
            if (sword.getEnchantments().getLevel(mending) != 1) {
                throw new GameTestAssertException(Component.literal("Netherite sword doesn't have Mending lvl 1"), 100);
            }

            LootTableModifierCallback.MODIFY.clearEvent();
        });
    }
}
