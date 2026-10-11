package net.morthen.resourcelibrary.modifier;

import net.morthen.resourcelibrary.LibConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

@EventBusSubscriber(modid = LibConstants.MOD_ID)
public class NeoforgeLootTableModifier implements LootTableModifier {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(LootTableModifierRegistry::runCallbacks);
    }

    // Fired in parallel on worker threads since 26.3
    @SubscribeEvent
    public static void loadTables(LootTableLoadEvent event) {
        LootTableModifierRegistry.freeze();
        for (Modifier modifier : LootTableModifierRegistry.modifiers()) {
            if (event.getName().equals(modifier.lootTable().identifier())) {
                event.getTable().addPool(modifier.lootPool());
            }
        }
    }
}
