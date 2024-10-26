package net.xstopho.resourcelibrary.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class ResourceCraftingRemainder extends Item implements IResourceCraftingRemainder {

    public ResourceCraftingRemainder(Properties properties) {
        super(properties);
    }

    public abstract ItemStack getRemainingItem(ItemStack stack);

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return getRemainingItem(stack);
    }

    @Override
    public ItemStack getCraftingRemainder(ItemStack stack) {
        return getRemainingItem(stack);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return getRemainingItem(stack);
    }
}
