package net.morthen.resourcelibrary.modifier;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.morthen.resourcelibrary.LibConstants;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeLootTableModifier implements LootTableModifier {

    // Registered on the mod bus in ResourceLibrary
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
