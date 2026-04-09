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
    implementation("com.android.tools.build:gradle:8.4.0") // match your AGP
    implementation("org.ow2.asm:asm-util:9.6")
    implementation("org.ow2.asm:asm-commons:9.6")
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