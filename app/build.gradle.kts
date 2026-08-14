plugins {
    id("java")
    id("com.gradleup.shadow") version "9.5.1"
}

val id: String = providers.gradleProperty("id").get()

base {
    archivesName = "${id}-app"
}

dependencies {
    implementation(project(":common"))
}

tasks.shadowJar {
    archiveClassifier = ""
    manifest {
        attributes["Main-Class"] = "top.untoldstudio.simpleui.app.Main"
    }
    from(project(":common").sourceSets.main.get().output)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    enabled = false
}