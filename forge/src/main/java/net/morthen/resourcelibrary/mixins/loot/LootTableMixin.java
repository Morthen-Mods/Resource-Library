package net.morthen.resourcelibrary.mixins.loot;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.morthen.resourcelibrary.modifier.ForgeLootDropModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Mixin(LootTable.class)
public class LootTableMixin {

    @Unique
    @Nullable
    Holder<LootTable> lootTableHolder;

    @WrapMethod(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V")
    private void resourcelibrary$modifyDrops(LootContext context, Consumer<ItemStack> lootConsumer, Operation<Void> original) {
        if (lootTableHolder == null) {
            this.lootTableHolder = getEntryOrDirect(context.getLevel(), (LootTable) (Object) this);
        }

        List<ItemStack> list = new ObjectArrayList<>();
        original.call(context, (Consumer<ItemStack>) list::add);
        Optional<ResourceKey<LootTable>> optional = this.lootTableHolder.unwrapKey();
        optional.ifPresent(lootTable -> {
            ForgeLootDropModifier.modifiers.forEach(modifier -> {
                if (lootTable.equals(modifier.lootTable())) {
                    list.forEach(modifier.modifier()::modify);
                }
            });
        });
        list.forEach(lootConsumer);
    }

    private Holder<LootTable> getEntryOrDirect(ServerLevel level, LootTable table) {
        HolderLookup.Provider provider = level
                .getServer()
                .reloadableRegistries()
                .lookup();

        HolderLookup<LootTable> lootTableHolderLookup = provider
                .lookup(Registries.LOOT_TABLE)
                .orElseThrow(() -> new IllegalStateException("Failed to fetch LootTable provider from HolderLookup.Provider"));

        return lootTableHolderLookup
                .listElements()
                .filter(it -> it.value().equals(table))
                .findFirst()
                .map(Function.<Holder<LootTable>>identity())
                .orElseGet(() -> Holder.direct(table));
    }
}
