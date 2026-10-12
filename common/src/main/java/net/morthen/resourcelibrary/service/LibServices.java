package net.morthen.resourcelibrary.service;

import net.morthen.resourcelibrary.LibConstants;

import java.util.ServiceLoader;

public final class LibServices {

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failed to load service for " + clazz.getName()));
        LibConstants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
