package net.xstopho.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.registration.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = LibConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeLootTableModifier implements LootTableModifier {

    private static final List<Modifier> modifiers = new ArrayList<>();
    private static final List<RangedModifier> rangedModifiers = new ArrayList<>();

    private record Modifier(RegistryObject<Item> item, float amount, float chance, ResourceKey<LootTable> lootTable) {}
    private record RangedModifier(RegistryObject<Item> item, float minAmount, float maxAmount, float chance, ResourceKey<LootTable> lootTable) {}

    @Override
    public void addLoot(RegistryObject<Item> item, float amount, float chance, ResourceKey<LootTable>... lootTables) {
        for (ResourceKey<LootTable> lootTable : lootTables) {
            modifiers.add(new Modifier(item, amount, chance, lootTable));
        }
    }

    @Override
    public final void addLoot(RegistryObject<Item> item, float minAmount, float maxAmount, float chance, ResourceKey<LootTable>... lootTables) {
        for (ResourceKey<LootTable> lootTable : lootTables) {
            rangedModifiers.add(new RangedModifier(item, minAmount, maxAmount, chance, lootTable));
        }
    }

    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        for (Modifier modifier : modifiers) {
            if (event.getName().equals(modifier.lootTable.location())) {
                event.getTable().addPool(LootTableModifier.lootPool(modifier.item.get(), modifier.chance, modifier.amount).build());
            }
        }

        for (RangedModifier modifier : rangedModifiers) {
            if (event.getName().equals(modifier.lootTable.location())) {
                event.getTable().addPool(LootTableModifier.lootPool(modifier.item.get(), modifier.chance, modifier.minAmount, modifier.maxAmount).build());
            }
        }
    }

}
