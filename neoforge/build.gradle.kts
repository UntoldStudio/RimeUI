import org.gradle.api.publish.maven.MavenPublication
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    id("java")
    id("net.neoforged.moddev") version "2.0.143"
    id("maven-publish")
    id("com.gradleup.shadow") version "9.5.1"
}

val mod_version: String = providers.gradleProperty("version").get()
val mod_group_id: String = providers.gradleProperty("group_id").get()
val minecraft_version: String = providers.gradleProperty("minecraft_version").get()
val minecraft_version_range: String = providers.gradleProperty("minecraft_version_range").get()
val neo_version: String = providers.gradleProperty("neo_version").get()
val neo_version_range: String = providers.gradleProperty("neo_version_range").get()
val loader_version_range: String = providers.gradleProperty("loader_version_range").get()
val mod_id: String = providers.gradleProperty("id").get()
val mod_name: String = providers.gradleProperty("name").get()
val mod_license: String = providers.gradleProperty("license").get()
val mod_authors: String = providers.gradleProperty("authors").get()
val mod_description: String = providers.gradleProperty("mod_description").get()
val parchment_mappings_version: String = providers.gradleProperty("parchment_mappings_version").get()
val parchment_minecraft_version: String = providers.gradleProperty("parchment_minecraft_version").get()

val shade: Configuration = configurations.create("shade")
configurations.named("implementation") {
    extendsFrom(shade)
}

base {
    archivesName = "${mod_id}-neoforge"
}

val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "neo_version" to neo_version,
        "neo_version_range" to neo_version_range,
        "loader_version_range" to loader_version_range,
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_license" to mod_license,
        "mod_version" to mod_version,
        "mod_authors" to mod_authors,
        "mod_description" to mod_description
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}

sourceSets.main.get().resources.srcDir("src/generated/resources")
sourceSets.main.get().resources.srcDir(generateModMetadata.map { it.destinationDir })

dependencies {
    shade(project(":common"))
}

neoForge {
    version = neo_version

    parchment {
        mappingsVersion = parchment_mappings_version
        minecraftVersion = parchment_minecraft_version
    }

    runs {
        create("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        }
    }

    mods {
        create(mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }

    ideSyncTask(generateModMetadata)
}

tasks.shadowJar {
    archiveClassifier = ""
    configurations = listOf(shade)
    from(project(":common").sourceSets.main.get().output)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["shadow"])
            artifactId = mod_id
        }
    }
}