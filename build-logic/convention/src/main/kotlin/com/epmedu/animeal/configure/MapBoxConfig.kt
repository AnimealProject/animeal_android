package com.epmedu.animeal.configure

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.epmedu.animeal.extension.getStringPropertyOrThrow
import com.epmedu.animeal.extension.loadProperties
import com.epmedu.animeal.extension.stringField
import java.util.*

private val Properties.mapBoxUsername: String
    get() = getStringPropertyOrThrow("MAPBOX_USERNAME")

private val Properties.mapBoxStyleId: String
    get() = getStringPropertyOrThrow("MAPBOX_STYLE_ID")

private val Properties.mapBoxPublicToken: String
    get() = getStringPropertyOrThrow("MAPBOX_PUBLIC_TOKEN")

fun ApplicationDefaultConfig.addMapBoxPublicKeyField() {
    val properties = loadProperties(path = "local.properties")

    stringField(name = "MAPBOX_PUBLIC_TOKEN", value = properties.mapBoxPublicToken)
}

fun ApplicationDefaultConfig.addMapBoxStyleURIField() {
    val properties = loadProperties(path = "local.properties")

    val mapBoxUsername = properties.mapBoxUsername
    val mapBoxStyleId = properties.mapBoxStyleId
    val mapBoxPublicKey = properties.mapBoxPublicToken

    val mapBoxStyleURI =
        "https://api.mapbox.com/styles/v1/${mapBoxUsername}/${mapBoxStyleId}?access_token=${mapBoxPublicKey}"

    stringField(name = "MAPBOX_STYLE_URI", value = mapBoxStyleURI)
}