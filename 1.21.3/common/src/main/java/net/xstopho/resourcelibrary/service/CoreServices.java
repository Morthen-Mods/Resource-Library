package net.xstopho.resourcelibrary.service;

import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.service.platform.IPlatformHelper;

import java.nio.file.Path;
import java.util.ServiceLoader;

public class CoreServices {

    public static Path getConfigDir() {
        return load(IPlatformHelper.class).getConfigDir();
    }

    public static IPlatformHelper.Platforms getPlatform() {
        return load(IPlatformHelper.class).getPlatform();
    }

    public static boolean isModLoaded(String modId) {
        return load(IPlatformHelper.class).isModLoaded(modId);
    }

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LibConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
