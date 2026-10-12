package net.morthen.resourcelibrary.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Client only part of {@link FabricNetworkHelper}, must only be loaded on the client.
 */
final class FabricClientNetworkHelper {

    private FabricClientNetworkHelper() {}

    static <T extends CustomPacketPayload> void registerReceiver(PayloadRegistry.Registration<T> registration) {
        ClientPlayNetworking.registerGlobalReceiver(registration.type(), (payload, context) ->
                registration.handleOnClient(payload, PayloadContext.of(context.player(), true)));
    }

    static void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}
