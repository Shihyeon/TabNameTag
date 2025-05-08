plugins {
    id("multiloader-platform")

    id("net.neoforged.moddev") version("2.0.+")
}

base {
    archivesName = "tabnametag-neoforge"
}

val configurationCommonModJava: Configuration = configurations.create("commonModJava") {
    isCanBeResolved = true
}
val configurationCommonModResources: Configuration = configurations.create("commonModResources") {
    isCanBeResolved = true
}

repositories {
    maven("https://maven.neoforged.net/releases/")
}

dependencies {
    configurationCommonModJava(project(path = ":common", configuration = "commonMainJava"))

    configurationCommonModResources(project(path = ":common", configuration = "commonMainResources"))
}

sourceSets {
    main {
        compileClasspath += configurationCommonModJava
        runtimeClasspath += configurationCommonModJava
    }
}

neoForge {
    version = BuildConfig.NEOFORGE_VERSION

    if (BuildConfig.PARCHMENT_VERSION != null) {
        parchment {
            minecraftVersion = BuildConfig.MINECRAFT_VERSION
            mappingsVersion = BuildConfig.PARCHMENT_VERSION
        }
    }

    runs {
        create("Client") {
            client()
            ideName = "NeoForge/Client"
        }
    }

    mods {
        create("tabnametag") {
            sourceSet(sourceSets["main"])
            sourceSet(project(":common").sourceSets["main"])
        }
    }
}

tasks {
    jar {
        from(configurationCommonModJava)
        destinationDirectory.set(file(rootProject.layout.buildDirectory).resolve("mods"))
    }

    processResources {
        from(configurationCommonModResources)
    }
}