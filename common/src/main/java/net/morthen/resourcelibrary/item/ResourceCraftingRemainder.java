package net.morthen.resourcelibrary.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

public abstract class ResourceCraftingRemainder extends Item implements IResourceCraftingRemainder {

    public ResourceCraftingRemainder(Properties properties) {
        super(properties);
    }

    public abstract ItemStackTemplate getRemainingItem(ItemStack stack);

    @Override
    public ItemStackTemplate getCraftingRemainder(ItemStack stack) {
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
