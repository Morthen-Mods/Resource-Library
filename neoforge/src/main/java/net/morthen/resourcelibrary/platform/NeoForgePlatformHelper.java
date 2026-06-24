package net.morthen.resourcelibrary.platform;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.morthen.resourcelibrary.service.platform.IPlatformHelper;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Platforms getPlatform() {
        return Platforms.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
