package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.morthen.resourcelibrary.service.LibServices;

import java.util.function.Supplier;

/**
 * Loader specific part of {@link RegistryProvider}. Use {@link RegistryProvider} instead of calling this directly.
 */
public interface RegistrarFactory {
    RegistrarFactory INSTANCE = LibServices.load(RegistrarFactory.class);

    <R> Registrar<R> create(String modId, ResourceKey<? extends Registry<R>> registryKey);

    @FunctionalInterface
    interface Registrar<R> {
        /**
         * Registers the object created by {@code factory} under {@code key}, either immediately or once the loader
         * allows registering objects.
         */
        void register(ResourceKey<R> key, Supplier<? extends R> factory);
    }
}
