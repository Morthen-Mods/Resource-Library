package net.morthen.resourcelibrary;

import net.minecraft.core.component.DataComponents;
import net.morthen.resourcelibrary.components.LibDataComponents;
import net.morthen.resourcelibrary.entity.NeoForgeEntityDataHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.tooltip.TooltipAppender;
import net.neoforged.neoforge.event.RegisterTooltipAppendersEvent;

@Mod(LibConstants.MOD_ID)
public class ResourceLibrary {

    public ResourceLibrary(IEventBus eventBus) {
        LibDataComponents.init();
        eventBus.addListener(ResourceLibrary::registerTooltipAppenders);
        eventBus.addListener(NeoForgeEntityDataHelper::registerAttachmentTypes);
    }

    private static void registerTooltipAppenders(RegisterTooltipAppendersEvent event) {
        event.registerComponentAppenderAfter(LibDataComponents.TOOLTIP, DataComponents.LORE,
                TooltipAppender.createComponentAppender(LibDataComponents.TOOLTIP));
    }
}
