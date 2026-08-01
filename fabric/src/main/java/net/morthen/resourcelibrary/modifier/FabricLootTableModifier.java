package net.morthen.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;
import java.util.function.Supplier;

public class FabricLootTableModifier implements LootTableModifier {
    @Override
    public void addItem(ItemLike item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, _, _) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item, chance.get(), amount));
                }
            }
        });
    }

    @Override
    public final void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, _, _) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item, chance.get(), minAmount, maxAmount));
                }
            }
        });
    }
}
