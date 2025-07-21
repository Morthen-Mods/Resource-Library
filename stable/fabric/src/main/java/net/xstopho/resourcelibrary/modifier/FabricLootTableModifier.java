package net.xstopho.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.registration.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class FabricLootTableModifier implements LootTableModifier {

    @Override
    public final void addItems(RegistryObject<Item> item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(item.get(), amount, chance, lootTables);
    }

    @Override
    public final void addItems(RegistryObject<Item> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(item.get(), minAmount, maxAmount, chance, lootTables);
    }


    @Override
    public final void addBlocks(RegistryObject<Block> block, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(block.get(), amount, chance, lootTables);
    }

    @Override
    public final void addBlocks(RegistryObject<Block> block, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(block.get(), minAmount, maxAmount, chance, lootTables);
    }

    @Override
    public final void addItems(ItemLike item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, lootTableSource, provider) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item, chance.get(), amount));
                }
            }
        });
    }

    @Override
    public final void addItems(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, lootTableSource, provider) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item, chance.get(), minAmount, maxAmount));
                }
            }
        });
    }
}
