package net.xstopho.resourcelibrary.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Optional;

public abstract class ResourceModelProvider extends FabricModelProvider {

    public ResourceModelProvider(FabricDataOutput output) {
        super(output);
    }

    /**
     * Helper method that fills the gap for furnace like blocks.
     * @param generator {@link BlockModelGenerators} passed down inside the Model Provider
     * @param block Your furnace like Block
     */
    public void createHorizontalBlock(BlockModelGenerators generator, Block block) {
        TextureMapping map = new TextureMapping();

        map.put(TextureSlot.FRONT, modifyBlockKey(block, ""));
        map.put(TextureSlot.SIDE, modifyBlockKey(block, "_side"));
        map.put(TextureSlot.TOP, modifyBlockKey(block, "_top"));

        ResourceLocation model = ModelTemplates.CUBE_ORIENTABLE.create(block, map, generator.modelOutput);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(createPropertyDispatch(model)));
    }

    /**
     * Helper method to generate alternate Item Models when hold in Main/Off-hand. <br>
     * Generated json Files get saved in assets/mod_id/models/item/in_hand
     * @param generator {@link ItemModelGenerators} passed down inside Model Provider
     * @param item base item that gets alternative models
     * @param parent Your custom Model Template
     */
    public void createInHandItemModel(ItemModelGenerators generator, Item item, ResourceLocation parent) {
        new ModelTemplate(Optional.of(parent), Optional.empty(), TextureSlot.LAYER0)
                .create(modifyItemKey(item), TextureMapping.layer0(modifyItemKey(item)), generator.output);
    }

    private PropertyDispatch createPropertyDispatch(ResourceLocation model) {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.NORTH, Variant.variant()
                        .with(VariantProperties.MODEL, model))
                .select(Direction.EAST, Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270));
    }

    private ResourceLocation modifyBlockKey(Block block, String texturePosition) {
        String[] parts = getBlockKey(block).toString().split(":");
        return ResourceLocation.fromNamespaceAndPath(parts[0], "block/" + parts[1] + texturePosition);
    }

    private ResourceLocation modifyItemKey(Item item) {
        String[] parts = getItemKey(item).toString().split(":");
        return ResourceLocation.fromNamespaceAndPath(parts[0], "item/in_hand/" + parts[1]);
    }

    private ResourceLocation getBlockKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private ResourceLocation getItemKey(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
