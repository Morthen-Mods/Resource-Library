package net.xstopho.resourcelibrary.mixins.common.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.event.LootTableModifierCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixin {

    @ModifyVariable(at = @At(value = "RETURN"),
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;")
    private ObjectArrayList<ItemStack> resource_library$getRandomItems(ObjectArrayList<ItemStack> stacks) {
        stacks.forEach(stack -> LootTableModifierCallback.MODIFY.invoker().modifyItemStack(stack));
        return stacks;
    }
}
