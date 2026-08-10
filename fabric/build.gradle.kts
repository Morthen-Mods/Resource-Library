plugins {
    id("net.morthen.gradle.multiloader")
}

multiloader {
    loader = "fabric"

    fabricApiVersion = providers.gradleProperty("fabric_api")
    fabricLoaderVersion = providers.gradleProperty("fabric_loader")

    withGametest()
    withModPublish {
        required = listOf(
            "fabric-api"
        )
    }

    applyMetadataReplacements(listOf("pack.mcmeta", "*.mixins.json", "fabric.mod.json"), mapOf(
        "fabric_api" to fabricApiVersion.get(),
        "fabric_loader" to fabricLoaderVersion.get(),
        "sources_url" to providers.gradleProperty("sources_url").get(),
        "issues_url" to providers.gradleProperty("issues_url").get()
    ))
}

dependencies {
    implementation("com.terraformersmc:modmenu:20.0.1")
}