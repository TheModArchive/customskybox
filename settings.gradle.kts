pluginManagement {
    repositories {
        mavenCentral()
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.wagyourtail.xyz/releases")
        maven("https://maven.wagyourtail.xyz/snapshots")
        gradlePluginPortal {
            content {
                excludeGroup("org.apache.logging.log4j")
            }
        }
    }
}

rootProject.name = "customskymod"