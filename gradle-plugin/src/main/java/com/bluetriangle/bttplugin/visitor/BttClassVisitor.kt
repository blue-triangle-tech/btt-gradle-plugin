package com.bluetriangle.bttplugin.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

open class BttClassVisitor(nextClassVisitor: ClassVisitor): ClassVisitor(Opcodes.ASM9, nextClassVisitor)