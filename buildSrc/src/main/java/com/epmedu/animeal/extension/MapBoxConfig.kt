package com.epmedu.animeal.extension

import com.android.build.api.dsl.ApplicationDefaultConfig
import org.gradle.api.GradleException
import java.io.FileInputStream
import java.util.*


fun ApplicationDefaultConfig.addMapBoxPublicKeyField() {
    val type = "String"
    val name = "MAPBOX_PUBLIC_TOKEN"

    buildConfigField(
        type, name, "\"${loadRootGradleProperties()[name]}\""
    )
}

fun ApplicationDefaultConfig.addMapBoxStyleURIField() {
    val properties = loadRootGradleProperties()


    val mapBoxUsernameField = "MAPBOX_USERNAME"
    val mapBoxUsername =
        properties[mapBoxUsernameField] as String?
            ?: throw GradleException("$mapBoxUsernameField is not defined in gradle.properties")

    val mapBoxStyleIdField = "MAPBOX_STYLE_ID"
    val mapBoxStyleId = properties[mapBoxStyleIdField] as String?
        ?: throw GradleException("$mapBoxStyleIdField is not defined in gradle.properties")

    val mapBoxPublicKeyField = "MAPBOX_PUBLIC_TOKEN"
    val mapBoxPublicKey = properties[mapBoxPublicKeyField] as String?
        ?: throw GradleException("$mapBoxPublicKeyField is not defined in gradle.properties")

    val mapBoxStyleURI =
        "https://api.mapbox.com/styles/v1/${mapBoxUsername}/${mapBoxStyleId}?access_token=${mapBoxPublicKey}"


    val type = "String"
    val fieldName = "MAPBOX_STYLE_URI"

    buildConfigField(
        type, fieldName, "\"${mapBoxStyleURI}\""
    )
}

fun loadRootGradleProperties(): Properties =
    FileInputStream("gradle.properties").use { inputStream ->
        Properties().apply {
            load(inputStream)
        }
    }