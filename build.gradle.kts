import xyz.wagyourtail.unimined.internal.minecraft.task.RemapJarTaskImpl

plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version "1.4.1"
}

group = "net.canelex.customsky"
version = "1.0"

unimined.minecraft {
    version("1.8.9")

    mappings {
        searge()
        mcp("stable", "22-1.8.9")
    }

    minecraftForge {
        loader("11.15.1.2318-1.8.9")
    }
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.withType<RemapJarTaskImpl>() {
    archiveFileName = "[1.8.9] customskymod.jar"
}