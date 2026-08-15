plugins {
    id("maven-publish")
}

val id: String = project.property("id") as String

dependencies {
    compileOnly(platform(rootProject.libs.lwjgl.bom))
    compileOnly(rootProject.libs.bundles.lwjgl.all)

    compileOnly(rootProject.libs.joml)
}

tasks.jar {
    archiveBaseName.set("rimeui-core")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = id
        }
    }
}