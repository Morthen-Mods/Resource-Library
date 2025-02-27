package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class LibraryTest implements ModInitializer {
    
    @Override
    public void onInitialize() {
        TestConstants.commonInit();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> SaveLootTablesCommand.saveCommand(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server)
                -> TestConstants.TEST_EVENT.invoker().onJoin(handler.getPlayer()));
    }
}
