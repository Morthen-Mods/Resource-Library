package net.xstopho.resourcelibrary_test.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.xstopho.resourcelibrary.registration.RegistryObject;
import net.xstopho.resourcelibrary.registration.RegistryProvider;
import net.xstopho.resourcelibrary_test.LibraryTestConstants;

public class CreativeTabRegistry {
    private static final RegistryProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistryProvider.get(BuiltInRegistries.CREATIVE_MODE_TAB, LibraryTestConstants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> RESOURCE_LIBRARY_TEST = CREATIVE_MODE_TABS.register("item_group", () -> CreativeModeTab.builder(null, -1)
            .title(Component.translatable("resourcelibrary_test.item_group")).icon(() -> new ItemStack(ItemRegistry.TEST_RECIPE_REMAINDER.get()))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ItemRegistry.TEST_IN_HAND_ITEM.get());
                output.accept(ItemRegistry.TEST_RECIPE_REMAINDER.get());
            }).build());

    public static void init() {}
}
