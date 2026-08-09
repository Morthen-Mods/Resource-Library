plugins {
    id("java")
    id("idea")
    id("net.morthen.gradle.multiloader") version "0.1.2" apply false
    id("net.fabricmc.fabric-loom") version "1.17-SNAPSHOT" apply false
    id("net.minecraftforge.gradle") version "[7.0.30, 8)" apply false
    id("net.neoforged.moddev") version "2.0.141" apply false
}

idea {
    module {
        isDownloadSources = true
    }
}