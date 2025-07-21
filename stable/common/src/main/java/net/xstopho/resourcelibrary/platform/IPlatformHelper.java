package net.xstopho.resourcelibrary.platform;

import java.nio.file.Path;

public interface IPlatformHelper {

    Path getConfigDir();

    default Platforms getPlatform() {
        return Platforms.NO_LOADER;
    }

    boolean isModLoaded(String modId);

    enum Platforms {
        FORGE, FABRIC, NEOFORGE, NO_LOADER
    }
}
