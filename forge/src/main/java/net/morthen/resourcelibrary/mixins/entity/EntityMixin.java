package net.morthen.resourcelibrary.mixins.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.morthen.resourcelibrary.entity.EntityDataType;
import net.morthen.resourcelibrary.entity.ForgeEntityDataHelper;
import net.morthen.resourcelibrary.entity.ForgeEntityDataHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

// Forge has no data attachments like Fabric and NeoForge, so the entity data is stored directly in the entity
@Mixin(Entity.class)
public abstract class EntityMixin implements ForgeEntityDataHolder {
    // Created on the first set, most entities never get any data
    @Unique
    private @Nullable Map<EntityDataType<?>, Object> resourcelibrary$entityData;

    @Override
    public @Nullable Map<EntityDataType<?>, Object> resourcelibrary$getEntityData() {
        return resourcelibrary$entityData;
    }

    @Override
    public Map<EntityDataType<?>, Object> resourcelibrary$getOrCreateEntityData() {
        if (resourcelibrary$entityData == null) resourcelibrary$entityData = new HashMap<>();
        return resourcelibrary$entityData;
    }

    @Inject(method = "saveWithoutId(Lnet/minecraft/world/level/storage/ValueOutput;)V", at = @At("RETURN"))
    private void resourcelibrary$saveEntityData(ValueOutput output, CallbackInfo ci) {
        ForgeEntityDataHelper.save(this, output);
    }

    @Inject(method = "load(Lnet/minecraft/world/level/storage/ValueInput;)V", at = @At("RETURN"))
    private void resourcelibrary$loadEntityData(ValueInput input, CallbackInfo ci) {
        ForgeEntityDataHelper.load(this, input);
    }
}
