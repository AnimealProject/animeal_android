package com.epmedu.animeal.extension

import com.android.build.api.dsl.ApplicationDefaultConfig

fun ApplicationDefaultConfig.stringField(name: String, value: String) {
    buildConfigField("String", name, value.toField())
}

private fun String.toField() = "\"$this\""