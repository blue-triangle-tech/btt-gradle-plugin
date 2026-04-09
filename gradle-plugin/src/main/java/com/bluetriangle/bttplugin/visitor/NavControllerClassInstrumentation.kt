package com.bluetriangle.bttplugin.visitor

import com.android.build.api.instrumentation.ClassContext
import org.objectweb.asm.ClassVisitor

class NavControllerClassInstrumentation :
    BttClassInstrumentation() {

    companion object {
        private const val NAV_HOST_CONTROLLER_CLASSNAME =
            "androidx.navigation.compose.NavHostControllerKt"
    }

    override fun isEnabled(parameters: ByteCodeManipulationParameters): Boolean {
        return parameters.composeNavigationInjectionEnabled.get()
    }

    override fun getVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        return NavControllerClassVisitor(nextClassVisitor)
    }

    override val className: String
        get() = NAV_HOST_CONTROLLER_CLASSNAME

}