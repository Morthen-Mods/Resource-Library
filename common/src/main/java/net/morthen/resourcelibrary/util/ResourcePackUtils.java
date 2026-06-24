package net.morthen.resourcelibrary.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.morthen.resourcelibrary.LibConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;


public class ResourcePackUtils {

    private static final Minecraft client =  Minecraft.getInstance();

    /**
     * Reads the custom Data from all active Resource Packs based on the given MetadataSectionType
     * @param metadataSectionType {@link MetadataSectionType<T>}
     * @return {@link LinkedList<T>}
     */
    public static <T> LinkedList<T> readAllMetaData(MetadataSectionType<T> metadataSectionType) {
        LinkedList<T> allMetaData = new LinkedList<>();

        for (Pack pack : client.getResourcePackRepository().getSelectedPacks()) {
            try (PackResources resources = pack.open()) {
                T metadataSection = resources.getMetadataSection(metadataSectionType);
                if (metadataSection != null) {
                    allMetaData.add(metadataSection);
                }
            } catch (IOException e) {
                LibConstants.LOG.error("Error reading pack.mcmeta", e);
            }
        }

        return allMetaData;
    }

    /**
     * Reads the first data found from the active Resource Packs based on the given MetadataSectionType
     * @param metadataSectionType {@link MetadataSectionType<T>}
     * @return {@link LinkedList<T>}
     */
    public static <T> T readMetaData(MetadataSectionType<T> metadataSectionType) {
        for (Pack pack : client.getResourcePackRepository().getSelectedPacks()) {
            try (PackResources resources = pack.open()) {
                T metadataSection = resources.getMetadataSection(metadataSectionType);
                if (metadataSection != null) {
                    return metadataSection;
                }
            } catch (IOException e) {
                LibConstants.LOG.error("Error reading pack.mcmeta", e);
            }
        }

        return null;
    }

    /**
     * Reads all the custom Data from all active Resource Packs
     * @param object {@link String} JsonObject name
     * @return {@link LinkedList<JsonObject>}
     */
    public static LinkedList<JsonObject> readAllMetaData(String object) {
        LinkedList<JsonObject> allMetaData = new LinkedList<>();

        for (Pack pack : client.getResourcePackRepository().getSelectedPacks()) {
            try (PackResources resources = pack.open()) {
                IoSupplier<InputStream> ioSup = resources.getRootResource("pack.mcmeta");
                if (ioSup == null) continue; // Jump to next Resource pack is pack.mcmeta is missing

                try (InputStream metaStream = ioSup.get()) {
                    String json = new String(metaStream.readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject meta = JsonParser.parseString(json).getAsJsonObject();

                    if (meta.has(object) && meta.get(object).isJsonObject()) {
                        allMetaData.add(meta.getAsJsonObject(object));
                    }
                }
            } catch (IOException e) {
                LibConstants.LOG.error("Error reading pack.mcmeta", e);
            }
        }

        return allMetaData;
    }

    /**
     * Reads the first data found from the active Resource packs
     * @param object {@link String} JsonObject name
     * @return {@link JsonObject}
     */
    public static JsonObject readMetaData(String object) {
        for (Pack pack : client.getResourcePackRepository().getSelectedPacks()) {
            try (PackResources resources = pack.open()) {
                IoSupplier<InputStream> ioSup = resources.getRootResource("pack.mcmeta");
                if(ioSup == null) continue; // Jump to next Resource pack is pack.mcmeta is missing

                try (InputStream metaStream = ioSup.get()) {
                    String json = new String(metaStream.readAllBytes(), StandardCharsets.UTF_8);
                    JsonObject meta = JsonParser.parseString(json).getAsJsonObject();

                    if (meta.has(object) && meta.get(object).isJsonObject()) {
                        return meta.getAsJsonObject(object);
                    }
                }
            } catch (IOException e) {
                LibConstants.LOG.error("Error reading pack.mcmeta", e);
            }
        }

        return null;
    }
}
