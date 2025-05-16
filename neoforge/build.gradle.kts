plugins {
    id("multiloader-platform")

    id("net.neoforged.gradle.userdev") version("7.0.104")
    id("net.neoforged.gradle.mixin") version("7.0.105")
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

    implementation("net.neoforged:neoforge:${BuildConfig.NEOFORGE_VERSION}")
}

sourceSets {
    main {
        compileClasspath += configurationCommonModJava
        runtimeClasspath += configurationCommonModJava
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