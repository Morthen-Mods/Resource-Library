package net.morthen.resourcelibrary.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.network.Networking;
import net.morthen.resourcelibrary.network.PayloadContext;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Forge has no data attachments, so the values are stored in the entity itself (see {@code EntityMixin}). Saving,
 * syncing and copying on respawn are done here, with the same behavior as the attachments of Fabric and NeoForge.
 */
public class ForgeEntityDataHelper implements EntityDataHelper {
    private static final String SAVE_KEY = LibConstants.MOD_ID + ":entity_data";

    // Called in ResourceLibrary
    public static void registerEvents() {
        PlayerEvent.Clone.BUS.addListener(ForgeEntityDataHelper::copyOnClone);
        PlayerEvent.StartTracking.BUS.addListener(event -> {
            if (event.getEntity() instanceof ServerPlayer player) syncAll(event.getTarget(), player);
        });
        // The client creates a new player entity on login, respawn and dimension change
        PlayerEvent.PlayerLoggedInEvent.BUS.addListener(event -> syncAllToSelf(event.getEntity()));
        PlayerEvent.PlayerRespawnEvent.BUS.addListener(event -> syncAllToSelf(event.getEntity()));
        PlayerEvent.PlayerChangedDimensionEvent.BUS.addListener(event -> syncAllToSelf(event.getEntity()));
    }

    @Override
    public <T> void register(EntityDataType<T> type) {
        // Only register the payload when it's needed, an unused channel would make vanilla clients unable to join
        if (type.isSynced()) {
            synchronized (ForgeEntityDataHelper.class) {
                if (!Networking.isRegistered(EntityDataPayload.TYPE)) {
                    Networking.registerClientbound(EntityDataPayload.TYPE, EntityDataPayload.STREAM_CODEC, ForgeEntityDataHelper::handleOnClient);
                }
            }
        }
    }

    private static ForgeEntityDataHolder holder(Object entity) {
        return (ForgeEntityDataHolder) entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> @Nullable T get(Entity entity, EntityDataType<T> type) {
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        return data != null ? (T) data.get(type) : null;
    }

    @Override
    public boolean has(Entity entity, EntityDataType<?> type) {
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        return data != null && data.containsKey(type);
    }

    @Override
    public <T> void set(Entity entity, EntityDataType<T> type, @Nullable T value) {
        if (!setWithoutSync(entity, type, value)) return;
        if (type.isSynced() && entity.level() instanceof ServerLevel) {
            sync(entity, type.syncTarget(), new EntityDataPayload(entity.getId(), List.of(new EntityDataPayload.Entry<>(type, value))));
        }
    }

    /**
     * @return {@code false} if nothing changed, because a value that was not set was removed
     */
    private static <T> boolean setWithoutSync(Entity entity, EntityDataType<T> type, @Nullable T value) {
        if (value != null) {
            holder(entity).resourcelibrary$getOrCreateEntityData().put(type, value);
            return true;
        }
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        return data != null && data.remove(type) != null;
    }

    private static void sync(Entity entity, EntityDataType.SyncTarget syncTarget, EntityDataPayload payload) {
        switch (syncTarget) {
            case TRACKING_AND_SELF -> {
                Networking.sendToPlayersTrackingEntity(entity, payload);
                if (entity instanceof ServerPlayer player) sendToSelf(player, payload);
            }
            case SELF -> {
                if (entity instanceof ServerPlayer player) sendToSelf(player, payload);
            }
            case TRACKING -> Networking.sendToPlayersTrackingEntity(entity, payload);
        }
    }

    private static void sendToSelf(ServerPlayer player, EntityDataPayload payload) {
        // Data can be set while the player is loaded, before it has a connection. It is synced on login instead.
        if (player.connection != null) Networking.sendToPlayer(player, payload);
    }

    private static void syncAllToSelf(Entity entity) {
        if (entity instanceof ServerPlayer player) syncAll(player, player);
    }

    /**
     * Sends all synced values of {@code entity} that {@code receiver} should know about.
     */
    private static void syncAll(Entity entity, ServerPlayer receiver) {
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        if (data == null) return;

        List<EntityDataPayload.Entry<?>> entries = new ArrayList<>();
        data.forEach((type, value) -> {
            if (type.isSynced() && type.syncTarget().test(entity, receiver)) entries.add(entry(type, value));
        });
        if (!entries.isEmpty()) Networking.sendToPlayer(receiver, new EntityDataPayload(entity.getId(), entries));
    }

    @SuppressWarnings("unchecked")
    private static <T> EntityDataPayload.Entry<T> entry(EntityDataType<T> type, Object value) {
        return new EntityDataPayload.Entry<>(type, (T) value);
    }

    private static void handleOnClient(EntityDataPayload payload, PayloadContext context) {
        Entity entity = context.player().level().getEntity(payload.entityId());
        if (entity == null) return;
        for (EntityDataPayload.Entry<?> entry : payload.entries()) {
            setWithoutSync(entity, entry);
        }
    }

    private static <T> void setWithoutSync(Entity entity, EntityDataPayload.Entry<T> entry) {
        setWithoutSync(entity, entry.type(), entry.value());
    }

    private static void copyOnClone(PlayerEvent.Clone event) {
        Map<EntityDataType<?>, Object> oldData = holder(event.getOriginal()).resourcelibrary$getEntityData();
        if (oldData == null) return;

        // Like on Fabric and NeoForge everything is kept when returning from the End, but only some data on death
        oldData.forEach((type, value) -> {
            if (!event.isWasDeath() || (event.getOriginal() instanceof ServerPlayer oldPlayer && type.shouldCopyOnDeath(oldPlayer))) {
                holder(event.getEntity()).resourcelibrary$getOrCreateEntityData().put(type, value);
            }
        });
    }

    // Called by EntityMixin
    public static void save(Object entity, ValueOutput output) {
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        if (data == null || data.isEmpty()) return;

        ValueOutput dataOutput = output.child(SAVE_KEY);
        data.forEach((type, value) -> store(dataOutput, type, value));
    }

    @SuppressWarnings("unchecked")
    private static <T> void store(ValueOutput output, EntityDataType<T> type, Object value) {
        output.store(type.id().toString(), type.codec(), (T) value);
    }

    // Called by EntityMixin
    public static void load(Object entity, ValueInput input) {
        // Load can be called on an existing entity, e.g. by /data merge, so values that are no longer saved are removed
        Map<EntityDataType<?>, Object> data = holder(entity).resourcelibrary$getEntityData();
        if (data != null) data.clear();

        input.child(SAVE_KEY).ifPresent(dataInput -> {
            for (EntityDataType<?> type : EntityDataRegistry.all()) {
                dataInput.read(type.id().toString(), type.codec())
                        .ifPresent(value -> holder(entity).resourcelibrary$getOrCreateEntityData().put(type, value));
            }
        });
    }
}
