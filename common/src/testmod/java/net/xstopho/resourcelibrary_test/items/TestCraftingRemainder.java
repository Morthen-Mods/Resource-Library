package net.xstopho.resourcelibrary_test.items;

import net.minecraft.world.item.ItemStack;
import net.xstopho.resourcelibrary.item.ResourceCraftingRemainder;

public class TestCraftingRemainder extends  ResourceCraftingRemainder {

    public TestCraftingRemainder(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getRemainingItem(ItemStack stack) {
        if (stack.getDamageValue() < stack.getMaxDamage() -1) {
            ItemStack damaged = stack.copy();
            damaged.setDamageValue(stack.getDamageValue() + 1);
            return damaged;
        }

        return ItemStack.EMPTY;
    }
}
