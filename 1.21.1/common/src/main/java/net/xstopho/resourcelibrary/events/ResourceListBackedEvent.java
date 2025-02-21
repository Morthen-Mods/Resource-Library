package net.xstopho.resourcelibrary.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

class ResourceListBackedEvent<T> extends ResourceEvent<T> {
    private final List<T> handlers = new ArrayList<>();
    private final Function<List<T>, T> factory;

    public ResourceListBackedEvent(Function<List<T>, T> factory) {
        this.factory = factory;
        update();
    }

    void update() {
        this.invoker = factory.apply(handlers);
    }

    @Override
    public void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");
        this.handlers.addLast(listener);
    }
}
