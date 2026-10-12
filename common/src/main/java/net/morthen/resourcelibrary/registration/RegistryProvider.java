package net.morthen.resourcelibrary.registration;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Registers objects of one registry for one mod on every loader.
 *
 * <pre>{@code
 * public static final RegistryProvider<Item> ITEMS = RegistryProvider.create(MOD_ID, Registries.ITEM);
 *
 * public static final RegistryObject<Item, Item> RUBY = ITEMS.register("ruby", key -> new Item(new Item.Properties().setId(key)));
 * }</pre>
 *
 * Providers must be created and used during mod initialization (mod constructor / initializer), before the
 * registries are frozen.
 *
 * @param <R> type of the registry, e.g. {@code Item}
 */
public final class RegistryProvider<R> {
    private final String modId;
    private final ResourceKey<? extends Registry<R>> registryKey;
    private final RegistrarFactory.Registrar<R> registrar;

    private final List<RegistryObject<R, ? extends R>> entries = new ArrayList<>();
    private final Collection<RegistryObject<R, ? extends R>> entriesView = Collections.unmodifiableList(entries);

    private RegistryProvider(String modId, ResourceKey<? extends Registry<R>> registryKey) {
        this.modId = modId;
        this.registryKey = registryKey;
        this.registrar = RegistrarFactory.INSTANCE.create(modId, registryKey);
    }

    public static <R> RegistryProvider<R> create(String modId, Registry<R> registry) {
        return new RegistryProvider<>(modId, registry.key());
    }

    /**
     * Creates a provider for a registry by its key, e.g. {@code Registries.ITEM}. The registry does not need to
     * exist yet, as long as it exists once objects are registered.
     */
    public static <R> RegistryProvider<R> create(String modId, ResourceKey<? extends Registry<R>> registryKey) {
        return new RegistryProvider<>(modId, registryKey);
    }

    public <T extends R> RegistryObject<R, T> register(String objectId, Supplier<? extends T> objectSupplier) {
        return register(objectId, _ -> objectSupplier.get());
    }

    /**
     * Registers an object whose factory receives the key it is registered under, e.g. for
     * {@code Item.Properties#setId} or {@code BlockBehaviour.Properties#setId}.
     */
    public <T extends R> RegistryObject<R, T> register(String objectId, Function<ResourceKey<R>, ? extends T> objectFactory) {
        ResourceKey<R> key = ResourceKey.create(registryKey, Identifier.fromNamespaceAndPath(modId, objectId));
        registrar.register(key, () -> objectFactory.apply(key));

        RegistryObject<R, T> registryObject = new RegistryObject<>(key);
        entries.add(registryObject);
        return registryObject;
    }

    /**
     * @return an unmodifiable view of all objects registered through this provider
     */
    public Collection<RegistryObject<R, ? extends R>> getEntries() {
        return entriesView;
    }

    public String getModId() {
        return modId;
    }

    public ResourceKey<? extends Registry<R>> getRegistryKey() {
        return registryKey;
    }

    @SuppressWarnings("unchecked")
    static <R> @Nullable Registry<R> findRegistry(ResourceKey<? extends Registry<R>> registryKey) {
        return (Registry<R>) BuiltInRegistries.REGISTRY.getValue(registryKey.identifier());
    }
}
