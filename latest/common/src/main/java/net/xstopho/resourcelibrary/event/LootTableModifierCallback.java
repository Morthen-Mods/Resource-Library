package net.xstopho.resourcelibrary.event;

import net.minecraft.world.item.ItemStack;

public interface LootTableModifierCallback {

    /**
     * Event gets called before the ItemStack is dropped by an Entity or added to the Chest.<br>
     * At this stage you can add additional DataComponents.
     */
    ResourceEvent<LootTableModifierCallback> MODIFY = new ResourceEvent<>(callbacks -> stack -> {
        for (LootTableModifierCallback callback : callbacks) {
            callback.modifyItemStack(stack);
        }
    });

    void modifyItemStack(ItemStack stack);
}