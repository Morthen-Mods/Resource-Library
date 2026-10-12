package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.morthen.resourcelibrary.service.LibServices;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Adds extra loot pools to existing loot tables.
 * <p>
 * Modifiers must be registered before the first loot table is loaded, i.e. during mod initialization. Either call
 * {@code addItem(...)} from your mod constructor / initializer, or pass a callback to {@link #register(Consumer)},
 * which the library runs at the right time on every loader. Registering after loot tables started loading throws an
 * {@link IllegalStateException}.
 */
@SuppressWarnings("unused")
public interface LootTableModifier {
    LootTableModifier INSTANCE = LibServices.load(LootTableModifier.class);

    static LootTableModifier getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a callback that adds loot table modifiers. The library calls it during common setup
     * (NeoForge/Forge: {@code FMLCommonSetupEvent}, Fabric: after all mods were initialized). Callbacks registered
     * after that point run immediately.
     *
     * @throws IllegalStateException if loot tables have already been loaded
     */
    static void register(Consumer<LootTableModifier> callback) {
        LootTableModifierRegistry.register(callback);
    }

    /**
     * @return an unmodifiable view of all registered modifiers
     */
    static List<Modifier> getModifiers() {
        return LootTableModifierRegistry.modifiers();
    }

    /**
     * @param item the item to add, e.g. a {@link net.morthen.resourcelibrary.registration.RegistryObject}. It is only
     *             resolved when a loot table is loaded, so it does not need to be registered yet.
     * @throws IllegalStateException if loot tables have already been loaded
     */
    default void addItem(Supplier<? extends ItemLike> item, int amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item, amount, amount, chance, lootTables);
    }

    /**
     * @param item the item to add, e.g. a {@link net.morthen.resourcelibrary.registration.RegistryObject}. It is only
     *             resolved when a loot table is loaded, so it does not need to be registered yet.
     * @throws IllegalStateException if loot tables have already been loaded
     */
    default void addItem(Supplier<? extends ItemLike> item, int minAmount, int maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        LootTableModifierRegistry.add(lootTables.stream()
                .map(lootTable -> new Modifier(lootTable, item, minAmount, maxAmount, chance))
                .toList());
    }

    /**
     * @throws IllegalStateException if loot tables have already been loaded
     */
    default void addItem(ItemLike item, int amount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(item, amount, amount, chance, lootTables);
    }

    /**
     * @throws IllegalStateException if loot tables have already been loaded
     */
    default void addItem(ItemLike item, int minAmount, int maxAmount, Supplier<Float> chance, List<ResourceKey<LootTable>> lootTables) {
        addItem(() -> item, minAmount, maxAmount, chance, lootTables);
    }

    static LootPool.Builder lootPool(ItemLike item, float chance, int amount) {
        return LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(Math.clamp(chance, 0.0f, 1.0f)))
                .add(LootItem.lootTableItem(item))
                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(amount)));
    }

    static LootPool.Builder lootPool(ItemLike item, float chance, int minAmount, int maxAmount) {
        return LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .when(LootItemRandomChanceCondition.randomChance(Math.clamp(chance, 0.0f, 1.0f)))
                .add(LootItem.lootTableItem(item))
                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(minAmount, maxAmount)));
    }

    record Modifier(ResourceKey<LootTable> lootTable, Supplier<? extends ItemLike> item, int minAmount, int maxAmount, Supplier<Float> chance) {
        public LootPool lootPool() {
            ItemLike item = item().get();
            if (minAmount() == maxAmount()) return LootTableModifier.lootPool(item, chance().get(), minAmount()).build();
            return LootTableModifier.lootPool(item, chance().get(), minAmount(), maxAmount()).build();
        }
    }
}
