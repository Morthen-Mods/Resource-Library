package net.morthen.resourcelibrary.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morthen.resourcelibrary.LibConstants;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod.EventBusSubscriber(modid = LibConstants.MOD_ID, value = Dist.CLIENT)
public class ForgeKeyMappingHelper implements KeyMappingHelper {
    private static final List<KeyMapping> KEY_MAPPINGS = new CopyOnWriteArrayList<>();

    private static volatile boolean registered = false;

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        registered = true;
        KEY_MAPPINGS.forEach(event::register);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        KeyMappingRegistry.onClientTick();
    }

    // Forge has no category registration of its own, it uses the vanilla one
    @Override
    public KeyMapping.Category registerCategory(Identifier id) {
        return KeyMapping.Category.register(id);
    }

    @Override
    public void register(KeyMapping keyMapping) {
        if (registered) {
            throw new IllegalStateException("Key mapping '" + keyMapping.getName() + "' was registered too late. "
                    + "Key mappings must be registered during mod initialization (mod constructor).");
        }
        KEY_MAPPINGS.add(keyMapping);
    }
}
