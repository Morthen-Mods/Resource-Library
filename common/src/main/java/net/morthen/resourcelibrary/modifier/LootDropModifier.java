package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.service.CoreServices;

public interface LootDropModifier {
    static LootDropModifier getInstance() { return CoreServices.load(LootDropModifier.class); }

    interface Modifier {
        void modify(ItemStack stack);
    }

    void modifyItemDrop(ResourceKey<LootTable> lootTable, Modifier modifier);
}
