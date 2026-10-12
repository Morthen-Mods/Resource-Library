package net.morthen.resourcelibrary.components;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Tooltip lines for items, the mod counterpart to vanilla's {@code minecraft:lore}, which is left to datapacks.
 * Use it through {@link LibDataComponents#TOOLTIP}:
 *
 * <pre>{@code
 * new Item.Properties().component(LibDataComponents.TOOLTIP, ItemTooltip.of(Component.translatable("tooltip.mymod.ruby")))
 * }</pre>
 *
 * The lines are shown right after the lore. Lines without an own color are shown in {@link #DEFAULT_STYLE gray}, and
 * like every component it can be hidden through {@code minecraft:tooltip_display}.
 */
@SuppressWarnings("unused")
public final class ItemTooltip implements TooltipProvider {
    public static final int MAX_LINES = 256;
    public static final Style DEFAULT_STYLE = Style.EMPTY.withColor(ChatFormatting.GRAY);
    public static final ItemTooltip EMPTY = new ItemTooltip(List.of());

    public static final Codec<ItemTooltip> CODEC = ComponentSerialization.CODEC.sizeLimitedListOf(MAX_LINES)
            .xmap(ItemTooltip::new, ItemTooltip::lines);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemTooltip> STREAM_CODEC = ComponentSerialization.STREAM_CODEC
            .apply(ByteBufCodecs.list(MAX_LINES))
            .map(ItemTooltip::new, ItemTooltip::lines);

    private final List<Component> lines;
    private final List<Component> styledLines;

    /**
     * @throws IllegalArgumentException if there are more than {@link #MAX_LINES} lines
     */
    public ItemTooltip(List<Component> lines) {
        if (lines.size() > MAX_LINES) {
            throw new IllegalArgumentException("Got " + lines.size() + " tooltip lines, but maximum is " + MAX_LINES);
        }
        this.lines = List.copyOf(lines);
        this.styledLines = this.lines.stream()
                .map(line -> ComponentUtils.mergeStyles(line, DEFAULT_STYLE))
                .toList();
    }

    public static ItemTooltip of(Component... lines) {
        return new ItemTooltip(List.of(lines));
    }

    /**
     * @return the lines as they were passed in, without {@link #DEFAULT_STYLE}
     */
    public List<Component> lines() {
        return lines;
    }

    public ItemTooltip withLineAdded(Component line) {
        List<Component> newLines = new ArrayList<>(lines);
        newLines.add(line);
        return new ItemTooltip(newLines);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        styledLines.forEach(consumer);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemTooltip other && lines.equals(other.lines);
    }

    @Override
    public int hashCode() {
        return lines.hashCode();
    }

    @Override
    public String toString() {
        return "ItemTooltip" + lines;
    }
}
