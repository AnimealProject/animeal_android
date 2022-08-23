package com.epmedu.animeal.extension

import java.io.FileInputStream
import java.util.*

fun Properties.propertyInt(key: String): Int {
    val property = getProperty(key)

    if (property.isNullOrEmpty()) {
        throw IllegalArgumentException("property $key is null")
    }

    return try {
        property.toInt()
    } catch (exception: NumberFormatException) {
        throw IllegalArgumentException("Cast exception for $key")
    }
}

fun Properties.getStringPropertyOrThrow(key: String) = when {
    containsKey(key) -> this[key].toString()
    else -> throw Exception("$key is not defined in properties")
}

fun loadProperties(path: String): Properties =
    FileInputStream(path).use { inputStream ->
        Properties().apply {
            load(inputStream)
        }
    }