package net.xstopho.resourcelibrary_test.handler;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.xstopho.resourcelibrary_test.ResourceLibraryTest;
import net.xstopho.resourcelibrary_test.datagen.BlockStateProv;
import net.xstopho.resourcelibrary_test.datagen.ItemModelProv;
import net.xstopho.resourcelibrary_test.datagen.TagProv;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ResourceLibraryTest.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModHandler {
    @SubscribeEvent
    public static void data(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new BlockStateProv(output, fileHelper));
        generator.addProvider(event.includeServer(), new ItemModelProv(output, fileHelper));

        TagProv.BlockTags blockTags = generator.addProvider(event.includeServer(), new TagProv.BlockTags(output, provider, fileHelper));
        generator.addProvider(event.includeServer(), new TagProv.ItemTags(output, provider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new TagProv.EnchantmentTags(output, provider));
        generator.addProvider(event.includeServer(), new TagProv.FluidTags(output, provider, fileHelper));
    }
}
