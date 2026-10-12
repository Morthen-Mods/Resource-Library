package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricRegistrarFactory implements RegistrarFactory {

    // Fabric has no registry event, objects are registered right away
    @Override
    public <R> Registrar<R> create(String modId, ResourceKey<? extends Registry<R>> registryKey) {
        return (key, factory) -> {
            Registry<R> registry = RegistryProvider.findRegistry(registryKey);
            if (registry == null) {
                throw new IllegalStateException("Registry " + registryKey.identifier() + " does not exist (yet), cannot register " + key.identifier());
            }
            Registry.register(registry, key, factory.get());
        };
    }
}
