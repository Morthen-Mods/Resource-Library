package net.xstopho.resourcelibrary_test;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.xstopho.resourcelibrary.service.CoreServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SaveLootTablesCommand {

    private static final List<String> loot_tables = new ArrayList<>();
    private static final HashMap<String, List<String>> differed_lists = new HashMap<>();

    public static void saveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("saveLootTables").executes(context -> {
            File file = new File(CoreServices.getConfigDir() + "/loot_tables.txt");


            BuiltInRegistries.ENTITY_TYPE.forEach(SaveLootTablesCommand::addEntityLootTable);
            BuiltInLootTables.all().forEach(SaveLootTablesCommand::addLootTable);
            Collections.sort(loot_tables);

            loot_tables.forEach(SaveLootTablesCommand::buildFields);

            StringBuilder builder = new StringBuilder();
            differed_lists.forEach((type, strings) -> {
                builder.append("\n").append(type).append("\n");
                for (String field : strings) {
                    builder.append(field).append("\n");
                }
            });

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(builder.toString());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            context.getSource().sendSystemMessage(Component.literal(String.format("Saving Loot tables in file: %s", file.getPath())));

            return 0;
        }));
    }

    private static void addLootTable(ResourceKey<LootTable> key) {
        ResourceLocation loot = key.location();
        loot_tables.add(String.format("%s:%s", loot.getNamespace(), loot.getPath()));
    }

    private static void addEntityLootTable(EntityType<?> type) {
        type.getDefaultLootTable().ifPresent(SaveLootTablesCommand::addLootTable);
    }

    private static void buildFields(String string) {
        String[] path = string.split(":");
        String type = path[1].split("/")[0];
        String value = getValueString(path);

        if (path[1].contains("trial_chamber")) {
            type = "trial_chamber";
        }

        if (differed_lists.containsKey(type)) {
            differed_lists.get(type).add(value);
        } else {
            List<String> values = new ArrayList<>();
            values.add(value);
            differed_lists.put(type, values);
        }
    }

    private static String getValueString(String[] path) {
        String field_string = "public static final ResourceKey<LootTable> %s = createKey(\"%s\");";
        String[] field = path[1].split("/");

        String value;

        if (path[1].contains("sheep")) {
            if (path[1].split("/").length > 2) {
                value = String.format(field_string, "SHEEP_" + field[field.length - 1].toUpperCase(), path[1].toLowerCase());
            } else {
                value = String.format(field_string, field[field.length - 1].toUpperCase(), path[1].toLowerCase());
            }
        } else if (path[1].contains("dispensers")) {
            value = String.format(field_string, "DISPENSERS_" + field[field.length - 1].toUpperCase(), path[1].toLowerCase());

        } else if (path[1].contains("pots")) {
            value = String.format(field_string, "POTS_" + field[field.length - 1].toUpperCase(), path[1].toLowerCase());

        } else if (path[1].contains("ominous")) {
            value = String.format(field_string, "OMINOUS_" + field[field.length - 1].toUpperCase(), path[1].toLowerCase());

        } else {
            value = String.format(field_string, field[field.length - 1].toUpperCase(), path[1].toLowerCase());
        }
        return value;
    }
}
