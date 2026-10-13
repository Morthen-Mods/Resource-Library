package net.morthen.resourcelibrary.gametest.tests;

import com.mojang.serialization.Codec;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.morthen.resourcelibrary.entity.EntityDataRegistry;
import net.morthen.resourcelibrary.entity.EntityDataType;
import net.morthen.resourcelibrary.gametest.GametestConstants;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EntityDataTests {
    private static final EntityDataRegistry ENTITY_DATA = EntityDataRegistry.create(GametestConstants.MOD_ID);

    private static EntityDataType<Integer> counter;
    private static EntityDataType<String> syncedName;
    private static EntityDataType<Integer> keptWithCondition;

    // Players with this tag keep keptWithCondition on death
    private static final String KEEP_TAG = "resourcelibrary_gametest_keep";

    public static void init(BiConsumer<String, Consumer<GameTestHelper>> consumer) {
        consumer.accept("entity_data_get_returns_default_when_unset", EntityDataTests::getReturnsDefaultWhenUnset);
        consumer.accept("entity_data_set_and_get", EntityDataTests::setAndGet);
        consumer.accept("entity_data_remove_restores_default", EntityDataTests::removeRestoresDefault);
        consumer.accept("entity_data_update_returns_new_value", EntityDataTests::updateReturnsNewValue);
        consumer.accept("entity_data_is_per_entity", EntityDataTests::isPerEntity);
        consumer.accept("entity_data_is_saved_and_loaded", EntityDataTests::isSavedAndLoaded);
        consumer.accept("entity_data_unset_is_not_loaded", EntityDataTests::unsetIsNotLoaded);
        consumer.accept("entity_data_builder_settings", EntityDataTests::builderSettings);
        consumer.accept("entity_data_register_duplicate_id_throws", EntityDataTests::registerDuplicateIdThrows);
        consumer.accept("entity_data_copy_on_death", EntityDataTests::copyOnDeath);
        consumer.accept("entity_data_copy_on_death_condition_not_met", EntityDataTests::copyOnDeathConditionNotMet);
        consumer.accept("entity_data_end_return_keeps_everything", EntityDataTests::endReturnKeepsEverything);
    }

    public static void setupEntityData() {
        counter = ENTITY_DATA.register("counter", EntityDataType.builder(Codec.INT, 0));
        syncedName = ENTITY_DATA.register("synced_name", EntityDataType.builder(Codec.STRING, "")
                .sync(ByteBufCodecs.STRING_UTF8, EntityDataType.SyncTarget.SELF)
                .copyOnDeath());
        keptWithCondition = ENTITY_DATA.register("kept_with_condition", EntityDataType.builder(Codec.INT, 0)
                .copyOnDeath(player -> player.entityTags().contains(KEEP_TAG)));
    }

    private static Pig spawnPig(GameTestHelper helper) {
        return helper.spawn(EntityTypes.PIG, 1, 2, 1);
    }

    public static void getReturnsDefaultWhenUnset(GameTestHelper helper) {
        Pig pig = spawnPig(helper);

        helper.assertValueEqual(counter.get(pig), 0, "value of an entity without data");
        helper.assertFalse(counter.has(pig), "has(...) is true for an entity without data");
        helper.succeed();
    }

    public static void setAndGet(GameTestHelper helper) {
        Pig pig = spawnPig(helper);
        counter.set(pig, 5);
        syncedName.set(pig, "Wilbur");

        helper.assertValueEqual(counter.get(pig), 5, "counter after set");
        helper.assertValueEqual(syncedName.get(pig), "Wilbur", "synced name after set");
        helper.assertTrue(counter.has(pig), "has(...) is false after set");
        helper.succeed();
    }

    public static void removeRestoresDefault(GameTestHelper helper) {
        Pig pig = spawnPig(helper);
        counter.set(pig, 5);
        counter.remove(pig);

        helper.assertValueEqual(counter.get(pig), 0, "value after remove");
        helper.assertFalse(counter.has(pig), "has(...) is true after remove");
        helper.succeed();
    }

    public static void updateReturnsNewValue(GameTestHelper helper) {
        Pig pig = spawnPig(helper);
        int first = counter.update(pig, value -> value + 1);
        int second = counter.update(pig, value -> value + 1);

        helper.assertValueEqual(first, 1, "value returned by the first update");
        helper.assertValueEqual(second, 2, "value returned by the second update");
        helper.assertValueEqual(counter.get(pig), 2, "value after two updates");
        helper.succeed();
    }

    public static void isPerEntity(GameTestHelper helper) {
        Pig first = spawnPig(helper);
        Pig second = spawnPig(helper);
        counter.set(first, 7);

        helper.assertValueEqual(counter.get(second), 0, "value of another entity");
        helper.succeed();
    }

    public static void isSavedAndLoaded(GameTestHelper helper) {
        Pig pig = spawnPig(helper);
        counter.set(pig, 42);
        syncedName.set(pig, "Wilbur");

        Pig loaded = saveAndLoad(helper, pig);
        helper.assertValueEqual(counter.get(loaded), 42, "counter after saving and loading");
        helper.assertValueEqual(syncedName.get(loaded), "Wilbur", "synced name after saving and loading");
        helper.succeed();
    }

    public static void unsetIsNotLoaded(GameTestHelper helper) {
        Pig pig = spawnPig(helper);
        counter.set(pig, 42);
        counter.remove(pig);

        Pig loaded = saveAndLoad(helper, pig);
        helper.assertFalse(counter.has(loaded), "a removed value was saved");
        helper.succeed();
    }

    private static Pig saveAndLoad(GameTestHelper helper, Pig pig) {
        TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, helper.getLevel().registryAccess());
        pig.saveWithoutId(output);
        CompoundTag tag = output.buildResult();

        Pig loaded = EntityTypes.PIG.create(helper.getLevel(), EntitySpawnReason.LOAD);
        helper.assertTrue(loaded != null, "could not create a pig");
        loaded.load(TagValueInput.create(ProblemReporter.DISCARDING, helper.getLevel().registryAccess(), tag));
        return loaded;
    }

    public static void builderSettings(GameTestHelper helper) {
        helper.assertValueEqual(counter.id(), Identifier.fromNamespaceAndPath(GametestConstants.MOD_ID, "counter"), "id of counter");
        helper.assertFalse(counter.isSynced(), "counter is synced");
        helper.assertTrue(syncedName.isSynced(), "synced name is not synced");
        helper.assertValueEqual(syncedName.syncTarget(), EntityDataType.SyncTarget.SELF, "sync target of synced name");
        helper.assertValueEqual(List.copyOf(ENTITY_DATA.getEntries()), List.of(counter, syncedName, keptWithCondition), "entries of the registry");
        helper.succeed();
    }

    public static void registerDuplicateIdThrows(GameTestHelper helper) {
        try {
            ENTITY_DATA.register("counter", EntityDataType.builder(Codec.INT, 0));
        } catch (IllegalArgumentException e) {
            helper.succeed();
            return;
        }
        helper.fail("registering an id twice did not throw");
    }

    private static ServerPlayer respawn(GameTestHelper helper, ServerPlayer player, boolean returnFromEnd) {
        Entity.RemovalReason reason = returnFromEnd ? Entity.RemovalReason.CHANGED_DIMENSION : Entity.RemovalReason.KILLED;
        return helper.getLevel().getServer().getPlayerList().respawn(player, returnFromEnd, reason);
    }

    private static void disconnect(GameTestHelper helper, ServerPlayer player) {
        helper.getLevel().getServer().getPlayerList().remove(player);
    }

    private static ServerPlayer playerWithAllData(GameTestHelper helper, boolean keepTag) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        if (keepTag) player.addTag(KEEP_TAG);
        counter.set(player, 1);
        syncedName.set(player, "Steve");
        keptWithCondition.set(player, 3);
        return player;
    }

    public static void copyOnDeath(GameTestHelper helper) {
        ServerPlayer respawned = respawn(helper, playerWithAllData(helper, true), false);

        helper.assertFalse(counter.has(respawned), "data without copyOnDeath was kept on death");
        helper.assertValueEqual(syncedName.get(respawned), "Steve", "data with copyOnDeath after death");
        helper.assertValueEqual(keptWithCondition.get(respawned), 3, "data with a met copyOnDeath condition after death");
        disconnect(helper, respawned);
        helper.succeed();
    }

    public static void copyOnDeathConditionNotMet(GameTestHelper helper) {
        ServerPlayer respawned = respawn(helper, playerWithAllData(helper, false), false);

        helper.assertFalse(keptWithCondition.has(respawned), "data with a copyOnDeath condition that is not met was kept on death");
        helper.assertValueEqual(syncedName.get(respawned), "Steve", "data with copyOnDeath after death");
        disconnect(helper, respawned);
        helper.succeed();
    }

    public static void endReturnKeepsEverything(GameTestHelper helper) {
        ServerPlayer respawned = respawn(helper, playerWithAllData(helper, false), true);

        helper.assertValueEqual(counter.get(respawned), 1, "data without copyOnDeath after returning from the End");
        helper.assertValueEqual(syncedName.get(respawned), "Steve", "data with copyOnDeath after returning from the End");
        helper.assertValueEqual(keptWithCondition.get(respawned), 3, "data with a copyOnDeath condition after returning from the End");
        disconnect(helper, respawned);
        helper.succeed();
    }
}
