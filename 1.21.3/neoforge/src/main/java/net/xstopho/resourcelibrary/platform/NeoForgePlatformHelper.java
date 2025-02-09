package net.xstopho.resourcelibrary.platform;

import net.neoforged.fml.ModList;
import net.xstopho.resourcelibrary.service.platform.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public Platforms getPlatform() {
        return Platforms.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
