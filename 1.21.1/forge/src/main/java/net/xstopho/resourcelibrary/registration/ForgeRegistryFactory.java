package net.xstopho.resourcelibrary.registration;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ForgeRegistryFactory implements RegistryProvider.Factory {
    @Override
    public <T> RegistryProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        final var modContainer = ModList.get().getModContainerById(modId);
        if (modContainer.isEmpty()) throw new NullPointerException("Cannot find Mod Container for " + modId);

        final var container = modContainer.get();
        if (container instanceof FMLModContainer fmlModContainer) {
            final var register = DeferredRegister.create(resourceKey, modId);
            register.register(fmlModContainer.getEventBus());
            return new Provider<>(register, modId);
        } else {
            throw new ClassCastException("The Container of the Mod " + modId + " is not a Forge one!");
        }
    }


    private static class Provider<T> implements RegistryProvider<T> {
        private final String modId;
        private final DeferredRegister<T> registry;

        private final Set<RegistryObject<T>> entries = new LinkedHashSet<>();
        private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries);

        private Provider(DeferredRegister<T> registry, String modId) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        public String getModId() {
            return modId;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final var object = registry.<I>register(name, supplier);
            final var registryObject = new RegistryObject<I>() {

                @Override
                public ResourceKey<I> getResourceKey() {
                    return object.getKey();
                }

                @Override
                public ResourceLocation getId() {
                    return object.getId();
                }

                @Override
                public I get() {
                    return object.get();
                }

                @Override
                public Holder<I> asHolder() {
                    return object.getHolder().orElseThrow();
                }
            };
            entries.add((RegistryObject<T>) registryObject);
            return registryObject;
        }

        @Override
        public Set<RegistryObject<T>> getEntries() {
            return entriesView;
        }
    }
}