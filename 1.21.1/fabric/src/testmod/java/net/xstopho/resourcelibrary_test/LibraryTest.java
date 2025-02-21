package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class LibraryTest implements ModInitializer {
    @Override
    public void onInitialize() {
        LibraryTestConstants.commonInit();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> SaveLootTablesCommand.saveCommand(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            LibraryTestConstants.TEST_EVENT.invoker().testInvoker(handler.getPlayer());
        });
    }
}
