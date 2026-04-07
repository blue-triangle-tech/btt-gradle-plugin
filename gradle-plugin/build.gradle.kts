plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = "com.github.blue-triangle-tech"
version = "2.19.5"

gradlePlugin {
    plugins {
        create("bttPlugin") {
            id = "com.github.blue-triangle-tech.btt-gradle-plugin"
            implementationClass = "com.bluetriangle.bttplugin.BttPlugin"
        }
    }
}

repositories {
    mavenCentral()
    google() // optional but good to keep
}
dependencies {
    implementation(kotlin("stdlib"))
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.blue-triangle-tech"
            artifactId = "btt-gradle-plugin"
            version = "2.19.5"

            from(components["java"])
        }
    }
}