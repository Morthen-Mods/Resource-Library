package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class LibraryTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TestConstants.clientInit();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> SaveLootTablesCommand.saveCommand(dispatcher));

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server)
                -> TestConstants.TEST_LIST_EVENT.invoker().onJoin(handler.getPlayer()));
    }
}
