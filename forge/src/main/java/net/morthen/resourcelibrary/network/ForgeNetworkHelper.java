package net.morthen.resourcelibrary.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;
import net.morthen.resourcelibrary.LibConstants;

import java.util.List;

public class ForgeNetworkHelper implements NetworkHelper {
    private static final Identifier CHANNEL_NAME = Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "payloads");

    private static volatile Channel<CustomPacketPayload> channel;

    // Registered on the mod bus in ResourceLibrary
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ForgeNetworkHelper::buildChannel);
    }

    private static void buildChannel() {
        List<PayloadRegistry.Registration<?>> registrations = PayloadRegistry.freeze();
        // Don't create a channel nobody uses, it would make vanilla clients unable to join
        if (registrations.isEmpty()) return;

        PayloadProtocol<RegistryFriendlyByteBuf, CustomPacketPayload> protocol = ChannelBuilder.named(CHANNEL_NAME)
                .networkProtocolVersion(protocolVersion(registrations))
                .payloadChannel()
                .play();

        PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> flow = null;
        for (PayloadRegistry.Registration<?> registration : registrations) {
            flow = register(protocol, registration);
        }
        channel = flow.build();
    }

    // Both sides have to know the same payloads, so a different set of payloads is a different protocol
    private static int protocolVersion(List<PayloadRegistry.Registration<?>> registrations) {
        return registrations.stream()
                .map(registration -> registration.type().id().toString())
                .sorted()
                .toList()
                .hashCode();
    }

    @SuppressWarnings("unchecked")
    private static <T extends CustomPacketPayload> PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> register(
            PayloadProtocol<RegistryFriendlyByteBuf, CustomPacketPayload> protocol, PayloadRegistry.Registration<T> registration) {
        // Forge requires the exact buffer type, decoding a RegistryFriendlyByteBuf with a codec for a super type is safe
        StreamCodec<RegistryFriendlyByteBuf, T> codec = (StreamCodec<RegistryFriendlyByteBuf, T>) registration.codec();

        PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> flow;
        if (registration.isClientbound() && registration.isServerbound()) flow = protocol.bidirectional();
        else if (registration.isClientbound()) flow = protocol.clientbound();
        else flow = protocol.serverbound();

        return flow.addMain(registration.type(), codec, (payload, context) -> handle(registration, payload, context));
    }

    private static <T extends CustomPacketPayload> void handle(PayloadRegistry.Registration<T> registration, T payload, CustomPayloadEvent.Context context) {
        if (context.isClientSide()) {
            registration.handleOnClient(payload, PayloadContext.of(ForgeClientNetworkHelper.player(), true));
        } else {
            registration.handleOnServer(payload, PayloadContext.of(context.getSender(), false));
        }
    }

    private static Channel<CustomPacketPayload> channel() {
        Channel<CustomPacketPayload> channel = ForgeNetworkHelper.channel;
        if (channel == null) {
            throw new IllegalStateException("No payloads registered or networking is not initialized yet");
        }
        return channel;
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        channel().send(payload, PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        channel().send(payload, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        channel().send(payload, PacketDistributor.TRACKING_ENTITY.with(entity));
    }
}
