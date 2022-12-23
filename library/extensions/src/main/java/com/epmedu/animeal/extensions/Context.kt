package com.epmedu.animeal.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.getSystemService
import com.epmedu.animeal.resources.R

private const val URI_SCHEME = "package"

fun Context.launchAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts(URI_SCHEME, packageName, null)
    }
    startActivity(intent)
}

// FLAG_ACTIVITY_NEW_TASK is required when we're starting activity from app context
fun Context.launchGpsSettings() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

fun Context.drawableCompat(id: Int) = requireNotNull(AppCompatResources.getDrawable(this, id)) {
    "Drawable with $id is null"
}

fun Context.copyText(
    text: String,
    @StringRes label: Int = R.string.text_copy_label,
    @StringRes toastText: Int?
) {
    val clipboard = getSystemService<ClipboardManager>()!!
    val clip = ClipData.newPlainText(getString(label), text)
    clipboard.setPrimaryClip(clip)
    toastText?.let {
        Toast
            .makeText(this, getString(toastText), Toast.LENGTH_SHORT)
            .show()
    }
}

inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
