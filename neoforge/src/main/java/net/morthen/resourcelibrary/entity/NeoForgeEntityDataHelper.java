package net.morthen.resourcelibrary.entity;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class NeoForgeEntityDataHelper implements EntityDataHelper {
    private static final Map<EntityDataType<?>, AttachmentType<?>> ATTACHMENTS = new ConcurrentHashMap<>();

    private static volatile boolean registered = false;

    // Registered on the mod bus in ResourceLibrary
    public static void registerAttachmentTypes(RegisterEvent event) {
        event.register(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, helper -> {
            registered = true;
            ATTACHMENTS.forEach((type, attachment) -> helper.register(type.id(), attachment));
        });
    }

    // Registered on the game bus in ResourceLibrary. NeoForge only knows unconditional copyOnDeath, so values with a
    // condition are copied here.
    public static void copyOnDeathWithCondition(PlayerEvent.Clone event) {
        if (!event.isWasDeath() || !(event.getOriginal() instanceof ServerPlayer oldPlayer)) return;

        ATTACHMENTS.keySet().forEach(type -> {
            if (type.hasCopyOnDeathCondition() && type.shouldCopyOnDeath(oldPlayer)) {
                copy(type, oldPlayer, event.getEntity());
            }
        });
    }

    private static <T> void copy(EntityDataType<T> type, Entity from, Entity to) {
        T value = from.getExistingDataOrNull(attachment(type));
        if (value != null) to.setData(attachment(type), value);
    }

    @Override
    public <T> void register(EntityDataType<T> type) {
        if (registered) {
            throw new IllegalStateException("Entity data '" + type.id() + "' was registered too late. "
                    + "Entity data must be registered during mod initialization (mod constructor).");
        }

        AttachmentType.Builder<T> builder = AttachmentType.builder(type::defaultValue)
                .serialize(type.codec().fieldOf("value"));
        StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec = type.streamCodec();
        if (streamCodec != null) {
            builder.sync((holder, player) -> holder instanceof Entity entity && type.syncTarget().test(entity, player), streamCodec);
        }
        if (type.alwaysCopyOnDeath()) builder.copyOnDeath();
        ATTACHMENTS.put(type, builder.build());
    }

    @SuppressWarnings("unchecked")
    private static <T> AttachmentType<T> attachment(EntityDataType<T> type) {
        return (AttachmentType<T>) Objects.requireNonNull(ATTACHMENTS.get(type), () -> type + " is not registered");
    }

    @Override
    public <T> @Nullable T get(Entity entity, EntityDataType<T> type) {
        return entity.getExistingDataOrNull(attachment(type));
    }

    @Override
    public boolean has(Entity entity, EntityDataType<?> type) {
        return entity.hasData(attachment(type));
    }

    @Override
    public <T> void set(Entity entity, EntityDataType<T> type, @Nullable T value) {
        if (value != null) entity.setData(attachment(type), value);
        else entity.removeData(attachment(type));
    }
}
