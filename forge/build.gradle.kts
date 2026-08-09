plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    loader = "forge"

    forgeVersion = "65.1.0"
    forgeMixins = listOf(
        "${ modId.get() }.mixins.json",
        "${ modId.get() }.forge.mixins.json"
    )

    withGametest()

    applyMetadataReplacements(listOf("pack.mcmeta", "META-INF/mods.toml"), mapOf(
        "forge_version" to forgeVersion.get(),
        "issues_url" to providers.gradleProperty("issues_url")
    ))
}