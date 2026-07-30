package net.morthen.resourcelibrary.modifier;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Optional;

public class FabricLootDropModifier implements LootDropModifier {

    @Override
    public void modifyItemDrop(ResourceKey<LootTable> lootTable, Modifier modifier) {
        LootTableEvents.MODIFY_DROPS.register((holder, _, drops) -> {
            Optional<ResourceKey<LootTable>> optional = holder.unwrapKey();
            optional.ifPresent(lootKey -> {
                if (lootTable.equals(lootKey)) {
                    drops.forEach(modifier::modify);
                }
            });
        });
    }
}
