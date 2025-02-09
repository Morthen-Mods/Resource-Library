package net.xstopho.resourcelibrary.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.resourcelibrary.service.platform.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public Platforms getPlatform() {
        return Platforms.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
