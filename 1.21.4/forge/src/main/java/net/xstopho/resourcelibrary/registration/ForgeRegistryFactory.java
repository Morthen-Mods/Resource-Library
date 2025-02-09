package net.xstopho.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ForgeRegistryFactory implements RegistryProvider.Factory {
    @Override
    public <T> RegistryProvider<T> create(String modId, Registry<T> registry) {
        final Optional<? extends ModContainer> optionalModContainer = ModList.get().getModContainerById(modId);

        if (optionalModContainer.isEmpty()) throw new NullPointerException("Cannot find ModContainer for id: " + modId);

        final ModContainer modContainer = optionalModContainer.get();
        if (modContainer instanceof FMLModContainer fmlModContainer) {
            final DeferredRegister<T> register = DeferredRegister.create(registry.key(), modId);
            register.register(Objects.requireNonNull(fmlModContainer.getEventBus()));
            return new Provider<>(modId, register);

        } else throw new ClassCastException("The Container of the Mod " + modId + " is not a NeoForge one!");
    }

    private static class Provider<T> implements RegistryProvider<T> {
        private final String modId;
        private final DeferredRegister<T> registry;

        private final Collection<RegistryObject<T>> entries = new ArrayList<>();

        public Provider(String modId, DeferredRegister<T> registry) {
            this.registry = registry;
            this.modId = modId;
        }

        @Override
        public <U extends T> RegistryObject<U> register(String objectId, Supplier<? extends U> objectSupplier) {
            final var object = registry.<U>register(objectId, objectSupplier);
            final RegistryObject<U> registryObject = new RegistryObject<>() {
                @Override
                public ResourceKey<U> getResourceKey() {
                    return (ResourceKey<U>) object.getKey();
                }

                @Override
                public ResourceLocation getId() {
                    return object.getId();
                }

                @Override
                public U get() {
                    return object.get();
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
