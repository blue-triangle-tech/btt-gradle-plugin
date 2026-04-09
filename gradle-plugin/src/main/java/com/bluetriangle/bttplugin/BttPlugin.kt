package com.bluetriangle.bttplugin

import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.bluetriangle.bttplugin.visitor.BttClassVisitorFactory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import kotlin.jvm.java

class BttPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        val extension = project.extensions
            .create("btt", BttPluginExtension::class.java)

        project.tasks.matching { it.name.startsWith("assemble") }
            .configureEach {
                doLast {
                    println("BttPlugin started: ${name}")
                    println("BttPlugin: composeNavigationInjectionEnabled = ${extension.composeNavigationInjectionEnabled.get()}")
                }
            }

        project.pluginManager.withPlugin("com.android.application") {
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