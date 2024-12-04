package net.xstopho.resourcelibrary.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public abstract class ResourceTagProvider<T> extends TagsProvider<T> {

    public ResourceTagProvider(PackOutput output, ResourceKey<? extends Registry<T>> registryKey, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, registryKey, lookupProvider);
    }

//    public final ResourceTagBuilder tagBuilder(TagKey<T> tagKey) {
//        return new ResourceTagBuilder(super.tag(tagKey));
//    }
//
//    public ResourceKey<T> getKey(T element) {
//        throw new IllegalArgumentException("Raw usage of ResourceTagsProvider isn't possible! Use pre defined provider or create your own by extending ResourceTagsProvider.");
//    }
//
//    public abstract static class BlockTagProvider extends ResourceTagProvider<Block> {
//
//        public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
//            super(output, Registries.BLOCK, lookupProvider);
//        }
//
//        @Override
//        public ResourceKey<Block> getKey(Block element) {
//            Optional<ResourceKey<Block>> optional = BuiltInRegistries.BLOCK.getResourceKey(element);
//
//            if (optional.isPresent()) {
//                return optional.get();
//            }
//
//            LibConstants.LOG.error("Resource Key for Block: {} isn't present!", element.getName().getString());
//            return BuiltInRegistries.BLOCK.getResourceKey(Blocks.AIR).get();
//        }
//    }
//
//    public abstract static class ItemTagProvider extends ResourceTagProvider<Item> {
//
//        public ItemTagProvider(PackOutput output,  CompletableFuture<HolderLookup.Provider> lookupProvider) {
//            super(output, Registries.ITEM, lookupProvider);
//        }
//
//        @Override
//        public ResourceKey<Item> getKey(Item element) {
//            Optional<ResourceKey<Item>> optional = BuiltInRegistries.ITEM.getResourceKey(element);
//
//            if (optional.isPresent()) {
//                return optional.get();
//            }
//
//            LibConstants.LOG.error("Resource Key for Item: {} isn't present!", element.getName().getString());
//            return BuiltInRegistries.ITEM.getResourceKey(Items.AIR).get();
//        }
//    }
//
//    public final class ResourceTagBuilder extends TagAppender<T> {
//
//        public ResourceTagBuilder(TagAppender<T> tagAppender) {
//            super(tagAppender.bui);
//        }
//
//        @SafeVarargs
//        public final ResourceTagBuilder add(T... elements) {
//            Stream.of(elements).map(ResourceTagProvider.this::getKey).forEach(this::add);
//            return this;
//        }
//    }
}
