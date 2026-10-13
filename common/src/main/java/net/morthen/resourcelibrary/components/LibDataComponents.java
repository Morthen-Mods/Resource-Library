package net.morthen.resourcelibrary.components;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.registration.RegistryProvider;

/**
 * Data components provided by the library.
 * <p>
 * The types are created eagerly, so they can be used in {@code Item.Properties} on every loader, no matter whether
 * the data component registry was already filled.
 */
public final class LibDataComponents {
    private static final RegistryProvider<DataComponentType<?>> DATA_COMPONENT_TYPES = RegistryProvider.create(LibConstants.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    /**
     * Tooltip lines for items, see {@link ItemTooltip}.
     */
    public static final DataComponentType<ItemTooltip> TOOLTIP = register("tooltip",
            DataComponentType.<ItemTooltip>builder().persistent(ItemTooltip.CODEC)
            .networkSynchronized(ItemTooltip.STREAM_CODEC).cacheEncoding().build());

    private static <T> DataComponentType<T> register(String id, DataComponentType<T> type) {
        DATA_COMPONENT_TYPES.register(id, () -> type);
        return type;
    }

    /**
     * Loads this class, which registers the data components. Called by each loader during mod initialization.
     */
    public static void init() {}
}
