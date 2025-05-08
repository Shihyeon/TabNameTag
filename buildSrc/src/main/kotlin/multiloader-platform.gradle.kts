plugins {
    id("multiloader-base")
    id("maven-publish")
}

tasks {
    processResources {
        val propertiesMap = mapOf(
            "version" to version,
            "minecraft_version" to BuildConfig.MINECRAFT_VERSION,
            "fabric_loader_version" to BuildConfig.FABRIC_LOADER_VERSION,
            "neoforge_version" to BuildConfig.NEOFORGE_VERSION
        )

        inputs.properties(propertiesMap)

        filesMatching(listOf("META-INF/neoforge.mods.toml", "fabric.mod.json")) {
            expand(propertiesMap)
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.FAIL
        from(rootDir.resolve("LICENSE"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name as String
            version = version

            from(components["java"])
        }
    }
}