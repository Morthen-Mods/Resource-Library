package net.morthen.resourcelibrary.gametests;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.morthen.resourcelibrary.LibConstants;
import net.morthen.resourcelibrary.gametests.objects.TestRecipeRemainderItem;
import net.morthen.resourcelibrary.registration.RegistryObject;
import net.morthen.resourcelibrary.registration.RegistryProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class RecipeRemainderTest {
    private static final int FIXED_DURABILITY = 3;
    private static final int DYNAMIC_DURABILITY = 4;

    private static final RegistryProvider<Item> ITEMS = RegistryProvider.get(LibConstants.MOD_ID, BuiltInRegistries.ITEM);

    private static RegistryObject<TestRecipeRemainderItem.FixedDurability> fixedDurabilityItem;
    private static RegistryObject<TestRecipeRemainderItem.DynamicDurability> dynamicDurabilityItem;

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("fixed_durability_remainder_increments_damage_on_use", RecipeRemainderTest::fixedDurabilityRemainderIncrementsDamageOnUse);
        consumer.accept("fixed_durability_remainder_consumed_on_final_use", RecipeRemainderTest::fixedDurabilityRemainderConsumedOnFinalUse);

        consumer.accept("dynamic_durability_applies_durability_on_first_use", RecipeRemainderTest::dynamicDurabilityAppliesDurabilityOnFirstUse);
        consumer.accept("dynamic_durability_remainder_consumed_on_final_use", RecipeRemainderTest::dynamicDurabilityRemainderConsumedOnFinalUse);
    }

    public static void setupRemainder() {
        fixedDurabilityItem = ITEMS.register("test_remainder_fixed",
                () -> new TestRecipeRemainderItem.FixedDurability("test_remainder_fixed", FIXED_DURABILITY));
        dynamicDurabilityItem = ITEMS.register("test_remainder_dynamic",
                () -> new TestRecipeRemainderItem.DynamicDurability("test_remainder_dynamic", DYNAMIC_DURABILITY));
    }

    // Runs the matching data-driven recipe (see data/resourcelibrary/recipe/) for a 2-wide crafting grid of
    // [ingredient, stick] through the same RecipeManager/CraftingRecipe#getRemainingItems path the vanilla
    // crafting menu uses, so these tests exercise the real recipe resolution, not just getRemainingItem() directly.
    private static NonNullList<ItemStack> craftAndGetRemainingItems(GameTestHelper helper, ItemStack ingredient) {
        ServerLevel level = helper.getLevel();
        CraftingInput input = CraftingInput.of(2, 1, List.of(ingredient, new ItemStack(Items.STICK)));
        Optional<RecipeHolder<CraftingRecipe>> recipe = level.recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, level);

        helper.assertTrue(recipe.isPresent(), "No crafting recipe matched the remainder test ingredients");
        return recipe.get().value().getRemainingItems(input);
    }

    /////////////////////////////////////////////////
    ///                FixedDurability             ///
    /////////////////////////////////////////////////

    public static void fixedDurabilityRemainderIncrementsDamageOnUse(GameTestHelper helper) {
        ItemStack fresh = new ItemStack(fixedDurabilityItem.get());
        NonNullList<ItemStack> remaining = craftAndGetRemainingItems(helper, fresh);

        ItemStack remainder = remaining.get(0);
        helper.assertTrue(remainder.getItem() == fixedDurabilityItem.get(), "Remainder is not the same fixed-durability item");
        helper.assertTrue(remainder.getDamageValue() == 1, "Remainder damage should have incremented by 1, was " + remainder.getDamageValue());
        helper.assertTrue(remaining.get(1).isEmpty(), "Stick should not produce a crafting remainder");
        helper.succeed();
    }

    public static void fixedDurabilityRemainderConsumedOnFinalUse(GameTestHelper helper) {
        ItemStack almostBroken = new ItemStack(fixedDurabilityItem.get());
        almostBroken.setDamageValue(FIXED_DURABILITY - 1);

        NonNullList<ItemStack> remaining = craftAndGetRemainingItems(helper, almostBroken);

        helper.assertTrue(remaining.get(0).isEmpty(), "Fixed-durability item should be fully consumed on its last use instead of leaving a remainder");
        helper.succeed();
    }

    /////////////////////////////////////////////////
    ///               DynamicDurability             ///
    /////////////////////////////////////////////////

    public static void dynamicDurabilityAppliesDurabilityOnFirstUse(GameTestHelper helper) {
        ItemStack fresh = new ItemStack(dynamicDurabilityItem.get());
        NonNullList<ItemStack> remaining = craftAndGetRemainingItems(helper, fresh);

        ItemStack remainder = remaining.get(0);
        helper.assertTrue(remainder.getItem() == dynamicDurabilityItem.get(), "Remainder is not the same dynamic-durability item");
        helper.assertTrue(remainder.get(DataComponents.MAX_DAMAGE) != null && remainder.get(DataComponents.MAX_DAMAGE) == DYNAMIC_DURABILITY,
                "Remainder should have been given the configured durability the first time the item was used");
        helper.assertTrue(remainder.getDamageValue() == 1, "Remainder damage should have incremented by 1, was " + remainder.getDamageValue());
        helper.succeed();
    }

    public static void dynamicDurabilityRemainderConsumedOnFinalUse(GameTestHelper helper) {
        ItemStack almostBroken = dynamicDurabilityItem.get().applyDurability();
        almostBroken.setDamageValue(DYNAMIC_DURABILITY - 1);

        NonNullList<ItemStack> remaining = craftAndGetRemainingItems(helper, almostBroken);

        helper.assertTrue(remaining.get(0).isEmpty(), "Dynamic-durability item should be fully consumed on its last use instead of leaving a remainder");
        helper.succeed();
    }
}
