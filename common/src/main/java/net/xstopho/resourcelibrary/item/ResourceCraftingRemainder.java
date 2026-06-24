package net.xstopho.resourcelibrary.item;

import net.minecraft.world.item.*;

public abstract class ResourceCraftingRemainder extends Item implements IResourceCraftingRemainder {

    public ResourceCraftingRemainder(Properties properties) {
        super(properties);
    }

    public abstract ItemStackTemplate getRemainingItem(ItemStack stack);

    @Override
    public ItemStackTemplate getRecipeRemainder(ItemStack stack) {
        return getRemainingItem(stack);
    }

    @Override
    public ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
        if (instance instanceof ItemStack stack) {
            return getRemainingItem(stack);
        }
        return super.getCraftingRemainder();
    }
}
