package net.xstopho.resourcelibrary.event;

import net.xstopho.resourcelibrary.LibConstants;

import java.util.Objects;

public class ResourceSimpleEvent<T> extends ResourceEvent<T> {
    @Override
    public final void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");
        if (this.invoker != null) {
            LibConstants.LOG.error("You registered a second invoker for an Simple Event, Use ResourceEventFactory.createListBackedEvent() to register multiple invoker for the same Event!");
        }
        this.invoker = listener;
    }
}
