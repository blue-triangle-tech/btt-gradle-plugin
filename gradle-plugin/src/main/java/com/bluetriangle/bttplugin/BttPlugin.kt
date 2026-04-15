package com.bluetriangle.bttplugin

import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.bluetriangle.bttplugin.instrumentations.BttClassVisitorFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import kotlin.jvm.java

class BttPlugin : Plugin<Project> {

    companion object {
        const val BTT_EXTENSION = "btt"
        const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    }
    override fun apply(project: Project) {

        val extension = project.extensions
            .create(BTT_EXTENSION, BttPluginExtension::class.java)

        project.tasks.matching { it.name.startsWith("assemble") }
            .configureEach {
                doLast {
                    println("BttPlugin started: ${name}")
                    println("BttPlugin: composeNavigationInjectionEnabled = ${extension.composeNavigationInjectionEnabled.get()}")
                }
            }

        project.pluginManager.withPlugin(ANDROID_APPLICATION_PLUGIN) {
            val androidComponents =
                project.extensions.getByType(AndroidComponentsExtension::class.java)
            androidComponents.onVariants { variant ->
                variant.instrumentation.transformClassesWith(BttClassVisitorFactory::class.java,
                    InstrumentationScope.ALL) {
                    parameters ->
                    parameters.composeNavigationInjectionEnabled.set(
                        extension.composeNavigationInjectionEnabled
                    )
                }
            }
        }
    }
}