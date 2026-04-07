plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = "com.github.blue-triangle-tech"
version = "1.0.3"

gradlePlugin {
    plugins {
        create("bttPlugin") {
            id = "com.github.blue-triangle-tech.btt-gradle-plugin"
            implementationClass = "com.bluetriangle.bttplugin.BttPlugin"
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

kotlin {
    jvmToolchain(11)
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