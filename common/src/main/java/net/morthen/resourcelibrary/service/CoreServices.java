package net.morthen.resourcelibrary.service;

import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.service.platform.IPlatformHelper;

import java.util.ServiceLoader;

public class CoreServices {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LibConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
