package com.bluetriangle.bttplugin.instrumentations

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

open class BttClassVisitor(nextClassVisitor: ClassVisitor): ClassVisitor(Opcodes.ASM9, nextClassVisitor)