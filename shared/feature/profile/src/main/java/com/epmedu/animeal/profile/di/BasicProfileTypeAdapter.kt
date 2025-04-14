package com.epmedu.animeal.profile.di

import com.epmedu.animeal.common.di.gson.AbsGsonTypeAdapter
import com.epmedu.animeal.profile.domain.model.Profile
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import javax.inject.Inject

class BasicProfileTypeAdapter @Inject constructor() : AbsGsonTypeAdapter<Profile>() {

    override val clazz: Class<Profile> = Profile::class.java

    override fun serialize(
        src: Profile?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonObject().apply {
            addProperty(CLASSNAME, src?.javaClass?.name)
            add(DATA, context?.serialize(src))
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Profile {
        val jsonObject = json?.asJsonObject
        val className = jsonObject?.get(CLASSNAME)?.asString ?: ""
        val clazz = getObjectClass(className)
        return context!!.deserialize(jsonObject?.get(DATA), clazz)
    }

    companion object {
        const val CLASSNAME = "profile_dto"
        const val DATA = "profile_data"
    }
}
