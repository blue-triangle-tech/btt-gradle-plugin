package com.bluetriangle.bttplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BttPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val extension = project.extensions
            .create("btt", BttPluginExtension::class.java)

        project.tasks.matching { it.name.startsWith("assemble") }
            .configureEach {
                doLast {
                    println("BTT: ${name} finished!")
                    println("BTT: composeNavigationInjectionEnabled = ${extension.composeNavigationInjectionEnabled.get()}")
                }
            }
    }
}