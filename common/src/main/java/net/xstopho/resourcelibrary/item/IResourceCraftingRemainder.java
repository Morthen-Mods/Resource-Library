package net.xstopho.resourcelibrary.item;

import net.minecraft.world.item.ItemStack;

public interface IResourceCraftingRemainder {

    ItemStack getRemainingItem(ItemStack stack);

    // Fabric implementation
    ItemStack getRecipeRemainder(ItemStack stack);

    // Neo-/Forge implementation
    ItemStack getCraftingRemainder(ItemStack stack);
}
