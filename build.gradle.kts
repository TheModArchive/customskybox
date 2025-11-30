import xyz.wagyourtail.unimined.internal.minecraft.task.RemapJarTaskImpl

plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version "1.4.1"
}

group = "net.canelex.customsky"
version = "1.0"

unimined.minecraft {
    version("1.7.10")

    mappings {
        searge()
        mcp("stable", "12-1.7.10")
    }

    minecraftForge {
        loader("10.13.4.1614-1.7.10")
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
    archiveFileName = "[1.7.10] customskymod.jar"
}