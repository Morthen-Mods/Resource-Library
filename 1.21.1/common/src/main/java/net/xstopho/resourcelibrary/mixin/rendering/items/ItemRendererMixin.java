package net.xstopho.resourcelibrary.mixin.rendering.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.xstopho.resourcelibrary.rendering.item.ItemModelRenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    public BakedModel useInHandModel(BakedModel model, ItemStack stack, ItemDisplayContext displayContext, boolean leftHanded, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        HashMap<Item, ModelResourceLocation> map = ItemModelRenderHelper.getItemModels();

        if (isInHand(displayContext) && map.containsKey(stack.getItem())) return getModel(map.get(stack.getItem()));

        return model;
    }

    private boolean isInHand(ItemDisplayContext context) {
        return context.firstPerson() || context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || context == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    private BakedModel getModel(ModelResourceLocation location) {
        return ((ItemRendererAccessor) this).lib_getItemModelShaper().getModelManager().getModel(location);
    }
}
