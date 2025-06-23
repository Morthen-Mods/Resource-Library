package net.xstopho.resourcelibrary_test;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.xstopho.resourcelibrary.service.CoreServices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class SaveLootTablesCommand {

    private static final HashMap<String, List<String>> differed_lists = new HashMap<>();

    public static void saveCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("saveLootTables").executes(context -> {
            File file = new File(CoreServices.getConfigDir() + "/loot_tables.txt");

            List<String> lootTableKeys = Stream.concat(
                    BuiltInLootTables.all().stream(),
                    BuiltInRegistries.ENTITY_TYPE.stream().map(EntityType::getDefaultLootTable)
            ).map(key -> key.location().toString())
                    .distinct().sorted().toList();

            lootTableKeys.forEach(SaveLootTablesCommand::buildFields);

            StringBuilder builder = new StringBuilder();
            differed_lists.forEach((type, strings) -> {
                builder.append(type).append("\n");
                for (String field : strings) {
                    builder.append(field).append("\n");
                }
                builder.append("\n");
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

        if (field.length > 2) {
            value = String.format(field_string, field[field.length - 2].toUpperCase() + "_" + field[field.length - 1].toUpperCase(), path[1].toLowerCase());
        } else {
            value = String.format(field_string, field[field.length - 1].toUpperCase(), path[1].toLowerCase());
        }

        return value;
    }
}
