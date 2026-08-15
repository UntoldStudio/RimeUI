plugins {
    id("maven-publish")
}

tasks.jar {
    archiveBaseName.set("rimeui-core")
}

val id: String = project.property("id") as String

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = id
        }
    }
}