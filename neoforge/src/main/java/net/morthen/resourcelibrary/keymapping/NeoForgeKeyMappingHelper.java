package net.morthen.resourcelibrary.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.LibConstants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@EventBusSubscriber(modid = LibConstants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeKeyMappingHelper implements KeyMappingHelper {
    private static final List<KeyMapping.Category> CATEGORIES = new CopyOnWriteArrayList<>();
    private static final List<KeyMapping> KEY_MAPPINGS = new CopyOnWriteArrayList<>();

    private static volatile boolean registered = false;

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        registered = true;
        CATEGORIES.forEach(event::registerCategory);
        KEY_MAPPINGS.forEach(event::register);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        KeyMappingRegistry.onClientTick();
    }

    // NeoForge sorts its own registered categories, registering them through vanilla too would add them twice
    @Override
    public KeyMapping.Category registerCategory(Identifier id) {
        checkNotRegistered("Key mapping category '" + id + "'");
        KeyMapping.Category category = new KeyMapping.Category(id);
        CATEGORIES.add(category);
        return category;
    }

    @Override
    public void register(KeyMapping keyMapping) {
        checkNotRegistered("Key mapping '" + keyMapping.getName() + "'");
        KEY_MAPPINGS.add(keyMapping);
    }

    private static void checkNotRegistered(String name) {
        if (registered) {
            throw new IllegalStateException(name + " was registered too late. "
                    + "Key mappings must be registered during mod initialization (mod constructor).");
        }
    }
}
