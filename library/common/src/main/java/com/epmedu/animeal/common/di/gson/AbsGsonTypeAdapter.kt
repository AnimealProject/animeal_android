package com.epmedu.animeal.common.di.gson

import com.google.gson.JsonDeserializer
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializer

abstract class AbsGsonTypeAdapter<T> : JsonSerializer<T>, JsonDeserializer<T> {

    abstract val clazz: Class<T>

    protected fun getObjectClass(className: String): Class<*> {
        return try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }
}