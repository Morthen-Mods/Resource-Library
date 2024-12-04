package net.xstopho.resourcelibrary.mixins.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.xstopho.resourcelibrary.LibConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "render", at = @At("HEAD"), argsOnly = true)
    public BakedModel useInHandModel(BakedModel model, ItemStack stack, ItemDisplayContext displayContext, boolean leftHanded, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {

        switch(displayContext) {
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                if (LibConstants.IN_HAND_MODELS.containsKey(stack.getItem())) {
                    return getModel(LibConstants.IN_HAND_MODELS.get(stack.getItem()));
                }
            }
            case GUI -> {
                if (LibConstants.GUI_MODELS.containsKey(stack.getItem())) {
                    return getModel(LibConstants.GUI_MODELS.get(stack.getItem()));
                }
            }
            case HEAD -> {
                if (LibConstants.HEAD_MODELS.containsKey(stack.getItem())) {
                    return getModel(LibConstants.HEAD_MODELS.get(stack.getItem()));
                }
            }
            case FIXED -> {
                if (LibConstants.FIXED_MODELS.containsKey(stack.getItem())) {
                    return getModel(LibConstants.FIXED_MODELS.get(stack.getItem()));
                }
            }
            case GROUND -> {
                if (LibConstants.GROUND_MODELS.containsKey(stack.getItem())) {
                    return getModel(LibConstants.GROUND_MODELS.get(stack.getItem()));
                }
            }
        }

        return model;
    }

    private BakedModel getModel(ResourceLocation location) {
        return ((ItemRendererAccessor) this).rLib_getItemModelShaper().getModelManager().getModel(ModelResourceLocation.inventory(location));
    }
}
