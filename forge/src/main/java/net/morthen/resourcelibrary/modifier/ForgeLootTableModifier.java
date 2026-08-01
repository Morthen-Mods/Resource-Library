package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morthen.resourcelibrary.LibConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeLootTableModifier implements LootTableModifier {
    private record Modifier(ResourceKey<LootTable> lootTable, LootPool lootPool) {}

    private static final List<Modifier> modifiers = new ArrayList<>();

    @Override
    public void addItem(ItemLike item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> lootTable : lootTables) {
            modifiers.add(new Modifier(lootTable, LootTableModifier.lootPool(item, chance.get(), amount).build()));
        }
    }

    @Override
    public void addItem(ItemLike item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> lootTable : lootTables) {
            modifiers.add(new Modifier(lootTable, LootTableModifier.lootPool(item, chance.get(), minAmount, maxAmount).build()));
        }
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
