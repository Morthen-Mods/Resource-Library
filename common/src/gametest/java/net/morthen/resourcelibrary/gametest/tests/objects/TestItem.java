package net.morthen.resourcelibrary.gametest.tests.objects;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.morthen.resourcelibrary.LibConstants;

public class TestItem extends Item {
    public TestItem(String id) {
        super(new Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, id))));
    }
}
