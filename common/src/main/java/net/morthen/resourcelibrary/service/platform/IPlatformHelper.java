package net.morthen.resourcelibrary.service.platform;

import java.nio.file.Path;

public interface IPlatformHelper {

    Path getConfigDir();

    default Platforms getPlatform() {
        return Platforms.NO_LOADER;
    }

    boolean isModLoaded(String modId);

    enum Platforms {
        FABRIC, FORGE, NEOFORGE, NO_LOADER
    }
}
