plugins {
    id("java")
}

val id = providers.gradleProperty("id").get()
val lwjglVersion = providers.gradleProperty("lwjgl_version").get()

base {
    archivesName = "${id}-common"
}

dependencies {
    compileOnly(platform("org.lwjgl:lwjgl-bom:${lwjglVersion}"))
    compileOnly("org.lwjgl:lwjgl")
    compileOnly("org.lwjgl:lwjgl-glfw")
    compileOnly("org.lwjgl:lwjgl-opengl")
    compileOnly("org.lwjgl:lwjgl-stb")
}