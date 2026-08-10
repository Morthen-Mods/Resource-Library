package net.morthen.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.registration.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class FabricLootTableModifier implements LootTableModifier {
    @Override
    public void addItem(RegistryObject<? extends ItemLike> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((key, tableBuilder, _, _) -> {
            for (ResourceKey<LootTable> table : lootTables) {
                if (key.equals(table)) {
                    tableBuilder.withPool(LootTableModifier.lootPool(item.get(), chance.get(), minAmount, maxAmount));
                }
            }
        });
    }

    @Override
    public void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableEvents.MODIFY.register((key, tableBuilder, _, _) -> {
            for (ResourceKey<LootTable> table : lootTables) {
                if (key.equals(table)) {
                    tableBuilder.withPool(LootTableModifier.lootPool(item, chance.get(), minAmount, maxAmount));
                }
            }
        });
    }
}
