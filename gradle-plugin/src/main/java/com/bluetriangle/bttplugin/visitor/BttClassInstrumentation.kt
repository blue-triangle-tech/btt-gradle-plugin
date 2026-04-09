package com.bluetriangle.bttplugin.visitor

import com.android.build.api.instrumentation.ClassContext
import org.objectweb.asm.ClassVisitor

abstract class BttClassInstrumentation {

    abstract fun isEnabled(parameters: ByteCodeManipulationParameters): Boolean

    abstract fun getVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor

    abstract val className: String

}