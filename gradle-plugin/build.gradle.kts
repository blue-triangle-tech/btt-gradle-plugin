plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = "com.github.blue-triangle-tech"
version = "1.0.2"

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

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["java"])
            }
        }
    }
}