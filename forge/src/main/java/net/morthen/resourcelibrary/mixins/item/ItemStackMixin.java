package net.morthen.resourcelibrary.mixins.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.morthen.resourcelibrary.components.LibDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.function.Consumer;

// Forge has no way to register tooltips of custom data components, vanilla only shows its own ones
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    // Directly after the lore, like on Fabric and NeoForge
    @Inject(method = "addDetailsToTooltip",
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/core/component/DataComponents;LORE:Lnet/minecraft/core/component/DataComponentType;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
                    ordinal = 0, shift = At.Shift.AFTER))
    private void resourcelibrary$addItemTooltip(Item.TooltipContext context, TooltipDisplay display, @Nullable Player player,
                                                TooltipFlag flag, Consumer<Component> consumer, CallbackInfo ci) {
        ((ItemStack) (Object) this).addToTooltip(LibDataComponents.TOOLTIP, context, display, consumer, flag);
    }
}
