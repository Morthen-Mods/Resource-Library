package net.xstopho.resourcelibrary.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class RecipeRemainder extends Item implements IRecipeRemainder {

    public RecipeRemainder(Properties properties) {
        super(properties);
    }

    public abstract ItemStack getRemainingItem(ItemStack itemStack);

    @Override
    public final boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public final ItemStack getRecipeRemainder(ItemStack itemStack) {
        return getRemainingItem(itemStack);
    }

    @Override
    public final ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return getRemainingItem(itemStack);
    }
}
