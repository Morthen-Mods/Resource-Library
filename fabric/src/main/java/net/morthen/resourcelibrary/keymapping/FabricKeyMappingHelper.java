package net.morthen.resourcelibrary.keymapping;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

/**
 * Must only be loaded on the client.
 */
public class FabricKeyMappingHelper implements KeyMappingHelper {

    // Called in ResourceLibrary
    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> KeyMappingRegistry.onClientTick());
    }

    // Fabric sorts the categories registered through vanilla
    @Override
    public KeyMapping.Category registerCategory(Identifier id) {
        return KeyMapping.Category.register(id);
    }

    @Override
    public void register(KeyMapping keyMapping) {
        net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(keyMapping);
    }
}
