package net.morthen.resourcelibrary.entity;

import io.netty.handler.codec.DecoderException;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.LibConstants;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Syncs {@link EntityDataType} values of one entity to the client, only used on Forge.
 */
record EntityDataPayload(int entityId, List<Entry<?>> entries) implements CustomPacketPayload {
    static final Type<EntityDataPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "entity_data"));
    static final StreamCodec<RegistryFriendlyByteBuf, EntityDataPayload> STREAM_CODEC = StreamCodec.of(EntityDataPayload::write, EntityDataPayload::read);

    @Override
    public Type<EntityDataPayload> type() {
        return TYPE;
    }

    private static void write(RegistryFriendlyByteBuf buf, EntityDataPayload payload) {
        buf.writeVarInt(payload.entityId);
        buf.writeVarInt(payload.entries.size());
        for (Entry<?> entry : payload.entries) {
            entry.write(buf);
        }
    }

    private static EntityDataPayload read(RegistryFriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        int size = buf.readVarInt();
        List<Entry<?>> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Identifier id = buf.readIdentifier();
            EntityDataType<?> type = EntityDataRegistry.byId(id);
            if (type == null || !type.isSynced()) {
                throw new DecoderException("Received unknown synced entity data '" + id + "'");
            }
            entries.add(Entry.read(buf, type));
        }
        return new EntityDataPayload(entityId, entries);
    }

    /**
     * @param value the new value, or {@code null} if it was removed
     */
    record Entry<T>(EntityDataType<T> type, @Nullable T value) {

        private void write(RegistryFriendlyByteBuf buf) {
            buf.writeIdentifier(type.id());
            buf.writeBoolean(value != null);
            if (value != null) streamCodec(type).encode(buf, value);
        }

        private static <T> Entry<T> read(RegistryFriendlyByteBuf buf, EntityDataType<T> type) {
            T value = buf.readBoolean() ? streamCodec(type).decode(buf) : null;
            return new Entry<>(type, value);
        }

        private static <T> StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec(EntityDataType<T> type) {
            return Objects.requireNonNull(type.streamCodec(), () -> type + " is not synced");
        }
    }
}
