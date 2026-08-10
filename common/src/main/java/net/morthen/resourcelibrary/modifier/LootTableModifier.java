package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.service.LibServices;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public interface LootTableModifier {
    LootTableModifier INSTANCE = LibServices.load(LootTableModifier.class);
    List<Modifier> modifiers = new ArrayList<>();

    static LootTableModifier getInstance() {
        return INSTANCE;
    }

    default void addItem(RegistryObject<? extends ItemLike> item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item, amount, amount, chance, lootTables);
    }
    void addItem(RegistryObject<? extends ItemLike> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables);

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

    record Modifier(ResourceKey<LootTable> lootTable, RegistryObject<? extends ItemLike> objectItem, ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance) {
        private ItemLike getItem() {
            if (objectItem != null) return objectItem.get();
            if (item != null) return item;
            throw new IllegalArgumentException("No ItemLike provided for LootTable Modification!");
        }

        public LootPool lootPool() {
            LootPool pool;
            if (minAmount() == maxAmount()) pool = LootTableModifier.lootPool(getItem(), chance().get(), minAmount()).build();
            else pool = LootTableModifier.lootPool(getItem(), chance().get(), minAmount(), maxAmount()).build();
            return pool;
        }
    }
}
