package net.morthen.resourcelibrary_test.metadatatypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.packs.metadata.MetadataSectionType;

public record BackpackMetadata(String colorCode) {
    public static final Codec<BackpackMetadata> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("colorCode").forGetter(BackpackMetadata::colorCode))
                    .apply(instance, BackpackMetadata::new));

    public static final MetadataSectionType<BackpackMetadata> TYPE = new MetadataSectionType<>("backpack", CODEC);
}
