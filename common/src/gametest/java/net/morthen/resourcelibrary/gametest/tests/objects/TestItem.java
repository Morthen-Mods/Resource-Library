package net.morthen.resourcelibrary.gametest.tests.objects;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class TestItem extends Item {
    public TestItem(ResourceKey<Item> key) {
        super(new Properties().setId(key));
    }
}
