package com.bluetriangle.bttplugin.visitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class NavControllerMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor,
    access: Int,
    name: String,
    descriptor: String
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    override fun onMethodEnter() {
        val replacementOwner = "com.bluetriangle.analytics.compose.ComposeKt"
        val replacementName = "withBttNavigationTracker"
        val replacementDescriptor = "(Landroidx/navigation/NavController;Landroidx/compose/runtime/Composer;I)Landroidx/navigation/NavController"

        invokeStatic(
            Type.getType(replacementOwner),
            Method(
                replacementName,
                replacementDescriptor
            )
        )
    }
}
