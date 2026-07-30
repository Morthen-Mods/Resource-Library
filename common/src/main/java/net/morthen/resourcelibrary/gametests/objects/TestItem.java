package net.morthen.resourcelibrary.gametests.objects;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.morthen.resourcelibrary.LibConstants;

public class TestItem extends Item {
    public TestItem(String id) {
        super(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, id))));
    }
}
