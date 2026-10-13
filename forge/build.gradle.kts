plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    loader = "forge"

    forgeVersion = providers.gradleProperty("forge")
    forgeMixins = listOf(
        "${ modId.get() }.mixins.json",
        "${ modId.get() }.forge.mixins.json"
    )

    withGametest()
    withModPublish()

    applyMetadataReplacements(listOf("pack.mcmeta", "META-INF/mods.toml"), mapOf(
        "forge_version" to forgeVersion.get(),
        "minecraft_version_max" to providers.gradleProperty("supported_minecraft_versions").map { it.split(",").last().trim() },
        "issues_url" to providers.gradleProperty("issues_url")
    ))
}