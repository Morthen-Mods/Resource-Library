package net.xstopho.resourcelibrary.platform;

import net.minecraftforge.fml.ModList;
import net.xstopho.resourcelibrary.service.platform.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public Platforms getPlatform() {
        return Platforms.FORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
