package net.xstopho.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.registration.RegistryObject;

public class FabricLootTableModifier implements LootTableModifier {

    @Override
    public void addLoot(RegistryObject<Item> item, float amount, float chance, ResourceKey<LootTable>... lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, lootTableSource, provider) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item.get(), chance, amount));
                }
            }
        });
    }

    @Override
    public void addLoot(RegistryObject<Item> item, float minAmount, float maxAmount, float chance, ResourceKey<LootTable>... lootTables) {
        LootTableEvents.MODIFY.register((resourceKey, builder, lootTableSource, provider) -> {
            for (ResourceKey<LootTable> lootTable : lootTables) {
                if (resourceKey.equals(lootTable)) {
                    builder.withPool(LootTableModifier.lootPool(item.get(), chance, minAmount, maxAmount));
                }
            }
        });
    }

}
