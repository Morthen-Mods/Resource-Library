package net.xstopho.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.registration.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@EventBusSubscriber(modid = LibConstants.MOD_ID)
public class NeoForgeLootTableModifier implements LootTableModifier {

    private static final List<ModifierHolder> modModifier = new ArrayList<>();
    private static final List<VanillaModifierHolder> vanillaModifier = new ArrayList<>();

    private record ModifierHolder(RegistryObject<?> object, float minAmount, float maxAmount, Supplier<Float> chance, ResourceKey<LootTable> lootTable) {}
    private record VanillaModifierHolder(ItemLike itemLike, float minAmount, float maxAmount, Supplier<Float> chance, ResourceKey<LootTable> lootTable) {}


    @Override
    public final void addItems(RegistryObject<Item> item, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(item, amount, amount, chance, lootTables);
    }

    @Override
    public final void addItems(RegistryObject<Item> item, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            modModifier.add(new ModifierHolder(item, minAmount, maxAmount, chance, table));
        }
    }

    @Override
    public final void addBlocks(RegistryObject<Block> block, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addBlocks(block, amount, amount, chance, lootTables);
    }

    @Override
    public final void addBlocks(RegistryObject<Block> block, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            modModifier.add(new ModifierHolder(block, minAmount, maxAmount, chance, table));
        }
    }

    @Override
    public final void addItems(ItemLike itemLike, float amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItems(itemLike, amount, amount, chance, lootTables);
    }

    @Override
    public final void addItems(ItemLike itemLike, float minAmount, float maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            vanillaModifier.add(new VanillaModifierHolder(itemLike, minAmount, maxAmount, chance, table));
        }
    }

    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        modModifier.forEach(modifier -> {
            if (event.getName().equals(modifier.lootTable().location())) {
                event.getTable().addPool(LootTableModifier.lootPool((ItemLike) modifier.object().get(), modifier.chance().get(), modifier.minAmount(), modifier.maxAmount()).build());
            }
        });

        vanillaModifier.forEach(modifier -> {
            if (event.getName().equals(modifier.lootTable().location())) {
                event.getTable().addPool(LootTableModifier.lootPool(modifier.itemLike(), modifier.chance().get(), modifier.minAmount(), modifier.maxAmount()).build());
            }
        });
    }
}
