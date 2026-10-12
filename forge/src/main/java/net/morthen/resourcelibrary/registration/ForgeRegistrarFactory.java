package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

public class ForgeRegistrarFactory implements RegistrarFactory {

    @Override
    public <R> Registrar<R> create(String modId, ResourceKey<? extends Registry<R>> registryKey) {
        if (!(ModList.getModContainerById(modId).orElse(null) instanceof FMLModContainer modContainer)) {
            throw new IllegalArgumentException("Cannot find a Forge mod container for mod id: " + modId);
        }

        DeferredRegister<R> register = DeferredRegister.create(registryKey, modId);
        register.register(modContainer.getModBusGroup());
        return (key, factory) -> register.register(key.identifier().getPath(), factory);
    }
}
