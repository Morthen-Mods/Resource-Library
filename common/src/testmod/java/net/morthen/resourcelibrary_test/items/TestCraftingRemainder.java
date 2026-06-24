package net.morthen.resourcelibrary_test.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.morthen.resourcelibrary.item.ResourceCraftingRemainder;

public class TestCraftingRemainder extends  ResourceCraftingRemainder {

    public TestCraftingRemainder(Properties properties, int durability) {
        super(properties.durability(durability));
    }

    @Override
    public ItemStackTemplate getRemainingItem(ItemStack stack) {
        if (stack.getDamageValue() < stack.getMaxDamage() -1) {
            ItemStack damaged = stack.copy();
            damaged.setDamageValue(stack.getDamageValue() + 1);
            return ItemStackTemplate.fromNonEmptyStack(damaged);
        }

        return super.getCraftingRemainder();
    }
}
