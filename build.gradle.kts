import me.modmuss50.mpp.ModPublishExtension
import me.modmuss50.mpp.platforms.curseforge.Curseforge
import me.modmuss50.mpp.platforms.modrinth.Modrinth

plugins {
    id("java")
    id("idea")
    id("net.morthen.gradle.multiloader") version "0.1.13" apply false

    id("net.fabricmc.fabric-loom") version "1.18.2" apply false
    id("net.minecraftforge.gradle") version "7.0.40" apply false
    id("net.neoforged.moddev") version "2.0.147" apply false

    id("me.modmuss50.mod-publish-plugin") version "2.2.0" apply false
}

subprojects {
    pluginManager.apply("idea")
    idea.module.isDownloadSources = true

    // The multiloader plugin only publishes for minecraft_version, the jars also run on the other supported versions
    pluginManager.withPlugin("me.modmuss50.mod-publish-plugin") {
        the<ModPublishExtension>().platforms.configureEach {
            when (this) {
                is Modrinth -> minecraftVersions.addAll("26.1", "26.1.1", "26.1.2")
                is Curseforge -> minecraftVersions.addAll("26.1", "26.1.1", "26.1.2")
            }
        }
    }
}