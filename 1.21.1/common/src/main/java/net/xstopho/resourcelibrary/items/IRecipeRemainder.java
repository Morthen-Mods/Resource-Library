package net.xstopho.resourcelibrary.items;

import net.minecraft.world.item.ItemStack;

public interface IRecipeRemainder {

    // Fabric implementation
    ItemStack getRecipeRemainder(ItemStack itemStack);

    // Neo-/Forge implementation
    ItemStack getCraftingRemainingItem(ItemStack itemStack);
}
