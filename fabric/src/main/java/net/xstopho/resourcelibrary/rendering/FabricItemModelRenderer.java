package net.xstopho.resourcelibrary.rendering;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.xstopho.resourcelibrary.ResourceLibrary;

import java.util.Map;

public class FabricItemModelRenderer implements ItemModelRenderer {

    @Override
    public void registerItemModel(Item item, ResourceLocation textureLocation) {
        Map<Item, ResourceLocation> map = ResourceLibrary.ITEM_MODEL_RENDERER_ENTRIES;

        if (!map.containsKey(item)) {
            map.put(item, textureLocation);
        } else throw new IllegalArgumentException("You already registered an custom Item Model for: \n" + item);
    }
}
