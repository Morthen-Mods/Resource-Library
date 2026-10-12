package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistrarFactory implements RegistrarFactory {

    @Override
    public <R> Registrar<R> create(String modId, ResourceKey<? extends Registry<R>> registryKey) {
        IEventBus modBus = ModList.get().getModContainerById(modId)
                .map(ModContainer::getEventBus)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find a mod event bus for mod id: " + modId));

        DeferredRegister<R> register = DeferredRegister.create(registryKey, modId);
        register.register(modBus);
        return (key, factory) -> register.register(key.identifier().getPath(), factory);
    }
}
