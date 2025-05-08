tasks.register("printProperties") {
    doLast {
        println("MINECRAFT_VERSION=${BuildConfig.MINECRAFT_VERSION}")
        println("MOD_VERSION=${BuildConfig.MOD_VERSION}")
    }
}
