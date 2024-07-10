package net.xstopho.resourcelibrary_test;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.xstopho.resourcelibrary_test.datagen.ModelProv;
import net.xstopho.resourcelibrary_test.datagen.TagProv;

public class ResourceLibraryTestDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(TagProv.ItemTags::new);
        pack.addProvider(TagProv.BlockTags::new);
        pack.addProvider(TagProv.FluidTags::new);
        pack.addProvider(TagProv.EnchantmentTags::new);
        pack.addProvider(TagProv.BlockEntityTypeTags::new);
        pack.addProvider(TagProv.EntityTypeTags::new);

        pack.addProvider(ModelProv::new);
    }
}
