package net.xstopho.resourcelibrary.event;

public abstract class ResourceEvent<T> {
    protected volatile T invoker;

    public final T invoker() {
        return invoker;
    }

    public abstract void register(T listener);
}
