package net.morthen.resourcelibrary.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/**
 * Client only part of {@link ForgeNetworkHelper}, must only be loaded on the client.
 */
final class ForgeClientNetworkHelper {

    static Player player() {
        return Minecraft.getInstance().player;
    }
}
