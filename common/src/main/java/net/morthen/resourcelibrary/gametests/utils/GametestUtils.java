package net.morthen.resourcelibrary.gametests.utils;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class GametestUtils {
    public static Holder<Enchantment> getEnchantment(GameTestHelper helper, ResourceKey<Enchantment> enchantment) {
        return helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantment);
    }

    public static ItemStack findItem(Container container, Item item) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() == item) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
