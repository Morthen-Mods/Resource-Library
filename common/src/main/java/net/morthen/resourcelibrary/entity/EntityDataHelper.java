package net.morthen.resourcelibrary.entity;

import net.minecraft.world.entity.Entity;
import net.morthen.resourcelibrary.service.LibServices;
import org.jetbrains.annotations.Nullable;

/**
 * Loader specific part of {@link EntityDataType}. Use {@link EntityDataRegistry} and {@link EntityDataType} instead
 * of calling this directly.
 */
public interface EntityDataHelper {
    EntityDataHelper INSTANCE = LibServices.load(EntityDataHelper.class);

    <T> void register(EntityDataType<T> type);

    /**
     * @return the value set on {@code entity}, or {@code null} if it has none
     */
    <T> @Nullable T get(Entity entity, EntityDataType<T> type);

    boolean has(Entity entity, EntityDataType<?> type);

    /**
     * Sets the value and syncs it if the type is synced and this is called on the server.
     *
     * @param value the new value, or {@code null} to remove it
     */
    <T> void set(Entity entity, EntityDataType<T> type, @Nullable T value);
}
