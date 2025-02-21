package net.xstopho.resourcelibrary.event;

import java.util.List;
import java.util.function.Function;

public class ResourceEventFactory {
    public static <T> ResourceEvent<T> createListBackedEvent(Function<List<T>, T> invokerFactory) {
        return new ResourceListBackedEvent<>(invokerFactory);
    }
}
