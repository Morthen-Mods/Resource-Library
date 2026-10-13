package net.morthen.resourcelibrary.keymapping;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.morthen.resourcelibrary.service.LibServices;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Registers {@link KeyMapping}s for one mod on every loader. All key mappings of a registry share one category.
 *
 * <pre>{@code
 * public static final KeyMappingRegistry KEY_MAPPINGS = KeyMappingRegistry.create(MOD_ID);
 *
 * // name "key.<modid>.zoom", category "key.category.<modid>.controls"
 * public static final KeyMapping ZOOM = KEY_MAPPINGS.register("zoom", InputConstants.KEY_Y);
 *
 * // runs on the client thread whenever the key was pressed
 * public static final KeyMapping OPEN_BACKPACK = KEY_MAPPINGS.register("open_backpack", InputConstants.KEY_B,
 *         () -> Networking.sendToServer(new OpenBackpackPayload()));
 * }</pre>
 *
 * Key mappings are client only, so this class must only be used on the client. Key mappings must be registered during
 * client initialization (mod constructor / client initializer); registering later throws an
 * {@link IllegalStateException}.
 */
@SuppressWarnings("unused")
public final class KeyMappingRegistry {
    private static final KeyMappingHelper HELPER = LibServices.load(KeyMappingHelper.class);
    private static final String DEFAULT_CATEGORY = "controls";
    private static final Object LOCK = new Object();

    // guarded by LOCK
    private static final Map<Identifier, KeyMapping.Category> CATEGORIES = new HashMap<>();
    // guarded by LOCK
    private static final Set<String> NAMES = new HashSet<>();
    private static final List<PressHandler> PRESS_HANDLERS = new CopyOnWriteArrayList<>();

    private final String modId;
    private final KeyMapping.Category category;
    private final List<KeyMapping> entries = new ArrayList<>();
    private final Collection<KeyMapping> entriesView = Collections.unmodifiableList(entries);

    private KeyMappingRegistry(String modId, KeyMapping.Category category) {
        this.modId = modId;
        this.category = category;
    }

    /**
     * Creates a registry with the category {@code <modId>:controls}, translated through
     * {@code key.category.<modId>.controls}.
     */
    public static KeyMappingRegistry create(String modId) {
        return create(modId, DEFAULT_CATEGORY);
    }

    /**
     * Creates a registry with the category {@code <modId>:<category>}, translated through
     * {@code key.category.<modId>.<category>}. Registries with the same category share it.
     */
    public static KeyMappingRegistry create(String modId, String category) {
        return new KeyMappingRegistry(modId, category(Identifier.fromNamespaceAndPath(modId, category)));
    }

    private static KeyMapping.Category category(Identifier id) {
        synchronized (LOCK) {
            return CATEGORIES.computeIfAbsent(id, HELPER::registerCategory);
        }
    }

    /**
     * Registers a keyboard key mapping named {@code key.<modId>.<id>}.
     *
     * @param defaultKey key code from {@code InputConstants}, e.g. {@code InputConstants.KEY_B}
     * @throws IllegalStateException    if key mappings were already registered with the loader
     * @throws IllegalArgumentException if a key mapping with the same name is already registered
     */
    public KeyMapping register(String id, int defaultKey) {
        return register(new KeyMapping(name(id), defaultKey, category));
    }

    /**
     * Registers a keyboard key mapping named {@code key.<modId>.<id>} and runs {@code onPress} on the client thread
     * for every press, at the end of the client tick.
     *
     * @param defaultKey key code from {@code InputConstants}, e.g. {@code InputConstants.KEY_B}
     * @throws IllegalStateException    if key mappings were already registered with the loader
     * @throws IllegalArgumentException if a key mapping with the same name is already registered
     * @see #register(KeyMapping, Runnable)
     */
    public KeyMapping register(String id, int defaultKey, Runnable onPress) {
        return register(new KeyMapping(name(id), defaultKey, category), onPress);
    }

    /**
     * Registers a key mapping created by the caller, e.g. a {@code ToggleKeyMapping} or one bound to a mouse button.
     * Use {@link #getCategory()} to put it into the category of this registry.
     *
     * @throws IllegalStateException    if key mappings were already registered with the loader
     * @throws IllegalArgumentException if a key mapping with the same name is already registered
     */
    public <K extends KeyMapping> K register(K keyMapping) {
        synchronized (LOCK) {
            if (NAMES.contains(keyMapping.getName())) {
                throw new IllegalArgumentException("Key mapping '" + keyMapping.getName() + "' is already registered!");
            }
            HELPER.register(keyMapping);
            NAMES.add(keyMapping.getName());
        }
        entries.add(keyMapping);
        return keyMapping;
    }

    /**
     * Registers a key mapping created by the caller and runs {@code onPress} on the client thread for every press, at
     * the end of the client tick.
     * <p>
     * The presses are consumed for this, so {@link KeyMapping#consumeClick()} of this key mapping always returns
     * {@code false} elsewhere. {@link KeyMapping#isDown()} is not affected.
     *
     * @throws IllegalStateException    if key mappings were already registered with the loader
     * @throws IllegalArgumentException if a key mapping with the same name is already registered
     */
    public <K extends KeyMapping> K register(K keyMapping, Runnable onPress) {
        Objects.requireNonNull(onPress, "onPress");
        register(keyMapping);
        PRESS_HANDLERS.add(new PressHandler(keyMapping, onPress));
        return keyMapping;
    }

    private String name(String id) {
        return "key." + modId + "." + id;
    }

    /**
     * @return an unmodifiable view of all key mappings registered through this registry
     */
    public Collection<KeyMapping> getEntries() {
        return entriesView;
    }

    public KeyMapping.Category getCategory() {
        return category;
    }

    public String getModId() {
        return modId;
    }

    // Called by each loader at the end of every client tick
    static void onClientTick() {
        for (PressHandler handler : PRESS_HANDLERS) {
            while (handler.keyMapping().consumeClick()) {
                handler.onPress().run();
            }
        }
    }

    private record PressHandler(KeyMapping keyMapping, Runnable onPress) {}
}
