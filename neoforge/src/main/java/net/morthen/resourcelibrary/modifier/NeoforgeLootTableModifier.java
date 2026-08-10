package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = LibConstants.MOD_ID)
public class NeoforgeLootTableModifier implements LootTableModifier {
    @Override
    public void addItem(RegistryObject<? extends ItemLike> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        lootTables.forEach(lootTable -> {
            modifiers.add(new Modifier(lootTable, item, null, minAmount, maxAmount, chance));
        });
    }

    @Override
    public void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        lootTables.forEach(lootTable -> {
            modifiers.add(new Modifier(lootTable, null, item, minAmount, maxAmount, chance));
        });
    }

    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        modifiers.forEach(modifier -> {
            if (event.getName().equals(modifier.lootTable().identifier())) {
                event.getTable().addPool(modifier.lootPool());
            }
        });
    }
}
