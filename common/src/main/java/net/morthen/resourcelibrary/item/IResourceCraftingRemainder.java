package net.morthen.resourcelibrary.item;

import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public interface IResourceCraftingRemainder {

    ItemStackTemplate getRemainingItem(ItemStack stack);

    // Fabric implementation
    ItemStackTemplate getRecipeRemainder(ItemStack stack);

    // Neoforge implementation
    ItemStackTemplate getCraftingRemainder(ItemInstance instance);
}
