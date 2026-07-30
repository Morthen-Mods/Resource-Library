package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.LibConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = LibConstants.MOD_ID)
public class NeoforgeLootTableModifier implements LootTableModifier {
    private record Modifier(ItemLike item, ResourceKey<LootTable> table, Supplier<Float> chance, float minAmount, float maxAmount) {}
    private static final List<Modifier> modifiers = new ArrayList<>();

    @Override
    public void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            modifiers.add(new Modifier(item, table, chance, minAmount, maxAmount));
        }
    }

    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        modifiers.forEach(modifier -> {
            if (event.getName().equals(modifier.table().identifier())) {
                LootPool pool = LootTableModifier.lootPool(modifier.item(), modifier.chance().get(), modifier.minAmount(), modifier.maxAmount()).build();
                event.getTable().addPool(pool);
            }
        });
    }
}
