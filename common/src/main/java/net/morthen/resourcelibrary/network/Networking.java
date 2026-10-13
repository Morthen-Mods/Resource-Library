package net.morthen.resourcelibrary.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.morthen.resourcelibrary.service.LibServices;
import org.jetbrains.annotations.Nullable;

/**
 * Loader independent networking for play phase payloads.
 * <p>
 * Payloads are registered once from common code, the library takes care of registering them with Fabric, NeoForge
 * or Forge. Registration must happen during mod initialization (mod constructor / initializer); registering later
 * throws an {@link IllegalStateException}. Handlers are always executed on the main thread.
 * <p>
 * Client handlers are stored in common code, so they must not reference client only classes directly. Delegate to a
 * separate client class instead, e.g. {@code (payload, context) -> MyClientHandlers.handleSync(payload)}.
 *
 * <pre>{@code
 * public record SyncPayload(int value) implements CustomPacketPayload {
 *     public static final Type<SyncPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(MOD_ID, "sync"));
 *     public static final StreamCodec<ByteBuf, SyncPayload> CODEC = ByteBufCodecs.VAR_INT.map(SyncPayload::new, SyncPayload::value);
 *
 *     @Override
 *     public Type<SyncPayload> type() {
 *         return TYPE;
 *     }
 * }
 *
 * Networking.registerClientbound(SyncPayload.TYPE, SyncPayload.CODEC, (payload, context) -> ...);
 * Networking.sendToPlayer(player, new SyncPayload(42));
 * }</pre>
 */
@SuppressWarnings("unused")
public final class Networking {
    private static final NetworkHelper HELPER = LibServices.load(NetworkHelper.class);

    /**
     * Registers a payload sent from the server to the client.
     *
     * @throws IllegalStateException    if payloads were already registered with the loader
     * @throws IllegalArgumentException if a payload with the same id is already registered
     */
    public static <T extends CustomPacketPayload> void registerClientbound(CustomPacketPayload.Type<T> type,
                                                                           StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
                                                                           PayloadHandler<T> clientHandler) {
        PayloadRegistry.add(new PayloadRegistry.Registration<>(type, codec, clientHandler, null));
    }

    /**
     * Registers a payload sent from the client to the server.
     *
     * @throws IllegalStateException    if payloads were already registered with the loader
     * @throws IllegalArgumentException if a payload with the same id is already registered
     */
    public static <T extends CustomPacketPayload> void registerServerbound(CustomPacketPayload.Type<T> type,
                                                                           StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
                                                                           PayloadHandler<T> serverHandler) {
        PayloadRegistry.add(new PayloadRegistry.Registration<>(type, codec, null, serverHandler));
    }

    /**
     * Registers a payload that can be sent in both directions.
     *
     * @throws IllegalStateException    if payloads were already registered with the loader
     * @throws IllegalArgumentException if a payload with the same id is already registered
     */
    public static <T extends CustomPacketPayload> void registerBidirectional(CustomPacketPayload.Type<T> type,
                                                                             StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
                                                                             PayloadHandler<T> clientHandler,
                                                                             PayloadHandler<T> serverHandler) {
        PayloadRegistry.add(new PayloadRegistry.Registration<>(type, codec, clientHandler, serverHandler));
    }

    /**
     * @return {@code true} if a payload with the id of {@code type} is registered
     */
    public static boolean isRegistered(CustomPacketPayload.Type<?> type) {
        return PayloadRegistry.registrations().containsKey(type.id());
    }

    /**
     * Sends a payload to the server. Must only be called on the client.
     */
    public static void sendToServer(CustomPacketPayload payload) {
        HELPER.sendToServer(payload);
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        HELPER.sendToPlayer(player, payload);
    }

    public static void sendToAllPlayers(MinecraftServer server, CustomPacketPayload payload) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            HELPER.sendToPlayer(player, payload);
        }
    }

    public static void sendToPlayersInDimension(ServerLevel level, CustomPacketPayload payload) {
        for (ServerPlayer player : level.players()) {
            HELPER.sendToPlayer(player, payload);
        }
    }

    /**
     * Sends a payload to all players in {@code level} within {@code radius} blocks of the given position.
     *
     * @param excluded player that does not receive the payload, e.g. the player that caused it
     */
    public static void sendToPlayersNear(ServerLevel level, @Nullable ServerPlayer excluded, double x, double y, double z,
                                         double radius, CustomPacketPayload payload) {
        double radiusSqr = radius * radius;
        for (ServerPlayer player : level.players()) {
            if (player != excluded && player.distanceToSqr(x, y, z) <= radiusSqr) {
                HELPER.sendToPlayer(player, payload);
            }
        }
    }

    /**
     * Sends a payload to all players that track {@code entity}, not including the entity itself.
     */
    public static void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        HELPER.sendToPlayersTrackingEntity(entity, payload);
    }

    /**
     * Sends a payload to all players that track {@code entity} and to the entity itself if it is a player.
     */
    public static void sendToPlayersTrackingEntityAndSelf(Entity entity, CustomPacketPayload payload) {
        HELPER.sendToPlayersTrackingEntity(entity, payload);
        if (entity instanceof ServerPlayer player) {
            HELPER.sendToPlayer(player, payload);
        }
    }
}
