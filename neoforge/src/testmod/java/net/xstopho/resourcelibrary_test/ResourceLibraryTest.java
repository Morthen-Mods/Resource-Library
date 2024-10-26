package net.xstopho.resourcelibrary_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(RLibTestConstants.MOD_ID)
public class ResourceLibraryTest {

    public ResourceLibraryTest(IEventBus eventBus) {
        RLibTestConstants.commonInit();
    }
}
