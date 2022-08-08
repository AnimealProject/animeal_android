package com.epmedu.animeal.common.component

interface BuildConfigProvider {

    val appVersion: String

    val mapBoxPublicKey: String

    val mapBoxStyleURI: String
}