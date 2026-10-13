package net.morthen.resourcelibrary.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface PayloadContext {

    /**
     * @return the receiving player on the client, or the sending player on the server
     */
    Player player();

    /**
     * @return {@code true} if the payload was received by the client
     */
    boolean isClientSide();

    /**
     * @throws IllegalStateException if called on the client
     */
    default ServerPlayer serverPlayer() {
        if (player() instanceof ServerPlayer serverPlayer) return serverPlayer;
        throw new IllegalStateException("No ServerPlayer available on the client");
    }

    /**
     * Sends a payload back to the other side of the connection.
     */
    default void reply(CustomPacketPayload payload) {
        if (isClientSide()) Networking.sendToServer(payload);
        else Networking.sendToPlayer(serverPlayer(), payload);
    }

    static PayloadContext of(Player player, boolean isClientSide) {
        return new SimplePayloadContext(player, isClientSide);
    }

    record SimplePayloadContext(Player player, boolean isClientSide) implements PayloadContext {}
}
