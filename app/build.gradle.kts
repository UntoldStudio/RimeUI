plugins {
    id("java")
    id("com.gradleup.shadow") version "9.5.1"
}

val id = providers.gradleProperty("id").get()

base {
    archivesName = "${id}-app"
}

val lwjglVersion = providers.gradleProperty("lwjgl_version").get()
val log4jCoreVersion = providers.gradleProperty("log4j_core_version").get()
val log4jApiVersion = providers.gradleProperty("log4j_api_version").get()
val slf4jApiVersion = providers.gradleProperty("slf4j_api_version").get()

dependencies {
    implementation(project(":common"))

    implementation("org.apache.logging.log4j:log4j-core:${log4jCoreVersion}")
    implementation("org.apache.logging.log4j:log4j-api:${log4jApiVersion}")
    implementation("org.slf4j:slf4j-api:${slf4jApiVersion}")

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