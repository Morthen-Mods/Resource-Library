package net.xstopho.resourcelibrary.events;

import java.util.List;
import java.util.function.Function;

public final class ResourceEventFactory {
    public static <T> ResourceEvent<T> createListBackedEvent(Function<List<T>, T> invokerFactory) {
        return new ResourceListBackedEvent<>(invokerFactory);
    }
}
