package net.morthen.resourcelibrary.gametest.tests;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TooltipDisplay;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.components.ItemTooltip;
import net.morthen.resourcelibrary.components.LibDataComponents;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.registration.RegistryProvider;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemTooltipTests {
    private static final RegistryProvider<Item> ITEMS = RegistryProvider.create(LibConstants.MOD_ID, Registries.ITEM);

    private static final String PLAIN_LINE = "generated_test_tooltip_plain";
    private static final String COLORED_LINE = "generated_test_tooltip_colored";
    private static final String LORE_LINE = "generated_test_tooltip_lore";

    private static RegistryObject<Item, Item> tooltipItem;

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("item_tooltip_component_is_registered", ItemTooltipTests::itemTooltipComponentIsRegistered);
        consumer.accept("item_tooltip_is_shown", ItemTooltipTests::itemTooltipIsShown);
        consumer.accept("item_tooltip_default_style_keeps_own_color", ItemTooltipTests::itemTooltipDefaultStyleKeepsOwnColor);
        consumer.accept("item_tooltip_is_shown_after_lore", ItemTooltipTests::itemTooltipIsShownAfterLore);
        consumer.accept("item_tooltip_hidden_by_tooltip_display", ItemTooltipTests::itemTooltipHiddenByTooltipDisplay);
        consumer.accept("item_tooltip_codec_round_trip", ItemTooltipTests::itemTooltipCodecRoundTrip);
    }

    public static void setupTooltip() {
        tooltipItem = ITEMS.register("tooltip_item", key -> new Item(new Item.Properties().setId(key)
                .component(LibDataComponents.TOOLTIP, ItemTooltip.of(
                        Component.literal(PLAIN_LINE),
                        Component.literal(COLORED_LINE).withStyle(ChatFormatting.RED)))));
    }

    private static List<Component> tooltipLines(GameTestHelper helper, ItemStack stack) {
        return stack.getTooltipLines(Item.TooltipContext.of(helper.getLevel()), null, TooltipFlag.NORMAL);
    }

    private static int indexOf(List<Component> lines, String text) {
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getString().equals(text)) return i;
        }
        return -1;
    }

    public static void itemTooltipComponentIsRegistered(GameTestHelper helper) {
        helper.assertValueEqual(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(LibDataComponents.TOOLTIP),
                Identifier.fromNamespaceAndPath(LibConstants.MOD_ID, "tooltip"), "id of LibDataComponents.TOOLTIP");
        helper.succeed();
    }

    public static void itemTooltipIsShown(GameTestHelper helper) {
        List<Component> lines = tooltipLines(helper, new ItemStack(tooltipItem.get()));

        helper.assertTrue(indexOf(lines, PLAIN_LINE) >= 0 && indexOf(lines, COLORED_LINE) >= 0,
                "ItemTooltip lines are missing in the tooltip: " + lines);
        helper.assertTrue(indexOf(lines, PLAIN_LINE) < indexOf(lines, COLORED_LINE), "ItemTooltip lines are out of order: " + lines);
        helper.succeed();
    }

    public static void itemTooltipDefaultStyleKeepsOwnColor(GameTestHelper helper) {
        List<Component> lines = tooltipLines(helper, new ItemStack(tooltipItem.get()));

        Component plain = lines.get(indexOf(lines, PLAIN_LINE));
        Component colored = lines.get(indexOf(lines, COLORED_LINE));
        helper.assertValueEqual(plain.getStyle().getColor(), TextColor.fromLegacyFormat(ChatFormatting.GRAY), "color of a line without own color");
        helper.assertValueEqual(colored.getStyle().getColor(), TextColor.fromLegacyFormat(ChatFormatting.RED), "color of a line with own color");
        helper.succeed();
    }

    public static void itemTooltipIsShownAfterLore(GameTestHelper helper) {
        ItemStack stack = new ItemStack(tooltipItem.get());
        stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal(LORE_LINE))));
        List<Component> lines = tooltipLines(helper, stack);

        int lore = indexOf(lines, LORE_LINE);
        helper.assertTrue(lore >= 0 && lore + 1 == indexOf(lines, PLAIN_LINE), "ItemTooltip is not shown directly after the lore: " + lines);
        helper.succeed();
    }

    public static void itemTooltipHiddenByTooltipDisplay(GameTestHelper helper) {
        ItemStack stack = new ItemStack(tooltipItem.get());
        stack.set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(LibDataComponents.TOOLTIP, true));
        List<Component> lines = tooltipLines(helper, stack);

        helper.assertTrue(indexOf(lines, PLAIN_LINE) < 0, "ItemTooltip is shown although it is hidden through tooltip_display: " + lines);
        helper.succeed();
    }

    public static void itemTooltipCodecRoundTrip(GameTestHelper helper) {
        ItemTooltip tooltip = ItemTooltip.of(Component.literal(PLAIN_LINE), Component.translatable("item.minecraft.diamond").withStyle(ChatFormatting.AQUA));
        DynamicOps<JsonElement> ops = helper.getLevel().registryAccess().createSerializationContext(JsonOps.INSTANCE);

        JsonElement json = ItemTooltip.CODEC.encodeStart(ops, tooltip).getOrThrow();
        ItemTooltip decoded = ItemTooltip.CODEC.parse(ops, json).getOrThrow();

        helper.assertValueEqual(decoded, tooltip, "ItemTooltip after encoding and decoding");
        helper.succeed();
    }
}
