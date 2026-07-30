package net.morthen.resourcelibrary.modifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

public class NeoforgeLootDropModifier implements LootDropModifier {
    public record DropModifier(ResourceKey<LootTable> lootTable, Modifier modifier) {}
    public static final List<DropModifier> modifiers = new ArrayList<>();

    @Override
    public void modifyItemDrop(ResourceKey<LootTable> lootTable, Modifier modifier) {
        modifiers.add(new DropModifier(lootTable, modifier));
    }
}
