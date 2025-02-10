package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class LibraryTest implements ModInitializer {
    @Override
    public void onInitialize() {
        LibraryTestConstants.commonInit();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> SaveLootTablesCommand.saveCommand(dispatcher));
    }
}
