package net.xstopho.resourcelibrary_test;

import net.fabricmc.api.ClientModInitializer;

public class LibraryTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TestConstants.clientInit();
    }
}
