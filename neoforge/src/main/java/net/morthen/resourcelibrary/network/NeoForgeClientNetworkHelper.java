package net.morthen.resourcelibrary.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

/**
 * Client only part of {@link NeoForgeNetworkHelper}, must only be loaded on the client.
 */
final class NeoForgeClientNetworkHelper {

    static void sendToServer(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
}
