package net.morthen.resourcelibrary.entity;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Storage of the {@link EntityDataType} values of an entity, added to {@code Entity} by {@code EntityMixin}.
 */
public interface ForgeEntityDataHolder {

    /**
     * @return the values of this entity, or {@code null} if no value was set yet
     */
    @Nullable Map<EntityDataType<?>, Object> resourcelibrary$getEntityData();

    Map<EntityDataType<?>, Object> resourcelibrary$getOrCreateEntityData();
}
