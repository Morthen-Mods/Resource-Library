package net.morthen.resourcelibrary.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Handles a received payload. Handlers are always executed on the main thread (client or server thread).
 */
@FunctionalInterface
public interface PayloadHandler<T extends CustomPacketPayload> {
    void handle(T payload, PayloadContext context);
}
