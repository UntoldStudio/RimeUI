plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(project(":core"))
}

tasks.jar {
    enabled = false
}
tasks.shadowJar {
    archiveClassifier.set("")
    archiveBaseName.set("rimeui-application")
}