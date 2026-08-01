package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.service.LibServices;

public interface LootDropModifier {
    LootDropModifier INSTANCE = LibServices.load(LootDropModifier.class);

    static LootDropModifier getInstance() { return INSTANCE; }

    interface Modifier {
        void modify(ItemStack stack);
    }

    void modifyItemDrop(ResourceKey<LootTable> lootTable, Modifier modifier);
}
