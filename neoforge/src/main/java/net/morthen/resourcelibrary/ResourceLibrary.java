package net.morthen.resourcelibrary;

import net.morthen.resourcelibrary.components.LibDataComponents;
import net.morthen.resourcelibrary.entity.NeoForgeEntityDataHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {

    public ResourceLibrary(IEventBus eventBus) {
        LibDataComponents.init();
        eventBus.addListener(NeoForgeEntityDataHelper::registerAttachmentTypes);
        NeoForge.EVENT_BUS.addListener(NeoForgeEntityDataHelper::copyOnDeathWithCondition);
    }
}
