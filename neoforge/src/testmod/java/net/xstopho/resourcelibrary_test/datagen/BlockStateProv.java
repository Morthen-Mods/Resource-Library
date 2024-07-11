package net.xstopho.resourcelibrary_test.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.xstopho.resourcelibrary.datagen.ResourceBlockStateProvider;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;

public class BlockStateProv extends ResourceBlockStateProvider {

    public BlockStateProv(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ResourceLibraryTest.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}