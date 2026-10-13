package net.morthen.resourcelibrary.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.morthen.resourcelibrary.LibConstants;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = LibConstants.MOD_ID)
public class NeoForgeNetworkHelper implements NetworkHelper {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        for (PayloadRegistry.Registration<?> registration : PayloadRegistry.freeze()) {
            register(registrar, registration);
        }
    }

    private static <T extends CustomPacketPayload> void register(PayloadRegistrar registrar, PayloadRegistry.Registration<T> registration) {
        if (registration.isClientbound() && registration.isServerbound()) {
            registrar.playBidirectional(registration.type(), registration.codec(),
                    (payload, context) -> registration.handleOnClient(payload, PayloadContext.of(context.player(), true)),
                    (payload, context) -> registration.handleOnServer(payload, PayloadContext.of(context.player(), false)));
        } else if (registration.isClientbound()) {
            registrar.playToClient(registration.type(), registration.codec(),
                    (payload, context) -> registration.handleOnClient(payload, PayloadContext.of(context.player(), true)));
        } else {
            registrar.playToServer(registration.type(), registration.codec(),
                    (payload, context) -> registration.handleOnServer(payload, PayloadContext.of(context.player(), false)));
        }
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        NeoForgeClientNetworkHelper.sendToServer(payload);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
    }
}
