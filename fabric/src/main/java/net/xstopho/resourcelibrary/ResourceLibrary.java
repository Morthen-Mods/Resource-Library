package net.xstopho.resourcelibrary;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ResourceLibrary implements ModInitializer {

    public static final Map<Item, ResourceLocation> ITEM_MODEL_RENDERER_ENTRIES = new HashMap<>();

    @Override
    public void onInitialize() {

    }
}
