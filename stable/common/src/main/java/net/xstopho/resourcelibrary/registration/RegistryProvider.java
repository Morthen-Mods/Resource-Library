package net.xstopho.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.xstopho.resourcelibrary.service.CoreServices;

import java.util.Collection;
import java.util.function.Supplier;

public interface RegistryProvider<T> {
    static <T> RegistryProvider<T> get(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return Factory.INSTANCE.create(resourceKey, modId);
    }

    static <T> RegistryProvider<T> get(Registry<T> registry, String modId) {
        return Factory.INSTANCE.create(registry, modId);
    }

    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    Collection<RegistryObject<T>> getEntries();

    String getModId();

    interface Factory {

        Factory INSTANCE = CoreServices.load(Factory.class);

        <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId);
        default <T> RegistryProvider<T> create(Registry<T> registry, String modId) {
            return create(registry.key(), modId);
        }

    }
}
