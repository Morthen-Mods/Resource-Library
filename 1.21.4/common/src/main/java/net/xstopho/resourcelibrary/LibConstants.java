package net.xstopho.resourcelibrary;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LibConstants {
    public static final String MOD_ID = "resourcelibrary";
    public static final String MOD_NAME = "Resource Library";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // ItemModelRenderer Maps
    public static final Map<Item, ResourceLocation> IN_HAND_MODELS = new HashMap<>();
    public static final Map<Item, ResourceLocation> GUI_MODELS = new HashMap<>();
    public static final Map<Item, ResourceLocation> HEAD_MODELS = new HashMap<>();
    public static final Map<Item, ResourceLocation> FIXED_MODELS = new HashMap<>();
    public static final Map<Item, ResourceLocation> GROUND_MODELS = new HashMap<>();
}
