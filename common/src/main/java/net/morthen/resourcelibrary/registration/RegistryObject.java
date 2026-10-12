package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Reference to an object registered through a {@link RegistryProvider}.
 * <p>
 * {@link #getId()} and {@link #getResourceKey()} are available right after {@code register(...)} returned.
 * {@link #get()} and {@link #getHolder()} only work once the object was actually added to the registry: on Fabric
 * that happens immediately, on Forge and NeoForge during the registry event of the mod loading process. Use
 * {@link #isBound()} to check.
 *
 * @param <R> type of the registry, e.g. {@code Item}
 * @param <T> type of the registered object, e.g. {@code MyItem}
 */
public final class RegistryObject<R, T extends R> implements Supplier<T> {
    private final ResourceKey<R> key;
    private volatile Holder.@Nullable Reference<R> holder;

    RegistryObject(ResourceKey<R> key) {
        this.key = key;
    }

    public ResourceKey<R> getResourceKey() {
        return key;
    }

    public Identifier getId() {
        return key.identifier();
    }

    /**
     * @return {@code true} if the object was added to its registry, so {@link #get()} and {@link #getHolder()} can be used
     */
    public boolean isBound() {
        return resolve() != null;
    }

    /**
     * @throws IllegalStateException if the object was not registered yet
     */
    public Holder<R> getHolder() {
        Holder.Reference<R> holder = resolve();
        if (holder == null) {
            throw new IllegalStateException("Registry object " + key.identifier() + " is not registered yet");
        }
        return holder;
    }

    /**
     * @throws IllegalStateException if the object was not registered yet
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get() {
        return (T) getHolder().value();
    }

    private Holder.@Nullable Reference<R> resolve() {
        Holder.Reference<R> holder = this.holder;
        if (holder != null) return holder;

        Registry<R> registry = RegistryProvider.findRegistry(key.registryKey());
        if (registry == null) return null;

        holder = registry.get(key).filter(Holder.Reference::isBound).orElse(null);
        this.holder = holder;
        return holder;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegistryObject<?, ?> other && key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "RegistryObject[" + key.registry() + " / " + key.identifier() + "]";
    }
}
