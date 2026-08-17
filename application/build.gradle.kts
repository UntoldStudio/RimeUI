import org.gradle.internal.os.OperatingSystem

plugins {
    alias(libs.plugins.shadow)
}

val lwjglVersion = rootProject.libs.versions.lwjgl.get()
val lwjglNatives = when {
    OperatingSystem.current().isWindows -> "natives-windows"
    OperatingSystem.current().isLinux -> "natives-linux"
    OperatingSystem.current().isMacOsX -> {
        val arch = System.getProperty("os.arch")
        if (arch == "aarch64" || arch == "arm64") "natives-macos-arm64" else "natives-macos"
    }
    else -> throw GradleException("Unsupported operating system")
}

dependencies {
    implementation(rootProject.libs.bundles.log.all)

    implementation(project(":core"))

    implementation(platform(rootProject.libs.lwjgl.bom))
    implementation(rootProject.libs.bundles.lwjgl.all)

    implementation(rootProject.libs.joml)

    implementation(rootProject.libs.jansi)

    runtimeOnly("org.lwjgl:lwjgl:${lwjglVersion}:${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-opengl:${lwjglVersion}:${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-glfw:${lwjglVersion}:${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-stb:${lwjglVersion}:${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-freetype:${lwjglVersion}:${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-harfbuzz:${lwjglVersion}:${lwjglNatives}")
}

tasks.jar {
    enabled = false
}
tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("rimeui-application")
}