plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(rootProject.libs.bundles.log.all)

    implementation(project(":core"))

    implementation(platform(rootProject.libs.lwjgl.bom))
    implementation(rootProject.libs.bundles.lwjgl.all)

    implementation(rootProject.libs.joml)

    implementation(rootProject.libs.jansi)
}

tasks.jar {
    enabled = false
}
tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("rimeui-application")
}