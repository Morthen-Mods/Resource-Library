package net.morthen.template.gametest;

import net.fabricmc.api.ModInitializer;
import net.morthen.resourcelibrary.gametest.GametestConstants;

public class Mod implements ModInitializer {
    @Override
    public void onInitialize() {
        GametestConstants.commonInit();
    }
}
