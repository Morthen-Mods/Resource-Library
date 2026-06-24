package net.xstopho.resourcelibrary_test;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.xstopho.resourcelibrary.util.ResourcePackUtils;
import net.xstopho.resourcelibrary_test.metadatatypes.BackpackMetadata;

import java.util.LinkedList;

public class LibraryTest implements ModInitializer {
    
    @Override
    public void onInitialize() {
        TestConstants.commonInit();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> SaveLootTablesCommand.saveCommand(dispatcher));

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> {
            commandDispatcher.register(Commands.literal("readMetaData").executes(commandContext -> {
                JsonObject meta = ResourcePackUtils.readMetaData("backpack");
                if(meta != null) {
                    commandContext.getSource().sendSystemMessage(Component.literal("Erstes Ergebnis: " + meta));
                }

                LinkedList<JsonObject> allMeta = ResourcePackUtils.readAllMetaData("backpack");
                if(!allMeta.isEmpty()) {
                    commandContext.getSource().sendSystemMessage(Component.literal("Alle Ergebnise: " + allMeta));
                }

                BackpackMetadata backpackMetadata = ResourcePackUtils.readMetaData(BackpackMetadata.TYPE);
                if (backpackMetadata != null) commandContext.getSource().sendSystemMessage(Component.literal("SectionType Ergebnis: " + backpackMetadata));

                LinkedList<BackpackMetadata> allSectionData = ResourcePackUtils.readAllMetaData(BackpackMetadata.TYPE);
                if (!allSectionData.isEmpty()) commandContext.getSource().sendSystemMessage(Component.literal("SectionType Ergebnise: " + allSectionData));

                return 0;
            }));
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server)
                -> TestConstants.TEST_EVENT.invoker().onJoin(handler.getPlayer()));
    }
}
