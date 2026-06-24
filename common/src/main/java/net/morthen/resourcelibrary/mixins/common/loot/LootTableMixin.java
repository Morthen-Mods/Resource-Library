package net.morthen.resourcelibrary.mixins.common.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.event.LootTableModifierCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LootTable.class)
public abstract class LootTableMixin {

    @ModifyVariable(at = @At(value = "RETURN"),
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", name = "result", remap = false)
    private ObjectArrayList<ItemStack> resource_library$getRandomItems(ObjectArrayList<ItemStack> result) {
        result.forEach(stack -> LootTableModifierCallback.MODIFY.invoker().modifyItemStack(stack));
        return result;
    }
}
