package net.morthen.resourcelibrary.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

/**
 * Loader specific part of {@link KeyMappingRegistry}. Use {@link KeyMappingRegistry} instead of calling this directly.
 */
public interface KeyMappingHelper {

    /**
     * Called once per id. Key mappings compare categories by identity, so only the returned instance must be used.
     */
    KeyMapping.Category registerCategory(Identifier id);

    /**
     * @throws IllegalStateException if key mappings were already registered with the loader
     */
    void register(KeyMapping keyMapping);
}
