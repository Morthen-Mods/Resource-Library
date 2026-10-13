package net.morthen.resourcelibrary.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class FabricNetworkHelper implements NetworkHelper {

    // Called once all mods ran their main entrypoint
    public static void postInit() {
        boolean client = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
        for (PayloadRegistry.Registration<?> registration : PayloadRegistry.freeze()) {
            register(registration, client);
        }
    }

    private static <T extends CustomPacketPayload> void register(PayloadRegistry.Registration<T> registration, boolean client) {
        if (registration.isClientbound()) {
            PayloadTypeRegistry.clientboundPlay().register(registration.type(), registration.codec());
            if (client) FabricClientNetworkHelper.registerReceiver(registration);
        }
        if (registration.isServerbound()) {
            PayloadTypeRegistry.serverboundPlay().register(registration.type(), registration.codec());
            ServerPlayNetworking.registerGlobalReceiver(registration.type(), (payload, context) ->
                    registration.handleOnServer(payload, PayloadContext.of(context.player(), false)));
        }
    }

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        FabricClientNetworkHelper.sendToServer(payload);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(player, payload);
        }
    }
}
