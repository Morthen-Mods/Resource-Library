plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    loader = "neoforge"
    neoForgeVersion = providers.gradleProperty("neoforge")

    withGametest()
    withModPublish()

    applyMetadataReplacements(listOf("pack.mcmeta", "META-INF/neoforge.mods.toml"), mapOf(
        "neoforge_version" to neoForgeVersion.get(),
        "minecraft_version_max" to providers.gradleProperty("supported_minecraft_versions").map { it.split(",").last().trim() },
        "sources_url" to providers.gradleProperty("sources_url").get(),
        "issues_url" to providers.gradleProperty("issues_url").get()
    ))
}