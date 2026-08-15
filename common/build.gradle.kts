plugins {
    id("java")
}

val id = providers.gradleProperty("id").get()
val lwjglVersion = providers.gradleProperty("lwjgl_version").get()
val log4jCoreVersion = providers.gradleProperty("log4j_core_version").get()
val log4jApiVersion = providers.gradleProperty("log4j_api_version").get()
val slf4jApiVersion = providers.gradleProperty("slf4j_api_version").get()
val jomlVersion = providers.gradleProperty("joml_version").get()

base {
    archivesName = "${id}-common"
}

dependencies {
    compileOnly("org.joml:joml:${jomlVersion}")

    compileOnly(platform("org.lwjgl:lwjgl-bom:${lwjglVersion}"))
    compileOnly("org.lwjgl:lwjgl")
    compileOnly("org.lwjgl:lwjgl-glfw")
    compileOnly("org.lwjgl:lwjgl-opengl")
    compileOnly("org.lwjgl:lwjgl-stb")

    compileOnly("org.apache.logging.log4j:log4j-core:${log4jCoreVersion}")
    compileOnly("org.apache.logging.log4j:log4j-api:${log4jApiVersion}")
    compileOnly("org.slf4j:slf4j-api:${slf4jApiVersion}")
}