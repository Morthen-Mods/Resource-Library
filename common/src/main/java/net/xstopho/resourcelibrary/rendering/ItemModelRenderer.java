package net.xstopho.resourcelibrary.rendering;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.xstopho.resourcelibrary.service.CoreServices;

public interface ItemModelRenderer {

    static ItemModelRenderer getInstance() {
        return CoreServices.load(ItemModelRenderer.class);
    }

    void registerItemModel(Item item, ResourceLocation textureLocation);
}
