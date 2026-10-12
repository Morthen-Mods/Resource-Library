package net.morthen.resourcelibrary.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.jetbrains.annotations.Nullable;

/**
 * Item whose crafting remainder depends on the used stack, e.g. a tool that loses durability on every craft.
 * <p>
 * Each loader asks for the remainder through its own hook, this class forwards all of them to
 * {@link #getRemainingItem(ItemStack)}. Items that need a different base class can't use this class; they have to
 * declare both bridge methods below themselves.
 */
public abstract class CraftingRemainderItem extends Item {

    public CraftingRemainderItem(Properties properties) {
        super(properties);
    }

    /**
     * @param stack the stack that was used in the recipe, must not be modified
     * @return the item that is left in the crafting grid, or {@code null} for none.
     *         {@link #getCraftingRemainder()} returns the remainder set through {@link Properties#craftRemainder}.
     */
    public abstract @Nullable ItemStackTemplate getRemainingItem(ItemStack stack);

    // Fabric (FabricItem) and Forge (IForgeItem) hook
    public final @Nullable ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        return getRemainingItem(stack);
    }

    // NeoForge (IItemExtension) hook
    public final @Nullable ItemStackTemplate getCraftingRemainder(ItemInstance instance) {
        if (instance instanceof ItemStack stack) return getRemainingItem(stack);
        if (instance instanceof ItemStackTemplate template) return getRemainingItem(template.create());
        return getCraftingRemainder();
    }
}
