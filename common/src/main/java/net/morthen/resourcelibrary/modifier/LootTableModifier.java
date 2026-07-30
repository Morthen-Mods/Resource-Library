package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.service.CoreServices;

import java.util.List;
import java.util.function.Supplier;

public interface LootTableModifier {

    static LootTableModifier getInstance() {
        return CoreServices.load(LootTableModifier.class);
    }

    default void addItem(RegistryObject<Item> item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item.get(), amount, chance, lootTables);
    }

    default void addItem(RegistryObject<Item> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item.get(), minAmount, maxAmount, chance, lootTables);
    }

    default void addBlock(RegistryObject<Block> block, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(block.get(), amount, chance, lootTables);
    }

    default void addBlock(RegistryObject<Block> block, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(block.get(), minAmount, maxAmount, chance, lootTables);
    }

    default void addBlock(Block block, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(block, minAmount, maxAmount, chance, lootTables);
    }

    default void addBlock(Block block, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(block, amount, amount, chance, lootTables);
    }

    default void addItem(ItemLike item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item, amount, amount, chance, lootTables);
    }
    void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables);

    static LootPool.Builder lootPool(ItemLike item, float chance, float amount) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .when(LootItemRandomChanceCondition.randomChance(Math.clamp(chance, 0.0f, 1.0f)))
                .add(LootItem.lootTableItem(item))
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(amount)));
    }

    static LootPool.Builder lootPool(ItemLike item, float chance, float minAmount, float maxAmount) {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .when(LootItemRandomChanceCondition.randomChance(Math.clamp(chance, 0.0f, 1.0f)))
                .add(LootItem.lootTableItem(item))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minAmount, maxAmount)));
    }
}
