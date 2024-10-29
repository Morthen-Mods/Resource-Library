package net.xstopho.resourcelibrary.rendering;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.xstopho.resourcelibrary.LibConstants;
import net.xstopho.resourcelibrary.mixins.rendering.ItemRendererMixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemModelRenderer {

    private final String modId;

    public ItemModelRenderer(String modId) {
        this.modId = modId;
    }

    /**
     * Registers a custom model for an item held in hand.
     * <p>
     * The given texture/model must be saved in the following directories:
     * <ul>
     *     <li>Texture: assets/{mod_id}/textures/item/in_hand</li>
     *     <li>Model: assets/{mod_id}/models/item/in_hand</li>
     * </ul>
     * </p>
     *
     * @param item      The item for which the model should be registered.
     * @param modelName The name of the model to be registered. This name is used to create the path to the texture.
     *                  The path is created in the format `{mod_id}:in_hand/{modelName}`.
     */
    public void registerInHandModel(Item item, String modelName) {
        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(this.modId, "in_hand/" + modelName);
        registerModel(item, textureLocation, LibConstants.IN_HAND_MODELS);
    }

    /**
     * Registers a custom model for an item rendered inside the inventory or any other GUI.
     * <p>
     * The given texture/model must be saved in the following directories:
     * <ul>
     *     <li>Texture: assets/{mod_id}/textures/item/gui</li>
     *     <li>Model: assets/{mod_id}/models/item/gui</li>
     * </ul>
     * </p>
     *
     * @param item      The item for which the model should be registered.
     * @param modelName The name of the model to be registered. This name is used to create the path to the texture.
     *                  The path is created in the format `{mod_id}:gui/{modelName}`.
     */
    public void registerGuiModel(Item item, String modelName) {
        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(this.modId, "gui/" + modelName);
        registerModel(item, textureLocation, LibConstants.GUI_MODELS);
    }

    /**
     * Registers a custom model for an item that is on your head.
     * <p>
     * The given texture/model must be saved in the following directories:
     * <ul>
     *     <li>Texture: assets/{mod_id}/textures/item/head</li>
     *     <li>Model: assets/{mod_id}/models/item/head</li>
     * </ul>
     * </p>
     *
     * @param item      The item for which the model should be registered.
     * @param modelName The name of the model to be registered. This name is used to create the path to the texture.
     *                  The path is created in the format `{mod_id}:head/{modelName}`.
     */
    public void registerHeadModel(Item item, String modelName) {
        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(this.modId, "head/" + modelName);
        registerModel(item, textureLocation, LibConstants.HEAD_MODELS);
    }

    /**
     * Registers a custom model for an item that is placed in an Item Frame.
     * <p>
     * The given texture/model must be saved in the following directories:
     * <ul>
     *     <li>Texture: assets/{mod_id}/textures/item/fixed</li>
     *     <li>Model: assets/{mod_id}/models/item/fixed</li>
     * </ul>
     * </p>
     *
     * @param item      The item for which the model should be registered.
     * @param modelName The name of the model to be registered. This name is used to create the path to the texture.
     *                  The path is created in the format `{mod_id}:fixed/{modelName}`.
     */
    public void registerFixedModel(Item item, String modelName) {
        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(this.modId, "fixed/" + modelName);
        registerModel(item, textureLocation, LibConstants.FIXED_MODELS);
    }

    /**
     * Registers a custom model for an item that is on the ground.
     * <p>
     * The given texture/model must be saved in the following directories:
     * <ul>
     *     <li>Texture: assets/{mod_id}/textures/item/ground</li>
     *     <li>Model: assets/{mod_id}/models/item/ground</li>
     * </ul>
     * </p>
     *
     * @param item      The item for which the model should be registered.
     * @param modelName The name of the model to be registered. This name is used to create the path to the texture.
     *                  The path is created in the format `{mod_id}:ground/{modelName}`.
     */
    public void registerGroundModel(Item item, String modelName) {
        ResourceLocation textureLocation = ResourceLocation.fromNamespaceAndPath(this.modId, "ground/" + modelName);
        registerModel(item, textureLocation, LibConstants.GROUND_MODELS);
    }

    private void registerModel(Item item, ResourceLocation textureLocation, Map<Item, ResourceLocation> modelMap) {
        if (!modelMap.containsKey(item)) {
            modelMap.put(item, textureLocation);
        } else LibConstants.LOG.error("You already registered an custom Model for {} at location {}", item, textureLocation);
    }
}
