package com.epmedu.animeal.extension

import java.io.FileInputStream
import java.util.*

fun provideVersionCode(): Int {
    val properties: Properties = loadVersionProperties()
    val major = properties.propertyInt("VERSION")
    val minor = properties.propertyInt("SUB_VERSION")
    val patch = properties.propertyInt("BUILD_VERSION")
    val hotfix = properties.propertyInt("HOTFIX_VERSION")

    return calcVersionCode(major, minor, patch, hotfix)
}

fun provideVersionName(): String {
    val properties: Properties = loadVersionProperties()
    val major = properties.propertyInt("VERSION")
    val minor = properties.propertyInt("SUB_VERSION")
    val patch = properties.propertyInt("BUILD_VERSION")

    return "$major.$minor.$patch"
}

private fun calcVersionCode(major: Int, minor: Int, patch: Int, hotfix: Int): Int =
    major * 10000000 + minor * 100000 + patch * 100 + hotfix


fun loadVersionProperties(): Properties =
    FileInputStream("app/version.properties").use { inputStream ->
        Properties().apply {
            load(inputStream)
        }
    }