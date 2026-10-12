package net.morthen.resourcelibrary.entity;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class FabricEntityDataHelper implements EntityDataHelper {
    private static final Map<EntityDataType<?>, AttachmentType<?>> ATTACHMENTS = new ConcurrentHashMap<>();

    @Override
    public <T> void register(EntityDataType<T> type) {
        StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec = type.streamCodec();
        AttachmentType<T> attachment = AttachmentRegistry.create(type.id(), builder -> {
            builder.persistent(type.codec());
            if (streamCodec != null) builder.syncWith(streamCodec, syncPredicate(type.syncTarget()));
            if (type.copyOnDeath()) builder.copyOnDeath();
        });
        ATTACHMENTS.put(type, attachment);
    }

    private static AttachmentSyncPredicate syncPredicate(EntityDataType.SyncTarget syncTarget) {
        return switch (syncTarget) {
            case TRACKING_AND_SELF -> AttachmentSyncPredicate.all();
            case SELF -> AttachmentSyncPredicate.targetOnly();
            case TRACKING -> AttachmentSyncPredicate.allButTarget();
        };
    }

    @SuppressWarnings("unchecked")
    private static <T> AttachmentType<T> attachment(EntityDataType<T> type) {
        return (AttachmentType<T>) Objects.requireNonNull(ATTACHMENTS.get(type), () -> type + " is not registered");
    }

    @Override
    public <T> @Nullable T get(Entity entity, EntityDataType<T> type) {
        return entity.getAttached(attachment(type));
    }

    @Override
    public boolean has(Entity entity, EntityDataType<?> type) {
        return entity.hasAttached(attachment(type));
    }

    @Override
    public <T> void set(Entity entity, EntityDataType<T> type, @Nullable T value) {
        entity.setAttached(attachment(type), value);
    }
}
