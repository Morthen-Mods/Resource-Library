plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    neoForgeVersion = "26.2.0.54-beta"
    loader = "neoforge"

    withGametest()

    applyMetadataReplacements(listOf("pack.mcmeta", "META-INF/neoforge.mods.toml"), mapOf(
        "neoforge_version" to neoForgeVersion.get(),
        "sources_url" to providers.gradleProperty("sources_url").get(),
        "issues_url" to providers.gradleProperty("issues_url").get()
    ))
}