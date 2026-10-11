package net.morthen.resourcelibrary.modifier;

import net.morthen.resourcelibrary.LibConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Internal storage for {@link LootTableModifier.Modifier}s.
 * <p>
 * Since 26.3 loot tables are loaded in parallel, so the modifier list is read concurrently by many worker threads.
 * Registration is therefore closed ({@link #freeze()}) as soon as the first loot table is loaded; registering
 * afterwards would race with those readers and silently miss every table that was already loaded.
 */
final class LootTableModifierRegistry {
    private static final Object LOCK = new Object();

    private static final List<LootTableModifier.Modifier> MODIFIERS = new CopyOnWriteArrayList<>();
    private static final List<LootTableModifier.Modifier> MODIFIERS_VIEW = Collections.unmodifiableList(MODIFIERS);

    // guarded by LOCK
    private static final List<Consumer<LootTableModifier>> CALLBACKS = new ArrayList<>();
    // guarded by LOCK
    private static boolean callbacksRun = false;

    private static volatile boolean frozen = false;

    private LootTableModifierRegistry() {}

    static List<LootTableModifier.Modifier> modifiers() {
        return MODIFIERS_VIEW;
    }

    static void add(Collection<LootTableModifier.Modifier> modifiers) {
        synchronized (LOCK) {
            checkNotFrozen();
            MODIFIERS.addAll(modifiers);
        }
    }

    static void register(Consumer<LootTableModifier> callback) {
        Objects.requireNonNull(callback, "callback");
        synchronized (LOCK) {
            checkNotFrozen();
            if (callbacksRun) callback.accept(LootTableModifier.getInstance());
            else CALLBACKS.add(callback);
        }
    }

    /**
     * Runs all callbacks passed to {@link LootTableModifier#register(Consumer)} so far. Callbacks registered
     * afterwards run immediately. Called by each loader at its common setup; calling it again is a no-op.
     */
    static void runCallbacks() {
        synchronized (LOCK) {
            if (callbacksRun) return;
            callbacksRun = true;

            LootTableModifier instance = LootTableModifier.getInstance();
            for (Consumer<LootTableModifier> callback : CALLBACKS) {
                callback.accept(instance);
            }
            CALLBACKS.clear();
        }
    }

    /**
     * Closes registration. Must be called by each loader before it reads {@link #modifiers()} while loading loot
     * tables. Safe to call from many threads at once; all of them return only after pending callbacks have run.
     */
    static void freeze() {
        if (frozen) return;
        synchronized (LOCK) {
            if (frozen) return;
            runCallbacks();
            frozen = true;
            LibConstants.LOG.debug("Loot table modifier registration closed with {} modifiers", MODIFIERS.size());
        }
    }

    private static void checkNotFrozen() {
        if (frozen) {
            throw new IllegalStateException("Loot table modifiers must be registered during mod initialization (before loot tables are loaded). "
                    + "Use LootTableModifier.register(...) or call addItem(...) from your mod constructor / initializer.");
        }
    }
}
