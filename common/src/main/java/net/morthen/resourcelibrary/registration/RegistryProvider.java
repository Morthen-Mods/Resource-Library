package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.morthen.resourcelibrary.service.LibServices;

import java.util.Collection;
import java.util.function.Supplier;

public interface RegistryProvider<T> {

    static <T> RegistryProvider<T> get(String modId, Registry<T> registry) {
        return Factory.INSTANCE.create(modId, registry);
    }

    <U extends T> RegistryObject<U> register(String objectId, Supplier<? extends U> objectSupplier);

    Collection<RegistryObject<T>> getEntries();

    String getModId();

    interface Factory {
        Factory INSTANCE = LibServices.load(Factory.class);

        <T> RegistryProvider<T> create(String modId, Registry<T> registry);
    }
}
