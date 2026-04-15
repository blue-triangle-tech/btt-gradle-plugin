package com.bluetriangle.bttplugin.util

import org.objectweb.asm.*
import java.io.File
import java.io.InputStream
import java.util.Properties
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream

object SdkVersionResolver {

    private const val SDK_PROD_GROUP = "com.github.blue-triangle-tech"
    private const val SDK_DEV_GROUP = "com.github.IsmailAloha"
    private const val SDK_ARTIFACT = "btt-android-sdk"

    private const val META_INF_PATH = "META-INF/btt.properties"
    private const val BUILD_CONFIG_CLASS_SUFFIX = "BuildConfig.class"

    private val cache = ConcurrentHashMap<File, String?>()

    fun resolveFromArtifacts(files: Collection<File>): String {
        files.forEach { file ->
            if (!isSdkArtifact(file)) return@forEach

            val version = cache.getOrPut(file) {
                readFromMetaInf(file)
                    ?: readFromBuildConfig(file)
            }

            if (version != null) return version
        }

        return "0.0.0"
    }

    private fun isSdkArtifact(file: File): Boolean {
        val name = file.name
        return name.contains(SDK_ARTIFACT)
    }

    // =========================
    // META-INF READER
    // =========================

    private fun readFromMetaInf(file: File): String? {
        return try {
            when {
                file.extension == "aar" -> readMetaInfFromAar(file)
                file.extension == "jar" -> readMetaInfFromJar(file)
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun readMetaInfFromJar(jarFile: File): String? {
        ZipFile(jarFile).use { zip ->
            val entry = zip.getEntry(META_INF_PATH) ?: return null
            zip.getInputStream(entry).use { return loadVersion(it) }
        }
    }

    private fun readMetaInfFromAar(aarFile: File): String? {
        ZipFile(aarFile).use { aar ->
            val classesEntry = aar.getEntry("classes.jar") ?: return null

            aar.getInputStream(classesEntry).use { jarStream ->
                ZipInputStream(jarStream).use { zis ->
                    var entry: ZipEntry?

                    while (zis.nextEntry.also { entry = it } != null) {
                        if (entry!!.name == META_INF_PATH) {
                            return loadVersion(zis)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun loadVersion(input: InputStream): String? {
        val props = Properties()
        props.load(input)
        return props.getProperty("sdk.version")
    }

    // =========================
    // BUILD CONFIG (ASM)
    // =========================

    private fun readFromBuildConfig(file: File): String? {
        return try {
            when {
                file.extension == "aar" -> readBuildConfigFromAar(file)
                file.extension == "jar" -> readBuildConfigFromJar(file)
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun readBuildConfigFromJar(jarFile: File): String? {
        ZipFile(jarFile).use { zip ->
            val entry = zip.entries().asSequence()
                .firstOrNull { it.name.endsWith(BUILD_CONFIG_CLASS_SUFFIX) }
                ?: return null

            zip.getInputStream(entry).use {
                return extractVersionFromClass(it)
            }
        }
    }

    private fun readBuildConfigFromAar(aarFile: File): String? {
        ZipFile(aarFile).use { aar ->
            val classesEntry = aar.getEntry("classes.jar") ?: return null

            aar.getInputStream(classesEntry).use { jarStream ->
                ZipInputStream(jarStream).use { zis ->
                    var entry: ZipEntry?

                    while (zis.nextEntry.also { entry = it } != null) {
                        if (entry!!.name.endsWith(BUILD_CONFIG_CLASS_SUFFIX)) {
                            return extractVersionFromClass(zis)
                        }
                    }
                }
            }
        }
        return null
    }

    private fun extractVersionFromClass(input: InputStream): String? {
        var version: String? = null

        val reader = ClassReader(input)
        reader.accept(object : ClassVisitor(Opcodes.ASM9) {
            override fun visitField(
                access: Int,
                name: String,
                descriptor: String,
                signature: String?,
                value: Any?
            ): FieldVisitor? {
                if (name == "SDK_VERSION" && value is String) {
                    version = value
                }
                return null
            }
        }, ClassReader.SKIP_CODE or ClassReader.SKIP_DEBUG or ClassReader.SKIP_FRAMES)

        return version
    }
}