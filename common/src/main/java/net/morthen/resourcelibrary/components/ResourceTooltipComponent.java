package net.morthen.resourcelibrary.components;

import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.function.Consumer;

public final class ResourceTooltipComponent implements ResourceBaseComponent, TooltipProvider {
    private final List<Component> tooltip;

    public ResourceTooltipComponent(List<Component> tooltip) {
        this.tooltip = tooltip;
    }

    public List<Component> getTooltip() {
        return tooltip;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceTooltipComponent component) {
            return this.tooltip.equals(component.getTooltip());
        }
        return false;
    }

    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        this.tooltip.forEach(consumer);
    }
}
