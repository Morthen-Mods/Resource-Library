package net.xstopho.resourcelibrary.registration;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface RegistryObject<T> extends Supplier<T> {

    ResourceKey<T> getResourceKey();

    ResourceLocation getId();

    @Override
    T get();
}
