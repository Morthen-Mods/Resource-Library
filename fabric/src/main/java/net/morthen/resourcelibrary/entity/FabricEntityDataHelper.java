package net.morthen.resourcelibrary.entity;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class FabricEntityDataHelper implements EntityDataHelper {
    private static final Map<EntityDataType<?>, AttachmentType<?>> ATTACHMENTS = new ConcurrentHashMap<>();

    // Called in ResourceLibrary
    public static void registerEvents() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) copyOnDeathWithCondition(oldPlayer, newPlayer);
        });
    }

    // Fabric only knows unconditional copyOnDeath, so values with a condition are copied here
    private static void copyOnDeathWithCondition(ServerPlayer oldPlayer, ServerPlayer newPlayer) {
        ATTACHMENTS.keySet().forEach(type -> {
            if (type.hasCopyOnDeathCondition() && type.shouldCopyOnDeath(oldPlayer)) copy(type, oldPlayer, newPlayer);
        });
    }

    private static <T> void copy(EntityDataType<T> type, Entity from, Entity to) {
        T value = from.getAttached(attachment(type));
        if (value != null) to.setAttached(attachment(type), value);
    }

    @Override
    public <T> void register(EntityDataType<T> type) {
        StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec = type.streamCodec();
        AttachmentType<T> attachment = AttachmentRegistry.create(type.id(), builder -> {
            builder.persistent(type.codec());
            if (streamCodec != null) builder.syncWith(streamCodec, syncPredicate(type.syncTarget()));
            if (type.alwaysCopyOnDeath()) builder.copyOnDeath();
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
