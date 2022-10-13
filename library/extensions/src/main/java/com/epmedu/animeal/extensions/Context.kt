package com.epmedu.animeal.extensions

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.content.res.AppCompatResources

private const val URI_SCHEME = "package"

fun Context.launchAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts(URI_SCHEME, packageName, null)
    }
    startActivity(intent)
}

fun Context.drawableCompat(id: Int) = requireNotNull(AppCompatResources.getDrawable(this, id)) {
    "Drawable with $id is null"
}

inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
