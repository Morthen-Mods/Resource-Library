package net.morthen.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public class FabricLootTableModifier implements LootTableModifier {

    public static void init() {
        // Fired in parallel on worker threads since 26.3
        LootTableEvents.MODIFY.register((key, tableBuilder, _, _) -> {
            LootTableModifierRegistry.freeze();
            for (Modifier modifier : LootTableModifierRegistry.modifiers()) {
                if (key.equals(modifier.lootTable())) {
                    tableBuilder.pool(modifier.lootPool());
                }
            }
        });
    }

    // Called once all mods ran their main entrypoint
    public static void postInit() {
        LootTableModifierRegistry.runCallbacks();
    }
}
