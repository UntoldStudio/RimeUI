plugins {
    id("java")
    id("com.gradleup.shadow") version "9.5.1"
}

val id = providers.gradleProperty("id").get()

base {
    archivesName = "${id}-app"
}

val lwjglVersion = providers.gradleProperty("lwjgl_version").get()

dependencies {
    implementation(project(":common"))

    implementation(platform("org.lwjgl:lwjgl-bom:${lwjglVersion}"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")
    implementation("org.lwjgl:lwjgl-stb")

    runtimeOnly("org.lwjgl:lwjgl-glfw::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-stb::natives-windows")
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