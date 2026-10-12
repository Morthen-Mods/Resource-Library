package net.morthen.resourcelibrary.entity;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registers {@link EntityDataType}s for one mod on every loader.
 *
 * <pre>{@code
 * public record Mana(int value, int max) {
 *     public static final Codec<Mana> CODEC = RecordCodecBuilder.create(instance -> instance.group(
 *             Codec.INT.fieldOf("value").forGetter(Mana::value),
 *             Codec.INT.fieldOf("max").forGetter(Mana::max)
 *     ).apply(instance, Mana::new));
 *     public static final StreamCodec<ByteBuf, Mana> STREAM_CODEC = StreamCodec.composite(
 *             ByteBufCodecs.VAR_INT, Mana::value,
 *             ByteBufCodecs.VAR_INT, Mana::max,
 *             Mana::new);
 * }
 *
 * public static final EntityDataRegistry ENTITY_DATA = EntityDataRegistry.create(MOD_ID);
 *
 * public static final EntityDataType<Mana> MANA = ENTITY_DATA.register("mana",
 *         EntityDataType.builder(Mana.CODEC, new Mana(0, 100)).sync(Mana.STREAM_CODEC).copyOnDeath());
 * }</pre>
 *
 * Types must be registered during mod initialization (mod constructor / initializer) on both client and server.
 */
public final class EntityDataRegistry {
    private static final Map<Identifier, EntityDataType<?>> TYPES = new ConcurrentHashMap<>();

    private final String modId;
    private final List<EntityDataType<?>> entries = new ArrayList<>();
    private final Collection<EntityDataType<?>> entriesView = Collections.unmodifiableList(entries);

    private EntityDataRegistry(String modId) {
        this.modId = modId;
    }

    public static EntityDataRegistry create(String modId) {
        return new EntityDataRegistry(modId);
    }

    /**
     * @throws IllegalArgumentException if a type with the same id is already registered
     */
    public <T> EntityDataType<T> register(String id, EntityDataType.Builder<T> builder) {
        EntityDataType<T> type = builder.build(Identifier.fromNamespaceAndPath(modId, id));
        if (TYPES.putIfAbsent(type.id(), type) != null) {
            throw new IllegalArgumentException("Entity data '" + type.id() + "' is already registered!");
        }
        EntityDataHelper.INSTANCE.register(type);
        entries.add(type);
        return type;
    }

    /**
     * @return an unmodifiable view of all types registered through this registry
     */
    public Collection<EntityDataType<?>> getEntries() {
        return entriesView;
    }

    public String getModId() {
        return modId;
    }

    static @Nullable EntityDataType<?> byId(Identifier id) {
        return TYPES.get(id);
    }

    static Collection<EntityDataType<?>> all() {
        return Collections.unmodifiableCollection(TYPES.values());
    }
}
