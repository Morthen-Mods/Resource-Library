package net.morthen.resourcelibrary.mixins.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.morthen.resourcelibrary.components.LibDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

// NeoForge can only register tooltips of custom data components since a later 26.1.2 build. The lore is added through
// addToTooltip on every NeoForge version, while the method that calls it changed, so the tooltip is added from there.
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    // Directly after the lore, like on Fabric and Forge
    @Inject(method = "addToTooltip", at = @At("RETURN"))
    private <T extends TooltipProvider> void resourcelibrary$addItemTooltip(DataComponentType<T> type, Item.TooltipContext context,
                                                                           TooltipDisplay display, Consumer<Component> consumer,
                                                                           TooltipFlag flag, CallbackInfo ci) {
        if (type == DataComponents.LORE) {
            ((ItemStack) (Object) this).addToTooltip(LibDataComponents.TOOLTIP, context, display, consumer, flag);
        }
    }
}
