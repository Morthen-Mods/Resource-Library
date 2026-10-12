package net.morthen.resourcelibrary.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.LibConstants;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Internal storage for payloads registered through {@link Networking}.
 * <p>
 * Each loader registers the payloads with its own networking API once during startup (Fabric: after all mods were
 * initialized, NeoForge: {@code RegisterPayloadHandlersEvent}, Forge: {@code FMLCommonSetupEvent}) and calls
 * {@link #freeze()} for that. Registering afterward would never reach the loader, so it throws instead.
 */
final class PayloadRegistry {
    private static final Object LOCK = new Object();

    // guarded by LOCK
    private static final Map<Identifier, Registration<?>> REGISTRATIONS = new LinkedHashMap<>();
    // guarded by LOCK
    private static boolean frozen = false;

    static <T extends CustomPacketPayload> void add(Registration<T> registration) {
        synchronized (LOCK) {
            if (frozen) {
                throw new IllegalStateException("Payload '" + registration.type().id() + "' was registered too late. "
                        + "Payloads must be registered during mod initialization (mod constructor / initializer).");
            }
            if (REGISTRATIONS.putIfAbsent(registration.type().id(), registration) != null) {
                throw new IllegalArgumentException("Payload '" + registration.type().id() + "' is already registered!");
            }
        }
    }

    /**
     * Closes registration and returns all registered payloads in registration order.
     */
    static List<Registration<?>> freeze() {
        synchronized (LOCK) {
            if (!frozen) {
                frozen = true;
                LibConstants.LOG.debug("Payload registration closed with {} payloads", REGISTRATIONS.size());
            }
            return List.copyOf(REGISTRATIONS.values());
        }
    }

    static Map<Identifier, Registration<?>> registrations() {
        synchronized (LOCK) {
            return Collections.unmodifiableMap(new LinkedHashMap<>(REGISTRATIONS));
        }
    }

    /**
     * @param clientHandler set if the payload can be sent to the client
     * @param serverHandler set if the payload can be sent to the server
     */
    record Registration<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
                                                       StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
                                                       @Nullable PayloadHandler<T> clientHandler,
                                                       @Nullable PayloadHandler<T> serverHandler) {
        Registration {
            Objects.requireNonNull(type, "type");
            Objects.requireNonNull(codec, "codec");
            if (clientHandler == null && serverHandler == null) {
                throw new IllegalArgumentException("Payload '" + type.id() + "' needs at least one handler");
            }
        }

        boolean isClientbound() {
            return clientHandler != null;
        }

        boolean isServerbound() {
            return serverHandler != null;
        }

        void handleOnClient(T payload, PayloadContext context) {
            Objects.requireNonNull(clientHandler, "clientHandler").handle(payload, context);
        }

        void handleOnServer(T payload, PayloadContext context) {
            Objects.requireNonNull(serverHandler, "serverHandler").handle(payload, context);
        }
    }
}
