package net.xstopho.resourcelibrary.service;

import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.platform.IPlatformHelper;

import java.util.ServiceLoader;

public class CoreServices {

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
