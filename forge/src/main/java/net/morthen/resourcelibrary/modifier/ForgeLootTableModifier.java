package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.registration.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeLootTableModifier implements LootTableModifier {

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
