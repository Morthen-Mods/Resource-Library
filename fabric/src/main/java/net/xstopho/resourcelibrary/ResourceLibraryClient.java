package net.xstopho.resourcelibrary;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.resourcelibrary.test.LibraryTest;

public class ResourceLibraryClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            LibraryTest.initClient();
        }
    }
}
