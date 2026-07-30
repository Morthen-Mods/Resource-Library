package net.morthen.resourcelibrary.event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("all")
public final class ResourceEvent<T> {
    private final List<T> handler = new ArrayList<>();
    private final T invoker;

    public ResourceEvent(@NotNull Function<List<T>, T> factory) {
        this.invoker = factory.apply(handler);
    }

    public T invoker() {
        return this.invoker;
    }

    public void register(T listener) {
        Objects.requireNonNull(listener, "Tries to register null Listener!");
        this.handler.addLast(listener);
    }

    public void clearEvent() {
        this.handler.clear();
    }
}
