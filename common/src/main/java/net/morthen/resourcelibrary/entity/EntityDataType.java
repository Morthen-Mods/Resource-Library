package net.morthen.resourcelibrary.entity;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Data attached to entities, saved together with the entity and optionally synced to clients. Created through an
 * {@link EntityDataRegistry}.
 * <p>
 * Values are immutable, like data components: to change them, set a new value. Only changes made through
 * {@link #set} or {@link #update} on the server are synced.
 *
 * <pre>{@code
 * Mana mana = MANA.get(player);
 * MANA.set(player, new Mana(mana.value() + 5, mana.max()));
 * MANA.update(player, mana -> new Mana(mana.value() + 5, mana.max()));
 * }</pre>
 *
 * Values are saved with {@link #codec()}. On Fabric and NeoForge the loader's data attachments are used, so the data
 * also shows up there under {@link #id()}.
 *
 * @param <T> type of the value, should be immutable
 */
@SuppressWarnings("unused")
public final class EntityDataType<T> {
    private static final Predicate<ServerPlayer> ALWAYS = player -> true;

    private final Identifier id;
    private final Codec<T> codec;
    private final T defaultValue;
    private final @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
    private final SyncTarget syncTarget;
    private final @Nullable Predicate<ServerPlayer> copyOnDeath;

    private EntityDataType(Identifier id, Builder<T> builder) {
        this.id = id;
        this.codec = builder.codec;
        this.defaultValue = builder.defaultValue;
        this.streamCodec = builder.streamCodec;
        this.syncTarget = builder.syncTarget;
        this.copyOnDeath = builder.copyOnDeath;
    }

    /**
     * @param codec        saves the value together with the entity
     * @param defaultValue returned by {@link #get} as long as the entity has no value
     */
    public static <T> Builder<T> builder(Codec<T> codec, T defaultValue) {
        return new Builder<>(codec, defaultValue);
    }

    /**
     * @return the value of {@code entity}, or {@link #defaultValue()} if it has none
     */
    public T get(Entity entity) {
        T value = EntityDataHelper.INSTANCE.get(entity, this);
        return value != null ? value : defaultValue;
    }

    /**
     * @return {@code true} if a value was set on {@code entity}, even if it equals the default value
     */
    public boolean has(Entity entity) {
        return EntityDataHelper.INSTANCE.has(entity, this);
    }

    public void set(Entity entity, T value) {
        EntityDataHelper.INSTANCE.set(entity, this, Objects.requireNonNull(value, "value"));
    }

    /**
     * Sets the value returned by {@code updater}, which receives the current value (or the default value).
     *
     * @return the new value
     */
    public T update(Entity entity, UnaryOperator<T> updater) {
        T value = Objects.requireNonNull(updater.apply(get(entity)), "updated value");
        EntityDataHelper.INSTANCE.set(entity, this, value);
        return value;
    }

    /**
     * Removes the value, so {@link #get} returns the default value again.
     */
    public void remove(Entity entity) {
        EntityDataHelper.INSTANCE.set(entity, this, null);
    }

    public Identifier id() {
        return id;
    }

    public Codec<T> codec() {
        return codec;
    }

    public T defaultValue() {
        return defaultValue;
    }

    /**
     * @return the codec for syncing, or {@code null} if the data is not synced
     */
    public @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
    }

    public boolean isSynced() {
        return streamCodec != null;
    }

    public SyncTarget syncTarget() {
        return syncTarget;
    }

    /**
     * @param player the player that died
     * @return {@code true} if the value of {@code player} is kept when it respawns
     */
    public boolean shouldCopyOnDeath(ServerPlayer player) {
        return copyOnDeath != null && copyOnDeath.test(player);
    }

    // The loaders can copy these values on death themselves
    boolean alwaysCopyOnDeath() {
        return copyOnDeath == ALWAYS;
    }

    // The library has to check the condition and copy these values on death
    boolean hasCopyOnDeathCondition() {
        return copyOnDeath != null && copyOnDeath != ALWAYS;
    }

    @Override
    public String toString() {
        return "EntityDataType[" + id + "]";
    }

    /**
     * The clients a synced value is sent to.
     */
    public enum SyncTarget {
        /**
         * All players that track the entity and the entity itself if it is a player.
         */
        TRACKING_AND_SELF,
        /**
         * Only the entity itself, so it is never synced for entities that are not players. Use it for data other
         * players don't need, e.g. for a HUD.
         */
        SELF,
        /**
         * All players that track the entity, but not the entity itself.
         */
        TRACKING;

        /**
         * @return {@code true} if the value of {@code entity} is sent to {@code player}
         */
        public boolean test(Entity entity, ServerPlayer player) {
            return switch (this) {
                case TRACKING_AND_SELF -> true;
                case SELF -> entity == player;
                case TRACKING -> entity != player;
            };
        }
    }

    public static final class Builder<T> {
        private final Codec<T> codec;
        private final T defaultValue;
        private @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec;
        private SyncTarget syncTarget = SyncTarget.TRACKING_AND_SELF;
        private @Nullable Predicate<ServerPlayer> copyOnDeath;

        private Builder(Codec<T> codec, T defaultValue) {
            this.codec = Objects.requireNonNull(codec, "codec");
            this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
        }

        /**
         * Syncs the value to all players that track the entity and to the entity itself if it is a player.
         */
        public Builder<T> sync(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
            return sync(streamCodec, SyncTarget.TRACKING_AND_SELF);
        }

        public Builder<T> sync(StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, SyncTarget syncTarget) {
            this.streamCodec = Objects.requireNonNull(streamCodec, "streamCodec");
            this.syncTarget = Objects.requireNonNull(syncTarget, "syncTarget");
            return this;
        }

        /**
         * Keeps the value when a player dies and respawns. Without it the value is only kept when a player returns
         * from the End.
         */
        public Builder<T> copyOnDeath() {
            this.copyOnDeath = ALWAYS;
            return this;
        }

        /**
         * Keeps the value when a player dies and respawns, but only if {@code condition} is true for the player that
         * died. Without it the value is only kept when a player returns from the End.
         *
         * <pre>{@code
         * .copyOnDeath(player -> player.level().getGameRules().get(GameRules.KEEP_INVENTORY))
         * .copyOnDeath(player -> MyConfig.keepManaOnDeath)
         * }</pre>
         */
        public Builder<T> copyOnDeath(Predicate<ServerPlayer> condition) {
            this.copyOnDeath = Objects.requireNonNull(condition, "condition");
            return this;
        }

        EntityDataType<T> build(Identifier id) {
            return new EntityDataType<>(id, this);
        }
    }
}
