package net.morthen.resourcelibrary.registration;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface RegistryObject<T> extends Supplier<T> {

    ResourceKey<T> getResourceKey();

    Identifier getId();

    @Override
    T get();
}
