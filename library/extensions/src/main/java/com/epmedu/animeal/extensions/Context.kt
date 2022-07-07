package com.epmedu.animeal.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

private const val URI_SCHEME = "package"

fun Context.launchAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts(URI_SCHEME, packageName, null)
    }
    startActivity(intent)
}