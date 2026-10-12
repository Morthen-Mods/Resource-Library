package net.morthen.resourcelibrary.gametest.tests.objects;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.morthen.resourcelibrary.item.CraftingRemainderItem;

public class TestRecipeRemainderItem {

    public static class FixedDurability extends CraftingRemainderItem {

        public FixedDurability(ResourceKey<Item> key, int durability) {
            super(new Properties().durability(durability).setId(key));
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

    public static class DynamicDurability extends CraftingRemainderItem {
        private final int durability;

        public DynamicDurability(ResourceKey<Item> key, int durability) {
            super(new Properties().setId(key));
            this.durability = durability;
        }

        public ItemStack applyDurability() {
            ItemStack stack = new ItemStack(this);
            stack.set(DataComponents.MAX_DAMAGE, this.durability);
            stack.set(DataComponents.MAX_STACK_SIZE, 1);
            return stack;
        }

        @Override
        public ItemStackTemplate getRemainingItem(ItemStack stack) {
            ItemStack damaged = stack.copy();

            if (!damaged.has(DataComponents.MAX_DAMAGE)) {
                damaged = this.applyDurability();
            }

            if (damaged.getDamageValue() < damaged.getMaxDamage() -1) {
                damaged.setDamageValue(damaged.getDamageValue() + 1);
                return ItemStackTemplate.fromNonEmptyStack(damaged);
            }

            return super.getCraftingRemainder();
        }
    }
}
