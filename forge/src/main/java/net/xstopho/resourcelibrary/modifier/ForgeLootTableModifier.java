package net.xstopho.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.registration.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeLootTableModifier implements LootTableModifier {

    private static final List<ModifierHolder> modModifier = new ArrayList<>();
    private static final List<VanillaModifierHolder> vanillaModifier = new ArrayList<>();

    private record ModifierHolder(RegistryObject<?> object, float minAmount, float maxAmount, float chance, ResourceKey<LootTable> lootTable) {}
    private record VanillaModifierHolder(ItemLike itemLike, float minAmount, float maxAmount, float chance, ResourceKey<LootTable> lootTable) {}


    @Override
    @SafeVarargs
    public final void addItems(RegistryObject<Item> item, float amount, float chance, ResourceKey<LootTable>... lootTables) {
        addItems(item, amount, amount, chance, lootTables);
    }

    @Override
    @SafeVarargs
    public final void addItems(RegistryObject<Item> item, float minAmount, float maxAmount, float chance, ResourceKey<LootTable>... lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            modModifier.add(new ModifierHolder(item, minAmount, maxAmount, chance, table));
        }
    }

    @Override
    @SafeVarargs
    public final void addBlocks(RegistryObject<Block> block, float amount, float chance, ResourceKey<LootTable>... lootTables) {
        addBlocks(block, amount, amount, chance, lootTables);
    }

    @Override
    @SafeVarargs
    public final void addBlocks(RegistryObject<Block> block, float minAmount, float maxAmount, float chance, ResourceKey<LootTable>... lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            modModifier.add(new ModifierHolder(block, minAmount, maxAmount, chance, table));
        }
    }

    @Override
    @SafeVarargs
    public final void addItems(ItemLike itemLike, float amount, float chance, ResourceKey<LootTable>... lootTables) {
        addItems(itemLike, amount, amount, chance, lootTables);
    }

    @Override
    @SafeVarargs
    public final void addItems(ItemLike itemLike, float minAmount, float maxAmount, float chance, ResourceKey<LootTable>... lootTables) {
        for (ResourceKey<LootTable> table : lootTables) {
            vanillaModifier.add(new VanillaModifierHolder(itemLike, minAmount, maxAmount, chance, table));
        }
    }

    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        modModifier.forEach(modifier -> {
            if (event.getName().equals(modifier.lootTable().location())) {
                event.getTable().addPool(LootTableModifier.lootPool((ItemLike) modifier.object().get(), modifier.chance(), modifier.minAmount(), modifier.maxAmount()).build());
            }
        });

        vanillaModifier.forEach(modifier -> {
            if (event.getName().equals(modifier.lootTable().location())) {
                event.getTable().addPool(LootTableModifier.lootPool(modifier.itemLike(), modifier.chance(), modifier.minAmount(), modifier.maxAmount()).build());
            }
        });
    }
}
