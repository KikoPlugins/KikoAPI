plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("xyz.jpenilla.run-paper") version "3.0.2"
    id("com.gradleup.shadow") version "9.3.1"
    id("maven-publish")
}

val minecraftVersion: String by project
val minMinecraftVersion: String by project
val bStatsVersion: String by project
val junitVersion: String by project
val mockbukkitVersion: String by project

group = "fr.kikoplugins.kikoapi"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")

    // Dependencies
    implementation("org.bstats:bstats-bukkit:$bStatsVersion")

    // Tests Dependencies
    testImplementation(paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:$mockbukkitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

paperweight {
    addServerDependencyTo = configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME).map { setOf(it) }
}

tasks {
    runServer {
        minecraftVersion(minecraftVersion)

        jvmArgs(
            "-Xmx4096M",
            "-Xms4096M",
            "-XX:+AllowEnhancedClassRedefinition",
            "-XX:HotswapAgent=core",
            "-Dcom.mojang.eula.agree=true"
        )

        downloadPlugins {
            modrinth("LuckPerms", "v5.5.17-bukkit")
            github("jpenilla", "TabTPS", "v1.3.29", "tabtps-paper-1.3.29.jar")
        }
    }

    build {
        dependsOn("jar")
    }

    test {
        useJUnitPlatform()
    }

    javadoc {
        isFailOnError = false
        options.encoding = "UTF-8"
    }

    register<Jar>("javadocJar") {
        dependsOn(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc.get().destinationDir)
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    shadowJar {
        archiveFileName.set("${project.name}-${project.version}.jar")

        relocate("org.bstats", "${project.group}.libs.org.bstats")
    }

    processResources {
        filteringCharset = "UTF-8"

        val props = mapOf(
            "version" to version,
            "minMinecraftVersion" to minMinecraftVersion
        )

        inputs.properties(props)
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}

artifacts {
    add("archives", tasks.named("sourcesJar"))
    add("archives", tasks.named("javadocJar"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
    repositories {
        mavenLocal()
    }
}