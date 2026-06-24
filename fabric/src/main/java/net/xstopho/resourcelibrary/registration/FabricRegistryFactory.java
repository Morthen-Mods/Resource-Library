package net.xstopho.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class FabricRegistryFactory implements RegistryProvider.Factory {

    @Override
    public <T> RegistryProvider<T> create(String modId, Registry<T> registry) {
        return new Provider<>(modId, registry);
    }

    private static class Provider<T> implements RegistryProvider<T> {
        private final String modId;
        private final Registry<T> registry;

        private final Collection<RegistryObject<T>> entries = new ArrayList<>();

        private Provider(String modId, Registry<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        public <U extends T> RegistryObject<U> register(String objectId, Supplier<? extends U> objectSupplier) {
            final Identifier objectLocation = Identifier.fromNamespaceAndPath(modId, objectId);
            final U object = Registry.register(registry, objectLocation, objectSupplier.get());

            final RegistryObject<U> registryObject = new RegistryObject<>() {
                final ResourceKey<U> resourceKey = ResourceKey.create((ResourceKey<? extends Registry<U>>) registry.key(), objectLocation);

                @Override
                public ResourceKey<U> getResourceKey() {

                    return resourceKey;
                }

                @Override
                public Identifier getId() {

                    return objectLocation;
                }

                @Override
                public U get() {
                    return object;
                }
            };

            entries.add((RegistryObject<T>) registryObject);
            return registryObject;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return entries;
        }

        @Override
        public String getModId() {
            return modId;
        }
    }
}
