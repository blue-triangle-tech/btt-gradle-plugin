package com.bluetriangle.bttplugin

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class BttPluginExtension @Inject constructor(objects: ObjectFactory) {

    val composeNavigationInjectionEnabled: Property<Boolean> =
        objects.property(Boolean::class.java)
            .convention(true)
}